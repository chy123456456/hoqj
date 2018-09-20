package com.keng.base.utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.jdom2.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.keng.base.httpClient.HttpClientApp;
import com.keng.base.httpClient.HttpClientResponse;

public class HttpClientUtil {
    private static Log _log = null;

    @SuppressWarnings("unused")
    private static Log getLog() {
        if (_log == null) _log = LogFactory.getLog(HttpClientUtil.class);
        return _log;
    }

    /**
     * 发送GET请求，并将返回数据解析为Html
     * 
     * @param app
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static Document getHtmlDocFromServer(HttpClientApp app, String url, Object... params) throws Exception {
        String content = requestDataFromServer(app, url, params);
        if (ObjectUtil.isEmpty(content)) return null;
        return Jsoup.parse(content);
    }

    /**
     * 发送GET请求，并将返回数据解析为Json
     * 
     * @param app
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getAjaxJsonFromServer(HttpClientApp app, String url, Object... params) throws Exception {
        String content = requestDataFromServer(app, url, params);
        if (ObjectUtil.isEmpty(content)) return null;
        return BeanUtils.parseTextToJson(content);
    }

    /**
     * 发送GET请求，并将返回数据解析为Xml
     * 
     * @param app
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static Element getAjaxXmlFromServer(HttpClientApp app, String url, Object... params) throws Exception {
        String content = requestDataFromServer(app, url, params);
        if (ObjectUtil.isEmpty(content)) return null;
        return BeanUtils.parseTextToXml(content);
    }

    /**
     * 发送POST请求，并将返回数据解析为Json
     * 
     * @param app
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static Map<String, Object> postAjaxRequestReturnJson(HttpClientApp app, String url, Object... params) throws Exception {
        String content = postRequestToServer(app, url, params);
        if (ObjectUtil.isEmpty(content)) return null;
        return BeanUtils.parseTextToJson(content);
    }

    /**
     * 发送POST请求，并将返回数据解析为Xml
     * 
     * @param app
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static Element postAjaxRequestReturnXml(HttpClientApp app, String url, Object... params) throws Exception {
        String content = postRequestToServer(app, url, params);
        if (ObjectUtil.isEmpty(content)) return null;
        return BeanUtils.parseTextToXml(content);
    }

    /**
     * 发送POST请求，并将返回数据解析为Html
     * 
     * @param app
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static Document postAjaxRequestReturnHtml(HttpClientApp app, String url, Object... params) throws Exception {
        String content = postRequestToServer(app, url, params);
        if (ObjectUtil.isEmpty(content)) return null;
        return Jsoup.parse(content);
    }

    /**
     * 用指定的client上下文环境向服务器请求数据
     * 
     * @param app
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String requestDataFromServer(HttpClientApp app, String url, Object... params) throws Exception {
        String result = null;
        url = encodeUriWithParam(url, params);
        HttpUriRequest request = app.createRequest(url, HttpClientApp.REQUEST_METHOD_GET);
        HttpClientResponse clientResponse = app.executeAction(request);
        int statusCode = clientResponse.getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            result = clientResponse.getTextContent();
        } else {
            throw new RuntimeException("非预期的Http状态码：" + statusCode);
        }
        clientResponse.consumeContent();
        return result;
    }

    /**
     * 用指定的client上下文环境向服务器发送POST请求
     * 
     * @param app
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String postRequestToServer(HttpClientApp app, String url, Object... params) throws Exception {
        String result = null;
        HttpUriRequest request = app.createRequest(url, HttpClientApp.REQUEST_METHOD_POST);
        request = encodeRequestBody(request, params);
        HttpClientResponse clientResponse = app.executeAction(request);
        int statusCode = clientResponse.getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            result = clientResponse.getTextContent();
        } else {
            throw new RuntimeException("非预期的Http状态码：" + statusCode);
        }
        clientResponse.consumeContent();
        return result;
    }

    /**
     * 展开参数拼装至URL，并为URL添加随机码
     * 
     * @param uri
     * @param params
     * @return
     */
    public static String encodeUriWithParam(String uri, Object[] params) {
        StringBuffer sb = new StringBuffer(uri);
        sb.append((sb.lastIndexOf("?") == -1) ? '?' : '&').append("__").append(ObjectUtil.getRandomString());
        if (params != null) {
            for (int i = 0; i < params.length; i = i + 2) {
                sb.append('&').append(params[i]).append('=').append(params[i + 1]);
            }
        }
        return sb.toString();
    }

    /**
     * 将参数封装成 requestBody
     * 
     * @param request
     * @param params
     * @return
     * @throws Exception
     */
    public static HttpUriRequest encodeRequestBody(HttpUriRequest request, Object[] params) throws Exception {
        List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>(params.length * 2 + 2);
        for (int i = 0; i < params.length; i = i + 2) {
            nvps.add(new BasicNameValuePair(String.valueOf(params[i]), String.valueOf(params[i + 1])));
        }
        if (nvps.size() > 0) ((HttpPost) request).setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
        return request;
    }
    // public static void main(String[] args) throws Exception {
    // String szText = requestDataFromServer(HttpClientApp.newBotInstance(), "http://localhost:8080/launcher/");
    // System.out.println(szText);
    // }
}
