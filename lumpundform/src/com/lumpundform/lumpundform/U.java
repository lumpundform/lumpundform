package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Clase de funciones de Utileria
 * @author Sergio
 *
 */
public class U {
	private static ShapeRenderer sr = new ShapeRenderer();
	private static SpriteBatch sb = new SpriteBatch();
	private static BitmapFont bmf = new BitmapFont(Gdx.files.internal("data/font/font.fnt"),
			Gdx.files.internal("data/font/font.png"), false);
	
	static public Vector3 voltearCoordenadas(Camera camara, int x, int y) {
		Vector3 posicion = new Vector3(x, y, 0);
		camara.unproject(posicion);
		
		return posicion;
	}
	
	static public Vector3 voltearCoordenadasInput(Camera camara) {
		return voltearCoordenadas(camara, Gdx.input.getX(), Gdx.input.getY());
	}
	
	static public void dibujarLineasColision(Poligono poligono, Color color) {
		Vector2[] puntos = poligono.puntos;
		
		sr.begin(ShapeType.Line);
		sr.setColor(color);
		for (int i = 0; i < puntos.length; i++) {
			int i2 = (i + 1 >= puntos.length) ? 0 : i + 1;
			sr.line(puntos[i].x, puntos[i].y, puntos[i2].x, puntos[i2].y);
		}
		sr.end();
	}
	static public void dibujarLineasColision(Poligono poligono) {
		dibujarLineasColision(poligono, Color.WHITE);
	}
	
	/**
	 * Función atajo de Gdx.app.log
	 * @param tag
	 * @param mensaje
	 */
	static public void l(String tag, Object mensaje) {
		Gdx.app.log(tag, mensaje + "");
	}
	
	static public void err(Exception e) {
		l("Error", e.getMessage());
	}
	
	static public void ds(Object mensaje) {
		CharSequence msg = mensaje + "";
		sb.begin();
		bmf.setColor(1.0f, 0.2f, 0.2f, 1.0f);
		bmf.draw(sb, msg, 30, 500);
		sb.end();
	}
}
