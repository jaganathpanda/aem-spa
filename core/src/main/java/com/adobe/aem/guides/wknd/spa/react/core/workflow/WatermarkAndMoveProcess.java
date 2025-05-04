package com.adobe.aem.guides.wknd.spa.react.core.workflow;

import com.day.cq.dam.api.Asset;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.dam.api.AssetManager;
import org.apache.commons.io.FilenameUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;

@Component(service = WorkflowProcess.class, property = {
    "process.label=Watermark and Move Image"
})
public class WatermarkAndMoveProcess implements WorkflowProcess {

    @Override
    public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) {
        try {
            String payloadPath = item.getWorkflowData().getPayload().toString();
            ResourceResolver resolver = session.adaptTo(ResourceResolver.class);
            Resource resource = resolver.getResource(payloadPath);
            if (resource == null) {
                throw new Exception("Payload resource not found: " + payloadPath);
            }

            Asset asset = resource.adaptTo(Asset.class);
            if (asset == null) {
                throw new Exception("Asset adaptation failed for: " + payloadPath);
            }

            InputStream is = asset.getOriginal().getStream();
            BufferedImage image = ImageIO.read(is);

            // Get metadata values
            MetaDataMap metaDataMap = item.getWorkflowData().getMetaDataMap();
            String approvalStatus = metaDataMap.get("approvalStatus", String.class);
            //String watermarkText = args.get("watermarkText", String.class);
            String targetPath = args.get("targetPath", String.class);
            String watermarkText = item.getNode().getMetaDataMap().get("watermarkText", String.class);
            String ext = FilenameUtils.getExtension(asset.getName());
            AssetManager assetManager = resolver.adaptTo(AssetManager.class);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String newPath;

            if ("approved".equalsIgnoreCase(approvalStatus)) {
                // Apply watermark
                Graphics2D g2d = image.createGraphics();
                g2d.setFont(new Font("Arial", Font.BOLD, 40));
                g2d.setColor(new Color(255, 0, 0, 128)); // semi-transparent red
                g2d.drawString(watermarkText, 20, image.getHeight() - 40);
                g2d.dispose();

                ImageIO.write(image, ext, baos);
                InputStream watermarkedStream = new ByteArrayInputStream(baos.toByteArray());

                newPath = targetPath + "/" + asset.getName();
                assetManager.createAsset(newPath, watermarkedStream, asset.getMimeType(), true);

                System.out.println("✅ Approved: Asset moved with watermark to " + newPath);
            } else {
                // Move to rejected folder without watermark
                newPath = "/content/dam/wknd-spa-react/rejected/" + asset.getName();
                ImageIO.write(image, ext, baos);
                InputStream originalStream = new ByteArrayInputStream(baos.toByteArray());

                assetManager.createAsset(newPath, originalStream, asset.getMimeType(), true);

                System.out.println("❌ Rejected: Asset moved to rejected folder at " + newPath);
            }

            // Delete original from source
            resource.adaptTo(Node.class).remove();
            resolver.commit();
            session.terminateWorkflow(item.getWorkflow());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
