package com.elfec.helpdesk.service;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.Binder;
import android.os.IBinder;
import android.support.design.widget.TextInputLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.elfec.helpdesk.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class FloatingWindowService extends Service {

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    private transient boolean mIsWindowShown;
    private WindowManager windowManager;
    private View mRootView;

    @Bind(R.id.txt_requirement_title)
    protected TextView txtRequirementTitle;

    @Bind(R.id.radio_group_action)
    protected RadioGroup radioGroupAction;
    @Bind(R.id.rbtn_approve)
    protected AppCompatRadioButton rbtnApprove;
    @Bind(R.id.rbtn_reject)
    protected AppCompatRadioButton rbtnReject;

    @Bind(R.id.txt_reject_reason)
    protected TextInputLayout txtRejectReason;

    @Bind(R.id.btn_cancel)
    protected AppCompatButton btnCancel;
    @Bind(R.id.btn_proceed)
    protected AppCompatButton btnProceed;

    private WindowManager.LayoutParams mParams;

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        inflateViews();
        show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @SuppressLint("InflateParams")
    private void inflateViews() {
        mRootView = LayoutInflater.from(new ContextThemeWrapper(this,
                        R.style.Theme_Elfec_Helpdesk)).inflate(
                R.layout.activity_requirement_approval, null, false);
        ButterKnife.bind(this, mRootView);
        setButtonsClickListeners();
        setTouchListener();
        setFontToAppcompatViews();
    }

    /**
     * Pone la custom font a las vistas appcompat a las cuales no se les aplica por alguna razon
     * la fuente de forma automática
     */
    private void setFontToAppcompatViews() {
        Typeface font = TypefaceUtils.load(getAssets(),
                "fonts/helvetica_neue_roman.otf");
        if(txtRejectReason.getEditText() != null)
            txtRejectReason.getEditText().setTypeface(font);
        txtRejectReason.setTypeface(font);
        rbtnApprove.setTypeface(font);
        rbtnReject.setTypeface(font);
        btnCancel.setTypeface(font);
        btnProceed.setTypeface(font);
    }

    /**
     * Pone los click listeners se los botones
     */
    private void setButtonsClickListeners() {
        radioGroupAction.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case (R.id.rbtn_approve):
                        btnProceed.setText(R.string.btn_approve);
                        txtRejectReason.setVisibility(View.GONE);
                        break;
                    case (R.id.rbtn_reject):
                        btnProceed.setText(R.string.btn_reject);
                        txtRejectReason.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                stopSelf();
            }
        });
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
                stopSelf();
            }
        });
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
        };

        mRootView.setOnTouchListener(touchListener);
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
            windowManager.addView(mRootView, mParams);
        }
        mIsWindowShown = true;
    }

    /**
     * Esconde el campo en ventana flotante
     */
    public void hide() {
        if (mRootView != null && mIsWindowShown) {
            windowManager.removeView(mRootView);
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
