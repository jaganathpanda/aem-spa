package com.adobe.aem.guides.wknd.spa.react.core.event;

import java.util.List;

import org.apache.sling.api.resource.observation.ResourceChange;
import org.apache.sling.api.resource.observation.ResourceChangeListener;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = ResourceChangeListener.class, property = {
        ResourceChangeListener.PATHS + "=/content",
        ResourceChangeListener.CHANGES + "=ADDED"
})
public class PageCreationListenerUsingResource implements ResourceChangeListener {
    private static final Logger log = LoggerFactory.getLogger(PageCreationListenerUsingResource.class);

    @Override
    public void onChange(List<ResourceChange> resourceChange) {
        for (ResourceChange change : resourceChange) {
            log.info(change.getPath());
        }

    }

}
