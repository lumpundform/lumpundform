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

/**
 * Clase personalizada que extiende a {@link Actor} y le agrega valores
 * específicos del juego
 * @author Sergio
 *
 */
public abstract class ObjetoActor extends Actor {
	// Valores Estáticos
	protected static final int DETENIDO = 0;
	protected static final int MOVIMIENTO = 1;
	protected static final int CAYENDO = 2;
	protected static final int IZQUIERDA = 10;
	protected static final int DERECHA = 11;
	protected static final int ARRIBA = 20;
	protected static final int ABAJO = 21;
	protected static final int COLISIONANDO = 30;

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
	protected boolean teletransportar = false;
	
	// Colisión
	protected Rectangulo hitbox;
	protected Map<String, Vector2> sensores;
	protected Map<String, Vector2> sensoresDelta;
	protected boolean colisionActores = false;
	protected boolean colisionPiso = false;

	/**
	 * Inicializa los valores generales de todos los actores
	 */
	protected ObjetoActor(String nombre, Vector2 puntoOrigen) {
		super(nombre);
		
		this.x = puntoOrigen.x;
		this.y = puntoOrigen.y;

		sensores = new HashMap<String, Vector2>();
		sensoresDelta = new HashMap<String, Vector2>();
		animacion = new HashMap<String, Animation>();

		tiempoTranscurrido = 0f;
	}
	
	public Vector2 getSensor(String nombre) {
		if (hitbox == null) return null;
		
		sensores.remove(nombre);
		sensores.put(nombre, getHitbox().punto(nombre));
		return sensores.get(nombre);
	}

	public void setSensorX(String nombre, float x) {
		hitbox.centrado = false;
		hitbox.posicionar(x, sensores.get(nombre).y);
		this.x = hitbox.centro().x - width / 2;
		hitbox.centrado = true;
	}

	public void setSensorY(String nombre, float y) {
		hitbox.centrado = false;
		hitbox.posicionar(sensores.get(nombre).x, y);
		this.y = hitbox.centro().y - height / 2;
		hitbox.centrado = true;
	}
	
	public Vector2 getSensorDelta(String nombre, float delta) {
		if (hitbox == null) return null;
		Vector2 punto = null;
		Vector2 puntoTemp = getHitbox().punto(nombre);
		
		sensoresDelta.remove(nombre);
		if (nombre.equals("inf-izq") || nombre.equals("sup-izq")) {
			punto = new Vector2(puntoTemp.x - (getVelocidad(delta) * 4), puntoTemp.y);
		} else if (nombre.equals("inf-der") || nombre.equals("sup-der")) {
			punto = new Vector2(puntoTemp.x + (getVelocidad(delta) * 4), puntoTemp.y);
		}
		sensoresDelta.put(nombre, punto);
		return sensoresDelta.get(nombre);
	}
	
	/**
	 * Aqui se inicializan todas las animaciones existentes de la clase
	 */
	protected abstract void cargarAnimaciones();
	
	
	
	/**
	 * Calcula la velocidad del {@link Actor} conforme al delta pasado
	 * @param delta El delta que proviene de {@link Screen.render()}
	 * @return La velocidad
	 */
	protected float getVelocidad(float delta) {
		return delta * velocidad;
	}
	
	
	
	/**
	 * Mueve al {@link Actor} a la izquierda conforme a su velocidad
	 * @param delta El delta que proviene de {@link Screen.render()}
	 */
	protected void moverIzquierda(float delta) {
		if (estado != CAYENDO) estado = MOVIMIENTO;
		x -= getVelocidad(delta);
	}
	/**
	 * Mueve al {@link Actor} a la derecha conforme a su velocidad
	 * @param delta El delta que proviene de {@link Screen.render()}
	 */
	protected void moverDerecha(float delta) {
		if (estado != CAYENDO) estado = MOVIMIENTO;
		x += getVelocidad(delta);
	}

	protected Vector2 getPosicionCentro() {
		return new Vector2(x + width / 2, y + height / 2);
	}
	protected void setPosicionCentro(Vector2 pos) {
		x = pos.x - width / 2;
		y = pos.y - height / 2;
	}
	
	
	
	/**
	 * Regresa el hitbox del {@link Actor} en la posición actual de dicho
	 * {@link Actor}
	 * @return El hitbox
	 */
	public Rectangulo getHitbox() {
		return hitbox.posicionar(x + (width / 2), y + (height / 2));
	}

	
	
	
	@Override
	public abstract void draw(SpriteBatch batch, float parentAlpha);
	
	@Override
	public void act(float delta) {
		estado = colisionPiso ? DETENIDO : CAYENDO;
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
		String spriteSheet = D.s(name).get("sprite_sheet_" + tipoAnimacion);
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
