package com.cool.cool.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Dang Dim
 * Date     : 08-Jan-18, 10:47 AM
 * Email    : d.dim@gl-f.com
 */

@RestController
@RequestMapping("/secure")
public class SecureController {

    public String secured() {
        return "you're secure now ! ";
    }

}
