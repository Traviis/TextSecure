package org.thoughtcrime.securesms;

import java.util.ArrayList;
import java.util.Map;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BlockListActivity extends Activity {
	
	private static final int MENU_BLOCK_BUTTON = 5556;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		//Intent intent = getIntent();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.block_list_contents);
		reload_items();
	}
	  @Override
	  public boolean onPrepareOptionsMenu(Menu menu)
	  {
	    super.onPrepareOptionsMenu(menu);
			
	    menu.clear();
	    menu.add(0, MENU_BLOCK_BUTTON, Menu.NONE, "Add Contact").setIcon(android.R.drawable.ic_input_add);
	    return true;
	  }

	  private void reload_items()
	  {
		  SharedPreferences prefs = getSharedPreferences("org.thoughtcrime.securesms.SecureSMS.BLOCKLIST",MODE_PRIVATE);
		  Map<String, String> vals = (Map<String, String>) prefs.getAll();
		  ArrayList<String> numbers = new ArrayList<String>();
	        for(Map.Entry<String, String> entry : vals.entrySet())
	        {
	        	numbers.add(entry.getValue());
	        }
		 ListView listview = (ListView) findViewById(R.id.block_list);
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,numbers);
		 listview.setAdapter(adapter);
		 
	  }
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    super.onOptionsItemSelected(item);
	    if (item.getItemId() == 5556)
	    {
	    	//TEMP
	    	//JUst add a number to the block list
	    	SharedPreferences prefs = getSharedPreferences("org.thoughtcrime.securesms.SecureSMS.BLOCKLIST",MODE_PRIVATE);
	        SharedPreferences.Editor pref_edit = prefs.edit();
	        pref_edit.putString("block1", "9999999999");
	        pref_edit.commit();
	        reload_items();
	    	
	    }
	    return true;
	  }
}
