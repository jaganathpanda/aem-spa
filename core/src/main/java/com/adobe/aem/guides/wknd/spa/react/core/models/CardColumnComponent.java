package com.adobe.aem.guides.wknd.spa.react.core.models;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

import javax.inject.Inject;

@Model(
    adaptables = SlingHttpServletRequest.class,
    adapters = ComponentExporter.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
    resourceType = CardColumnComponent.RESOURCE_TYPE)
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION
)
public class CardColumnComponent implements ComponentExporter {
    public static final String RESOURCE_TYPE = "wknd-spa-react/components/cardcolumns";

    @ChildResource(name = "cards")
    private List<Card> cards;

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String getExportedType() {
        return CardColumnComponent.RESOURCE_TYPE;
    }

    @Model(adaptables = Resource.class)
    public static class Card {
        @ValueMapValue
        private String title;

        @ValueMapValue
        private String description;

        @Inject
         private String image;

        public String getTitle() {
            return title;
        }

        public String getImage() {
            return image;
        }

        public String getDescription() {
            return description;
        }
    }
}