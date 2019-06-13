package com.mygdx.game;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;


public class TextAreaAccessor implements TweenAccessor<TextArea> {
    public static final int ALPHA = 0;
    /**
     * implemented getValues method, used to get the targeted value from the target TextArea.
     * @param target target TextArea
     * @param tweenType value to be accessed
     * @param returnValues values are placed here
     * @return
     */
    @Override
    public int getValues(TextArea target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case ALPHA:
                returnValues[0] = target.getColor().a;
                return 1;
                default:
                    assert false;
                    return -1;
        }
    }

    /**
     * Sets values of target.
     * @param target TextArea to be modified.
     * @param tweenType which property to modify
     * @param newValues values are stored here
     */
    @Override
    public void setValues(TextArea target, int tweenType, float[] newValues) {
        switch (tweenType){
            case ALPHA:
                target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
                break;
                default:
                    assert false;
        }
    }
}
