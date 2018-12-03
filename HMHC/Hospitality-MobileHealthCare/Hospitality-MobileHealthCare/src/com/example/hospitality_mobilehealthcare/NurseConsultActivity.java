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

public class NurseConsultActivity extends Activity {
	EditText etoken,euser,egender,emob,eage,eaddr,eblood,eprblm;
	String si,stoken,suser,sgender,smob,sage,saddr,sblood,sprob;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nurse_consult);
		etoken=(EditText)findViewById(R.id.EditText02);
		euser=(EditText)findViewById(R.id.editText1);
		egender=(EditText)findViewById(R.id.EditText01);
		emob=(EditText)findViewById(R.id.EditText03);
		eage=(EditText)findViewById(R.id.editText4);
		eaddr=(EditText)findViewById(R.id.EditText04);
		eblood=(EditText)findViewById(R.id.editText5);
		eprblm=(EditText)findViewById(R.id.editText6);
		
		si=getIntent().getStringExtra("data");
		AsynchronousNurseConsult an=new AsynchronousNurseConsult(NurseConsultActivity.this);
		an.execute();
		Button update=(Button)findViewById(R.id.button1);
		Button back=(Button)findViewById(R.id.button2);
		update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 stoken=etoken.getText().toString();
				 suser=euser.getText().toString();
				 sgender=egender.getText().toString();
				 smob=emob.getText().toString();
				 sage=eage.getText().toString();
				 saddr=eaddr.getText().toString();
				 sblood=eblood.getText().toString();
				 sprob=eprblm.getText().toString();
				 AsynchNurseConsultUpdate ah=new AsynchNurseConsultUpdate(NurseConsultActivity.this);
				 ah.execute();
				
			}
		});
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent home=new Intent(NurseConsultActivity.this,PatientlistActivity.class);
				startActivity(home);
				
			}
		});
				
	}

	public void showData(String result) {
		// TODO Auto-generated method stub
		String[] ar=result.split("@");
		String stoken=ar[0];
		etoken.setText(stoken);
		String suser=ar[1];
		euser.setText(suser);
		String sgender=ar[2];
		egender.setText(sgender);
		String smob=ar[3];
		emob.setText(smob);
		String sage=ar[4];
		eage.setText(sage);
		String saddr=ar[5];
		eaddr.setText(saddr);
		String sblood=ar[6];
		eblood.setText(sblood);
		
		
	}

	public void showDataa(String result1) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), result1, Toast.LENGTH_LONG).show();
		
	}
}
