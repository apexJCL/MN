package com.itc.mn.GUI;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.itc.mn.Cosas.FuncionX;
import com.itc.mn.Metodos.*;
import com.itc.mn.Pantallas.RenderScreen;
import com.kotcrab.vis.ui.widget.VisTextButton;
import com.kotcrab.vis.ui.widget.VisTextField;
import com.kotcrab.vis.ui.widget.VisWindow;

/**
 * Created by zero_ on 02/10/2015.
 */
public class VentanaValores extends VisWindow {

    private final Game game;
    private VisTextButton aceptar, cancelar;
    private FuncionX fx;
    private VentanaCarga vc = new VentanaCarga();
    private Metodo.Tipo tipo;

    /**
     * Crea una v con los campos mandados.
     * Cada parte del arreglo tendra que ser de 2, el primero para el hint de la caja, el 2do para el nombre
     * a usar al momento de recuperar valores
     * @param title Titulo de la v
     * @param campos String[] de 2, [0] = hint, [1] = nombre variable
     */
    public VentanaValores(String title, String[][] campos, Game game, Metodo.Tipo tipo) {
        super(title);
        // La v se llamara igual que el titulo que reciba
        setName(title);
        // Una referencia a Game para poder intercambiar la pantalla
        this.game = game;
        // Guardamos referencia al tip
        this.tipo = tipo;
        for(String[] campo: campos){
            VisTextField tmp = new VisTextField();
            tmp.setMessageText(campo[0]);
            tmp.setName(campo[1]);
            add(tmp).expandX().center().pad(1f).colspan(2).row();
            tmp.addListener(new AndroidInput(tmp));
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
        // Asignamos una cadena vacia a los nombres de los actores que no son indispensables
        renombra();
        validacion(tipo);
        closeOnEscape();
        addCloseButton();
        pack();
        setPosition((Gdx.graphics.getWidth()-getWidth())/2f, (Gdx.graphics.getHeight()-getHeight())/2f);
    }

    /**
     * Devuelve la referencia al actor boton Aceptar
     * @return
     */
    public VisTextButton getAceptar(){ return aceptar; }

    /**
     * Asigna una cadena vacia a los nombres de los actores para evitar checar si es nulo o no
     */
    private void renombra(){
        for(Actor actor: getChildren())
            if(actor.getName() == null)
                actor.setName("");
    }

    private void validacion(Metodo.Tipo tipo){
        restringeEP();
        switch (tipo){
            case PUNTO_FIJO:
                break;
            case BISECCION: // Para validar que haya cambio de signo entre los puntos dados
                break;
            case NEWTON_RAPHSON:
                break;
            case REGLA_FALSA:
                break;
            case SECANTE:
                break;
        }
    }

    /**
     * Habilita una restriccion en el campo Error, para que sea de 0 a 100
     */
    private void restringeEP(){
        for(Actor textfield: getChildren())
            if (textfield.getName().equals("ep"))
                textfield.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if (!((VisTextField) actor).getText().equals("")) {
                            try{
                                double value = Double.parseDouble(((VisTextField) actor).getText());
                                actor.setColor((value > 0 && value < 100) ? Color.GREEN: Color.RED);
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

    /**
     * Regresa el valor contenido en alguno de los campos/variables declaradas, por defecto TextArea
     * @param variable Nombre de la variable asignado en su creacion
     * @return
     * @throws Exception No se encuentra la variable solicitada
     */
    public String getVariable(String variable) throws Exception{
        for(Actor textField: getChildren())
            if (textField.getName() != null)
                if (textField.getName().equals(variable))
                    return ((VisTextField)textField).getText();
        throw new Exception("Variable no encontrada");
    }

    /**
     * Regresa un actor con un nombre determinado
     * @param nombre
     * @return
     */
    public Actor getActor(String nombre){
        for(Actor actor: getChildren())
            if(actor.getName().equals(nombre))
                return actor;
        return null;
    }

    /**
     * Parpadea la ventana, para llamar la atencion
     */
    public void parpadear(){
        addAction(Actions.sequence(Actions.alpha(0.5f, 0.03f), Actions.alpha(1, 0.03f), Actions.alpha(0.5f, 0.03f), Actions.alpha(1, 0.03f)));
    }

    /**
     * Asigna un evento personalizado dependiendo del tipo de metodo a ejecutar
     * @param tipo
     */
    public void asignaEvento(Metodo.Tipo tipo){
        aceptar.addListener(new Proceso(this, tipo));
    }

    public Metodo.Tipo getTipo() {
        return tipo;
    }

    private class Proceso extends ClickListener {

        private final Metodo.Tipo tipo;
        private final VentanaValores v;
        private double a, b;

        public Proceso(VentanaValores v, Metodo.Tipo tipo){
            this.v = v;
            this.tipo = tipo;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            try {
                if (Double.parseDouble(v.getVariable("ep")) > 0 && Double.parseDouble(v.getVariable("ep")) <= 100)
                    switch (tipo) {
                        case BISECCION:
                            //Para corroborar que haya cambio de signo entre los valores dados
                            fx = new FuncionX(v.getVariable("f"));
                            a = fx.obtenerValor(Double.parseDouble(v.getVariable("a")));
                            b = fx.obtenerValor(Double.parseDouble(v.getVariable("b")));
                            if((a*b) < 0) {
                                game.setScreen(new RenderScreen(game, new Biseccion(v.getVariable("f"), Double.parseDouble(v.getVariable("a")), Double.parseDouble(v.getVariable("b")), Double.parseDouble(v.getVariable("ep")+"d") / 100d)));
                            }
                            else{
                                getActor("a").setColor(1, 0, 0, 1);
                                getActor("b").setColor(1, 0, 0, 1);
                            }
                            break;
                        case REGLA_FALSA:
                            fx = new FuncionX(v.getVariable("f"));
                            a = fx.obtenerValor(Double.parseDouble(v.getVariable("a")));
                            b = fx.obtenerValor(Double.parseDouble(v.getVariable("b")));
                            if((a*b) < 0) {
                                game.getScreen().dispose();
                                game.setScreen(new RenderScreen(game, new ReglaFalsa(v.getVariable("f"), Double.parseDouble(v.getVariable("a")), Double.parseDouble(v.getVariable("b")), Double.parseDouble(v.getVariable("ep")+"d") / 100d)));
                            }else{
                                getActor("a").setColor(1, 0, 0, 1);
                                getActor("b").setColor(1, 0, 0, 1);
                            }
                            break;
                        case PUNTO_FIJO:
                            game.getScreen().dispose();
                            game.setScreen(new RenderScreen(game, new PFijo(v.getVariable("f1"), v.getVariable("f2"), Double.parseDouble(v.getVariable("vi")), Double.parseDouble(v.getVariable("ep")+"d") / 100d)));
                        break;
                        case NEWTON_RAPHSON:
                            game.getScreen().dispose();
                            game.setScreen(new RenderScreen(game, new NewtonRaphson(v.getVariable("fx"), v.getVariable("f'x"), Double.parseDouble(v.getVariable("vi")), Double.parseDouble(v.getVariable("ep")+"d") / 100d)));
                            break;
                    }
            } catch (Exception ex) {
                ex.printStackTrace();
                v.fadeOut();
            }
        }
    }

    private class AndroidInput extends ClickListener implements Input.TextInputListener{

        private VisTextField field;

        public AndroidInput(VisTextField field){
            this.field = field;
        }

        @Override
        public void clicked(InputEvent event, float x, float y) {
            if(Gdx.app.getType().equals(Application.ApplicationType.Android))
                Gdx.input.getTextInput(new AndroidInput(field), field.getName(), "", field.getMessageText());
        }

        @Override
        public void input(String text) {
            field.setText(text);
        }

        @Override
        public void canceled() {

        }
    }
}
