package com.adobe.aem.guides.wknd.spa.react.core.utility;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = GetResourceResolver.class, immediate = true)
public class GetResourceResolver {

    private static final Logger log = LoggerFactory.getLogger(GetResourceResolver.class);

    private static final String SUBSERVICE_NAME = "resource-resolver-user";

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    public ResourceResolver getResolver() {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put(ResourceResolverFactory.SUBSERVICE, SUBSERVICE_NAME);
            return resourceResolverFactory.getServiceResourceResolver(param);
        } catch (LoginException e) {
            log.error("Error getting service resource resolver", e);
        }
        return null;
    }
}
