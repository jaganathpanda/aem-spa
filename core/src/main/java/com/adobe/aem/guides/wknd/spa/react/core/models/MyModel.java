package com.adobe.aem.guides.wknd.spa.react.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.adobe.aem.guides.wknd.spa.react.core.services.MyCustomService;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MyModel {

    @OSGiService
    private MyCustomService myCustomService;

    public String getGreeting(String name) {
        return myCustomService.sayHello(name);
    }
}