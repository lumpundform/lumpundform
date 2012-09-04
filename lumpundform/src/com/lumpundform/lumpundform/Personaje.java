package com.lumpundform.lumpundform;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Personaje extends Actor {
	// Valores Estáticos
	protected static final int DE_PIE = 0;
	protected static final int MOVIMIENTO = 1;
	protected static final int CAYENDO = 2;
	protected static final int IZQUIERDA = 10;
	protected static final int DERECHA = 11;
	protected static final int ARRIBA = 20;
	protected static final int ABAJO = 21;

	protected static final float PIXELS_PER_METER = 60.0f;

	private World mundo;
	private Body cuerpo;

	// Animaciones
	protected Map<String, String> nombreImagen;
	protected Map<String, Integer> columnas;
	protected Map<String, Integer> renglones;
	protected Map<String, Animation> animacion;
	private float tiempoTranscurrido;

	// Estado, Posición y Tamaño
	protected int estado;
	protected int direccionX;
	protected int direccionY;

	// Movimiento
	protected float destinoX;
	protected float velocidad;

	/**
	 * Inicializa los valores generales de todos los personajes
	 */
	protected Personaje(String nombre, World m) {
		super(nombre);
		mundo = m;
		nombreImagen = new HashMap<String, String>();
		columnas = new HashMap<String, Integer>();
		renglones = new HashMap<String, Integer>();
		animacion = new HashMap<String, Animation>();

		crearCuerpo();

		tiempoTranscurrido = 0f;
	}

	@Override
	public void draw(SpriteBatch batch, float alpha) {
		boolean flip = false;

		if (estado == CAYENDO)
			Gdx.app.log("Estado", "Estado: " + estado);

		// Revisar de cual animación se va a agarrar el cuadro actual
		String nombreAnimacion;
		switch (estado) {
		case DE_PIE:
		default:
			nombreAnimacion = "dePie";
			tiempoTranscurrido = 0f; // Reinicia el tiempo transcurrido para que
										// empiece las animaciones desde el
										// principio
			break;
		case MOVIMIENTO:
			nombreAnimacion = "corriendo";
			break;
		case CAYENDO:
			nombreAnimacion = "cayendo";
			break;
		}
		TextureRegion cuadroActual = animacion.get(nombreAnimacion)
				.getKeyFrame(tiempoTranscurrido, true);

		// Si está caminando al revés, voltea el sprite
		if (direccionX == IZQUIERDA) {
			cuadroActual.flip(true, false);
			flip = true;
		}
		
		cuerpo.setTransform(x, y, 0.0f);

		// Dibuja el cuadro actual
		batch.draw(cuadroActual, cuerpo.getPosition().x
				- (width / 2), cuerpo.getPosition().y
				- (height / 2));
		Gdx.app.log("X", "X: " + cuerpo.getPosition().x);
		// Después de dibujarlo, lo vuelve a voltear si se volteó para dejarlo
		// en posición normal
		if (flip)
			cuadroActual.flip(true, false);
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void act(float delta) {
		tiempoTranscurrido += delta;

		// Para caer
		if (y > (20 + height / 2)) {
			estado = CAYENDO;
			y -= velocidad * delta;
			if (y <= (20 + height / 2)) {
				estado = DE_PIE;
			}
		} else if (destinoX != x) {
			estado = MOVIMIENTO;
			if (destinoX < x) { // Se está moviendo hacia la izquierda
				direccionX = IZQUIERDA;
				x -= velocidad * delta;
				if (x < destinoX) {
					x = destinoX;
					estado = DE_PIE;
				}
			} else { // Se está moviendo hacia la derecha
				direccionX = DERECHA;
				x += velocidad * delta;
				if (x > destinoX) {
					x = destinoX;
					estado = DE_PIE;
				}
			}
		} else {
			estado = DE_PIE;
		}
	}

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
		Texture texturaAnimacion = new Texture(Gdx.files.internal(nombreImagen
				.get(tipoAnimacion)));
		TextureRegion[][] tmp = TextureRegion.split(texturaAnimacion,
				(int) width, (int) height);
		TextureRegion[] cuadrosAnimacion = new TextureRegion[columnas
				.get(tipoAnimacion) * renglones.get(tipoAnimacion)];
		int index = 0;
		for (int i = 0; i < renglones.get(tipoAnimacion); i++) {
			for (int j = 0; j < columnas.get(tipoAnimacion); j++) {
				cuadrosAnimacion[index++] = tmp[i][j];
			}
		}
		return new Animation(0.05f, cuadrosAnimacion);
	}

	private void crearCuerpo() {
		BodyDef cuerpoBodyDef = new BodyDef();
		//TODO: hacer implemetnación para que el cuerpo sea dinámico
		cuerpoBodyDef.type = BodyDef.BodyType.StaticBody;
		cuerpoBodyDef.position.set(0, 0);

		cuerpo = mundo.createBody(cuerpoBodyDef);

		/**
		 * Boxes are defined by their "half width" and "half height", hence the
		 * 2 multiplier.
		 */
		PolygonShape jumperShape = new PolygonShape();
		jumperShape.setAsBox(width / (2 * PIXELS_PER_METER), height
				/ (2 * PIXELS_PER_METER));

		/**
		 * The character should not ever spin around on impact.
		 */
		cuerpo.setFixedRotation(true);

		/**
		 * The density of the jumper, 70, was found experimentally. Play with
		 * the number and watch how the character moves faster or slower.
		 * 
		 * The linear damping was also found the same way.
		 */
		
		cuerpo.createFixture(jumperShape, 1.0f);
		jumperShape.dispose();
		cuerpo.setLinearVelocity(new Vector2(0.0f, 0.0f));
		cuerpo.setLinearDamping(5.0f);
	}
}
