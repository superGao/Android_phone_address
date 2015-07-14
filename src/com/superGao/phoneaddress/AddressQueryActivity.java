package com.superGao.phoneaddress;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.superGao.db.dao.AddressDao;
/**
 * 查询电话归属地
 * @author gao
 *
 */
public class AddressQueryActivity extends Activity {

	private EditText etNumber;
	private TextView tvResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_query);

		etNumber = (EditText) findViewById(R.id.et_number);
		tvResult = (TextView) findViewById(R.id.tv_result);

		etNumber.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				System.out.println("onTextChanged");
				if (s.length() >= 3) {
					String address = AddressDao.getAddress(s.toString());
					if (!TextUtils.isEmpty(address)) {
						tvResult.setText(address);
					} else {
						tvResult.setText("无结果");
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				System.out.println("beforeTextChanged");
			}

			@Override
			public void afterTextChanged(Editable s) {
				System.out.println("afterTextChanged");
			}
		});
	}

	/**
	 * 开始查询
	 * 
	 * @param view
	 */
	public void startQuery(View view) {
		String number = etNumber.getText().toString();
		if (!TextUtils.isEmpty(number)) {
			System.out.println("查询号码:" + number);

			String address = AddressDao.getAddress(number);
			if (!TextUtils.isEmpty(address)) {
				tvResult.setText(address);
			} else {
				tvResult.setText("无结果");
			}
		} else {// 执行抖动动画
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			etNumber.startAnimation(shake);
			vibrate();
		}
	}

	private void vibrate() {
		Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrator.vibrate(1000);//震动2秒
		//long[] pattern = new long[] { 500, 1000, 500, 1000 };// 先等待0.5秒,再震动1秒,再等待0.5秒,再震动1秒...
		//vibrator.vibrate(pattern, -1);//参2等于-1时,表示不循环,大于等于0时,表示从以上数组的哪个位置开始循环
	}
}
