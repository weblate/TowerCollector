/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.analytics;

import info.zamojski.soft.towercollector.model.AnalyticsStatistics;

public class CompositeReportingService implements IAnalyticsReportingService {

    private IAnalyticsReportingService[] analyticsServices;

    public CompositeReportingService(IAnalyticsReportingService... analyticsServices) {
        this.analyticsServices = analyticsServices;
    }

    @Override
    public void setAppOptOut(boolean optOut) {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.setAppOptOut(optOut);
        }
    }

    @Override
    public void sendMainActivityStarted() {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendMainActivityStarted();
        }
    }

    @Override
    public void sendMainActivityStopped() {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendMainActivityStopped();
        }
    }

    @Override
    public void sendPreferencesActivityStarted() {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendPreferencesActivityStarted();
        }
    }

    @Override
    public void sendPreferencesActivityStopped() {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendPreferencesActivityStopped();
        }
    }

    @Override
    public void sendUpdateAction(String source) {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendUpdateAction(source);
        }
    }

    @Override
    public void sendMigrationStarted() {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendMigrationStarted();
        }
    }

    @Override
    public void sendMigrationFinished(long duration, int oldDbVersion, AnalyticsStatistics stats) {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendMigrationFinished(duration, oldDbVersion, stats);
        }
    }

    @Override
    public void sendCollectorStarted(IntentSource source) {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendCollectorStarted(source);
        }
    }

    @Override
    public void sendCollectorFinished(long duration, String transportMode, AnalyticsStatistics stats) {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendCollectorFinished(duration, transportMode, stats);
        }
    }

    @Override
    public void sendCollectorApiVersionUsed(String apiVersion) {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendCollectorApiVersionUsed(apiVersion);
        }
    }

    @Override
    public void sendUploadStarted(IntentSource source) {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendUploadStarted(source);
        }
    }

    @Override
    public void sendUploadFinished(long duration, String networkType, AnalyticsStatistics stats) {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendUploadFinished(duration, networkType, stats);
        }
    }

    @Override
    public void sendExportStarted() {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendExportStarted();
        }
    }

    @Override
    public void sendExportFinished(long duration, String fileType, AnalyticsStatistics stats) {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendExportFinished(duration, fileType, stats);
        }
    }

    @Override
    public void sendExportDeleteAction() {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendExportDeleteAction();
        }
    }

    @Override
    public void sendExportKeepAction() {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendExportKeepAction();
        }
    }

    @Override
    public void sendExportUploadAction() {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendExportUploadAction();
        }
    }

    @Override
    public void sendPrefsUpdateCheckEnabled(boolean enabled) {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendPrefsUpdateCheckEnabled(enabled);
        }
    }

    @Override
    public void sendPrefsNotifyMeasurementsCollected(boolean enabled) {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendPrefsNotifyMeasurementsCollected(enabled);
        }
    }

    @Override
    public void sendPrefsAppTheme(String theme) {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendPrefsAppTheme(theme);
        }
    }

    @Override
    public void sendPrefsCollectorApiVersion(String apiVersion) {
        for (IAnalyticsReportingService service : analyticsServices) {
            service.sendPrefsCollectorApiVersion(apiVersion);
        }
    }
}
