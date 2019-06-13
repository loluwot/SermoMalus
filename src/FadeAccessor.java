package com.mygdx.game;

import aurelienribon.tweenengine.TweenAccessor;

public class FadeAccessor implements TweenAccessor<Fade> {
    public static final int ALPHA = 0;
    /**
     * implemented getValues method, used to get the targeted value from the target Fade.
     * @param target target Fade
     * @param tweenType value to be accessed
     * @param returnValues values are placed here
     * @return
     */
    @Override
    public int getValues(Fade target, int tweenType, float[] returnValues) {
        switch(tweenType){
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
     * @param target Fade to be modified.
     * @param tweenType which property to modify
     * @param newValues values are stored here
     */
    @Override
    public void setValues(Fade target, int tweenType, float[] newValues) {
        switch (tweenType){
            case ALPHA:
                target.alpha = newValues[0];
                default:
                    assert false;
        }
    }
}
