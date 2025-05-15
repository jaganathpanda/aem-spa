package com.adobe.aem.guides.wknd.spa.react.core.jobs;

import com.adobe.aem.guides.wknd.spa.react.core.utility.GetResourceResolver;
import com.adobe.granite.offloading.api.OffloadingJobProperties;
import com.day.cq.dam.api.Asset;
import org.apache.sling.event.jobs.Job;
import org.apache.sling.event.jobs.consumer.JobConsumer;

import java.util.Collections;
import java.util.Map;

import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = JobConsumer.class, immediate = true, property = {
        JobConsumer.PROPERTY_TOPICS + "=com/wknd/aem/asset/upload"
})
public class AssetMetadataConsumer implements JobConsumer {
    private static final Logger log = LoggerFactory.getLogger(AssetMetadataConsumer.class);

    @Reference
    GetResourceResolver resolver;

    @Override
    public JobResult process(Job job) {
        // Retrieve the asset path from the job payload
        String assetPath = (String) job.getProperty(OffloadingJobProperties.INPUT_PAYLOAD.propertyName());
        if (assetPath == null) {
            log.error("Job payload missing asset path");
            return JobResult.FAILED;
        }
        ResourceResolver resourceResolver = resolver.getResolver();
        Resource assetRes = resourceResolver.getResource(assetPath);
        Asset asset = assetRes != null ? assetRes.adaptTo(Asset.class) : null;
        if (asset != null) {
            // Example: log some metadata values
            String title = asset.getMetadataValue("dc:title");
            String description = asset.getMetadataValue("dc:description");
            log.info("Processed asset: {}  Title='{}', Description='{}'",
                    assetPath, title, description);
        } else {
            log.warn("Asset not found at path: {}", assetPath);
        }
        return JobResult.OK;
    }
}
