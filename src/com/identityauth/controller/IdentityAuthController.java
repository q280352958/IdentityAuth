package com.identityauth.controller;

import java.util.Map;

import org.dom4j.DocumentException;

import com.identityauth.bean.RequestProJsonBean;
import com.identityauth.thread.IdentityAuthProcess;
import com.identityauth.util.JsonTool;
import com.identityauth.util.XmlTool;
import com.jfinal.core.Controller;

public class IdentityAuthController extends Controller {

	private RequestProJsonBean reqJson;

	public void index() throws DocumentException {
		String respXml = null;
		String respJson = null;
		Map<String, Object> reMap = null;
		Exception ibex = null;
		// respJson = new ResponseJsonBean();
		String json = getPara("JSON");
		System.out.println("ÇëÇóµÄJSON£º" + json);
		if(!json.isEmpty()){
			reqJson = (RequestProJsonBean)JsonTool.json2Obj(json, RequestProJsonBean.class);
			IdentityAuthProcess process = new IdentityAuthProcess();
			respXml = process.run(reqJson);
			respJson = JsonTool.map2Json(XmlTool.xml2Map(respXml));
		}
		 renderJson(respJson);
	}
}
