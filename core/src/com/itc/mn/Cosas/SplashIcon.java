package com.itc.mn.Cosas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.itc.mn.Pantallas.RenderScreen;

/**
 * Created by zero_ on 16/10/2015.
 */
public class SplashIcon extends Actor {

    private Sprite icon = new Sprite(new Texture(Gdx.files.internal("splash.png")));
    private Game game;
    private RenderScreen renderScreen;

    public SplashIcon(final Game game, final RenderScreen renderScreen){
        this.game = game;
        this.renderScreen = renderScreen;
        setPosition((Gdx.graphics.getWidth()-icon.getWidth())/2f, (Gdx.graphics.getHeight()-icon.getHeight())/2f);
        setSize(icon.getWidth(), icon.getHeight());
        setColor(1, 1, 1, 0);
        icon.setColor(1, 1, 1, 0);
    }

    public Actor introAnimation(){
        Action changeScreen = new Action() {
            @Override
            public boolean act(float delta) {
                switchScreen();
                return false;
            }
        };
        addAction(Actions.sequence(Actions.fadeIn(0.5f), Actions.delay(0.2f), Actions.fadeOut(0.5f), changeScreen));
        return this;
    }

    private void switchScreen(){
        game.setScreen(renderScreen);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        icon.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        icon.draw(batch);
        icon.setColor(getColor().r, getColor().g, getColor().b, getColor().a);
        if(Gdx.input.isTouched())
            switchScreen();
    }
}
