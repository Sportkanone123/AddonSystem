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
 *
 * Credits:
 * AddonManager contains parts of AlphaLibary by WhoIsAlphaHelix.
 * GitHub: https://github.com/WhoIsAlphaHelix/AlphaLibary
 */

package de.sportkanone123.addonsystem;

import de.sportkanone123.addonsystem.exceptions.AddonNotFoundException;
import de.sportkanone123.addonsystem.exceptions.InvalidAddonException;
import de.sportkanone123.addonsystem.loaders.AddonLoader;
import de.sportkanone123.addonsystem.objects.Addon;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AddonManager {
    private final static Pattern JAR_PATTERN = Pattern.compile("(.+?)(\\.jar)");

    private final Boolean DEBUG_ADDONS;
    private final File addonDir;
    private final List<Addon> addons = new ArrayList<>();

    public AddonManager(File directory, Boolean debug) throws InvalidAddonException {
        if(directory == null)
            throw new InvalidAddonException("directory cannot be null");

        if(!directory.isDirectory())
            throw new InvalidAddonException("directory must be a directory");

        if(!directory.exists())
            directory.mkdirs();

        this.addonDir = directory;
        this.DEBUG_ADDONS = debug;
    }

    public Addon[] loadAddons() {
        return this.loadAddons(this.addonDir);
    }

    public Addon[] loadAddons(File directory) {
        List<Addon> result = new ArrayList<>();

        for(File file : directory.listFiles()) {
            try {
                result.add(this.loadAddon(file));
            } catch(InvalidAddonException | AddonNotFoundException e) {
                if(DEBUG_ADDONS)
                    System.out.println("Cannot load '" + file.getName() + "' in folder '" + directory.getPath() + "': " + e.getMessage());
            }
        }

        return result.toArray(new Addon[result.size()]);
    }

    public synchronized Addon loadAddon(File file) throws InvalidAddonException, AddonNotFoundException {
        Addon result;

        if(!AddonManager.JAR_PATTERN.matcher(file.getName()).matches()) {
            throw new InvalidAddonException("file '" + file.getName() + "' is not a Jar!");
        }

        result = AddonLoader.loadAddon(file, this.addonDir);

        if(result != null) {
            this.addons.add(result);
            if(result.getDescription() != null)
                if(DEBUG_ADDONS)
                    System.out.println(result.getName() + " [v." + result.getVersion() + " by " + result.getAuthor() + "] loaded");
        }

        return result;
    }

    public void unloadAddons(){
        List<Addon> result = new ArrayList<>();

        for(Addon addon : getAddons()){
            addon.onDisable();
        }

        this.addons.removeAll(result);
    }

    public Addon[] getAddons() {
        return this.addons.toArray(new Addon[0]);
    }

    public File getAddonDir() {
        return addonDir;
    }
}
