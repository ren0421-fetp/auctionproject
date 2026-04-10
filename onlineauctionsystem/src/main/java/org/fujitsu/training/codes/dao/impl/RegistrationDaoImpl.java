package org.fujitsu.training.codes.dao.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;

import org.fujitsu.training.codes.dao.CityDao;
import org.fujitsu.training.codes.dao.CountryDao;
import org.fujitsu.training.codes.dao.StateDao;
import org.fujitsu.training.codes.dao.UserDao;
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

    public RegistrationDaoImpl(UserDao userDao, CountryDao countryDao, StateDao stateDao, CityDao cityDao) {
        this.userDao = userDao;
        this.countryDao = countryDao;
        this.stateDao = stateDao;
        this.cityDao = cityDao;
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
        if (userDao.selectByUsername(form.getUsername()) != null) {
            //throw new DuplicateUsernameException("username already exists");
        	throw new Exception();
        }

        if (stateDao.countStateByCountry(form.getStateId(), form.getCountryId()) == 0) {
            throw new IllegalArgumentException("selected state does not belong to the selected country");
        }

        if (cityDao.countCityByState(form.getCityId(), form.getStateId()) == 0) {
            throw new IllegalArgumentException("selected city does not belong to the selected state");
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

        boolean inserted = userDao.insertUser(user);
        if (inserted != true) {
            throw new IllegalStateException("failed to save registration");
        }

        return user.getUsername();
    }

    private String hashPassword(String rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (Exception ex) {
            throw new IllegalStateException("failed to hash password", ex);
        }
    }
}
