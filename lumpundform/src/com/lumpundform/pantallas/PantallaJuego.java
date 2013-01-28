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
	private EscenarioHelper escenarioHelper;

	/**
	 * Inicializa la {@link PantallaJuego} y crea una {@link CamaraJuego}, un
	 * {@link SpriteBatch} y un nuevo escenario
	 */
	public PantallaJuego() {
		setCamara();

		batch = new SpriteBatch();

		// TODO: hacer que se cargue dinamicamente el escenario
		escenarioHelper = new EscenarioHelper(batch, getCamara(), "escenario101");

		setInputProcessor();
	}

	@Override
	public void render(float delta) {
		// Limpiar pantalla
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		getEscenarioHelper().actuarDibujar(delta);

		if (isHeroeMuerto()) {
			U.ds("Has muerto, jajaja. Presiona cualquier tecla para reiniciar el juego", 30, 300, 1.5f);
		}
	}

	public boolean isHeroeMuerto() {
		return getEscenarioHelper().getEscenario().isHeroeMuerto();
	}

	@Override
	public void reset() {
		setCamara();
		escenarioHelper = new EscenarioHelper(batch, getCamara(), "escenario101");
		setInputProcessor();
	}

	private void setInputProcessor() {
		Gdx.input.setInputProcessor(new InputMultiplexer(Gdx.input.getInputProcessor(), escenarioHelper.getEscenario(),
				new GestureDetector(new ProcesadorEntradaJuego(escenarioHelper)), new ProcesadorEntradaJuego(
						escenarioHelper)));
	}

	private void setCamara() {
		camara = new CamaraJuego();
		getCamara().setToOrtho(false);

		U.init(camara);
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
	public EscenarioHelper getEscenarioHelper() {
		return escenarioHelper;
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
