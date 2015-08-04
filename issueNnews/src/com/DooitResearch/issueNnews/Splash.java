package com.DooitResearch.issueNnews;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

public class Splash extends Activity {
	Handler handler;
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
    setContentView(R.layout.splash);
    Common.customTitle(this, "두잇서베이");
    CookieSyncManager.createInstance(this);
	CookieManager cookieManager = CookieManager.getInstance();
	cookieManager.removeAllCookie();
	new Common(this);
	try{
        findViewById(Window.ID_ANDROID_CONTENT).post(new Runnable() {
			@Override
			public void run() {
				Rect rcStatus =new Rect();
				Window window=getWindow();
				window.getDecorView().getWindowVisibleDisplayFrame(rcStatus);
		        Common.status= rcStatus.top;
		        Common.content=window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
		        Common.title=Common.content-Common.status;
			}
		});
		}catch(Exception e){
			Log.e("checkDevice", " Error Occur");
		}
	Display display=((WindowManager)this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    Common.d_Width=(int)display.getWidth();
    Common.d_Height=(int)display.getHeight();

    if(!Common.checkNetwork(this)) {
		finish();
		return;
	}
	handler=new Handler(){
		public void handleMessage(android.os.Message message){
			   if(Common.checkPreference()){
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("phone", Common.phone));
					postParameters.add(new BasicNameValuePair("phone_id", Common.phone_id));
					postParameters.add(new BasicNameValuePair("id", Common.id));
					postParameters.add(new BasicNameValuePair("lat", "kakaoPoll"));
					postParameters.add(new BasicNameValuePair("lng", "kakaoPoll"));
					boolean response=false;
					boolean backgroundResponse=false;
					String AsyncTaskError=null;
					ProgressDialog dialog=null;
						try {
							System.out.println(1);
							response = Common.executeHttpPost(
							"http://app.dooit.co.kr/member/login_check/reload",postParameters);
							System.out.println(2);
							System.out.println(response);
							if (response) {
								backgroundResponse = true;
							} else {
								backgroundResponse = false;
							}
						} catch (Exception e) {
							AsyncTaskError = e.getMessage();
						}if (backgroundResponse == true) {
							Common.goWebview(Splash.this, Common.Issue, false, false);
						} else {
							if (AsyncTaskError != null) {
								Log.e("loginError", "AsyncTaskError");
								Toast.makeText(
										Splash.this,
										"로그인 인증오류\n(" + AsyncTaskError
												+ ")가 발생하였습니다.\n 다시 시도해 주시기 바랍니다.  ",
										Toast.LENGTH_LONG).show();
							} else {
									Toast.makeText(Splash.this, "아이디와 비밀번호를 다시 확인해 주세요",
											Toast.LENGTH_LONG).show();
							}
							Common.deletePreference();
							finish();
						}	
			    }else{
			    	startActivity(new Intent(Splash.this, Login.class));
					finish();
			    }
		}
	};
	handler.sendEmptyMessageDelayed(0, 400);
}
}