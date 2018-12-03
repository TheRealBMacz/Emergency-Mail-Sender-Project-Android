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
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
	String sname,spassword;
	EditText name,password;
	Spinner sp;
	String s="doctor",ussr;
	ArrayList<String> al=new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sp=(Spinner)findViewById(R.id.spinner1);
		ArrayAdapter<String>ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,al);
		al.add("doctor");
		al.add("nurse");
		al.add("user");
		sp.setAdapter(ad);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"Please select any option",100).show();
			}
			
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
			     s=sp.getItemAtPosition(arg2).toString();
				if(s.equals("doctor"))
				{
					Toast.makeText(getApplicationContext(),"Doctor selected!",100).show();
					s="doctor";
				}
				else if(s.equals("user"))
				{
					Toast.makeText(getApplicationContext(),"User Selected!",100).show();
					s="user";
				}
				else if(s.equals("nurse"))
				{
					Toast.makeText(getApplicationContext(),"Nurse Selected!",100).show();
					s="nurse";
				}
				
				
			}
		});
		Button bt=(Button)findViewById(R.id.button1);
		 name=(EditText)findViewById(R.id.editText1);
		 password=(EditText)findViewById(R.id.editText2);
		
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sname=name.getText().toString();
				spassword=password.getText().toString();
				
				Asynchronouslogin an=new Asynchronouslogin(MainActivity.this);
				an.execute();
				
			}
		});
		Button bt1=(Button)findViewById(R.id.button2);
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent it1=new Intent(MainActivity.this,RegisterActivity.class);
				startActivity(it1);
				
				
			}
		});
	}

	public void showData(String result) {
		// TODO Auto-generated method stub
		{
			if(result.equals("login Successfull")&&s.equals("doctor")) {
			Toast.makeText(getApplicationContext(),result, 100).show();
			Intent show=new Intent(MainActivity.this,DoctorActivity.class);
			show.putExtra("data", sname);
			startActivity(show);		
		}
			else if(result.equals("login Successfull")&&s.equals("user"))
			{
				Toast.makeText(getApplicationContext(),result, 100).show();
				Intent show=new Intent(MainActivity.this,PatientActivity.class);
				show.putExtra("data", sname);
				startActivity(show);
			}
			else if(result.equals("login Successfull")&&s.equals("nurse"))
			{
				Toast.makeText(getApplicationContext(),result, 100).show();
				Intent show=new Intent(MainActivity.this,NurseActivity.class);
				show.putExtra("data", sname);
				startActivity(show);
			}
			else {
				Toast.makeText(getApplicationContext(),result, 100).show();
			}
		}

	}
}

