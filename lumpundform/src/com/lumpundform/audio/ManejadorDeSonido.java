package com.lumpundform.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;
import com.lumpundform.audio.SonidosDisponibles;
import com.lumpundform.utilerias.LRUCache;
import com.lumpundform.utilerias.LRUCache.CacheEntryRemovedListener;

public class ManejadorDeSonido implements
		CacheEntryRemovedListener<SonidosDisponibles, Sound>, Disposable {

	private float volumen = 1f;
	private boolean habilitado = true;

	private final LRUCache<SonidosDisponibles, Sound> cacheSonido;

	public ManejadorDeSonido() {
		cacheSonido = new LRUCache<SonidosDisponibles, Sound>(10);
		cacheSonido.setEntryRemovedListener(this);
	}

	public void play(SonidosDisponibles sonido) {
		if (!habilitado)
			return;
		Sound sonidoParaReproducir = cacheSonido.get(sonido);
		if (sonidoParaReproducir == null) {
			FileHandle archivoDeSonido = Gdx.files.internal(sonido
					.getNombreArchivo());
			sonidoParaReproducir = Gdx.audio.newSound(archivoDeSonido);
			cacheSonido.add(sonido, sonidoParaReproducir);
		}
		sonidoParaReproducir.play(volumen);
	}
	
	public void load(SonidosDisponibles sonido) {
		Sound sonidoParaReproducir = cacheSonido.get(sonido);
		if (sonidoParaReproducir == null) {
			FileHandle archivoDeSonido = Gdx.files.internal(sonido.getNombreArchivo());
			sonidoParaReproducir = Gdx.audio.newSound(archivoDeSonido);
			cacheSonido.add(sonido, sonidoParaReproducir);
		}
	}

	public void ajustarVolumen(float volumen) {
		if (volumen < 0 || volumen > 1f)
			throw new IllegalArgumentException(
					"El volumen debe de estar entre 1 y 0.");

		this.volumen = volumen;
	}

	public void habilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	@Override
	public void dispose() {
		for (Sound sonido : cacheSonido.retrieaveAll()) {
			sonido.stop();
			sonido.dispose();
		}
	}

	@Override
	public void notifyEntryRemoved(SonidosDisponibles key, Sound value) {
		value.dispose();
	}

}
