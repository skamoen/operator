package nl.swaen.operator;

import java.util.ArrayList;

import org.cocos2d.actions.CCProgressTimer;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCCallFuncN;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCProgressTo;
import org.cocos2d.actions.interval.CCRotateBy;
import org.cocos2d.actions.interval.CCRotateTo;
import org.cocos2d.actions.interval.CCScaleTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.actions.interval.CCSpawn;
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

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

public class ProcessenLevel1Layer extends CCLayer {
	private final CGSize winSize = CCDirector.sharedDirector().displaySize();
	private final float xScale = winSize.width / 320;
	private final float yScale = winSize.width / 480;
	CCColorLayer gameLayer = CCColorLayer.node(ccColor4B.ccc4(255, 255, 255, 255));
	private CCSprite selSprite = null;
	private int selIndex;

	private final ArrayList<CCSprite> movableSprites = new ArrayList<CCSprite>();
	private final ArrayList<CCSprite> addedIngredients = new ArrayList<CCSprite>();
	private final CGPoint[] initialPos = new CGPoint[4];
	private final float[] initialRot = new float[4];
	private static final String TAG = "POPERATOR";

	CCSprite beker;
	CCSprite spiraal;
	CCSprite theelepel;
	CCSprite reageerbuis;
	CCSprite pipett;
	CCSprite progressBorder;
	CCProgressTimer progressBar;

	CGPoint oldLocation;
	CCCallFunc actionCallFunc;

	Context _cocos2dContext;

	// set default scene
	public static CCScene scene() {
		CCScene scene = CCScene.node();
		CCLayer layer = new ProcessenLevel1Layer();

		scene.addChild(layer);

		return scene;
	}

	protected ProcessenLevel1Layer() {
		Log.i(TAG, "setting up level 1 layer");
		addChild(gameLayer);
		this.setIsTouchEnabled(true);
		setUpMenus();

		CCInstructionLayer overlay = new CCInstructionLayer();
		overlay = overlay.setUpWithTitle("Maak een brandstof verbeteraar", "introImageFullScreen.ccbi", "Start",
				"remove");
		ArrayList<CCSprite> addedIngredients = new ArrayList();

		addChild(overlay);
		setUpLevel();

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

	public void terugButton(Object sender) {

		CCScene nextScene = MenuProcessenLayer.scene();

		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

	}

	private void setUpLevel() {

		beker = CCSprite.sprite("beker.png");
		beker.setPosition(MenuLayer.iPos(CGPoint.ccp(160, 180)));
		beker.setScale(0.9f);
		gameLayer.addChild(beker);

		spiraal = CCSprite.sprite("spiraal.png");
		spiraal.setPosition(MenuLayer.iPos(CGPoint.ccp(250, 440)));
		initialPos[0] = spiraal.getPosition();
		initialRot[0] = spiraal.getRotation();
		spiraal.setScale(xScale);
		gameLayer.addChild(spiraal);

		theelepel = CCSprite.sprite("theelepel.png");
		theelepel.setPosition(MenuLayer.iPos(CGPoint.ccp(70, 380)));
		initialPos[1] = theelepel.getPosition();
		initialRot[1] = theelepel.getRotation();
		theelepel.setFlipX(true);
		theelepel.setRotation(10);
		theelepel.setScale(xScale);
		gameLayer.addChild(theelepel);

		reageerbuis = CCSprite.sprite("reageerbuis.png");
		reageerbuis.setPosition(MenuLayer.iPos(CGPoint.ccp(50, 250)));
		reageerbuis.setScale(0.9f);
		reageerbuis.setRotation(40);
		initialPos[2] = reageerbuis.getPosition();
		initialRot[2] = reageerbuis.getRotation();
		gameLayer.addChild(reageerbuis);

		pipett = CCSprite.sprite("pipett.png");
		pipett.setPosition(MenuLayer.iPos(CGPoint.ccp(250, 330)));
		pipett.setRotation(10);
		pipett.setScale(0.8f);
		initialPos[3] = pipett.getPosition();
		initialRot[3] = pipett.getRotation();
		gameLayer.addChild(pipett);

		progressBorder = CCSprite.sprite("progressBarBackground.png");
		progressBorder.setPosition(CGPoint.ccp(winSize.width / 2, winSize.height * 0.06f));
		gameLayer.addChild(progressBorder, 2);

		progressBar = CCProgressTimer.progress("progressBar.png");
		progressBar.setType(CCProgressTimer.kCCProgressTimerTypeHorizontalBarLR);
		progressBar.setAnchorPoint(0, 0);

		progressBorder.addChild(progressBar, 3);

		movableSprites.add(spiraal);
		movableSprites.add(theelepel);
		movableSprites.add(reageerbuis);
		movableSprites.add(pipett);
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
		Log.d("MainActivity", "TouchesBegan");

		CGPoint touchLocation = convertTouchToNodeSpace(event);
		oldLocation = touchLocation;
		selectSpriteForTouch(touchLocation);
		if (selSprite != null) {
			System.out.println("selSprite is " + selSprite);
			return true;
		} else {
			System.out.println("selSprite is null");
			return false;
		}
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
				selSprite.runAction(CCRotateTo.action(0.1f, initialRot[selIndex]));
				selSprite = newSprite;
				selSprite.runAction(CCMoveTo.action(0.1f, initialPos[selIndex]));
			}

			initialPos[selIndex] = newSprite.getPosition();
			initialRot[selIndex] = newSprite.getRotation();
			CCRotateBy rotLeft = CCRotateBy.action(0.1f, -4.0f);
			CCRotateBy rotCenter = CCRotateBy.action(0.1f, 0);
			CCRotateBy rotRight = CCRotateBy.action(0.1f, 4.0f);
			CCSequence rotSeq = CCSequence.actions(rotLeft, rotCenter, rotRight, rotCenter);
			newSprite.runAction(CCRepeatForever.action(rotSeq));
			selSprite = newSprite;

		}

	}
	@Override
	public boolean ccTouchesMoved(MotionEvent event) {

		CGPoint touchLocation = this.convertTouchToNodeSpace(event);
		CGPoint translation = CGPoint.ccpSub(touchLocation, oldLocation);

		panForTranslation(translation);

		if (CGRect.containsPoint(beker.getBoundingBox(), touchLocation) && selSprite != null) {
			beker.setScale(0.95f);
		} else
			beker.setScale(0.9f);

		oldLocation = touchLocation;

		return true;

	}

	private void panForTranslation(CGPoint translation) {
		if (selSprite != null) {
			CGPoint newPos = CGPoint.ccpAdd(selSprite.getPosition(), translation);
			selSprite.setPosition(newPos);
		} else {
			CGPoint newPos = CGPoint.ccpAdd(this.getPosition(), translation);
		}
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {

		CGPoint touchLocation = convertTouchToNodeSpace(event);

		if (CGRect.containsPoint(beker.getBoundingBox(), touchLocation) && selSprite != null) {
			beker.setScale(0.95f);
			CCMoveTo action1 = CCMoveTo.action(0.2f, beker.getPosition());
			CCSpawn action2 = CCSpawn.actions(CCScaleTo.action(0.2f, 0), CCRotateBy.action(0.2f, 720));
			beker.setScale(0.9f);

			try {
				if (selSprite.equals(spiraal)) {
					Log.i(TAG, "spiraal added");
					addedIngredients.add(spiraal);
				} else if (selSprite.equals(pipett)) {
					Log.i(TAG, "pipett added");
					addedIngredients.add(pipett);
				} else if (selSprite.equals(reageerbuis)) {
					Log.i(TAG, "reageerbuis added");
					addedIngredients.add(reageerbuis);
				} else if (selSprite.equals(theelepel)) {
					Log.i(TAG, "theelepel added");
					addedIngredients.add(theelepel);
				}

				CCSequence actionSequence2 = CCSequence.actions(action1, action2);
				selSprite.runAction(actionSequence2);
			} catch (Exception e) {
				Log.e(TAG, "Error selecting Sprite", e);
			}

			if (selSprite != null) {
				runAction(CCCallFuncN.action(this, "ingredientAdded"));
			} else
				Log.d(TAG, "SelSprite is null!");

			// SoundEngine.sharedEngine().playEffect(app, resId)

			selSprite = null;
		}

		else {
			if (selSprite != null) {
				selSprite.stopAllActions();
				selSprite.runAction(CCRotateTo.action(0.1f, initialRot[selIndex]));
				selSprite.runAction(CCMoveTo.action(0.1f, initialPos[selIndex]));
			}
			selSprite = null;
			beker.setScale(0.9f);
		}
		return true;

	}
	public void ingredientAdded(Object sender) {
		Log.i(TAG, "starting addIngredients");

		float newPercentage = 0;
		boolean check = false;
		CCAction.action();

		if (progressBar.getPercentage() < 50) {
			Log.d(TAG, "Percentage < 50");
			newPercentage = 50;
		} else if (progressBar.getPercentage() < 100) {
			Log.d(TAG, "Percentage < 100");
			newPercentage = 100;
			check = true;
			actionCallFunc = CCCallFuncN.action(this, "checkIngredienten");
		}

		Log.d(TAG, "Starting actiosequence with new percentage: " + newPercentage);
		progressBar.runAction(CCProgressTo.action(0.8f, newPercentage));
		if (check)
			runAction(actionCallFunc);
	}

	public void checkIngredienten(Object sender) {
		Log.i(TAG, "Start checkIngredienten");
		if (addedIngredients.size() == 2) {
			if (addedIngredients.contains(pipett) && addedIngredients.contains(spiraal)) {
				Log.d(TAG, "Going to SuccesLayer()");
				this.showSuccesOverlay();
			} else {
				Log.d(TAG, "Going to FailedLayer()");
				this.showFailedOverlay();
			}
		} else {
			Log.i(TAG, "Niet genoeg ingredienten");
		}

	}
	public void showSuccesOverlay() {
		CCScene nextScene = ProcessenLevel1CompleteLayer.scene();
		// CCScene nextScene = MenuProcessenLayer.scene();

		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

	}
	public void showFailedOverlay() {
		CCScene nextScene = ProcessenLevel1FailedLayer.scene();

		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

	}
}
