package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PantallaJuego implements Screen {
	// private Lumpundform juego;

	private SpriteBatch batch;
	OrthographicCamera camara;
	Heroe heroe;
	Texture fondo;

	private Stage escenario;

	public PantallaJuego(Lumpundform juego) {
		// this.juego = juego;

		batch = new SpriteBatch();
		heroe = new Heroe("heroe");

		// Cámara en Modo Ortográfico
		camara = new OrthographicCamera();
		camara.setToOrtho(false);

		// Escenario
		escenario = new Stage(2000, 720, false, batch);
		escenario.addActor(heroe);
		escenario.setCamera(camara);
		
		// Fondo
		fondo = new Texture(Gdx.files.internal("fondo_bosque.jpg"));

		// Detectar gestos con DetectorGestos
		Gdx.input.setInputProcessor(new GestureDetector(
				new DetectorGestos(this)));
	}

	@Override
	public void render(float delta) {
		// Limpiar pantalla
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.disableBlending();
		batch.draw(fondo, 0, 0);
		batch.enableBlending();
		batch.end();
		
		// Mover cámara
		if (heroe.x > 300) {
			if (heroe.estado == Personaje.MOVIMIENTO && heroe.direccionX == Personaje.IZQUIERDA) {
				camara.translate(-heroe.velocidad * delta, 0);
			} else if (heroe.estado == Personaje.MOVIMIENTO && heroe.direccionX == Personaje.DERECHA) {
				camara.translate(heroe.velocidad * delta, 0);
			}
		}

		escenario.act(delta);
		escenario.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		batch.dispose();
		escenario.dispose();
	}

}
