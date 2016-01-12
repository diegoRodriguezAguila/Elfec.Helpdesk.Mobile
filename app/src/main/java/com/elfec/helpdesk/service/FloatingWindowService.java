package com.elfec.helpdesk.service;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.elfec.helpdesk.service.floating_window.AbstractFloatingWindowView;
import com.elfec.helpdesk.service.floating_window.IFloatingWindowService;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FloatingWindowService extends Service implements IFloatingWindowService {

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    private transient boolean mIsWindowShown;
    private WindowManager windowManager;

    private View mRootView;

    private WindowManager.LayoutParams mParams;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /**
     * Pone el touch listener a los campos necesarios
     */
    private void setTouchListener() {
        mParams = new WindowManager.LayoutParams(380,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW|WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        mParams.gravity = Gravity.TOP | Gravity.START;
        mParams.x = 90;
        mParams.y = 100;
        mRootView.setOnTouchListener(new FloatingWindowTouchListener());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Muestra el campo en ventana flotante
     *
     */
    @Override
    public void show(AbstractFloatingWindowView view) {
        if(view==null)
            throw new IllegalArgumentException("view cannot be null dude");
        if (!mIsWindowShown) {
            mRootView = view.getRootView();
            view.bindToService(this);
            setTouchListener();
            windowManager.addView(mRootView, mParams);
        }
        mIsWindowShown = true;
    }

    /**
     * Esconde el campo en ventana flotante
     */
    @Override
    public void hide() {
        if (mRootView != null && mIsWindowShown) {
            windowManager.removeView(mRootView);
            mIsWindowShown = false;
        }
    }

    /**
     * Esconde la ventana flotante y detiene el servicio
     */
    @Override
    public void exit(){
        hide();
        stopSelf();
    }

    /**
     * Devuelve true si la vista ya está mostrada
     *
     * @return true if is shown
     */
    public boolean isWindowShown() {
        return mIsWindowShown;
    }

    //region Inner Classes

    /**
     * Binder para la clase
     *
     * @author drodriguez
     */
    public class LocalBinder extends Binder {
        @NonNull
        public FloatingWindowService getService() {
            return FloatingWindowService.this;
        }
    }

    /**
     * Clase de touch listener para la vista de la
     * ventana flotante
     */
    private class FloatingWindowTouchListener implements View.OnTouchListener {

        private int initialX;
        private int initialY;
        private float initialTouchX;
        private float initialTouchY;

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initialX = mParams.x;
                    initialY = mParams.y;
                    initialTouchX = event.getRawX();
                    initialTouchY = event.getRawY();
                    return true;
                case MotionEvent.ACTION_UP:
                    return true;
                case MotionEvent.ACTION_MOVE:
                    mParams.x = initialX
                            + (int) (event.getRawX() - initialTouchX);
                    mParams.y = initialY
                            + (int) (event.getRawY() - initialTouchY);
                    windowManager.updateViewLayout(mRootView,
                            mParams);
                    return true;
            }
            return false;
        }
    }

    //endregion
}
