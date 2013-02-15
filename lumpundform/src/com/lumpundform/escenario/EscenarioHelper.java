package com.lumpundform.escenario;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lumpundform.actores.Heroe;
import com.lumpundform.actores.ObjetoActor;
import com.lumpundform.colision.Poligono;
import com.lumpundform.eventos.Evento;
import com.lumpundform.excepciones.EscenarioSinHeroeException;
import com.lumpundform.interfaz.InterfazHelper;
import com.lumpundform.lumpundform.CamaraJuego;
import com.lumpundform.utilerias.D;

/**
 * Clase que ayuda con las funciones de los escenarios, como cargar el mapa, los
 * actores, las líneas de colisión y hace que actúen y se dibujen los actores
 * 
 * @author Sergio Valencia & Luis Gutiérrez
 * 
 */
public class EscenarioHelper {
	private CamaraJuego camara;
	private MapaHelper mh;
	private InterfazHelper interfazHelper;
	private EscenarioBase escenario;
	private boolean dibujarColision;

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

		interfazHelper = new InterfazHelper(escenario);

		escenario.cargarEscenas("1");
		escenario.cargarEventos(mh.eventosMapa());

		escenario.setCamera(camara);

		// Agregar las colisiones del piso
		escenario.setPiso(new Poligono(mh.getVerticesPlataforma("piso")));

		escenario.agregarActor("heroe", mh.getOrigenHeroe());

		interfazHelper.agregarElementos();
	}

	/**
	 * Manda a actuar a todos los {@link ObjetoActor}es del escenario y los
	 * dibuja. También calcula las colisiones de los {@link Actor}es y dibuja
	 * las líneas de colisión
	 * 
	 * @param delta
	 *            Proviene de {@link Screen#render(float)}
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
		escenario.revisarEventos();
		// escenario.revisarEscena();
		escenario.destruirAtaques();

		escenario.acomodarActores(mh.getWidth());
		escenario.acomodarHeroe();

		// Debug líneas colisión
		if (isDibujarColision()) {
			escenario.dibujarLineasColision(camara);
		}

		// Dibujar los actores del escenario
		escenario.draw();

		// Mover la cámara
		moverCamara();
	}

	/**
	 * Mueve la {@link CamaraJuego} conforme a la posición del {@link Heroe}
	 */
	private void moverCamara() {
		try {
			Heroe heroe = getHeroe();
			float factor = 1.7f;
			float destinoCamara;

			if (heroe.derecha()) {
				destinoCamara = heroe.getX() + heroe.getWidth() / 2 + camara.viewportWidth / 6;
				if (camara.position.x < destinoCamara) {
					camara.setPosicion((float) (camara.position.x + heroe.velDelta() * factor), camara.position.y);
				}
				if (camara.position.x >= destinoCamara) {
					camara.setPosicion(destinoCamara, camara.position.y);
				}
			} else {
				destinoCamara = heroe.getX() + heroe.getWidth() / 2 - camara.viewportWidth / 6;
				if (camara.position.x > destinoCamara) {
					camara.setPosicion((float) (camara.position.x - heroe.velDelta() * factor), camara.position.y);
				}
				if (camara.position.x <= destinoCamara) {
					camara.setPosicion(destinoCamara, camara.position.y);
				}
			}

			if (camara.getPosicionOrigen().x < 0)
				camara.setPosicionOrigen(0, camara.getPosicionOrigen().y);
			if (camara.getPosicionOrigen().x + camara.viewportWidth > mh.getWidth())
				camara.setPosicionOrigen(mh.getWidth() - camara.viewportWidth, camara.getPosicionOrigen().y);
		} catch (EscenarioSinHeroeException e) {
		}
	}

	/**
	 * Regresa al {@link Heroe} del escenario.
	 * 
	 * @return El héroe.
	 * @throws EscenarioSinHeroeException
	 *             No hay {@link Heroe} en el {@link EscenarioBase}.
	 */
	public Heroe getHeroe() throws EscenarioSinHeroeException {
		return escenario.getHeroe();
	}

	/**
	 * Avanza al siguiente cuadro de texto de la conversación actual.
	 */
	public void siguienteCuadroTexto() {
		Evento evento = conversacionEnCurso();
		if (evento != null) {
			escenario.continuarConversacionActual(evento.getNombre());
		}
	}

	/**
	 * Busca la conversación que se está llevando a cabo.
	 * 
	 * @return La conversación en forma de {@link Evento} o null si no hay.
	 */
	private Evento conversacionEnCurso() {
		for (int i = 0; i < escenario.getEventos().size; i++) {
			Evento evento = escenario.getEventos().get(i);
			if (evento.esEscenaActivada()) {
				return evento;
			}
		}
		return null;
	}

	public EscenarioBase getEscenario() {
		return escenario;
	}

	public InterfazHelper getInterfazHelper() {
		return interfazHelper;
	}

	public void setInterfazHelper(InterfazHelper interfazHelper) {
		this.interfazHelper = interfazHelper;
	}

	public boolean isDibujarColision() {
		return dibujarColision;
	}

	public void setDibujarColision(boolean dibujarColision) {
		this.dibujarColision = dibujarColision;
	}

	public boolean getInterfazBloqueada() {
		return escenario.getInterfazBloqueada();
	}

	public void setInterfazBloqueada(boolean estado) {
		this.escenario.setInterfazBloqueada(estado);
	}
}