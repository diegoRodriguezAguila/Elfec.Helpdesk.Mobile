package com.elfec.helpdesk.view.floating;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.elfec.helpdesk.R;
import com.elfec.helpdesk.helpers.ui.ButtonClicksHelper;
import com.elfec.helpdesk.presenter.RequirementApprovalPresenter;
import com.elfec.helpdesk.presenter.views.IRequirementApprovalView;
import com.elfec.helpdesk.service.floating_window.AbstractFloatingWindowView;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * Vista para ventana flotante de aprovacion de requerimientos
 */
public class RequirementApproval extends AbstractFloatingWindowView implements IRequirementApprovalView {

    private View mRootView;

    private RequirementApprovalPresenter presenter;

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

    public RequirementApproval(Context context) {
        super(context);
        inflateViews();
        presenter = new RequirementApprovalPresenter(this);
    }

    @SuppressLint("InflateParams")
    private void inflateViews() {
        mRootView = LayoutInflater.from(new ContextThemeWrapper(getContext(),
                R.style.Theme_Elfec_Helpdesk)).inflate(
                R.layout.activity_requirement_approval, null, false);
        ButterKnife.bind(this, mRootView);
        setButtonsClickListeners();
        setFontToAppcompatViews();
    }

    /**
     * Pone la custom font a las vistas appcompat a las cuales no se les aplica por alguna razon
     * la fuente de forma automática
     */
    private void setFontToAppcompatViews() {
        Typeface font = TypefaceUtils.load(getContext().getAssets(),
                "fonts/helvetica_neue_roman.otf");
        if (txtRejectReason.getEditText() != null)
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
                if (ButtonClicksHelper.canClickButton()) {
                    getService().exit();
                }
            }
        });
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ButtonClicksHelper.canClickButton()) {
                    if (rbtnApprove.isChecked())
                        presenter.approveRequirement();
                    else presenter.rejectRequirement();
                }
            }
        });
    }

    @Override
    public View getRootView() {
        return mRootView;
    }

    //region Interface Methods

    @Nullable
    @Override
    public String getRejectReason() {
        return txtRejectReason.getEditText() == null ? null :
                txtRejectReason.getEditText().getText().toString().trim();
    }

    @Override
    public void showMessage(@StringRes int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish() {
        getService().exit();
    }
    //endregion
}
