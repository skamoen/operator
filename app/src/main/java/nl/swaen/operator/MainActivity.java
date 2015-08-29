package nl.swaen.operator;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

public class MainActivity extends Activity {

    public static boolean game1Level1 = true;
    public static boolean game1Level2 = true;
    public static boolean game1Level3 = true;
    public static boolean game2Level1 = true;

    protected CCGLSurfaceView _glSurfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        _glSurfaceView = new CCGLSurfaceView(this);

        setContentView(_glSurfaceView);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onStart() {
        super.onStart();

        CCDirector.sharedDirector().attachInView(_glSurfaceView);

        CCDirector.sharedDirector().setDisplayFPS(true);

        CCDirector.sharedDirector().setAnimationInterval(1.0f / 60.0f);

        CCScene scene = MenuLayer.scene();
        scene.addChild(new MainMenuLayer());
        CCDirector.sharedDirector().runWithScene(scene);
    }

    @Override
    public void onPause() {
        super.onPause();

        CCDirector.sharedDirector().pause();
    }

    @Override
    public void onResume() {
        super.onResume();

        CCDirector.sharedDirector().resume();
    }

    @Override
    public void onStop() {
        super.onStop();

        CCDirector.sharedDirector().end();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

}