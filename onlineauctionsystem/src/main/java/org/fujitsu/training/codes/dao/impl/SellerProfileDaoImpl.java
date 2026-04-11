package org.fujitsu.training.codes.dao.impl;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.CityDao;
import org.fujitsu.training.codes.dao.CountryDao;
import org.fujitsu.training.codes.dao.StateDao;
import org.fujitsu.training.codes.dao.UserDao;
import org.fujitsu.training.codes.model.data.City;
import org.fujitsu.training.codes.model.data.Country;
import org.fujitsu.training.codes.model.data.State;
import org.fujitsu.training.codes.model.data.User;
import org.fujitsu.training.codes.model.form.ChangePasswordForm;
import org.fujitsu.training.codes.model.form.SellerProfileForm;
import org.springframework.stereotype.Repository;

@Repository
public class SellerProfileDaoImpl {
    private static final Logger logger = LogManager.getLogger(SellerProfileDaoImpl.class);

    private final UserDao userDao;
    private final CountryDao countryDao;
    private final StateDao stateDao;
    private final CityDao cityDao;

    public SellerProfileDaoImpl(UserDao userDao, CountryDao countryDao, StateDao stateDao, CityDao cityDao) {
        this.userDao = userDao;
        this.countryDao = countryDao;
        this.stateDao = stateDao;
        this.cityDao = cityDao;
    }

    public SellerProfileForm getSellerProfileForm(String username) {
        User user = userDao.selectByUsername(username);
        if (user == null || !"seller".equalsIgnoreCase(user.getUserType())) {
            return null;
        }

        SellerProfileForm form = new SellerProfileForm();
        form.setUsername(user.getUsername());
        form.setFirstName(user.getFirstName());
        form.setLastName(user.getLastName());
        form.setGender(user.getGender());
        form.setAddress(user.getAddress());
        form.setCountryId(user.getCountryId());
        form.setStateId(user.getStateId());
        form.setCityId(user.getCityId());
        form.setEmail(user.getEmail());
        form.setContactNo(user.getContactNo());
        form.setPhotoPath(user.getPhotoPath());
        form.setCurrentPhotoPath(user.getPhotoPath());
        return form;
    }

    public void updateSellerProfile(SellerProfileForm form, String username) throws Exception {
        logger.info("Updating seller profile for username: {}", username);

        User existing = userDao.selectByUsername(username);
        if (existing == null || !"seller".equalsIgnoreCase(existing.getUserType())) {
            throw new IllegalArgumentException("Seller account not found.");
        }

        User user = new User();
        user.setUsername(username);
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

        int updated = userDao.updateSellerProfile(user);
        if (updated != 1) {
            throw new IllegalStateException("Seller profile update failed.");
        }
    }

    public void changeSellerPassword(ChangePasswordForm form, String username) throws Exception {
        logger.info("Changing seller password for username: {}", username);

        User existing = userDao.selectByUsername(username);
        if (existing == null || !"seller".equalsIgnoreCase(existing.getUserType())) {
            throw new IllegalArgumentException("Seller account not found.");
        }

        String currentHash = hashPassword(form.getCurrentPassword());
        if (!currentHash.equals(existing.getPasswordHash())) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }

        String newHash = hashPassword(form.getNewPassword());
        int updated = userDao.updateSellerPassword(username, newHash);
        if (updated != 1) {
            throw new IllegalStateException("Password update failed.");
        }
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

    private String hashPassword(String rawPassword) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
        return HexFormat.of().formatHex(hash);
    }
}
