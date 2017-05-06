package com.example.architecture.bad.myfigurecollection.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Extended ImageView to manage touches.
 */
@SuppressLint("AppCompatCustomView")
public class TouchImageView extends ImageView {

    private static final String TAG = TouchImageView.class.getName();
    private Context context;

    public TouchImageView(Context context) {
        super(context);
        constructor(context);
    }

    public TouchImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        constructor(context);
    }

    public TouchImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        constructor(context);
    }

    public TouchImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        constructor(context);
    }

    private void constructor(Context context) {
        this.context = context;

        setOnTouchListener(createOnTouchListener());
    }

    @SuppressLint("ClickableViewAccessibility")
    private OnTouchListener createOnTouchListener() {
        return (v, event) -> {
            Log.d(TAG, "event type: " + event.getAction());

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_SCROLL:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
            }
            invalidate();
            return true;
        };

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure called!");
    }
}
