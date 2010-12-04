/*
 * BuilderAdapter.java
 *
 * Copyright (C) 2010 Mario Sanchez Prada
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

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BuilderAdapter extends ArrayAdapter<Builder> {

    private Builder[] items;

    public BuilderAdapter(Context context, int textViewResourceId, Builder[] items) {
	super(context, textViewResourceId, items);
	this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	View v = convertView;

	if (v == null) {
	    LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
		    Context.LAYOUT_INFLATER_SERVICE);
	    v = vi.inflate(R.layout.builder_listitem, null);
	}

	Builder builder = items[position];
	if (builder!= null) {
	    TextView tv = (TextView) v.findViewById(R.id.builderitem);
	    if (tv != null) {
		tv.setText(builder.getName());
		int color;
		switch (builder.getBuildResult()) {
		case PASSED:
		    color = Color.GREEN;
		    break;
		case FAILED:
		    color = Color.RED;
		    break;
		case FAILED_AGAIN:
		    color = Color.rgb(255, 128, 0); // ORANGE
		    break;
		case RUNNING:
		    color = Color.YELLOW;
		    break;
		case NO_DATA:
		    color = Color.WHITE;
		    break;
		default:
		    color = Color.GRAY;
		}
		tv.setTextColor(color);
	    }
	}
	return v;
    }
}
