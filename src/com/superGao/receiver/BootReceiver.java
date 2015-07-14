package com.superGao.receiver;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.superGao.service.AddressService;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 开机启动电话归属地应用
		Intent it = new Intent(context, AddressService.class);
		context.startService(it);
		
		boolean isServiceRunning = false;
		if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {

			// 检查Service状态

			ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			
			for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
				String serviceName=service.service.getClassName();
				if("com.superGao.service.AddressService".equals(serviceName)){
					isServiceRunning = true;
				}
			}
			if (!isServiceRunning) {
				Intent i = new Intent(context, AddressService.class);
				context.startService(i);
			}
		}
	}
}
