package com.jumpwheel.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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

public class FloorEntity extends Actor {

    private Texture floor;

    private World world;

    private Body body, leftBody;

    private Fixture fixture, leftFixture;

    private int n;

    public FloorEntity(World world, Texture floor, int x, int width, int y) {
        this.world = world;
        this.floor = floor;
        this.n = width;

        BodyDef def = new BodyDef();
        def.position.set(x + width / 2f, y - 0.5f);
        body = world.createBody(def);

        PolygonShape box = new PolygonShape();
        box.setAsBox(width / 2f, 0.5f);
        fixture = body.createFixture(box, 1);
        fixture.setRestitution(0);
        fixture.setUserData("floor");
        box.dispose();

        BodyDef leftDef = new BodyDef();
        leftDef.position.set(x+0.01f, y - 0.5f);
        leftBody = world.createBody(leftDef);

        PolygonShape leftBox = new PolygonShape();
        leftBox.setAsBox(0.01f, 0.35f);
        leftFixture = leftBody.createFixture(leftBox, 1);
        leftFixture.setRestitution(0);
        leftFixture.setUserData("spike");
        leftBox.dispose();

        setSize(width * Constants.PIXELS_IN_METER, Constants.PIXELS_IN_METER);
        setPosition(x * Constants.PIXELS_IN_METER, (y - 1) * Constants.PIXELS_IN_METER);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for(int i = 0; i < n; i++)
            batch.draw(floor, getX()+i*Constants.PIXELS_IN_METER, getY(), Constants.PIXELS_IN_METER, getHeight());
        // batch.draw(overfloor, getX(), getY() + 0.9f * getHeight(), getWidth(), 0.1f * getHeight());
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }
}
