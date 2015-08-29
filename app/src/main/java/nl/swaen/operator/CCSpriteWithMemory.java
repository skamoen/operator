package nl.swaen.operator;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.nodes.CCSpriteSheet;
import org.cocos2d.opengl.CCTexture2D;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.graphics.Bitmap;

public class CCSpriteWithMemory extends CCSprite {
	CGPoint initialPosition = null;
	float initialRotation = 0;

	public void setInitialPosition(CGPoint position) {
		this.initialPosition.set(position.x, position.y);
	}

	public CCSpriteWithMemory(CCTexture2D texture) {
		super(texture);
	}

	public CCSpriteWithMemory() {
		super();

	}

	public CCSpriteWithMemory(Bitmap image, String key) {
		super(image, key);

	}

	public CCSpriteWithMemory(CCSpriteFrame spriteFrame) {
		super(spriteFrame);

	}

	public CCSpriteWithMemory(CCSpriteSheet spritesheet, CGRect rect) {
		super(spritesheet, rect);

	}

	public CCSpriteWithMemory(CCTexture2D texture, CGRect rect) {
		super(texture, rect);

	}

	public CCSpriteWithMemory(String spriteFrameName, boolean isFrame) {
		super(spriteFrameName, isFrame);

	}

	public CCSpriteWithMemory(String filepath, CGRect rect) {
		super(filepath, rect);

	}

	public CCSpriteWithMemory(String filepath) {
		super(filepath);

	}

}
