package com.identityauth.util;

public interface ServiceInf {
	// �˲鷽��
	public String nciicCheck(String inLicense, String inConditions);

	// ȡ�������ļ�ģ��
	public String nciicGetCondition(String inLicense) throws Exception;
}

