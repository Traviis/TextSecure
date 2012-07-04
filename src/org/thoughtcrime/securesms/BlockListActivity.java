package org.thoughtcrime.securesms;

import java.util.ArrayList;
import java.util.Map;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
		final ListView lv = (ListView)findViewById(R.id.block_list);
		lv.setLongClickable(true);
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Object li = lv.getItemAtPosition(arg2);
				String remove = li.toString(); //Delete this
				SharedPreferences prefs = getSharedPreferences("org.thoughtcrime.securesms.SecureSMS.BLOCKLIST",MODE_PRIVATE);
				SharedPreferences.Editor pref_edit = prefs.edit();
				pref_edit.remove(remove);
				pref_edit.commit();
				Log.i("SecureSMS","Removed Block from address:" + remove);
				reload_items();
				return true;
			}
		});
		
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
	  public boolean onOptionsItemSelected(MenuItem item) 
	  {
	    super.onOptionsItemSelected(item);
	    if (item.getItemId() == MENU_BLOCK_BUTTON)
	    {
	    	//Add an item to the block list
	    	SharedPreferences prefs = getSharedPreferences("org.thoughtcrime.securesms.SecureSMS.BLOCKLIST",MODE_PRIVATE);
	    	
	        SharedPreferences.Editor pref_edit = prefs.edit();
	        
	        //EditText etext = (EditText)findViewById(R.id.add_to_list_text);
	        //String new_item = etext.getText().toString();
	        //pref_edit.putString(new_item,new_item);
	        //pref_edit.commit();
	        showDialog(0);
	        reload_items();
	        
	        
	    	
	    }
	    return true;
	  }

	  @Override
	    protected Dialog onCreateDialog(int id)
	    {
	    	AlertDialog.Builder abuilder = new AlertDialog.Builder(this);
	    	
	        abuilder.setTitle("Add number to block:");
	    	LayoutInflater inflater = LayoutInflater.from(this);
	    	final View view_layout = inflater.inflate(R.layout.alert_dialog_text, null);
	    	abuilder.setView(view_layout);
	    	
	    	abuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					SharedPreferences prefs = getSharedPreferences("org.thoughtcrime.securesms.SecureSMS.BLOCKLIST",MODE_PRIVATE);
			        SharedPreferences.Editor pref_edit = prefs.edit();
			        EditText etext = (EditText)view_layout.findViewById(R.id.ALERT_DIALOG_EDIT_TEXT);
			        String tdata = etext.getText().toString();
			        pref_edit.putString(tdata,tdata);
			        pref_edit.commit();
			        reload_items();
			        etext.setText("");
			        
			        
					
				}
			});
	    	abuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					reload_items();
					
				}
			});
	    	
	    	
	    	return abuilder.create();
	    }

}
