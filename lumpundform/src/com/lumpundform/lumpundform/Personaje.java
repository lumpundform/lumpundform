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

	private World mundo;
	protected Body cuerpo;

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
	protected boolean cambio_direccion;

	/**
	 * Inicializa los valores generales de todos los personajes
	 */
	protected Personaje(String nombre, World m, float w, float h) {
		super(nombre);
		mundo = m;
		width = w;
		height = h;
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


		// Dibuja el cuadro actual
		batch.draw(cuadroActual, x
				- (width / 2), y
				- (height / 2));
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
		
		if (estado == MOVIMIENTO) {
			if (cambio_direccion) {
				detenerMovX();
				cambio_direccion = false;
			}
			if (destinoX == x) { // No tiene destino, solo dirección
				float impulsoX = direccionX == IZQUIERDA ? -12.0f : 12.0f;
				
				cuerpo.applyLinearImpulse(new Vector2(impulsoX, 0.0f),
						new Vector2(0,
								0));
			}
		} else if (estado == CAYENDO) {
			if (cuerpo.getLinearVelocity().y == 0)
				estado = DE_PIE;
		} else if (estado == DE_PIE) {
			detenerMovX();
		}
		
		// TODO: hacer una funcion para que convierta los datos de pixeles de
		// escenario a metros de mundo
		
		x = cuerpo.getPosition().x * PantallaJuego.PIXELS_PER_METER;
		y = cuerpo.getPosition().y * PantallaJuego.PIXELS_PER_METER;
	}
	
	private void detenerMovX() {
		cuerpo.setLinearVelocity(new Vector2(0, cuerpo.getLinearVelocity().y));
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
		cuerpoBodyDef.type = BodyDef.BodyType.DynamicBody;
		cuerpoBodyDef.position.set(2.0f, 5.0f);
		cuerpoBodyDef.angle = (float) Math.toRadians(0.0f);

		cuerpo = mundo.createBody(cuerpoBodyDef);

		/**
		 * Boxes are defined by their "half width" and "half height", hence the
		 * 2 multiplier.
		 */
		PolygonShape cuerpoShape = new PolygonShape();
		cuerpoShape.setAsBox((width * 0.33f) / (2 * PantallaJuego.PIXELS_PER_METER),
				height / (2 * PantallaJuego.PIXELS_PER_METER));

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
		
		cuerpo.createFixture(cuerpoShape, 8f);
		cuerpoShape.dispose();
		// cuerpo.setLinearVelocity(new Vector2(0.0f, 0.0f));
		cuerpo.setLinearDamping(5.0f);
		
	}
}
