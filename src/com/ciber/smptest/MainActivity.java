package com.ciber.smptest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.Header;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.sap.mobile.lib.request.BaseRequest;
import com.sap.mobile.lib.request.INetListener;
import com.sap.mobile.lib.request.IRequest;
import com.sap.mobile.lib.request.IRequestStateElement;
import com.sap.mobile.lib.request.IResponse;
import com.sap.smp.rest.ClientConnection;
import com.sap.smp.rest.SMPClientListeners.ISMPUserRegistrationListener;
import com.sap.smp.rest.SMPException;
import com.sap.smp.rest.UserManager;

public class MainActivity extends Activity implements ISMPUserRegistrationListener {

	private UserManager mUserManager;
	private String mEndPoint;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Log.d("GB", "starting...");

		ClientConnectionUtility ccu = new ClientConnectionUtility(this);

		mUserManager = new UserManager(ccu.getClientConnection());

		mUserManager.setUserRegistrationListener(MainActivity.this);
		try {
			Log.d("GB", "registering...");
			mUserManager.registerUser(false);
		} catch (SMPException e) {
			Log.d("GB", e.getMessage(), e);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onAsyncRegistrationResult(State registrationState, ClientConnection conn, int errCode, String errMsg) {

		if (registrationState == State.SUCCESS) {
			
			try {
				Log.d("GB", "APPCID" + mUserManager.getApplicationConnectionId());
				
				read(mUserManager.getApplicationConnectionId());
				
			} catch (SMPException e) {
				Log.d("GB", e.getMessage(), e);
			}
		}
		if (registrationState == State.FAILURE) {
			Log.d("GB", errCode + errMsg);
			
		}

	}
	
	public void read(String appID){
		
		IRequest request = new BaseRequest();
		request.setListener(new INetListener() {
			
			@Override
			public void onSuccess(IRequest arg0, IResponse arg1) {
			
				Log.d("GB", "great success! ");
			}
			
			@Override
			public void onError(IRequest arg0, IResponse arg1, IRequestStateElement arg2) {
				
				
				Log.d("GB", "error :-( " + arg1.toString());
			}
		});
		
		request.setPriority(IRequest.PRIORITY_HIGH);
		request.setRequestTAG("GET_NOTIFS");
		request.setRequestMethod(IRequest.REQUEST_METHOD_GET);
		
		mEndPoint = getResources().getString(R.string.endpoint);
		
		mEndPoint += "?ODATA_JSON_FORMAT";
		
		request.setRequestUrl(mEndPoint);
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("X-SMP-APPCID", appID);
		
		ClientConnectionUtility.reqManagerObject.makeRequest(request);
		
	}
	
	
	
	

}
