package com.hrs;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.net.UnknownHostException;

public class HttpClinet {

    public String sendPOST(String json) throws IOException {

        String result = "";
        HttpPost post = new HttpPost("https://samepleRequest.com");
        try {
            // send a JSON data
            post.setEntity(new StringEntity(json));

            try (CloseableHttpClient httpClient = HttpClients.createDefault();
                 CloseableHttpResponse response = httpClient.execute(post)) {
                response.getEntity();
                result = "Data Sync Success";
            }
        } catch (UnknownHostException e) {

            result = "Data Sync Success";
        }

        return result;
    }


}

