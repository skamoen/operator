package nl.swaen.operator;

import android.util.Log;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemImage;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCLabel;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

public class MenuLayer extends CCLayer {
    int fontTopBar = 64;
    final static int fontMenuItem = 65;
    public static CGSize winSize = CCDirector.sharedDirector().displaySize();

    public static CCScene scene() {
        CCScene scene = CCScene.node();
        CCLayer layer = new MenuLayer();

        scene.addChild(layer);

        return scene;
    }

    protected MenuLayer() {
        CCSprite bg = CCSprite.sprite("backgroundBlue-hd.png");

        bg.setScaleX(winSize.width / bg.getTexture().getWidth());
        bg.setScaleY(winSize.height / bg.getTexture().getHeight());
        bg.setPosition(CGPoint.make(winSize.width / 2, winSize.height / 2));

        addChild(bg, 0);

        CCColorLayer topBar = CCColorLayer.node(ccColor4B.ccc4(71, 217, 242, 255), winSize.width,
                (winSize.height * 0.10f));
        CCLabel text = CCLabel.makeLabel("POPERATOR", "ARLRDBD.TTF", fontTopBar);
        text.setPosition(CGPoint.ccp(winSize.width / 1.75f, topBar.getHeight() / 2));
        topBar.addChild(text);

        CCMenuItemImage terugButton = CCMenuItemImage.item("terugButton-hd.png", "terugButtonActive-hd.png", this,
                "terugButton");
        terugButton.setScaleY((topBar.getHeight() / terugButton.getBoundingBox().size.height) * 0.7f);
        terugButton.setScaleX(terugButton.getScaleY());
        CCMenu backMenu = CCMenu.menu(terugButton);
        backMenu.alignItemsHorizontally();
        backMenu.setPosition(topBar.getWidth() * 0.075f, topBar.getHeight() / 2);
        topBar.addChild(backMenu, 1);

        topBar.setPosition(CGPoint.ccp(0, winSize.height - topBar.getHeight()));
        addChild(topBar);

    }

    public static CCMenuItemSprite createMenuItemWithImage(String text, boolean isDisabled, CCNode node, String action) {

        CCSprite buttonDefault = CCSprite.sprite("buttonBlue2-hd.png");

        CCSprite buttonActive = CCSprite.sprite("buttonBlueActive2-hd.png");
        CCSprite buttonDisabled = CCSprite.sprite("buttonBlueDisabled-hd.png");

        CCMenuItemSprite menuItem1 = CCMenuItemSprite.item(buttonDefault, buttonActive, buttonDisabled, node, action);
        CCLabel lblText = CCLabel.makeLabel(text, "ARLRDBD.TTF", fontMenuItem);

        menuItem1.setScaleX(winSize.width / buttonDefault.getTexture().getWidth());
        menuItem1.setScaleY((winSize.height / buttonDefault.getTexture().getHeight()) * .25f);

        menuItem1.addChild(lblText);
        lblText.setPosition(menuItem1.getContentSize().width / 2, menuItem1.getContentSize().height / 2.5f);
        if (isDisabled == true)
            menuItem1.setIsEnabled(!isDisabled);
        return menuItem1;
    }

    public void terugButton(Object sender) {
        Log.i("MenuLayer", "Going back!");
        CCScene nextScene = MenuLayer.scene();
        nextScene.addChild(new MainMenuLayer());

        CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));
    }

    public static CGPoint iPos(CGPoint oldPos) {
        float ih = 480;
        float iw = 320;

        oldPos.x = (oldPos.x / iw) * winSize.width;
        oldPos.y = (oldPos.y / ih) * winSize.height;

        return oldPos;
    }

}
