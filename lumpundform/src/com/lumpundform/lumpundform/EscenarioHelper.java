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
		
		MapaHelper mh = new MapaHelper(nombre);
		renderer = mh.cargarMapa();
		
		escenario = new EscenarioBase(mh.getWidth(), mh.getHeight(), true, batch);
		escenario.setCamera(camara);
		
		// Agregar las colisiones del piso
		escenario.piso = new Poligono(mh.cargarColisiones(camara, "piso"));
		
		// Héroe y actores de prueba
		Vector2 origenHeroe = mh.origenHeroe(camara);
		Heroe heroe = new Heroe("heroe", origenHeroe);
		Humanoide amigo = new Humanoide("amigo", origenHeroe);
		Humanoide amigo2 = new Humanoide("amigo", new Vector2(origenHeroe.x + 350, origenHeroe.y + 99));
		Humanoide amigo3 = new Humanoide("amigo", new Vector2(origenHeroe.x + 250, origenHeroe.y + 201));
		Humanoide amigo4 = new Humanoide("amigo", new Vector2(origenHeroe.x - 100, origenHeroe.y + 300));
		
		try {
			escenario.addActor(heroe);
			escenario.addActor(amigo);
			escenario.addActor(amigo2);
			escenario.addActor(amigo3);
			escenario.addActor(amigo4);
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
		// Dibujar mapa
		renderer.render(camara);
		
		// Colisión
		escenario.colisionActores();
		escenario.colisionPiso();
		
		// Actuar de todos los actores del escenario
		escenario.act(delta);
		
		// Debug líneas colisión
		dibujarLineasColision();
		
		// Mover la cámara
		moverCamara();
		
		// Dibujar los actores del escenario 
		escenario.draw();
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
	
	private void moverCamara() {
		camara.setPosicion(getHeroe().x, camara.position.y);
		
		if (camara.posicionOrigen.x < 0) camara.setPosicionOrigen(0, camara.posicionOrigen.y);
		U.l("Posicion origen camara", camara.position);
	}
	
	/**
	 * Regresa al héroe del escenario
	 * @return El héroe
	 */
	private ObjetoActor getHeroe() {
		return (ObjetoActor) escenario.findActor("heroe");
	}

}
