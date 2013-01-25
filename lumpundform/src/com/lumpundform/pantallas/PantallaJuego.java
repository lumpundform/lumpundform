package com.lumpundform.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.lumpundform.escenario.EscenarioHelper;
import com.lumpundform.input.ProcesadorEntradaJuego;
import com.lumpundform.lumpundform.CamaraJuego;
import com.lumpundform.utilerias.U;

/**
 * Base para las diferentes pantallas del juego, implementa {@link Screen}
 * 
 * @author Sergio
 * 
 */
public class PantallaJuego extends PantallaBase {
	private CamaraJuego camara;
	private SpriteBatch batch;
	private EscenarioHelper escenario;

	/**
	 * Inicializa la {@link PantallaJuego} y crea una {@link CamaraJuego}, un
	 * {@link SpriteBatch} y un nuevo escenario
	 */
	public PantallaJuego() {
		setCamara();

		batch = new SpriteBatch();

		// TODO: hacer que se cargue dinamicamente el escenario
		escenario = new EscenarioHelper(batch, getCamara(), "escenario101");

		setInputProcessor();
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

	@Override
	public void reset() {
		setCamara();
		escenario = new EscenarioHelper(batch, getCamara(), "escenario101");
		setInputProcessor();
	}

	private void setInputProcessor() {
		Gdx.input.setInputProcessor(new InputMultiplexer(Gdx.input.getInputProcessor(), escenario.getEscenario(),
				new GestureDetector(new ProcesadorEntradaJuego(escenario)), new ProcesadorEntradaJuego(escenario)));
	}

	private void setCamara() {
		camara = new CamaraJuego();
		getCamara().setToOrtho(false);

		U.init(camara);
	}

}
