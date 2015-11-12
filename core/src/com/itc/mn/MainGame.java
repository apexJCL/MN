package com.itc.mn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.itc.mn.GUI.Statistics;
import com.itc.mn.Things.Const;
import com.itc.mn.Screens.SplashScreen;
import com.kotcrab.vis.ui.VisUI;

public class MainGame extends Game {

	@Override
	public void create () {
		buildPrefs();
        VisUI.load();
		//this.setScreen(new SplashScreen(this));
		this.setScreen(new Statistics());
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
