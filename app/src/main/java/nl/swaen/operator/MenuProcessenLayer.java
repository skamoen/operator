package nl.swaen.operator;

import android.util.Log;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

public class MenuProcessenLayer extends CCLayer {

    public static CCScene scene() {
        CCScene scene = CCScene.node();
        CCLayer layer = new MenuLayer();

        scene.addChild(layer);
        scene.addChild(new MenuProcessenLayer());

        return scene;
    }

    protected MenuProcessenLayer() {
        CGSize winSize = CCDirector.sharedDirector().displaySize();
        // CCSprite bg = CCSprite.sprite("backgroundBlue-hd.png");

        // bg.setScaleX(winSize.width / bg.getTexture().getWidth());
        // bg.setScaleY(winSize.height / bg.getTexture().getHeight());
        // bg.setPosition(CGPoint.make(winSize.width / 2, winSize.height / 2));

        // addChild(bg);
        // TODO Insert Boolean values from MainActivity

        CCMenuItemSprite menuItem1 = MenuLayer.createMenuItemWithImage("Brandstof", false, this, "procesGame1");
        CCMenuItemSprite menuItem2 = MenuLayer.createMenuItemWithImage("Haargel", false, this, "procesGame2");
        CCMenuItemSprite menuItem3 = MenuLayer.createMenuItemWithImage("Cake", false, this, "procesGame3");

        CCMenu procesMenu = CCMenu.menu(menuItem1, menuItem2, menuItem3);

        procesMenu.alignItemsVertically();

        addChild(procesMenu, 1);
    }

    public void procesGame1(Object sender) {
        Log.i("Procesmenu", "procesGame1");

        CCScene nextScene = ProcessenLevel1Layer.scene();

        CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

    }

    public void procesGame2(Object sender) {
        Log.i("Procesmenu", "procesGame2");

        CCScene nextScene = ProcessenLevel2Layer.scene();

        CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

    }

    public void procesGame3(Object sender) {
        Log.i("Procesmenu", "procesGame3");

        CCScene nextScene = ProcessenLevel3Layer.scene();

        CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

    }
}
