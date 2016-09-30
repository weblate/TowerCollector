/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package info.zamojski.soft.towercollector.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.osmdroid.api.IMapController;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import info.zamojski.soft.towercollector.BuildConfig;
import info.zamojski.soft.towercollector.MyApplication;
import info.zamojski.soft.towercollector.R;
import info.zamojski.soft.towercollector.dao.MeasurementsDatabase;
import info.zamojski.soft.towercollector.events.MeasurementSavedEvent;
import info.zamojski.soft.towercollector.events.PrintMainWindowEvent;
import info.zamojski.soft.towercollector.model.Measurement;

public class MainMapFragment extends MainFragmentBase {

    private static final String TAG = MainMapFragment.class.getSimpleName();

    private MapView mainMapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_map_fragment, container, false);
        configureControls(rootView);
        return rootView;
    }

    @Override
    protected void configureOnResume() {
        super.configureOnResume();

    }

    @Override
    protected void configureControls(View view) {
        super.configureControls(view);
        OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);
        OpenStreetMapTileProviderConstants.setCacheSizes(100, 85);
        mainMapView = (MapView) view.findViewById(R.id.main_map);
        mainMapView.setBuiltInZoomControls(true);
        mainMapView.setMultiTouchControls(true);
        mainMapView.setMinZoomLevel(getResources().getInteger(R.integer.preferences_main_map_zoom_level_min_value));
        mainMapView.setMaxZoomLevel(getResources().getInteger(R.integer.preferences_main_map_zoom_level_max_value));

        IMapController mapController = mainMapView.getController();
        mapController.setZoom(MyApplication.getPreferencesProvider().getMainMapZoomLevel());
        mainMapView.setMapListener(new DelayedMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent scrollEvent) {
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent zoomEvent) {
                MyApplication.getPreferencesProvider().setMainMapZoomLevel(zoomEvent.getZoomLevel());
                return false;
            }
        }));

        printLastMeasurements(mapController);
    }

    private void printLastMeasurements(IMapController mapController) {
        Measurement lastMeasurement = MeasurementsDatabase.getInstance(getContext()).getLastMeasurement();
        if (lastMeasurement != null) {
            GeoPoint startPoint = new GeoPoint(lastMeasurement.getLatitude(), lastMeasurement.getLongitude());
            mapController.setCenter(startPoint);

            // TODO: temporarily load last 1000 measurements instead of these visible
            List<Measurement> measurements = MeasurementsDatabase.getInstance(getContext()).getOlderMeasurements(System.currentTimeMillis(), 0, 1000);

            // TODO: don't print on main thread
            // create markers
            ArrayList<OverlayItem> items = new ArrayList<>();
            for (Measurement m : measurements) {
                OverlayItem item = new OverlayItem(null, null, new GeoPoint(m.getLatitude(), m.getLongitude()));
                items.add(item);
            }

            // create overlay
            ItemizedOverlayWithFocus<OverlayItem> overlay = new ItemizedOverlayWithFocus<>(items, null, getContext());
            mainMapView.getOverlays().add(overlay);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MeasurementSavedEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PrintMainWindowEvent event) {

    }
}
