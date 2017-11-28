package com.example.timotej.whatdo;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.util.ArrayList;

import static com.example.timotej.whatdo.R.id.map;

//https://github.com/osmdroid/osmdroid
//https://github.com/osmdroid/osmdroid/wiki/How-to-use-the-osmdroid-library
//http://stackoverflow.com/questions/6173388/googlemaps-custom-itemizedoverlay-and-overlayitem-the-correct-way-to-show-diff
//https://github.com/osmdroid/osmdroid/blob/762c3d4241a9a78b244ab233f6f00bb866ee482a/osmdroid-third-party/src/main/java/org/osmdroid/google/overlay/GooglePolylineOverlay.java
//
public class AddLocationActivity extends AppCompatActivity {
    MapView mMapView;
    ScaleBarOverlay mScaleBarOverlay;
    DisplayMetrics dm;
    ArrayList<OverlayItem> items;
    OverlayItem marker;
    private ItemizedOverlayWithFocus<OverlayItem> mMyLocationOverlay;
    Button mNextButton;
    double lang = 46.5572215;
    double lat = 15.6358783;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setTitle("Tapni, da izbereš lokacijo");
        Context ctx = getApplicationContext();
        //important! set your user agent to prevent getting banned from the osm servers
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_map);

        mNextButton = (Button) findViewById(R.id.nextButton);
        mNextButton.setClickable(false);
        mMapView = (MapView) findViewById(map);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);
        IMapController mapController = mMapView.getController();
        mapController.setZoom(17);

        GeoPoint startPoint = new GeoPoint(lang,lat);
        mapController.setCenter(startPoint);
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        mScaleBarOverlay = new ScaleBarOverlay(mMapView);
        mScaleBarOverlay.setCentred(true);
//play around with these values to get the location on screen in the right place for your applicatio
        mScaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mMapView.getOverlays().add(this.mScaleBarOverlay);


        items = new ArrayList<OverlayItem>();
        //items.add(new OverlayItem("Smeti2", "Veliko smeti", new GeoPoint(lang,lat)));
        //items.add(new OverlayItem("Smeti", "Veliko smeti3", new GeoPoint(lang,lat)));

        mMyLocationOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        IMapController mapController = mMapView.getController();
                        mapController.setCenter(item.getPoint());
                        mapController.zoomTo(mMapView.getMaxZoomLevel());
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        Toast.makeText(AddLocationActivity.this
                                ,
                                "Item '" + item.getTitle() + "' (index=" + index
                                        + ") got long pressed", Toast.LENGTH_LONG).show();
                        return false;
                    }
                }, this);


        mMyLocationOverlay.setFocusItemsOnTap(true);
        mMapView.getOverlays().add(mMyLocationOverlay);

        //Track path //trikotnik
        //GooglePolylineOverlay gp = new GooglePolylineOverlay(Color.RED,8);
        //gp.addPoints( new GeoPoint(46.25139,15.2568), new GeoPoint(46.25139,15.2578), new GeoPoint(46.25300,15.2572),new GeoPoint(46.25139,15.2568));
        //mMapView.getOverlays().add(gp);


        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                lat = p.getLatitude();
                lang = p.getLongitude();
                Toast.makeText(getBaseContext(),p.getLatitude()+ " - "+p.getLongitude(),Toast.LENGTH_LONG).show();
                marker = new OverlayItem("bla","blabla",p);
                mMyLocationOverlay.removeAllItems();
                mMyLocationOverlay.addItem(marker);
                if(!mNextButton.isClickable())
                    mNextButton.setClickable(true);
                mMapView.invalidate();
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                //items.add(new OverlayItem("Smeti2", "Veliko smeti", new GeoPoint(p.getLatitude(),p.getLongitude())));
                return false;
            }
        };



        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Load menu
                Intent intent = new Intent(AddLocationActivity.this, AddNewActivity.class);
                Bundle extras = new Bundle();
                extras.putDouble("lang",lang);
                extras.putDouble("lat",lat);
                intent.putExtras(extras);
                startActivity(intent);

            }
        });
        MapEventsOverlay OverlayEvents = new MapEventsOverlay(getBaseContext(),mReceive);
        mMapView.getOverlays().add(OverlayEvents);
    }
    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
    }

}
