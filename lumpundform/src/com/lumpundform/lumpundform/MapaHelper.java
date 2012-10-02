package com.lumpundform.lumpundform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

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
	
	public String propiedad(String nombre) {
		return mapa.objectGroups.get(0).objects.get(0).properties.get(nombre);
	}
	
	public int propiedadInt(String nombre) {
		return Integer.parseInt(propiedad(nombre));
	}
	
	public Vector2 origenHeroe(OrthographicCamera camara) {
		Vector3 origen = U.voltearCoordenadas(camara, propiedadInt("origenX"), propiedadInt("origenY"));
		return new Vector2(origen.x, origen.y);
	}
}
