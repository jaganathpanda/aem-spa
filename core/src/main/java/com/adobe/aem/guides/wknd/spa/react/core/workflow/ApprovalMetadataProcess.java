package com.adobe.aem.guides.wknd.spa.react.core.workflow;

import java.util.Calendar;

import javax.jcr.Node;

import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.aem.guides.wknd.spa.react.core.utility.GetResourceResolver;
import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;


@Component(service = WorkflowProcess.class,
    property = {
        "process.label=Add Approval Metadata"
    })
public class ApprovalMetadataProcess implements WorkflowProcess {

    @Reference
    private GetResourceResolver getResourceResolver;

    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap) {
        try {
            String payload = workItem.getWorkflowData().getPayloadType();
            ResourceResolver resolver = getResourceResolver.getResolver();
            PageManager pm = resolver.adaptTo(PageManager.class);
            Page page = pm.getPage(payload);
            String reviewerName=workItem.getMetaDataMap().get("user",String.class);
            if (page != null) {
                Node node = page.adaptTo(Node.class);
                if (node != null) {
                    node.setProperty("approvedBy", reviewerName);
                    node.setProperty("approvalDate", Calendar.getInstance());
                    resolver.commit();

                }

            }
        } catch (Exception e) {

        }

    }

}
