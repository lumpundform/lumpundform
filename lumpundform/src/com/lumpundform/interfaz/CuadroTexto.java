package com.lumpundform.interfaz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lumpundform.utilerias.U;

public class CuadroTexto {
	NinePatch np = new NinePatch(new Texture(Gdx.files.internal("ct.png")), 3, 3, 3, 3);
	SpriteBatch batch;

	public CuadroTexto(SpriteBatch batch) {
		this.batch = batch;
	}
	
	public void draw() {
		this.batch.begin();
		np.draw(batch, 10.0f, 10.0f, 150.0f, 100.0f);
		this.batch.end();
		// U.ds("Mensaje\notroMensaje\ttabulacion.", 500.0f, 100.0f);
	}
	
}
