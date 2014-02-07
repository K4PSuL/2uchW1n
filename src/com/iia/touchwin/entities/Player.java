package com.iia.touchwin.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Player implements Serializable, Parcelable {

	private int id;
	private String login;
	private String password;
	private Date dateCreate;
	private String avatar;
	private boolean enable;
	private Date birthdate;

	/**
	 * Default constructor.
	 */
	public Player() {

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
	 * @return the login
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * @param value
	 *            the login to set
	 */
	public void setLogin(final String value) {
		this.login = value;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param value
	 *            the password to set
	 */
	public void setPassword(final String value) {
		this.password = value;
	}

	/**
	 * @return the dateCreate
	 */
	public Date getDateCreate() {
		return this.dateCreate;
	}

	/**
	 * @param value
	 *            the dateCreate to set
	 */
	public void setDateCreate(final Date value) {
		this.dateCreate = value;
	}

	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return this.avatar;
	}

	/**
	 * @param value
	 *            the avatar to set
	 */
	public void setAvatar(final String value) {
		this.avatar = value;
	}

	/**
	 * @return the enable
	 */
	public boolean isEnable() {
		return this.enable;
	}

	/**
	 * @param value
	 *            the enable to set
	 */
	public void setEnable(final boolean value) {
		this.enable = value;
	}

	/**
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return this.birthdate;
	}

	/**
	 * @param value
	 *            the birthdate to set
	 */
	public void setBirthdate(final Date value) {
		this.birthdate = value;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}

}
