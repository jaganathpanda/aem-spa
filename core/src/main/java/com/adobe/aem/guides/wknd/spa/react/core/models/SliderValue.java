package com.adobe.aem.guides.wknd.spa.react.core.models;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;


@Model(adaptables = Resource.class , defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SliderValue {
 @Inject
 private String image;

 @Inject
 private String caption;

 public String getImage() {
    return image;
 }

 public String getCaption() {
    return caption;
 }
 


}
