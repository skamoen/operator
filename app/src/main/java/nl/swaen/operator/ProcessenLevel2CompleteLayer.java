package nl.swaen.operator;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.types.ccColor4B;

import android.util.Log;

public class ProcessenLevel2CompleteLayer extends CCLayer {
	CCInstructionLayer overlay = new CCInstructionLayer();
	private final CCColorLayer gameLayer;

	public static CCScene scene() {
		CCScene scene = CCScene.node();
		CCLayer layer = new ProcessenLevel2CompleteLayer();

		scene.addChild(layer);

		return scene;
	}

	protected ProcessenLevel2CompleteLayer() {
		gameLayer = CCColorLayer.node(ccColor4B.ccc4(255, 255, 255, 255));

		overlay.setUpWithTitle("Super!", "", "Volgend spel", "ProcessenLevel2Complete");
		addChild(gameLayer);
		showOverlay();
		// Animation

	}
	public void showOverlay() {
		Log.i("Succeslayer", "Adding overlay");
		gameLayer.addChild(overlay);
	}
}
