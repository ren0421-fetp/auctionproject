package org.fujitsu.training.codes.service;

import java.util.List;
import org.fujitsu.training.codes.dao.impl.RegistrationDaoImpl;
import org.fujitsu.training.codes.model.data.City;
import org.fujitsu.training.codes.model.data.Country;
import org.fujitsu.training.codes.model.data.State;
import org.fujitsu.training.codes.model.form.RegistrationForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {

    private final RegistrationDaoImpl registrationDao;
    private final FileService fileService;

    public UserRegistrationService(RegistrationDaoImpl registrationDao, FileService fileService) {
        this.registrationDao = registrationDao;
        this.fileService = fileService;
    }

    @Transactional
    public String register(RegistrationForm form) throws Exception {
        if (form.getPhotoFile() != null && !form.getPhotoFile().isEmpty()) {
            String photoPath = fileService.saveFile(form.getPhotoFile(), "profile", form.getUsername());
            form.setPhotoPath(photoPath);
        }
        return registrationDao.registerUser(form);
    }

    public List<Country> getCountries() {
        return registrationDao.getCountries();
    }

    public List<State> getStates() {
        return registrationDao.getStates();
    }

    public List<City> getCities() {
        return registrationDao.getCities();
    }
}