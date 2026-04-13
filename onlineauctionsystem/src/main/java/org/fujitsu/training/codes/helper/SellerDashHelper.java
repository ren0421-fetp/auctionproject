/*package org.fujitsu.training.codes.helper;

import org.fujitsu.training.codes.dao.UserDao;
import org.fujitsu.training.codes.model.data.User;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class SellerDashHelper {

    private final UserDao userDao;

    public SellerDashHelper(UserDao userDao) {
        this.userDao = userDao;
    }

    public String prepareSellerHome(String username, Model model) {
        User specificUser = userDao.selectByUsername(username);
        if (specificUser == null) {
            return "redirect:/app/login";
        }

        model.addAttribute("loggedInUser", specificUser);
        return "sellerMainDash";
    }
}*/

package org.fujitsu.training.codes.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fujitsu.training.codes.dao.UserDao;
import org.fujitsu.training.codes.model.data.User;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class SellerDashHelper {
    private static final Logger logger = LogManager.getLogger("seller-flow");

    private static final String LOGIN_REDIRECT = "redirect:/app/login";

    private final UserDao userDao;

    public SellerDashHelper(UserDao userDao) {
        this.userDao = userDao;
    }

    public String prepareSellerHome(String username, Model model) {
        logger.info("Loading seller dashboard for {}.", username);
        User specificUser = userDao.selectByUsername(username);
        if (specificUser == null) {
            logger.warn("Seller dashboard load failed. User {} not found.", username);
            return LOGIN_REDIRECT;
        }

        model.addAttribute("loggedInUser", specificUser);
        logger.info("Seller dashboard loaded for {}.", username);
        return "sellerMainDash";
    }
}

