package com.example.hospitality_mobilehealthcare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NurseprofileActivity extends Activity {
	EditText eusername,epassword,etoken,egender,emobileno,eage,eaddress,ebloodgrp,especialization;
	String suser,spassw,sgender,smobile,sage,saddr,sblood,sspl,stoken,snurse;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurseprofile);
		eusername=(EditText)findViewById(R.id.editText1);
		epassword=(EditText)findViewById(R.id.editText2);
		egender=(EditText)findViewById(R.id.EditText01);
		emobileno=(EditText)findViewById(R.id.EditText03);
		eage=(EditText)findViewById(R.id.editText4);
		etoken=(EditText)findViewById(R.id.EditText02);
		eaddress=(EditText)findViewById(R.id.EditText04);
		ebloodgrp=(EditText)findViewById(R.id.editText5);
		especialization=(EditText)findViewById(R.id.editText6);
		snurse=getIntent().getStringExtra("dataa").toString();
		AsynchNurseDataTransfer ac=new AsynchNurseDataTransfer(NurseprofileActivity.this);
		ac.execute();
		Button btsend=(Button)findViewById(R.id.button1);
		Button btback=(Button)findViewById(R.id.button2);
		btsend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 suser=eusername.getText().toString();
				 spassw=epassword.getText().toString();
				 sgender=egender.getText().toString();
				 smobile=emobileno.getText().toString();
				 sage=eage.getText().toString();
				 saddr=eaddress.getText().toString();
				 sblood=ebloodgrp.getText().toString();
				 stoken=etoken.getText().toString();
				sspl=especialization.getText().toString();
				AsynchronousNurseUpdate ar=new AsynchronousNurseUpdate(NurseprofileActivity.this);
				ar.execute();
			}
		});
           btback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it=new Intent(NurseprofileActivity.this,MainActivity.class);
				Toast.makeText(getApplicationContext(), "Please login Again!", 100).show();
				startActivity(it);
				
			}
		});
		
	}

	public void showData(String result) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
	}

	public void showDataa(String result1) {
		// TODO Auto-generated method stub

		String[] ar=result1.split("@");
		String stoken=ar[0];
		etoken.setText(stoken);
		String suser=ar[1];
		eusername.setText(suser);
		String spass=ar[2];
		epassword.setText(spass);
		String sgender=ar[3];
		egender.setText(sgender);
		String smob=ar[4];
		emobileno.setText(smob);
		String sage=ar[5];
		eage.setText(sage);
		String sblood=ar[6];
		ebloodgrp.setText(sblood);
		String saddr=ar[7];
		eaddress.setText(saddr);
		String sspl=ar[8];
		especialization.setText(sspl);
		
		
		
	}

	
}
