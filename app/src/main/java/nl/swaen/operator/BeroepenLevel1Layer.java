package nl.swaen.operator;

import java.util.ArrayList;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCParallaxNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import android.util.Log;
import android.view.MotionEvent;

public class BeroepenLevel1Layer extends CCLayer {
	private final CGSize winSize = CCDirector.sharedDirector().displaySize();
	CCColorLayer gameLayer = CCColorLayer.node(ccColor4B.ccc4(255, 255, 255, 255));
	private static final String TAG = "BEROEPEN";

	// sprites
	CCSprite operator;
	CCSprite wb;
	CCSprite ontwikkelaar;
	CCSprite laborant;
	CCSprite touchBeganIn;

	// layers
	CCParallaxNode backgroundNode;

	CGPoint oldLocation;
	float velocity;
	ArrayList<Boolean> status;

	public static CCScene scene() {
		CCScene scene = CCScene.node();
		CCLayer layer = new BeroepenLevel1Layer();

		scene.addChild(layer);

		return scene;
	}

	protected BeroepenLevel1Layer() {
		Log.w(TAG, "Setting up level 1 layer");
		addChild(gameLayer);
		this.setIsTouchEnabled(true);
		setUpMenus();

		CCInstructionLayer overlay = new CCInstructionLayer();
		overlay = overlay.setUpWithTitle("Maak een brandstof verbeteraar", "introImageFullscreen.ccbi", "Start",
				"remove");

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
		CCScene nextScene = MainMenuLayer.scene();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));
	}

	public void setUpLevel() {

		float scale = (winSize.width / 320);

		backgroundNode = CCParallaxNode.node();
		this.addChild(backgroundNode);

		CCLayer achter = CCLayer.node();
		CCLayer background = CCLayer.node();
		CCLayer midden = CCLayer.node();
		CCLayer voor = CCLayer.node();

		// Set backgrounds for different layers
		CCSprite s = CCSprite.sprite("achtergrond2.png");
		s.setPosition(0 * 3652, 0);
		s.setScale(scale);
		background.addChild(s);

		CCSprite a = CCSprite.sprite("achter2.png");
		a.setPosition(0 * 2735, 0);
		a.setScale(scale);
		achter.addChild(a);

		CCSprite c = CCSprite.sprite("midden2_01.png");
		c.setPosition(-375, 0);
		c.setScale(scale);
		midden.addChild(c);

		CCSprite q = CCSprite.sprite("midden2_02.png");
		q.setPosition(375, 0);
		q.setScale(scale);
		midden.addChild(q);

		CCSprite b = CCSprite.sprite("voor2_01.png");
		b.setPosition(-500, 0);
		b.setScale(scale);
		voor.addChild(b);

		CCSprite w = CCSprite.sprite("voor2_02.png");
		w.setPosition(500, 0);
		w.setScale(scale);
		voor.addChild(w);

		operator = CCSprite.sprite("procesoperator2.png");
		operator.setPosition(0, 0);
		operator.setScale(scale);

		laborant = CCSprite.sprite("laborant2.png");
		laborant.setPosition(900, 0);
		laborant.setScale(1);

		wb = CCSprite.sprite("werktuigbouwkundige.png");
		wb.setPosition(-600, -150);
		wb.setScale(scale);

		ontwikkelaar = CCSprite.sprite("productontwikkelaar.png");
		ontwikkelaar.setPosition(450, -100);
		ontwikkelaar.setScale(scale);

		CCLayer poppetjesLayer = CCLayer.node();
		poppetjesLayer.addChild(ontwikkelaar);
		poppetjesLayer.addChild(wb);
		poppetjesLayer.addChild(laborant);
		poppetjesLayer.addChild(operator);

		CGPoint middenSpeed = CGPoint.ccp(0.0655f, 0.0655f);
		CGPoint achterSpeed = CGPoint.ccp(0.0378f, 0.0378f);
		CGPoint bgSpeed = CGPoint.ccp(0.01f, 0.01f);
		CGPoint voorSpeed = CGPoint.ccp(0.0933f, 0.0933f);
		CGPoint poppetjesSpeed = CGPoint.ccp(0.075f, 0.075f);

		backgroundNode.addChild(background, 7, bgSpeed.x, bgSpeed.y, 160, winSize.height / 2);
		backgroundNode.addChild(achter, 10, achterSpeed.x, achterSpeed.y, 160, winSize.height / 2);
		backgroundNode.addChild(midden, 13, middenSpeed.x, middenSpeed.y, 160, winSize.height / 2);
		backgroundNode.addChild(voor, 16, voorSpeed.x, voorSpeed.y, 160, winSize.height / 2);
		backgroundNode.addChild(poppetjesLayer, 14, poppetjesSpeed.x, poppetjesSpeed.y, 0, winSize.height / 2);

		CCSprite o2 = CCSprite.sprite("symb-o-c.png");
		CCSprite w2 = CCSprite.sprite("symb-w-c.png");
		CCSprite l2 = CCSprite.sprite("symb-l-c.png");
		CCSprite p2 = CCSprite.sprite("symb-p-c.png");

		o2.setVisible(false);
		w2.setVisible(false);
		l2.setVisible(false);
		p2.setVisible(false);

		CCSprite o1 = CCSprite.sprite("symb-o-bw.png");
		CCSprite w1 = CCSprite.sprite("symb-w-bw.png");
		CCSprite l1 = CCSprite.sprite("symb-l-bw.png");
		CCSprite p1 = CCSprite.sprite("symb-p-bw.png");

		p1.setScale(0.5f);
		p2.setScale(0.5f);
		o1.setScale(0.5f);
		o2.setScale(0.5f);
		l1.setScale(0.5f);
		l2.setScale(0.5f);
		w1.setScale(0.5f);
		w2.setScale(0.5f);

		w1.setOpacity(120);
		l1.setOpacity(120);
		o1.setOpacity(120);
		p1.setOpacity(120);

		o2.setPosition(o1.getPosition());
		w2.setPosition(w1.getPosition());
		l2.setPosition(l1.getPosition());
		p2.setPosition(p1.getPosition());

		addChild(o1);
		addChild(o2);
		addChild(w1);
		addChild(w2);
		addChild(l1);
		addChild(l2);
		addChild(p1);
		addChild(p2);

		status = new ArrayList<Boolean>();
		status.add(0, true);
		status.add(1, true);
		status.add(2, true);
		status.add(3, true);

		this.scheduleUpdate();
	}
	public void update(float dt) {
		// float dt = 0.1f;
		CGPoint backgroundScrollVel = CGPoint.ccp(velocity, 0);
		CGPoint a = CGPoint.ccpAdd(backgroundNode.getPosition(), CGPoint.ccpMult(backgroundScrollVel, dt));

		if (a.x < 9000 && a.x > -9000) {
			backgroundNode.setPosition(CGPoint.ccpAdd(backgroundNode.getPosition(),
					CGPoint.ccpMult(backgroundScrollVel, dt)));
			velocity = velocity * 0.95f;
		} else {
			backgroundNode.setPosition(backgroundNode.getPosition());
			velocity = 0;
		}
	}
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		ArrayList<CCSprite> boxes = new ArrayList<CCSprite>();
		boxes.add(operator);
		boxes.add(wb);
		boxes.add(ontwikkelaar);
		boxes.add(laborant);

		CGPoint touchLocation = convertTouchToNodeSpace(event);
		oldLocation = touchLocation;

		for (CCSprite spriet : boxes) {
			CGRect boundingBox = spriet.getBoundingBox();
			boundingBox.origin.y += 240;
			boundingBox.origin.x += backgroundNode.getPosition().x / 13.4104;

			if (CGRect.containsPoint(boundingBox, touchLocation)) {
				if (spriet.equals(operator) || spriet.equals(wb) || spriet.equals(ontwikkelaar)
						|| spriet.equals(laborant)) {
					touchBeganIn = spriet;

				}
			} else
				Log.d(TAG, "No popje touched");
		}

		return true;
	}

	@Override
	public boolean ccTouchesMoved(MotionEvent event) {
		CGPoint touchLocation = convertTouchToNodeSpace(event);

		CGPoint translation = CGPoint.ccpSub(touchLocation, oldLocation);
		velocity = translation.x * 200;

		CGPoint oldTouchLocation = touchLocation;
		return true;
	}

	@Override
	public boolean ccTouchesEnded(MotionEvent event) {
		CGPoint touchLocation = convertTouchToNodeSpace(event);

		ArrayList<CCSprite> boxes = new ArrayList<CCSprite>();
		boxes.add(operator);
		boxes.add(wb);
		boxes.add(ontwikkelaar);
		boxes.add(laborant);

		if (touchBeganIn != null) {
			for (CCSprite spriet : boxes) {
				CGRect boundingBox = spriet.getBoundingBox();
				boundingBox.origin.y += 240;
				boundingBox.origin.x += backgroundNode.getPosition().x / 13 / 4104;

				if (CGRect.containsPoint(boundingBox, touchLocation)) {
					Log.d(TAG, "touches ended in POPPEJES");
					this.pauseSchedulerAndActions();
					String img;
					if (spriet.equals(operator)) {
						img = "tekstprocesoperator.jpg";
						status.set(0, true);
					} else if (spriet.equals(wb)) {
						img = "tekstwerktuigbok.jpg";
						status.set(1, true);
					} else if (spriet.equals(ontwikkelaar)) {
						img = "tekstproductontw.jpg";
						status.set(2, true);
					} else if (spriet.equals(laborant)) {
						img = "tekstlaborantmedw.jpg";
						status.set(3, true);
						this.pauseSchedulerAndActions();
					}

					// TODO: CREATE TEKST LAYER

				} else {
					Log.d(TAG, "No popje touched");
				}
			}
			touchBeganIn = null;

		}

		CGPoint oldTouchLocation = touchLocation;
		return true;
	}
}
