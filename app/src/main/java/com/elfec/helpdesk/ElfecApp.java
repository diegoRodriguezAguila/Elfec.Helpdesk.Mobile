package com.elfec.helpdesk;

import android.app.Application;

import com.elfec.helpdesk.security.AppPreferences;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Aplicación que extiende de la aplicación android
 */
public class ElfecApp extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/helvetica_neue_roman.otf").setFontAttrId(R.attr.fontPath).build());
        AppPreferences.init(this);
    }

}
