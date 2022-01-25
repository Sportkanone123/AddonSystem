/*
 * This file is part of AddonSystem - https://github.com/Sportkanone123/AddonSystem
 * Copyright (C) 2022 Sportkanone123
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.sportkanone123.addonsystem.objects;

public class AddonDescription {
    String name, version, description, author;

    public AddonDescription (String name, String version, String description, String author){
        this.name = name;
        this.version = version;
        this.description = description;
        this.author = author;
    }

    public AddonDescription (AddonDescriptionFile file){
        this.name = file.getName();
        this.version = file.getVersion();
        this.description = file.getDescription();
        this.author = file.getAuthor();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
