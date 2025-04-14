package com.adobe.aem.guides.wknd.spa.react.core.models.impl;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.aem.guides.wknd.spa.react.core.models.SliderValue;
import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;

@Model(
    adaptables = SlingHttpServletRequest.class,
    adapters = ComponentExporter.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
    resourceType = SpaCarouselImpl.RESOURCE_TYPE)
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION
)
public class SpaCarouselImpl implements ComponentExporter {

    public static final String RESOURCE_TYPE = "wknd-spa-react/components/spaCarousel";
    @ChildResource(name = "slides")
    private Collection<SliderValue> slides; 

    @ValueMapValue
    private String text; 

@PostConstruct
  protected void init() {
    slides = CollectionUtils.emptyIfNull(this.slides);
  }

    public Collection<SliderValue> getSlides() {
        return slides;
    }


    public String getText() {
        return StringUtils.isNotBlank(text) ? text : "(Default)";
    }


    @Override
    public String getExportedType() {
        return SpaCarouselImpl.RESOURCE_TYPE;
    }
}
