package com.lumpundform.interfaz;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class ElementoInterfaz extends Button {

	protected boolean fadeIn = false;
	protected boolean fadeOut = false;

	public ElementoInterfaz(NinePatchDrawable ninePatchDrawable) {
		super(new NinePatchDrawable(ninePatchDrawable));
	}

	public void setFadeIn() {
		fadeIn = true;
		fadeOut = false;
	}

	public void setFadeOut() {
		fadeOut = true;
		fadeIn = false;
	}

}
