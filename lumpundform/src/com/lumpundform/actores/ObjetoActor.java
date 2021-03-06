package com.lumpundform.actores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lumpundform.acciones.ObjetoActorAction;
import com.lumpundform.animacion.Animacion;
import com.lumpundform.colision.Hitbox;
import com.lumpundform.colision.Rectangulo;
import com.lumpundform.escenario.EscenarioBase;
import com.lumpundform.excepciones.AnimacionInexistenteException;

/**
 * Clase personalizada que extiende a {@link Actor} y le agrega valores
 * específicos del juego.
 * 
 * @author Sergio Valencia
 * 
 */
public abstract class ObjetoActor extends Actor {
	// Valores estáticos de todos los actores
	public static enum Direccion {
		IZQUIERDA, DERECHA, ARRIBA, ABAJO
	}

	// Estado
	private String estado;
	private String estadoDefault;

	// ID
	private int id;

	// Animaciones
	private Map<String, Animacion> animaciones;
	private float tiempoTranscurrido;
	private boolean loopAnimacion;
	private float widthTextura;
	private float heightTextura;
	private boolean quitarConAnimacion;

	// Estado, Posición y Tamaño
	private Direccion direccionX;

	// Movimiento
	private float destinoX;
	private Direccion direccionDestinoX;
	private float velocidad;
	private float velocidadVertical;
	private boolean teletransportar = false;
	private boolean caer = false;

	// Colisión
	private Hitbox hitboxDefault;
	private Map<String, Hitbox> hitboxes;
	private Map<String, Vector2> esquinas;
	private boolean colisionPiso = false;

	// Pertenece a evento
	private String perteneceAEvento;

	/**
	 * Inicializa los valores generales de todos los actores. Sólo debe usarse
	 * por las subclases de {@link ObjetoActor}.
	 * 
	 * @param nombre
	 *            El nombre del {@link Actor} para poderlo referenciar.
	 */
	protected ObjetoActor(String nombre) {
		super();

		setName(nombre);

		setHitboxes(new HashMap<String, Hitbox>());
		esquinas = new HashMap<String, Vector2>();
		setAnimaciones(new HashMap<String, Animacion>());
		setLoopAnimacion(true);

		setTiempoTranscurrido(0f);

		setQuitarConAnimacion(false);

		addAction(new ObjetoActorAction());
	}

	/**
	 * Quita al actor del {@link com.lumpundform.escenario.EscenarioBase} al que
	 * pertenece y borra sus acciones.
	 */
	public void quitar() {
		remove();
	}

	/**
	 * Si el héroe esta volteando hacia la derecha.
	 * 
	 * @return <code>true</code> ó <code>false</code>.
	 */
	public boolean derecha() {
		return getDireccionX() == Direccion.DERECHA;
	}

	/**
	 * Regresa el punto especificado del hitbox del {@link ObjetoActor}.
	 * 
	 * @param nombre
	 *            El nombre del punto. Puede ser <code>"inf-izq"</code>,
	 *            <code>"inf-der"</code>, <code>"sup-izq"</code>,
	 *            <code>"sup-der"</code>.
	 * @return El punto.
	 */
	public Vector2 getEsquina(String nombre) {
		if (getHitbox() == null)
			return null;

		esquinas.remove(nombre);
		esquinas.put(nombre, getHitbox().punto(nombre));
		return esquinas.get(nombre);
	}

	/**
	 * Posiciona al heroe en x tomando como referencia el punto especificado del
	 * hitbox del {@link ObjetoActor}.
	 * 
	 * @param nombre
	 *            El nombre del punto igual que en {@link #getEsquina(String)}.
	 * @param x
	 *            La posicion <code>x</code> del punto.
	 */
	public void setEsquinaX(String nombre, float x) {
		getHitboxAnimacion().setCentrado(false);
		getHitboxAnimacion().posicionar(x, getEsquina(nombre).y);
		// TODO: Hacer que reste o sume dependiendo si es sup o inf
		setX(getHitboxAnimacion().getCentro().x - getWidth() / 2);
		getHitboxAnimacion().setCentrado(true);
	}

	/**
	 * Posiciona al heroe en y tomando como referencia el punto especificado del
	 * hitbox del {@link ObjetoActor}.
	 * 
	 * @param nombre
	 *            El nombre del punto igual que en {@link #getEsquina(String)}.
	 * @param y
	 *            La posicion <code>y</code> del punto.
	 */
	public void setEsquinaY(String nombre, float y) {
		getHitboxAnimacion().setCentrado(false);
		getHitboxAnimacion().posicionar(getEsquina(nombre).x, y);
		// TODO: Hacer que reste o sume dependiendo si es sup o inf
		setY(getHitboxAnimacion().getCentro().y - (getHeight() / 2));
		getHitboxAnimacion().setCentrado(true);
	}

	/**
	 * Inicializa todas las animaciones existentes de la clase.
	 * 
	 * @param nombres
	 *            Una lista de todos los nombres de la animaciones del
	 *            {@link ObjetoActor}.
	 */
	protected void cargarAnimaciones(String... nombres) {
		for (String nombre : nombres) {
			getAnimaciones().put(nombre, new Animacion(this, nombre));
		}
	}

	/**
	 * Calcula la velocidad del {@link ObjetoActor} con el delta default.
	 * 
	 * @return La velocidad calculada.
	 */
	public float velDelta() {
		return velDelta(Gdx.graphics.getDeltaTime());
	}

	/**
	 * Calcula la velocidad del {@link ObjetoActor} conforme al delta
	 * transcurrido.
	 * 
	 * @param delta
	 *            El delta de {@link Screen#render(float)}.
	 * @return La velocidad calculada.
	 */
	public float velDelta(float delta) {
		return delta * velocidad;
	}

	/**
	 * Calcula la velocidad vertical del {@link ObjetoActor} conforme al delta
	 * transcurrido.
	 * 
	 * @param delta
	 *            El delta de {@link Screen#render(float)}.
	 * @return La velocidad vertical calculada.
	 */
	public float calcVelocidadVertical(float delta) {
		return delta * velocidadVertical;
	}

	/**
	 * Mueve al {@link ObjetoActor} en la dirección en la que está volteando
	 * actualmente.
	 */
	public void moverEnDireccion() {
		if (derecha()) {
			moverDerecha();
		} else {
			moverIzquierda();
		}
	}

	/**
	 * Mueve al {@link ObjetoActor} a la izquierda con el delta default.
	 */
	protected void moverIzquierda() {
		moverIzquierda(Gdx.graphics.getDeltaTime());
	}

	/**
	 * Mueve al {@link ObjetoActor} a la izquierda conforme a su velocidad.
	 * 
	 * @param delta
	 *            El delta que proviene de {@link Screen#render(float)}.
	 */
	protected void moverIzquierda(float delta) {
		setX(getX() - velDelta(delta));
	}

	/**
	 * Mueve al {@link ObjetoActor} a la derecha con el delta default.
	 */
	protected void moverDerecha() {
		moverDerecha(Gdx.graphics.getDeltaTime());
	}

	/**
	 * Mueve al {@link ObjetoActor} a la derecha conforme a su velocidad.
	 * 
	 * @param delta
	 *            El delta que proviene de {@link Screen#render(float)}.
	 */
	protected void moverDerecha(float delta) {
		setX(getX() + velDelta(delta));
	}

	/**
	 * Mueve al {@link ObjetoActor} hacia abajo conforme a su velocidad
	 * vertical.
	 * 
	 * @param delta
	 *            El delta de {@link Screen#render(float)}.
	 */
	protected void moverAbajo(float delta) {
		setY(getY() - calcVelocidadVertical(delta));
	}

	/**
	 * Regresa la posición del centro del {@link ObjetoActor}.
	 * 
	 * @return La posición.
	 */
	public Vector2 getPosicionCentro() {
		return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}

	/**
	 * Regresa la posición de <code>x</code> del {@link ObjetoActor}.
	 * 
	 * @return El valor de <cdoe>x</code>.
	 */
	public float getXCentro() {
		return getPosicionCentro().x;
	}

	/**
	 * Posiciona al {@link Heroe} conforme a su punto del centro.
	 * 
	 * @param pos
	 *            La posición a donde mover al {@link ObjetoActor}.
	 */
	public void setPosicionCentro(Vector2 pos) {
		setX(pos.x - getWidth() / 2);
		setY(pos.y - getHeight() / 2);
	}

	/**
	 * Regresa el hitbox del {@link ObjetoActor} en la posición actual de dicho
	 * {@link ObjetoActor}.
	 * 
	 * @return El hitbox.
	 */
	public Rectangulo getHitbox() {
		return getHitboxAnimacion().posicion();
	}

	/**
	 * Regresa el hitbox adecuado para la animación en efecto.
	 * 
	 * @return El hitbox.
	 */
	public Hitbox getHitboxAnimacion() {
		if (getHitboxes().containsKey(getEstado())) {
			return getHitboxes().get(getEstado());
		} else {
			return hitboxDefault;
		}
	}

	/**
	 * Dibuja al {@link ObjetoActor} en su posición actual. Calcula el cuadro
	 * que debe dibujar de entre las animaciones del {@link ObjetoActor}
	 * dependiendo de su estado y el tiempo transcurrido.
	 */
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		boolean flip = false;
		TextureRegion cuadroActual = getCuadroActual();

		// Si está caminando al revés, voltea el sprite
		if (!derecha()) {
			cuadroActual.flip(true, false);
			flip = true;
		}

		Color color = batch.getColor();
		batch.setColor(color.r, color.g, color.b, parentAlpha);
		batch.draw(cuadroActual, getX(), getY(), getWidth(), getHeight());

		if (flip)
			cuadroActual.flip(true, false);
	}

	/**
	 * Regresa el cuadro actual del {@link ObjetoActor} dependiendo de su
	 * animación actual.
	 * 
	 * @return El cuadro actual.
	 */
	protected TextureRegion getCuadroActual() {
		return getAnimacionActual().getKeyFrame(getTiempoTranscurrido(),
				isLoopAnimacion());
	}

	protected <T extends Actor> List<T> getActoresEscenario(Class<T> clase) {
		EscenarioBase escenario = (EscenarioBase) getStage();
		return (escenario == null) ? null : escenario.getActores(clase);
	}

	public boolean isColisionPiso() {
		return colisionPiso;
	}

	public void setColisionPiso(boolean colisionPiso) {
		this.colisionPiso = colisionPiso;
	}

	public Map<String, Animacion> getAnimaciones() {
		return animaciones;
	}

	/**
	 * Regresa la {@link Animacion} del {@link ObjetoActor} dependiendo de su
	 * estado actual.
	 * 
	 * @return La {@link Animacion}.
	 */
	public Animacion getAnimacion() {
		return getAnimacion(getEstado());
	}

	/**
	 * Regresa la {@link Animacion} del {@link ObjetoActor} dependiendo del
	 * nombre que se especificó.
	 * 
	 * @param nombre
	 *            El nombre de la animación.
	 * @return La {@link Animacion}.
	 */
	public Animacion getAnimacion(String nombre) {
		if (getAnimaciones().containsKey(nombre)) {
			return getAnimaciones().get(nombre);
		} else if (nombre != getEstadoDefault()) {
			return getAnimacion(getEstadoDefault());
		} else {
			throw new AnimacionInexistenteException(getName(), nombre);
		}
	}

	/**
	 * Regresa la {@link Animation} que corresponde actualmente a la
	 * {@link Animacion}.
	 * 
	 * @return La {@link Animation}.
	 */
	protected Animation getAnimacionActual() {
		return getAnimacion().actual();
	}

	public void setAnimaciones(Map<String, Animacion> animacion) {
		this.animaciones = animacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoDefault() {
		return estadoDefault;
	}

	public void setEstadoDefault(String estadoDefault) {
		this.estadoDefault = estadoDefault;
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

	public void setHitboxDefault(Hitbox hitbox) {
		this.hitboxDefault = hitbox;
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

	public Direccion getDireccionDestinoX() {
		return direccionDestinoX;
	}

	public void setDireccionDestinoX(Direccion direccionDestinoX) {
		this.direccionDestinoX = direccionDestinoX;
	}

	public boolean isCaer() {
		return caer;
	}

	public void setCaer(boolean caer) {
		this.caer = caer;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isLoopAnimacion() {
		return loopAnimacion;
	}

	public void setLoopAnimacion(boolean loopAnimacion) {
		this.loopAnimacion = loopAnimacion;
	}

	/**
	 * Si no se ha especificado el ancho de la textura, se inicializa igual que
	 * el ancho del {@link Actor}.
	 */
	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		if (getWidthTextura() == 0.0f) {
			setWidthTextura(width);
		}
	}

	/**
	 * Si no se ha especificado el alto de la textura, se inicializa igual que
	 * el alto del {@link Actor}.
	 */
	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		if (getHeightTextura() == 0.0f) {
			setHeightTextura(height);
		}
	}

	public float getWidthTextura() {
		return widthTextura;
	}

	public void setWidthTextura(float widthTextura) {
		this.widthTextura = widthTextura;
	}

	public float getHeightTextura() {
		return heightTextura;
	}

	public void setHeightTextura(float heightTextura) {
		this.heightTextura = heightTextura;
	}

	public boolean isQuitarConAnimacion() {
		return quitarConAnimacion;
	}

	public void setQuitarConAnimacion(boolean quitarConAnimacion) {
		this.quitarConAnimacion = quitarConAnimacion;
	}

	public float getVelocidadVertical() {
		return velocidadVertical;
	}

	public void setVelocidadVertical(float velocidadVertical) {
		this.velocidadVertical = velocidadVertical;
	}

	public Map<String, Hitbox> getHitboxes() {
		return hitboxes;
	}

	public void setHitboxes(Map<String, Hitbox> hitboxes) {
		this.hitboxes = hitboxes;
	}

}
