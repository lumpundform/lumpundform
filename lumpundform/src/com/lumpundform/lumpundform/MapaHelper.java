package com.lumpundform.lumpundform;

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
import com.badlogic.gdx.math.Vector3;

/**
 * Clase que contiene funciones necesarias para cargar el mapa en el juego
 * 
 * @author Sergio
 * 
 */
public class MapaHelper {
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
	public MapaHelper(String nombre) {
		mapa = TiledLoader.createMap(Gdx.files.internal(D.s(nombre).get(
				"archivo_tmx")));
		atlas = new TileAtlas(mapa,
				Gdx.files.internal(D.s(nombre).get("atlas")));
		renderer = new TileMapRenderer(mapa, atlas, 16, 16);
		fondo = new Texture(Gdx.files.internal(D.s(nombre).get("fondo")));
		sb = new SpriteBatch();
	}

	/**
	 * Dibuja el mapa
	 */
	public void renderMapa(CamaraJuego camara) {
		renderer.render(camara);
	}

	/**
	 * Dibuja el fondo del mapa
	 * 
	 * @param camara
	 *            La {@link CamaraJuego} con la que se va a dibujar el fondo
	 */
	public void renderFondo(Camera camara) {
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
	public int getWidth() {
		return mapa.width * mapa.tileWidth;
	}

	/**
	 * Regresa el alto total del mapa
	 * 
	 * @return El alto
	 */
	public int getHeight() {
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
	public Vector2 getOrigenHeroe(CamaraJuego camara) {
		TiledObject objeto = tiledObject("origenes", "heroe");
		Vector3 origen = U.voltearCoordenadas(camara, objeto.x, objeto.y);
		return new Vector2(origen.x, origen.y);
	}
	
	public TiledObjectGroup eventosMapa() {
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
	public Vector2[] getVerticesPlataforma(CamaraJuego camara,
			String nombreObjeto) {
		TiledObject objeto = tiledObject("plataformas", nombreObjeto);
		Vector2 puntoInicialObjeto = new Vector2(objeto.x, objeto.y);
		String[] puntos = objeto.polyline.split(" ");

		Vector2[] vectoresPuntos = new Vector2[puntos.length];
		for (int i = 0; i < puntos.length; i++) {
			String[] punto = puntos[i].split(",");

			Vector3 p = U.voltearCoordenadas(camara,
					Integer.parseInt(punto[0]), (int) puntoInicialObjeto.y
							+ Integer.parseInt(punto[1]));
			vectoresPuntos[i] = new Vector2(p.x, p.y);
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
