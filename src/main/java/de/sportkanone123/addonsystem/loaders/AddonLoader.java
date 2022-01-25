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
 * AddonClassLoader is based on AlphaLibary by WhoIsAlphaHelix.
 * GitHub: https://github.com/WhoIsAlphaHelix/AlphaLibary
 */

package de.sportkanone123.addonsystem.loaders;

import de.sportkanone123.addonsystem.exceptions.AddonNotFoundException;
import de.sportkanone123.addonsystem.exceptions.InvalidAddonException;
import de.sportkanone123.addonsystem.objects.Addon;

import java.io.File;
import java.io.IOException;

public class AddonLoader {
    public static Addon loadAddon(File file, File addonDir) throws InvalidAddonException, AddonNotFoundException {
        if(file == null)
            throw  new InvalidAddonException("file cannot be null");

        if(!file.exists())
            throw new AddonNotFoundException(String.valueOf(file.getPath()) + " does not exist");

        File dataFolder = new File(addonDir + File.separator + file.getName());

        AddonClassLoader loader;

        try {
            loader = new AddonClassLoader(Addon.class.getClassLoader(), file, dataFolder);
            loader.close();
            return loader.getAddon();
        } catch(IOException e) {
            throw new InvalidAddonException(e);
        }
    }
}
