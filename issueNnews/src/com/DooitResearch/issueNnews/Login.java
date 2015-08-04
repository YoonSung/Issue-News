package com.DooitResearch.issueNnews;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	boolean Flag = false;
	String id,pw,phone_id,phone,AsyncTaskError;
	ProgressDialog dialog;
	CookieManager cookieManager;
	boolean backgroundResponse, response;
	Common common;
	SharedPreferences spf;
	ArrayList<NameValuePair> postParameters;
	EditText eid;
	EditText epw;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.login);
	    common=new Common(this);
	}
	public void btnJoin(View v){
		Common.goWebview(this, Common.join, false, true);
	}
	public void btnYes(View v){
		LayoutInflater inflater = getLayoutInflater();
		final View customview=inflater.inflate(R.layout.login_box, null);
		new AlertDialog.Builder(this)
		.setIcon(R.drawable.ic_box)
		.setTitle(R.string.kor)
		.setView(customview)
		.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(!Common.checkNetwork(Login.this)||!Common.checkPhoneNum(Login.this))
					return;
			    eid=((EditText)customview.findViewById(R.id.eid));
			    epw=((EditText)customview.findViewById(R.id.epw));
			    id=eid.getText().toString().trim();
			    pw=epw.getText().toString().trim();
				if((id.length()==0)||(pw.length()==0)){
					Common.nToast(Login.this, "입력값에 공백이 있습니다.");
					return;
				}
					phone=Common.phone;
					phone_id=Common.phone_id;
						new LoginCheck().execute();
				return;
			}
		}).setNegativeButton("ID/PW 찾기", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Common.goWebview(Login.this, Common.found, false, true);
			}
		}).create().show();
	}
	public void btnNot(View v){
		new AlertDialog.Builder(Login.this)
		.setIcon(R.drawable.ic_info)
		.setTitle("이슈N뉴스")
		.setMessage("회원로그인시 참여포인트 지급 \n\n" +
				"및 두잇서베이의 모든 서비스를\n\n이용하실 수 있습니다.\n\n"+
				"최초 1회 로그인 이후 부터는\n\n자동로그인이 가능합니다.")
		.setPositiveButton(" 입 장 ",
			new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					Common.goWebview(Login.this, Common.Issue, false, false);
					finish();
				}
			}).show();
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
	       if(keyCode == KeyEvent.KEYCODE_MENU) {
	    	   Common.goStory(this);
	       }else if(keyCode == KeyEvent.KEYCODE_BACK){
	    	   if (!Flag) {
	    		   Toast.makeText(Login.this, "뒤로버튼을 한번 더 누르시면 종료됩니다.",
						Toast.LENGTH_LONG).show();
	    		   Flag = true;
	    		   handler.sendEmptyMessageDelayed(0, 1500);
	    	   } else {
	    		   finish();
	    	   }
	      }
	       return false;
	}
	
	private class LoginCheck extends AsyncTask<String, Integer, Long> {
		protected void onPreExecute() {
			super.onPreExecute();
			if(Common.checkPreference()){
				id=Common.id;
				phone=Common.phone;
				phone_id=Common.phone_id;
			}
			dialog = ProgressDialog.show(Login.this, "이슈N뉴스폴", "로그인 인증 중..");
		}
		@Override
		protected Long doInBackground(String... arg0) {
				postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("phone", phone));
				postParameters.add(new BasicNameValuePair("phone_id", phone_id));
				postParameters.add(new BasicNameValuePair("id", id));
				postParameters.add(new BasicNameValuePair("password", pw));
					try {
						response = Common.executeHttpPost(
								"http://app.dooit.co.kr/member/login_check",postParameters);
						if (response) {
							Common.savePreference(id);
							backgroundResponse = true;
						} else {
							backgroundResponse = false;
						}
					} catch (Exception e) {
						AsyncTaskError = e.getMessage();
					}
				return null;
		}
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);
			if (backgroundResponse == true) {
				Common.goWebview(Login.this, Common.Issue, false, false);
				finish();
			} else {
				if (AsyncTaskError != null) {
					Log.e("loginError", "AsyncTaskError");
					Toast.makeText(
							Login.this,
							"로그인 인증오류\n(" + AsyncTaskError
									+ ")가 발생하였습니다.\n 다시 시도해 주시기 바랍니다.  ",
							Toast.LENGTH_LONG).show();
				} else {
						Toast.makeText(Login.this, "아이디와 비밀번호를 다시 확인해 주세요",
								Toast.LENGTH_LONG).show();
				}
				Common.deletePreference();
			}	
			dialog.dismiss();
		}
	}
}

