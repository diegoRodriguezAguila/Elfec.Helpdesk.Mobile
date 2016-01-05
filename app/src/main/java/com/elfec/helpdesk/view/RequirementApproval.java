package com.elfec.helpdesk.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.elfec.helpdesk.R;
import com.elfec.helpdesk.service.FloatingWindowService;
import com.elfec.helpdesk.view.floating.RequirementApprovalView;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RequirementApproval extends AppCompatActivity implements ServiceConnection {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri //data = getIntent().getData();
        //test prupouses don't forget to delete TODO
        data = Uri.parse("http://helpdesk.elfec" +
                ".bo/requirements/R1234?user_code=DROD01&request_user=RMAL01");
        if (data == null) {
            showNoDataDialog();
            System.out.println("Data is null");
        } else {
            System.out.println(data.toString());
            List<String> params = data.getPathSegments();
            Intent serviceIntent = new Intent(getApplication(), FloatingWindowService.class);
            startService(serviceIntent);
            bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
            finish();
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

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

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        FloatingWindowService.LocalBinder binder = (FloatingWindowService.LocalBinder) service;
        FloatingWindowService mService = binder.getService();
        mService.show(new RequirementApprovalView(this));
        unbindService(this);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
