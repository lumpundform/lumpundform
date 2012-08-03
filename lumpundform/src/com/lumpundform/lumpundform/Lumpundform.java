package com.lumpundform.lumpundform;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
		
		// TODO: cambiar el nivel del log para que no salgan todos los errores en consola
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		batch = new SpriteBatch();
		heroe = new Personaje();
		
		// Cámara en Modo Ortográfico
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		// Limpiar pantalla y poner fondo azul cielo
		Gdx.gl.glClearColor(0.26f, 0.82f, 0.82f, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		// Hacer que pantalla cuente pixeles de izquierda a derecha y de abajo a arriba
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		// Dibujar
		batch.begin();
		
		// Héroe
		heroe.dibujar(batch);
		
		batch.end();
		
		// Cuando se está tocando la pantalla
		if (Gdx.input.isTouched()) {
			// Alinear coordenadas del punto del toque con le dirección de la camara
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
			heroe.destinoX = touchPos.x;
			heroe.enMovimiento = true;
		}
		
		// Mueve al personaje a la izquierda (A) o derecha (D)
		if(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D)) {
			float velocidadDelta = (heroe.velocidad * Gdx.graphics.getDeltaTime());
			if(Gdx.input.isKeyPressed(Keys.A)) {  
				heroe.destinoX = heroe.posicionX - velocidadDelta;
			}
			if(Gdx.input.isKeyPressed(Keys.D)) {
				heroe.destinoX = heroe.posicionX + velocidadDelta;
			}
			heroe.enMovimiento = true;
		}
		
		heroe.caminar(Gdx.graphics.getDeltaTime());
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
