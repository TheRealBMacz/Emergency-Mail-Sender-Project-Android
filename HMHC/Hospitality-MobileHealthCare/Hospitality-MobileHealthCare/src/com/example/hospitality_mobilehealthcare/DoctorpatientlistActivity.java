package com.example.hospitality_mobilehealthcare;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DoctorpatientlistActivity extends Activity {
	ListView lv;
	String name;
	ArrayList<String>al=new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_doctorpatientlist);
		lv=(ListView)findViewById(R.id.listView1);
		AsynchronousDocPatList la=new AsynchronousDocPatList(DoctorpatientlistActivity.this);
		la.execute();
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				name=lv.getItemAtPosition(arg2).toString();
				Intent it=new Intent(DoctorpatientlistActivity.this,DoctorConsultActivity.class);
				it.putExtra("data", name);
				startActivity(it);
				
			}
		});
	}

	public void showData(String result) {
		// TODO Auto-generated method stub
		String[] obj=result.split("@");
		for(int i=0;i<obj.length;i++)
		{
			al.add(obj[i]);
		}
		ArrayAdapter<String>ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,al);
		lv.setAdapter(ad);	
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent iti=new Intent(DoctorpatientlistActivity.this,DoctorActivity.class);
		startActivity(iti);
		super.onBackPressed();
	}
}
