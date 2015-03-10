package com.identityauth.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.codehaus.xfire.transport.http.EasySSLProtocolSocketFactory;
import org.codehaus.xfire.util.dom.DOMOutHandler;



public class NciicClient {
	public static final String SERVICE_URL = "https://api.nciic.com.cn/nciic_ws/services/";

	public NciicClient() {
	}

	/**
	 * @param args
	 * @throws MalformedURLException
	 */
//	public static void main(String[] args) throws MalformedURLException {
//		/**
//		 * ��Ȩ�ļ����ƣ�������ļ����ڿͻ��˹��̸�Ŀ¼�£��� ���ļ�·����ӡ��� C:\\1.txt
//		 */
//		String license = "license";
//		String con = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><ROWS><INFO><SBM>xmswxmsw53199</SBM></INFO><ROW><GMSFHM>������ݺ���</GMSFHM><XM>����</XM></ROW><ROW FSD=\"350200\" YWLX=\"���֤��ѯ\"><GMSFHM>359002198007172014</GMSFHM><XM>��Ծ��</XM></ROW><ROW FSD=\"350900\" YWLX=\"���֤��ѯ\"><GMSFHM>352225199007070011</GMSFHM><XM>�߼ѱ�</XM></ROW></ROWS>";
//		new NciicClient().executeClient("NciicServices", license, con);
//	}
	
	public String sendRequest(String request)throws MalformedURLException{
		String license = "license";
		return new NciicClient().executeClient("NciicServices", license, request);
	}

	public String executeClient(String serviceName, String license,
			String condition) throws MalformedURLException {
		ProtocolSocketFactory easy = new EasySSLProtocolSocketFactory();
		Protocol protocol = new Protocol("https", easy, 443);
		Protocol.registerProtocol("https", protocol);
		Service serviceModel = new ObjectServiceFactory().create(
				ServiceInf.class, "NciicServices", null, null);
		ServiceInf service = (ServiceInf) new XFireProxyFactory().create(
				serviceModel, SERVICE_URL + serviceName);
		Client client = ((XFireProxy) Proxy.getInvocationHandler(service))
				.getClient();
		client.addOutHandler(new DOMOutHandler());
		
		// ѹ������
		client.setProperty(CommonsHttpMessageSender.GZIP_ENABLED, Boolean.TRUE);
		// ���Գ�ʱ
		client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE,
				"1");
		client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "0");
		/**
		 * ��ȡ��Ȩ�ļ����ݣ���Ϊ��Ȩ�ļ�Ϊ���ܸ�ʽ�벻Ҫ���������κ��޸�
		 */
		String licensecode = null;
		String result = null;
		BufferedReader in;
		
		try {
			InputStream is = this.getClass().getResourceAsStream("/"+license + ".txt");
			InputStreamReader isr = new InputStreamReader(is);
			in = new BufferedReader(isr);
			licensecode = in.readLine();
			// ���ú˲鷽��
			result = service.nciicCheck(licensecode, condition);
			// ��������ģ���ȡ
			// result = service.nciicGetCondition(licensecode);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
