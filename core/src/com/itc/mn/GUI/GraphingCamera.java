package com.itc.mn.GUI;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * This class will be holding the basics of the graphing camera stuff
 */
public class GraphingCamera extends Stage {

    public GraphingCamera(Viewport viewport, final OrthographicCamera camera, final FrontEnd gui_stage) {
        super(viewport);
        addListener(new ActorGestureListener() {

            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                super.pan(event, x, y, deltaX, deltaY);
                if (y > -230) // Zona muerta para que al interactuar con los sliders o el TextField, no haga panning
                    camera.position.set(camera.position.x - deltaX * camera.zoom, camera.position.y - deltaY * camera.zoom, 0);
            }

            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                super.zoom(event, initialDistance, distance);
                float diff = initialDistance - distance;
                if (diff > 0)
                    camera.zoom += (camera.zoom < 1) ? camera.zoom * 0.01f : 0;
                else
                    camera.zoom -= (camera.zoom > 0.02f) ? camera.zoom * 0.01f : 0;
            }

            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Para eventos tactiles de android, usamos puntero, para PC usamos button
                if (button == 1)
                    gui_stage.getMenu().showMenu(gui_stage, x, y);
            }

            @Override
            public boolean longPress(Actor actor, float x, float y) {
                gui_stage.getMenu().showMenu(gui_stage, x, y);
                return true;
            }

            @Override
            public boolean handle(Event e) {
                return super.handle(e);
            }
        });
        addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                // Esto se encarga de controlar el zoom con el teclado
                // El Zoom de la camara va de 0f a 1f, siendo 0f lo mas cercano, pero
                // causa problemas al renderizar, por eso se limita a 0.02f
                if (event.getKeyCode() == Input.Keys.DOWN) { // Zoom menos
                    camera.zoom += (camera.zoom < 1) ? 0.01f : 0;
                    return true;
                } else if (event.getKeyCode() == Input.Keys.UP) { // Zoom mas
                    camera.zoom -= (camera.zoom > 0.02f) ? 0.01f : 0;
                    return true;
                }
                return super.keyTyped(event, character);
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                if (amount > 0)
                    camera.zoom += (camera.zoom < 1) ? 0.015f : 0;
                else
                    camera.zoom += (camera.zoom > 0.02f) ? -0.015f : 0;
                return super.scrolled(event, x, y, amount);
            }
        });
    }
}
