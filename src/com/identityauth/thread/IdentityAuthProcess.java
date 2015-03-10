package com.identityauth.thread;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;
import org.xml.sax.SAXException;

import com.identityauth.bean.RequestProJsonBean;
import com.identityauth.bean.RequestProXmlBean;
import com.identityauth.util.JsonTool;
import com.identityauth.util.NciicClient;
import com.identityauth.util.XmlTool;

public class IdentityAuthProcess {
	/**
	 * boss接口地址
	 */
	// private static String reqUrl = PropKit
	// .use(new File("bossInter_config.txt")).get("provinceBossUrl");
	private static String reqUrl = "";

	public String run(RequestProJsonBean qryJson) 
			 {
		Map<String, Object> reMap = new HashMap<String, Object>();

		String xml = parseRequest(qryJson.getROWS());
		RequestProXmlBean req = new RequestProXmlBean();
		req.setContent(xml);
		String respXml = callService(req, qryJson);
//		reMap.put("msg", JsonTool.map2Json(XmlTool.xml2Map(respXml)));
		// }
		return respXml;
	}

	/**
	 * 解析map为xml
	 * 
	 * @param map
	 * @return
	 * @throws BossInterException
	 */
	private String parseRequest(Map<String, Object> map) {
		StringBuffer sb = new StringBuffer();
		XmlTool.map2Xml(map, sb);
		String xml = sb.toString();
		return xml;
	}

	private String callService(RequestProXmlBean xml,
			RequestProJsonBean jsonBean) {
		String reqXml = null;
		String respXml = null;
		reqXml = xml.getFullString(jsonBean);
		System.out.println("请求xml报文:" + reqXml);
		NciicClient client = new NciicClient();
		try {
			respXml = client.sendRequest(reqXml);
			System.out.println("响应xml报文:" + respXml);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return respXml;
	}

}
