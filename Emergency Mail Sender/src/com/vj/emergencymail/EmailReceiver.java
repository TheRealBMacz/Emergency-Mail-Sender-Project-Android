package com.vj.emergencymail;

import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class EmailReceiver extends BroadcastReceiver{
	 private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	 private static final String TAG = "SMSBroadcastReceiver";
	 
	 public static final Uri SMS_INBOX_CONTENT_URI = Uri.parse("content://sms/inbox");
	 String msg;
	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		if (intent.getAction().equals(SMS_RECEIVED)) {
            final Bundle bundle = intent.getExtras();
            if (bundle != null) {
           	  Log.i(TAG, "Message recieved: ");
            	
                        System.out.println(" Receiver done");
                        
                        
                        Object[] pdus = (Object[])bundle.get("pdus");
                        final SmsMessage[] messages = new SmsMessage[pdus.length];
                        for (int i = 0; i < pdus.length; i++) {
                            messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                            System.out.println("message is.."+messages[i]);
                            Log.i(TAG, "Message recieved: " + messages[0].getMessageBody());
                             msg=messages[0].getMessageBody();
                            System.out.println("message body is.."+msg);
                            
                            if (messages.length > -1) {
                                Log.i(TAG, "Message recieved: " + messages[0].getMessageBody());
                            }
                        }
                        
                        Intent serviceIntent = new Intent(context,MessageService.class);
                        serviceIntent.putExtra("msg", msg);
                        context.startService(serviceIntent);
                       }
			}
        else
        {
       	  Log.i(TAG, "in else... ");
       	 
        }
		
	}

}
