package com.iia.touchwin.entities;

import java.io.Serializable;

public class Game implements Serializable {

	private int id;
	private String libelle;

	/**
	 * Default constructor.
	 */
	public Game() {

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @param value
	 *            the id to set
	 */
	public void setId(final int value) {
		this.id = value;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return this.libelle;
	}

	/**
	 * @param value
	 *            the libelle to set
	 */
	public void setLibelle(final String value) {
		this.libelle = value;
	}

}
