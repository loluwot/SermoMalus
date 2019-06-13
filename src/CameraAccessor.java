package com.mygdx.game;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Tween Accessor for Orthographic Camera
 * Essentially, tween engine works by looking at the current value of a property, incrementing it closer to the target using a formula (tween equations), and then stopping when reaching the target.
 * This allows for very smooth animation to be possible.
 * @author Andy Cai
 * @version 1.0
 */
public class CameraAccessor implements TweenAccessor<OrthographicCamera> {
    public static final int X = 0;
    public static final int Y = 1;

    /**
     * implemented getValues method, used to get the targeted value from the target OrthographicCamera.
     * @param target target OrthographicCamera
     * @param tweenType value to be accessed
     * @param returnValues values are placed here
     * @return
     */
    @Override
    public int getValues(OrthographicCamera target, int tweenType, float[] returnValues) {
        switch(tweenType){
            case Y:
                returnValues[0] = target.position.y;
                return 1;
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
    public void setValues(OrthographicCamera target, int tweenType, float[] newValues) {
        switch(tweenType){

            case Y:
                target.position.y = newValues[0];
                default:
                    assert false;
        }
    }
}
