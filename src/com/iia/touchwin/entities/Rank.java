package com.iia.touchwin.entities;

import java.io.Serializable;

public class Rank implements Serializable {

	private int id;
	private String login;
	private int win;
	private int total;

	/**
	 * Default constructor.
	 */
	public Rank() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	

}
