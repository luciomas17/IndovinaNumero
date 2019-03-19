package it.polito.tdp.numero.model;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class NumeroModel {
	private final int NMAX = 100;
	private final int TMAX = 8;
	private int segreto;
	private IntegerProperty tentativiFatti;
	private boolean inGioco;
	private Set<Integer> tentativi;
	
	public NumeroModel() {
		this.inGioco = false;
		this.tentativiFatti = new SimpleIntegerProperty();
		this.tentativi = new HashSet<Integer>();
	}
	
	/**
	 * Avvia nuova partita
	 */
	public void newGame() {
		this.segreto = (int)(Math.random() * NMAX) + 1;
		this.tentativiFatti.set(0);
		this.inGioco = true;
		this.tentativi = new HashSet<Integer>();
	}
	
	/**
	 * Metodo per effettuare un tentativo
	 * @param t il tentativo
	 * @return 1 se il tentativo è troppo alto, -1 se è troppo basso, 0 se l'utente ha indovinato
	 */
	public int tentativo(int t) {
		if(!inGioco) {
			throw new IllegalStateException("La partita è terminata");
		}
		
		if(!tentativoValido(t)) {
			throw new InvalidParameterException(String.format("Devi inserire un numero tra %d e %d", 1, NMAX));
		}
		
		this.tentativiFatti.set(this.tentativiFatti.get() + 1);
		this.tentativi.add(new Integer (t));
		
		if(this.tentativiFatti.get() == this.TMAX) {
			this.inGioco = false;
		}
		
		if(t == this.segreto) {
			this.inGioco = false;
			return 0;
		}
		if(t > this.segreto) {
			return 1;
		}
		return -1;
	}
	
	public boolean tentativoValido(int t) {
		if(t<1 || t>NMAX)
			return false;
		else if(this.tentativi.contains(new Integer (t)))
			return false;
		else
			return true;
	}

	public boolean isInGioco() {
		return inGioco;
	}

	public int getSegreto() {
		return segreto;
	}

	public int getTMAX() {
		return TMAX;
	}

	public IntegerProperty tentativiFattiProperty() {
		return this.tentativiFatti;
	}
	

	public int getTentativiFatti() {
		return this.tentativiFattiProperty().get();
	}
	

	public void setTentativiFatti(final int tentativiFatti) {
		this.tentativiFattiProperty().set(tentativiFatti);
	}
	

}
