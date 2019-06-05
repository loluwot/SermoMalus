package com.mygdx.game;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ButtonAccessor implements TweenAccessor<TextButton> {
    public static final int X = 0;
    public static final int Y = 1;
    public static final int XY = 2;
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
