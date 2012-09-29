package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PantallaJuego implements Screen {
	private OrthographicCamera camara;
	private SpriteBatch batch;
	private BaseEscenario escenario;
	
	public PantallaJuego() {
		camara = new OrthographicCamera();
		camara.setToOrtho(false);

		batch = new SpriteBatch();
		
		escenario = new BaseEscenario(batch, camara);
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
