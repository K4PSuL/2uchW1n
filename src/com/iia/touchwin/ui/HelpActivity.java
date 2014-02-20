package com.iia.touchwin.ui;

import java.util.List;
import java.util.Vector;

import com.iia.touchwin.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class HelpActivity extends FragmentActivity {

	private PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		initialisePaging();
	}

	private void initialisePaging() {
		List<Fragment> aFragments = new Vector<Fragment>();
		aFragments
				.add(Fragment.instantiate(this, FragmentStep1.class.getName()));
		aFragments
				.add(Fragment.instantiate(this, FragmentStep2.class.getName()));
		aFragments
				.add(Fragment.instantiate(this, FragmentStep3.class.getName()));
		mPagerAdapter = new PagerAdapter(this.getSupportFragmentManager(),
				aFragments);

		ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
		pager.setAdapter(mPagerAdapter);
	}
}
