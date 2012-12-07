package com.lumpundform.lumpundform.client;

import com.lumpundform.lumpundform.Lumpundform;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.utils.Clipboard;

public class GwtLauncher extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(480, 320);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new Lumpundform();
	}

	@Override
	public Net getNet() {
		return null;
	}

	@Override
	public Clipboard getClipboard() {
		return null;
	}
}