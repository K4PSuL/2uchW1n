package com.iia.touchwin.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Class for create notification on device
 * 
 * @author Nicolas GAUTIER
 * 
 */
public class NotificationUtils {

	private Context context;
	private Integer actualNotificationId = 0;

	public NotificationUtils(Context context) {
		this.context = context;
	}

	/**
	 * Create a notification on the device
	 * 
	 * @param context
	 *            Context of my activity
	 * @param notificationTicker
	 *            Short text on actionBar
	 * @param notificationTitle
	 *            Title of notification
	 * @param notificationText
	 *            Text of notification
	 */
	public void createShortNotification(String notificationTicker,
			String notificationTitle, String notificationText) {

		// Standby intent usely to go on new activity
		PendingIntent pendingIntent = PendingIntent.getActivity(this.context,
				0, new Intent(this.context, Const.NOTIFICATION_ACTIVITY),
				PendingIntent.FLAG_ONE_SHOT);

		// Create notification
		Notification.Builder builder = new Notification.Builder(this.context)
				.setWhen(System.currentTimeMillis())
				.setTicker(notificationTicker)
				.setSmallIcon(Const.NOTIFICATION_ICON)
				.setContentTitle(notificationTitle)
				.setContentText(notificationText)
				.setContentIntent(pendingIntent);

		// Push notification on the device
		pushNotification(builder.build());
	}

	/**
	 * Push notification on the device
	 * 
	 * @param context
	 *            Context of actuel activity or service
	 * @param notification
	 *            Notification to push on the device
	 */
	private void pushNotification(Notification notification) {

		// Get NotificationManager by context
		NotificationManager notifManager = (NotificationManager) this.context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// Send Notification on the device by NotificationManager
		notifManager.notify(actualNotificationId++, notification);
	}

}
