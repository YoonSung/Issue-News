package com.DooitResearch.issueNnews;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;

public class Intro_Issue extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    int w=(int)Common.d_Width;
	    ScrollView sv=new ScrollView(this);
	    LinearLayout lay=new LinearLayout(this);
	    LinearLayout.LayoutParams lay_param=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.FILL_PARENT);
	    lay.setGravity(Gravity.CENTER);
	    ImageView iv=new ImageView(this);
	    iv.setBackgroundResource(R.drawable.about_issue);
	    LinearLayout.LayoutParams iv_param=new LinearLayout.LayoutParams(w,w*3);
	    lay.addView(iv, iv_param);
	    sv.addView(lay,lay_param);
	    setContentView(sv);
	}

}
