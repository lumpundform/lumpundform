package com.lumpundform.utilerias;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.lumpundform.colision.Poligono;
import com.lumpundform.lumpundform.CamaraJuego;

/**
 * Clase de funciones de Utileria
 * 
 * @author Sergio
 * 
 */
public class U {
	private static ShapeRenderer sr = new ShapeRenderer();
	private static SpriteBatch sb = new SpriteBatch();
	private static BitmapFont bmf = Fuentes.regular();

	/**
	 * Voltea coordenadas de un punto a coordenadas de la {@link CamaraJuego} de
	 * la pantalla
	 * 
	 * @param camara
	 *            La {@link CamaraJuego} de {@link Screen}
	 * @param pos
	 *            La posición de la cual se quieren voltear las coordenadas
	 * @return La posición con las coordenadas volteadas
	 */
	static public Vector2 voltearCoordenadas(CamaraJuego camara, Vector2 pos) {
		return camara.unproject(pos);
	}

	/**
	 * Manda llamar a {@link #voltearCoordenadas(CamaraJuego, Vector2)} con los
	 * valores X y Y especificados
	 * 
	 * @param camara
	 *            La {@link CamaraJuego} de {@link Screen}
	 * @param x
	 *            La x del punto
	 * @param y
	 *            La y del punto
	 * @return El punto en coordenadas de la {@link CamaraJuego}
	 */
	static public Vector2 voltearCoordenadas(CamaraJuego camara, float x,
			float y) {
		return voltearCoordenadas(camara, new Vector2(x, y));
	}

	/**
	 * Manda llamar a {@link #voltearCoordenadas(CamaraJuego, float, float)} con
	 * los valores de {@link Gdx#input()}
	 * 
	 * @param camara
	 *            La cámara de {@link Screen}
	 * @return El punto en coordenadas de la {@link CamaraJuego}
	 */
	static public Vector2 voltearCoordenadasInput(CamaraJuego camara) {
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
		Vector2[] vertices = poligono.getVertices();

		sr.begin(ShapeType.Line);
		sr.setColor(color);
		for (int i = 0; i < vertices.length; i++) {
			int i2 = (i + 1 >= vertices.length) ? 0 : i + 1;
			sr.line(vertices[i].x - camara.getPosicionOrigen().x, vertices[i].y
					- camara.getPosicionOrigen().y,
					vertices[i2].x - camara.getPosicionOrigen().x,
					vertices[i2].y - camara.getPosicionOrigen().y);
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
	 * Dibuja en pantalla el mensaje dado en la posición 30, 30
	 * 
	 * @param mensaje
	 *            El mensaje a imprimir en pantalla
	 */
	static public void ds(Object mensaje) {
		ds(mensaje, 30.0f, 30.0f);
	}

	/**
	 * Dibuja en pantalla el mensaje dado en la posición dada
	 * 
	 * @param mensaje
	 *            El mensaje a imprimir en pantalla
	 * @param x
	 *            La x de la posición
	 * @param y
	 *            La y de la posición
	 */
	static public void ds(Object mensaje, float x, float y) {
		CharSequence msg = mensaje + "";
		sb.begin();
		bmf.setColor(1.0f, 0.2f, 0.2f, 1.0f);
		bmf.draw(sb, msg, x, y);
		sb.end();
	}
}
