package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class EscenarioHelper {
	private OrthographicCamera camara;
	private TileMapRenderer renderer;
	private EscenarioBase escenario;
	private Heroe heroe;

	public EscenarioHelper(SpriteBatch batch, OrthographicCamera cam, String nombre) {
		camara = cam;
		
		MapaHelper mh = new MapaHelper(nombre);
		
		renderer = mh.cargarMapa();
		
		escenario = new EscenarioBase(mh.getWidth(), mh.getHeight(), true, batch);
		escenario.setCamera(camara);
		
		// Agregar las colisiones
		escenario.piso = new Poligono(mh.cargarColisiones(camara, "piso"));
		
		heroe = new Heroe("heroe", mh.origenHeroe(camara));
		if (heroe != null) {
			escenario.addActor(heroe);
		}
		
	}

	public void actuarDibujar(float delta) {
		// Dibujar mapa
		renderer.render(camara);
		
		// Dibujar y actuar de todos los actores del escenario
		escenario.act(delta);
		escenario.draw();
		
		// Dibujar líneas colisión
		U.dibujarLineasColision(escenario.piso);
		
		// TODO: manejar esta lógica dentro de los actores, posiblemente dentro
		// del método act()
		if(escenario.piso.estaColisionando(new Vector2(heroe.x, heroe.y)) == "") {
			heroe.y -= 3;
		} else {
			if (escenario.piso.estaColisionando(new Vector2(heroe.x, heroe.y)) == "arriba")
				heroe.y += 3;
			heroe.x += 2;
		}
	}

}
