package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lumpundform.utilerias.Fuentes;

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
	private float paddingX = 10.0f;
	private float paddingY = 5.0f;
	private float posicionRetratoX;

	public boolean terminado = false;
	public boolean continuar = false;


	public int index = 0;
	
	public String texto;
	public String newstr = "";

	public CuadroTexto(String posicion) {
		if(posicion == "der") {
			this.posicionX = Gdx.graphics.getWidth() - this.anchoCuadro - 10.0f;
			this.posicionRetratoX = (posicionX - paddingX);
		} else if (posicion == "izq") {
			this.posicionX = 10.0f;
			this.posicionRetratoX = (posicionX + (anchoCuadro - retrato.getWidth() - paddingX));
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

	public void draw() {
		batch.begin();
		np.draw(batch, posicionX, posicionY, anchoCuadro, altoCuadro);
		bmf.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		bmf.setScale(0.50f);
		bmf.drawWrapped(batch, texto, (posicionX + paddingX), (posicionY + altoCuadro - paddingY), (anchoCuadro - paddingX - retrato.getWidth()));
		batch.draw(retrato, posicionRetratoX, (posicionY + (altoCuadro - retrato.getHeight() - paddingY)), retrato.getWidth(), retrato.getHeight());
		batch.end();
	}
}