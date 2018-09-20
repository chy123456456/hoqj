package com.keng.base.httpClient;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

public class HttpClientResponse {
    private HttpResponse response;

    public HttpClientResponse() {
    }

    public HttpClientResponse(HttpResponse response) {
        this.setResponse(response);
    }

    public HttpResponse getResponse() {
        return response;
    }

    public int getStatusCode() {
        return response.getStatusLine().getStatusCode();
    }

    public StatusLine getStatusLine() {
        return response.getStatusLine();
    }

    public Header[] getHeaders(String s) {
        return response.getHeaders(s);
    }

    public Header getHeader(String s) {
        Header[] headers = response.getHeaders(s);
        if (headers != null && headers.length > 0) {
            return headers[0];
        }
        return null;
    }

    public String getHeaderValue(String s) {
        Header header = response.getFirstHeader(s);
        if (header != null) {
            return header.getValue();
        }
        return null;
    }

    public String getTextContent() throws Exception {
        HttpEntity entity = response.getEntity();
        String text = "";
        String contentEncoding = this.getHeaderValue("Content-Encoding");
        if ("gzip".equals(contentEncoding)) {
            GZIPInputStream gzipIn = new GZIPInputStream(entity.getContent());
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(gzipIn, "UTF-8"));
            String temp = null;
            while ((temp = buffReader.readLine()) != null) {
                text += temp;
            }
            buffReader.close();
        } else {
            text = EntityUtils.toString(entity, Charset.forName("UTF-8"));
        }
        return text;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    public void consumeContent() throws Exception {
        HttpEntity entity = response.getEntity();
        if (entity != null) EntityUtils.consume(entity);
    }
}
