package com.taejun.animalsound;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.jawnnypoo.physicslayout.Physics;
import com.jawnnypoo.physicslayout.PhysicsConfig;
import com.jawnnypoo.physicslayout.PhysicsLayoutParams;
import com.jawnnypoo.physicslayout.PhysicsLayoutParamsProcessor;

/**
 * Typical FrameLayout with some physics added on. Call {@link #getPhysics()} to get the
 * physics component.
 */
public class PhysicsFrameLayout extends FrameLayout {

    private Physics physics;

    public PhysicsFrameLayout(Context context) {
        super(context);
        init(null);
    }

    public PhysicsFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PhysicsFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(21)
    public PhysicsFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setWillNotDraw(false);
        physics = new Physics(this, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        physics.onSizeChanged(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        physics.onLayout(changed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        physics.onDraw(canvas);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return physics.onInterceptTouchEvent(ev);
    }

    boolean isTap = false;
    float downX = 0;
    float downY = 0;

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {

        final int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                isTap = true;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                final float x = event.getX();
                final float y = event.getY();
                if (Math.abs(downX - x) >= 10 || Math.abs(downY - y) >= 10) {
                    isTap = false;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (isTap) {
                    final float x = event.getX();
                    final float y = event.getY();
                    View view = findTopChildUnder((int) x, (int) y);
                    if (view!=null) {
                        AnimalImageView imageView = (AnimalImageView)view;
                        imageView.playSound();
                    }
                    isTap = false;
                }
                break;
            }

        }

        return physics.onTouchEvent(event);
    }

    public View findTopChildUnder(int x, int y) {
        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (x >= child.getX() && x < child.getX() + child.getWidth() &&
                    y >= child.getY() && y < child.getY() + child.getHeight()) {
                return child;
            }
        }
        return null;
    }
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @NonNull
    public Physics getPhysics() {
        return physics;
    }

    private static class LayoutParams extends FrameLayout.LayoutParams implements PhysicsLayoutParams {

        PhysicsConfig config;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            config = PhysicsLayoutParamsProcessor.process(c, attrs);
        }

        @Override
        public PhysicsConfig getConfig() {
            return config;
        }
    }
}
