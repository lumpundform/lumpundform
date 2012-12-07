package com.lumpundform.actores;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lumpundform.colision.Rectangulo;
import com.lumpundform.excepciones.DatoInexistenteException;
import com.lumpundform.utilerias.D;
import com.lumpundform.utilerias.U;

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

	// Animaciones
	private Map<String, Animation> animacion;
	private float tiempoTranscurrido;

	// Estado, Posición y Tamaño
	private Direccion direccionX;

	// Movimiento
	private float destinoX;
	private float velocidad;
	private boolean teletransportar = false;

	// Colisión
	private Rectangulo hitbox;
	private Map<String, Vector2> sensores;
	private boolean colisionActores = false;
	private boolean colisionPiso = false;

	// Pertenece a evento
	private String perteneceAEvento;

	/**
	 * Inicializa los valores generales de todos los actores
	 */
	protected ObjetoActor(String nombre) {
		super();

		setName(nombre);

		sensores = new HashMap<String, Vector2>();
		setAnimacion(new HashMap<String, Animation>());

		setTiempoTranscurrido(0f);
	}

	/**
	 * Si el héroe esta volteando hacia la derecha
	 * 
	 * @return {@link true} o {@link false}
	 */
	public boolean derecha() {
		return getDireccionX() == Direccion.DERECHA;
	}

	/**
	 * Regresa el punto especificado del hitbox del {@link ObjetoActor}
	 * 
	 * @param nombre
	 *            El nombre del punto. Puede ser "inf-izq", "inf-der",
	 *            "sup-izq", "sup-der"
	 * @return El punto
	 */
	public Vector2 getEsquina(String nombre) {
		if (getHitbox() == null)
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
	public void setEsquinaX(String nombre, float x) {
		hitbox.setCentrado(false);
		hitbox.posicionar(x, getEsquina(nombre).y);
		// TODO: Hacer que reste o sume dependiendo si es sup o inf
		setX(hitbox.getCentro().x - getWidth() / 2);
		hitbox.setCentrado(true);
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
	public void setEsquinaY(String nombre, float y) {
		getHitbox().setCentrado(false);
		getHitbox().posicionar(getEsquina(nombre).x, y);
		// TODO: Hacer que reste o sume dependiendo si es sup o inf
		setY(getHitbox().getCentro().y - getHeight() / 2);
		getHitbox().setCentrado(true);
	}

	/**
	 * Aqui se inicializan todas las animaciones existentes de la clase
	 */
	protected void cargarAnimaciones(String... nombres) {
		try {
			for (String nombre : nombres) {
				getAnimacion().put(nombre, initAnimacion(nombre));
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
	public float getVelocidad(float delta) {
		return delta * velocidad;
	}

	/**
	 * Mueve al {@link ObjetoActor} a la izquierda conforme a su velocidad
	 * 
	 * @param delta
	 *            El delta que proviene de {@link Screen#render()}
	 */
	protected void moverIzquierda(float delta) {
		setX(getX() - getVelocidad(delta));
	}

	/**
	 * Mueve al {@link ObjetoActor} a la derecha conforme a su velocidad
	 * 
	 * @param delta
	 *            El delta que proviene de {@link Screen#render()}
	 */
	protected void moverDerecha(float delta) {
		setX(getX() + getVelocidad(delta));
	}

	/**
	 * Regresa la posición del centro del {@link ObjetoActor}
	 * 
	 * @return La posición
	 */
	public Vector2 getPosicionCentro() {
		return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}

	/**
	 * Posiciona al {@link Heroe} conforme a su punto del centro
	 * 
	 * @param pos
	 *            La posición a donde mover al {@link ObjetoActor}
	 */
	public void setPosicionCentro(Vector2 pos) {
		setX(pos.x - getWidth() / 2);
		setY(pos.y - getHeight() / 2);
	}

	/**
	 * Regresa el hitbox del {@link ObjetoActor} en la posición actual de dicho
	 * {@link ObjetoActor}
	 * 
	 * @return El hitbox
	 */
	public Rectangulo getHitbox() {
		return hitbox.posicionar(getX() + (getWidth() / 2), getY()
				+ (getHeight() / 2));
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		boolean flip = false;
		TextureRegion cuadroActual = getCuadroActual();

		// Si está caminando al revés, voltea el sprite
		if (getDireccionX() == Direccion.IZQUIERDA) {
			cuadroActual.flip(true, false);
			flip = true;
		}

		Color color = batch.getColor();
		batch.setColor(color.r, color.g, color.b, parentAlpha);
		batch.draw(cuadroActual, getX(), getY());

		if (flip)
			cuadroActual.flip(true, false);
	}

	@Override
	public void act(float delta) {
		setTiempoTranscurrido(getTiempoTranscurrido() + delta);
	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
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
	private Animation initAnimacion(String tipoAnimacion)
			throws DatoInexistenteException {
		String spriteSheet = D.gs(getName(), "sprite_sheet_" + tipoAnimacion);
		int columnas = D.gi(getName(), "columnas_" + tipoAnimacion);
		int renglones = D.gi(getName(), "renglones_" + tipoAnimacion);

		int columnasOffset = D.gi(getName(), "columnas_offset_" + tipoAnimacion);

		Texture texturaAnimacion = new Texture(Gdx.files.internal(spriteSheet));
		TextureRegion[][] tmp = TextureRegion.split(texturaAnimacion,
				(int) getWidth(), (int) getHeight());
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

	public boolean isColisionPiso() {
		return colisionPiso;
	}

	public void setColisionPiso(boolean colisionPiso) {
		this.colisionPiso = colisionPiso;
	}

	public boolean isColisionActores() {
		return colisionActores;
	}

	public void setColisionActores(boolean colisionActores) {
		this.colisionActores = colisionActores;
	}

	public Map<String, Animation> getAnimacion() {
		return animacion;
	}

	public void setAnimacion(Map<String, Animation> animacion) {
		this.animacion = animacion;
	}

	public float getTiempoTranscurrido() {
		return tiempoTranscurrido;
	}

	public void setTiempoTranscurrido(float tiempoTranscurrido) {
		this.tiempoTranscurrido = tiempoTranscurrido;
	}

	public String getPerteneceAEvento() {
		return perteneceAEvento;
	}

	public void setPerteneceAEvento(String perteneceAEvento) {
		this.perteneceAEvento = perteneceAEvento;
	}

	public void setHitbox(Rectangulo hitbox) {
		this.hitbox = hitbox;
	}

	public float getDestinoX() {
		return destinoX;
	}

	public void setDestinoX(float destinoX) {
		this.destinoX = destinoX;
	}

	public Direccion getDireccionX() {
		return direccionX;
	}

	public void setDireccionX(Direccion direccionX) {
		this.direccionX = direccionX;
	}

	public float getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(float velocidad) {
		this.velocidad = velocidad;
	}

	public boolean isTeletransportar() {
		return teletransportar;
	}

	public void setTeletransportar(boolean teletransportar) {
		this.teletransportar = teletransportar;
	}

}
