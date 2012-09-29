package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BaseEscenario {
	private Stage stage;

	public BaseEscenario(SpriteBatch batch, Camera camara) {
		Heroe heroe = new Heroe("heroe", new Vector2(0, 0));
		
		stage = new Stage(2000, 720.0f, false, batch);
		stage.setCamera(camara);
		stage.addActor(heroe);
	}

	public void actuarDibujar(float delta) {
		stage.act(delta);
		stage.draw();
	}

}
