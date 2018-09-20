package com.keng.base.common;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.keng.base.utils.JacksonBuilder;
import com.keng.base.utils.MD5Encrypt;

public class HttpUtils {
    private static HttpUtils              instance;
    private static Map<String, HttpUtils> instances;
    private static List<Header>           headers;
    private static int                    connectTimeout        = 3000;
    private static int                    connectRequestTimeout = 6000;
    private HttpHost                      proxy;
    private RequestConfig                 requestConfig, proxyConfig;
    private BasicCookieStore              cookieStore;
    private CloseableHttpClient           httpclient;
    private SSLConnectionSocketFactory    sslSocketFactory;

    private HttpUtils() {
        cookieStore = new BasicCookieStore();
        sslSocketFactory = this.getSslSocketFactory();
        requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout).setConnectionRequestTimeout(connectRequestTimeout).build();
        httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).setDefaultRequestConfig(requestConfig)
                .setSSLSocketFactory(sslSocketFactory).build();
        this.defaultHeader();
        /*
         * if (!StringUtils.isBlank(Constants.PROXY_HOST) && Constants.PROXY_PORT != null) {
         * this.setProxy(Constants.PROXY_HOST, Constants.PROXY_PORT, Constants.PROXY_TYPE); }
         */
    }

    public static HttpUtils getInstance() {
        synchronized (HttpUtils.class) {
            if (headers == null) {
                headers = new ArrayList<Header>();
            }
            if (instance == null) {
                instance = new HttpUtils();
            }
        }
        return instance;
    }

    public static HttpUtils getInstance(String client) {
        synchronized (HttpUtils.class) {
            if (headers == null) {
                headers = new ArrayList<Header>();
            }
            if (instances == null) {
                instances = new HashMap<String, HttpUtils>();
            }
            if (!instances.containsKey(client)) {
                instances.put(client, new HttpUtils());
            }
        }
        return instances.get(client);
    }

    public List<Cookie> getCookies() {
        return cookieStore.getCookies();
    }

    public HttpHost getProxy() {
        return proxy;
    }

    /**
     * 添加请求Heander
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        headers.add(new BasicHeader(name, value));
    }

    /**
     * 设置默认的请求Header
     */
    private void defaultHeader() {
        this.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        this.addHeader("Accept-Encoding", "gzip,deflate,sdch");
        this.addHeader("Accept-Language", "zh-CN,zh;q=0.8");
        this.addHeader("Connection", "keep-alive");
        this.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
    }

    /**
     * HTTPS访问（允许信任自签证书，即信任所有证书）
     * @return
     * @date 2014-5-9 下午5:46:58
     */
    private SSLConnectionSocketFactory getSslSocketFactory() {
        SSLConnectionSocketFactory sslSocketFactory = null;
        try {
            // 信任所有SSL
            TrustStrategy trustStore = new TrustSelfSignedStrategy();
            SSLContext sslcontext = new SSLContextBuilder().loadTrustMaterial(null, trustStore).build();
            sslSocketFactory = new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

    /**
     * 设置代理
     * @param host 代理IP
     * @param port 代理端口
     * @param scheme 代理类型（HTTP,HTTPS）
     * @auther wsyte_000
     * @date 2014-4-12 上午2:18:22
     */
    public void setProxy(String host, int port, String scheme) {
        if ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme)) {
            proxy = new HttpHost(host, port, scheme);
        } else {
            proxy = new HttpHost(host, port);
        }
        proxyConfig = RequestConfig.copy(requestConfig).setProxy(proxy).build();
    }

    /**
     * HTTP GET请求
     * @param url URL
     * @param params 参数
     * @param isProxy 是否使用代理
     * @return HTML内容
     * @throws Exception
     * @auther wsyte_000
     * @date 2014-4-12 上午2:14:35
     */
    public String httpAlipyGet(String url, Map<String, String> params, boolean isProxy) throws Exception {
        return httpAlipyGet(url, headers, params, isProxy);
    }

    /**
     * HTTP GET请求
     * @param url
     * @param headers
     * @param params
     * @param isProxy
     * @return
     * @throws Exception
     * @auther wsyte_000
     * @date 2014-5-9 下午11:46:32
     */
    public String httpAlipyGet(String url, List<Header> headers, Map<String, String> params, boolean isProxy) throws Exception {
        String html = null;
        CloseableHttpResponse response = get(url, headers, params, isProxy);
        if (response != null) {
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    html = EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        }
        return html;
    }

    /**
     * HTTP POST请求
     * @param url URL
     * @param params 参数
     * @param isProxy 是否使用代理
     * @return HTML内容
     * @throws Exception
     * @auther wsyte_000
     * @date 2014-4-12 上午2:15:47
     */
    public String httpPost(String url, Map<String, String> params, boolean isProxy) throws Exception {
        return httpPost(url, headers, params, isProxy);
    }

    /**
     * HTTP GET请求
     * @param url
     * @param headers
     * @param params
     * @param isProxy
     * @return
     * @throws Exception
     * @auther wsyte_000
     * @date 2014-5-9 下午11:46:32
     */
    public String httpGet(String url, List<Header> headers, Map<String, String> params, boolean isProxy) throws Exception {
        String html = null;
        CloseableHttpResponse response = get(url, headers, params, isProxy);
        if (response != null) {
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    html = EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        }
        return html;
    }

    /**
     * HTTP POST请求
     * @param url
     * @param headers
     * @param params
     * @param isProxy
     * @return
     * @throws Exception
     * @auther wsyte_000
     * @date 2014-5-9 下午11:46:51
     */
    public String httpPost(String url, List<Header> headers, Map<String, String> params, boolean isProxy) throws Exception {
        String html = null;
        CloseableHttpResponse response = post(url, headers, params, isProxy);
        if (response != null) {
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    html = EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        }
        return html;
    }

    /**
     * HTTP POST请求(json请求)
     * @param url
     * @param headers
     * @param json
     * @param isProxy
     * @return
     * @throws Exception
     * @auther wsyte_000
     * @date 2014-5-9 下午11:46:51
     */
    public String httpPost(String url, List<Header> headers, String json, boolean isProxy) throws Exception {
        String html = null;
        CloseableHttpResponse response = post(url, headers, json, isProxy);
        if (response != null) {
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    html = EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        }
        return html;
    }

    /**
     * GET 请求
     * @param url
     * @param headers
     * @param params
     * @param isProxy
     * @return
     * @throws Exception
     * @date 2014-6-9 下午1:55:52
     */
    private CloseableHttpResponse get(String url, List<Header> headers, Map<String, String> params, boolean isProxy) throws Exception {
        // 写入请求参数
        String parm = null;
        if (params != null && params.size() > 0) {
            parm = "";
            for (String key : params.keySet()) {
                parm += URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(params.get(key), "UTF-8") + "&";
            }
            parm = parm.substring(0, parm.length() - 1);
        }
        HttpGet request = new HttpGet(url + (parm == null ? "" : "?" + parm));
        if (isProxy && proxyConfig != null) { // 设置代理
            request.setConfig(proxyConfig);
        }
        // 设置请求Header
        for (Header header : headers) {
            request.addHeader(header);
        }
        /*
         * CloseableHttpResponse response = httpclient.execute(request); if (response.getStatusLine().getStatusCode() !=
         * HttpStatus.SC_OK) { request.abort(); return null; } return response;
         */
        return httpclient.execute(request);
    }

    /**
     * POST请求
     * @param url
     * @param headers
     * @param params
     * @param isProxy
     * @return
     * @throws Exception
     * @date 2014-6-9 下午1:57:28
     */
    private CloseableHttpResponse post(String url, List<Header> headers, Map<String, String> params, boolean isProxy) throws Exception {
        HttpPost request = new HttpPost(url);
        if (isProxy && proxyConfig != null) { // 设置代理
            request.setConfig(proxyConfig);
        }
        // 写入请求参数
        List<NameValuePair> nvps = null;
        if (params != null && params.size() > 0) {
            nvps = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                nvps.add(new BasicNameValuePair(key, params.get(key)));
            }
            request.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
        }
        // 设置请求Header
        for (Header header : headers) {
            request.addHeader(header);
        }
        /*
         * CloseableHttpResponse response = httpclient.execute(request); if (response.getStatusLine().getStatusCode() !=
         * HttpStatus.SC_OK) { request.abort(); return null; } return response;
         */
        return httpclient.execute(request);
    }

    /**
     * POST请求(参数为json)
     * @param url
     * @param headers
     * @param json
     * @param isProxy
     * @return
     * @throws Exception
     */
    private CloseableHttpResponse post(String url, List<Header> headers, String json, boolean isProxy) throws Exception {
        HttpPost request = new HttpPost(url);
        if (isProxy && proxyConfig != null) { // 设置代理
            request.setConfig(proxyConfig);
        }
        request.addHeader(HTTP.CONTENT_TYPE, "application/json");
        // 写入请求参数
        StringEntity se = new StringEntity(json, ContentType.APPLICATION_JSON);
        request.setEntity(se);
        // 设置请求Header
        for (Header header : headers) {
            request.addHeader(header);
        }
        return httpclient.execute(request);
    }

    public File downloadFile(String url, String path, boolean isProxy) throws Exception {
        File file = null;
        // 获取名称
        int index = url.lastIndexOf('/');
        String fileName = url.substring(index);
        index = fileName.lastIndexOf('.');
        String suffix = index != -1 ? fileName.substring(index) : "";
        String newName = MD5Encrypt.MD5(url).toLowerCase() + suffix;
        file = new File(path + "/" + newName);
        if (!file.exists()) {
            // 下载文件
            CloseableHttpResponse response = get(url, headers, null, isProxy);
            if (response != null) {
                try {
                    // 通过输入流获取图片数据
                    InputStream inStream = response.getEntity().getContent();
                    // 得到二进制数据
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = inStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, len);
                    }
                    inStream.close();
                    // 写入到磁盘
                    FileOutputStream fops = new FileOutputStream(file);
                    fops.write(outStream.toByteArray());
                    fops.flush();
                    fops.close();
                } finally {
                    response.close();
                }
            }
        }
        return file;
    }

    /**
     * 读取网络图片
     * @param url 地址
     * @param isProxy 是否代理
     * @return
     * @date 2014年8月5日 下午4:17:06
     */
    public BufferedImage readImage(String url, boolean isProxy) {
        BufferedImage image = null;
        CloseableHttpResponse response = null;
        try {
            response = get(url, headers, null, isProxy);
            if (response != null) {
                try {
                    InputStream inputStream = response.getEntity().getContent();
                    image = ImageIO.read(inputStream);
                    inputStream.close();
                } finally {
                    response.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public void close() {
        try {
            httpclient.close();
            instance = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bpoint退款需要 自定义post请求
     * @param url
     * @param param
     * @param headerparam
     * @param header
     * @return
     */
    public String httpPost(String url, String param, String headerparam, String header) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // POST方法
            // 设置通用的请求属性
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Host", "http://www.bpoint.com.au");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty(headerparam, header);
            conn.connect();
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送请求参数
            out.write(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @param url
     * @param params
     * @param code
     * @return
     * @throws Exception
     */
    public String httpPost(String url, Map<String, String> params, String encode) throws Exception {
        String html = null;
        CloseableHttpResponse response = null;
        HttpPost request = new HttpPost(url);
        // 写入请求参数
        List<NameValuePair> nvps = null;
        if (params != null && params.size() > 0) {
            nvps = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                nvps.add(new BasicNameValuePair(key, params.get(key)));
            }
            request.setEntity(new UrlEncodedFormEntity(nvps, encode));
        }
        response = httpclient.execute(request);
        if (response != null) {
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    html = EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        }
        return html;
    }

    /**
     * HTTP GET请求
     * @param url URL
     * @param params 参数
     * @param isProxy 是否使用代理
     * @return HTML内容
     * @throws Exception
     * @auther wsyte_000
     * @date 2014-4-12 上午2:14:35
     */
    public String httpGet(String url, Map<String, String> params, boolean isProxy) throws Exception {
        return httpAlipyGet(url, headers, params, isProxy);
    }

    /**
     * 测试推送
     * @param ss
     */
    public static void main(String[] ss) {
        HttpUtils httpUtils = HttpUtils.getInstance();
        httpUtils.setProxy("127.0.0.1", 8888, "http");
        String url = "https://api.parse.com/1/push";
        List<Header> headers = new ArrayList<Header>();
        Header header1 = new BasicHeader("X-Parse-Application-Id", "Umlxr9T4QrptOQP3kmCwUXxTGHM2kx643i2GNJTr");
        Header header2 = new BasicHeader("X-Parse-REST-API-Key", "0Q2v8WzJhybgG3yoYLTAYIsQdh2RLTpOGNI9mTNc");
        headers.add(header1);
        headers.add(header2);
        String json = "{\"where\":{\"GCMSenderId\":\"xiaoyou\"},\"data\":{\"action\":\"com.wyhd.receive.push\",\"orderNo\":99999}}";
        try {
            String sss = httpUtils.httpPost(url, headers, json, false);
            System.out.println(sss);
            @SuppressWarnings("unchecked")
            Map<String, Object> map = JacksonBuilder.mapper().readValue(sss, Map.class);
            System.out.println(map.get("result"));
            System.out.println(map.get("result").equals(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
