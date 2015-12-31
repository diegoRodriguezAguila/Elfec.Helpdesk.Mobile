package com.elfec.helpdesk.service;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.elfec.helpdesk.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FloatingWindowService extends Service {

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    private boolean mIsWindowShown;
    private WindowManager windowManager;
    private View mFloatingEditTextView;

    WindowManager.LayoutParams params;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        inflateViews();
        show();
    }

    @SuppressLint("InflateParams")
    private void inflateViews() {
        mFloatingEditTextView = LayoutInflater.from(
                CalligraphyContextWrapper.wrap(new ContextThemeWrapper(this,
                        R.style.Theme_Elfec_Helpdesk))).inflate(
                R.layout.activity_requirement_approval, null, false);
        AppCompatButton button = (AppCompatButton)mFloatingEditTextView.findViewById(R.id.btn_cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                stopSelf();
            }
        });
        setTouchListener();
    }

    /**
     * Pone el touch listener a los campos necesarios
     */
    private void setTouchListener() {
        params = new WindowManager.LayoutParams(
                370,
                320,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW|WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 90;
        params.y = 100;
        View.OnTouchListener touchListener = new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX
                                + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY
                                + (int) (event.getRawY() - initialTouchY);
                        windowManager.updateViewLayout(mFloatingEditTextView,
                                params);
                        return true;
                }
                return false;
            }
        };

        // this code is for dragging the chat head
        mFloatingEditTextView.setOnTouchListener(touchListener);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Muestra el campo en ventana flotante
     *
     */
    public void show() {
        if (!mIsWindowShown) {
            windowManager.addView(mFloatingEditTextView, params);
        }
        mIsWindowShown = true;
    }

    /**
     * Esconde el campo en ventana flotante
     */
    public void hide() {
        if (mFloatingEditTextView != null && mIsWindowShown) {
            windowManager.removeView(mFloatingEditTextView);
            mIsWindowShown = false;
        }
    }

    /**
     * Devuelve true si la vista ya está mostrada
     *
     * @return true if is shown
     */
    public boolean isWindowShown() {
        return mIsWindowShown;
    }

    /**
     * Binder para la clase
     *
     * @author drodriguez
     */
    public class LocalBinder extends Binder {
        public FloatingWindowService getService() {
            return FloatingWindowService.this;
        }
    }
}
