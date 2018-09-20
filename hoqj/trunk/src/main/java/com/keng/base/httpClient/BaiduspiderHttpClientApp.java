package com.keng.base.httpClient;
import org.apache.http.client.methods.HttpUriRequest;

public class BaiduspiderHttpClientApp extends HttpClientApp {
    public static String HTTP_USER_AGENT     = "Mozilla/5.0 (compatible; Baiduspider/2.0; +http://www.baidu.com/search/spider.html)";
    public static String HTTP_USER_AGENT_KEY = "User-Agent";

    public BaiduspiderHttpClientApp() {
    }

    @Override
    protected HttpUriRequest initRequestHeader(HttpUriRequest request) {
        request.setHeader(HTTP_USER_AGENT_KEY, HTTP_USER_AGENT);
        request.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        request.setHeader("Accept-Encoding", "gzip,deflate,sdch");
        request.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.6,en;q=0.4,zh-TW;q=0.2");
        return super.initRequestHeader(request);
    }
}
