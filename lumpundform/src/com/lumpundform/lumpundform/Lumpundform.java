package com.lumpundform.lumpundform;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class Lumpundform implements ApplicationListener {
	private SpriteBatch batch;
	private Personaje heroe;
	private OrthographicCamera camera;
	
	@Override
	public void create() {
		Texture.setEnforcePotImages(false);
		
		batch = new SpriteBatch();
		heroe = new Personaje();
		
		// Cámara en Modo Ortográfico
		camera = new OrthographicCamera();
		camera.setToOrtho(true);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void render() {
		// Limpiar pantalla
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		// Dibujar Héroe
		batch.begin();
		batch.draw(heroe.normal, (heroe.posicionX - (heroe.ancho / 2)), heroe.posicionY);
		batch.end();
		
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
			heroe.destinoX = touchPos.x;
			heroe.enMovimiento = true;
		}
		
		float deltaCounter = 0;
		if (deltaCounter > 0.5) {
			deltaCounter = 0;
			heroe.caminar(deltaCounter);
		} else {
			deltaCounter += Gdx.graphics.getDeltaTime();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
