package com.adobe.aem.guides.wknd.spa.react.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Component(service = Servlet.class, property = {
        "sling.servlet.resourceTypes=wknd-spa-react/components/spaCarousel",
        "sling.servlet.methods=GET",
        "sling.servlet.extensions=json"
})
public class PageInfoServlet extends SlingAllMethodsServlet {
    private static final Logger log = LoggerFactory.getLogger(PageInfoServlet.class);

    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        try {
            String currentPath = request.getResource().getPath();
            ResourceResolver resolver = request.getResourceResolver();
            PageManager pageManager = resolver.adaptTo(PageManager.class);
            Page page = pageManager.getPage(currentPath);
            log.info(page.getTitle());
            response.setContentType("application/json");
            response.getWriter().println(page.getTitle());

        } catch (IOException ioException) {
            log.error("exception");
        }
    }

}
