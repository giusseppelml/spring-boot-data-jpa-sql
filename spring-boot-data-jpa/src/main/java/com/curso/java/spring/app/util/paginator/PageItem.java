package com.curso.java.spring.app.util.paginator;

public class PageItem {
	
	private int numero;
	private boolean actual;
	
	public PageItem(int numero, boolean actual) {
		setNumero(numero);
		setActual(actual);
	}

	public int getNumero() {
		return numero;
	}

	private void setNumero(int numero) {
		this.numero = numero;
	}

	public boolean isActual() {
		return actual;
	}

	private void setActual(boolean actual) {
		this.actual = actual;
	}
	
	

}
