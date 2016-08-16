package com.android.example.uis;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

public class ActionTabWithSlippingFragmentActivity extends FragmentActivity 
				implements ActionBar.TabListener{
    
	private Fragment1 mFragment1 = new Fragment1();
	private Fragment2 mFragment2 = new Fragment2();
	private Fragment3 mFragment3 = new Fragment3();
	
	private static final int TAB_INDEX_COUNT = 3;
	
	private static final int TAB_INDEX_ONE = 0;
	private static final int TAB_INDEX_TWO = 1;
	private static final int TAB_INDEX_THREE = 2;

	private ImageView funnel_exit;

	private ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;
	private ActionBar actionBar;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();

    }

	private void initView(){

		funnel_exit = (ImageView) findViewById(R.id.android_funnel_exit);

		setUpActionBar();
		setUpViewPager();
		setUpTabs();
	}


    private void setUpActionBar() {
    	actionBar = getActionBar();
    	actionBar.setHomeButtonEnabled(false);
    	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    	actionBar.setDisplayShowTitleEnabled(false);
    	actionBar.setDisplayShowHomeEnabled(false);
    }
    
    private void setUpViewPager() {
    	mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
    	
    	mViewPager = (ViewPager)findViewById(R.id.pager);
    	mViewPager.setAdapter(mViewPagerAdapter);
    	mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
    		@Override
    		public void onPageSelected(int position) {
    			actionBar = getActionBar();
    			actionBar.setSelectedNavigationItem(position);
    		}
    		
    		@Override
    		public void onPageScrollStateChanged(int state) {
    			switch(state) {
    				case ViewPager.SCROLL_STATE_IDLE:
    					//TODO
    					break;
    				case ViewPager.SCROLL_STATE_DRAGGING:
    					//TODO
    					break;
    				case ViewPager.SCROLL_STATE_SETTLING:
    					//TODO
    					break;
    				default:
    					//TODO
    					break;
    			}
    		}
    	});
    }
    
    private void setUpTabs() {
    	actionBar = getActionBar();
    	for (int i = 0; i < mViewPagerAdapter.getCount(); ++i) {
    		actionBar.addTab(actionBar.newTab()
    				.setText(mViewPagerAdapter.getPageTitle(i))
    				.setTabListener(this));
    	}
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    }
    
    public class ViewPagerAdapter extends FragmentPagerAdapter {

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int position) {
			// TODO Auto-generated method stub
			switch (position) {
				case TAB_INDEX_ONE:
					return mFragment1;
				case TAB_INDEX_TWO:
					return mFragment2;
				case TAB_INDEX_THREE:
					return mFragment3;
			}
			throw new IllegalStateException("No fragment at position " + position);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return TAB_INDEX_COUNT;
		}
    	
		@Override
		public CharSequence getPageTitle(int position) {
			String tabLabel = null;
			switch (position) {
				case TAB_INDEX_ONE:
					tabLabel = getString(R.string.tab_1);
					break;
				case TAB_INDEX_TWO:
					tabLabel = getString(R.string.tab_2);
					break;
				case TAB_INDEX_THREE:
					tabLabel = getString(R.string.tab_3);
					break;
			}
			return tabLabel;
		}
    }

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}