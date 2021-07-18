package com.zhuweitung.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zhuweitung.signin.Cookie;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zhuweitung
 * @create 2021/4/18
 */
@Log4j2
@Data
public class HttpUtil {
    /**
     * 设置连接建立超时 5000ms
     * 设置从连接池获取连接超时 5000ms
     * 设置数据包传输最大间隔 10000ms
     */
    private static final RequestConfig REQUEST_CONFIG = RequestConfig.custom().setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .setSocketTimeout(10000)
            .build();

    /**
     * 设置超时重试次数为2
     */
    private static final DefaultHttpRequestRetryHandler HTTP_REQUEST_RETRY_HANDLER = new DefaultHttpRequestRetryHandler(2, true);

    static Cookie cookie = Cookie.getInstance();

    private static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.70";

    private static CloseableHttpClient httpClient = null;

    private static CloseableHttpResponse httpResponse = null;

    /**
     * @description 设置UA标识
     * @param userAgent
     * @return void
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static void setUserAgent(String userAgent) {
        HttpUtil.userAgent = userAgent;
    }

    /**
     * @description 获取默认请求头
     * @param 
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("user-agent", userAgent);
        headers.put("accept-language", "zh-Hans-CN;q=1, en-CN;q=0.9, ja-CN;q=0.8, zh-Hant-HK;q=0.7, io-Latn-CN;q=0.6");
        headers.put("accept", "application/json");
        headers.put("content-type", "application/x-www-form-urlencoded");
        return headers;
    }

    /**
     * @description post请求
     * @param url
     * @return com.google.gson.JsonObject
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static JsonObject doPost(String url) {
        return doPost(url, null, getDefaultHeaders());
    }

    /**
     * @description post请求
     * @param url
     * @param params
     * @return com.google.gson.JsonObject
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static JsonObject doPost(String url, Map<String, String> params) {
        StringBuilder requestBody = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (requestBody.length() > 0) {
                requestBody.append("&");
            }
            requestBody.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return doPost(url, requestBody.toString());
    }

    /**
     * @description post请求
     * @param url
     * @param params
     * @param extHeaders
     * @return com.google.gson.JsonObject
     * @author zhuweitung
     * @date 2021/7/18
     */
    public static JsonObject doPost(String url, Map<String, String> params, Properties extHeaders) {
        StringBuilder requestBody = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (requestBody.length() > 0) {
                requestBody.append("&");
            }
            requestBody.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return doPost(url, requestBody.toString(), extHeaders);
    }

    /**
     * @description post请求
     * @param url
     * @param requestBody
     * @return com.google.gson.JsonObject
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static JsonObject doPost(String url, String requestBody) {
        return doPost(url, requestBody, getDefaultHeaders());
    }

    /**
     * @description post请求
     * @param url
     * @param requestBody
     * @param extHeaders
     * @return com.google.gson.JsonObject
     * @author zhuweitung
     * @date 2021/7/18
     */
    public static JsonObject doPost(String url, String requestBody, Properties extHeaders) {
        Map<String, String> headers = getDefaultHeaders();
        for (String key : extHeaders.stringPropertyNames()) {
            headers.put(key, extHeaders.getProperty(key));
        }
        return doPost(url, requestBody, headers);
    }

    /**
     * @description post请求
     * @param url
     * @param requestBody
     * @param headers
     * @return com.google.gson.JsonObject
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static JsonObject doPost(String url, String requestBody, Map<String, String> headers) {
        httpClient = HttpClients.custom().setRetryHandler(HTTP_REQUEST_RETRY_HANDLER).build();
        JsonObject resultJson = null;
        //创建httpPost实例
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(REQUEST_CONFIG);

        //设置请求头
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("User-Agent", userAgent);
        httpPost.setHeader("Cookie", cookie.getCookies());
        if (MapUtils.isNotEmpty(headers)) {
            headers.entrySet().forEach(entry -> httpPost.setHeader(entry.getKey(), entry.getValue()));
        }
        if (StringUtil.isJsonString(requestBody)) {
            httpPost.setHeader("Content-Type", "application/json");
        } else {
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        }

        //封装请求参数
        if (StringUtils.isNotBlank(requestBody)) {
            StringEntity stringEntity = new StringEntity(requestBody, "utf-8");
            httpPost.setEntity(stringEntity);
        }

        try {
            //执行post请求
            httpResponse = httpClient.execute(httpPost);
            resultJson = parseResult(httpResponse);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        } finally {
            releaseHttpResource(httpClient, httpResponse);
        }
        return resultJson;
    }

    /**
     * @description get请求
     * @param url
     * @return com.google.gson.JsonObject
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static JsonObject doGet(String url) {
        return doGet(url, null);
    }

    /**
     * @description get请求
     * @param url
     * @param params
     * @return com.google.gson.JsonObject
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static JsonObject doGet(String url, Map<String, String> params) {
        httpClient = HttpClients.custom().setRetryHandler(HTTP_REQUEST_RETRY_HANDLER).build();
        JsonObject resultJson = null;
        try {
            //封装请求参数
            URIBuilder builder = new URIBuilder(url);
            if (MapUtils.isNotEmpty(params)) {
                params.entrySet().forEach(entry -> builder.addParameter(entry.getKey(), entry.getValue()));
            }
            URI uri = builder.build();
            //创建httpGet实例
            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(REQUEST_CONFIG);

            //设置请求头信息
            Map<String , String> headers = getDefaultHeaders();
            if (MapUtils.isNotEmpty(headers)) {
                headers.entrySet().forEach(entry -> httpGet.setHeader(entry.getKey(), entry.getValue()));
            }
            httpGet.setHeader("Connection", "keep-alive");
            httpGet.setHeader("User-Agent", userAgent);
            httpGet.setHeader("Cookie", cookie.getCookies());

            //执行get请求
            httpResponse = httpClient.execute(httpGet);
            resultJson = parseResult(httpResponse);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        } finally {
            releaseHttpResource(httpClient, httpResponse);
        }
        return resultJson;

    }

    /**
     * @description 处理响应结果
     * @param httpResponse
     * @return com.google.gson.JsonObject
     * @author zhuweitung
     * @date 2021/4/18
     */
    public static JsonObject parseResult(CloseableHttpResponse httpResponse) throws IOException {
        JsonObject jsonObj = null;
        if (httpResponse != null) {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            jsonObj = new JsonParser().parse(result).getAsJsonObject();
            switch (statusCode) {
                case 200:
                    break;
                case 412:
                    log.debug(httpResponse.getStatusLine());
                    break;
                default:
            }
        }
        return jsonObj;
    }

    /**
     * @description 释放资源
     * @param httpClient
     * @param httpResponse
     * @return void
     * @author zhuweitung
     * @date 2021/4/18
     */
    private static void releaseHttpResource(CloseableHttpClient httpClient, CloseableHttpResponse httpResponse) {
        if (httpResponse != null) {
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
