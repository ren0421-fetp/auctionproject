/*package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.dao.UserDao;
import org.fujitsu.training.codes.model.data.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/seller")
public class SellerDashController {
    private final UserDao userDao; // Inject your UserDao

    public SellerDashController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping("/home")
    public String showSellerHome(HttpSession session, Model model) {
        String username = (String) session.getAttribute("loggedInUsername");
        
        if (username == null) {
            return "redirect:/login"; // fix this
        }

        // 2. Fetch the specific User details from the database
        User specificUser = userDao.selectByUsername(username); 
        
        // 3. Pass the specific user to the JSP
        model.addAttribute("loggedInUser", specificUser);
        
        return "sellerMainDash"; 
    }
}*/

package org.fujitsu.training.codes.controller;

import org.fujitsu.training.codes.helper.SellerDashHelper;
import org.fujitsu.training.codes.helper.SessionRoleHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/seller")
public class SellerDashController {

    private static final String LOGIN_REDIRECT = "redirect:/app/login";

    private final SellerDashHelper sellerDashHelper;
    private final SessionRoleHelper sessionRoleHelper;

    public SellerDashController(SellerDashHelper sellerDashHelper,
            SessionRoleHelper sessionRoleHelper) {
        this.sellerDashHelper = sellerDashHelper;
        this.sessionRoleHelper = sessionRoleHelper;
    }

    @RequestMapping("/home")
    public String showSellerHome(HttpSession session, Model model) {
        String username = sessionRoleHelper.getSellerUsername(session);
        if (username == null) {
            return LOGIN_REDIRECT;
        }

        return sellerDashHelper.prepareSellerHome(username, model);
    }
}
