package org.uusoftware.magnetometer;

import java.util.ArrayList;

import org.uusoftware.magnetometer.adapter.NavDrawerListAdapter;
import org.uusoftware.magnetometer.model.NavDrawerItem;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sbstrm.appirater.Appirater;

public class MainActivity extends AppCompatActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	Fragment fragment = null;
	FragmentManager fragmentManager;
	FragmentTransaction ft;

	boolean doubleBackToExitPressedOnce = false;

	String str1 = "https://www.facebook.com/uusoftware";
	String str2 = "http://twitter.com/uusoftware1";
	String str3 = "market://details?id=org.uusoftware.magnetometer";
	String str4 = "market://search?q=pub:U%26U+Software";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// AppRater
		Appirater.appLaunched(this);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name) {
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				supportInvalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				supportInvalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_about:
			fragmentManager = getSupportFragmentManager();
			ft = fragmentManager.beginTransaction();
			fragment = new AboutUsFragment();
			ft.replace(R.id.frame_container, fragment, "AboutUs").commit();
			setTitle("Hakkımızda");
			return true;
		case R.id.action_share:
			Intent sharingIntent = new Intent(
					android.content.Intent.ACTION_SEND);
			sharingIntent.setType("text/plain");
			String shareBody = "Magnetometer on Google Play: https://play.google.com/store/apps/details?id=org.uusoftware.magnetometer";
			sharingIntent
					.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			startActivity(Intent.createChooser(sharingIntent, "Share"));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_share).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	private void displayView(int position) {
		fragmentManager = getSupportFragmentManager();
		ft = fragmentManager.beginTransaction();

		switch (position) {
		case 0:
			fragment = new HomeFragment();
			ft.replace(R.id.frame_container, fragment, "Home").commit();
			setTitle(navMenuTitles[0]);
			mDrawerList.setItemChecked(0, true);
			mDrawerList.setSelection(0);
			mDrawerLayout.closeDrawer(mDrawerList);
			break;
		case 1:
			fragment = new AboutUsFragment();
			ft.replace(R.id.frame_container, fragment, "AboutUs").commit();
			setTitle(navMenuTitles[1]);
			mDrawerList.setItemChecked(1, true);
			mDrawerList.setSelection(1);
			mDrawerLayout.closeDrawer(mDrawerList);
			break;
		case 2:
			Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(str1));
			startActivity(intent1);
			break;
		case 3:
			Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(str2));
			startActivity(intent2);
			break;
		case 4:
			Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(str3));
			startActivity(intent3);
			break;
		case 5:
			Intent intent4 = new Intent(Intent.ACTION_VIEW, Uri.parse(str4));
			startActivity(intent4);
			break;
		default:
			break;
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		HomeFragment fragment0 = (HomeFragment) getSupportFragmentManager()
				.findFragmentByTag("Home");
		AboutUsFragment fragment1 = (AboutUsFragment) getSupportFragmentManager()
				.findFragmentByTag("AboutUs");

		// HomeFragment OnBackPressed
		if (fragment0 != null) {
			if (fragment0.isVisible()) {
				if (doubleBackToExitPressedOnce) {
					finish();
				}

				this.doubleBackToExitPressedOnce = true;
				Toast.makeText(this, getString(R.string.exit),
						Toast.LENGTH_SHORT).show();

				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						doubleBackToExitPressedOnce = false;
					}
				}, 2000);
			}
		}

		// AboutUsFragment OnBackPressed
		if (fragment1 != null) {
			if (fragment1.isVisible()) {
				fragmentManager = getSupportFragmentManager();
				ft = fragmentManager.beginTransaction();
				fragment = new HomeFragment();
				ft.replace(R.id.frame_container, fragment, "Home").commit();
				setTitle(navMenuTitles[0]);
				mDrawerList.setItemChecked(0, true);
				mDrawerList.setSelection(0);
			}
		}

		mDrawerLayout.closeDrawer(mDrawerList);

	}
}