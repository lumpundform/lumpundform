package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lumpundform.utilerias.Fuentes;

public class CuadroTexto {
	NinePatch np = new NinePatch(new Texture(Gdx.files.internal("ct.png")), 3, 3, 3, 3);
	SpriteBatch batch = new SpriteBatch();
	public Boolean dibujar = true;
	
	private float x = 10.0f;
	private float y = 10.0f;
	private float width = 325.0f;
	private float height = 125.0f;
	private float paddingX = 10.0f;
	private float paddingY = 10.0f;
	
	public String texto;
	
	public int index = 0;
	public String newstr = "";
	
	private static BitmapFont bmf = Fuentes.regular();

	public CuadroTexto(String texto) {
		this.texto = texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public void setPaddingX(float paddingX) {
		this.paddingX = paddingX;
	}
	
	public void setPaddingY(float paddingY) {
		this.paddingY = paddingY;
	}
	public void draw(String texto) {
		if(dibujar) {
			batch.begin();
			np.draw(batch, x, y, width, height);
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
		bmf.setScale(0.80f);
		bmf.drawWrapped(batch, msg, (x + paddingX), (height - paddingY), (width - paddingX*2));
		batch.end();
	}
}
