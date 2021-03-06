package com.lumpundform.utilerias;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.lumpundform.colision.Poligono;
import com.lumpundform.excepciones.CamaraNoInicializadaException;
import com.lumpundform.lumpundform.CamaraJuego;

/**
 * Clase de funciones de Utileria
 * 
 * @author Sergio
 * 
 */
public class U {
	private static CamaraJuego camara;
	private static ShapeRenderer sr = new ShapeRenderer();
	private static SpriteBatch sb = new SpriteBatch();
	private static BitmapFont bmf = Fuentes.regular();
	public static Logger lg;

	public static void init(CamaraJuego camara) {
		setCamara(camara);
		lg = Logger.getLogger(U.class);
	}

	private static void setCamara(CamaraJuego camara) {
		U.camara = camara;
	}

	public static CamaraJuego getCamara() {
		if (U.camara == null) {
			throw new CamaraNoInicializadaException();
		} else {
			return U.camara;
		}
	}

	/**
	 * Voltea coordenadas de un punto a coordenadas de la {@link CamaraJuego} de
	 * la pantalla
	 * 
	 * @param pos
	 *            La posición de la cual se quieren voltear las coordenadas
	 * @return La posición con las coordenadas volteadas
	 */
	public static Vector2 voltearCoordenadas(Vector2 pos) {
		return getCamara().unproject(pos);
	}

	/**
	 * Manda llamar a {@link #voltearCoordenadas(Vector2)} con los valores X y Y
	 * especificados
	 * 
	 * @param x
	 *            La x del punto
	 * @param y
	 *            La y del punto
	 * @return El punto en coordenadas de la {@link CamaraJuego}
	 */
	public static Vector2 voltearCoordenadas(float x, float y) {
		return voltearCoordenadas(new Vector2(x, y));
	}

	/**
	 * Dibuja las líneas de colisión del {@link Poligono} dado.
	 * 
	 * @param poligono
	 *            El {@link Poligono} del cuál se van a dibujar las líneas de
	 *            colisón.
	 * @param camara
	 *            La {@link CamaraJuego} actual.
	 * @param color
	 *            El color con el que se van a dibujar las líneas.
	 */
	public static void dibujarLineasColision(Poligono poligono, CamaraJuego camara, Color color) {
		Vector2[] vertices = poligono.getVertices();

		sr.begin(ShapeType.Line);
		sr.setColor(color);
		for (int i = 0; i < vertices.length; i++) {
			int i2 = (i + 1 >= vertices.length) ? 0 : i + 1;
			sr.line(vertices[i].x - camara.getPosicionOrigen().x, vertices[i].y - camara.getPosicionOrigen().y,
					vertices[i2].x - camara.getPosicionOrigen().x, vertices[i2].y - camara.getPosicionOrigen().y);
		}
		sr.end();
	}

	/**
	 * Manda llamar a
	 * {@link U#dibujarLineasColision(Poligono, CamaraJuego, Color)} con un
	 * color predeterminado de {@link Color#WHITE}
	 * 
	 * @param poligono
	 *            El {@link Poligono} del cuál se van a dibujar las líneas de
	 *            colisión
	 * @param camara
	 *            La {@link CamaraJuego} actual
	 */
	public static void dibujarLineasColision(Poligono poligono, CamaraJuego camara) {
		dibujarLineasColision(poligono, camara, Color.WHITE);
	}

	/**
	 * Función atajo de {@link Application#log(String, String)}
	 * 
	 * @param tag
	 *            La etiqueta
	 * @param mensaje
	 *            El mensaje
	 */
	public static void l(String tag, Object mensaje, Class<? extends Object> klass) {
		Logger lg = Logger.getLogger(klass);
		lg.debug(mensaje);
	}

	/**
	 * Imprime en el log el mensaje del error dado
	 * 
	 * @param e
	 *            El error
	 */
	public static void err(Exception e) {
		e.printStackTrace();
	}

	/**
	 * Dibuja en pantalla el mensaje dado en la posición 30, 30
	 * 
	 * @param mensaje
	 *            El mensaje a imprimir en pantalla
	 */
	public static void ds(Object mensaje) {
		ds(mensaje, 30.0f, 30.0f, Fuentes.colorDefault);
	}

	public static void ds(Object mensaje, float escala) {
		ds(mensaje, 30.0f, 30.0f, escala, Fuentes.colorDefault);
	}

	public static void ds(Object mensaje, float escala, Color color) {
		ds(mensaje, 30.0f, 30.0f, escala, color);
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
	 * @param escala
	 *            A que escala dibujar el texto
	 */
	public static void ds(Object mensaje, float x, float y, float escala) {
		ds(mensaje, x, y, escala, Fuentes.colorDefault);
	}

	public static void ds(Object mensaje, float x, float y, Color color) {
		ds(mensaje, x, y, 1.0f, color);
	}

	public static void ds(Object mensaje, float x, float y, float escala, Color color) {
		CharSequence msg = mensaje + "";
		sb.begin();
		bmf.setColor(color);
		bmf.setScale(escala);
		bmf.drawWrapped(sb, msg, x, y, Gdx.graphics.getWidth() - (x * 2));
		sb.end();
	}

	public static int numeroConKeycode(int keycode) {
		switch (keycode) {
		case Keys.NUM_1:
			return 1;
		case Keys.NUM_2:
			return 2;
		case Keys.NUM_3:
			return 3;
		case Keys.NUM_4:
			return 4;
		case Keys.NUM_5:
			return 5;
		case Keys.NUM_6:
			return 6;
		default:
			return 0;
		}
	}
	
	public static boolean implementaInterface(Object object, Class interf){
	    for (Class c : object.getClass().getInterfaces()) {
	        if (c.equals(interf)) {
	            return true;
	        }
	    }
	    return false;
	}
}
