package com.itc.mn.Pantallas;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.itc.mn.Cosas.Const;
import com.itc.mn.Cosas.MyListener;

import java.util.ArrayList;

/**
 * Created by zero_ on 10/09/2015.
 */
public class RenderScreen implements Screen {

    private OrthographicCamera camera;
    private Stage stage;
    private Game game;
    private FitViewport viewport;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private float[][] valores;
    private ArrayList<float[][]> funciones;
    private Color[] colores = {Color.BLUE, Color.GREEN, Color.CYAN, Color.YELLOW,  Color.FIREBRICK, Color.ROYAL, Color.RED, Color.SALMON, Color.MAGENTA, Color.LIME, Color.TAN, Color.TEAL, Color.VIOLET};
    // Default
    {
        // Cosas a generar por defecto
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        // Definimos el viewport
        viewport = new FitViewport(Const.WIDTH, Const.HEIGHT);
        // Creamos las cosas para renderizar
        camera = new OrthographicCamera(Const.WIDTH, Const.HEIGHT);
        camera.setToOrtho(false);
        camera.position.set(0, 0, 0);
        camera.update();
        // Definimos el Stage para entradas tactiles
        stage = new Stage();
        stage.setViewport(viewport);
        // For moving
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new ActorGestureListener() {
            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                super.pan(event, x, y, deltaX, deltaY);
                camera.position.set(camera.position.x - deltaX * camera.zoom, camera.position.y - deltaY * camera.zoom, 0);
                System.out.println("Camera X: " + camera.position.x + " Camera Y: " + camera.position.y);
            }

            @Override
            public void zoom(InputEvent event, float initialDistance, float distance) {
                super.zoom(event, initialDistance, distance);
                float diff = initialDistance - distance;
                if (diff > 0)
                    camera.zoom += (camera.zoom < 1) ? 0.01f : 0;
                else
                    camera.zoom -= (camera.zoom > 0.02f) ? 0.01f : 0;

            }
        });
        stage.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                // Esto se encarga de controlar el zoom con el teclado
                // El Zoom de la camara va de 0f a 1f, siendo 0f lo mas cercano, pero
                // causa problemas al renderizar, por eso se limita a 0.02f
                if (event.getKeyCode() == Input.Keys.DOWN) { // Zoom menos
                    camera.zoom += (camera.zoom < 1) ? 0.01f : 0;
                    System.out.println("Camera Zoom: " + camera.zoom);
                    return true;
                } else if (event.getKeyCode() == Input.Keys.UP) { // Zoom mas
                    camera.zoom -= (camera.zoom > 0.02f) ? 0.01f : 0;
                    System.out.println("Camera Zoom: " + camera.zoom);
                    return true;
                }
                return super.keyTyped(event, character);
            }

            @Override
            public boolean scrolled(InputEvent event, float x, float y, int amount) {
                if (amount > 0)
                    camera.zoom += (camera.zoom < 1) ? 0.01f: 0;
                else
                    camera.zoom += (camera.zoom > 0.02f) ? -0.01f: 0;
                return super.scrolled(event, x, y, amount);
            }
        });
    }

    /**
     * Recibe el arrreglo de valores de una funcion y lo grafica
     * @param game Referencia a Game para manejo de pantallas
     * @param valores Valores de la funcion
     */
    public RenderScreen(Game game, float[][] valores){
        // Solo para tener una referencia al manejador de pantallas
        this.game = game;
        this.valores = valores;
    }

    /**
     * Recibe un ArrayList de varias funciones, para graficacion multiple
     * @param game Referencia a Game para manejo de pantallas
     * @param funciones ArrayList con arreglos de valores para cada funcion
     */
    public RenderScreen(Game game, ArrayList<float[][]> funciones){
        // Solo para tener una referencia al manejador de pantallas
        this.game = game;
        this.funciones = funciones;
    }

    private void renderArreglo(){
        // Para que se renderize con la camara
        shapeRenderer.setProjectionMatrix(camera.combined);
        // Para comenzar el renderizado
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);
        // Para renderizar solo cuando tenemos el arreglo sencillo de valores
        if(valores != null) {
            // Obviamente define el color
            shapeRenderer.setColor(Color.CYAN);
            // Procesando arreglo
            for (int i = 0; i < valores.length; i++)
                shapeRenderer.point((valores[i][0]*Const.scaling), (valores[i][1]*Const.scaling), 0);
        }
        else{
            int counter = 0;
            for (float[][] funcion : funciones) {
                if (counter < funciones.size())
                    counter++;
                else
                    counter = 0;
                shapeRenderer.setColor(colores[counter]);
                for (int i = 0; i < funcion.length - 1; i++)
                    shapeRenderer.point(funcion[i][0]*Const.scaling, funcion[i][1]*Const.scaling, 0);
            }
        }
        // Para finalizar el renderizado
        shapeRenderer.end();
    }

    private void renderEjes(){
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(1, 1, 1, 1);
        // Renderizan X e Y
        shapeRenderer.line(0, -camera.viewportHeight + camera.position.y , 0, camera.viewportHeight + camera.position.y);
        shapeRenderer.line(-camera.viewportWidth + camera.position.x, 0, camera.viewportWidth + camera.position.x, 0);
        // Renderiza la graduacion de los ejejejes
        for (int i = 0; i < camera.viewportWidth + Math.abs(camera.position.x); i+=Const.scaling){
            shapeRenderer.line(i, -1, i, 1);
            shapeRenderer.line(-i, -1, -i, 1);
        }
        for (int i = 0; i < camera.viewportHeight + Math.abs(camera.position.y); i+=Const.scaling){
            shapeRenderer.line(-1, i, 1, i);
            shapeRenderer.line(-1, -i, 1, -i);
        }
        shapeRenderer.end();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Aplicamos el viewport
        viewport.apply();
        // Actualizamos la camara
        camera.update();
        // Batch
        batch.setProjectionMatrix(camera.combined);
        // Actualizamos el Stage
        stage.act();
        stage.draw();
        // Renderizamos con el shapeRenderer
        renderArreglo();
        // Renderizamos los ejes X e Y
        renderEjes();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.zoom = 0.3f;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}