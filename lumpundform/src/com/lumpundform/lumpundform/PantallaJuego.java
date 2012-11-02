package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

/**
 * Base para las diferentes pantallas del juego, implementa {@link Screen}
 * 
 * @author Sergio
 * 
 */
public class PantallaJuego implements Screen {
	private CamaraJuego camara;
	private SpriteBatch batch;
	private EscenarioHelper escenario;

	/**
	 * Inicializa la {@link PantallaJuego} y crea una {@link CamaraJuego}, un
	 * {@link SpriteBatch} y un nuevo escenario
	 */
	public PantallaJuego() {
		camara = new CamaraJuego();
		getCamara().setToOrtho(false);

		batch = new SpriteBatch();

		// TODO: hacer que se cargue dinamicamente el escenario
		escenario = new EscenarioHelper(batch, getCamara(), "escenario101");

		Gdx.input.setInputProcessor(new GestureDetector(
				new ProcesadorEntradaJuego(this)));
	}

	@Override
	public void render(float delta) {
		// Limpiar pantalla
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		getEscenario().actuarDibujar(delta);
	}

	/**
	 * Regresa la {@link CamaraJuego} de la {@link PantallaJuego}
	 * 
	 * @return La {@link CamaraJuego}
	 */
	public CamaraJuego getCamara() {
		return camara;
	}

	/**
	 * Regresa el {@link EscenarioHelper} de la {@link PantallaJuego}
	 * 
	 * @return El {@link EscenarioHelper}
	 */
	public EscenarioHelper getEscenario() {
		return escenario;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
