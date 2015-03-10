package com.identityauth.config;

import com.identityauth.controller.IdentityAuthController;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.render.ViewType;

public class IdentityAuthConfig extends JFinalConfig{

	@Override
	public void configConstant(Constants me) {
		me.setEncoding("utf-8");
		me.setDevMode(true);
		me.setViewType(ViewType.FREE_MARKER);
	}

	@Override
	public void configRoute(Routes me) {
		me.add("/identityAuth", IdentityAuthController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
		//loadPropertyFile("identityAuth_config.txt");
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		
	}

}
