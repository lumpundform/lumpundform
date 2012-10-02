package com.lumpundform.lumpundform;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class ObjetoActor extends Actor {
	// Valores Estáticos
	protected static final int DETENIDO = 0;
	protected static final int MOVIMIENTO = 1;
	protected static final int CAYENDO = 2;
	protected static final int IZQUIERDA = 10;
	protected static final int DERECHA = 11;
	protected static final int ARRIBA = 20;
	protected static final int ABAJO = 21;

	// Animaciones
	protected Map<String, Animation> animacion;
	protected float tiempoTranscurrido;

	// Estado, Posición y Tamaño
	protected int estado;
	protected int direccionX;
	protected int direccionY;

	// Movimiento
	protected float destinoX;
	protected float velocidad;
	protected boolean cambio_direccion;

	/**
	 * Inicializa los valores generales de todos los actores
	 */
	protected ObjetoActor(String nombre, Vector2 puntoOrigen) {
		super(nombre);
		
		this.x = puntoOrigen.x;
		this.y = puntoOrigen.y;
		
		animacion = new HashMap<String, Animation>();

		tiempoTranscurrido = 0f;
	}
	protected void cargarAnimaciones() {
		animacion.put("detenido", initAnimacion("detenido"));
	}

	
	
	
	@Override
	public abstract void draw(SpriteBatch batch, float parentAlpha);
	
	@Override
	public void act(float delta) {
		tiempoTranscurrido += delta;
	}
	
	@Override
	public Actor hit(float x, float y) {
		return null;
	}
	
	
	
	
	/**
	 * Regresa el cuadro actual dentro de la animación elegida del objeto
	 * 
	 * @author Sergio
	 * @return El cuadro actual
	 */
	protected abstract TextureRegion getCuadroActual();

	/**
	 * Inicializa la animación del personaje, con la imagen y datos
	 * proporcionados.
	 * 
	 * @param tipoAnimacion
	 *            Una cadena con el nombre del tipo de animación a realizar, con
	 *            el que obtiene los valores de los diferentes Maps
	 * @return La animación en si
	 */
	protected Animation initAnimacion(String tipoAnimacion) {
		String spriteSheet = D.s(name).get("sprite_sheet");
		int columnas = D.i("heroe").get("columnas_" + tipoAnimacion);
		int renglones = D.i("heroe").get("renglones_" + tipoAnimacion);
		
		Texture texturaAnimacion = new Texture(Gdx.files.internal(spriteSheet));
		TextureRegion[][] tmp = TextureRegion.split(texturaAnimacion,
				(int) width, (int) height);
		TextureRegion[] cuadrosAnimacion = new TextureRegion[columnas
		                                                     * renglones];
		int index = 0;
		for (int i = 0; i < renglones; i++) {
			for (int j = 0; j < columnas; j++) {
				cuadrosAnimacion[index++] = tmp[i][j];
			}
		}
		return new Animation(0.05f, cuadrosAnimacion);
	}

}
