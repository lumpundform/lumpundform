package com.lumpundform.utilerias;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class SpriteSheet {
	private Texture textura;
	private String ruta;
	private int columnas;
	private int columnasOffset;
	private int renglones;
	private int renglonesOffset;

	public SpriteSheet(String ruta, int columnas, int columnasOffset, int renglones, int renglonesOffset) {
		setRuta(ruta);
		setColumnas(columnas);
		setColumnasOffset(columnasOffset);
		setRenglones(renglones);
		setRenglonesOffset(renglonesOffset);
	}

	public Texture getTextura() {
		if (textura == null) {
			textura = new Texture(Gdx.files.internal(ruta));
		}
		return textura;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public int getColumnas() {
		return columnas;
	}

	public void setColumnas(int columnas) {
		this.columnas = columnas;
	}

	public int getColumnasOffset() {
		return columnasOffset;
	}

	public void setColumnasOffset(int columnasOffset) {
		this.columnasOffset = columnasOffset;
	}

	public int getRenglones() {
		return renglones;
	}

	public void setRenglones(int renglones) {
		this.renglones = renglones;
	}

	public int getRenglonesOffset() {
		return renglonesOffset;
	}

	public void setRenglonesOffset(int renglonesOffset) {
		this.renglonesOffset = renglonesOffset;
	}

}