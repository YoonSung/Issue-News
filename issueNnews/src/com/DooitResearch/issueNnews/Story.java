package com.DooitResearch.issueNnews;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.LinearLayout;
public class Story extends Activity implements android.view.View.OnClickListener{
	
	Button s1, s2, s3, s4, s5, s6;
	boolean Flag = false;
	Animation ani1,ani2,ani3,ani4,ani5, ani6;
	int layout_height, layout_width, w, h;
	int btn_width, btn_height, bottom_height;
	boolean login;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);	    
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
        Resources res=getResources();
        DisplayMetrics dm=res.getDisplayMetrics();
        btn_width=(int)(298.15*dm.density);
        btn_height=(int)(67*dm.density);
        bottom_height=(int)(32.83*dm.density);
        layout_width=Common.d_Width;
        layout_height=Common.d_Height-bottom_height-Common.status;
		w=layout_width/90;
		new Common(this);
		
	    s1=new Button(this);
	    s1.setTag(1);
	    s1.setBackgroundResource(R.drawable.s1);
	    s2=new Button(this);
	    s2.setTag(2);
	    s2.setBackgroundResource(R.drawable.s2);
	    s3=new Button(this);
	    s3.setTag(3);
	    s3.setBackgroundResource(R.drawable.s3);
	    s4=new Button(this);
	    s4.setTag(4);
	    s4.setBackgroundResource(R.drawable.s4);
	    s5=new Button(this);
	    s5.setTag(5);
	    s5.setBackgroundResource(R.drawable.s5);
	    s6=new Button(this);
	    s6.setTag(6);
	    System.out.println("id : "+Common.id);
	    if(Common.id==null){
	    	Bitmap bm=BitmapFactory.decodeResource(getResources(), R.drawable.s6);
	    	Drawable dw=(Drawable)new BitmapDrawable(bm);
	    	dw.setAlpha(75);
	    	s6.setBackgroundDrawable(dw);
	    	s6.setClickable(false);
	    }else{
	    	s6.setBackgroundResource(R.drawable.s6);
	    }
	    AbsoluteLayout.LayoutParams s1_param=new AbsoluteLayout.LayoutParams(btn_width, btn_height, layout_width/90*1, layout_height/48*2);
	    AbsoluteLayout.LayoutParams s2_param=new AbsoluteLayout.LayoutParams(btn_width, btn_height, layout_width/90*6, layout_height/48*10);
	    AbsoluteLayout.LayoutParams s3_param=new AbsoluteLayout.LayoutParams(btn_width, btn_height, layout_width/90*1, layout_height/48*18);
	    AbsoluteLayout.LayoutParams s4_param=new AbsoluteLayout.LayoutParams(btn_width, btn_height, layout_width/90*6, layout_height/48*26);
	    AbsoluteLayout.LayoutParams s5_param=new AbsoluteLayout.LayoutParams(btn_width, btn_height, layout_width/90*1, layout_height/48*34);
	    AbsoluteLayout.LayoutParams s6_param=new AbsoluteLayout.LayoutParams(btn_width, btn_height, layout_width/90*6, layout_height/48*42);
	    AbsoluteLayout btn_layout=new AbsoluteLayout(this);
	    btn_layout.setBackgroundColor(Color.BLACK);
	    LinearLayout.LayoutParams layout_Param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, layout_height);
	    
	    btn_layout.addView(s1, s1_param);
	    btn_layout.addView(s2, s2_param);
	    btn_layout.addView(s3, s3_param);
	    btn_layout.addView(s4, s4_param);
	    btn_layout.addView(s5, s5_param);
	    btn_layout.addView(s6, s6_param);
	    setContentView(btn_layout, layout_Param);
	    
	    graphic(true);
	    
	    s1.setOnClickListener(this);
	    s2.setOnClickListener(this);
	    s3.setOnClickListener(this);
	    s4.setOnClickListener(this);
	    s5.setOnClickListener(this);
	    if(Common.id!=null)
	    s6.setOnClickListener(this);
	    
	}
	public void onBackPressed() {
		if(!Flag){
			Flag=true;
			graphic(false);
			Handler handler=new Handler(){
				public void handleMessage(android.os.Message message){
					Flag=false;
					finish();
					overridePendingTransition(R.anim.activity_stop,R.anim.activity_out);
				}
			};
			handler.sendEmptyMessageDelayed(0, 500);
		}
	}
	@Override
	public void onClick(View v) {
		int p_n=(Integer) v.getTag();
		
		switch(p_n){
		case 1:
			startActivity(new Intent(Story.this, Intro_Issue.class));
			break;
		case 2:
			startActivity(new Intent(Story.this, Intro_Dooit.class));
			break;
		case 3:
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=두잇서베이(DOOITSURVEY)")));
			break;
		case 4:
			String message ="이슈와 뉴스를\n\"내 손으로 직접!\"\n"
					+"가입시 추천인 아이디 \n[ "+Common.id+" ]를 입력하시면 \n추천인 300P 가\n즉시 적립됩니다.\n";
			    String referenceURLString = "https://market.android.com/details?hl=ko&id=com.DooitLocalResearch";
			    String appVersion = "1.0";
					  try { 
					        ArrayList< Map < String, String > > arrMetaInfo = new ArrayList< Map< String, String > >();
					        Map < String, String > metaInfoAndroid = new Hashtable < String, String >(1);
					        metaInfoAndroid.put("os", "android");
					        metaInfoAndroid.put("devicetype", "phone");
					        metaInfoAndroid.put("installurl", "market://details?id=com.DooitLocalResearch");
					        metaInfoAndroid.put("executeurl", "market://details?id=com.DooitLocalResearch");
					        arrMetaInfo.add(metaInfoAndroid);
					        KakaoLink link = new KakaoLink(this, "www.dooit.co.kr", referenceURLString, appVersion, 
					        		message, "이슈 앤 뉴스폴", arrMetaInfo, "UTF-8");
					        if (link.isAvailable()) {
					        	startActivity(link.getIntent());
					        } else {
					        	Common.nToast(this, "카카오톡이 설치되어 있지 않습니다.");
					        }
					  }catch(UnsupportedEncodingException e){
					  e.printStackTrace(); 
					  }
			break;
		case 5:
			Intent intent=new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:info@dooit.co.kr"));
			intent.putExtra(Intent.EXTRA_SUBJECT, "이슈 앤 뉴스폴 이용자 의견보내기");
			startActivity(intent);
			break;
		case 6:
			Common.deletePreference();
			setResult(Common.m_logout);
			finish();
			startActivity(new Intent(Story.this,Login.class));
			break;
		}
	};
	
	public void graphic(boolean start){
			int r=Animation.RELATIVE_TO_PARENT;
			int a=Animation.ABSOLUTE;
			int f_fo, t_fo;
			float l_fr, l_to, r_fr,r_to;			
			
		if(start){
			f_fo=r;
			t_fo=a;
			l_fr=(float)-1.0;
			l_to=layout_width/90*1;
			r_fr=(float)1.5;
			r_to=layout_width-btn_width-layout_width/90*8;
		}else{
			f_fo=a;
			t_fo=r;
			l_fr=layout_width/90*1;
			l_to=(float)-1.0;
			r_fr=layout_width-btn_width-layout_width/90*8;
			r_to=(float)1.5;
		}
		ani1 = new TranslateAnimation(f_fo,l_fr, t_fo, l_to,a, 0, a,0);//h*2-Common.title,a, h*2-Common.title);
		ani2 = new TranslateAnimation(f_fo,r_fr, t_fo, r_to,a, 0,a, 0);
		ani3 = new TranslateAnimation(f_fo,l_fr, t_fo, l_to,a, 0,a, 0);
		ani4 = new TranslateAnimation(f_fo,r_fr, t_fo, r_to,a, 0,a, 0);
		ani5 = new TranslateAnimation(f_fo,l_fr, t_fo, l_to,a, 0,a, 0);
		ani6 = new TranslateAnimation(f_fo,r_fr, t_fo, r_to,a, 0,a, 0);
		ani1.setStartOffset(200);
		ani1.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
		ani1.setDuration(300);
		ani1.setFillAfter(true);
		ani2.setStartOffset(200);
		ani2.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
		ani2.setDuration(300);
		ani2.setFillAfter(true);
		ani3.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
		ani3.setDuration(300);
		ani3.setFillAfter(true);
		ani3.setStartOffset(200);
		ani4.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
		ani4.setDuration(300);
		ani4.setFillAfter(true);
		ani4.setStartOffset(200);
		ani5.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
		ani5.setDuration(300);
		ani5.setFillAfter(true);
		ani5.setStartOffset(200);
		ani6.setInterpolator(this, android.R.anim.accelerate_decelerate_interpolator);
		ani6.setDuration(300);
		ani6.setFillAfter(true);
		ani6.setStartOffset(200);
		s1.startAnimation(ani1);
		s2.startAnimation(ani2);
		s3.startAnimation(ani3);
		s4.startAnimation(ani4);
		s5.startAnimation(ani5);
		s6.startAnimation(ani6);
	}
}
