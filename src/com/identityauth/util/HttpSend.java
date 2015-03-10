package com.identityauth.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.jfinal.kit.PropKit;

public class HttpSend {

    /**
     * 发送单段xml报文的http方法，主要用于向省BOSS发送报文
     * @param xml
     * @param timeOut
     * @return
     * @throws IOException
     */
    public String send(String xml, int timeOut) throws IOException{
//        String url = PropKit.use(new File("bossInter_config.txt")).get("interBossUrl");
    	String url = "testUrl";
        return send(url,xml,timeOut);
    }
    /**
     * 发送单段xml报文的http方法，主要用于向省BOSS发送报文
     * @param xml
     * @param timeOut
     * @return
     * @throws IOException
     */
    /**
     *
     * 发送单段xml报文的http方法，主要用于向省BOSS发送报文
     * @param url           发送地址
     * @param xml           xml报文
     * @return              返回的结果字符串
     * @throws IOException  如果发送有错误则抛出
     */
    public String send(String url,String xml,int timeOut) throws IOException{
        String res = null;
        StringEntity se = null;
        try {
            //se = new StringEntity(xml,"UTF-8");
        	se = new StringEntity(xml,"GBK");
        } catch (UnsupportedEncodingException e) {
            
        }
        HttpPost hp = new HttpPost(url);
        hp.setEntity(se);
        //hp.addHeader("Content-Type", "text/xml; charset=UTF-8");
        hp.addHeader("Content-Type", "text/xml; charset=GBK");
        res = httpExecute(hp,timeOut);
        return res;


    }

    /**
     * 发送两段xml报文的http方法，主要用于向集团BOSS发送报文
     * @param xmlHead           新版xml的报文头
     * @param xmlBody           新版xml的报文体
     * @return                  返回的结果字符串
     * @throws IOException      如果发送有错误则抛出
     */
    public String sendMultipart(String xmlHead,String xmlBody) throws IOException{
        String url = PropKit.use(new File("bossInter_config.txt")).get("interBossUrl");

        String res = null;
        StringBody xmlhead = null;
        StringBody xmlbody = null;
        try {
            xmlhead = new StringBody(xmlHead,"multipart/form-data",Charset.forName("utf-8"));
            xmlbody = new StringBody(xmlBody,"multipart/form-data",Charset.forName("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IOException("generate StringBody exception:"+e.getMessage());
        }
        MultipartEntity mpe = new MultipartEntity();
        mpe.addPart("xmlhead", xmlhead);
        mpe.addPart("xmlbody", xmlbody);
        HttpPost hp = new HttpPost(url);
        hp.setEntity(mpe);
        res = httpExecute(hp,0);
        return res;
    }

    /**
     * http发送的实体方法，将传入的HttpPost对象进行发送，将解析返回的实体为字符串
     * @param hp                已封装且需要发送的HttpPost对象
     * @param timeOut
     * @return                  返回实体，已解析为字符串
     * @throws IOException      如果发送有错误则抛出
     */
    private String httpExecute(HttpPost hp, int timeOut) throws IOException {
        HttpClient httpclient = HttpManage.getConnection();
        if(timeOut>0){
            HttpParams params = httpclient.getParams();
            params.setIntParameter("http.socket.timeout", timeOut*1000);
            params.setIntParameter("http.connection.timeout", timeOut*1000);
            hp.setParams(params);
        }
        // Execute the request
        HttpResponse response = null;
        try {
            response = httpclient.execute(hp);
        } catch (IOException e) {
            httpclient.getConnectionManager().shutdown();
            throw e;
        }
        // Examine the response status
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try {
                    String resContent = EntityUtils.toString(entity,"utf-8");
                    return resContent;
                } catch (IOException ex) {
                    // In case of an IOException the connection will be released
                    // back to the connection manager automatically
                    throw ex;
                } catch (RuntimeException ex) {
                    // In case of an unexpected exception you may want to abort
                    // the HTTP request in order to shut down the underlying
                    // connection and release it back to the connection manager.
                    hp.abort();
                    throw ex;
                } finally {
                    // Closing the input stream will trigger connection release
                    EntityUtils.consume(entity);
                    httpclient.getConnectionManager().shutdown();
                }
            }else{
                return "";
            }
        }else{
            httpclient.getConnectionManager().shutdown();
            throw new IOException("response code:"+response.getStatusLine().getStatusCode());
        }
    }
}
