package com.adobe.aem.guides.wknd.spa.react.core.workflow;

import com.day.cq.dam.api.Asset;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.dam.api.AssetManager;
import org.apache.commons.io.FilenameUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;

import javax.jcr.Node;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

@Component(
    service = WorkflowProcess.class,
    property = {"process.label=Watermark and Move Image"}
)
public class WatermarkAndMoveProcess implements WorkflowProcess {

    @Override
    public void execute(WorkItem item, WorkflowSession session, MetaDataMap args) {
        try {
            String payloadPath = item.getWorkflowData().getPayload().toString();
            ResourceResolver resolver = session.adaptTo(ResourceResolver.class);
            Asset asset = resolver.getResource(payloadPath).adaptTo(Asset.class);

            String brand = asset.getMimeType(); // Ensure brand is set
            InputStream is = asset.getOriginal().getStream();
            BufferedImage image = ImageIO.read(is);

            // Load watermark based on brand
            BufferedImage watermark = ImageIO.read(resolver.getResource("/content/dam/brand-watermarks/" + brand + ".png").adaptTo(InputStream.class));

            // Apply watermark
            Graphics2D g2d = image.createGraphics();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2d.drawImage(watermark, 10, 10, null);
            g2d.dispose();

            // Save watermarked image to target location
            String newPath = "/content/dam/images/approved/" + brand + "/" + asset.getName();
            AssetManager assetManager = resolver.adaptTo(AssetManager.class);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, FilenameUtils.getExtension(asset.getName()), baos);
            InputStream watermarkedStream = new ByteArrayInputStream(baos.toByteArray());
            assetManager.createAsset(newPath, watermarkedStream, asset.getMimeType(), true);

            // Delete original from review folder
            resolver.getResource(payloadPath).adaptTo(Node.class).remove();
            resolver.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
