/*package org.fujitsu.training.codes.dao.impl;

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
*/

package org.fujitsu.training.codes.dao.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
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
import org.fujitsu.training.codes.model.data.City;
import org.fujitsu.training.codes.model.data.Country;
import org.fujitsu.training.codes.model.data.State;
import org.fujitsu.training.codes.model.data.User;
import org.fujitsu.training.codes.model.form.RegistrationForm;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrationDaoImpl {
    private static final Logger logger = LogManager.getLogger("auth-flow");

    private final UserDao userDao;
    private final CountryDao countryDao;
    private final StateDao stateDao;
    private final CityDao cityDao;
    private final SqlSessionFactory ssf;

    public RegistrationDaoImpl(UserDao userDao,
            CountryDao countryDao,
            StateDao stateDao,
            CityDao cityDao,
            SqlSessionFactory ssf) {
        this.userDao = userDao;
        this.countryDao = countryDao;
        this.stateDao = stateDao;
        this.cityDao = cityDao;
        this.ssf = ssf;
    }

    public List<Country> getCountries() {
    	logger.info("Getting countries");
        try {
        	logger.info("Returning countries");
            return countryDao.selectAllCountries();
        } catch (Exception ex) {
            logger.error("Failed to load countries for registration: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<State> getStates() {
    	logger.info("Getting states");
        try {
        	logger.info("Returning states");
            return stateDao.selectAllStates();
        } catch (Exception ex) {
            logger.error("Failed to load states for registration: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<City> getCities() {
    	logger.info("Getting cities");
        try {
        	logger.info("Returning all cities");
            return cityDao.selectAllCities();
        } catch (Exception ex) {
            logger.error("Failed to load cities for registration: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public String registerUser(RegistrationForm form) throws Exception {
        logger.info("Starting registration save. username={}", form.getUsername());

        SqlSession sess = ssf.openSession();
        try {
            User existingUser = sess.selectOne(
                    "org.fujitsu.training.codes.dao.UserDao.selectByUsername",
                    form.getUsername());

            if (existingUser != null) {
                throw new DuplicateUsernameException("Username already exists.");
            }

            Map<String, Integer> stateParams = new HashMap<>();
            stateParams.put("stateId", form.getStateId());
            stateParams.put("countryId", form.getCountryId());

            Integer stateCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.StateDao.countStateByCountry",
                    stateParams);

            if (stateCount == null || stateCount == 0) {
                throw new IllegalArgumentException("Selected state does not belong to the selected country.");
            }

            Map<String, Integer> cityParams = new HashMap<>();
            cityParams.put("cityId", form.getCityId());
            cityParams.put("stateId", form.getStateId());

            Integer cityCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.CityDao.countCityByState",
                    cityParams);

            if (cityCount == null || cityCount == 0) {
                throw new IllegalArgumentException("Selected city does not belong to the selected state.");
            }

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
            user.setPhotoPath(form.getPhotoPath());
            user.setPasswordHash(hashPassword(form.getPassword()));
            user.setUserType(form.getUserType());
            user.setIsLocked(false);
            user.setFailedLoginAttempts(0);

            Integer inserted = sess.insert(
                    "org.fujitsu.training.codes.dao.UserDao.insertUser",
                    user);

            if (inserted == null || inserted != 1) {
                throw new IllegalStateException("Failed to register user.");
            }

            sess.commit();
            logger.info("Registration save completed. username={}", user.getUsername());
            return user.getUsername();
        } catch (DuplicateUsernameException ex) {
            sess.rollback();
            logger.error("Registration save failed. username={}: {}", form.getUsername(), ex.getMessage(), ex);
            throw ex;
        } catch (IllegalArgumentException ex) {
            sess.rollback();
            logger.error("Registration save failed. username={}: {}", form.getUsername(), ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Registration save failed. username={}: {}", form.getUsername(), ex.getMessage(), ex);
            throw new Exception("Registration failed due to an unexpected system error.", ex);
        } finally {
            sess.close();
        }
    }


    private String hashPassword(String password) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(hash);
    }
}
