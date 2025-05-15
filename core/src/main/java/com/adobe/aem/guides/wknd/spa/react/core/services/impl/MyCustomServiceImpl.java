package com.adobe.aem.guides.wknd.spa.react.core.services.impl;

import org.osgi.service.component.annotations.Component;

import com.adobe.aem.guides.wknd.spa.react.core.services.MyCustomService;

@Component(service = MyCustomService.class, immediate = true)
public class MyCustomServiceImpl implements MyCustomService {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name + "!";
    }
}