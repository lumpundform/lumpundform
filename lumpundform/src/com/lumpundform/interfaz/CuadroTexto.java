package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lumpundform.utilerias.Fuentes;
import com.lumpundform.utilerias.U;

public class CuadroTexto {
	private NinePatch np = new NinePatch(new Texture(Gdx.files.internal("texturas/ct.png")), 3,
			3, 3, 3);
	
	private static BitmapFont bmf = Fuentes.regular();

	private Texture retrato = new Texture(Gdx.files.internal("personajes/samus_portrait.png"));
	
	private SpriteBatch batch = new SpriteBatch();
	
	public Boolean dibujar = true;

	private float posicionX;
	private float posicionY = 400.0f;
	private float anchoCuadro = 300.0f;
	private float altoCuadro = 60.0f;
	private float paddingX = 7.0f;
	private float paddingY = 5.0f;
	private float posicionTextoX;
	private float posicionRetratoX;

	private boolean terminado = false;
	private boolean continuar = false;

	private String texto;
	private String textoNuevo = "";
	

	public CuadroTexto(String posicion) {
		if(posicion.equals("der")) {
			this.posicionX = Gdx.graphics.getWidth() - this.anchoCuadro - 10.0f;
			this.posicionRetratoX = (posicionX + paddingX);
			this.posicionTextoX = posicionX + (paddingX * 2) + retrato.getWidth(); 
			
		} else {
			this.posicionX = 10.0f;
			this.posicionRetratoX = (posicionX + (anchoCuadro - retrato.getWidth() - paddingX));
			this.posicionTextoX = posicionX + paddingX;
		}
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public void setX(float x) {
		this.posicionX = x;
	}

	public void setY(float y) {
		this.posicionY = y;
	}

	public void setWidth(float width) {
		this.anchoCuadro = width;
	}

	public void setHeight(float height) {
		this.altoCuadro = height;
	}

	public void setPaddingX(float paddingX) {
		this.paddingX = paddingX;
	}

	public void setPaddingY(float paddingY) {
		this.paddingY = paddingY;
	}
	
	private String iterarTexto() {
		if (textoNuevo.length() < texto.length() && !terminado) {
			textoNuevo += texto.substring(textoNuevo.length(), textoNuevo.length() + 1);
		} else if (!terminado && textoNuevo.length() >= texto.length() && !continuar) {
			terminado = true;
			U.l("length", textoNuevo.length());
		} else if (terminado && continuar) {
			textoNuevo = "";
			continuar = false;
			terminado = false;
			U.l("length terminado", textoNuevo.length());
		}
		return textoNuevo;
	}

	public void draw() {
		batch.begin();
		np.draw(batch, posicionX, posicionY, anchoCuadro, altoCuadro);
		bmf.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		bmf.setScale(0.50f);
		bmf.drawWrapped(batch, iterarTexto(), posicionTextoX, (posicionY + altoCuadro - paddingY), (anchoCuadro - (paddingX * 2) - retrato.getWidth()));
		batch.draw(retrato, posicionRetratoX, (posicionY + (altoCuadro - retrato.getHeight() - paddingY)), retrato.getWidth(), retrato.getHeight());
		batch.end();
	}
	
	public boolean terminado() {
		return terminado;
	}
	
	public void continuar() {
		continuar = true;
	}

	public void completar() {
		textoNuevo = texto;
	}
}