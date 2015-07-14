package com.superGao.view;

import com.superGao.phoneaddress.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 自定义组合控件步骤: 1. 写一个类继承RelativeLayout(ViewGroup) 2. 写组合控件的布局文件 3.
 * 在自定义组合控件中初始化界面, 将布局文件添加到当前的对象中 4. 添加相关方法和自定义属性
 * 
 * 自定义属性步骤: 1. values/attrs.xml 声明自定义的属性 2. 在布局文件中,声明命名空间, 在自定义控件中声明自定义属性 3.
 * 在自定义控件构造方法中获取属性内容
 * 
 * @author Kevin
 * 
 */
public class SettingClickView extends RelativeLayout {

	private TextView tvTitle;
	private TextView tvDesc;

	public SettingClickView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public SettingClickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public SettingClickView(Context context) {
		super(context);
		initView();
	}

	/**
	 * 初始化布局
	 */
	private void initView() {
		View view = View.inflate(getContext(), R.layout.setting_click_item,
				null);// 初始化子布局
		this.addView(view);// 将子布局添加给当前ViewGroup

		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		tvDesc = (TextView) view.findViewById(R.id.tv_desc);
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	/**
	 * 设置描述
	 * 
	 * @param desc
	 */
	public void setDesc(String desc) {
		tvDesc.setText(desc);
	}

}
