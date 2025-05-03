package com.adobe.aem.guides.wknd.spa.react.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

@Component(service = Servlet.class, property = {
        "sling.servlet.paths=/bin/page-title", // <-- Corrected: 'paths' instead of 'path'
        "sling.servlet.methods=GET"             // <-- Corrected: 'methods' instead of 'method'
})
public class PageTitleServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getParameter("path");

        if (path == null || path.isEmpty()) {
            response.getWriter().println("Missing path parameter.");
            return; // <-- Important: prevent further execution
        }

        ResourceResolver resolver = request.getResourceResolver();
        PageManager pageManager = resolver.adaptTo(PageManager.class);

        if (pageManager != null) {
            Page page = pageManager.getPage(path);
            if (page != null) {
                String pageTitle = page.getTitle();
                response.getWriter().println(pageTitle != null ? pageTitle : "No title set");
            } else {
                response.getWriter().println("Page not found");
            }
        } else {
            response.getWriter().println("PageManager could not be adapted");
        }
    }
}
