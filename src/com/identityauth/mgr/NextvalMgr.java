package com.identityauth.mgr;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class NextvalMgr {
	private NextvalMgr() {

	}

	public static NextvalMgr getInstance() {
		return NextvalMgrHolder.instance;
	}

	private static class NextvalMgrHolder {
		private static NextvalMgr instance = new NextvalMgr();
	}
	public String getNextval(String seqId) {
		Record record = Db.findFirst("SELECT nextval('"+seqId+"') as nextval");
		return record.get("nextval")+"";
	}

}
