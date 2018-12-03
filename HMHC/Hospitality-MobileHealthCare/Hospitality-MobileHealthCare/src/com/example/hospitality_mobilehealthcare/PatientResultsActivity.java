package com.example.hospitality_mobilehealthcare;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class PatientResultsActivity extends Activity {
	String s;
	TextView tproblem,tmedicines;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patient_results);
		 tproblem=(TextView)findViewById(R.id.textView1);
		 tmedicines=(TextView)findViewById(R.id.TextView01);
		 s=getIntent().getStringExtra("data");
		  AsynchPatientResults an=new AsynchPatientResults(PatientResultsActivity.this);
			an.execute();
	}

	public void showData(String result) {
		// TODO Auto-generated method stub
		
	    String[] ar=result.split("@");
		String sprob=ar[0];
		tproblem.setText(sprob);
		String smed=ar[1];
		tmedicines.setText(smed);
		
		
	}

	
}
