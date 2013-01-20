package com.lumpundform.escenario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Vector2;
import com.lumpundform.actores.Heroe;
import com.lumpundform.lumpundform.CamaraJuego;
import com.lumpundform.utilerias.D;
import com.lumpundform.utilerias.U;

/**
 * Clase que contiene funciones necesarias para cargar el mapa en el juego
 * 
 * @author Sergio
 * 
 */
class MapaHelper {
	TiledMap mapa;
	TileAtlas atlas;
	TileMapRenderer renderer;
	Texture fondo;
	SpriteBatch sb;

	/**
	 * Lee los archivos necesarios y crea las variables necesarias para el
	 * funcionamiento de la clase
	 * 
	 * @param nombre
	 */
	MapaHelper(String nombre) {
		DatosEscenario de = D.de(nombre);
		mapa = TiledLoader.createMap(Gdx.files.internal(de.getArchivoTmx()));
		atlas = new TileAtlas(mapa, Gdx.files.internal(de.getAtlas()));
		renderer = new TileMapRenderer(mapa, atlas, 16, 16);
		fondo = new Texture(Gdx.files.internal(de.getFondo()));
		sb = new SpriteBatch();
	}

	/**
	 * Dibuja el mapa
	 */
	void renderMapa(CamaraJuego camara) {
		renderer.render(camara);
	}

	/**
	 * Dibuja el fondo del mapa
	 * 
	 * @param camara
	 *            La {@link CamaraJuego} con la que se va a dibujar el fondo
	 */
	void renderFondo(Camera camara) {
		float fondo_x = (camara.viewportWidth / 2) - camara.position.x;
		sb.begin();
		sb.disableBlending();
		sb.draw(fondo, fondo_x * 0.15f, 0);
		sb.enableBlending();
		sb.end();
	}

	/**
	 * Regresa el ancho total del mapa
	 * 
	 * @return El ancho
	 */
	int getWidth() {
		return mapa.width * mapa.tileWidth;
	}

	/**
	 * Regresa el alto total del mapa
	 * 
	 * @return El alto
	 */
	int getHeight() {
		return mapa.height * mapa.tileHeight;
	}

	/**
	 * Regresa el punto de origen del {@link Heroe}
	 * 
	 * @param camara
	 *            La {@link CamaraJuego} a referenciar para las coordenadas del
	 *            punto
	 * @return El punto de origen
	 */
	Vector2 getOrigenHeroe() {
		TiledObject objeto = tiledObject("origenes", "heroe");
		return U.voltearCoordenadas(objeto.x, objeto.y);
	}

	TiledObjectGroup eventosMapa() {
		TiledObjectGroup eventos = tiledObjectGroup("eventos");
		return eventos;
	}

	/**
	 * Carga las colisiones del mapa del objeto referenciado por nombreObjeto
	 * 
	 * @param camara
	 *            La {@link CamaraJuego} a referenciar para las coordenadas del
	 *            punto
	 * @param nombreObjeto
	 *            El nombre del objeto del cuál se quieren saber sus vértices
	 * @return Un arreglo de todos los vertices del objeto
	 */
	Vector2[] getVerticesPlataforma(String nombreObjeto) {
		TiledObject objeto = tiledObject("plataformas", nombreObjeto);
		Vector2 puntoInicialObjeto = new Vector2(objeto.x, objeto.y);
		String[] puntos = objeto.polyline.split(" ");

		Vector2[] vectoresPuntos = new Vector2[puntos.length];
		for (int i = 0; i < puntos.length; i++) {
			String[] punto = puntos[i].split(",");

			vectoresPuntos[i] = U.voltearCoordenadas(Integer.parseInt(punto[0]),
					(int) puntoInicialObjeto.y + Integer.parseInt(punto[1]));
		}
		return vectoresPuntos;
	}

	/**
	 * Carga el {@link TiledObject} del grupo y objeto dados del archivo *.tmx
	 * 
	 * @param nombreGrupo
	 *            El nombre del grupo
	 * @param nombreObjeto
	 *            El nombre del objeto
	 * @return El objeto
	 */
	private TiledObject tiledObject(String nombreGrupo, String nombreObjeto) {
		TiledObjectGroup objetoGrupo = tiledObjectGroup(nombreGrupo);
		for (int i = 0; i < objetoGrupo.objects.size(); i++) {
			if (objetoGrupo.objects.get(i).name.equals(nombreObjeto)) {
				return objetoGrupo.objects.get(i);
			}
		}
		return null;
	}

	/**
	 * Carga el {@link TiledObjectGroup} del nombre dado del archivo *.tmx
	 * 
	 * @param nombre
	 *            El nombre del {@link TiledObjectGroup}
	 * @return El objectGroup
	 */
	TiledObjectGroup tiledObjectGroup(String nombre) {
		for (int i = 0; i < mapa.objectGroups.size(); i++) {
			if (mapa.objectGroups.get(i).name.equals(nombre)) {
				return mapa.objectGroups.get(i);
			}
		}
		return null;
	}

}
