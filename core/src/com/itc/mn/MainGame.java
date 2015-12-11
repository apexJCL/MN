package com.itc.mn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.itc.mn.Methods.Interpolation;
import com.itc.mn.Structures.Lists.XYList;
import com.itc.mn.Things.Const;
import com.itc.mn.UI.MainScreen;

public class MainGame extends Game {

	private XYList list = new XYList();

	@Override
	public void create () {
		buildPrefs();
		setScreen(new MainScreen());
    }

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

    /**
     * It builds the initial preferences of the application
     */
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
