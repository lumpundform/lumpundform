package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
 * @author Sergio
 *
 */
public class MapaHelper {
	TiledMap mapa;
	TileAtlas atlas;
	
	public MapaHelper(String nombre) {
		mapa = TiledLoader.createMap(Gdx.files.internal(D.s(nombre).get("archivo_tmx")));
		atlas = new TileAtlas(mapa, Gdx.files.internal(D.s(nombre).get("atlas")));
	}
	
	
	
	public TileMapRenderer cargarMapa() {
		return new TileMapRenderer(mapa, atlas, 16, 16);
	}
	
	
	
	public int getWidth() {
		return mapa.width * mapa.tileWidth;
	}
	
	public int getHeight() {
		return mapa.height * mapa.tileHeight;
	}
	
	
	
	public Vector2 origenHeroe(OrthographicCamera camara) {
		TiledObject objeto = tiledObject("origenes", "heroe");
		Vector3 origen = U.voltearCoordenadas(camara, objeto.x, objeto.y);
		return new Vector2(origen.x, origen.y);
	}
	
	public Vector2[] cargarColisiones(OrthographicCamera camara, String nombreObjeto) {
		TiledObject objeto = tiledObject("plataformas", nombreObjeto);
		Vector2 puntoInicialObjeto = new Vector2(objeto.x, objeto.y);
		String[] puntos = objeto.polyline.split(" ");
		
		Vector2[] vectoresPuntos = new Vector2[puntos.length];
		for (int i = 0; i < puntos.length; i++) {
			String[] punto = puntos[i].split(",");
			
			Vector3 p = U.voltearCoordenadas(camara, Integer.parseInt(punto[0]), (int) puntoInicialObjeto.y + Integer.parseInt(punto[1]));
			vectoresPuntos[i] = new Vector2(p.x, p.y);
		}
		return vectoresPuntos;
	}

	
	private TiledObject tiledObject(String nombreGrupo, String nombreObjeto) {
		TiledObjectGroup objetoGrupo = tiledObjectGroup(nombreGrupo);
		for (int i = 0; i < objetoGrupo.objects.size(); i++) {
			if (objetoGrupo.objects.get(i).name.equals(nombreObjeto)) {
				return objetoGrupo.objects.get(i);
			}
		}
		return null;
	}
	private TiledObjectGroup tiledObjectGroup(String nombre) {
		for (int i = 0; i < mapa.objectGroups.size(); i++) {
			if (mapa.objectGroups.get(i).name.equals(nombre)) {
				return mapa.objectGroups.get(i);
			}
		}
		return null;
	}

}
