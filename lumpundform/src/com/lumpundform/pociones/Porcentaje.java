package com.lumpundform.pociones;

public class Porcentaje {
	private float valor;
	private float base;
	private float max;
	private float aumento;

	public Porcentaje() {
		setBase(0.05f);
		reset();
		setMax(0.35f);
		setAumento(0.04f);
	}

	public void reset() {
		setValor(getBase());
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}

	public float getBase() {
		return base;
	}

	public void setBase(float base) {
		this.base = base;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getAumento() {
		return aumento;
	}

	public void setAumento(float aumento) {
		this.aumento = aumento;
	}

	public void aumentar() {
		setValor(getValor() + getAumento());
		if (getValor() > getMax()) {
			setValor(getMax());
		}
	}
}
