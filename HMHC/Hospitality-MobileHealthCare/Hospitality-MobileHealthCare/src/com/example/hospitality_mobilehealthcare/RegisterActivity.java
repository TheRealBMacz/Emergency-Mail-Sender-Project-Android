package com.example.hospitality_mobilehealthcare;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	ArrayList<String>al=new ArrayList<String>();

	EditText eid,euser,epwd,eage,emob,eblood,eaddr;
	RadioButton rmale,rfemale;
	Button btsubmit;
	String sgender="male",sid,suser,spwd,sage,smob,sblood,saddr,s="user";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		     al.add("user");
	        al.add("doctor");
	        al.add("nurse");
	   ArrayAdapter<String>ad=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1,al);
	   final Spinner sp=(Spinner)findViewById(R.id.spinner1);
       sp.setAdapter(ad);
       sp.setOnItemSelectedListener(new OnItemSelectedListener() {
		
		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "please select role!!", Toast.LENGTH_SHORT).show();
			
		}
		
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			s=sp.getItemAtPosition(arg2).toString();
			if(s.equals("user"))
			{
				Toast.makeText(getApplicationContext(),"user selected",100).show();
				s="user";
			}
			else if(s.equals("doctor"))
			{
				Toast.makeText(getApplicationContext(),"doctor selected",100).show();
				s="doctor";
			}
			else if(s.equals("nurse"))
			{
				Toast.makeText(getApplicationContext(),"nurse selected",100).show();
				s="nurse";
			}
			
		}
	});
		 eid=(EditText)findViewById(R.id.editText1);
		euser=(EditText)findViewById(R.id.editText2);
		epwd=(EditText)findViewById(R.id.EditText01);
		eage=(EditText)findViewById(R.id.EditText02);
		emob=(EditText)findViewById(R.id.editText3);
		eblood=(EditText)findViewById(R.id.EditText03);
		eaddr=(EditText)findViewById(R.id.EditText04);
		rmale=(RadioButton)findViewById(R.id.radio0);
		rfemale=(RadioButton)findViewById(R.id.radio1);
		rmale.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sgender="male";
				
			}
		});
		rfemale.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sgender="female";
			}
		});
		
		Button btsubmit=(Button)findViewById(R.id.button1);
		btsubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sid=eid.getText().toString();
				suser=euser.getText().toString();
				spwd=epwd.getText().toString();
				sage=eage.getText().toString();
				smob=emob.getText().toString();
				sblood=eblood.getText().toString();
				saddr=eaddr.getText().toString();
				AsynchronousRegistration ar=new AsynchronousRegistration(RegisterActivity.this);
				ar.execute();
			}
		});
		
	}

	public void showData(String result) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
		Intent it=new Intent(RegisterActivity.this,MainActivity.class);
		startActivity(it);
		
	}
}
