/**
 * UUWiFiWebBase 版权声明
 * Copyright (c) 2018, EouTech All rights reserved
 *
 * @brief 文件说明
 * <p>
 * TODO 本文件功能作用详细说明
 * <p>
 * @history 修订说明
 * 20180228 Chris 初始版本
 */
package com.algorithm.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    private static Logger logger= LoggerFactory.getLogger( HttpUtils.class );

    private static HttpUtils pms = null;

    public static HttpUtils getInstance() {
        if ( pms == null ) {
            pms = new HttpUtils();
        }
        return pms;
    }

    public JSONObject doHttpClientPost( String outPutStr, String content_type, String url, String authorization) throws Exception {

        HttpClient httpClient = getMyHttpClient();

        HttpPost method = new HttpPost( url );

//		method.addHeader("Content-Type",content_type);
        if( StringUtils.isEmpty( authorization ) ){
            method.setHeader( "Authorization", "Basic cG1zc3luYzpteXZpZmkyMDE4" );
        }else {
            method.setHeader( "Authorization", authorization );
        }
//        method.addHeader( "X-Redmine-API-Key", pmsApiAccessKey );

        if ( null != outPutStr ) {
            // 解决中文乱码问题
            StringEntity entity = new StringEntity( outPutStr, "utf-8" );
            entity.setContentEncoding( "UTF-8" );
            method.setEntity( entity );
        }
        //content_type:"application/json;charset=ISO-8859-1"
        method.setHeader( "Content-Type", content_type );

        HttpResponse response = httpClient.execute( method );
        String statusCode = String.valueOf( response.getStatusLine().getStatusCode() );
        if ( !statusCode.startsWith( "2" ) ) {
            logger.info( "PmsSyncUtil doPost return exception..statusCode = {0}", statusCode );
            return null;
        }

        String responseStr = EntityUtils.toString( response.getEntity() );

        return JSONObject.parseObject( responseStr );

    }

    public JSONObject doHttpClientGetJson( String url, boolean addHeader,String header ) throws Exception {
        String responseStr =  doHttpClientGet(url,addHeader,header);
        return JSONObject.parseObject( responseStr );
    }

    public String doHttpClientGet( String url, boolean addHeader,String header ) throws Exception {
        HttpClient httpClient = getMyHttpClient();

        HttpGet get = new HttpGet( url );
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(31000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(31000).build();
        get.setConfig( requestConfig );
        if(header == null){
            get.addHeader( "Authorization", "Basic cG1zc3luYzpteXZpZmkyMDE4" );
            if ( addHeader ) {
                get.addHeader( "X-Redmine-API-Key", "87fadcd47c7126f3cf417be9b7a973d1ef19fb1a" );
            }
        }else {
            get.addHeader( "Authorization", header );
        }

        HttpResponse response = httpClient.execute( get );

        String statusCode = String.valueOf( response.getStatusLine().getStatusCode() );
        if ( !statusCode.startsWith( "2" ) ) {
            logger.info( "httpUtil doGet return exception..statusCode = {0}", statusCode );
            return null;
        }

        String responseStr = EntityUtils.toString( response.getEntity(), "UTF-8" );

        responseStr = URLDecoder.decode( responseStr, "UTF-8" );

        return responseStr;
    }



    public JSONObject doHttpClientGet( String url, boolean addHeader ) throws Exception {
        return doHttpClientGetJson(url,addHeader,null);
    }

    public void doHttpClientDownloadFromUrl( String url, String savePath,String filename,String authorizaitionInfo ) throws Exception {
        HttpClient httpClient = getMyHttpClient();

//        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet( url );

        if(authorizaitionInfo!=null){
            get.addHeader( "Authorization", authorizaitionInfo );
        }

        HttpResponse response = httpClient.execute( get );

        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();


        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }

        FileOutputStream output = new FileOutputStream(saveDir+File.separator+filename);

        //得到网络资源的字节数组,并写入文件
//        output.write(get.getResponseBody());
//        output.close();
//        return JSONObject.parseObject( responseStr );
        byte[] buffer=new byte[10240];
        int ch = 0;
        while ((ch = is.read(buffer)) != -1) {
            output.write(buffer,0,ch);
        }
        is.close();
        output.flush();
        output.close();
    }

    public int doHttpClientPuts( String url, String putData,String  authorizaitionInfo) throws Exception {
        HttpClient httpClient = getMyHttpClient();
        HttpPut put = new HttpPut( url );

        if(! StringUtils.isEmpty(putData)){
            StringEntity entity = new StringEntity( putData, "utf-8" );
            entity.setContentEncoding( "UTF-8" );
            put.setEntity( entity );
        }


//        put.addHeader( "X-Redmine-API-Key", pmsApiAccessKey );
        put.setHeader( "Content-Type", "application/json" );

        if(StringUtils.isEmpty( authorizaitionInfo )){
            put.setHeader( "Authorization", authorizaitionInfo);
        }

        HttpResponse response = httpClient.execute( put );

        String statusCode = String.valueOf( response.getStatusLine().getStatusCode() );
        if ( !statusCode.startsWith( "2" ) ) {
            logger.info( "PmsSyncUtil doPut return exception..statusCode = {0}", statusCode );
            return -1;
        } else {
            logger.info( "==> PmsSyncUtil update success,statusCode = {0}", statusCode );
            return 0;
        }
    }


    public JSONObject doHttpClientPut( String url, String putData, String  authorizaitionInfo) throws Exception {
        JSONObject jsonObject=null;
        HttpClient httpClient = getMyHttpClient();

        HttpPut put = new HttpPut( url );

        if(! StringUtils.isEmpty(putData)){
            StringEntity entity = new StringEntity( putData, "utf-8" );
            entity.setContentEncoding( "UTF-8" );
            put.setEntity( entity );
        }


//        put.addHeader( "X-Redmine-API-Key", pmsApiAccessKey );
        put.setHeader( "Content-Type", "application/json" );

        if(!StringUtils.isEmpty( authorizaitionInfo )){
            put.setHeader( "Authorization", authorizaitionInfo);
        }

        HttpResponse response = httpClient.execute( put );

        HttpEntity httpEntity = response.getEntity();

        if (httpEntity != null){
            String str=EntityUtils.toString(httpEntity);
            jsonObject= JSONObject.parseObject( str );
        }

        return jsonObject;
    }


    /**
     * 忽略证书认证   https://...
     *
     * @return
     */
    public HttpClient getMyHttpClient() {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            TrustManager easyTrustManager = new X509TrustManager() {

                public void checkClientTrusted( java.security.cert.X509Certificate[] x509Certificates, String s )
                        throws java.security.cert.CertificateException {
                }

                public void checkServerTrusted( java.security.cert.X509Certificate[] x509Certificates, String s )
                        throws java.security.cert.CertificateException {
                }

                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            SSLContext sslcontext = SSLContext.getInstance( "TLS" );
            sslcontext.init( null, new TrustManager[] { easyTrustManager }, null );
            SSLSocketFactory sf = new SSLSocketFactory( sslcontext );
            //忽略所有主机名的比对
            sf.setHostnameVerifier( SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER );
            Scheme sch = new Scheme( "https", 443, sf );
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register( sch );
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return httpClient;
    }


    /**
     * 发送 GET 请求（HTTPs），K-V形式
     * @param url
     * @return
     */

    public static JSONObject doGetPms( String url ) {
        return doGetPms( url, new HashMap< String, Object >(), "" );
    }

    /**
     * 发送 GET 请求（HTTPs），K-V形式
     * @param params
     * @return
     */
    public static JSONObject doGetPms( String apiurl, Map< String, Object > params, String authorization ) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        StringBuffer param = new StringBuffer();
        int i = 0;
        if (params != null){
            for ( String key : params.keySet() ) {
                if ( i == 0 )
                    param.append( "?" );
                else
                    param.append( "&" );
                param.append( key ).append( "=" ).append( params.get( key ) );
                i++;
            }
        }
        apiurl += param;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            javax.net.ssl.SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(apiurl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

//            httpUrlConn.setRequestProperty( "Content-Type", "application/json" );
            if ( TextUtils.isEmpty( authorization ) ){
                httpUrlConn.setRequestProperty( "Authorization", "Basic cG1zc3luYzpteXZpZmkyMDE4" );
            } else {
                httpUrlConn.setRequestProperty( "Authorization", "Basic " + authorization );
            }
//            httpUrlConn.setRequestProperty( "X-Redmine-API-Key", "87fadcd47c7126f3cf417be9b7a973d1ef19fb1a" );
//            httpUrlConn.setRequestProperty( "User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)" );
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod("GET");

            httpUrlConn.connect();

//            // 当有数据需要提交时
//            if (null != outputStr) {
//                OutputStream outputStream = httpUrlConn.getOutputStream();
//                // 注意编码格式，防止中文乱码
//                outputStream.write(outputStr.getBytes("UTF-8"));
//                outputStream.close();
//            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            System.out.println(buffer.toString());
            jsonObject = JSONObject.parseObject(buffer.toString());
        }  catch (Exception e) {

            System.err.println("https request error:"+e.getMessage());
        }
        return jsonObject;
    }


    /**
     * 从网络url (https)中下载文件
     */
    public static String  downLoadFromUrl(String urlStr,String savePath,String filename,String authorizaitionInfo) throws IOException, NoSuchProviderException, NoSuchAlgorithmException, KeyManagementException {
        // 创建SSLContext对象，并使用我们指定的信任管理器初始化
        String filePath="";
        try {
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            javax.net.ssl.SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(urlStr);
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3*1000);
            conn.setSSLSocketFactory(ssf);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            if(authorizaitionInfo!=null && !authorizaitionInfo.isEmpty()){
                conn.setRequestProperty( "Authorization", authorizaitionInfo );
            }

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod("GET");
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            //获取自己数组
            byte[] getData = readInputStream(inputStream);

            //文件保存位置
            File saveDir = new File(savePath);
            if(!saveDir.exists()){
                saveDir.mkdir();
            }
            FileOutputStream fos = new FileOutputStream(saveDir+File.separator+filename);
            fos.write(getData);
            if(fos!=null){
                fos.close();
            }
            if(inputStream!=null){
                inputStream.close();
            }

            System.out.println("info:"+url+" download success");
//            filePath= file.getAbsolutePath();
        }catch (Exception e) {
            System.err.println("https request error:"+e.getMessage());
        }
        return filePath;
    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


    public static byte[] getRequestPostBytes( HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }
}

