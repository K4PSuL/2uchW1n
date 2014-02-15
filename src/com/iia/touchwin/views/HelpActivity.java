package com.iia.touchwin.views;

import java.util.List;
import java.util.Vector;

import com.iia.touchwin.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class HelpActivity extends FragmentActivity {
	
	private PagerAdapter mPagerAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		initialisePaging();
	}
	
	private void initialisePaging() {
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this, FragmentStep1.class.getName()));
		fragments.add(Fragment.instantiate(this, FragmentStep2.class.getName()));
		fragments.add(Fragment.instantiate(this, FragmentStep3.class.getName()));
		mPagerAdapter = new PagerAdapter(this.getSupportFragmentManager(), fragments);
		
		ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
		pager.setAdapter(mPagerAdapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
