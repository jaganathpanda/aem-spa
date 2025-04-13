package com.adobe.aem.guides.wknd.spa.react.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;

import com.adobe.cq.export.json.ComponentExporter;

public interface SpaCarousel extends ComponentExporter {
    List<Resource> getItems();
    String getCarouselTitle();
}
