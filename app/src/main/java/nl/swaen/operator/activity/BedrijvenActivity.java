//package nl.swaen.operator.activity;
//
//import android.app.Activity;
//import android.os.Bundle;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.R;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//public class BedrijvenActivity extends Activity implements OnMapReadyCallback {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.map_activity);
//
//        MapFragment mapFragment = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap map) {
//        LatLng sydney = new LatLng(-33.867, 151.206);
//
//        map.setMyLocationEnabled(true);
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
//
//        map.addMarker(new MarkerOptions()
//                .title("Sydney")
//                .snippet("The most populous city in Australia.")
//                .position(sydney));
//    }
//}