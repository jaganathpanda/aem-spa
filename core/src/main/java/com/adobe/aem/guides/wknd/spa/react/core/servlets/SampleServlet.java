package com.adobe.aem.guides.wknd.spa.react.core.servlets;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import javax.servlet.Servlet;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.apache.sling.api.servlets.ServletResolverConstants.SLING_SERVLET_PATHS;

@Component(
    service = Servlet.class,
    property = {
        SLING_SERVLET_PATHS + "=/bin/blogposts"
    }
)
public class SampleServlet extends SlingAllMethodsServlet {

    private static final String GRAPHQL_ENDPOINT = "https://author.aem-instance/graphql/your-site/blog-endpoint.json";

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String graphqlQuery = "{ blogPostList { items { title summary published } } }";

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(GRAPHQL_ENDPOINT);
            post.setHeader("Content-Type", "application/json");

            JSONObject payload = new JSONObject();
            payload.put("query", graphqlQuery);
            post.setEntity(new StringEntity(payload.toString(), StandardCharsets.UTF_8));

            HttpResponse httpResponse = httpClient.execute(post);
            InputStream responseStream = httpResponse.getEntity().getContent();
            String result = IOUtils.toString(responseStream, StandardCharsets.UTF_8);

            response.getWriter().write(result);

        } catch (Exception e) {
            response.setStatus(500);
            try {
                response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
            } catch (Exception ex) {
                // ignore
            }
        }
    }
}
