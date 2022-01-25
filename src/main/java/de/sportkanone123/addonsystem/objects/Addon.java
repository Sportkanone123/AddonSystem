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

import de.sportkanone123.addonsystem.loaders.AddonClassLoader;

import java.io.File;

public abstract class Addon {
    private String name, description, author, version;
    private File dataFolder;
    private ClassLoader loader;
    private boolean loaded = false;

    public Addon() {}

    public Addon(AddonDescription info) {
        this.name = info.getName();
        this.version = info.getVersion();
        this.description = info.getDescription();
        this.author = info.getAuthor();
    }

    public void load(AddonClassLoader classLoader, File dataFolder) {
        this.dataFolder = dataFolder;
        this.loader = classLoader;
        this.onEnable();
        this.loaded = true;
    }

    public void unload(AddonClassLoader classLoader, File dataFolder) {
        this.onDisable();
        this.loaded = false;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public void setAddonDescription(AddonDescription info) {
        this.name = info.getName();
        this.version = info.getVersion();
        this.description = info.getDescription();
        this.author = info.getAuthor();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public void setDataFolder(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public ClassLoader getLoader() {
        return loader;
    }

    public void setLoader(ClassLoader loader) {
        this.loader = loader;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
