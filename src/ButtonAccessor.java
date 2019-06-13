package com.mygdx.game;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
/**
 * Tween Accessor for Text Buttons
 * Essentially, tween engine works by looking at the current value of a property, incrementing it closer to the target using a formula (tween equations), and then stopping when reaching the target.
 * This allows for very smooth animation to be possible.
 * @author Andy Cai
 * @version 1.0
 */
public class ButtonAccessor implements TweenAccessor<TextButton> {


    //targeted values
    public static final int X = 0;
    public static final int Y = 1;
    public static final int XY = 2;

    /**
     * implemented getValues method, used to get the targeted value from the target TextButton.
     * @param target target TextButton
     * @param tweenType value to be accessed
     * @param returnValues values are placed here
     * @return
     */
    @Override
    public int getValues(TextButton target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case X:
                returnValues[0] = target.getX();
                return 1;
            case Y:
                returnValues[0] = target.getY();
                return 1;
            case XY:
                returnValues[0] = target.getX();
                returnValues[1] = target.getY();
                return 2;
            default:
                assert false;
                return 0;
        }
    }

    /**
     * Sets values of target.
     * @param target TextButton to be modified.
     * @param tweenType which property to modify
     * @param newValues values are stored here
     */
    @Override
    public void setValues(TextButton target, int tweenType, float[] newValues) {
        switch(tweenType){
            case X:
                target.setPosition(newValues[0], target.getY());
                break;
            case Y:
                target.setPosition(target.getX(), newValues[0]);
                break;
            case XY:
                target.setPosition(newValues[0], newValues[1]);
                break;
                default:
                    assert false;
        }
    }
}
