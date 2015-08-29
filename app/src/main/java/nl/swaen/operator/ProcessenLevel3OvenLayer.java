package nl.swaen.operator;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import android.util.Log;
import android.view.MotionEvent;

public class ProcessenLevel3OvenLayer extends CCLayer {
	CCInstructionLayer overlay;
	CCColorLayer gameLayer;

	private final CGSize winSize = CCDirector.sharedDirector().displaySize();

	CCSprite ei;
	CCSprite ei_l;
	CCSprite ei_r;
	CCSprite schaal;
	CCSprite spiegelei;
	CCSprite selSprite;

	private final ArrayList<CCSprite> movableSprites = new ArrayList<CCSprite>();

	private final CGPoint[] initialPos = new CGPoint[3];
	private int selIndex;
	private CGPoint oldLocation;

	int phase;

	public static CCScene scene() {
		CCScene scene = CCScene.node();
		CCLayer layer = new ProcessenLevel3OvenLayer();

		scene.addChild(layer);

		return scene;
	}

	protected ProcessenLevel3OvenLayer() {
		gameLayer = CCColorLayer.node(ccColor4B.ccc4(255, 255, 255, 255));
		addChild(gameLayer);
		setUpMenus();
		overlay = new CCInstructionLayer();
		overlay = overlay.setUpWithTitle("Cake Bakken", "animate", "Start", "remove");
		setIsTouchEnabled(true);
		phase = 1;

		setUpLevel();
		addChild(overlay);
	}

	public void setUpMenus() {
		CCMenuItemLabel terugButton = CCMenuItemLabel.item(CCLabel.makeLabel("Terug", "Arial", 30f), this,
				"terugButton");
		CCMenu myMenu = CCMenu.menu(terugButton);
		myMenu.setColor(ccColor3B.ccc3(0, 0, 0));
		myMenu.alignItemsHorizontally();
		myMenu.setPosition(CGPoint.ccp(50, 50));
		this.addChild(myMenu);
	}

	public void setUpLevel() {
		schaal = CCSprite.sprite("schaal.png");
		schaal.setAnchorPoint(CGPoint.ccp(0, 0));
		schaal.setPosition(0, 0);
		gameLayer.addChild(schaal);

		ei = CCSprite.sprite("ei.png");
		ei.setPosition(160, 320);
		ei.setScale(1);
		gameLayer.addChild(ei);

		spiegelei = CCSprite.sprite("spiegelei.png");
		spiegelei.setPosition(160, 120);
		initialPos[0] = spiegelei.getPosition();
		spiegelei.setScale(0.45f);
		spiegelei.setVisible(false);
		gameLayer.addChild(spiegelei);

		ei_l = CCSprite.sprite("ei_l.png");
		ei_l.setPosition(160 - 54 / 2, 120);
		initialPos[1] = ei_l.getPosition();
		ei_l.setVisible(false);
		gameLayer.addChild(ei_l);

		ei_r = CCSprite.sprite("ei_r.png");
		ei_r.setPosition(160 + 77 / 2, 120);
		initialPos[2] = ei_r.getPosition();
		ei_r.setScale(1);
		ei_r.setVisible(false);
		gameLayer.addChild(ei_r);

		movableSprites.add(ei);
		movableSprites.add(ei_l);
		movableSprites.add(ei_r);

	}

	public void terugButton(Object sender) {

		CCScene nextScene = MenuProcessenLayer.scene();

		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

	}

	private CGPoint boundLayerPost(CGPoint newPos) {
		CGPoint retval = newPos;
		retval.x = Math.min(retval.x, 0);
		retval.x = Math.max(retval.x, winSize.width);
		retval.y = this.position_.y;
		return retval;

	}

	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		Log.i("Level3", "TouchesBegan");
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		oldLocation = touchLocation;

		selectSpriteForTouch(touchLocation);
		if (selSprite != null)
			return true;
		else
			return false;

	}
	public void selectSpriteForTouch(CGPoint touchLocation) {
		CCSprite newSprite = null;
		for (int i = 0; i < movableSprites.size(); i++) {
			CCSprite sprite = movableSprites.get(i);
			if (CGRect.containsPoint(sprite.getBoundingBox(), touchLocation)) {
				newSprite = sprite;
				selIndex = i;
				break;
			}
		}

		if (newSprite != selSprite) {
			if (selSprite != null) {
				selSprite.stopAllActions();
				selSprite = newSprite;
				selSprite.runAction(CCMoveTo.action(0.1f, initialPos[selIndex]));
			}

			initialPos[selIndex] = newSprite.getPosition();
			CCRotateTo rotLeft = CCRotateTo.action(0.1f, -4.0f);
			CCRotateTo rotCenter = CCRotateTo.action(0.1f, 0);
			CCRotateTo rotRight = CCRotateTo.action(0.1f, 4.0f);
			CCSequence rotSeq = CCSequence.actions(rotLeft, rotCenter, rotRight, rotCenter);
			newSprite.runAction(CCRepeatForever.action(rotSeq));
			selSprite = newSprite;

		}
	}

	private void panForTranslation(CGPoint translation) {
		if (selSprite != null) {
			CGPoint newPos = CGPoint.ccpAdd(selSprite.getPosition(), translation);
			selSprite.setPosition(newPos);
		} else {
			CGPoint newPos = CGPoint.ccpAdd(this.getPosition(), translation);
			this.setPosition(boundLayerPost(newPos));
		}
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		if (selSprite != null) {
			CGPoint touchLocation = convertTouchToNodeSpace(event);

			if (selSprite.equals(ei) && phase == 1) {
				if (touchLocation.y > 400) {

				} else if (touchLocation.y < 120) {
					CGPoint translation = CGPoint.ccpSub(CGPoint.ccp(160, touchLocation.y),
							CGPoint.ccp(160, oldLocation.y));
					if (translation.y < -40) {
						ei.setVisible(false);
						ei_l.setVisible(true);
						ei_r.setVisible(true);
						spiegelei.setVisible(true);
						// TODO play Crack sound

						phase = 2;
					}
				} else {
					CGPoint translation = CGPoint.ccpSub(CGPoint.ccp(160, touchLocation.y),
							CGPoint.ccp(160, oldLocation.y));
					this.panForTranslation(translation);
				}
			} else if (phase == 2) {
				CGPoint translation = CGPoint
						.ccpSub(CGPoint.ccp(touchLocation.x, 120), CGPoint.ccp(oldLocation.x, 120));
			}

		}
		CGPoint oldLocation = this.convertTouchToNodeSpace(event);
		return true;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		CGPoint touchLocation = convertTouchToNodeSpace(event);
		if (selSprite != null) {

			if (Math.abs(touchLocation.x - 160) > 100 && phase == 2) {
				Log.i("Level3-1", "Touche ended in bounding box");
				CCAction action1 = CCMoveTo.action(0.2f, CGPoint.ccp(420, 120));
				CCAction action2 = CCMoveTo.action(0.2f, CGPoint.ccp(-100, 120));
				CCAction action3 = CCScaleTo.action(0.2f, 1);

				if (selSprite.equals(ei)) {
					ei_l.runAction(action2);
					ei_r.runAction(action1);
					spiegelei.runAction(action3);

					selSprite = null;

					// TODO to scene 2;

				}
			} else {
				Log.i("Level3-1", "Touche ended outside bounding box");
				selSprite.stopAllActions();
				selSprite.runAction(CCRotateTo.action(0.1f, 0));
				selSprite.runAction(CCMoveTo.action(0.1f, initialPos[selIndex]));
				selSprite = null;
			}
		}

		return true;
	}
}
