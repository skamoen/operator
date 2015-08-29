package nl.swaen.operator;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.types.ccColor4B;

public class ProcessenLevel2FailedLayer extends CCLayer {
	CCInstructionLayer overlay = new CCInstructionLayer();

	public static CCScene scene() {
		CCScene scene = CCScene.node();
		CCLayer layer = new ProcessenLevel2FailedLayer();

		scene.addChild(layer);

		return scene;
	}

	protected ProcessenLevel2FailedLayer() {
		CCColorLayer gameLayer = CCColorLayer.node(ccColor4B.ccc4(255, 255, 255, 255));

		overlay.setUpWithTitle("Jammer! Vekeerde volgorde", "", "Opnieuw", "ProcessenLevel2Failed");
		addChild(gameLayer);

		CCSequence.actions(CCDelayTime.action(3.5f), CCCallFunc.action(this, "showOverlay"));
		addChild(overlay);

		// Animation

	}
	protected void showOverlay() {
		this.addChild(overlay);
	}
}
