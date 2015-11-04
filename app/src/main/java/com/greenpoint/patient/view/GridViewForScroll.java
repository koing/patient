package com.greenpoint.patient.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;


public class GridViewForScroll extends GridView {

    public GridViewForScroll(Context context) {
        super(context);
    }

    public GridViewForScroll(Context context,AttributeSet attrs){
        super(context,attrs);
    }

    public GridViewForScroll(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(5*1024*1024,MeasureSpec.AT_MOST));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
