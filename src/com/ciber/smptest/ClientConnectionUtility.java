package com.ciber.smptest;

import android.content.Context;

import com.sap.mobile.lib.configuration.Preferences;
import com.sap.mobile.lib.request.ConnectivityParameters;
import com.sap.mobile.lib.request.RequestManager;
import com.sap.mobile.lib.supportability.Logger;
import com.sap.smp.rest.ClientConnection;

public class ClientConnectionUtility {

	
	//TODO remove public static vars!
	public static Logger logger;
	public static Preferences pref;
	public static ConnectivityParameters param;
	public static RequestManager reqManagerObject;
	public static ClientConnection clientConnection;

	public ClientConnectionUtility( Context context){
		
		logger = new Logger();
		pref = new Preferences(context, logger);
		param = new ConnectivityParameters();
		param.setUserName(context.getResources().getString(R.string.username));
		param.setUserPassword(context.getResources().getString(R.string.password));
		reqManagerObject = new RequestManager(logger, pref, param, 1);
		clientConnection = new ClientConnection(context,context.getResources().getString(R.string.app), "default", context.getResources().getString(R.string.sec), reqManagerObject);
		clientConnection.setConnectionProfile(context.getResources().getString(R.string.smp));
		
		//clientConnection.setApplicationConnectionID(<ApplicationConnectionID>); 
		}

	public ClientConnection getClientConnection() {
		return clientConnection;
	}

}
