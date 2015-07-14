package com.superGao.phoneaddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.superGao.service.AddressService;
import com.superGao.ui.SettingItemView;
import com.superGao.util.PrefUtils;
import com.superGao.util.ServiceStatusUtils;
import com.superGao.view.SettingClickView;

/**
 * 设置中心
 * 
 * @author gao
 * 
 */
public class PhoneAddressActivity extends Activity {

	private SettingItemView sivAddress;

	private SettingClickView scvStyle;
	private SettingClickView scvLocation;

	final String[] mItems = new String[] { "半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_address);
		initAddress();
		initAddressStyle();
		initAddressLocation();
		// 拷贝归属地数据库
		copyDb("address.db");

		// 检查电话归属地是否开启
		boolean result = PrefUtils.getBoolean("phoneAddress", true,
				getApplicationContext());
		Intent service = new Intent(getApplicationContext(),
				AddressService.class);
		if (result) {
			//sivAddress.setChecked(true);
			sivAddress.setDesc("已开启来电归属地显示");
			PrefUtils.putBoolean("phoneAddress", true, getApplicationContext());
			startService(service);// 开启归属地服务
		} else {
			sivAddress.setChecked(false);
			sivAddress.setDesc("已关闭来电归属地显示");
			PrefUtils
					.putBoolean("phoneAddress", false, getApplicationContext());
			stopService(service);// 关闭归属地服务
		}

	}

	/**
	 * 拷贝数据库
	 */
	private void copyDb(String dbName) {
		AssetManager assets = getAssets();// 获取asset管理器
		File filesDir = getFilesDir();// 获取项目路径
		File desFile = new File(filesDir, dbName);

		if (desFile.exists()) {
			System.out.println("数据库" + dbName + "已存在,无需拷贝!");
			return;
		}

		InputStream in = null;
		FileOutputStream out = null;

		try {
			in = assets.open(dbName);// 打开assets目录的文件
			out = new FileOutputStream(desFile);

			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("拷贝数据库" + dbName + "成功!");
	}

	/**
	 * 初始化归属地位置
	 */
	private void initAddressLocation() {
		scvLocation = (SettingClickView) findViewById(R.id.scv_location);
		scvLocation.setTitle("归属地提示框位置");
		scvLocation.setDesc("设置归属地提示框位置");
		scvLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 跳转到修改归属地位置的页面
				startActivity(new Intent(getApplicationContext(),
						DragViewActivity.class));
			}
		});
	}

	/**
	 * 初始化归属地风格
	 */
	private void initAddressStyle() {
		scvStyle = (SettingClickView) findViewById(R.id.scv_style);
		scvStyle.setTitle("归属地提示框风格");

		int which = PrefUtils.getInt("address_style", 0, this);
		scvStyle.setDesc(mItems[which]);

		scvStyle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showChooseDialog();
			}
		});
	}

	/**
	 * 选择归属地弹窗
	 */
	protected void showChooseDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("归属地提示框风格");
		builder.setIcon(R.drawable.ic_launcher);

		int which = PrefUtils.getInt("address_style", 0, this);
		builder.setSingleChoiceItems(mItems, which,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.out.println("which=" + which);
						// 保存风格
						PrefUtils.putInt("address_style", which,
								getApplicationContext());
						scvStyle.setDesc(mItems[which]);// 更新描述
						dialog.dismiss();
					}
				});

		builder.setNegativeButton("取消", null);
		builder.show();
	}

	/**
	 * 初始化归属地
	 */
	private void initAddress() {
		sivAddress = (SettingItemView) findViewById(R.id.siv_showAddress);
		sivAddress.setTitle("电话归属地设置");
		// 根据服务是否运行来更新checkbox
		boolean serviceRunning = ServiceStatusUtils.isServiceRunning(this,
				"com.itcast.mobilesafe01.service.AddressService");
		sivAddress.setChecked(serviceRunning);

		sivAddress.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent service = new Intent(getApplicationContext(),
						AddressService.class);

				if (sivAddress.isChecked()) {
					sivAddress.setChecked(false);
					sivAddress.setDesc("已关闭电话归属地显示");
					PrefUtils.putBoolean("phoneAddress", false,
							getApplicationContext());
					stopService(service);// 关闭归属地服务

				} else {
					sivAddress.setChecked(true);
					sivAddress.setDesc("已开启电话归属地显示");
					PrefUtils.putBoolean("phoneAddress", true,
							getApplicationContext());
					startService(service);// 开启归属地服务
				}
			}
		});
	}

	/**
	 * 查询归属地的点击事件
	 */
	public void numberQuery(View view) {
		startActivity(new Intent(this, AddressQueryActivity.class));
		// 页面切换动画
		overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
	}

}
