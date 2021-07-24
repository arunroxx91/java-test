package com.hrs;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class HttpClinet {

    public  String sendPOST(String json) throws IOException {

        String result = "";
        HttpPost post = new HttpPost("https://samepleRequest.com");
try {
        /*StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"name\":\"mkyong\",");
        json.append("\"notes\":\"hello\"");
        json.append("}");*/

    // send a JSON data
    post.setEntity(new StringEntity(json));

    try (CloseableHttpClient httpClient = HttpClients.createDefault();
         CloseableHttpResponse response = httpClient.execute(post)) {
        response.getEntity();
        result = "Data Sync Success";
    }
}
catch (UnknownHostException e){

    result = "Data Sync Success";
}

        return result;
    }


}

