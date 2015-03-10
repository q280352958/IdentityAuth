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
     * 连接管理器的单例对象
     */
    private static HttpManage httpManage;

    /**
     * 实际的客户端连接管理器
     */
    private final ThreadSafeClientConnManager cm;

    /**
     * 连接参数对象
     */
    private final HttpParams params;

    /**
     * 连接管理器的构造函数
     * @param maxTotalConnection    最大连接
     * @param maxPerRoute           每个路由的默认最大连接
     * @param timeoutSecond         每连接的超时秒数
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
     * 获得连接
     * @return      返回设置好参数的http连接
     */
    public static HttpClient getConnection() {
        if (httpManage == null) {
            // 如果连接管理器未初始化，那默认为其初始化
            httpManage = new HttpManage(200, 50, 30);
        }
        //F5出错，暂时屏蔽
//        return new DefaultHttpClient(httpManage.cm, httpManage.params);
        return new DefaultHttpClient();
    }

    /**
     * 初始化连接管理器
     * @param maxTotalConnection    最大连接
     * @param maxPerRoute           每个路由的默认最大连接
     * @param timeoutSecond         每连接的超时秒数
     */
    public static void Initial(int maxTotalConnection, int maxPerRoute,
            int timeoutSecond) {
        httpManage = new HttpManage(maxTotalConnection, maxPerRoute,
                timeoutSecond);
    }

    /**
     * 关闭连接管理器
     */
    public static void shutdown() {
        if (httpManage != null) {
            httpManage.cm.shutdown();
        }
    }
}
