package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PantallaJuego implements Screen {
	public CamaraJuego camara;
	private SpriteBatch batch;
	public EscenarioHelper escenario;
	
	public PantallaJuego() {
		camara = new CamaraJuego();
		camara.setToOrtho(false);

		batch = new SpriteBatch();
		
		// TODO: hacer que se cargue dinamicamente el escenario
		escenario = new EscenarioHelper(batch, camara, "escenario101");
	}
	
	
	
	
	@Override
	public void render(float delta) {
		// Limpiar pantalla
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		escenario.actuarDibujar(delta);
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void show() {}

	@Override
	public void hide() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
