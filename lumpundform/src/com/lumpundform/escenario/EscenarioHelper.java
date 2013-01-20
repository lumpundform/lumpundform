package com.lumpundform.escenario;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lumpundform.actores.Heroe;
import com.lumpundform.actores.ObjetoActor;
import com.lumpundform.colision.Poligono;
import com.lumpundform.eventos.Evento;
import com.lumpundform.excepciones.ActorNoDefinidoException;
import com.lumpundform.interfaz.InterfazHelper;
import com.lumpundform.lumpundform.CamaraJuego;
import com.lumpundform.utilerias.D;
import com.lumpundform.utilerias.U;

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
	private EscenarioBase escenario;

	/**
	 * Inicializa el escenario, el mapa, las colisiones y el {@link Heroe} para
	 * el escenario del nombre dado
	 * 
	 * @param batch
	 *            El {@link SpriteBatch} con el que se van a dibujar los actores
	 * @param cam
	 *            La {@link CamaraJuego} de la pantalla actual
	 * @param nombre
	 *            El nombre del escenario para referenciarlo en {@link D}
	 */
	public EscenarioHelper(SpriteBatch batch, CamaraJuego cam, String nombre) {
		camara = cam;

		mh = new MapaHelper(nombre);

		escenario = new EscenarioBase(mh.getWidth(), mh.getHeight(), true, batch);

		InterfazHelper ih = new InterfazHelper(escenario);

		escenario.cargarEscenas("1");
		escenario.cargarEventos(mh.eventosMapa());

		escenario.setCamera(camara);

		// Agregar las colisiones del piso
		escenario.setPiso(new Poligono(mh.getVerticesPlataforma("piso")));

		escenario.agregarActor("heroe", mh.getOrigenHeroe());

		ih.agregarElementos();
		ih.agregarHabilidades(getHeroe());
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
		escenario.colisionAtaques();
		escenario.colisionPociones();
		escenario.colisionPiso();

		// Actuar de todos los actores del escenario
		escenario.act(delta);

		// Eventos
		escenario.revisarEventos(camara, delta);
		// escenario.revisarEscena();
		escenario.destruirAtaques(camara);

		escenario.acomodarActores(mh.getWidth());
		escenario.acomodarHeroe(camara);

		// Debug líneas colisión
		escenario.dibujarLineasColision(camara);

		// Dibujar los actores del escenario
		escenario.draw();

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
			destinoCamara = heroe.getX() + heroe.getWidth() / 2 + camara.viewportWidth / 6;
			if (camara.position.x < destinoCamara) {
				camara.setPosicion((float) (camara.position.x + heroe.getVelocidad(delta) * factor), camara.position.y);
			}
			if (camara.position.x >= destinoCamara) {
				camara.setPosicion(destinoCamara, camara.position.y);
			}
		} else {
			destinoCamara = heroe.getX() + heroe.getWidth() / 2 - camara.viewportWidth / 6;
			if (camara.position.x > destinoCamara) {
				camara.setPosicion((float) (camara.position.x - heroe.getVelocidad(delta) * factor), camara.position.y);
			}
			if (camara.position.x <= destinoCamara) {
				camara.setPosicion(destinoCamara, camara.position.y);
			}
		}

		if (camara.getPosicionOrigen().x < 0)
			camara.setPosicionOrigen(0, camara.getPosicionOrigen().y);
		if (camara.getPosicionOrigen().x + camara.viewportWidth > mh.getWidth())
			camara.setPosicionOrigen(mh.getWidth() - camara.viewportWidth, camara.getPosicionOrigen().y);
	}

	/**
	 * Regresa al {@link Heroe} del escenario
	 * 
	 * @return El héroe
	 */
	public Heroe getHeroe() {
		return (Heroe) escenario.getHeroe();
	}

	public EscenarioBase getEscenario() {
		return escenario;
	}

	public boolean hayEventoActivado() {
		for(int i = 0; i < escenario.getEventos().size; i++) {
			Evento evento = escenario.getEventos().get(i);
			if(evento.getActivado() && !evento.getTerminado()) {
				return true;
			}
		}
		return false;
	}

	public Evento getEventoActivado() {
		for(int i = 0; i < escenario.getEventos().size; i++) {
			Evento evento = escenario.getEventos().get(i);
			if(evento.getActivado() && !evento.getTerminado()) {
				return evento;
			}
		}
		return null;
	}

	public boolean hayEscenaActivada() {
		for(int i = 0; i < escenario.getEventos().size; i++) {
			Evento evento = escenario.getEventos().get(i);
			if(evento.getActivado() && !evento.getTerminado() && evento.getTipoEvento().equals("escena")) {
				return true;
			}
		}
		return false;
	}

	public Evento getEscenaActivada() {
		for(int i = 0; i < escenario.getEventos().size; i++) {
			Evento evento = escenario.getEventos().get(i);
			if(evento.getActivado() && !evento.getTerminado() && evento.getTipoEvento().equals("escena")) {
				return evento;
			}
		}
		return null;
	}
}