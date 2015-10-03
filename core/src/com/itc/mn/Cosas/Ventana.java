package com.itc.mn.Cosas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.itc.mn.Metodos.*;
import com.itc.mn.Pantallas.RenderScreen;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;

/**
 * Created by zero_ on 02/10/2015.
 */
public class Ventana extends VisWindow {

    private final Game game;
    private VisTextButton aceptar, cancelar;

    /**
     * Crea una v con los campos mandados.
     * Cada parte del arreglo tendrá que ser de 2, el primero para el hint de la caja, el 2do para el nombre
     * a usar al momento de recuperar valores
     * @param title Titulo de la v
     * @param campos String[] de 2, [0] = hint, [1] = nombre variable
     */
    public Ventana(String title, String[][] campos, Game game) {
        super(title);
        // La v se llamara igual que el titulo que reciba
        setName(title);
        // Una referencia a Game para poder intercambiar la pantalla
        this.game = game;
        for(String[] campo: campos){
            VisTextField tmp = new VisTextField();
            tmp.setMessageText(campo[0]);
            tmp.setName(campo[1]);
            add(tmp).expandX().center().pad(1f).colspan(2).row();
        }
        // Creamos los botones
        aceptar = new VisTextButton("Aceptar");
        cancelar = new VisTextButton("Cancelar");
        // Los agregamos a la v
        add(cancelar).expandX().pad(3f);
        add(aceptar).expandX().pad(3f).row();
        // Agregamos accion basica al boton cancelar
        cancelar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                close();
            }
        });
        restringeEP();
        closeOnEscape();
        addCloseButton();
        pack();
        setPosition((Gdx.graphics.getWidth()-getWidth())/2f, (Gdx.graphics.getHeight()-getHeight())/2f);
    }

    public VisTextButton getAceptar(){ return aceptar; }

    private void restringeEP(){
        for(Actor textfield: getChildren())
            if(textfield.getName() != null) {
                if (textfield.getName().equals("ep"))
                    textfield.addListener(new ChangeListener() {
                        @Override
                        public void changed(ChangeEvent event, Actor actor) {
                            if (!((VisTextField) actor).getText().equals("")) {
                                try{
                                    double value = Double.parseDouble(((VisTextField) actor).getText());
                                    actor.setColor((value < 0 || value > 100) ? Color.RED: Color.GREEN);
                                }
                                catch (Exception ex){
                                    actor.setColor(Color.RED);
                                }
                            }
                            else
                                actor.setColor(1, 1, 1, 1);
                        }
                    });
            }
    }

    public String getVariable(String variable) throws Exception{
        for(Actor textField: getChildren())
            if (textField.getName() != null)
                if (textField.getName().equals(variable))
                    return ((VisTextField)textField).getText();
        throw new Exception("Variable no encontrada");
    }

    public void parpadear(){
        addAction(Actions.sequence(Actions.alpha(0.5f, 0.03f), Actions.alpha(1, 0.03f), Actions.alpha(0.5f, 0.03f), Actions.alpha(1, 0.03f)));
    }

    public void asignaEvento(Metodo.Tipo tipo){
        aceptar.addListener(new Proceso(this, tipo));
    }

    private class Proceso extends ClickListener {

        private final Metodo.Tipo tipo;
        private final Ventana v;

        public Proceso(Ventana v, Metodo.Tipo tipo){
            this.v = v;
            this.tipo = tipo;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            try {
                if (Double.parseDouble(v.getVariable("ep")) > 0 && Double.parseDouble(v.getVariable("ep")) <= 100)
                    switch (tipo) {
                        case BISECCION:
                            game.setScreen(new RenderScreen(game, new Biseccion(v.getVariable("f"), Float.parseFloat(v.getVariable("a")), Float.parseFloat(v.getVariable("b")), Float.parseFloat(v.getVariable("ep"))/100)));
                            break;
                        case REGLA_FALSA:
                            game.setScreen(new RenderScreen(game, new ReglaFalsa(v.getVariable("f"), Float.parseFloat(v.getVariable("a")), Float.parseFloat(v.getVariable("b")), Float.parseFloat(v.getVariable("ep"))/100)));
                            break;
                        case PUNTO_FIJO:
                        game.setScreen(new RenderScreen(game, new PFijo(v.getVariable("f1"), v.getVariable("f2"), Double.parseDouble(v.getVariable("vi")), Double.parseDouble(v.getVariable("ep")) / 100)));
                        break;
                        case NEWTON_RAPHSON:
                            game.setScreen(new RenderScreen(game, new NewtonRaphson(v.getVariable("fx"), v.getVariable("f'x"), Float.parseFloat(v.getVariable("vi")), Double.parseDouble(v.getVariable("ep")) / 100)));
                            break;
                    }
            } catch (Exception ex) {
                ex.printStackTrace();
                v.fadeOut();
            }
        }
    }
}
