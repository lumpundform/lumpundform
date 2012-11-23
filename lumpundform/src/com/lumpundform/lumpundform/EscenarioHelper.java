package com.lumpundform.lumpundform;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Clase que ayuda con las funciones de los escenarios, como cargar el mapa, los
 * actores, las líneas de colisión y hace que actúen y se dibujen los actores
 * 
 * @author Sergio
 * 
 */
public class EscenarioHelper {
	private CamaraJuego camara;
	private MapaHelper mh;
	private InterfazHelper ih;
	private EscenarioBase escenario;

	/**
	 * Inicializa el escenario, el mapa, las colisiones y el {@link Heroe} para
	 * el escenario del nombre dado
	 * 
	 * @param batch
	 *            El SpriteBatch con el que se van a dibujar los actores
	 * @param cam
	 *            La cámara de la pantalla actual
	 * @param nombre
	 *            El nombre del escenario para referenciarlo en la clase D
	 */
	public EscenarioHelper(SpriteBatch batch, CamaraJuego cam, String nombre) {
		camara = cam;

		mh = new MapaHelper(nombre);

		ih = new InterfazHelper();

		escenario = new EscenarioBase(mh.getWidth(), mh.getHeight(), true,
				batch);

		escenario.cargarEventos(mh.eventosMapa(), camara);

		escenario.setCamera(camara);

		// Agregar las colisiones del piso
		escenario.piso = new Poligono(mh.getVerticesPlataforma(camara, "piso"));

		try {
			escenario.agregarActor("heroe", mh.getOrigenHeroe(camara));
		} catch (ActorNoDefinidoException e) {
			U.err(e);
		}
	}

	/**
	 * Manda a actuar a todos los {@link ObjetoActor}es del escenario y los
	 * dibuja. También calcula las colisiones de los {@link Actor}es y dibuja
	 * las líneas de colisión
	 * 
	 * @param delta
	 *            Proviene de {@link Screen#render()}
	 */
	public void actuarDibujar(float delta) {

		// Dibujar Background
		mh.renderFondo(camara);

		// Dibujar mapa
		mh.renderMapa(camara);

		// Colisión
		escenario.colisionActores();
		escenario.colisionPiso();
		escenario.colisionAtaques();

		// Eventos
		escenario.revisarEventos(camara, delta);
		escenario.destruirAtaques(camara);

		// Actuar de todos los actores del escenario
		escenario.act(delta);

		escenario.acomodarActores(mh.getWidth());
		escenario.acomodarHeroe(camara);

		// Debug líneas colisión
		escenario.dibujarLineasColision(camara);

		// Dibujar los actores del escenario
		escenario.draw();
		
		// Dibujar la interfaz
		ih.dibujar(getHeroe());

		// Mover la cámara
		moverCamara(delta);
	}

	/**
	 * Mueve la {@link CamaraJuego} conforme a la posición del {@link Heroe}
	 * 
	 * @param delta
	 *            El delta de {@link Screen#render(float)}
	 */
	private void moverCamara(float delta) {
		Heroe heroe = getHeroe();
		float factor = 1.7f;
		float destinoCamara;

		if (heroe.derecha()) {
			destinoCamara = heroe.x + heroe.width / 2 + camara.viewportWidth
					/ 6;
			if (camara.position.x < destinoCamara) {
				camara.setPosicion(
						(float) (camara.position.x + heroe.getVelocidad(delta)
								* factor), camara.position.y);
			}
			if (camara.position.x >= destinoCamara) {
				camara.setPosicion(destinoCamara, camara.position.y);
			}
		} else {
			destinoCamara = heroe.x + heroe.width / 2 - camara.viewportWidth
					/ 6;
			if (camara.position.x > destinoCamara) {
				camara.setPosicion(
						(float) (camara.position.x - heroe.getVelocidad(delta)
								* factor), camara.position.y);
			}
			if (camara.position.x <= destinoCamara) {
				camara.setPosicion(destinoCamara, camara.position.y);
			}
		}

		if (camara.getPosicionOrigen().x < 0)
			camara.setPosicionOrigen(0, camara.getPosicionOrigen().y);
		if (camara.getPosicionOrigen().x + camara.viewportWidth > mh.getWidth())
			camara.setPosicionOrigen(mh.getWidth() - camara.viewportWidth,
					camara.getPosicionOrigen().y);
	}

	/**
	 * Regresa al {@link Heroe} del escenario
	 * 
	 * @return El héroe
	 */
	public Heroe getHeroe() {
		return (Heroe) escenario.findActor("heroe");
	}

	/**
	 * Regresa el {@link Poligono} del piso del escenario
	 * 
	 * @return El piso
	 */
	public Poligono getPiso() {
		return escenario.piso;
	}

}
