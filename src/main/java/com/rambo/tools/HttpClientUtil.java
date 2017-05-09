package com.rambo.tools;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create by Rambo on 2017/5/9
 * HTTP 服务工具类，包括 带参数的 post/http get/htts get 方法
 **/
public class HttpClientUtil {
    private RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000).setConnectionRequestTimeout(15000).build();
    private static HttpClientUtil instance = null;

    private HttpClientUtil() {
    }

    public static HttpClientUtil getInstance() {
        if (instance == null) {
            instance = new HttpClientUtil();
        }
        return instance;
    }

    /**
     * Description:发送 post请求
     *
     * @param httpUrl 地址
     */
    public String sendHttpPost(String httpUrl) {
        HttpPost httpPost = new HttpPost(httpUrl);
        return sendHttpPost(httpPost, null);
    }

    /**
     * Description:发送 post请求
     *
     * @param httpUrl 地址
     * @param params  参数(格式:key1=value1&key2=value2)
     */
    public String sendHttpPost(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);
        try {
            StringEntity stringEntity = new StringEntity(params, "UTF-8");
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost, null);
    }

    /**
     * Description:发送 post请求
     *
     * @param httpUrl 地址
     * @param params  参数(格式:key1=value1&key2=value2)
     * @param charset 请求参数字符集
     */
    public String sendHttpPost(String httpUrl, String params, String charset) {
        HttpPost httpPost = new HttpPost(httpUrl);
        try {
            StringEntity stringEntity = new StringEntity(params, charset);
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost, charset);
    }

    /**
     * Description:发送 post请求
     *
     * @param httpUrl 地址
     * @param maps    参数
     */
    public String sendHttpPost(String httpUrl, Map<String, String> maps) {
        HttpPost httpPost = new HttpPost(httpUrl);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost, null);
    }

    /**
     * Description:发送 post请求
     *
     * @param httpUrl   地址
     * @param headParam 请求头设置的参数
     * @param reqParams 请求参数
     */
    public String sendHttpPost(String httpUrl, Map<String, String> headParam, Map<String, String> reqParams) {
        HttpPost httpPost = new HttpPost(httpUrl);
        try {
            for (String key : headParam.keySet()) {
                httpPost.setHeader(key, headParam.get(key));
            }

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (String key1 : reqParams.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key1, reqParams.get(key1)));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost, null);
    }

    /**
     * Description:发送 post请求
     *
     * @param httpUrl 地址
     * @param maps    参数
     * @param charset post 携带参数的字符集
     */
    public String sendHttpPost(String httpUrl, Map<String, String> maps, String charset) {
        HttpPost httpPost = new HttpPost(httpUrl);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (String key : maps.keySet()) {
            nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, charset));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendHttpPost(httpPost, charset);
    }

    /**
     * 发送 get请求
     *
     * @param httpUrl 请求地址
     */
    public String sendHttpGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGet(httpGet, null);
    }

    /**
     * 发送 get请求
     *
     * @param httpUrl 请求地址
     * @param charset 返回数据字符集
     */
    public String sendHttpGet(String httpUrl, String charset) {
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGet(httpGet, charset);
    }

    /**
     * 发送 Https get请求
     *
     * @param httpUrl 请求连接
     */
    public String sendHttpsGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpsGet(httpGet, null);
    }

    /**
     * 发送 Https get请求
     *
     * @param httpUrl 请求连接
     * @param charset 返回数据字符集
     */
    public String sendHttpsGet(String httpUrl, String charset) {
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpsGet(httpGet, charset);
    }

    /**
     * 发送Post请求
     *
     * @param httpPost post 对象
     * @param charset  返回数据字符集
     */
    private String sendHttpPost(HttpPost httpPost, String charset) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        httpPost.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            String retMessage = EntityUtils.toString(response.getEntity(), StringUtil.isEmpty(charset) ? "UTF-8" : charset);
            EntityUtils.consume(response.getEntity());
            return retMessage;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(response, httpClient);
        }
        return null;
    }

    /**
     * 发送Get请求
     *
     * @param httpGet httpget 对象
     * @param charset 字符集
     */
    private String sendHttpGet(HttpGet httpGet, String charset) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            httpGet.setConfig(requestConfig);
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String retMessage = EntityUtils.toString(entity, StringUtil.isEmpty(charset) ? "UTF-8" : charset);
            EntityUtils.consume(response.getEntity());
            return retMessage;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(response, httpClient);
        }
        return null;
    }

    /**
     * 发送Get请求Https
     *
     * @param httpGet httpget 对象
     * @param charset 字符集
     */
    private String sendHttpsGet(HttpGet httpGet, String charset) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String responseContent = null;
        try {
            PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(httpGet.getURI().toString()));
            DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
            httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
            httpGet.setConfig(requestConfig);

            response = httpClient.execute(httpGet);
            responseContent = EntityUtils.toString(response.getEntity(), StringUtil.isEmpty(charset) ? "UTF-8" : charset);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(response, httpClient);
        }
        return responseContent;
    }

    /**
     * Description:关闭资源
     */
    private void closeResource(CloseableHttpResponse response, CloseableHttpClient httpClient) {
        try {
            // 关闭连接,释放资源
            if (response != null) {
                response.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}