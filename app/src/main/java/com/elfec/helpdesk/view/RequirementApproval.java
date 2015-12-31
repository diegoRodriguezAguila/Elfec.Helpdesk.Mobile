package com.elfec.helpdesk.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.elfec.helpdesk.service.FloatingWindowService;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RequirementApproval extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(getApplication(), FloatingWindowService.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
