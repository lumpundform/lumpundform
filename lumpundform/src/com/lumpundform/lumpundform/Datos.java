package com.lumpundform.lumpundform;

import java.util.HashMap;
import java.util.Map;

/**
 * Contiene los nombres de los archivos de sprite_sheet de los objetos, asi como
 * las columnas y renglones que usan cada una.
 * 
 * Para sacar el sprite sheet, se usa la funcion 's':
 * 		Datos.s("heroe").get("sprite_sheet")
 * Para sacar las columnas de alguna animacion se usa la funcion 'i':
 * 		Datos.i("heroe").get("columnas_" + nombreAnimacion)
 * Para sacar los renglones de alguna animacion se usa la funcion 'i':
 * 		Datos.i("heroe").get("renglones_" + nombreAnimacion)
 * @author Sergio
 *
 */
public class Datos {
	/**
	 * Regresa un Mapa de datos del objeto del nombre dado con todos los datos
	 * tipo String que haya
	 * 
	 * @param nombre El nombre del objeto del cual sacar el Mapa
	 * @return Un Map con todos los datos tipo String del objeto
	 */
	@SuppressWarnings("unchecked")
	static public Map<String, String> s(String nombre) {
		return datosString().get(nombre);
	}
	/**
	 * Regresa un Mapa de datos del objeto del nombre dado con todos los datos
	 * tipo Integer que haya
	 * 
	 * @param nombre El nombre del objeto del cual sacar el Mapa
	 * @return Un Map con todos los datos tipo Integer del objeto
	 */
	@SuppressWarnings("unchecked")
	static public Map<String, Integer> i(String nombre) {
		return datosInteger().get(nombre);
	}
	
	@SuppressWarnings("rawtypes")
	static private Map<String, Map> datosString() {
		Map<String, String> datosHeroe = new HashMap<String, String>();
		datosHeroe.put("sprite_sheet", "heroe_sprite_sheet.png");
		
		Map<String, Map> datos = new HashMap<String, Map>();
		datos.put("heroe", datosHeroe);
		
		return datos;
	}
	
	@SuppressWarnings("rawtypes")
	static private Map<String, Map> datosInteger() {
		Map<String, Integer> datosHeroe = new HashMap<String, Integer>();
		datosHeroe.put("columnas_detenido", 1);
		datosHeroe.put("renglones_detenido", 1);
		
		Map<String, Map> datos = new HashMap<String, Map>();
		datos.put("heroe", datosHeroe);
		
		return datos;
	}
}
