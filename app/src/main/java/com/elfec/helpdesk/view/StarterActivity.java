package com.elfec.helpdesk.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.elfec.helpdesk.R;
import com.elfec.helpdesk.model.RequirementApproval;
import com.elfec.helpdesk.service.FloatingWindowService;
import com.elfec.helpdesk.view.floating.RequirementApprovalView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class StarterActivity extends AppCompatActivity implements ServiceConnection {

    private RequirementApprovalView mRequirementApprovalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri data = getIntent().getData();
        //<editor-fold desc="test prupouses don't forget to delete TODO">
        if(data == null)
            data = Uri.parse("http://helpdesk.elfec" +
                    ".bo/requirements/R1112?user_code=DROD01&request_user=RMAL01");
        //</editor-fold>
        if (data == null || !initRequirementApprovalView(data)) {
            showNoDataDialog();
        } else {
            Intent serviceIntent = new Intent(getApplication(), FloatingWindowService.class);
            startService(serviceIntent);
            bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mRequirementApprovalView = null;
    }


    //region Private Methods

    /**
     * Inicializa la vista de aprobación/rechazo de requerimientos
     * si la inicialización es correcta retorna true
     * @param data URL de donde sacar los datos
     * @return true si la inicialización fue correcta
     */
    private boolean initRequirementApprovalView(@NonNull Uri data) {
        String reqId = data.getLastPathSegment();
        String userCode = data.getQueryParameter("user_code");
        String requestUser = data.getQueryParameter("request_user");
        //bad parameters
        if(reqId==null || userCode==null || requestUser==null)
            return false;
        mRequirementApprovalView = new RequirementApprovalView(this, reqId, new RequirementApproval(userCode,
                requestUser));
        return true;
    }

    /**
     * Muestra dialogo de que no se pasó la información necesaria para iniciar la app
     */
    public void showNoDataDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.title_no_url_data)
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(R.string.msg_no_url_data)
                .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }
    //endregion

    //region Service Bind Methods
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        FloatingWindowService.LocalBinder binder = (FloatingWindowService.LocalBinder) service;
        FloatingWindowService mService = binder.getService();
        mService.show(mRequirementApprovalView);
        unbindService(this);
        finish();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
    //endregion
}
