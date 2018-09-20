package com.keng.base.httpClient;
import org.apache.http.client.methods.HttpUriRequest;

public class ChromeHttpClientApp extends HttpClientApp {
    public static String HTTP_USER_AGENT     = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36";
    public static String HTTP_USER_AGENT_KEY = "User-Agent";

    public ChromeHttpClientApp() {
    }

    @Override
    protected HttpUriRequest initRequestHeader(HttpUriRequest request) {
        request.setHeader(HTTP_USER_AGENT_KEY, HTTP_USER_AGENT);
        request.setHeader("Accept", "text/html,application/json,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("Accept-Encoding", "gzip");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4,zh-TW;q=0.2");
        return super.initRequestHeader(request);
    }
}
