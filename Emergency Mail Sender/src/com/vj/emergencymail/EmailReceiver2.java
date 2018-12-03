package com.vj.emergencymail;

import java.util.Timer;
import java.util.TimerTask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EmailReceiver2  extends BroadcastReceiver{

	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
			
			
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() 
            {
                public void run() 
                {
                System.out.println(" Receiver done");
               Emailsetting em=new Emailsetting();
    			em.timer();
                
          /* Intent serviceIntent = new Intent(context,MissService.class);
                context.startService(serviceIntent);*/
                }
            }, 5000, 1000 *60*1);
           } 
	}

	protected void timer() {
		// TODO Auto-generated method stub
		
	}

}
