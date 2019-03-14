package com.jumpwheel.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jumpwheel.Constants;


/**
 * Created by MiToshiba on 21/08/2016.
 */

public class PlayerEntity extends Actor {

    private Image texture;

    private World world;

    private Body body;

    private Fixture fixture;

    private boolean alive = true;

    private boolean jumping = true;

    private float rotacion;

    private boolean acelerar = false, saltar = false, trick = false, acelerar_trick;

    private float _y;

    public PlayerEntity(World world, Texture texture, Vector2 position) {
        this.world = world;
        this.texture = new Image(texture);

        // Create the player body.
        BodyDef def = new BodyDef();                // (1) Create the body definition.
        def.position.set(position);                 // (2) Put the body in the initial position.
        def.type = BodyDef.BodyType.DynamicBody;    // (3) Remember to make it dynamic.
        body = world.createBody(def);               // (4) Now create the body.


        CircleShape circle = new CircleShape();      // (1) Create the shape.
        circle.setRadius(0.5f);                  // (2) 1x1 meter box.
        fixture = body.createFixture(circle, 8);       // (3) Create the fixture.
        fixture.setFriction(2);
        fixture.setRestitution(0);

        fixture.setUserData("player");              // (4) Set the user data.
        circle.dispose();                              // (5) Destroy the shape.

        // Set the size to a value that is big enough to be rendered on the screen.
        setSize(Constants.PIXELS_IN_METER, Constants.PIXELS_IN_METER);
        rotacion = 0;
        _y = getY();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x - 0.5f) * Constants.PIXELS_IN_METER,
                (body.getPosition().y - 0.5f) * Constants.PIXELS_IN_METER);

        rotacion = body.getPosition().x*(360/3.141592f);
        rotacion = rotacion%360;

        texture.setColor(batch.getColor());
        texture.setSize(getWidth(), getHeight());
        texture.setPosition(getX(),getY());
        texture.setOriginX(getWidth()/2);
        texture.setOriginY(getHeight()/2);
        texture.setRotation(-rotacion);
        texture.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {

        acelerar = false;
        saltar = false;
        trick = false;
        acelerar_trick = false;

        for(int i = 0; i < 5; i++) {
            if (Gdx.input.isTouched(i) && Gdx.input.getY(i) > Gdx.graphics.getHeight()/8) {
                if (Gdx.input.getX(i) > Gdx.graphics.getWidth() / 2 )
                    saltar = true;
                else
                    acelerar = true;
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.A)) acelerar = true;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) saltar = true;
        if(Gdx.input.isKeyPressed(Input.Keys.D) && body.getLinearVelocity().y < 0.5f) trick = true;
        if(Gdx.input.isKeyPressed(Input.Keys.F)) acelerar_trick = true;

        float speedY;

        if(jumping)
            speedY = body.getLinearVelocity().y;
        else
            speedY = 0;

        if(acelerar_trick)
            body.setLinearVelocity(Constants.VELOCITY_TRICK, speedY);
        else if (alive && !acelerar)
            body.setLinearVelocity(Constants.PLAYER_SPEED, speedY);
        else if(alive)
            body.setLinearVelocity(Constants.PLAYER_ACELERATE, speedY);

        if(saltar || trick) this.jump();

        if (jumping) {
            body.applyForceToCenter(0, -Constants.IMPULSE_JUMP * 3.5f, true);
        }

    }

    public boolean getAcelerar(){
        return acelerar;
    }

    public float getVelocityX(){
        return body.getLinearVelocity().x;
    }

    public void setVelocity(float x, float y){body.setLinearVelocity(x, y);}

    public void jump() {
        if ((!jumping && alive && body.getLinearVelocity().y < 0.1f) || trick) {
            jumping = true;
            trick = false;
            Vector2 position = body.getPosition();
            body.applyLinearImpulse(0, Constants.IMPULSE_JUMP, position.x, position.y, true);
        }
    }

    public void detach() {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public float getAbcisa(){return getX();}
}