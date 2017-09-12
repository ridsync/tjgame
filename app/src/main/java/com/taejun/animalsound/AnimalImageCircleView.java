package com.taejun.animalsound;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by arent on 2017. 9. 12..
 */

public class AnimalImageCircleView extends de.hdodenhof.circleimageview.CircleImageView {


    public AnimalImageCircleView(Context context) {
        super(context);
    }

    public AnimalImageCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimalImageCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
