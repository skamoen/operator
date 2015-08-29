package nl.swaen.operator;

import android.util.Log;
import android.view.MotionEvent;

import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCRotateTo;
import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemLabel;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.particlesystem.CCParticleSmoke;
import org.cocos2d.particlesystem.CCParticleSystem;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;
import org.cocos2d.types.ccColor4F;

import java.util.ArrayList;

public class ProcessenLevel3WeegschaalLayer extends CCLayer {
    CCInstructionLayer overlay;
    CCColorLayer gameLayer;

    private final CGSize winSize = CCDirector.sharedDirector().displaySize();
    private final float xScale = winSize.width / 320;
    private final float yScale = winSize.height / 480;

    CCSprite weegschaal;
    CCSprite hoop;
    CCSprite pijl;
    CCSprite meelpak;

    CCParticleSystem particleSystem;

    private final ArrayList<CCSprite> movableSprites = new ArrayList<CCSprite>();

    private final CGPoint[] initialPos = new CGPoint[1];
    private final float[] initialRot = new float[1];
    private CCSprite selSprite = null;
    private CGPoint oldLocation;
    private CGPoint touchLocation;

    int phase;
    int progress = 0;

    public static CCScene scene() {
        CCScene scene = CCScene.node();
        CCLayer layer = new ProcessenLevel3WeegschaalLayer();

        scene.addChild(layer);

        return scene;
    }

    protected ProcessenLevel3WeegschaalLayer() {
        gameLayer = CCColorLayer.node(ccColor4B.ccc4(255, 255, 255, 255));
        addChild(gameLayer);
        setUpMenus();
        overlay = new CCInstructionLayer();
        overlay = overlay.setUpWithTitle("Meel afwegen", "animate", "Start", "remove");
        setIsTouchEnabled(true);
        phase = 1;

        setUpLevel();
        addChild(overlay);
        scheduleUpdate();
    }

    public void update(float dt) {
        if (phase == 2) {
            progress++;
            if (progress < 200) {
                hoop.setScale(0.1f + (1 - 0.1f) * progress / 200);
                pijl.setPosition(MenuLayer.iPos(CGPoint.ccp(135.0f + (208 - 135) * progress / 200, pijl.getPosition().y)));
                Log.d("Progress", "Voortgang is " + progress + "/200");
            } else {
                phase = 1;
                particleSystem.stopSystem();
                // ccTouchesEnded(null);
            }
        }
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
        weegschaal = CCSprite.sprite("weegschaal.png");
        weegschaal.setPosition(MenuLayer.iPos(CGPoint.ccp(160, 110)));
        weegschaal.setScaleX(xScale);
        weegschaal.setScaleY(yScale);
        gameLayer.addChild(weegschaal);

        hoop = CCSprite.sprite("hoop.png");
        hoop.setAnchorPoint(CGPoint.ccp(0.5f, 0));
        hoop.setPosition(MenuLayer.iPos(CGPoint.ccp(160, 142)));
        hoop.setScaleX(xScale * 0.1f);
        hoop.setScaleY(yScale * 0.1f);
        gameLayer.addChild(hoop);

        pijl = CCSprite.sprite("arrow.png");
        pijl.setPosition(MenuLayer.iPos(CGPoint.ccp(135, 96)));
        pijl.setScaleX(xScale * 0.1f);
        pijl.setScaleY(yScale * 0.1f);
        gameLayer.addChild(pijl);

        particleSystem = CCParticleSmoke.node();
        particleSystem.setPosition(MenuLayer.iPos(CGPoint.ccp(160, 300)));
        particleSystem.setAngle(-90);
        particleSystem.setAngleVar(20);
        particleSystem.setSpeed(40);
        particleSystem.setEmissionRate(30);
        particleSystem.setLife(3);
        particleSystem.setStartColor(ccColor4F.ccc4FFromccc4B(ccColor4B.ccc4(0, 0, 0, 255)));
        particleSystem.setBlendAdditive(true);
        // particleSystem.stopSystem();
        gameLayer.addChild(particleSystem);

        meelpak = CCSprite.sprite("meelzak.png");
        meelpak.setAnchorPoint(1, 0);
        meelpak.setPosition(MenuLayer.iPos(CGPoint.ccp(330, 340)));
        initialPos[0] = meelpak.getPosition();
        meelpak.setScaleX(xScale * 1.5f);
        meelpak.setScaleY(yScale * 1.5f);
        meelpak.setRotation(15);
        initialRot[0] = meelpak.getRotation();
        gameLayer.addChild(meelpak);

        movableSprites.add(meelpak);

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
        Log.d("Touch began select ", "" + selSprite);
        if (selSprite != null)
            return true;
        else
            return false;

    }

    public void selectSpriteForTouch(CGPoint touchLocation) {
        CCSprite newSprite = null;

        for (CCSprite sprite : movableSprites) {
            if (CGRect.containsPoint(sprite.getBoundingBox(), touchLocation)) {
                newSprite = sprite;
                break;
            }
        }

        if (newSprite != null) {
            if (newSprite != selSprite) {
                if (selSprite != null) {
                    selSprite.stopAllActions();

                    selSprite = newSprite;
                    selSprite.runAction(CCMoveTo.action(0.1f, initialPos[0]));
                }

                initialPos[0] = newSprite.getPosition();
                selSprite = newSprite;

            }
        }
    }

    @Override
    public boolean ccTouchesMoved(MotionEvent event) {

        if (selSprite != null && selSprite.equals(meelpak)) {
            oldLocation = touchLocation;
            touchLocation = convertTouchToNodeSpace(event);

            float dy = touchLocation.y - initialPos[0].y;
            float newAngle = dy / 3 + initialRot[0];

            if (newAngle < -40) {
                newAngle = -40;
            }

            meelpak.setRotation(newAngle);

            if (meelpak.getRotation() < 0) {
                if (phase != 2) {
                    particleSystem.resetSystem();
                } else {
                    particleSystem.setPosition(CGPoint.ccp(particleSystem.getPosition().x, meelpak.getPosition().y));
                }
                phase = 2;
            } else {
                phase = 3;
                particleSystem.stopSystem();
            }
            Log.d("Angle", "New angle: " + newAngle);
        }

        return true;
    }

    @Override
    public boolean ccTouchesEnded(MotionEvent event) {
        phase = 1;
        particleSystem.stopSystem();
        if (selSprite != null)
            selSprite.runAction(CCRotateTo.action(0.1f, initialRot[0]));
        selSprite = null;

        return true;
    }
}
