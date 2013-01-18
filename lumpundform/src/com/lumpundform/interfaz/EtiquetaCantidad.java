package com.lumpundform.interfaz;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.lumpundform.utilerias.Fuentes;

public class EtiquetaCantidad extends Label {

	public EtiquetaCantidad(CharSequence text, Vector2 posicion) {
		this(text, posicion, Fuentes.regular().getColor());
	}

	public EtiquetaCantidad(CharSequence text, Vector2 posicion, Color color) {
		super(text, new LabelStyle(Fuentes.regular(), color));

		getStyle().font.setScale(0.75f);

		setX(posicion.x);
		setY(posicion.y);

		float duracion = 0.5f;
		MoveByAction mover = Actions.moveBy(0.0f, 20.0f, duracion);
		AlphaAction desaparecer = Actions.fadeOut(duracion);
		RemoveActorAction quitar = Actions.removeActor();
		ParallelAction moverDesaparecer = Actions.parallel(mover, desaparecer);
		addAction(Actions.sequence(moverDesaparecer, quitar));
	}

}
