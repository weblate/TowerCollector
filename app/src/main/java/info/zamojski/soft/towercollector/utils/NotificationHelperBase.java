/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.utils;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import info.zamojski.soft.towercollector.CollectorService;
import info.zamojski.soft.towercollector.R;
import info.zamojski.soft.towercollector.UploaderService;
import info.zamojski.soft.towercollector.tasks.ExportFileAsyncTask;

public class NotificationHelperBase {

    protected static final String COLLECTOR_NOTIFICATION_CHANNEL_ID = "collector_notification_channel";
    protected static final String UPLOADER_NOTIFICATION_CHANNEL_ID = "collector_notification_channel";
    protected static final String OTHER_NOTIFICATION_CHANNEL_ID = "collector_notification_channel";

    protected boolean isUsingNotificationChannel() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O);
    }

    protected boolean isUsingNotificationPriority() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O);
    }
}
