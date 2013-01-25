package com.lumpundform.escenario;

/**
 * Clase que almacena los datos de un {@link EscenarioBase} leídos del archivo
 * de datos XML.
 * 
 * @author Sergio Valencia
 * 
 */
public class DatosEscenario {
	private String archivoTmx;
	private String atlas;
	private String fondo;

	public DatosEscenario(String archivoTmx, String atlas, String fondo) {
		setArchivoTmx(archivoTmx);
		setAtlas(atlas);
		setFondo(fondo);
	}

	public String getArchivoTmx() {
		return archivoTmx;
	}

	public void setArchivoTmx(String archivoTmx) {
		this.archivoTmx = archivoTmx;
	}

	public String getAtlas() {
		return atlas;
	}

	public void setAtlas(String atlas) {
		this.atlas = atlas;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

}
