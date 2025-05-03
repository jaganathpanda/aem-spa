package com.adobe.aem.guides.wknd.spa.react.core.servlets;

import javax.servlet.Servlet;

import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_PATHS;

@Component(
    service = Servlet.class,
    property = {
          SLING_SERVLET_PATHS + "=/bin/internal-blogposts"
    }
)
public class InternalGraphQLServlet extends SlingAllMethodsServlet{
   // @Reference
   // private GraphQLClient
    
}
