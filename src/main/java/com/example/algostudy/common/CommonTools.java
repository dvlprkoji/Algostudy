package com.example.algostudy.common;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

@Controller
public class CommonTools {

    public void printParams(HttpServletRequest request) {
        System.out.println("=================["+request.getRequestURI()+"]=================");
        request.getParameterNames().asIterator().forEachRemaining(name -> {
            System.out.println(name + " = " + request.getParameter(name));
        });
        System.out.println("================================================================");


    }
}
