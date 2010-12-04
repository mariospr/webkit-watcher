/*
 * Builder.java
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

public class Builder {

    public enum BuildResult {
	UNKNOWN,      // GRAY
	PASSED,       // GREEN
	FAILED,       // RED
	FAILED_AGAIN, // ORANGE
	RUNNING,      // YELLOW
	NO_DATA       // WHITE
    };

    private String name;
    private String path;
    private BuildResult buildResult;
    private int revision;
    private String buildNumber;
    private String summary;
    private String status;

    public Builder(String name) {
	this.name = name;
	this.path = null;
	this.buildResult = BuildResult.UNKNOWN;
	this.revision = -1;
	this.summary = null;
    }

    public String getName() {
	return name;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path= path;
    }

    public BuildResult getBuildResult() {
	return buildResult;
    }

    public void setBuildResult(BuildResult buildResult) {
	this.buildResult = buildResult;
    }

    public int getRevision() {
	return revision;
    }

    public void setRevision(int revision) {
	this.revision = revision;
    }

    public String getRevisionAsString() {
	if (this.revision == -1)
	    return new String("Unknown");

	return Integer.toString(this.revision);
    }

    public void setRevisionFromString(String revision) {
	try {
	    this.revision = Integer.parseInt(revision);
	} catch (NumberFormatException e) {
	    this.revision = -1;
	}
    }

    public String getBuildNumber() {
	return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
	this.buildNumber = buildNumber;
    }

    public String getSummary() {
	return summary;
    }

    public void setSummary(String summary) {
	this.summary = summary;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String toString() {
	return this.getName();
    }
}
