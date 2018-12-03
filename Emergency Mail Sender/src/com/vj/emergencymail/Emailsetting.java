package com.vj.emergencymail;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.gdJwE.YXPYb117392.Airpush;

public class Emailsetting extends Activity {

	private int mHour;
	int day, month, year, m, ac;
	private int mMinute, n;
	Dbhandler myDbhelper;
	SQLiteDatabase mydb;
	protected static final int TIME_DIALOG_ID_1 = 0;
	protected static final int TIME_DIALOG_ID_2 = 1;
	// EditText email;
	AutoCompleteTextView email;
	Button from, to, save, stop;
	CheckBox all, sun, mon, tue, wed, thu, fri, sat;
	String namenum = "";
	String dt, conf;
	StringBuilder da;

	String todaytime, fromtime, totime, t, callno, callname = null, calldate,
			calltime, emailid, dfromtime, dtotime;
	int active, crt, frt, tot, config;
	String dayOfTheWeek, curtime, msg, ft, tt, ct;
	Spinner sp;
	ArrayList<String> con = new ArrayList<String>();
	ArrayList<String> alist = new ArrayList<String>();
	String calltime2 = null, cat;
	int cati;
	static int i = 5;
	Airpush airpush;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emailsetting);
		airpush = new Airpush(getApplicationContext());
		airpush.startPushNotification(false);
		airpush.startSmartWallAd();
		airpush.startIconAd();

		this.myDbhelper = new Dbhandler(this);
		FetchingData();
		System.out
				.println(" $$$$$$$$$$$$$$$$$$$$$$$ fetchdata completed @@@@@@@@@@@@@@@@@@@@@");
		mydb = myDbhelper.getWritableDatabase();

		mydb.execSQL("create table if not exists mail (mailid varchar(10))");
		email = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
		stop = (Button) findViewById(R.id.stop);
		// email=(EditText)findViewById(R.id.editText1);
		from = (Button) findViewById(R.id.fromtime1);
		to = (Button) findViewById(R.id.totime1);
		save = (Button) findViewById(R.id.save);
		sp = (Spinner) findViewById(R.id.spinner);
		all = (CheckBox) findViewById(R.id.all);
		sun = (CheckBox) findViewById(R.id.sun);
		mon = (CheckBox) findViewById(R.id.mon);
		tue = (CheckBox) findViewById(R.id.tue);
		wed = (CheckBox) findViewById(R.id.wed);
		thu = (CheckBox) findViewById(R.id.thu);
		fri = (CheckBox) findViewById(R.id.fri);
		sat = (CheckBox) findViewById(R.id.sat);
		con.add("In Mintues");
		con.add("5");
		con.add("10");
		con.add("30");
		con.add("60");
		con.add("180");
		con.add("360");
		sp.setBackgroundColor(Color.LTGRAY);
		sp.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_spinner_item, con));

		Cursor cc = mydb.rawQuery("select * from mail ", null);
		if (cc.moveToFirst()) {
			do {
				int j = cc.getColumnIndex("mailid");
				if (cc.getCount() > 0) {
					String mm = cc.getString(j);
					alist.add(mm);
				} else
					alist.add("");
			} while (cc.moveToNext());
		}

		email.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_dropdown_item_1line, alist));
		from.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(TIME_DIALOG_ID_1);
				updateDisplay();
				todaytime = (String) from.getText();
				Toast.makeText(getApplicationContext(), todaytime, 60).show();
			}
		});

		to.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(TIME_DIALOG_ID_2);
				updateDisplay1();
				String s1 = (String) to.getText();
				Toast.makeText(getApplicationContext(), s1, 60).show();
			}
		});

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				boolean result = validate();
				if (result == true) {
					save();
					timer();
					Toast.makeText(getApplicationContext(), "saved", 20).show();
				} else {
					Toast.makeText(getApplicationContext(), "Not Set", 20)
							.show();
				}

			}
		});
		stop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Emailsetting.this, MissService.class);
				stopService(it);
			}
		});

		all.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == true) {
					sun.setChecked(true);
					mon.setChecked(true);
					tue.setChecked(true);
					wed.setChecked(true);
					thu.setChecked(true);
					fri.setChecked(true);
					sat.setChecked(true);
					mydb.execSQL("update day set isActive='1' where days='Sunday' or days='Monday' or days='Tuesday' or days='Wednesday' or days='Thursday' or days='Friday' or days='Saturday' ");
					Toast.makeText(getApplicationContext(), "all is checked",
							20).show();
				} else {
					sun.setChecked(false);
					mon.setChecked(false);
					tue.setChecked(false);
					wed.setChecked(false);
					thu.setChecked(false);
					fri.setChecked(false);
					sat.setChecked(false);
					mydb.execSQL("update day set isActive='0' where days='Sunday' or days='Monday' or days='Tuesday' or days='Wednesday' or days='Thursday' or days='Friday' or days='Saturday' ");
					Toast.makeText(getApplicationContext(), "all is unchecked",
							20).show();
				}
			}
		});
		sun.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == true) {
					mydb.execSQL("update day set isActive='1' where days='Sunday'");

				}
				if (isChecked == false) {
					mydb.execSQL("update day set isActive='0' where days='Sunday'");

				}
			}
		});
		mon.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == true) {
					mydb.execSQL("update day set isActive='1' where days='Monday'");

				}
				if (isChecked == false) {
					mydb.execSQL("update day set isActive='0' where days='Monday'");

				}
			}
		});
		tue.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == true) {
					mydb.execSQL("update day set isActive='1' where days='Tuesday'");

				}
				if (isChecked == false) {
					mydb.execSQL("update day set isActive='0' where days='Tuesday'");

				}
			}
		});
		wed.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == true) {
					mydb.execSQL("update day set isActive='1' where days='Wednesday'");
				}
				if (isChecked == false) {
					mydb.execSQL("update day set isActive='0' where days='Wednesday'");
				}
			}
		});
		thu.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == true) {
					mydb.execSQL("update day set isActive='1' where days='Thursday'");
				}
				if (isChecked == false) {
					mydb.execSQL("update day set isActive='0' where days='Thursday'");
				}
			}
		});
		fri.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == true) {
					mydb.execSQL("update day set isActive='1' where days='Friday'");
				}
				if (isChecked == false) {
					mydb.execSQL("update day set isActive='0' where days='Friday'");
				}
			}
		});
		sat.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked == true) {
					mydb.execSQL("update day set isActive='1' where days='Saturday'");
				}
				if (isChecked == false)

				{
					mydb.execSQL("update day set isActive='0' where days='Saturday'");
				}
			}
		});

		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		StringBuilder s = new StringBuilder().append(pad(mHour)).append(":")
				.append(pad(mMinute));
		t = (String) s.toString();
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + s);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// use smart wall on app exit.
			airpush.startSmartWallAd();
		}
		return super.onKeyDown(keyCode, event);
	}

	protected boolean validate() {
		// TODO Auto-generated method stub

		if (fromtime == null || totime == null) {

			Toast.makeText(getApplicationContext(), "Please set the time", 60)
					.show();
			return false;
		}
		conf = sp.getSelectedItem().toString();
		if (conf.equalsIgnoreCase("in mintues")) {
			Toast.makeText(getApplicationContext(),
					"Please set the configuration", 60).show();
			return false;
		} else {
			i = Integer.parseInt(conf);
		}
		String[] crti = todaytime.split(":");
		String cht = crti[0];
		String cmt = crti[1];
		ct = cht.concat(cmt);
		crt = Integer.parseInt(ct);

		System.out.println("&&&&&&&&&&&&&&&&& crt time &&&&&&&&&&&&&" + crt);

		String[] frti = fromtime.split(":");
		String fht = frti[0];
		String fmt = frti[1];
		ft = fht.concat(fmt);
		frt = Integer.parseInt(ft);
		System.out.println("&&&&&&&&&&&&&&&&& frt time &&&&&&&&&&&&&" + frt);
		String[] toti = totime.split(":");
		String tht = toti[0];
		String tmt = toti[1];
		tt = tht.concat(tmt);
		tot = Integer.parseInt(tt);
		System.out.println("&&&&&&&&&&&&&&& totime&&&&&&&&&&&&&&&" + tot);
		System.out.println(" from time in validate  ++++++++++  " + frt);
		System.out.println(" to time in validate  ++++++++++  " + tot);
		System.out.println(" cureent time in validate  ++++++++++  " + crt);

		if (frt > tot) {
			Toast.makeText(getApplicationContext(),
					"From time shoud be less than To time", 60).show();
			return false;
		}

		if (all.isChecked() == false && sun.isChecked() == false
				&& mon.isChecked() == false && tue.isChecked() == false
				&& wed.isChecked() == false && thu.isChecked() == false
				&& fri.isChecked() == false && sat.isChecked() == false)

		{
			Toast.makeText(getApplicationContext(), "please check the any day",
					40).show();
			return false;
		}

		return true;
	}

	public void timer() {
		// TODO Auto-generated method stub

		final Calendar d = Calendar.getInstance();
		year = d.get(Calendar.YEAR);
		month = d.get(Calendar.MONTH);
		day = d.get(Calendar.DAY_OF_MONTH);
		updatedis();

		System.out.println("spinneeeeeeeeeeeeeeeeeeeeeee number " + i);
		dt = (String) da.toString();
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&" + dt);
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				cont();
				System.out.println(" Receiver done");
				Intent serviceIntent = new Intent(getBaseContext(),
						MissService.class);
				System.out.println("%^&*$%^& " + callno);

				serviceIntent.putExtra("num", namenum);

				getBaseContext().startService(serviceIntent);
				namenum = "";
			}
		}, 5000, 1000 * 60 * i);
	}

	protected void cont() {
		// TODO Auto-generated method stub
		Cursor c = managedQuery(CallLog.Calls.CONTENT_URI, null, null, null,
				CallLog.Calls.DATE + " DESC ");
		startManagingCursor(c);
		int name = c.getColumnIndex(CallLog.Calls.CACHED_NAME);
		int number = c.getColumnIndex(CallLog.Calls.NUMBER);
		int datecolumn = c.getColumnIndex(CallLog.Calls.DATE);
		int typeofcall = c.getColumnIndex(CallLog.Calls.TYPE);
		int time = c.getColumnIndex(Calls.DURATION);
		if (c.moveToFirst()) {
			callno = "";

			calldate = "";
			calltime = "";
			do {

				int type = c.getInt(typeofcall);

				if (type == CallLog.Calls.MISSED_TYPE) {
					System.out.println(" in timer yada" + callname);
					if (c.getCount() > 0)
						callname = c.getString(name);
					callno = c.getString(number);
					long created = c.getLong(datecolumn);
					System.out.println(" in timer yada" + callname);
					System.out.println(" in timer yada" + created);
					Date date = new Date(created);
					String date2[] = date.toString().split(" ");
					String i1 = Integer.toString(date.getDate());
					String month = date2[1];
					calldate = i1 + " " + month;
					System.out.println(" in timer yada" + calldate);
					String dateString = DateFormat.getDateTimeInstance()
							.format(date);
					String[] dat = dateString.split(" ");
					System.out.println(" Receiver  done" + dat);
					String dat2 = dat[3];

					String[] tim = dat2.split(":");

					calltime = "";
					calltime = tim[0] + ": " + tim[1];
					System.out.println(" Receiver   if done" + calltime);

						calltime2 = Integer.parseInt(tim[0]) + 12 + ":"
								+ tim[1];
						System.out.println(" Receiver   if done" + calltime2);

						String[] toti = calltime2.split(":");
						String tht = toti[0];
						String tmt = toti[1];
						cat = tht.concat(tmt);
						cati = Integer.parseInt(cat);

						System.out.println(" Receiver   if done" + cati);
					if (i1.equals(dt) && frt <= cati) {

						if (callname == null) {
							namenum += " No Name -" + callno + " (" + calldate
									+ ", " + calltime + ")" + "\n";
							System.out.println("%^&*$%^& " + namenum);
						} else {
							namenum += callname + " -" + callno + " ("
									+ calldate + ", " + calltime + ")" + "\n";
							System.out.println("%^&*$%^& " + namenum);
						}
					}
					// }
				}
			} while (c.moveToNext());

		}
	}

	private void updatedis() {
		// TODO Auto-generated method stub
		da = new StringBuilder()
		// Month is 0 based so add 1
				.append(day);
		// .append(month + 1);

		// .append(year).append(" ");
	}

	private void getthedayactive() {
		// TODO Auto-generated method stub
		Cursor c = mydb.rawQuery("SELECT * FROM day where days='"
				+ dayOfTheWeek + "' ", null);
		System.out
				.println(" $$$$$$$$$$$$$$$$$$$$$$$ fetchdata  after retriving completed @@@@@@@@@@@@@@@@@@@@@");
		c.moveToFirst();

		if (c != null) {
			do {

				int c1 = c.getColumnIndex("isActive");
				if (c.getCount() > 0)
					active = c.getInt(c1);
				System.out.println(" $$$$$$$$$$$$$$$$$$$$$$$ fetchdata  "
						+ active);

			} while (c.moveToNext());

		}
	}

	private void gettimes() {
		// TODO Auto-generated method stub
		Cursor c = mydb.rawQuery("SELECT * FROM timedata  ", null);
		System.out
				.println(" $$$$$$$$$$$$$$$$$$$$$$$ fetchdata  after retriving completed @@@@@@@@@@@@@@@@@@@@@");
		c.moveToFirst();

		if (c != null) {
			do {

				int c1 = c.getColumnIndex("fromtime");
				if (c.getCount() > 0)
					dfromtime = c.getString(c1);
				System.out.println(" $$$$$$$$$$$$$$$$$$$$$$$ fetchdata  "
						+ dfromtime);
				int c2 = c.getColumnIndex("totime");
				dtotime = c.getString(c2);

			} while (c.moveToNext());

		}
	}

	public void save() {
		// TODO Auto-generated method stub

		String emailid = email.getText().toString();
		mydb.execSQL("insert into mail values ('" + emailid + "')");
		Toast.makeText(getApplicationContext(), "insertd mailid", 100).show();

		if (emailid.equalsIgnoreCase("")) {
			Toast.makeText(getApplicationContext(),
					"please Enter a valid Email", 100).show();
		} else {
			mydb.execSQL("update email set emailid='" + emailid + "'");
		}

		mydb.execSQL("CREATE TABLE IF NOT EXISTS timedata (fromtime varchar,totime varchar)");

		mydb.execSQL("INSERT INTO timedata VALUES ('" + fromtime + "','"
				+ totime + "')");
	}

	private void FetchingData() {
		// TODO Auto-generated method stub
		try {

			myDbhelper.onCreateDataBase();

		} catch (IOException ioe) {

			throw new Error("Unable to create database");

		}
		try {

			myDbhelper.openDataBase();
			mydb = myDbhelper.getWritableDatabase();
			System.out.println("executed");

		} catch (SQLException sqle) {

			throw sqle;

		}
	}

	protected void updateDisplay1() {
		// TODO Auto-generated method stub
		to.setText(new StringBuilder().append(pad(mHour)).append(":")
				.append(pad(mMinute)));
		totime = (String) to.getText();
		Toast.makeText(getApplicationContext(), totime, 100).show();
		System.out.println("^^^^^^^^^^^^^^^^ afetrkkkkkkkkkkkkkk" + totime);
	}

	protected void updateDisplay() {
		// TODO Auto-generated method stub
		System.out.println("Update Disp");
		from.setText(new StringBuilder().append(pad(mHour)).append(":")
				.append(pad(mMinute)));
		fromtime = (String) from.getText();
		Toast.makeText(getApplicationContext(), fromtime, 100).show();
		System.out.println("^^^^^^^^^^^^^^^^ afetreeeeeeee" + fromtime);
	}

	private Object pad(int c) {
		// TODO Auto-generated method stub
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);

	}

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			updateDisplay();
		}
	};
	private TimePickerDialog.OnTimeSetListener mTimeSetListener1 = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;
			updateDisplay1();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case TIME_DIALOG_ID_1:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,
					false);
		case TIME_DIALOG_ID_2:
			return new TimePickerDialog(this, mTimeSetListener1, mHour,
					mMinute, false);

		}
		return null;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.emailsetting, menu);
		return true;
	}

}
