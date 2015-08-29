package nl.swaen.operator;

import android.util.Log;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

public class BeroepenLevel1TextLayer extends CCLayer {
	private final CGSize winSize = CCDirector.sharedDirector().displaySize();
	CCColorLayer gameLayer = CCColorLayer.node(ccColor4B.ccc4(255, 255, 255, 255));
	private static final String TAG = "BEROEPEN";

	private final float xScale = winSize.width / 320;
	private final float yScale = winSize.height / 480;

	CGPoint touchLocation;
	CGPoint oldLocation;

	public CCScene scene(String spriteName) {
		CCScene scene = CCScene.node();
		CCLayer layer = new BeroepenLevel1TextLayer(spriteName);
		CCSprite sprite = CCSprite.sprite(spriteName);
		sprite.setPosition(MenuLayer.iPos(CGPoint.ccp(160, 240)));
		setUpMenus();
		scene.addChild(layer);

		return scene;
	}

	protected BeroepenLevel1TextLayer() {
		Log.w(TAG, "Setting up Beroepen Textlayer zonder");
		addChild(gameLayer);
		this.setIsTouchEnabled(true);

		// overlay = overlay.setUpWithTitle("Zoek alle beroepen",
		// "introImageFullscreen.ccbi", "Start", "remove");
		setUpMenus();

	}

	protected BeroepenLevel1TextLayer(String spriteName) {
		Log.w(TAG, "Setting up Beroepen Textlayer");
		addChild(gameLayer);
		this.setIsTouchEnabled(true);

		CCSprite sprite = CCSprite.sprite(spriteName);
		// sprite.setPosition(winSize.height / 2, winSize.width / 2);

		sprite.setScaleX(winSize.width / sprite.getTexture().getWidth());
		sprite.setScaleY(winSize.height / sprite.getTexture().getHeight());
		sprite.setAnchorPoint(0, 0);
		sprite.setPosition(0, 0);

		this.addChild(sprite);
		setUpMenus();

	}
	public void setUpMenus() {
		CCMenuItemLabel terugButton = CCMenuItemLabel.item(CCLabel.makeLabel("Terug", "Arial", 30f), this,
				"terugButton");
		CCMenu myMenu = CCMenu.menu(terugButton);
		myMenu.setColor(ccColor3B.ccWHITE);
		myMenu.alignItemsHorizontally();
		myMenu.setPosition(CGPoint.ccp(50, 50));
		this.addChild(myMenu);
	}

	public void terugButton(Object sender) {
		Log.d(TAG, "popping scene");
		CCDirector.sharedDirector().popScene();
	}
}
