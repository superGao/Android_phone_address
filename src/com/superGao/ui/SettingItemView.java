package com.superGao.ui;

import com.superGao.phoneaddress.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 设置中心
 * @author gao
 *
 */
public class SettingItemView extends RelativeLayout {
	
	private static final String NAMESPACE="http://schemas.android.com/apk/res/com.superGao.mobilesafe";
	private CheckBox cbStatus;
	private TextView tvContent;
	private TextView tvTitle;
	private String mDescOn;
	private String mDescOff;
	
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}
	
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		
		//获取属性值
		String title=attrs.getAttributeValue(NAMESPACE,"title");
		mDescOn = attrs.getAttributeValue(NAMESPACE,"desc_on");
		mDescOff = attrs.getAttributeValue(NAMESPACE,"desc_off");
		
		setTitle(title);
		setDesc(mDescOff);
	}
	
	public SettingItemView(Context context) {
		super(context);
		initView();
	}
	
	/**
	 * 初始化自定义View
	 */
	private void initView() {
		View view=View.inflate(getContext(),R.layout.ui_setting_item, null);
		//将子布局添加到当前的groupView
		this.addView(view);
		
		tvTitle = (TextView)view.findViewById(R.id.tv_title);
		cbStatus=(CheckBox)view.findViewById(R.id.cb_status);
		tvContent=(TextView)view.findViewById(R.id.tv_content);
	}
	
	
	
	/**
	 * 设置标题
	 */
	public void setTitle(String title){
		tvTitle.setText(title);
	}
	
	/**
	 * 设置文本
	 */
	public void setDesc(String desc){
		tvContent.setText(desc);
	}
	
	/**
	 * 判断复选框是否选中
	 */
	public boolean isChecked(){
		
		return cbStatus.isChecked();
	}
	
	/**
	 * 设置复选框选中状态
	 */
	public void setChecked(boolean isChecked){
		cbStatus.setChecked(isChecked);
		
		if(isChecked){
			setDesc(mDescOn);
		}else{
			setDesc(mDescOff);
		}
	}
	
	
}
