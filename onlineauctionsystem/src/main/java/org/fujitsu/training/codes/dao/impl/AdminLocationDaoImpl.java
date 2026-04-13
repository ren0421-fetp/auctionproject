/*package org.fujitsu.training.codes.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.City;
import org.fujitsu.training.codes.model.data.Country;
import org.fujitsu.training.codes.model.data.State;
import org.fujitsu.training.codes.model.form.CityForm;
import org.fujitsu.training.codes.model.form.CountryForm;
import org.fujitsu.training.codes.model.form.StateForm;
import org.springframework.stereotype.Repository;

@Repository
public class AdminLocationDaoImpl {
    private static final Logger logger = LogManager.getLogger(AdminLocationDaoImpl.class);
    private final SqlSessionFactory ssf;

    public AdminLocationDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<Country> getCountries() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.CountryDao.selectAllCountries");
        }
    }

    public List<State> getStates() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.StateDao.selectAllStates");
        }
    }

    public List<City> getCities() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.CityDao.selectAllCities");
        }
    }

    public CountryForm getCountryForm(Integer countryId) {
        try (SqlSession sess = ssf.openSession()) {
            Country country = sess.selectOne("org.fujitsu.training.codes.dao.CountryDao.selectCountryById", countryId);
            if (country == null) {
                return null;
            }

            CountryForm form = new CountryForm();
            form.setCountryId(country.getCountryId());
            form.setCountryName(country.getCountryName());
            return form;
        }
    }

    public StateForm getStateForm(Integer stateId) {
        try (SqlSession sess = ssf.openSession()) {
            State state = sess.selectOne("org.fujitsu.training.codes.dao.StateDao.selectStateById", stateId);
            if (state == null) {
                return null;
            }

            StateForm form = new StateForm();
            form.setStateId(state.getStateId());
            form.setCountryId(state.getCountryId());
            form.setStateName(state.getStateName());
            return form;
        }
    }

    public CityForm getCityForm(Integer cityId) {
        try (SqlSession sess = ssf.openSession()) {
            City city = sess.selectOne("org.fujitsu.training.codes.dao.CityDao.selectCityById", cityId);
            if (city == null) {
                return null;
            }

            CityForm form = new CityForm();
            form.setCityId(city.getCityId());
            form.setStateId(city.getStateId());
            form.setCityName(city.getCityName());
            return form;
        }
    }

    public void saveCountry(CountryForm form) throws Exception {
        logger.info("Saving country {}", form.getCountryName());

        SqlSession sess = ssf.openSession();
        try {
            java.util.Map<String, Object> params = new java.util.HashMap<>();
            params.put("countryName", form.getCountryName());
            params.put("countryId", form.getCountryId());

            Integer duplicateCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.CountryDao.countCountryByName",
                    params);

            if (duplicateCount != null && duplicateCount > 0) {
                throw new IllegalArgumentException("Country name already exists.");
            }

            Country country = new Country();
            country.setCountryId(form.getCountryId());
            country.setCountryName(form.getCountryName().trim());

            Integer affected;
            if (form.getCountryId() == null) {
                affected = sess.insert("org.fujitsu.training.codes.dao.CountryDao.insertCountry", country);
            } else {
                affected = sess.update("org.fujitsu.training.codes.dao.CountryDao.updateCountry", country);
            }

            if (affected == null || affected != 1) {
                throw new IllegalStateException("Failed to save country.");
            }

            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void saveState(StateForm form) throws Exception {
        logger.info("Saving state {}", form.getStateName());

        SqlSession sess = ssf.openSession();
        try {
            java.util.Map<String, Object> stateParams = new java.util.HashMap<>();
            stateParams.put("stateName", form.getStateName());
            stateParams.put("countryId", form.getCountryId());
            stateParams.put("stateId", form.getStateId());

            Integer duplicateCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.StateDao.countStateByName",
                    stateParams);


            if (duplicateCount != null && duplicateCount > 0) {
                throw new IllegalArgumentException("State name already exists in the selected country.");
            }

            State state = new State();
            state.setStateId(form.getStateId());
            state.setCountryId(form.getCountryId());
            state.setStateName(form.getStateName().trim());

            Integer affected;
            if (form.getStateId() == null) {
                affected = sess.insert("org.fujitsu.training.codes.dao.StateDao.insertState", state);
            } else {
                affected = sess.update("org.fujitsu.training.codes.dao.StateDao.updateState", state);
            }

            if (affected == null || affected != 1) {
                throw new IllegalStateException("Failed to save state.");
            }

            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void saveCity(CityForm form) throws Exception {
        logger.info("Saving city {}", form.getCityName());

        SqlSession sess = ssf.openSession();
        try {
            java.util.Map<String, Object> cityParams = new java.util.HashMap<>();
            cityParams.put("cityName", form.getCityName());
            cityParams.put("stateId", form.getStateId());
            cityParams.put("cityId", form.getCityId());

            Integer duplicateCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.CityDao.countCityByName",
                    cityParams);


            if (duplicateCount != null && duplicateCount > 0) {
                throw new IllegalArgumentException("City name already exists in the selected state.");
            }

            City city = new City();
            city.setCityId(form.getCityId());
            city.setStateId(form.getStateId());
            city.setCityName(form.getCityName().trim());

            Integer affected;
            if (form.getCityId() == null) {
                affected = sess.insert("org.fujitsu.training.codes.dao.CityDao.insertCity", city);
            } else {
                affected = sess.update("org.fujitsu.training.codes.dao.CityDao.updateCity", city);
            }

            if (affected == null || affected != 1) {
                throw new IllegalStateException("Failed to save city.");
            }

            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void deleteCountry(Integer countryId) throws Exception {
        SqlSession sess = ssf.openSession();
        try {
            Integer stateCount = sess.selectOne("org.fujitsu.training.codes.dao.CountryDao.countStatesByCountryId", countryId);
            Integer userCount = sess.selectOne("org.fujitsu.training.codes.dao.CountryDao.countUsersByCountryId", countryId);

            if ((stateCount != null && stateCount > 0) || (userCount != null && userCount > 0)) {
                throw new IllegalArgumentException("Country cannot be deleted because it is already in use.");
            }

            Integer deleted = sess.delete("org.fujitsu.training.codes.dao.CountryDao.deleteCountryById", countryId);
            if (deleted == null || deleted != 1) {
                throw new IllegalStateException("Failed to delete country.");
            }

            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void deleteState(Integer stateId) throws Exception {
        SqlSession sess = ssf.openSession();
        try {
            Integer cityCount = sess.selectOne("org.fujitsu.training.codes.dao.StateDao.countCitiesByStateId", stateId);
            Integer userCount = sess.selectOne("org.fujitsu.training.codes.dao.StateDao.countUsersByStateId", stateId);

            if ((cityCount != null && cityCount > 0) || (userCount != null && userCount > 0)) {
                throw new IllegalArgumentException("State cannot be deleted because it is already in use.");
            }

            Integer deleted = sess.delete("org.fujitsu.training.codes.dao.StateDao.deleteStateById", stateId);
            if (deleted == null || deleted != 1) {
                throw new IllegalStateException("Failed to delete state.");
            }

            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void deleteCity(Integer cityId) throws Exception {
        SqlSession sess = ssf.openSession();
        try {
            Integer userCount = sess.selectOne("org.fujitsu.training.codes.dao.CityDao.countUsersByCityId", cityId);

            if (userCount != null && userCount > 0) {
                throw new IllegalArgumentException("City cannot be deleted because it is already in use.");
            }

            Integer deleted = sess.delete("org.fujitsu.training.codes.dao.CityDao.deleteCityById", cityId);
            if (deleted == null || deleted != 1) {
                throw new IllegalStateException("Failed to delete city.");
            }

            sess.commit();
        } catch (Exception ex) {
            sess.rollback();
            throw ex;
        } finally {
            sess.close();
        }
    }
}
*/

package org.fujitsu.training.codes.dao.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.model.data.City;
import org.fujitsu.training.codes.model.data.Country;
import org.fujitsu.training.codes.model.data.State;
import org.fujitsu.training.codes.model.form.CityForm;
import org.fujitsu.training.codes.model.form.CountryForm;
import org.fujitsu.training.codes.model.form.StateForm;
import org.springframework.stereotype.Repository;

@Repository
public class AdminLocationDaoImpl {
    private static final Logger logger = LogManager.getLogger("admin-flow");

    private final SqlSessionFactory ssf;

    public AdminLocationDaoImpl(SqlSessionFactory ssf) {
        this.ssf = ssf;
    }

    public List<Country> getCountries() {
    	logger.info("Getting all countries");
        try (SqlSession sess = ssf.openSession()) {
        	logger.info("Returning all countries");
        	return sess.selectList("org.fujitsu.training.codes.dao.CountryDao.selectAllCountries");
        } catch (Exception ex) {
            logger.error("Failed to load countries: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<State> getStates() {
        try (SqlSession sess = ssf.openSession()) {
            return sess.selectList("org.fujitsu.training.codes.dao.StateDao.selectAllStates");
        } catch (Exception ex) {
            logger.error("Failed to load states: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public List<City> getCities() {
    	logger.info("Getting all cities");
        try (SqlSession sess = ssf.openSession()) {
        	logger.info("Returning all cities");
            return sess.selectList("org.fujitsu.training.codes.dao.CityDao.selectAllCities");
        } catch (Exception ex) {
            logger.error("Failed to load cities: {}", ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }

    public CountryForm getCountryForm(Integer countryId) {
    	logger.info("Getting country form");
        try (SqlSession sess = ssf.openSession()) {
            Country country = sess.selectOne(
                    "org.fujitsu.training.codes.dao.CountryDao.selectCountryById",
                    countryId);

            if (country == null) {
                return null;
            }

            CountryForm form = new CountryForm();
            form.setCountryId(country.getCountryId());
            form.setCountryName(country.getCountryName());
            logger.info("Returning country form");
            return form;
        } catch (Exception ex) {
            logger.error("Failed to load country form for countryId={}: {}", countryId, ex.getMessage(), ex);
            return null;
        }
    }

    public StateForm getStateForm(Integer stateId) {
    	logger.info("Getting state form");
        try (SqlSession sess = ssf.openSession()) {
            State state = sess.selectOne(
                    "org.fujitsu.training.codes.dao.StateDao.selectStateById",
                    stateId);

            if (state == null) {
                return null;
            }

            StateForm form = new StateForm();
            form.setStateId(state.getStateId());
            form.setCountryId(state.getCountryId());
            form.setStateName(state.getStateName());
            logger.info("Returning state form");
            return form;
        } catch (Exception ex) {
            logger.error("Failed to load state form for stateId={}: {}", stateId, ex.getMessage(), ex);
            return null;
        }
    }

    public CityForm getCityForm(Integer cityId) {
    	logger.info("Getting city form");
        try (SqlSession sess = ssf.openSession()) {
            City city = sess.selectOne(
                    "org.fujitsu.training.codes.dao.CityDao.selectCityById",
                    cityId);

            if (city == null) {
                return null;
            }

            CityForm form = new CityForm();
            form.setCityId(city.getCityId());
            form.setStateId(city.getStateId());
            form.setCityName(city.getCityName());
            logger.info("Returning city form");
            return form;
        } catch (Exception ex) {
            logger.error("Failed to load city form for cityId={}: {}", cityId, ex.getMessage(), ex);
            return null;
        }
    }

    public void saveCountry(CountryForm form) throws Exception {
        logger.info("Starting country save. countryId={}, countryName={}",
                form.getCountryId(), form.getCountryName());

        SqlSession sess = ssf.openSession();
        try {
            validateDuplicateCountry(sess, form);

            Country country = new Country();
            country.setCountryId(form.getCountryId());
            country.setCountryName(form.getCountryName().trim());

            Integer affected;
            if (form.getCountryId() == null) {
                affected = sess.insert("org.fujitsu.training.codes.dao.CountryDao.insertCountry", country);
            } else {
                affected = sess.update("org.fujitsu.training.codes.dao.CountryDao.updateCountry", country);
            }

            validateAffectedRow(affected, "Failed to save country.");
            sess.commit();

            logger.info("Country save completed. countryId={}, countryName={}",
                    form.getCountryId(), form.getCountryName());
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Country save failed. countryId={}, countryName={}: {}",
                    form.getCountryId(), form.getCountryName(), ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void saveState(StateForm form) throws Exception {
        logger.info("Starting state save. stateId={}, stateName={}, countryId={}",
                form.getStateId(), form.getStateName(), form.getCountryId());

        SqlSession sess = ssf.openSession();
        try {
            validateDuplicateState(sess, form);

            State state = new State();
            state.setStateId(form.getStateId());
            state.setCountryId(form.getCountryId());
            state.setStateName(form.getStateName().trim());

            Integer affected;
            if (form.getStateId() == null) {
                affected = sess.insert("org.fujitsu.training.codes.dao.StateDao.insertState", state);
            } else {
                affected = sess.update("org.fujitsu.training.codes.dao.StateDao.updateState", state);
            }

            validateAffectedRow(affected, "Failed to save state.");
            sess.commit();

            logger.info("State save completed. stateId={}, stateName={}, countryId={}",
                    form.getStateId(), form.getStateName(), form.getCountryId());
        } catch (Exception ex) {
            sess.rollback();
            logger.error("State save failed. stateId={}, stateName={}, countryId={}: {}",
                    form.getStateId(), form.getStateName(), form.getCountryId(), ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void saveCity(CityForm form) throws Exception {
        logger.info("Starting city save. cityId={}, cityName={}, stateId={}",
                form.getCityId(), form.getCityName(), form.getStateId());

        SqlSession sess = ssf.openSession();
        try {
            validateDuplicateCity(sess, form);

            City city = new City();
            city.setCityId(form.getCityId());
            city.setStateId(form.getStateId());
            city.setCityName(form.getCityName().trim());

            Integer affected;
            if (form.getCityId() == null) {
                affected = sess.insert("org.fujitsu.training.codes.dao.CityDao.insertCity", city);
            } else {
                affected = sess.update("org.fujitsu.training.codes.dao.CityDao.updateCity", city);
            }

            validateAffectedRow(affected, "Failed to save city.");
            sess.commit();

            logger.info("City save completed. cityId={}, cityName={}, stateId={}",
                    form.getCityId(), form.getCityName(), form.getStateId());
        } catch (Exception ex) {
            sess.rollback();
            logger.error("City save failed. cityId={}, cityName={}, stateId={}: {}",
                    form.getCityId(), form.getCityName(), form.getStateId(), ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void deleteCountry(Integer countryId) throws Exception {
        logger.info("Starting country delete. countryId={}", countryId);

        SqlSession sess = ssf.openSession();
        try {
            Integer stateCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.CountryDao.countStatesByCountryId",
                    countryId);
            Integer userCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.CountryDao.countUsersByCountryId",
                    countryId);

            if ((stateCount != null && stateCount > 0) || (userCount != null && userCount > 0)) {
                throw new IllegalArgumentException("Country cannot be deleted because it is already in use.");
            }

            Integer deleted = sess.delete(
                    "org.fujitsu.training.codes.dao.CountryDao.deleteCountryById",
                    countryId);

            validateAffectedRow(deleted, "Failed to delete country.");
            sess.commit();

            logger.info("Country delete completed. countryId={}", countryId);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("Country delete failed. countryId={}: {}", countryId, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void deleteState(Integer stateId) throws Exception {
        logger.info("Starting state delete. stateId={}", stateId);

        SqlSession sess = ssf.openSession();
        try {
            Integer cityCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.StateDao.countCitiesByStateId",
                    stateId);
            Integer userCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.StateDao.countUsersByStateId",
                    stateId);

            if ((cityCount != null && cityCount > 0) || (userCount != null && userCount > 0)) {
                throw new IllegalArgumentException("State cannot be deleted because it is already in use.");
            }

            Integer deleted = sess.delete(
                    "org.fujitsu.training.codes.dao.StateDao.deleteStateById",
                    stateId);

            validateAffectedRow(deleted, "Failed to delete state.");
            sess.commit();

            logger.info("State delete completed. stateId={}", stateId);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("State delete failed. stateId={}: {}", stateId, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    public void deleteCity(Integer cityId) throws Exception {
        logger.info("Starting city delete. cityId={}", cityId);

        SqlSession sess = ssf.openSession();
        try {
            Integer userCount = sess.selectOne(
                    "org.fujitsu.training.codes.dao.CityDao.countUsersByCityId",
                    cityId);

            if (userCount != null && userCount > 0) {
                throw new IllegalArgumentException("City cannot be deleted because it is already in use.");
            }

            Integer deleted = sess.delete(
                    "org.fujitsu.training.codes.dao.CityDao.deleteCityById",
                    cityId);

            validateAffectedRow(deleted, "Failed to delete city.");
            sess.commit();

            logger.info("City delete completed. cityId={}", cityId);
        } catch (Exception ex) {
            sess.rollback();
            logger.error("City delete failed. cityId={}: {}", cityId, ex.getMessage(), ex);
            throw ex;
        } finally {
            sess.close();
        }
    }

    private void validateDuplicateCountry(SqlSession sess, CountryForm form) {
        Map<String, Object> params = new HashMap<>();
        params.put("countryName", form.getCountryName());
        params.put("countryId", form.getCountryId());

        Integer duplicateCount = sess.selectOne(
                "org.fujitsu.training.codes.dao.CountryDao.countCountryByName",
                params);

        if (duplicateCount != null && duplicateCount > 0) {
            throw new IllegalArgumentException("Country name already exists.");
        }
    }

    private void validateDuplicateState(SqlSession sess, StateForm form) {
        Map<String, Object> params = new HashMap<>();
        params.put("stateName", form.getStateName());
        params.put("countryId", form.getCountryId());
        params.put("stateId", form.getStateId());

        Integer duplicateCount = sess.selectOne(
                "org.fujitsu.training.codes.dao.StateDao.countStateByName",
                params);

        if (duplicateCount != null && duplicateCount > 0) {
            throw new IllegalArgumentException("State name already exists in the selected country.");
        }
    }

    private void validateDuplicateCity(SqlSession sess, CityForm form) {
        Map<String, Object> params = new HashMap<>();
        params.put("cityName", form.getCityName());
        params.put("stateId", form.getStateId());
        params.put("cityId", form.getCityId());

        Integer duplicateCount = sess.selectOne(
                "org.fujitsu.training.codes.dao.CityDao.countCityByName",
                params);

        if (duplicateCount != null && duplicateCount > 0) {
            throw new IllegalArgumentException("City name already exists in the selected state.");
        }
    }

    private void validateAffectedRow(Integer affected, String errorMessage) {
        if (affected == null || affected != 1) {
            throw new IllegalStateException(errorMessage);
        }
    }
}

