package com.jawnnypoo.physicslayout.sample;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatImageView;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by arent on 2017. 9. 8..
 */

public class TouchImageView extends AppCompatImageView {
    private GestureDetectorCompat gestureDetector;

    public static int streamId;
    SoundPool sound = new SoundPool.Builder()
            .setMaxStreams(10)
            .build();

    public TouchImageView(Context context) {
        super(context);
        init(context);
    }

    public TouchImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TouchImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        final int soundId = sound.load(getContext(), R.raw.cat1, 1);
        gestureDetector = new GestureDetectorCompat(context, new GestureDetector.OnGestureListener() {


            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                streamId = sound.play(soundId, 0.79F, 0.79F, 1, 0, 1.0F);
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
          return false;
    }
}
