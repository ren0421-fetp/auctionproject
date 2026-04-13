package org.fujitsu.training.codes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/home")
public class PublicHomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String loadHome() {
        return "homeView";
    }
}
