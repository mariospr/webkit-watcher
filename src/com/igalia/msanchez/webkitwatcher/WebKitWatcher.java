/*
 * WebKitWatcher.java
 *
 * Copyright (C) 2010-2012 Mario Sanchez Prada
 * Authors: Mario Sanchez Prada <msanchez@igalia.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 2 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 */

package com.igalia.msanchez.webkitwatcher;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class WebKitWatcher extends ListActivity {

    public WebKitWatcher() {
	super();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        String[] options = this.getResources().getStringArray(R.array.main_view_options);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options));
    }
    
    protected void onListItemClick(ListView l, View v, int position, long id) {
	super.onListItemClick(l, v, position, id);
	Object o = this.getListAdapter().getItem(position);
	String name = o.toString();
	
	// Yeah, this is hackish.
	Intent intent = null;
	if (name.equalsIgnoreCase(this.getResources().getString(R.string.builders_name_apple_mac)))
	    intent = new Intent(WebKitWatcher.this, AppleMacListView.class);
	else if (name.equalsIgnoreCase(this.getResources().getString(R.string.builders_name_apple_windows)))
	    intent = new Intent(WebKitWatcher.this, AppleWindowsListView.class);
	else if (name.equalsIgnoreCase(this.getResources().getString(R.string.builders_name_chromium)))
	    intent = new Intent(WebKitWatcher.this, ChromiumListView.class);
	else if (name.equalsIgnoreCase(this.getResources().getString(R.string.builders_name_gtk)))
	    intent = new Intent(WebKitWatcher.this, GTKListView.class);
	else if (name.equalsIgnoreCase(this.getResources().getString(R.string.builders_name_qt)))
	    intent = new Intent(WebKitWatcher.this, QtListView.class);
	else if (name.equalsIgnoreCase(this.getResources().getString(R.string.builders_name_efl)))
	    intent = new Intent(WebKitWatcher.this, EFLListView.class);
	else if (name.equalsIgnoreCase(this.getResources().getString(R.string.builders_name_misc)))
	    intent = new Intent(WebKitWatcher.this, MiscellaneousListView.class);
	else if (name.equalsIgnoreCase(this.getResources().getString(R.string.builders_name_all)))
	    intent = new Intent(WebKitWatcher.this, AllBotsListView.class);
	
	if (intent != null)
	    startActivity(intent);
    }
}