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
 * 
 * @author Sergio
 * 
 */
public class U {
	private static ShapeRenderer sr = new ShapeRenderer();
	private static SpriteBatch sb = new SpriteBatch();
	private static BitmapFont bmf = new BitmapFont(
			Gdx.files.internal("data/font/font.fnt"),
			Gdx.files.internal("data/font/font.png"), false);

	/**
	 * Voltea coordenadas de un punto a coordenadas de la {@link CamaraJuego} de
	 * la pantalla
	 * 
	 * @param camara
	 *            La {@link CamaraJuego} de {@link Screen}
	 * @param x
	 *            La x del punto
	 * @param y
	 *            La y del punto
	 * @return El punto en coordenadas de la {@link CamaraJuego}
	 */
	static public Vector3 voltearCoordenadas(Camera camara, int x, int y) {
		Vector3 posicion = new Vector3(x, y, 0);
		camara.unproject(posicion);

		return posicion;
	}

	/**
	 * Manda llamar a {@link U#voltearCoordenadas(Camera camara, int x, int y)}
	 * con los valores de {@link Gdx#input()}
	 * 
	 * @param camara
	 *            La cámara de {@link Screen}
	 * @return El punto en coordenadas de la {@link CamaraJuego}
	 */
	static public Vector3 voltearCoordenadasInput(Camera camara) {
		return voltearCoordenadas(camara, Gdx.input.getX(), Gdx.input.getY());
	}

	/**
	 * Dibuja las líneas de colisión del {@link Poligono} dado
	 * 
	 * @param poligono
	 *            El {@link Poligono} del cuál se van a dibujar las líneas de
	 *            colisón
	 * @param color
	 *            El color con el que se van a dibujar las líneas
	 */
	static public void dibujarLineasColision(Poligono poligono,
			CamaraJuego camara, Color color) {
		Vector2[] vertices = poligono.vertices;

		sr.begin(ShapeType.Line);
		sr.setColor(color);
		for (int i = 0; i < vertices.length; i++) {
			int i2 = (i + 1 >= vertices.length) ? 0 : i + 1;
			sr.line(vertices[i].x - camara.posicionOrigen.x, vertices[i].y
					- camara.posicionOrigen.y, vertices[i2].x
					- camara.posicionOrigen.x, vertices[i2].y
					- camara.posicionOrigen.y);
		}
		sr.end();
	}

	/**
	 * Manda llamar a {@link U#dibujarLineasColision(Poligono, Color)} con un
	 * color predeterminado de {@link Color#WHITE}
	 * 
	 * @param poligono
	 *            El {@link Poligono} del cuál se van a dibujar las líneas de
	 *            colisión
	 */
	static public void dibujarLineasColision(Poligono poligono,
			CamaraJuego camara) {
		dibujarLineasColision(poligono, camara, Color.WHITE);
	}

	/**
	 * Función atajo de {@link Gdx#app#log()}
	 * 
	 * @param tag
	 *            La etiqueta
	 * @param mensaje
	 *            El mensaje
	 */
	static public void l(String tag, Object mensaje) {
		Gdx.app.log(tag, mensaje + "");
	}

	/**
	 * Imprime en el log el mensaje del error dado
	 * 
	 * @param e
	 *            El error
	 */
	static public void err(Exception e) {
		e.printStackTrace();
	}

	/**
	 * Dibuja en pantalla el mensaje dado
	 * 
	 * @param mensaje
	 *            El mensaje a imprimir en pantalla
	 */
	static public void ds(Object mensaje) {
		// TODO: hacer que se le pueda pasar una posición dinámica
		CharSequence msg = mensaje + "";
		sb.begin();
		bmf.setColor(1.0f, 0.2f, 0.2f, 1.0f);
		bmf.draw(sb, msg, 30, 500);
		sb.end();
	}
}
