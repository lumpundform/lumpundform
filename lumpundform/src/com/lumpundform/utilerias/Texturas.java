/**
 * 
 */
package com.lumpundform.utilerias;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * Clase de ayuda para reusar los {@link Texture}s que ya hayan sido creados.
 * 
 * @author Sergio Valencia
 * 
 */
public class Texturas {
	private static Map<String, Texture> texturas = new HashMap<String, Texture>();

	public static Texture get(String ruta) {
		Texture textura = texturas.get(ruta);
		if (textura == null) {
			textura = new Texture(Gdx.files.internal(ruta));
			texturas.put(ruta, textura);
		}
		return textura;
	}

}
