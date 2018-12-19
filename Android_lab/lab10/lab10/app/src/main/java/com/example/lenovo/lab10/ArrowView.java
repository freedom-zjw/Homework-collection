package com.example.lenovo.lab10;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by lenovo on 2016/12/13.
 */
public class ArrowView extends ImageView {
    private static final float ROTATION_THRESHOLD = 0.5f;
    public ArrowView(Context context) {
        super(context);
    }
    public ArrowView(Context context, AttributeSet attrs) { super(context, attrs); }
    public ArrowView(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle); }

    private float mCurRotation = 0;
    //private void init() {...}
    //public float getCurRotation(){...}
    public void onUpdateRotation(float newRotation) {
        RotateAnimation animation = new RotateAnimation(mCurRotation, newRotation,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);
        startAnimation(animation);
        mCurRotation = newRotation;
    }
    public float getCurRotation() {
        return mCurRotation;
    }
}
