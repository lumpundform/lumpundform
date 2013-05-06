package com.lumpundform.audio;

public enum SonidosDisponibles {
		ATAQUE("sonido/open_menu.mp3");
		
		private final String nombreArchivo;
		
		private SonidosDisponibles(String nombreArchivo) {
			this.nombreArchivo = nombreArchivo;
		}
		
		public String getNombreArchivo() {
			return nombreArchivo;
		}
}
