package test.工具合集.HTTP;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/9
 * @描述
 */
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import test.工具合集.JSON.JsonUtil;
import test.工具合集.logger.Logger;
import test.工具合集.logger.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by lijunfu on 14-5-22.
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private final static String UTF8 = "UTF-8";

    private final static String CONTENT_TYPE_JSON = "application/json";


    /**
     * 执行一个HTTP GET请求,编码方式为GBK，返回请求响应的HTML
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet4GBK(String url, Map<String, String> params) {
        String strResult = null;
        String realUrl = "";
        if (params != null && !params.isEmpty()) {
            realUrl = url + "?";
            for (Map.Entry<String, String> entry : params.entrySet()) {
                try {
                    //手动编码参数值
                    realUrl += entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "gbk") + "&";
                } catch (UnsupportedEncodingException e) {
                    logger.error("UnsupportedEncodingException=", e);
                }
            }
        }
        realUrl = realUrl.substring(0, realUrl.length() - 1);
        logger.info("|doGet4GBK|url|" , realUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        //设置sock_timeout 为10秒！！注意这个参数不设置，可能会造成阻塞
        client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
        // 设置连接超时为10秒
        client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
        HttpGet httpGet = new HttpGet(realUrl);
        HttpResponse httpResponse;
        try {
            httpResponse = client.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 第3步：使用getEntity方法获得返回结果
                strResult = EntityUtils.toString(httpResponse.getEntity());
                logger.info("|doGet4GBK|result|" , strResult);
                // 去掉返回结果中的"\r"字符，
            }
        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException|", e);
        } catch (IOException e) {
            logger.error("IOException|", e);
        } catch (Exception e) {
            logger.error("Exception|", e);
        } finally {
            if (httpGet != null) {
                httpGet.releaseConnection();
            }

            if (client != null) {
                client.getConnectionManager().shutdown();
                client = null;
            }
        }
        return strResult;
    }


    /**
     * 执行一个HTTP POST请求，返回请求响应的HTML
     *
     * @param url
     *         请求的URL地址
     * @param params
     *         请求的查询参数,可以为null
     * @return 返回请求响应的HTML
     * @throws IOException
     * @throws IllegalStateException
     */
    public static String doPostJson(String url, Map<String, String> params) throws Exception {
        String strResult = "";
        HttpPost post = null;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        //设置sock_timeout 为10秒！！注意这个参数不设置，可能会造成阻塞
        httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
        // 设置连接超时为10秒
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);

        try {
            post = new HttpPost(url);
            String json = JsonUtil.toJson(params);

            StringEntity se = new StringEntity(json, UTF8);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_JSON));
            se.setContentType(CONTENT_TYPE_JSON);
            post.setEntity(se);
            HttpResponse response = httpClient.execute(post);

            // 若状态码为200 ok
            if (response.getStatusLine().getStatusCode() == 200) {
                // 取出回应字串
                strResult = EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            logger.error("doPostJson|PostJson请求异常|Exception", e);
        } finally {
            if (post != null){
                post.releaseConnection();
            }

            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
                httpClient = null;
            }
        }

        return strResult;
    }


    /**
     * 执行一个HTTP POST请求，传递json数据，使用UTF-8编码，setContentType("application/json");返回请求响应的HTML，返回数据也用UFT-8解析
     *
     * @param url
     *         请求的URL地址
     * @param json
     *         请求传递的json数据
     * @return 返回请求响应的HTML
     * @throws IllegalStateException
     * @throws IOException
     */
    public static String doPostPutJsonAllUTF84Ctrl(String url, String json) throws IllegalStateException, IOException {
        HttpPost post = null;
        String strResult = "";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        //设置sock_timeout 为10秒！！注意这个参数不设置，可能会造成阻塞
        httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
        // 设置连接超时为10秒
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);

        try {
            post = new HttpPost(url);
            if (StringUtils.isBlank(json)) {
                json = "{}";
            }
            StringEntity se = new StringEntity(json, UTF8);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, CONTENT_TYPE_JSON));
            se.setContentType(CONTENT_TYPE_JSON);
            post.setEntity(se);

            HttpResponse response = httpClient.execute(post);
            // 若状态码为200 ok
            if (response.getStatusLine().getStatusCode() == 200) {
                // 取出回应字串
                strResult = new String(EntityUtils.toByteArray(response.getEntity()), UTF8);
            }
        } catch (IOException e) {
            logger.error("IOException|", e);
        } catch (ParseException e) {
            logger.error("ParseException|", e);
        } catch (Exception e) {
            logger.error("Exception|", e);
        } finally {
            if (post != null){
                post.releaseConnection();
            }
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
                httpClient = null;
            }
        }
        return strResult;
    }

}
