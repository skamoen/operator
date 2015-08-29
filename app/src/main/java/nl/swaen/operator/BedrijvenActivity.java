package nl.swaen.operator;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.dd.plist.NSObject;
import com.dd.plist.PropertyListParser;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.cocos2d.utils.PlistParser;

import java.util.HashMap;

import nl.swaen.operator.R;

public class BedrijvenActivity extends FragmentActivity {

    /**
     * Called when the activity is first created.
     */
    // @Override
    // public void onCreate(Bundle savedInstanceState) {
    // super.onCreate(savedInstanceState);
    //
    // TextView text = new TextView(this);
    // text.setText("Hello World, Android - mkyong.com");
    // setContentView(text);
    // }
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bedrijven);
        setUpMapIfNeeded();

        HashMap<String, Object> bedrijfMap = PlistParser.parse(am.open("demoBedrijf.plist"));

    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the
        // map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(52.638793, 4.656229)).title("Home Sweet Home"));
    }

    // 04-12 13:16:48.835: W/System.err(2131): java.io.FileNotFoundException:
    // /assets/demoBedrijf.plist: open failed: ENOENT (No such file or
    // directory)

    public NSObject parsePlist() {
        AssetManager am = getAssets();
        try {
            NSObject obj = PropertyListParser.parse(am.open("demoBedrijf.plist"));
            Log.d("Parse Plist", obj.getClass().getName());
            return obj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
