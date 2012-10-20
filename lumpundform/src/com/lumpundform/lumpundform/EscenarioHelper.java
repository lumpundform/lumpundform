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
	private Heroe heroe;
	private Humanoide amigo;

	public EscenarioHelper(SpriteBatch batch, OrthographicCamera cam, String nombre) {
		camara = cam;
		
		MapaHelper mh = new MapaHelper(nombre);
		
		renderer = mh.cargarMapa();
		
		escenario = new EscenarioBase(mh.getWidth(), mh.getHeight(), true, batch);
		escenario.setCamera(camara);
		
		// Agregar las colisiones
		escenario.piso = new Poligono(mh.cargarColisiones(camara, "piso"));
		
		heroe = new Heroe("heroe", mh.origenHeroe(camara));
		amigo = new Humanoide("amigo", mh.origenHeroe(camara));
		
		try {
			escenario.addActor(heroe);
			escenario.addActor(amigo);
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
		escenario.act(delta);
		escenario.draw();
		
		dibujarLineasColision();
		
		// TODO: manejar esta lógica dentro de los actores, posiblemente dentro
		// del método act()
		if(escenario.piso.estaColisionando(new Vector2(heroe.x, heroe.y)) == "") {
			heroe.y -= 3;
		} else {
			if (escenario.piso.estaColisionando(new Vector2(heroe.x, heroe.y)) == "arriba")
				heroe.y += 3;
			//heroe.x += 2;
		}
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

}
