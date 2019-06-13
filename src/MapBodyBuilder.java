package com.mygdx.game;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

/**
 * @author daemonexmachina
 * @author Andy Cai
 * @version 2.0 Additional methods and funcionalities added to suit my needs.
 */
//This class was originally created by daemonexmachina in a StackOverflow Answer. It has since been edited with more methods such as an overload for buildShapes, buildShapesX and buildShapesY.
//The original code can be found at this link: https://gamedev.stackexchange.com/questions/66924/how-can-i-convert-a-tilemap-to-a-box2d-world

public class MapBodyBuilder {

    // The pixels per tile. If your tiles are 16x16, this is set to 16f
    private static float ppt = 16;
    private static float x = 0;
    private static float y = 0;

    /**
     * Builds box2d bodies out of objects in tilemap
     * @param map map
     * @param pixels pixels per tile, used for pixel to meter translations
     * @param world box2d world
     * @param layer layer where objects are
     * @param sensor if objects should be sensors (i.e. no collision but collision can still be detected)
     * @param friction friction of the objects
     * @return
     */
    public static Array<Body> buildShapes(Map map, float pixels, World world, String layer, boolean sensor, float friction) {
        ppt = pixels;
        MapObjects objects = map.getLayers().get(layer).getObjects();

        Array<Body> bodies = new Array<Body>();

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape;

            if (object instanceof RectangleMapObject) {
                
                shape = getRectangle((RectangleMapObject)object);
                
            }
            else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject)object);
            }
            else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject)object);
            }
            else if (object instanceof CircleMapObject) {
                shape = getCircle((CircleMapObject)object);
            }
            else {
                continue;
            }

            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bd);
            body.getPosition().set(x, y);
            FixtureDef def = new FixtureDef();
            def.density = 1;
            def.friction = friction;
            def.shape = shape;
            def.isSensor = sensor;
            body.createFixture(def);
            bodies.add(body);
            shape.dispose();
        }
        return bodies;
    }

    /**
     * Gets a list of X values of the objects in the TMX file.
     * @param map
     * @param pixels
     * @param world
     * @param layer
     * @param sensor
     * @param friction
     * @return
     */
    public static Array<Float> buildShapesX(Map map, float pixels, World world, String layer, boolean sensor, float friction) {
        ppt = pixels;
        MapObjects objects = map.getLayers().get(layer).getObjects();

        Array<Float> floats = new Array<Float>();

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape;

            if (object instanceof RectangleMapObject) {
                
                shape = getRectangle((RectangleMapObject)object);
                
                floats.add(((RectangleMapObject) object).getRectangle().getX());
            }
            else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject)object);
            }
            else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject)object);
            }
            else if (object instanceof CircleMapObject) {
                shape = getCircle((CircleMapObject)object);
            }
            else {
                continue;
            }

        }
        return floats;
    }

    /**
     * Gets a list of heights of objects in TMX
     * @param map
     * @param pixels
     * @param world
     * @param layer
     * @param sensor
     * @param friction
     * @return
     */
    public static Array<Float> buildShapesY(Map map, float pixels, World world, String layer, boolean sensor, float friction) {
        ppt = pixels;
        MapObjects objects = map.getLayers().get(layer).getObjects();

        Array<Float> floats = new Array<Float>();

        for(MapObject object : objects) {

            if (object instanceof TextureMapObject) {
                continue;
            }

            Shape shape;

            if (object instanceof RectangleMapObject) {
                shape = getRectangle((RectangleMapObject)object);
                
                floats.add(((RectangleMapObject) object).getRectangle().getHeight());
            }
            else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject)object);
            }
            else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject)object);
            }
            else if (object instanceof CircleMapObject) {
                shape = getCircle((CircleMapObject)object);
            }
            else {
                continue;
            }

        }
        return floats;
    }


    private static PolygonShape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        x = rectangle.x;
        y = rectangle.y;
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / ppt,
                (rectangle.y + rectangle.height * 0.5f ) / ppt);
        polygon.setAsBox(rectangle.width * 0.5f / ppt,
                rectangle.height * 0.5f / ppt,
                size,
                0.0f);
        return polygon;
    }

    private static CircleShape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / ppt);
        circleShape.setPosition(new Vector2(circle.x / ppt, circle.y / ppt));
        return circleShape;
    }

    private static PolygonShape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            
            worldVertices[i] = vertices[i] / ppt;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private static ChainShape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / ppt;
            worldVertices[i].y = vertices[i * 2 + 1] / ppt;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }
}