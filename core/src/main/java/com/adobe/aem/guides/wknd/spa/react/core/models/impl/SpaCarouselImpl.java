package com.adobe.aem.guides.wknd.spa.react.core.models.impl;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.guides.wknd.spa.react.core.models.SliderValue;
import com.adobe.aem.guides.wknd.spa.react.core.models.SpaCarousel;
import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.annotation.JsonProperty;

@Model(
    adaptables = Resource.class,
    adapters = { SpaCarousel.class },
    resourceType = SpaCarouselImpl.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION
)
public class SpaCarouselImpl implements SpaCarousel {

    public static final String RESOURCE_TYPE = "wknd-spa-react/components/spaCarousel";

    @Inject
    @Via("resource")
    @JsonProperty("slides")
    private List<Resource> slides; 

    @Override
    public List<Resource> getSlides() {
        return slides;
    }

    @Override
    public String getExportedType() {
        return SpaCarouselImpl.RESOURCE_TYPE;
    }
}
