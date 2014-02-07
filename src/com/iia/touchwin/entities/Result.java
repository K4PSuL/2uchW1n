package com.iia.touchwin.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class Result implements Serializable, Parcelable {

	private int id;
	private Date playDate;
	private int id_game;
	private int id_player1;
	private int id_player2;
	private int scoreP1;
	private int scoreP2;

	/**
	 * Default constructor.
	 */
	public Result() {

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
	 * @return the playDate
	 */
	public Date getPlayDate() {
		return this.playDate;
	}

	/**
	 * @param value
	 *            the playDate to set
	 */
	public void setPlayDate(final Date value) {
		this.playDate = value;
	}

	/**
	 * @return the id_game
	 */
	public int getId_game() {
		return this.id_game;
	}

	/**
	 * @param value
	 *            the id_game to set
	 */
	public void setId_game(final int value) {
		this.id_game = value;
	}

	/**
	 * @return the id_player1
	 */
	public int getId_player1() {
		return this.id_player1;
	}

	/**
	 * @param value
	 *            the id_player1 to set
	 */
	public void setId_player1(final int value) {
		this.id_player1 = value;
	}

	/**
	 * @return the id_player2
	 */
	public int getId_player2() {
		return this.id_player2;
	}

	/**
	 * @param value
	 *            the id_player2 to set
	 */
	public void setId_player2(final int value) {
		this.id_player2 = value;
	}

	/**
	 * @return the scoreP1
	 */
	public int getScoreP1() {
		return this.scoreP1;
	}

	/**
	 * @param value
	 *            the scoreP1 to set
	 */
	public void setScoreP1(final int value) {
		this.scoreP1 = value;
	}

	/**
	 * @return the scoreP2
	 */
	public int getScoreP2() {
		return this.scoreP2;
	}

	/**
	 * @param value
	 *            the scoreP2 to set
	 */
	public void setScoreP2(final int value) {
		this.scoreP2 = value;
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
