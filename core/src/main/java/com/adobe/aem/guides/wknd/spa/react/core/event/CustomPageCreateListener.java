package com.adobe.aem.guides.wknd.spa.react.core.event;

import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.spa.react.core.utility.GetResourceResolver;
import com.day.cq.workflow.model.WorkflowModel;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.WorkflowService;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;

@Component(service = EventHandler.class, property = {
        EventConstants.EVENT_TOPIC + "=org/apache/sling/api/resource/Resource/ADDED",
        EventConstants.EVENT_FILTER + "=(path=/content/wknd-spa-react/us/en/.*)"
})
public class CustomPageCreateListener implements EventHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomPageCreateListener.class);

    @Reference
    WorkflowService workflowService;

    @Reference
    GetResourceResolver resolver;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void handleEvent(Event event) {
        try {
            log.info("Custome workflow triggered: {}", event.getTopic());
            String path = (String) event.getProperty("path");
            log.info("Page created at: {}", path);

            ResourceResolver resourceResolver = resolver.getResolver();
            Session session = resourceResolver.adaptTo(Session.class);
            WorkflowSession workflowSession = workflowService.getWorkflowSession(session);
            WorkflowModel workflowModel = workflowSession.getModel("/var/workflow/models/wknd0");
            WorkflowData workflowDat = workflowSession.newWorkflowData("JCR_PATH", path);
            workflowSession.startWorkflow(workflowModel, workflowDat);
            log.info("Workflow started for path: {}", path);
        } catch (com.day.cq.workflow.WorkflowException e) {
            e.printStackTrace();
        }
    }

}