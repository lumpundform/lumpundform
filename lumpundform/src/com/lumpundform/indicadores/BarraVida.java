package com.lumpundform.indicadores;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.lumpundform.actores.Personaje;

public class BarraVida extends Actor {
	private Personaje personaje;
	private NinePatch vida;
	private NinePatch fondo;
	private float anchoTotal = 100.0f;
	private float alto = 8.0f;

	public BarraVida(Personaje personaje) {
		this.personaje = personaje;
		
		vida = new NinePatch(new Texture(Gdx.files.internal("lifebar.png")), 1, 1, 1, 1);
		fondo = new NinePatch(new Texture(Gdx.files.internal("bar_background.png")), 1, 1, 1, 1);
		
		setHeight(alto);
		anchoTotal = personaje.getHitbox().getAncho();
		
		personaje.getStage().addActor(this);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		fondo.draw(batch, getX(), getY(), anchoTotal, alto);
		vida.draw(batch, getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void act(float delta) {
		float anchoVida = personaje.getVida() * anchoTotal / personaje.getVidaMax();

		setWidth(anchoVida);
		setX(personaje.getEsquina("sup-izq").x);
		setY(personaje.getEsquina("sup-izq").y + 5.0f);
	}
}
