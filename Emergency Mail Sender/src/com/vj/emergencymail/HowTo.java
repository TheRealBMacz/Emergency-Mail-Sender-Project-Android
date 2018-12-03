package com.vj.emergencymail;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class HowTo extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);  
	        setContentView(R.layout.howto);
	        Button b=(Button)findViewById(R.id.startb);
	        b.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent it=new Intent(HowTo.this,Emailsetting.class);
					
					startActivity(it);
				}
			});
	 }
}
