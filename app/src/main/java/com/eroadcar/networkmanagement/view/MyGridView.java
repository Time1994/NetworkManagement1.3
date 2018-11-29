package com.eroadcar.networkmanagement.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author hnbcinfo 自定义GridView控件，解决在ListView
 *         或ScrollView中使用GridView导致GridView显示不全的问题 当前应用：时间轴中，图片显示
 */
public class MyGridView extends GridView {
	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyGridView(Context context) {
		super(context);
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}