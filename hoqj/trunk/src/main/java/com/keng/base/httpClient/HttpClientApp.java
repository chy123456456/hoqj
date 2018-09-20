package com.keng.base.httpClient;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public abstract class HttpClientApp {
    public static final String REQUEST_METHOD_GET  = HttpGet.METHOD_NAME;
    public static final String REQUEST_METHOD_POST = HttpPost.METHOD_NAME;
    protected static Log       log                 = null;

    /**
     * 启动一个客户端实例，并将请求伪装成搜索引擎
     * @return
     */
    public static HttpClientApp newBotInstance() {
        return new BaiduspiderHttpClientApp();
    }

    /**
     * 启动一个客户端实例
     * @return
     */
    public static HttpClientApp newInstance() {
        return new ChromeHttpClientApp();
    }
    private CloseableHttpClient httpClient;
    private CookieStore         cookieStore;
    private HttpClientContext   localContext;

    public HttpClientApp() {
        if (log == null) {
            log = LogFactory.getLog(this.getClass());
        }
        init();
    }

    public HttpClientApp addProxy(String host, int port) {
        HttpHost proxy = new HttpHost(host, port);
        this.httpClient = createHttpClient(proxy);
        return this;
    }

    public HttpClientApp addProxy(HttpHost proxy) {
        this.httpClient = createHttpClient(proxy);
        return this;
    }

    private CloseableHttpClient createHttpClient(HttpHost proxy) {
        SSLContext sslContext = null;
        X509TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[] { tm }, null);
        } catch (Exception e) {
        }
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        // SSLContext sslContext = SSLContexts.createSystemDefault();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.STRICT_HOSTNAME_VERIFIER);
        Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory> create().register("http", plainsf).register("https", sslsf)
                .build();
        HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000).setConnectionRequestTimeout(30 * 1000)
                .setSocketTimeout(60 * 1000).build();
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setUserAgent(ChromeHttpClientApp.HTTP_USER_AGENT)
                .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build()).disableRedirectHandling()
                .setDefaultRequestConfig(requestConfig).setConnectionManager(cm);
        if (proxy != null) {
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
            httpClientBuilder = httpClientBuilder.setRoutePlanner(routePlanner);
        }
        return httpClientBuilder.build();
    }

    public HttpUriRequest createRequest(String uri, String method) throws Exception {
        HttpUriRequest request = null;
        if (REQUEST_METHOD_GET.equals(method)) {
            request = new HttpGet(uri);
        } else if (REQUEST_METHOD_POST.equals(method)) {
            request = new HttpPost(uri);
        } else {
            throw new Exception("wrong method!");
        }
        return request;
    }

    public HttpClientResponse executeAction(HttpUriRequest request) throws Exception {
        HttpClientResponse clientResponse = new HttpClientResponse();
        clientResponse = executeAction(request, clientResponse, 0);
        return clientResponse;
    }

    private HttpClientResponse executeAction(HttpUriRequest request, HttpClientResponse clientResponse, int redirectNum) throws Exception {
        String url = null;
        request = this.initRequestHeader(request);
        log.debug("发送Http请求：" + request.getRequestLine());
        clientResponse.setResponse(httpClient.execute(request, localContext));
        log.debug("收到Http返回：" + clientResponse.getStatusLine());
        int statusCode = clientResponse.getStatusCode();
        switch (statusCode) {
        case 301:
        case 302:
            url = clientResponse.getHeaderValue("Location");
            if (redirectNum > 10) {
                throw new RuntimeException("该页面包含重定向循环： " + url);
            }
            log.debug("重定向至" + url + "，本次是连续第" + (redirectNum + 1) + "次重定向。");
            if (url.equals(request.getURI().toString())) {
                redirectNum++;
            } else {
                redirectNum = 1;
            }
            request = this.createRequest(url, HttpClientApp.REQUEST_METHOD_GET);
            clientResponse.consumeContent();
            clientResponse = executeAction(request, clientResponse, redirectNum);
            break;
        default:
        }
        return clientResponse;
    }

    public HttpClientResponse executeAjax(HttpUriRequest request) throws Exception {
        request.setHeader("X-Requested-With", "XMLHttpRequest");
        return executeAction(request);
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public HttpClientContext getLocalContext() {
        return localContext;
    }

    /**
     * 初始化
     */
    protected void init() {
        httpClient = createHttpClient(null);
        localContext = HttpClientContext.create();
        cookieStore = new BasicCookieStore();
        localContext.setCookieStore(cookieStore);
    }

    protected HttpUriRequest initRequestHeader(HttpUriRequest request) {
        return request;
    }
}
