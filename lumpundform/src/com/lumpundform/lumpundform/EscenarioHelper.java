package com.lumpundform.lumpundform;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Clase que ayuda con las funciones de los escenarios, como cargar el mapa,
 * los actores, las líneas de colisión y hace que actúen y se dibujen los
 * actores
 * @author Sergio
 *
 */
public class EscenarioHelper {
	private CamaraJuego camara;
	private MapaHelper mh;
	private TileMapRenderer renderer;
	private EscenarioBase escenario;

	/**
	 * Inicializa el escenario, el mapa, las colisiones y el héroe para el 
	 * escenario del nombre dado
	 * @param batch El SpriteBatch con el que se van a dibujar los actores
	 * @param cam La cámara de la pantalla actual
	 * @param nombre El nombre del escenario para referenciarlo en la clase D
	 */
	public EscenarioHelper(SpriteBatch batch, CamaraJuego cam, String nombre) {
		camara = cam;
		
		mh = new MapaHelper(nombre);
		renderer = mh.cargarMapa();
		
		escenario = new EscenarioBase(mh.getWidth(), mh.getHeight(), true, batch);
		escenario.setCamera(camara);
		
		// Agregar las colisiones del piso
		escenario.piso = new Poligono(mh.cargarColisiones(camara, "piso"));
		
		// Héroe y actores de prueba
		Vector2 origenHeroe = mh.origenHeroe(camara);
		Heroe heroe = new Heroe("heroe", origenHeroe);
		Humanoide amigo = new Humanoide("amigo", origenHeroe);
		
		try {
			escenario.addActor(heroe);
			escenario.addActor(amigo);
		} catch (NullPointerException e) {
			U.err(e);
		}
	}

	/**
	 * Manda a actuar a todos los actores del escenario y los dibuja. También
	 * calcula las colisiones de los actores y dibuja las líneas de colisión
	 * @param delta Proviene de Screen.render()
	 */
	public void actuarDibujar(float delta) {
		
		// Dibujar Background
		mh.renderFondo(camara);
		
		// Dibujar mapa
		renderer.render(camara);
		
		// Colisión
		escenario.colisionActores();
		escenario.colisionPiso(delta);
		
		// Actuar de todos los actores del escenario
		escenario.act(delta);
		
		acomodarActores();
		
		// Debug líneas colisión
		dibujarLineasColision();
		
		// Dibujar los actores del escenario 
		escenario.draw();
		
		// Mover la cámara
		moverCamara(delta);
	}
	
	/**
	 * Dibuja las líneas de colisión del piso del escenario y de todos los
	 * actores que se encuentran en el escenario
	 */
	private void dibujarLineasColision() {
		List<Actor> actores = escenario.getActors();
		
		for (int i = 0; i < actores.size(); i++) {
			ObjetoActor actor = (ObjetoActor) actores.get(i);
			U.dibujarLineasColision(actor.getHitbox(), camara);
		}

		U.dibujarLineasColision(escenario.piso, camara);
	}
	
	/**
	 * Mueve la cámara conforme a la posición del {@link Heroe}
	 * @param delta El delta de {@link Screen.render(float)}
	 */
	private void moverCamara(float delta) {
		Heroe heroe = getHeroe();
		float factor = 1.7f;
		float destinoCamara;
		
		if (heroe.direccionX == ObjetoActor.DERECHA) {
			destinoCamara = heroe.x + heroe.width / 2 + camara.viewportWidth / 6;
			if (camara.position.x < destinoCamara) {
				camara.setPosicion((float) (camara.position.x + heroe.getVelocidad(delta) * factor), camara.position.y);
			}
			if (camara.position.x >= destinoCamara) {
				camara.setPosicion(destinoCamara, camara.position.y);
			}
		} else {
			destinoCamara = heroe.x + heroe.width / 2 - camara.viewportWidth / 6;
			if (camara.position.x > destinoCamara) {
				camara.setPosicion((float) (camara.position.x - heroe.getVelocidad(delta) * factor), camara.position.y);
			}
			if (camara.position.x <= destinoCamara) {
				camara.setPosicion(destinoCamara, camara.position.y);
			}
		}
		
		if (camara.posicionOrigen.x < 0) camara.setPosicionOrigen(0, camara.posicionOrigen.y);
		if (camara.posicionOrigen.x + camara.viewportWidth > mh.getWidth())
			camara.setPosicionOrigen(mh.getWidth() - camara.viewportWidth, camara.posicionOrigen.y);
	}
	
	/**
	 * Regresa al héroe del escenario
	 * @return El héroe
	 */
	public Heroe getHeroe() {
		return (Heroe) escenario.findActor("heroe");
	}
	
	public Poligono getPiso() {
		return escenario.piso;
	}

	/**
	 * Limita las posiciones de los actores del escenario para que no se salgan
	 * del mismo
	 */
	private void acomodarActores() {
		List<Actor> actores = escenario.getActors();
		
		for (int i = 0; i < actores.size(); i ++) {
			ObjetoActor actor = (ObjetoActor) actores.get(i);
			if (actor.getSensor("inf-izq").x < 0) actor.setSensorX("inf-izq", 0.0f);
			if (actor.getSensor("inf-der").x > mh.getWidth()) actor.setSensorX("inf-izq", (mh.getWidth() - actor.getHitbox().getAncho()));
		}
	}

}
