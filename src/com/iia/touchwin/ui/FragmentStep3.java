package com.iia.touchwin.ui;

import com.iia.touchwin.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class FragmentStep3 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		//return super.onCreateView(inflater, container, savedInstanceState);
		return (RelativeLayout) inflater.inflate(R.layout.fragment_step3, container, false);
	}
	
}