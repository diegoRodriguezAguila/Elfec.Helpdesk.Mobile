package com.elfec.helpdesk.view.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.text.Html;

import com.elfec.helpdesk.R;
import com.elfec.helpdesk.presenter.views.IProcessView;

/**
 * Abstracción de una vista que muestra un proceso
 * en notificaciones
 */
public abstract class NotificationProcessView<TResult> implements IProcessView<TResult> {

    private static final int NOTIFICATION_ID = 267;
    private static final long[] VIBRATION_PATTERN = {0, 500};

    private final Context mContext;
    private NotificationCompat.Builder mBuilder;
    private final NotificationManager mNotificationManager;
    private final Uri mErrorSoundUri;
    private final Uri mResultSoundUri;

    public NotificationProcessView(Context context) {
        mContext = context;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context
                .NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mContext);
        mErrorSoundUri = Uri.parse("android.resource://" + mContext.getPackageName() + "/" +
                R.raw.error_sound);
        mResultSoundUri = Uri.parse("android.resource://" + mContext.getPackageName() + "/" +
                R.raw.notify);
    }

    @Override
    public void setError(@StringRes int title, String message) {
        mBuilder.setProgress(0, 0, false).setContentText(message)
                .setOngoing(false)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.notif_error))
                .setAutoCancel(true)
                .setCategory(NotificationCompat.CATEGORY_ERROR)
                .setContentTitle(mContext.getResources().getString(title))
                .setSound(mErrorSoundUri)
                .setVibrate(VIBRATION_PATTERN);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    public void setError(@StringRes int title, @StringRes int message) {
        setError(title, mContext.getResources().getString(message));
    }

    @Override
    public void showProcessing(@StringRes int title, @StringRes int message) {
        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.helpdesk_notification)
                .setContentTitle(mContext.getResources().getText(title))
                .setContentText(mContext.getResources().getText(message))
                .setCategory(NotificationCompat.CATEGORY_PROGRESS)
                .setProgress(0, 0, true)
                .setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));

        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    @Override
    public void setResult(TResult result) {
        CharSequence title = Html.fromHtml(getResultTitle(result));
        CharSequence message = Html.fromHtml(getResultMessage(result));
        mBuilder.setProgress(0, 0, false).setContentText(message)
                .setOngoing(false)
                .setCategory(NotificationCompat.CATEGORY_STATUS)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(),
                        R.drawable.notif_success))
                .setAutoCancel(true)
                .setContentTitle(title)
                .setSound(mResultSoundUri)
                .setVibrate(VIBRATION_PATTERN);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    /**
     * Obtiene según el resultado un titulo para mostrar
     * @param result resultado
     * @return titulo
     */
    public abstract String getResultTitle(TResult result);
    /**
     * Obtiene según el resultado un mensaje para mostrar
     * @param result resultado
     * @return mensaje
     */
    public abstract String getResultMessage(TResult result);
}
