package com.superGao.util;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceStatusUtils {

	public static boolean isServiceRunning(Context ctx, String name) {
		// 活动管理器
		ActivityManager am = (ActivityManager) ctx
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> runningServices = am.getRunningServices(100);// 获取当前运行的所有服务,
																				// 最多返回100条

		for (RunningServiceInfo runningServiceInfo : runningServices) {
			String serviceName = runningServiceInfo.service.getClassName();// 获取服务名称
			if (serviceName.equals(name)) {// 服务存在
				return true;
			}
		}

		return false;
	}
}
