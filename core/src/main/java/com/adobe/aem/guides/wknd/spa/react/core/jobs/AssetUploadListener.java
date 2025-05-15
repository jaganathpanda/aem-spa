package com.adobe.aem.guides.wknd.spa.react.core.jobs;

import com.day.cq.dam.api.DamEvent;
import org.apache.sling.event.jobs.JobManager;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.event.jobs.Job;
import org.osgi.service.component.annotations.*;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.offloading.api.OffloadingJobProperties;

@Component(service = EventHandler.class, immediate = true, property = {
        EventConstants.EVENT_TOPIC + "=" + DamEvent.EVENT_TOPIC
})
public class AssetUploadListener implements EventHandler {
    private static final Logger log = LoggerFactory.getLogger(AssetUploadListener.class);

    @Reference
    private JobManager jobManager;

    @Override
    public void handleEvent(Event event) {
        DamEvent damEvent = DamEvent.fromEvent(event);
        if (damEvent != null && DamEvent.Type.ASSET_CREATED.equals(damEvent.getType())) {
            String assetPath = damEvent.getAssetPath();
            // Prepare job properties (using Offloading payload for asset path)
            Map<String, Object> props = new HashMap<>();
            props.put(OffloadingJobProperties.INPUT_PAYLOAD.propertyName(), assetPath);
            // Enqueue a job on topic "com/mycompany/aem/asset/upload"
            Job job = jobManager.addJob("com/wknd/aem/asset/upload", props);
            log.info("Posted job [{}] for uploaded asset: {}", job != null ? job.getId() : null, assetPath);
        }
    }
}
