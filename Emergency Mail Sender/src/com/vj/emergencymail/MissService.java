package com.vj.emergencymail;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class MissService extends Service {
	SQLiteDatabase mydb;
	String today;
	Dbhandler myDbhelper;
	String todaytime,fromtime,totime,t,callno,callname,emailid,dfromtime,dtotime;
	int active,crt,frt,tot;
	String dayOfTheWeek,curtime,msg,ft,tt,ct;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		
		  
		final Calendar ca = Calendar.getInstance();
		int mHour = ca.get(Calendar.HOUR_OF_DAY);
		int mMinute = ca.get(Calendar.MINUTE);
		StringBuilder s1=new StringBuilder().append(pad(mHour)).append(":").append(pad(mMinute));
		
	   todaytime=(String)s1.toString();
		
		super.onCreate();
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
	 	
	 	}catch(SQLException sqle){
	 
	 		throw sqle;
	 
	 	}
	}

	private Object pad(int ca) {
		// TODO Auto-generated method stub
		if (ca >= 10)
			return String.valueOf(ca);
		else
			return "0" + String.valueOf(ca);

		
	}
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
	
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		this.myDbhelper=new Dbhandler(this);
		FetchingData();
		
     	mydb=myDbhelper.getWritableDatabase();
		String num = null,date=null,time=null;
		Cursor c=mydb.rawQuery("select * from email", null);
    	c.moveToFirst();
    	if(c!=null)
    	{
    		do
    		{
    			int c1=c.getColumnIndex("emailid");
    			if(c.getCount()>0)
    			emailid=c.getString(c1);
    			System.out.println(" $$$$$$$$$$$$$$$$$$$  "+emailid);
    		}while(c.moveToNext());
    	}
		Bundle bundle = intent.getExtras();
		System.out.println("!@#$%!@#$%^&*()_!@#$%^&*()_        vijay @#$%^&*("+bundle.size());
		System.out.println("!@#$%!@#$%^&*()_!@#$%^&*()_        vijay @#$%^&*("+bundle);
	
		if(bundle.equals(null))
		{
			System.out.println("no strings 12345678");
		}
		else
		{
			 num=bundle.getString("num");
			
			 time=bundle.getString("time");
			 System.out.println("%^&*   in service  $%^& "+num);
			
		}
			if(num.equals("")||num.equals(null))
			{
				num="no calls";
				 System.out.println("%^&*   in service  $%^& "+num);
				
			}
			else
			{
				num=num;
			}
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    	Date d = new Date();
         today = sdf.format(d);
         Toast.makeText(getApplicationContext(), today, 100).show();
         getthedayactive();
         
         if(active==1)
         {
         gettimes();
         
        
         String[] crti=todaytime.split(":");
         String cht=crti[0];
         String cmt=crti[1];
         ct=cht.concat(cmt);
         crt=Integer.parseInt(ct);
         
         System.out.println("&&&&&&&&&&&&&&&&& crt time &&&&&&&&&&&&&"+crt);
         
         String[] frti=dfromtime.split(":");
         String fht=frti[0];
         String fmt=frti[1];
         ft=fht.concat(fmt);
         frt=Integer.parseInt(ft);
         System.out.println("&&&&&&&&&&&&&&&&& frt time &&&&&&&&&&&&&"+frt);
         String[] toti=dtotime.split(":");
         String tht=toti[0];
         String tmt=toti[1];
         tt=tht.concat(tmt);
         tot=Integer.parseInt(tt);
         System.out.println("&&&&&&&&&&&&&&& totime&&&&&&&&&&&&&&&"+tot);
         if((frt<=crt)&&(crt<=tot))
              {
        	 GMailSender sender = new GMailSender("alert4rm.ems@gmail.com", "bmaczlove1143");
	        try {
	        	
	        	
	        	System.out.println("$$#$%^ in try %^&**");
				sender.sendMail(" Ping Me - Notification. ",   
				        "Hi Greeting from EMS(Emergency Mail Sender)Team,"+"\n"+
				"You got a call from: " +"\n"+num  
				        +"\n" 
				        		+"\n" +"Thanks,"+ "\n"+
				        		"--EMS Team."+ "\n"+
				        		"support at: bmaczlove@gmail.com "+ "\n"+
				        		"developed by:bmacz-bindhu-kiran-lavanya"+ "\n"+
				        		"Contact: +917097631143 (INDIA)",   
				        "bmaczlove@gmail.com",   
				        emailid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
              }
		
         }
         return super.onStartCommand(intent, flags, startId);
	}

	private void gettimes() {
		// TODO Auto-generated method stub
		Cursor c=mydb.rawQuery("SELECT * FROM timedata  ", null);
		   System.out.println(" $$$$$$$$$$$$$$$$$$$$$$$ fetchdata  after retriving completed @@@@@@@@@@@@@@@@@@@@@");
		   c.moveToFirst();
		 
		  if(c!=null)
		   {
			   do{
				  
				   int c1=c.getColumnIndex("fromtime");
				   if(c.getCount()>0)
				   dfromtime=c.getString(c1);
				   System.out.println(" $$$$$$$$$$$$$$$$$$$$$$$ fetchdata  "+dfromtime);
				   int c2=c.getColumnIndex("totime");
				   dtotime=c.getString(c2);
				   
			   }while(c.moveToNext());
			 c.close();
			 mydb.close();
		   }
	}

	private void getthedayactive() {
		// TODO Auto-generated method stub
		 Cursor c=mydb.rawQuery("SELECT * FROM day where days='"+today+"' ", null);
		   System.out.println(" $$$$$$$$$$$$$$$$$$$$$$$ fetchdata  after retriving completed @@@@@@@@@@@@@@@@@@@@@");
		   c.moveToFirst();
		 
		  if(c!=null)
		   {
			   do{
				  
				   int c1=c.getColumnIndex("isActive");
				   if(c.getCount()>0)
				   active=c.getInt(c1);
				   System.out.println(" $$$$$$$$$$$$$$$$$$$$$$$ fetchdata  "+active);
				   
			   }while(c.moveToNext());
			  
		   }
	}
	


}
