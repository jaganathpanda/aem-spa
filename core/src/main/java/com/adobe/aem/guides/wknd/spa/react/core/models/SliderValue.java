package com.adobe.aem.guides.wknd.spa.react.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;


@Model(adaptables = Resource.class , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SliderValue {
@ValueMapValue(name = "image")
 private String image;

 @ValueMapValue(name = "caption")
 private String caption;

 public String getImage() {
    return image;
 }

 public String getCaption() {
    return caption;
 }
 


}
