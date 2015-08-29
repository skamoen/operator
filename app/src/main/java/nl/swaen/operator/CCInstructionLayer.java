package nl.swaen.operator;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemFont;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCLabel.TextAlignment;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import android.util.Log;
import android.view.MotionEvent;

import nl.swaen.operator.activity.MainActivity;

public class CCInstructionLayer extends CCLayer {
	public static CGSize winSize = CCDirector.sharedDirector().displaySize();

	public CCInstructionLayer setUpWithTitle(String titleString, String fileName, String submitString, String method) {

		CCColorLayer instructionSquare = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 124), winSize.width * 0.8f,
				winSize.height * 0.8f);
		instructionSquare.setPosition(CGPoint.ccp((winSize.width - (winSize.width * 0.8f)) / 2,
				(winSize.height - (winSize.height * 0.8f)) / 2));
		CCLabel title = CCLabel.makeLabel(titleString, CGSize.make(winSize.width * 0.6f, 300), TextAlignment.CENTER,
				"Arial", 30);
		title.setPosition(CGPoint.ccp(instructionSquare.getWidth() / 2, winSize.height * 0.7f));

		instructionSquare.addChild(title);

		// CCNode level = CCBReader.nodeGraphFromFile("testLayer.ccbi");
		// level.setPosition(CGPoint.ccp(30, 80));
		// instructionSquare.addChild(level);

		CCMenuItemFont menuItem3 = CCMenuItemFont.item(submitString, this, method);

		CCMenu myMenu = CCMenu.menu(menuItem3);
		myMenu.alignItemsHorizontally();
		myMenu.setPosition(CGPoint.ccp(instructionSquare.getWidth() / 2, 40));
		instructionSquare.addChild(myMenu);
		this.addChild(instructionSquare);

		return this;
	}

	public void remove(Object sender) {
		Log.i("InstructionLayer", "Removing instructionlayer");
		this.removeFromParentAndCleanup(false);
	}

	public void ProcessenLevel1Complete(Object sender) {
		Log.i("InstructionLayer", "Going from level 1 to level 2");
		MainActivity.game1Level2 = true;
		CCScene nextScene = ProcessenLevel2Layer.scene();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));
	}

	public void Processenlevel2Complete(Object sender) {
		Log.i("InstructionLayer", "Going from level 2 to level 3");
		MainActivity.game1Level3 = true;
		CCScene nextScene = ProcessenLevel3Layer.scene();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));
	}

	public void ProcessenLevel3Complete(Object sender) {
		Log.i("InstructionLayer", "Going from level 3 to menu");
		CCScene nextScene = MainMenuLayer.scene();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

	}

	public void ProcessenLevel1Failed(Object sender) {
		Log.i("InstructionLayer", "Restarting level 1");
		CCScene nextScene = ProcessenLevel1Layer.scene();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

	}

	public void ProcessenLevel2Failed(Object sender) {
		Log.i("InstructionLayer", "Restarting level 2");
		CCScene nextScene = ProcessenLevel2Layer.scene();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

	}

	public void ProcessenLevel3Failed(Object sender) {
		Log.i("InstructionLayer", "Restarting level 3");
		CCScene nextScene = ProcessenLevel3Layer.scene();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		Log.i("InstructionLayer", "Touch incoming");
		CGPoint pt = convertTouchToNodeSpace(event);
		CGPoint touchLocation = CCDirector.sharedDirector().convertToGL(pt);
		if (CGRect.containsPoint(CGRect.make(30, 30, 260, 420), touchLocation)) {
			return false;
		} else
			return true;
	}

	@Override
	protected void registerWithTouchDispatcher() {
		// TODO Auto-generated method stub
		super.registerWithTouchDispatcher();
	}
}
