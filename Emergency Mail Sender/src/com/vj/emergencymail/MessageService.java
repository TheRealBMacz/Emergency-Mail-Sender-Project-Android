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
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MessageService extends Service{

	SQLiteDatabase mydb;
	String today;
	Dbhandler myDbhelper;
	String todaytime,fromtime,totime,t,callno,callname,emailid,dfromtime,dtotime;
	int active,crt,frt,tot;
	String dayOfTheWeek,curtime,ft,tt,ct,msg;
	Bundle bundle;
	 private static final String TAG = "SMSBroadcastReceiver";
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		 /* Object[] pdus = (Object[])bundle.get("pdus");
          final SmsMessage[] messages = new SmsMessage[pdus.length];
          for (int i = 0; i < pdus.length; i++) {
              messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
              System.out.println("message is.."+messages[i]);
              Log.i(TAG, "Message recieved: " + messages[0].getMessageBody());
               msg=messages[0].getMessageBody();
              System.out.println("message body in service is.."+msg);
              
              if (messages.length > -1) {
                  Log.i(TAG, "Message recieved: " + messages[0].getMessageBody());
              }
          }*/
		super.onStart(intent, startId);
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
	private Object pad(int ca) {
		// TODO Auto-generated method stub
		if (ca >= 10)
			return String.valueOf(ca);
		else
			return "0" + String.valueOf(ca);

		
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String msg=null;
		this.myDbhelper=new Dbhandler(this);
		FetchingData();
		
     	mydb=myDbhelper.getWritableDatabase();
     	
		Bundle bundle = intent.getExtras();
		if(bundle==null)
		{
			System.out.println("no strings 12345678");
		}
		else
		{
			 msg=bundle.getString("msg");
			 //msg=bundle.getString("msg");
			
			 System.out.println("%^&    in service  *$%^& " +msg);
		}
		
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
				        "you have got a new message as" +"\n" + msg 
				         +"\n" +
				        		"\n" +"Thanks,"+ "\n"+
				        		"--EMS Team."+ "\n"+
				        		"support at: bmaczlove@gmail.com "+ "\n"+
				        		"developed by:bmacz-bindhu-kiran-lavanya"+ "\n"+
				        		"Contact: +917097631143 (INDIA)",   
				        "bmaczlove@gmail.com",   
				        emailid);
				System.out.println("$$#$%^ in try %^&**");
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
	

}
