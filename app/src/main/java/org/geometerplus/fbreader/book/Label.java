/*
 * Copyright (C) 2007-2015 FBReader.ORG Limited <contact@fbreader.org>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.fbreader.book;

import java.util.UUID;

public class Label {

    public final String Uid;
    public final String Name;

    public Label(String uid, String name) {
        if (uid == null || name == null) {
            throw new IllegalArgumentException("Label(" + uid + "," + name + ")");
        }
        Uid = uid;
        Name = name;
    }

    Label(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    @Override
    public String toString() {
        return Name + "[" + Uid + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Label)) {
            return false;
        }
        return Name.equals(((Label)other).Name);
    }

    @Override
    public int hashCode() {
        return Name.hashCode();
    }
}
