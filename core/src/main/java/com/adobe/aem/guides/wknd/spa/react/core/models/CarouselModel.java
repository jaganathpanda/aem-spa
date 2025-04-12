package com.adobe.aem.guides.wknd.spa.react.core.models;

import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;

import com.adobe.cq.export.json.ComponentExporter;
import com.fasterxml.jackson.annotation.JsonProperty;

@Model(adaptables = Resource.class, adapters = ComponentExporter.class, resourceType = CarouselModel.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = "jackson", extensions = "model.json")
public class CarouselModel implements ComponentExporter {
    static final String RESOURCE_TYPE = "wknd-spa-react/components/spaCarousel";

    @Inject
    @JsonProperty("items")
    private List<Resource> items;

    public List<Resource> getItems() {
        return items;
    }

    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }

}
