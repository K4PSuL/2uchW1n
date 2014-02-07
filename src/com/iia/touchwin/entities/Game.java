package com.iia.touchwin.entities;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

public class Game implements Serializable, Parcelable {

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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub

	}

}
