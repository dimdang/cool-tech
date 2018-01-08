package com.cool.cool.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dang Dim
 * Date     : 08-Jan-18, 10:47 AM
 * Email    : d.dim@gl-f.com
 */

@RestController
@RequestMapping("/product")
public class HomeController {

    public String home(){
        return "welcome to home page!";
    }
}
