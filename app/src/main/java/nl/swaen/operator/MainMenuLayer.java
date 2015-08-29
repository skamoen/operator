package nl.swaen.operator;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.ccColor3B;

import android.content.Intent;
import android.util.Log;

public class MainMenuLayer extends CCLayer {
	private static final String TAG = "POPERATOR";

	public static CCScene scene() {
		CCScene scene = CCScene.node();
		CCLayer layer = new MainMenuLayer();

		scene.addChild(layer);

		return scene;
	}

	protected MainMenuLayer() {
		CGSize winSize = CCDirector.sharedDirector().displaySize();

		CCMenuItemSprite menuItem1 = MenuLayer.createMenuItemWithImage("Processen", false, this, "processenMenu");
		CCMenuItemSprite menuItem2 = MenuLayer.createMenuItemWithImage("Beroepen", false, this, "beroepenMenu");
		CCMenuItemSprite menuItem3 = MenuLayer.createMenuItemWithImage("Bedrijven", false, this, "bedrijvenMenu");

		CCMenu mainMenu = CCMenu.menu(menuItem1, menuItem2, menuItem3);

		mainMenu.alignItemsVertically();

		addChild(mainMenu, 1);

	}
	public void processenMenu(Object sender) {
		Log.d(TAG, "Going to processenMenu");

		CCScene nextScene = MenuProcessenLayer.scene();

		CCDirector.sharedDirector().pushScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

	}

	public void beroepenMenu(Object sender) {
		Log.d(TAG, "Going to beroepen");

		CCScene nextScene = BeroepenLevel1Layer.scene();

		CCDirector.sharedDirector().pushScene(CCFadeTransition.transition(1, nextScene, ccColor3B.ccWHITE));

	}

	public void bedrijvenMenu(Object sender) {
		Log.d(TAG, "Going to beroepenMenu");

		Intent intent = new Intent(CCDirector.sharedDirector().getActivity(), BedrijvenActivity.class);
		CCDirector.sharedDirector().getActivity().startActivity(intent);

	}

}
