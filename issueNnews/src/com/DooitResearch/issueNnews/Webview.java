package com.DooitResearch.issueNnews;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Browser;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.HttpAuthHandler;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Webview extends Activity {
	WebView web;
	ProgressBar pb;
	static String current_url=Common.Issue;
	static String past_url;
	static String new_url;
	Intent intent;
	boolean full;
	boolean ing;
	boolean Flag = false;
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    Common.checkNetwork(this);
		intent=getIntent();
		full=intent.getBooleanExtra("full", false);
		if(intent.getStringExtra("url")!=null)
		current_url=intent.getStringExtra("url");
		if(!full){
			setContentView(R.layout.webview);
			web=(WebView)findViewById(R.id.web1);
			pb=(ProgressBar)findViewById(R.id.pb1);
			Common.web_context=this;
		}else{
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
			setContentView(R.layout.webview2);
			web=(WebView)findViewById(R.id.web2);
			pb=(ProgressBar)findViewById(R.id.pb2);
		}
			PageUse(current_url);
	}
	private void PageUse(String url) {
		WebSettings setting=web.getSettings();
        StringBuffer userAgent = new StringBuffer(setting.getUserAgentString());
        userAgent.append(";DooitKakaoPollApp;");
        if(!current_url.contains("doooit.tistory")){
        	web.setBackgroundResource(R.drawable.webview_bg);    
			web.setBackgroundColor(0);
        }
        web.setWebViewClient(new ViewClient());      
        setting.setJavaScriptEnabled(true);
        setting.getLoadsImagesAutomatically();
        setting.setPluginsEnabled(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
		setting.setCacheMode(WebSettings.LOAD_NO_CACHE);
		setting.setUserAgentString(userAgent.toString());
        web.setWebChromeClient(new JSWebChromeClient());
        web.setVerticalScrollbarOverlay(true);
		web.addJavascriptInterface(new MyJavaScriptInterface(), "DooitLocalResearch");
		web.clearCache(true);
        web.loadUrl(url);
	}
	final class MyJavaScriptInterface {
		MyJavaScriptInterface() {}
		public void JoinEnd() {
	        Common.centerToast(Webview.this, "감사합니다. 회원가입되었습니다.");
	        viewFinish(0);
	    }
		public void WebviewClose() {
	         viewFinish(0);
	    }
	}
	private class ViewClient extends WebViewClient{
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			new_url=url;
			past_url=current_url;
			current_url=url;		
			if(("kakaolink").equals(url.substring(0, 9))){
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		        intent.addCategory(Intent.CATEGORY_BROWSABLE);
		        intent.putExtra(Browser.EXTRA_APPLICATION_ID, Webview.this.getPackageName());
		        Webview.this.startActivity(intent);
				return true;
			} else if(url.equals(Common.login)){
				viewFinish(Common.m_login);
				return true;
			} else if(url.equals(Common.join)){
				viewFinish(Common.m_url);
				return true;
			} else if(url.contains(Common.survey)||(!full&&url.contains(Common.report))) {
				Common.goWebview(Webview.this, url, true, true);
				return true;
			} else if(url.contains(Common.poll)
					||url.contains(Common.point)
					||url.contains(Common.location)){
				PageUse(Common.Issue);
				if(!past_url.contains("http://app.dooit.co.kr/survey/view/end")){
				new AlertDialog.Builder(Webview.this)
				.setIcon(R.drawable.ic_warn)
				.setTitle("권한없음")
				.setMessage("\'두잇서베이\' 어플리케이션에서\n\n" +
						"해당서비스 이용이 가능합니다..\n\n" +
						"마켓으로 이동하시겠습니까?")
				.setPositiveButton(" 다운로드 ",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=두잇서베이(DOOITSURVEY)")));
						}
					}).show();
				}
				return true;
			} else {
				return super.shouldOverrideUrlLoading(view, url);
			}
		}
		public void onLoadResource(WebView view, String url) {
			super.onLoadResource(view, url);
		}
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if(url.contains("http://app.dooit.co.kr/member/login/survey/view")){
				PageUse(past_url);
					new AlertDialog.Builder(Webview.this)
						.setTitle("알림")
						.setMessage("\n본 설문은 로그인 후\n\n참여할수 있습니다\n\n로그인 페이지로 이동하시겠습니까?\n")
						.setPositiveButton("이동",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								viewFinish(Common.m_login);
							}
						}).setNegativeButton("취소", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								viewFinish(0);
							}
						}).setCancelable(false).create().show(); 
			}
		}
		public void onPageFinished(WebView view, String url) {	
			past_url=current_url;
			current_url=url;			
			super.onPageFinished(view, url);
		}
		public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
			super.doUpdateVisitedHistory(view, url, isReload);
		}
		public void onFormResubmission(WebView view, Message dontResend, Message resend) {
			super.onFormResubmission(view, dontResend, resend);
		}
		public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
			super.onReceivedHttpAuthRequest(view, handler, host, realm);
		}
		public void onScaleChanged(WebView view, float oldScale, float newScale) {
			super.onScaleChanged(view, oldScale, newScale);
		}
		public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
			super.onTooManyRedirects(view, cancelMsg, continueMsg);
		}
		public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
			super.onUnhandledKeyEvent(view, event);
		}
		public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
			return super.shouldOverrideKeyEvent(view, event);
		}
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
	        super.onReceivedError(view, errorCode, description, failingUrl);
	        Common.nToast(Webview.this, "네트워크 상태가 원활하지 않습니다. 잠시 후 다시 시도해 주세요.");
	        viewFinish(0);	        
	    }
	}
	private class JSWebChromeClient extends WebChromeClient{
		public void onProgressChanged(WebView view, int Progress) {
			if(Progress<100){
				pb.setVisibility(View.VISIBLE);
			}else if(Progress==100){
				pb.setVisibility(View.GONE);
			}
			pb.setProgress(Progress);
			super.onProgressChanged(view, Progress);
		}
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
	          final JsResult finalRes = result;
	           new AlertDialog.Builder(view.getContext())
	           	 .setTitle("알림")
	               .setMessage(message)
	               .setPositiveButton(android.R.string.ok,
	                       new DialogInterface.OnClickListener()
	                       {
	         public void onClick(DialogInterface dialog, int which) {
	          finalRes.confirm();  
	         }})
	               .setCancelable(false)
	               .create()
	               .show();
	           return true;
		}
		public boolean onJsConfirm(WebView view, String url,
				String message, JsResult result) {
		 	  final JsResult finalRes = result;
	            new AlertDialog.Builder(view.getContext())
	            	.setTitle("확인")
	                .setMessage(message)
	                .setPositiveButton(android.R.string.ok,
				                       new DialogInterface.OnClickListener()
				                       {
								         @Override
								         public void onClick(DialogInterface dialog, int which) {
								          finalRes.confirm();  
								         }
								       }
	                				  )
	                .setNegativeButton(android.R.string.cancel,  
	                					new DialogInterface.OnClickListener() {
	                						@Override
						                   public void onClick(DialogInterface dialog, int which) {
						                   	finalRes.cancel();
						                   }
										}
	                				  )
	                .setCancelable(true)
	                .create()
	                .show();
	            return true;
	    	}
	}
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Flag = false;
				break;
			}
		}
	};
	public void viewFinish(int resultCode){
		setResult(resultCode);
		finish();
		overridePendingTransition(R.anim.activity_stop,R.anim.activity_out);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	       if(keyCode == KeyEvent.KEYCODE_MENU) {
	    	   if(!full){
	    		   Common.goStory(Webview.this);
	    	   }else{
	    		   Common.nToast(this, "FULL SCREEN에서는\n사용할 수 없습니다.");
	    	   }
	       }else if(keyCode == KeyEvent.KEYCODE_BACK){
	    	   if(full){
	    		   	viewFinish(0);
	    	   }else if(!full){
	    			if(current_url.equalsIgnoreCase(Common.Issue)){
	    		  			if (!Flag) {
	    			    		   Common.nToast(this, "뒤로버튼을 한번 더 누르시면 종료됩니다.");
	    			    		   Flag = true;
	    			    		   handler.sendEmptyMessageDelayed(0, 1500);
	    		  			} else {
	    			    		   finish();
	    		  			}
	    			}else if(web.canGoBack()){
	    				new_url=current_url;
	    				current_url=past_url;
	    				web.goBack();
	    			}else if((current_url.equals(Common.found))||(current_url.equals(Common.join))){
	    				finish();
	    				overridePendingTransition(R.anim.activity_stop,R.anim.activity_out);
	    			}else{
	    				viewFinish(0);
	    			}
	    	   }
	      }
	       		return false;
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		Context c=Common.web_context;
			if(resultCode==Common.m_url){
					PageUse(Common.join);
			}else if(resultCode==Common.m_login){
					moveLogin(c);
			}else if(resultCode==Common.m_logout){
					finish();
			}
	}
	public void moveLogin(Context c){
		startActivity(new Intent(Webview.this, Login.class));
		((Activity) c).finish();
		overridePendingTransition(R.anim.activity_stop,R.anim.activity_out);
	}
}