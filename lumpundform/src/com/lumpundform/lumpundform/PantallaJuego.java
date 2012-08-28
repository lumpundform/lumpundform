package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class PantallaJuego implements Screen {
	// private Lumpundform juego;

	private SpriteBatch batch;
	OrthographicCamera camara;
	Heroe heroe;
	Texture fondo;
	TiledMap mapa;
	TileAtlas atlas;
	TileMapRenderer tileMapRenderer;

	private Stage escenario;
	private static final int[] layersList = { 0 };

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

		// Mapa
		mapa = TiledLoader.createMap(Gdx.files.internal("data/world/level1/level.tmx"));
		atlas = new TileAtlas(mapa, Gdx.files.internal("data/packer"));
		tileMapRenderer = new TileMapRenderer(mapa, atlas, 16, 16);

		// Detectar gestos con DetectorGestos
		Gdx.input.setInputProcessor(new InputMultiplexer(new GestureDetector(
				new DetectorGestos(this)), new ProcesadorEntrada(this)));
	}

	@Override
	public void render(float delta) {
		// Limpiar pantalla
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		tileMapRenderer.render(camara);

		// Mover cámara
		Gdx.app.log("Camara", "Posicion camara: " + camara.position.y);
		if (heroe.x > 400 && camara.position.x >= 400 && camara.position.x <= 2000) {
			if (heroe.estado == Personaje.MOVIMIENTO
					&& heroe.direccionX == Personaje.IZQUIERDA) {
				camara.translate(-heroe.velocidad * delta, 0);
			} else if (heroe.estado == Personaje.MOVIMIENTO
					&& heroe.direccionX == Personaje.DERECHA) {
				camara.translate(heroe.velocidad * delta, 0);
			}
			if (camara.position.x > 2000) camara.position.set(2000, 225, 0);
			if (camara.position.x < 400) camara.position.set(400, 225, 0);
		}

		if (Gdx.input.isTouched()) {
			Vector3 pos = DetectorGestos.alinearCoordenadas(Gdx.input.getX(),
					Gdx.input.getY(), camara);

			heroe.destinoX = pos.x;
			heroe.estado = Personaje.MOVIMIENTO;
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
