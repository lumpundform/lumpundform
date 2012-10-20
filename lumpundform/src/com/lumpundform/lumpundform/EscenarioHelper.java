package com.lumpundform.lumpundform;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EscenarioHelper {
	private OrthographicCamera camara;
	private TileMapRenderer renderer;
	private EscenarioBase escenario;

	public EscenarioHelper(SpriteBatch batch, OrthographicCamera cam, String nombre) {
		camara = cam;
		
		MapaHelper mh = new MapaHelper(nombre);
		
		renderer = mh.cargarMapa();
		
		escenario = new EscenarioBase(mh.getWidth(), mh.getHeight(), true, batch);
		escenario.setCamera(camara);
		
		// Agregar las colisiones
		escenario.piso = new Poligono(mh.cargarColisiones(camara, "piso"));
		
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

		// Detectar gestos con DetectorGestos
		Gdx.input.setInputProcessor(new ProcesadorEntrada(heroe));
		
	}

	public void actuarDibujar(float delta) {
		// Dibujar mapa
		renderer.render(camara);
		
		// Dibujar y actuar de todos los actores del escenario
		escenario.colisionActores();
		escenario.colisionPiso();
		escenario.act(delta);
		escenario.draw();
		
		dibujarLineasColision();
	}
	
	/**
	 * Dibuja las líneas de colisión del piso del escenario y de todos los
	 * actores que se encuentran en el escenario
	 */
	public void dibujarLineasColision() {
		List<Actor> actores = escenario.getActors();
		
		for (int i = 0; i < actores.size(); i++) {
			ObjetoActor actor = (ObjetoActor) actores.get(i);
			U.dibujarLineasColision(actor.getHitbox());
		}

		U.dibujarLineasColision(escenario.piso);
	}
	
	public ObjetoActor getHeroe() {
		return (ObjetoActor) escenario.findActor("heroe");
	}

}
