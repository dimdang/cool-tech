package com.cool.cool.controller;

import com.cool.cool.repositories.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dang Dim
 * Date     : 08-Jan-18, 10:47 AM
 * Email    : d.dim@gl-f.com
 */

@RestController
@RequestMapping("/product")
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String home(){
        return "welcome to home page!";
    }
}
