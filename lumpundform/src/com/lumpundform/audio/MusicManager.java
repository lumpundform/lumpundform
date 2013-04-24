package com.lumpundform.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

public class MusicManager implements Disposable {

	public enum MusicaDisponible {
		CASTLEVANIA("musica/castlevania.mp3");
		
		private final String nombreArchivo;
		
		private MusicaDisponible(String nombreArchivo) {
			this.nombreArchivo = nombreArchivo;
		}
		
		public String getNombreArchivo() {
			return nombreArchivo;
		}
	}
	
	private Music musicaEnCurso;
	private float volumen = 1f;
	private boolean habilitado = true;
	
	public MusicManager(){
		
	}
	
	public void play(MusicaDisponible musica) {
		if (!habilitado) return;
		
		stop();
		
		FileHandle archivoMusica = Gdx.files.internal(musica.getNombreArchivo());
		musicaEnCurso = Gdx.audio.newMusic(archivoMusica);
		musicaEnCurso.setVolume(volumen);
		musicaEnCurso.setLooping(true);
		musicaEnCurso.play();
	}
	
	public void stop() {
		if (musicaEnCurso != null) {
			musicaEnCurso.stop();
			musicaEnCurso.dispose();
		}
	}
	
	public void acomodarVolumen(float volumen) {
		this.volumen = volumen;
		
		if(musicaEnCurso != null)
			musicaEnCurso.setVolume(volumen);
	}
	
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
		
		if (!habilitado) 
			stop();
	}
	
	@Override
	public void dispose() {
		stop();		
	}
	
}
