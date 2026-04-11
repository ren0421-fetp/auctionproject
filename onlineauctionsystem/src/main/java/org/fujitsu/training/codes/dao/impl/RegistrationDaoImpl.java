package org.fujitsu.training.codes.dao.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.CityDao;
import org.fujitsu.training.codes.dao.CountryDao;
import org.fujitsu.training.codes.dao.StateDao;
import org.fujitsu.training.codes.dao.UserDao;
import org.fujitsu.training.codes.exceptions.DuplicateUsernameException;
//import org.fujitsu.training.codes.exceptions.DuplicateUsernameException;
import org.fujitsu.training.codes.model.data.City;
import org.fujitsu.training.codes.model.data.Country;
import org.fujitsu.training.codes.model.data.State;
import org.fujitsu.training.codes.model.data.User;
import org.fujitsu.training.codes.model.form.RegistrationForm;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class RegistrationDaoImpl {
    private final UserDao userDao;
    private final CountryDao countryDao;
    private final StateDao stateDao;
    private final CityDao cityDao;
    private static final Logger logger = LogManager.getLogger(RegistrationDaoImpl.class);
    private SqlSessionFactory ssf;
    
    public RegistrationDaoImpl(UserDao userDao, CountryDao countryDao, StateDao stateDao, CityDao cityDao,
    		SqlSessionFactory ssf) {
        this.userDao = userDao;
        this.countryDao = countryDao;
        this.stateDao = stateDao;
        this.cityDao = cityDao;
        this.ssf = ssf;
    }

    public List<Country> getCountries() {
        return countryDao.selectAllCountries();
    }

    public List<State> getStates() {
        return stateDao.selectAllStates();
    }

    public List<City> getCities() {
        return cityDao.selectAllCities();
    }

    public String registerUser(RegistrationForm form) throws Exception {
        logger.info("Starting registration process for username: {}", form.getUsername());
        
        SqlSession sess = ssf.openSession();
        
        try {
            // 1. Check for Duplicate Username
            User existingUser = sess.selectOne("selectByUsername", form.getUsername());
            if (existingUser != null) {
                logger.warn("Registration failed: Username '{}' already exists.", form.getUsername());
                throw new DuplicateUsernameException("Username already exists");
            }

            // 2. Validate Location Hierarchy using mapped statements
            Map<String, Integer> stateParams = new HashMap<>();
            stateParams.put("stateId", form.getStateId());
            stateParams.put("countryId", form.getCountryId());
            Integer stateCount = sess.selectOne("countStateByCountry", stateParams);
            if (stateCount == null || stateCount == 0) {
                throw new IllegalArgumentException("Selected state does not belong to the selected country.");
            }

            Map<String, Integer> cityParams = new HashMap<>();
            cityParams.put("cityId", form.getCityId());
            cityParams.put("stateId", form.getStateId());
            Integer cityCount = sess.selectOne("countCityByState", cityParams);
            if (cityCount == null || cityCount == 0) {
                throw new IllegalArgumentException("Selected city does not belong to the selected state.");
            }

            // 3. Map Form to User Data Model
            User user = new User();
            user.setUsername(form.getUsername());
            user.setFirstName(form.getFirstName());
            user.setLastName(form.getLastName());
            user.setGender(form.getGender());
            user.setAddress(form.getAddress());
            user.setCountryId(form.getCountryId());
            user.setStateId(form.getStateId());
            user.setCityId(form.getCityId());
            user.setEmail(form.getEmail());
            user.setContactNo(form.getContactNo());
            user.setPhotoPath(form.getPhotoPath()); // Path is set by the Controller after upload
            user.setPasswordHash(hashPassword(form.getPassword()));
            user.setUserType(form.getUserType());
            user.setIsLocked(false);
            user.setFailedLoginAttempts(0);

            // 4. Execute Insert and Commit
            sess.insert("insertUser", user);
            sess.commit();
            
            logger.info("Successfully registered user: {}", user.getUsername());
            return user.getUsername();

        } catch (DuplicateUsernameException | IllegalArgumentException e) {
            // Rollback and rethrow business exceptions so the Controller can display them to the UI
            sess.rollback();
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected database error while registering user {}: {}", form.getUsername(), e.getMessage(), e);
            sess.rollback();
            throw new Exception("Registration failed due to an unexpected system error.", e);
        } finally {
            // Guarantee closure to prevent connection leaks
            sess.close();
        }
    }

    private String hashPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(hash);
    }
}
