package com.itc.mn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.itc.mn.Cosas.Const;
import com.itc.mn.Pantallas.RenderScreen;

import java.util.Locale;

public class MainGame extends Game {

	@Override
	public void create () {
		buildPrefs();
        String s = Locale.getDefault().toString();
        System.out.println(s.substring(0, s.indexOf('_')));
        System.out.println(s.substring(s.indexOf('_')+1, s.length()));
        this.setScreen(new RenderScreen(this));
    }

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	private void buildPrefs(){
		Preferences prefs = Gdx.app.getPreferences(Const.pref_name);
		String generalPreferences = prefs.getString(Const.id);
		if(generalPreferences.length() <= 1) {
			Const tmp = new Const();
			prefs.putString(Const.id, new Json().prettyPrint(tmp));
			prefs.flush();
		}
	}
}
