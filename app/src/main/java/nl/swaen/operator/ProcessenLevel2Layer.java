package nl.swaen.operator;

import java.util.ArrayList;

import org.cocos2d.actions.CCProgressTimer;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCFiniteTimeAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveTo;
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

import android.util.Log;
import android.view.MotionEvent;

import nl.swaen.operator.CCInstructionLayer;

public class ProcessenLevel2Layer extends CCLayer {

	private final CGSize winSize = CCDirector.sharedDirector().displaySize();
	CCInstructionLayer overlay;
	CCColorLayer gameLayer;
	private static final String TAG = "POPERATOR";
	private final float xScale = winSize.width / 320;
	private final float yScale = winSize.height / 480;

	private final ArrayList<CCSprite> movableSprites = new ArrayList<CCSprite>();
	private final ArrayList<CCSprite> addedIngredients = new ArrayList<CCSprite>();

	CCSprite theelepel;
	CCSprite progressBorder;
	CCProgressTimer progressBar;
	CCSprite ketelBg;
	CCSprite ketel;
	CCSprite draaiJetser;
	CCSprite emmer;
	CCSprite zakje;

	private CCSprite selSprite = null;
	private int selIndex;

	private final CGPoint[] initialPos = new CGPoint[3];
	private final float[] initialRot = new float[3];
	private CGPoint oldLocation;

	public static CCScene scene() {
		CCScene scene = CCScene.node();
		CCLayer layer = new ProcessenLevel2Layer();

		scene.addChild(layer);

		return scene;
	}

	protected ProcessenLevel2Layer() {
		gameLayer = CCColorLayer.node(ccColor4B.ccc4(255, 255, 255, 255));
		addChild(gameLayer);
		setUpMenus();
		overlay = new CCInstructionLayer();
		overlay = overlay.setUpWithTitle("Maak Haargel", "animate", "Start", "remove");
		setUpLevel();
		addChild(overlay);
		setIsTouchEnabled(true);
	}

	public void setUpLevel() {
		ketel = CCSprite.sprite("ketel.png");
		ketel.setPosition(MenuLayer.iPos(CGPoint.ccp(170, 180)));
		ketel.setScaleX(xScale);
		ketel.setScaleY(yScale);

		ketelBg = CCSprite.sprite("greenPixel.png");
		ketelBg.setAnchorPoint(0, 0);
		ketelBg.setPosition(ketel.getPosition().x - CGRect.width(ketel.getBoundingBox()) / 2, ketel.getPosition().y
				- CGRect.height(ketel.getBoundingBox()) / 3.4f);
		ketelBg.setScaleX(CGRect.width(ketel.getBoundingBox()) / 2.18f);
		ketelBg.setScaleY(CGRect.height(ketel.getBoundingBox()) / 25);
		gameLayer.addChild(ketelBg);

		gameLayer.addChild(ketel);

		draaiJetser = CCSprite.sprite("draaiJetser.png");
		draaiJetser.setPosition(MenuLayer.iPos(CGPoint.ccp(87, 83)));
		draaiJetser.setScaleX(xScale);
		draaiJetser.setScaleY(yScale);
		gameLayer.addChild(draaiJetser);

		theelepel = CCSprite.sprite("theelepel.png");
		theelepel.setPosition(MenuLayer.iPos(CGPoint.ccp(250, 400)));
		theelepel.setRotation(10);
		theelepel.setScale(xScale);
		initialPos[0] = theelepel.getPosition();
		initialRot[0] = theelepel.getRotation();
		gameLayer.addChild(theelepel);

		emmer = CCSprite.sprite("emmertje.png");
		emmer.setPosition(MenuLayer.iPos(CGPoint.ccp(250, 280)));
		emmer.setFlipX(true);
		emmer.setRotation(40);
		emmer.setScale(xScale * 0.8f);
		initialPos[1] = emmer.getPosition();
		initialRot[1] = emmer.getRotation();
		gameLayer.addChild(emmer);

		zakje = CCSprite.sprite("zakje.png");
		zakje.setPosition(MenuLayer.iPos(CGPoint.ccp(250, 150)));
		zakje.setFlipX(true);
		zakje.setScale(0.9f);
		initialPos[2] = zakje.getPosition();
		initialRot[2] = zakje.getRotation();
		gameLayer.addChild(zakje);

		movableSprites.add(theelepel);
		movableSprites.add(emmer);
		movableSprites.add(zakje);

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

		// if (CGRectContainsPoint(spiraal.boundingBox, touchLocation)) {
		// [self checkIngredienten:self];
		// NSLog(@"knop");
		// }

		if (CGRect.containsPoint(draaiJetser.getBoundingBox(), touchLocation)) {
			checkIngredienten();
		}
		for (int i = 0; i < movableSprites.size(); i++) {
			CCSprite sprite = movableSprites.get(i);
			if (CGRect.containsPoint(sprite.getBoundingBox(), touchLocation)) {
				newSprite = sprite;
				selIndex = i;
				break;
			}
		}

		if (newSprite != null && newSprite != selSprite) {
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
		if (selSprite != null) {
			CGPoint touchLoction = this.convertTouchToNodeSpace(event);

			CGPoint translation = CGPoint.ccpSub(touchLoction, oldLocation);
			this.panForTranslation(translation);

			oldLocation = this.convertTouchToNodeSpace(event);
			return true;
		}
		return false;

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

		// make bounding box scalable.
		CGPoint box1 = MenuLayer.iPos(CGPoint.ccp(10, 87));
		CGPoint box2 = MenuLayer.iPos(CGPoint.ccp(145, 200));

		if (selSprite != null && CGRect.containsPoint(CGRect.make(box1.x, box1.y, box2.x, box2.y), touchLocation)) {
			Log.d(TAG, "touches ended in boundingbox");
			CCFiniteTimeAction action1 = CCMoveTo.action(0.2f,
					MenuLayer.iPos(CGPoint.ccp((145 / 2 + 10), (200 / 2 + 87))));
			CCFiniteTimeAction action2 = CCSpawn.actions(CCScaleTo.action(0.2f, 0), CCRotateBy.action(0.2f, 720));

			CCFiniteTimeAction actionCallFunc = CCCallFunc.action(this, "ingredientAdded");

			if (selSprite.equals(theelepel)) {
				addedIngredients.add(theelepel);
				Log.d(TAG, "theelepel added");
			} else if (selSprite.equals(emmer)) {
				addedIngredients.add(emmer);
				Log.d(TAG, "emmer added");
			} else if (selSprite.equals(zakje)) {
				addedIngredients.add(zakje);
				Log.d(TAG, "zakje; added");
			}

			Log.d(TAG, "addedIngredients size: " + addedIngredients.size());

			CCAction actionSequence = CCSequence.actions(action1, action2, actionCallFunc);
			selSprite.runAction(actionSequence);
			// TODO Play bubblesound [[SimpleAudioEngine sharedEngine]
			// playEffect:@"largeBubble.wav"]

			selSprite = null;
		} else if (selSprite != null) {
			selSprite.stopAllActions();
			selSprite.runAction(CCRotateTo.action(0.1f, 0));
			selSprite.runAction(CCMoveTo.action(0.1f, initialPos[selIndex]));
			selSprite.runAction(CCRotateTo.action(0.1f, initialRot[selIndex]));
			selSprite = null;
		}

		return true;

	}

	public void ingredientAdded() {
		Log.d(TAG, "ingredient gedropt op juiste plek");
		float newPercentage = 0;
		float ketelHeight = ketel.getBoundingBox().size.height * 0.64f;
		float ketelCorr = ketelHeight * 0.33f;

		CCAction actionCallFunc = CCAction.action();

		Log.d(TAG, "addedIngredients size: " + addedIngredients.size());

		if (addedIngredients.size() < 3) {
			// 0.0225
			newPercentage = (ketelHeight - ketelCorr) / 4 + ketelCorr * addedIngredients.size();
			actionCallFunc = CCSequence.actions(CCScaleTo.action(0.1f, xScale, yScale));

		} else if (addedIngredients.size() < 4) {
			Log.d(TAG, "Ketelheight: " + ketelHeight);
			newPercentage = ketelHeight;
			actionCallFunc = CCRepeatForever.action(CCSequence.actions(CCScaleTo.action(0.8f, 1.6f, 1.6f),
					CCScaleTo.action(0.8f, 1.0f, 1.0f), CCScaleTo.action(0.8f, 1.6f, 1.6f),
					CCScaleTo.action(0.8f, 1.0f, 1.0f)));
		}

		CCAction actionSequence = CCSequence.actions(CCScaleTo.action(0.8f, ketelBg.getScaleX(), newPercentage));

		ketelBg.runAction(actionSequence);
		draaiJetser.runAction(actionCallFunc);

	}
	void checkIngredienten() {
		Log.i(TAG, "Checking ingredients for correct combo");

		if (addedIngredients.size() == 3) {
			if (addedIngredients.get(0).equals(emmer) && addedIngredients.get(1).equals(theelepel)
					&& addedIngredients.get(2).equals(zakje)) {
				showSuccesOverlay();
			}
			Log.i(TAG, "Wrong combo");
			showFailedOverlay();
		} else {
			showFailedOverlay();
		}
	}

	public void showSuccesOverlay() {
		CCScene nextScene = ProcessenLevel2CompleteLayer.scene();
		// CCScene nextScene = MenuProcessenLayer.scene();

		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

	}
	public void showFailedOverlay() {
		CCScene nextScene = ProcessenLevel2FailedLayer.scene();

		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

	}
}
