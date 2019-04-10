package ru.kromarong;

import com.badlogic.gdx.Game;

import ru.kromarong.Screen.GameScreen;


public class SpaceGame extends Game {
	
	@Override
	public void create () {
        setScreen(new GameScreen());
	}

}
