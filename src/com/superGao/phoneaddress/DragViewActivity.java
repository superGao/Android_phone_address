package com.superGao.phoneaddress;

import com.superGao.util.PrefUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 归属地位置修改
 * 
 * 设置半透明效果: android:theme="@android:style/Theme.Translucent.NoTitleBar"
 * 
 * @author gao
 * 
 */
public class DragViewActivity extends Activity {

	private ImageView ivDrag;

	private int startX;
	private int startY;

	private int mScreenWidth;
	private int mScreenHeight;

	private TextView tvTop;

	private TextView tvBottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag_view);

		tvTop = (TextView) findViewById(R.id.tv_top);
		tvBottom = (TextView) findViewById(R.id.tv_bottom);

		WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		mScreenWidth = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		mScreenHeight = wm.getDefaultDisplay().getHeight();// 屏幕高度

		ivDrag = (ImageView) findViewById(R.id.iv_drag);

		int lastX = PrefUtils.getInt("lastX", 0, this);// left
		int lastY = PrefUtils.getInt("lastY", 0, this);// top

		// 根据布局位置,更新提示框位置
		if (lastY > mScreenHeight / 2) {// 屏幕下方
			tvTop.setVisibility(View.VISIBLE);
			tvBottom.setVisibility(View.INVISIBLE);
		} else {
			tvTop.setVisibility(View.INVISIBLE);
			tvBottom.setVisibility(View.VISIBLE);
		}

		System.out.println("lastX:" + lastX);
		System.out.println("lastY:" + lastY);

		// measure(测量)->layout(设置位置)->onDraw(绘制), 这些方法必须在oncreate方法执行完了之后才会调用
		// ivDrag.layout(lastX, lastY, lastX + ivDrag.getWidth(),
		// lastY + ivDrag.getHeight());//此处不能直接修改布局位置,因为布局还没有开始绘制

		// 获取当前控件的布局参数
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivDrag
				.getLayoutParams();// 父控件就谁,就拿谁定义的布局参数
		// 临时修改布局参数
		params.topMargin = lastY;
		params.leftMargin = lastX;
		// System.out.println("topMargin=" + topMargin);
		// 设置触摸监听
		ivDrag.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:// 按下动作
					// System.out.println("按下");

					// 记录起点坐标
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:// 移动动作
					// System.out.println("移动");

					// 获取移动后坐标
					int endX = (int) event.getRawX();
					int endY = (int) event.getRawY();

					// 计算偏移量
					int dx = endX - startX;
					int dy = endY - startY;

					// 根据偏移量,更新位置
					int l = ivDrag.getLeft() + dx;
					int t = ivDrag.getTop() + dy;
					int r = ivDrag.getRight() + dx;
					int b = ivDrag.getBottom() + dy;

					// 避免布局 超出屏幕边界
					if (l < 0 || r > mScreenWidth) {
						return true;
					}
					// 避免布局 超出屏幕边界
					if (t < 0 || b > mScreenHeight - 20) {// 减掉状态栏高度
						return true;
					}

					// 根据布局位置,更新提示框位置
					if (t > mScreenHeight / 2) {// 屏幕下方
						tvTop.setVisibility(View.VISIBLE);
						tvBottom.setVisibility(View.INVISIBLE);
					} else {
						tvTop.setVisibility(View.INVISIBLE);
						tvBottom.setVisibility(View.VISIBLE);
					}

					ivDrag.layout(l, t, r, b);

					// 重新初始化起点坐标
					startX = (int) event.getRawX();
					startY = (int) event.getRawY();
					break;
				case MotionEvent.ACTION_UP:// 抬起动作
					// System.out.println("抬起");
					// 记录最终坐标点
					PrefUtils.putInt("lastX", ivDrag.getLeft(),
							getApplicationContext());
					PrefUtils.putInt("lastY", ivDrag.getTop(),
							getApplicationContext());
					break;

				default:
					break;
				}

				return true;// 返回true表示消费此事件, 事件不会再进行传递
			}
		});
	}
}
