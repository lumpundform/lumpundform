package com.lumpundform.utilerias;

public class SpriteSheet {
	private String ruta;
	private int columnas;
	private int columnasOffset;
	private int renglones;
	private int renglonesOffset;
	private int cuadrosInicio;
	private int cuadrosFin;

	public SpriteSheet(String ruta, int columnas, int columnasOffset, int renglones, int renglonesOffset,
			int cuadrosInicio, int cuadrosFin) {
		setRuta(ruta);
		setColumnas(columnas);
		setColumnasOffset(columnasOffset);
		setRenglones(renglones);
		setRenglonesOffset(renglonesOffset);
		setCuadrosInicio(cuadrosInicio);
		setCuadrosFin(cuadrosFin);
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

	public int getCuadrosInicio() {
		return cuadrosInicio;
	}

	public void setCuadrosInicio(int cuadrosInicio) {
		this.cuadrosInicio = cuadrosInicio;
	}

	public int getCuadrosFin() {
		return cuadrosFin;
	}

	public void setCuadrosFin(int cuadrosFin) {
		this.cuadrosFin = cuadrosFin;
	}

}
