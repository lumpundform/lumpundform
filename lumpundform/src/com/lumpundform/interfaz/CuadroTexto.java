package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lumpundform.utilerias.Fuentes;

public class CuadroTexto {
	NinePatch np = new NinePatch(new Texture(Gdx.files.internal("ct.png")), 3,
			3, 3, 3);
	
	SpriteBatch batch = new SpriteBatch();
	public Boolean dibujar = true;

	private float posicionX;
	private float posicionY = 400.0f;
	private float anchoCuadro = 300.0f;
	private float altoCuadro = 60.0f;
	private float paddingX = 10.0f;
	private float paddingY = 5.0f;
	private float posicionRetratoX;

	public boolean terminado = false;
	public String texto;

	public int index = 0;
	public String newstr = "";

	private static BitmapFont bmf = Fuentes.regular();
	private Texture retrato = new Texture(Gdx.files.internal("samus_portrait.png"));

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

	public void draw(String texto) {
		if (dibujar) {
			batch.begin();
			np.draw(batch, posicionX, posicionY, anchoCuadro, altoCuadro);
			batch.end();
			drawString(texto, batch);
		}
	}

	public Boolean drawCt() {
		draw(newstr);
		if (index < texto.length()) {
			newstr += texto.substring(index, ++index);
			return true;
		} else {
			return false;
		}

	}

	private void drawString(Object mensaje, SpriteBatch batch) {
		CharSequence msg = mensaje + "";
		batch.begin();
		bmf.setColor(1.0f, 0.2f, 0.2f, 1.0f);
		bmf.setScale(0.60f);
		bmf.drawWrapped(batch, msg, (posicionX + paddingX), (posicionY + altoCuadro - paddingY),
				(anchoCuadro - paddingX));
		batch.draw(retrato, posicionRetratoX, (posicionY + (altoCuadro - retrato.getHeight() - paddingY)), retrato.getWidth(), retrato.getHeight());
		batch.end();
	}
}