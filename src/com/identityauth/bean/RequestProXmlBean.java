package com.identityauth.bean;

public class RequestProXmlBean {

	private String mContent;
	
	public void setContent(String content){
		mContent = content;
	}
	
	public String getFullString(RequestProJsonBean jsonBean){
		
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"+
		"<ROWS><INFO><SBM>xmswxmsw53199</SBM></INFO><ROW><GMSFHM>������ݺ���</GMSFHM><XM>����</XM></ROW>"+
		mContent+
		"</ROWS>";
	}
}
