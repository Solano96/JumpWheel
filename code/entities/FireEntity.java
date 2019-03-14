package com.jumpwheel.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jumpwheel.Constants;

/**
 * Created by MiToshiba on 22/08/2016.
 */

public class FireEntity extends Actor {

    private TextureRegion texture[];

    private World world;

    private Body body;

    private Fixture fixture;

    private float k = 0;

    private boolean subir = true;

    public FireEntity(World world, Texture texture[], float x, float y) {
        this.world = world;
        this.texture = new TextureRegion[4];

        for(int i = 0; i < 4; i++){
            this.texture[i] = new TextureRegion(texture[i]);
        }

        BodyDef def = new BodyDef();
        def.position.set(x, y - 0.5f);
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(-0.25f, -0.45f);
        vertices[1] = new Vector2(0.25f, -0.45f);
        vertices[2] = new Vector2(0, 0.45f);
        box.set(vertices);                          // (4) And put them in the shape.
        fixture = body.createFixture(box, 8);       // (5) Create the fixture.
        fixture.setFriction(1);
        fixture.setUserData("fire");               // (6) And set the user data to enemy.
        box.dispose();                              // (7) Destroy the shape when you don't need it.

        setSize(Constants.PIXELS_IN_METER, Constants.PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * Constants.PIXELS_IN_METER, (body.getPosition().y-0.5f) * Constants.PIXELS_IN_METER);
        setRotation(body.getAngle()*(360/3.141592f));
        //batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        batch.draw (texture[(int)k], getX(), getY(), getWidth()/2, getHeight()/2, getWidth(), getHeight(), 1, 1, getRotation());

        if(subir) {
            k += 0.2f;
            if(k > 3.8f) subir = false;
        }
        else{
            k-=0.2f;
            if(k < 0.2f) subir = true;
        }
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

}
