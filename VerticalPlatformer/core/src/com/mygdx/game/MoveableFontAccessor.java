package com.mygdx.game;

import aurelienribon.tweenengine.TweenAccessor;

public class MoveableFontAccessor implements TweenAccessor<MovableText> {
    public static final int XY = 0;
    public static final int XYSIZEALPHA = 1;
    public static final int ALPHA = 2;
    public static final int TEXT = 3;
    @Override
    public int getValues(MovableText target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case XY:
                returnValues[0] = target.x;
                returnValues[1] = target.y;
                return 2;
            case XYSIZEALPHA:
                returnValues[0] = target.x;
                returnValues[1] = target.y;
                returnValues[2] = target.getSize();
                returnValues[3] = target.getAlpha();
                return 4;
            case ALPHA:
                returnValues[0] = target.getAlpha();
                return 1;
            case TEXT:
                returnValues[0] = target.numPeriods;
                return 1;
                default:
                    assert false;
                    return -1;
        }
    }

    @Override
    public void setValues(MovableText target, int tweenType, float[] newValues) {
        switch (tweenType){
            case XY:
                target.x = newValues[0];
                target.y = newValues[1];
                break;
            case XYSIZEALPHA:
                target.x = newValues[0];
                target.y = newValues[1];
                target.setSize(newValues[2]);
                target.setAlpha(newValues[3]);
                break;
            case ALPHA:
                target.setAlpha(newValues[0]);
                break;
            case TEXT:
                target.numPeriods = ((int)newValues[0]);
                break;
                default:
                    assert false;

        }
    }
}
