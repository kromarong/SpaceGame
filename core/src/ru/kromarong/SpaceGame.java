package ru.kromarong;

import com.badlogic.gdx.Game;

import ru.kromarong.screen.MenuScreen;


public class SpaceGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen(this));
	}

}
