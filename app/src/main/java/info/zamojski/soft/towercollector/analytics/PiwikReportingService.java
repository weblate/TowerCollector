/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.analytics;

import android.app.Application;

import org.piwik.sdk.Piwik;
import org.piwik.sdk.Tracker;
import org.piwik.sdk.TrackerConfig;
import org.piwik.sdk.dispatcher.Packet;
import org.piwik.sdk.extra.TrackHelper;

import java.util.ArrayList;
import java.util.Collections;

import info.zamojski.soft.towercollector.BuildConfig;
import info.zamojski.soft.towercollector.analytics.internal.Action;
import info.zamojski.soft.towercollector.analytics.internal.Category;
import info.zamojski.soft.towercollector.analytics.internal.Dimension;
import info.zamojski.soft.towercollector.analytics.internal.Label;
import info.zamojski.soft.towercollector.analytics.internal.Metric;
import info.zamojski.soft.towercollector.analytics.internal.ScreenPaths;
import info.zamojski.soft.towercollector.analytics.internal.Screens;
import info.zamojski.soft.towercollector.model.AnalyticsStatistics;

public class PiwikReportingService implements IAnalyticsReportingService {

    private Application application;

    private Tracker tracker;

    public PiwikReportingService(Application application, boolean trackingEnabled, boolean dryRun) {
        this.application = application;

        this.tracker = Piwik.getInstance(application).newTracker(new TrackerConfig(BuildConfig.ANALYTICS_SERVICE_URI, 1, "Default"));
        this.tracker.setOptOut(!trackingEnabled);
        this.tracker.setDryRunTarget(dryRun ? new ArrayList<Packet>() : null);
    }

    @Override
    public void setAppOptOut(boolean optOut) {
        this.tracker.setOptOut(optOut);
    }

    @Override
    public void sendMainActivityStarted() {
        TrackHelper.track()
                .screen(ScreenPaths.MainActivity)
                .title(Screens.MainActivity)
                .with(tracker);
    }

    @Override
    public void sendMainActivityStopped() {
    }

    @Override
    public void sendPreferencesActivityStarted() {
        TrackHelper.track()
                .screen(ScreenPaths.PreferencesActivity)
                .title(Screens.PreferencesActivity)
                .with(tracker);
    }

    @Override
    public void sendPreferencesActivityStopped() {
    }

    @Override
    public void sendUpdateAction(String source) {
        TrackHelper.track()
                .dimension(Dimension.UpdateSource, source)
                .event(Category.Tasks, Action.Update)
                .name(Label.Select)
                .value(1f)
                .with(tracker);
    }

    @Override
    public void sendMigrationStarted() {
        TrackHelper.track()
                .event(Category.Tasks, Action.DbMigration)
                .name(Label.Start)
                .value(1f)
                .with(tracker);
    }

    @Override
    public void sendMigrationFinished(long duration, int oldDbVersion, AnalyticsStatistics stats) {

        TrackHelper.track()
                .dimension(Dimension.MigrationDbVersion, String.valueOf(oldDbVersion))
// TODO: custom metrics tracking not supported in Piwik
//                .setCustomMetric(Metric.StatisticsLocations, stats.getLocations())
//                .setCustomMetric(Metric.StatisticsCells, stats.getCells())
//                .setCustomMetric(Metric.StatisticsDays, stats.getDays())
//                .setCustomMetric(Metric.Duration, duration)
                .event(Category.Tasks, Action.DbMigration)
                .name(Label.Finish)
                .value(1f)
                .with(tracker);
// TODO: timing tracking not supported in Piwik
//        this.tracker.send(new HitBuilders.TimingBuilder(Category.Tasks, Action.DbMigration, duration)
//                .setLabel(Label.Finish)
//                .setCustomDimension(Dimension.MigrationDbVersion, String.valueOf(oldDbVersion))
//                .build());
    }

    @Override
    public void sendCollectorStarted(IntentSource source) {
        TrackHelper.track()
                .event(Category.Tasks, Action.Collect)
                .name(convertToStartLabel(source))
                .value(1f)
                .with(tracker);
    }

    @Override
    public void sendCollectorFinished(long duration, String meansOfTransport, AnalyticsStatistics stats) {
        TrackHelper.track()
                .dimension(Dimension.CollectorMeansOfTrasport, meansOfTransport)
// TODO: custom metrics tracking not supported in Piwik
//                .setCustomMetric(Metric.CollectedLocationsInSession, stats.getLocations())
//                .setCustomMetric(Metric.CollectedCellsInSession, stats.getCells())
//                .setCustomMetric(Metric.Duration, duration)
                .event(Category.Tasks, Action.Collect)
                .name(Label.Finish)
                .value(1f)
                .with(tracker);
// TODO: timing tracking not supported in Piwik
//        this.tracker.send(new HitBuilders.TimingBuilder(Category.Tasks, Action.Collect, duration)
//                .setLabel(Label.Finish)
//                .setCustomDimension(Dimension.CollectorMeansOfTrasport, meansOfTransport)
//                .build());
    }

    @Override
    public void sendCollectorApiVersionUsed(String apiVersion) {
        TrackHelper.track()
                .event(Category.Runtime, Action.CollectorApiVersion)
                .name(apiVersion)
                .value(1f)
                .with(tracker);
    }

    @Override
    public void sendUploadStarted(IntentSource source) {
        TrackHelper.track()
                .event(Category.Tasks, Action.Upload)
                .name(convertToStartLabel(source))
                .value(1f)
                .with(tracker);
    }

    @Override
    public void sendUploadFinished(long duration, String networkType, AnalyticsStatistics stats) {
        TrackHelper.track()
                .dimension(Dimension.UploadNetworkType, networkType)
// TODO: custom metrics tracking not supported in Piwik
//                .setCustomMetric(Metric.StatisticsLocations, stats.getLocations())
//                .setCustomMetric(Metric.StatisticsCells, stats.getCells())
//                .setCustomMetric(Metric.StatisticsDays, stats.getDays())
//                .setCustomMetric(Metric.Duration, duration)
                .event(Category.Tasks, Action.Upload)
                .name(Label.Finish)
                .value(1f)
                .with(tracker);
// TODO: timing tracking not supported in Piwik
//        this.tracker.send(new HitBuilders.TimingBuilder(Category.Tasks, Action.Upload, duration)
//                .setLabel(Label.Finish)
//                .setCustomDimension(Dimension.UploadNetworkType, networkType)
//                .build());
    }

    @Override
    public void sendExportStarted() {
        TrackHelper.track()
                .event(Category.Tasks, Action.Export)
                .name(Label.Start)
                .value(1f)
                .with(tracker);
    }

    @Override
    public void sendExportFinished(long duration, String fileType, AnalyticsStatistics stats) {
        TrackHelper.track()
                .dimension(Dimension.ExportFileType, fileType)
// TODO: custom metrics tracking not supported in Piwik
//                .setCustomMetric(Metric.StatisticsLocations, stats.getLocations())
//                .setCustomMetric(Metric.StatisticsCells, stats.getCells())
//                .setCustomMetric(Metric.StatisticsDays, stats.getDays())
//                .setCustomMetric(Metric.Duration, duration)
                .event(Category.Tasks, Action.Export)
                .name(Label.Finish)
                .value(1f)
                .with(tracker);
// TODO: timing tracking not supported in Piwik
//        this.tracker.send(new HitBuilders.TimingBuilder(Category.Tasks, Action.Export, duration)
//                .setLabel(Label.Finish)
//                .setCustomDimension(Dimension.ExportFileType, fileType)
//                .build());
    }

    @Override
    public void sendExportDeleteAction() {
        sendExportAction(Label.Delete);
    }

    @Override
    public void sendExportKeepAction() {
        sendExportAction(Label.Keep);
    }

    @Override
    public void sendExportUploadAction() {
        sendExportAction(Label.Upload);
    }

    @Override
    public void sendPrefsUpdateCheckEnabled(boolean enabled) {
        sendBooleanValue(Action.UpdateCheckEnabled, enabled);
    }

    @Override
    public void sendPrefsNotifyMeasurementsCollected(boolean enabled) {
        sendBooleanValue(Action.NotifyMeasurementsCollectedEnabled, enabled);
    }

    @Override
    public void sendPrefsAppTheme(String theme) {
        TrackHelper.track()
                .event(Category.Preferences, Action.AppTheme)
                .name(theme)
                .value(1f)
                .with(tracker);
    }

    @Override
    public void sendPrefsCollectorApiVersion(String apiVersion) {
        TrackHelper.track()
                .event(Category.Preferences, Action.CollectorApiVersion)
                .name(apiVersion)
                .value(1f)
                .with(tracker);
    }

    private void sendExportAction(String action) {
        TrackHelper.track()
                .event(Category.Tasks, Action.Export)
                .name(action)
                .value(1f)
                .with(tracker);
    }

    private void sendBooleanValue(String action, boolean value) {
        TrackHelper.track()
                .event(Category.Preferences, action)
                .name(Label.Usage)
                .value(value ? 1f : 0f)
                .with(tracker);
    }

    private String convertToStartLabel(IntentSource source) {
        switch (source) {
            case User:
                return Label.Start;
            case Application:
                return Label.StartIntent;
            case System:
                return Label.StartSystemIntent;
            default:
                throw new UnsupportedOperationException(String.format("Unsupported intent source '%s'", source));
        }
    }
}
