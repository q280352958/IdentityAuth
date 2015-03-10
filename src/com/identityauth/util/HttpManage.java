package com.identityauth.util;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.params.SyncBasicHttpParams;

public class HttpManage {
	/**
     * ���ӹ������ĵ�������
     */
    private static HttpManage httpManage;

    /**
     * ʵ�ʵĿͻ������ӹ�����
     */
    private final ThreadSafeClientConnManager cm;

    /**
     * ���Ӳ�������
     */
    private final HttpParams params;

    /**
     * ���ӹ������Ĺ��캯��
     * @param maxTotalConnection    �������
     * @param maxPerRoute           ÿ��·�ɵ�Ĭ���������
     * @param timeoutSecond         ÿ���ӵĳ�ʱ����
     */
    private HttpManage(int maxTotalConnection, int maxPerRoute, int timeoutSecond) {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
                .getSocketFactory()));
        cm = new ThreadSafeClientConnManager(schemeRegistry);
        cm.setMaxTotal(maxTotalConnection);
        cm.setDefaultMaxPerRoute(maxPerRoute);
        
        params = new SyncBasicHttpParams();
        HttpConnectionParamBean cParamsBean = new HttpConnectionParamBean(params);
        cParamsBean.setConnectionTimeout(1000*timeoutSecond);
        cParamsBean.setSoTimeout(1000*timeoutSecond);
        HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
        paramsBean.setVersion(HttpVersion.HTTP_1_1);
        paramsBean.setContentCharset("utf-8");
    }

    /**
     * �������
     * @return      �������úò�����http����
     */
    public static HttpClient getConnection() {
        if (httpManage == null) {
            // ������ӹ�����δ��ʼ������Ĭ��Ϊ���ʼ��
            httpManage = new HttpManage(200, 50, 30);
        }
        //F5������ʱ����
//        return new DefaultHttpClient(httpManage.cm, httpManage.params);
        return new DefaultHttpClient();
    }

    /**
     * ��ʼ�����ӹ�����
     * @param maxTotalConnection    �������
     * @param maxPerRoute           ÿ��·�ɵ�Ĭ���������
     * @param timeoutSecond         ÿ���ӵĳ�ʱ����
     */
    public static void Initial(int maxTotalConnection, int maxPerRoute,
            int timeoutSecond) {
        httpManage = new HttpManage(maxTotalConnection, maxPerRoute,
                timeoutSecond);
    }

    /**
     * �ر����ӹ�����
     */
    public static void shutdown() {
        if (httpManage != null) {
            httpManage.cm.shutdown();
        }
    }
}
