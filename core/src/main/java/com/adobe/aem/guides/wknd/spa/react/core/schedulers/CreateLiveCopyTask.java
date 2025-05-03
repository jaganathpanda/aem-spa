package com.adobe.aem.guides.wknd.spa.react.core.schedulers;

import com.day.cq.wcm.msm.api.LiveRelationshipManager;
import com.day.cq.wcm.msm.api.LiveCopy;
import com.day.cq.wcm.msm.api.RolloutManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.LoginException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

@Component(service = Runnable.class, immediate = true)
public class CreateLiveCopyTask implements Runnable {

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

    // @Reference
    // private LiveCopyManager liveCopyManager;

    // @Override
    // public void run() {
    //     String sourcePath = "/content/wknd-spa-react/us/en";
    //     String liveCopyPath = "/content/wknd-spa-react/us/es";
    //     String title = "Spanish Live Copy";
    //     String rolloutConfigPath = "/libs/msm/wcm/rolloutconfigs/default";

    //     try (ResourceResolver resolver = getAdminResourceResolver()) {
    //         Resource source = resolver.getResource(sourcePath);
    //         Resource targetParent = resolver.getResource("/content/wknd-spa-react/us");

    //         if (source != null && targetParent != null) {
    //             liveCopyManager.createLiveCopy(
    //                 source,                // source
    //                 targetParent,         // target parent
    //                 "es",                 // new name
    //                 title,                // title
    //                 new String[]{rolloutConfigPath},  // rollout config
    //                 null                  // template (optional)
    //             );
    //             resolver.commit();
    //             System.out.println("✅ Live Copy created successfully.");
    //         }
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    // }

    // private ResourceResolver getAdminResourceResolver() throws LoginException {
    //     // Use service user mapping in production
    //     return org.apache.sling.api.resource.ResourceResolverFactory
    //         .getServiceResourceResolver(
    //             Collections.singletonMap(
    //                 ResourceResolverFactory.SUBSERVICE, "your-service-user"
    //             )
    //         );
    // }
}
