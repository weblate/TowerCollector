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
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.views.MapView;

import info.zamojski.soft.towercollector.BuildConfig;
import info.zamojski.soft.towercollector.R;
import info.zamojski.soft.towercollector.events.MeasurementSavedEvent;
import info.zamojski.soft.towercollector.events.PrintMainWindowEvent;

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
        mainMapView.setMinZoomLevel(13);
        mainMapView.setMaxZoomLevel(20);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MeasurementSavedEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PrintMainWindowEvent event) {

    }
}
