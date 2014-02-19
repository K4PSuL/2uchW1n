package com.iia.touchwin.app;

import java.util.ArrayList;

import com.iia.touchwin.entities.Rank;

import android.app.Application;

public class TouchWin extends Application {

	  private ArrayList<Rank> aRanks;

	  public ArrayList<Rank> getRanks(){
	    return aRanks;
	  }
	  public void setRanks(ArrayList<Rank> aRanks){
		  this.aRanks = aRanks;
	  }
	  
}