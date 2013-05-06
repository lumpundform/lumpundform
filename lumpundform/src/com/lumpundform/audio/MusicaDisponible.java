package com.lumpundform.audio;

public enum MusicaDisponible {
	CASTLEVANIA("musica/castlevania.mp3"), DT("musica/dt.mp3");

	private final String nombreArchivo;

	private MusicaDisponible(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}
}