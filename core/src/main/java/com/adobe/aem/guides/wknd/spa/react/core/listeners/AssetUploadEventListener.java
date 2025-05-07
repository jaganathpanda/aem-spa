package com.adobe.aem.guides.wknd.spa.react.core.listeners;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;
import javax.jcr.observation.ObservationManager;

import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true)
public class AssetUploadEventListener implements EventListener {

    private static final Logger LOG = LoggerFactory.getLogger(AssetUploadEventListener.class);

    @Reference
    private SlingRepository repository;

    private Session session;
    private ObservationManager observationManager;

    @Activate
    protected void activate() {
        try {
            session = repository.loginService("datawrite", null); // Use a system user mapping
            observationManager = session.getWorkspace().getObservationManager();

            observationManager.addEventListener(
                this,
                Event.NODE_ADDED,
                "/content/dam/my-assets",
                true,
                null,
                null,
                false
            );

            LOG.info("AssetUploadEventListener registered successfully.");
        } catch (RepositoryException e) {
            LOG.error("Failed to register JCR EventListener", e);
        }
    }

    @Deactivate
    protected void deactivate() {
        try {
            if (observationManager != null) {
                observationManager.removeEventListener(this);
            }
            if (session != null) {
                session.logout();
            }
        } catch (RepositoryException e) {
            LOG.error("Error during EventListener deactivation", e);
        }
    }

    @Override
    public void onEvent(EventIterator events) {
        while (events.hasNext()) {
            Event event = events.nextEvent();
            try {
                String path = event.getPath();
                String user = event.getUserID();
                LOG.info("Asset uploaded at: {} by user: {}", path, user);
            } catch (RepositoryException e) {
                LOG.error("Error processing asset upload event", e);
            }
        }
    }
}

