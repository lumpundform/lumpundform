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
 * 
 * @author Sergio
 * 
 */
public abstract class ObjetoActor extends Actor {
	// Valores estáticos de todos los actores
	public static enum Direccion {
		IZQUIERDA, DERECHA, ARRIBA, ABAJO
	}
	public static final int EXPLOTANDO = 40;

	// Animaciones
	protected Map<String, Animation> animacion;
	protected float tiempoTranscurrido;

	// Estado, Posición y Tamaño
	protected Direccion direccionX;

	// Movimiento
	protected float destinoX;
	protected float velocidad;
	protected boolean teletransportar = false;

	// Colisión
	protected Rectangulo hitbox;
	private Map<String, Vector2> sensores;
	public boolean colisionActores = false;
	public boolean colisionPiso = false;

	// Pertenece a evento
	public String perteneceAEvento;

	/**
	 * Inicializa los valores generales de todos los actores
	 */
	protected ObjetoActor(String nombre) {
		super(nombre);

		sensores = new HashMap<String, Vector2>();
		animacion = new HashMap<String, Animation>();

		tiempoTranscurrido = 0f;
	}

	/**
	 * Si el héroe esta volteando hacia la derecha
	 * 
	 * @return {@link true} o {@link false}
	 */
	public boolean derecha() {
		return direccionX == Direccion.DERECHA;
	}

	/**
	 * Regresa el punto especificado del hitbox del {@link ObjetoActor}
	 * 
	 * @param nombre
	 *            El nombre del punto. Puede ser "inf-izq", "inf-der",
	 *            "sup-izq", "sup-der"
	 * @return El punto
	 */
	public Vector2 getSensor(String nombre) {
		if (hitbox == null)
			return null;

		sensores.remove(nombre);
		sensores.put(nombre, getHitbox().punto(nombre));
		return sensores.get(nombre);
	}

	/**
	 * Posiciona al heroe en x tomando como referencia el punto especificado del
	 * hitbox del {@link ObjetoActor}
	 * 
	 * @param nombre
	 *            El nombre del punto
	 * @param x
	 *            La posicion x del punto
	 */
	public void setSensorX(String nombre, float x) {
		hitbox.centrado = false;
		hitbox.posicionar(x, getSensor(nombre).y);
		// TODO: Hacer que reste o sume dependiendo si es sup o inf
		this.x = hitbox.getCentro().x - width / 2;
		hitbox.centrado = true;
	}

	/**
	 * Posiciona al heroe en y tomando como referencia el punto especificado del
	 * hitbox del {@link ObjetoActor}
	 * 
	 * @param nombre
	 *            El nombre del punto
	 * @param y
	 *            La posicion y del punto
	 */
	public void setSensorY(String nombre, float y) {
		hitbox.centrado = false;
		hitbox.posicionar(getSensor(nombre).x, y);
		// TODO: Hacer que reste o sume dependiendo si es sup o inf
		this.y = hitbox.getCentro().y - height / 2;
		hitbox.centrado = true;
	}

	/**
	 * Aqui se inicializan todas las animaciones existentes de la clase
	 */
	protected void cargarAnimaciones(String... nombres) {
		try {
			for (String nombre : nombres) {
				animacion.put(nombre, initAnimacion(nombre));
			}
		} catch (DatoInexistenteException e) {
			U.err(e);
		}
	}

	/**
	 * Calcula la velocidad del {@link ObjetoActor} conforme al delta pasado
	 * 
	 * @param delta
	 *            El delta que proviene de {@link Screen.render()}
	 * @return La velocidad
	 */
	protected float getVelocidad(float delta) {
		return delta * velocidad;
	}

	/**
	 * Mueve al {@link ObjetoActor} a la izquierda conforme a su velocidad
	 * 
	 * @param delta
	 *            El delta que proviene de {@link Screen#render()}
	 */
	protected void moverIzquierda(float delta) {
		x -= getVelocidad(delta);
	}

	/**
	 * Mueve al {@link ObjetoActor} a la derecha conforme a su velocidad
	 * 
	 * @param delta
	 *            El delta que proviene de {@link Screen#render()}
	 */
	protected void moverDerecha(float delta) {
		x += getVelocidad(delta);
	}

	/**
	 * Regresa la posición del centro del {@link ObjetoActor}
	 * 
	 * @return La posición
	 */
	protected Vector2 getPosicionCentro() {
		return new Vector2(x + width / 2, y + height / 2);
	}

	/**
	 * Posiciona al {@link Heroe} conforme a su punto del centro
	 * 
	 * @param pos
	 *            La posición a donde mover al {@link ObjetoActor}
	 */
	protected void setPosicionCentro(Vector2 pos) {
		x = pos.x - width / 2;
		y = pos.y - height / 2;
	}

	/**
	 * Regresa el hitbox del {@link ObjetoActor} en la posición actual de dicho
	 * {@link ObjetoActor}
	 * 
	 * @return El hitbox
	 */
	public Rectangulo getHitbox() {
		return hitbox.posicionar(x + (width / 2), y + (height / 2));
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		boolean flip = false;
		TextureRegion cuadroActual = getCuadroActual();

		// Si está caminando al revés, voltea el sprite
		if (direccionX == Direccion.IZQUIERDA) {
			cuadroActual.flip(true, false);
			flip = true;
		}

		batch.draw(cuadroActual, x, y);

		if (flip)
			cuadroActual.flip(true, false);
	}

	@Override
	public void act(float delta) {
		tiempoTranscurrido += delta;
	}

	@Override
	public Actor hit(float x, float y) {
		return null;
	}

	/**
	 * Regresa el cuadro actual dentro de la animación elegida del
	 * {@link ObjetoActor}
	 * 
	 * @return El cuadro actual
	 */
	protected abstract TextureRegion getCuadroActual();

	/**
	 * Inicializa la animación del {@link ObjetoActor}, con la imagen y datos
	 * proporcionados.
	 * 
	 * @param tipoAnimacion
	 *            Una cadena con el nombre del tipo de animación a realizar, con
	 *            el que obtiene los valores de los diferentes {@link Map}s
	 * @return La animación en si
	 */
	protected Animation initAnimacion(String tipoAnimacion)
			throws DatoInexistenteException {
		String spriteSheet = D.gs(name, "sprite_sheet_" + tipoAnimacion);
		int columnas = D.gi(name, "columnas_" + tipoAnimacion);
		int renglones = D.gi(name, "renglones_" + tipoAnimacion);

		int columnasOffset = D.gi(name, "columnas_offset_" + tipoAnimacion);

		Texture texturaAnimacion = new Texture(Gdx.files.internal(spriteSheet));
		TextureRegion[][] tmp = TextureRegion.split(texturaAnimacion,
				(int) width, (int) height);
		TextureRegion[] cuadrosAnimacion = new TextureRegion[columnas
				* renglones];
		int index = 0;
		for (int i = 0; i < renglones; i++) {
			for (int j = columnasOffset; j < columnas + columnasOffset; j++) {
				cuadrosAnimacion[index++] = tmp[i][j];
			}
		}
		return new Animation(0.05f, cuadrosAnimacion);
	}

}
