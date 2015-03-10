package com.identityauth.bean;

public class RequestProXmlBean {

	private String mContent;
	
	public void setContent(String content){
		mContent = content;
	}
	
	public String getFullString(RequestProJsonBean jsonBean){
		
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
		"<ROWS><INFO><SBM>xmswxmsw53199</SBM></INFO><ROW><GMSFHM>公民身份号码</GMSFHM><XM>姓名</XM></ROW>"+
		mContent+
		"</ROWS>";
	}
}
