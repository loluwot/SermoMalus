package com.mygdx.game;

import aurelienribon.tweenengine.TweenAccessor;

public class EllipsePathAccessor implements TweenAccessor<EllipsePath> {
    public static final int ANGLE = 0;
    public static final int ALPHA = 1;/**
     * implemented getValues method, used to get the targeted value from the target EllipsePath.
     * @param target target EllipsePath
     * @param tweenType value to be accessed
     * @param returnValues values are placed here
     * @return
     */
    
    @Override
    public int getValues(EllipsePath target, int tweenType, float[] returnValues) {
        switch (tweenType){
            case ANGLE:
                returnValues[0] = target.angle;
                return 1;
            case ALPHA:
                returnValues[0] = target.alpha;
                return 1;
                default:
                    assert false;
                    return -1;
        }
    }
    /**
     * Sets values of target.
     * @param target EllipsePath to be modified.
     * @param tweenType which property to modify
     * @param newValues values are stored here
     */
    @Override
    public void setValues(EllipsePath target, int tweenType, float[] newValues) {
        switch(tweenType){
            case ANGLE:
                target.angle = newValues[0];
                break;
            case ALPHA:
                target.alpha = newValues[0];
                break;
                default:
                    assert false;
        }
    }
}
