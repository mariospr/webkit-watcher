/*
 * AppleMAcListView.java
 *
 * Copyright (C) 2010-2014 Mario Sanchez Prada
 * Authors: Mario Sanchez Prada <mario@mariospr.org>
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

package org.mariospr.webkitwatcher;

public class AppleMacListView extends BuildBotsListView {

    public AppleMacListView() {
	super(R.array.apple_mac_buildbots_regexps);
    }
}
