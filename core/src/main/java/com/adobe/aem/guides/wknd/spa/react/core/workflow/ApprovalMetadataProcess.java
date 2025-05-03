package com.adobe.aem.guides.wknd.spa.react.core.workflow;

import java.util.Calendar;
import java.util.List;

import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.spa.react.core.utility.GetResourceResolver;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.HistoryItem;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

@Component(service = WorkflowProcess.class, property = {
        "process.label=Add Approval Metadata"
})
public class ApprovalMetadataProcess implements WorkflowProcess {

    private static final Logger log = LoggerFactory.getLogger(ApprovalMetadataProcess.class);

    @Reference
    private GetResourceResolver getResourceResolver;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) {
        ResourceResolver resolver = null;
        try {
            String payload = workItem.getWorkflowData().getPayload().toString(); // typically /content/.../jcr:content
            resolver = getResourceResolver.getResolver();
            String processArg = metaDataMap.get("PROCESS_ARGS", String.class);
            Resource contentResource = resolver.getResource(payload);
            if (contentResource != null) {
                Node contentNode = contentResource.adaptTo(Node.class);
                if (contentNode != null) {
                    String reviewerName = workItem.getWorkflow().getInitiator();
                    String reviewerComment = workItem.getMetaDataMap().get("comment", String.class);
                    contentNode.setProperty("approvedBy", reviewerName);
                    contentNode.setProperty("approvalDate", Calendar.getInstance());
                    resolver.commit();
                    List<HistoryItem> history = workflowSession.getHistory(workItem.getWorkflow());
                    for (HistoryItem item : history) {
                        String user = item.getUserId();
                        String comment = item.getComment();
                        String stepName = item.getWorkItem().getNode().getTitle();
                        log.info("Step: {}, User: {}, Comment: {}", stepName, user, comment,processArg);
                    }
                    workflowSession.terminateWorkflow(workItem.getWorkflow());
                    log.info("Approval metadata set by: {}", reviewerComment);
                } else {
                    log.warn("Could not adapt resource to node: {}", payload);
                }
            } else {
                log.warn("Resource not found at payload path: {}", payload);
            }
        } catch (Exception e) {
            log.error("Error setting approval metadata", e);
        } finally {
            if (resolver != null && resolver.isLive()) {
                resolver.close();
            }
        }
    }
}
