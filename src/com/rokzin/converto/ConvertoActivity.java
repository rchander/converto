package com.rokzin.converto;

import com.rokzin.converto.core.SlideHolder;
import com.rokzin.converto.ui.MassView;
import com.rokzin.converto.ui.TemperatureView;
import com.rokzin.converto.utils.PreferenceSet;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ViewSwitcher;

public class ConvertoActivity extends Activity {

	private SlideHolder mSlideHolder;
	private ViewSwitcher viewSwitcher;
	private MassView mv;
	private TemperatureView tv;
	private ListView menu_items;
	private Menu menu;
	public static int APP_HEIGHT;
	public static int APP_WIDTH;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_converto);

		mSlideHolder = (SlideHolder) findViewById(R.id.slideHolder);

		initialize();

	}

	private void initialize() {
		Display display = getWindowManager().getDefaultDisplay();
		APP_WIDTH = display.getWidth();
		APP_HEIGHT = display.getHeight();

		menu_items = (ListView) findViewById(R.id.menu_list);
		viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher1);
		mv = new MassView(ConvertoActivity.this);
		tv = new TemperatureView(ConvertoActivity.this);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(ConvertoActivity.this, R.layout.menu_item, R.id.menu_item, PreferenceSet.getMenuItems());
		menu_items.setAdapter(adapter);
		menu_items.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int id, long id2) {
				String selected_item = (String) menu_items.getItemAtPosition(id);
				if (selected_item == PreferenceSet.TEMPERATURE) {
					setNewPage(tv);

				}
				if (selected_item == PreferenceSet.MASS) {
					setNewPage(mv);

				}
			}

			private void setNewPage(View v) {
				mSlideHolder.toggle();
				viewSwitcher.removeAllViews();
				viewSwitcher.addView(v);
				menu.getItem(0).setTitle(v.toString());

			}
		});

		viewSwitcher.addView(mv);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem item_type = menu.add("Type");
		MenuItem item_settings = menu.add("Settings");
		item_type.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		this.menu = menu;
		item_type.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				mSlideHolder.toggle();
				return true;
			}
		});
		return true;
	}
}
