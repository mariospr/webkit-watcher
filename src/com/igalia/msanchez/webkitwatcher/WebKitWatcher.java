/*
 * WebKitWatcher.java
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

public class WebKitWatcher extends ListActivity {

        private BuildBotMonitor buildbot;
        private ListView listView;

        public WebKitWatcher() {
                super();
                this.buildbot = null;
                this.listView = null;
        }

        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                this.buildbot = new BuildBotMonitor(this);
                this.listView = getListView();
                this.listView.setTextFilterEnabled(true);
                this.listView.setOnItemClickListener(new OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                                // When clicked, show a toast with the TextView text
                                Builder builder = (Builder) parent.getAdapter().getItem(position);
                                Toast.makeText(getApplicationContext(),
                                                "Last build number: " + builder.getBuildNumber() + "\n"
                                                + "SVN revision: " + builder.getRevisionAsString() + "\n"
                                                + builder.getSummary(),
                                                Toast.LENGTH_LONG).show();
                        }
                });
                registerForContextMenu(this.listView);
                this.buildbot.refreshState();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.main_menu, menu);
                return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                // Handle item selection
                switch (item.getItemId()) {
                case R.id.refresh:
                        this.buildbot.refreshState();
                        return true;
                default:
                        return super.onOptionsItemSelected(item);
                }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                        ContextMenuInfo menuInfo) {
                super.onCreateContextMenu(menu, v, menuInfo);
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
        }

        @Override
        public boolean onContextItemSelected(MenuItem item) {
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                ListAdapter adapter = listView.getAdapter();
                Builder builder = (Builder) adapter.getItem((int)info.id);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = null;

                switch (item.getItemId()) {
                case R.id.browsebuilder:
                        url = this.buildbot.getURL() + "/" + builder.getPath();
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        return true;
                case R.id.browselastbuild:
                        url = this.buildbot.getURL() + "/" + builder.getPath() + "/builds/" + builder.getBuildNumber();
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        return true;
                case R.id.browserevision:
                        int revision = builder.getRevision();
                        if (revision != -1) {
                                url = "http://trac.webkit.org/changeset/" + builder.getRevision();
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                        } else {
                                Toast.makeText(getApplicationContext(),
                                                this.getString(R.string.error_unknown_svn_revision),
                                                Toast.LENGTH_LONG).show();
                        }
                        return true;
                case R.id.browsecoreconsole:
                        url = this.buildbot.getURL() + "/console?category=core";
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        return true;
                case R.id.browsecorewaterfall:
                        url = this.buildbot.getURL() + "/waterfall?category=core";
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                        return true;
                default:
                        return super.onOptionsItemSelected(item);
                }
        }

        public void updateView () {

                // Get a list of valid builders first
                List<Builder> validBuilders = new ArrayList<Builder>();
                for (Builder builder : buildbot.getBuilders().values()) {
                        if (builder.getName() != null) {
                                validBuilders.add(builder);
                        }
                }

                // Show error message if no valid builder was found
                if (validBuilders.isEmpty()) {
                        Toast.makeText(getApplicationContext(),
                                        this.getString(R.string.error_no_valid_builders_found),
                                        Toast.LENGTH_LONG).show();
                }

                // Build the adapter and use it in the list view
                ArrayAdapter<Builder> adapter = new BuilderAdapter(this, R.layout.builder_listitem, validBuilders.toArray(new Builder[0]));
                Comparator<Builder> comparator = new Comparator<Builder>() {
                        @Override
                        public int compare(Builder b1, Builder b2) {
                                return b1.getName().compareToIgnoreCase(b2.getName());
                        }
                };
                adapter.sort(comparator);
                setListAdapter(adapter);
        }
}