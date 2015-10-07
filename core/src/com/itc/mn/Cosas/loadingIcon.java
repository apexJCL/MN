package com.itc.mn.Cosas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by zero_ on 05/10/2015.
 */
public class loadingIcon extends Actor {

    // Cargamos el TextureAtlas que previamente creamos con los sprites para carga
    private static TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("loading.txt"));
    private Animation animation;
    private float elapsedTime = 0;
    private float r_width, r_height;

    public loadingIcon(){
        // Cargamos la animacion
        animation = new Animation(1/15f, atlas.getRegions());
        r_width = atlas.getRegions().get(0).originalWidth;
        r_height = atlas.getRegions().get(0).originalHeight;
        setSize(r_width, r_height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(elapsedTime, true), (getParent().getWidth()-r_width)/2f, (getParent().getHeight()-r_height)/2f);
    }
}
