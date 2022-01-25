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
 * AddonClassLoader contains parts of AlphaLibary by WhoIsAlphaHelix.
 * GitHub: https://github.com/WhoIsAlphaHelix/AlphaLibary
 */

package de.sportkanone123.addonsystem.loaders;

import de.sportkanone123.addonsystem.exceptions.AddonNotFoundException;
import de.sportkanone123.addonsystem.exceptions.InvalidAddonDescriptionException;
import de.sportkanone123.addonsystem.exceptions.InvalidAddonException;
import de.sportkanone123.addonsystem.objects.Addon;
import de.sportkanone123.addonsystem.objects.AddonDescription;
import de.sportkanone123.addonsystem.objects.AddonDescriptionFile;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AddonClassLoader extends URLClassLoader {
    private File dataFolder;
    private Addon addon;

    public AddonClassLoader(ClassLoader parent, File file, File dataFolder) throws InvalidAddonException, MalformedURLException {
        super(new URL[]{file.toURI().toURL()}, parent);

        this.dataFolder = dataFolder;

        try {
            Class<?> mainClass;
            AddonDescription description;

            try {
                AddonDescriptionFile descriptionFile = getPluginDescription(file);

                mainClass = loadClass(descriptionFile.getMainClass());
                description = new AddonDescription(descriptionFile);
            } catch (Throwable throwable) {
                throw new InvalidAddonException(throwable, "error loading addon");
            }

            this.addon = (Addon) mainClass.getDeclaredConstructor().newInstance();
            this.addon.setAddonDescription(description);
        } catch(IllegalAccessException ex) {
            throw new InvalidAddonException(ex, "no public constructor");
        } catch(InstantiationException ex) {
            throw new InvalidAddonException(ex, "abnormal addon type");
        } catch(NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        this.initialize(this.addon);
    }

    public AddonDescriptionFile getPluginDescription(File file) throws InvalidAddonDescriptionException, AddonNotFoundException, InvalidAddonException {
        if(file == null)
            throw new AddonNotFoundException("file cannot be null");

        JarFile jarFile = null;
        InputStream stream = null;

        try {
            jarFile = new JarFile(file);
            JarEntry entry = jarFile.getJarEntry("addon.yml");

            if (entry == null)
                throw new InvalidAddonException("jar does not contain plugin.yml");

            return new AddonDescriptionFile(jarFile.getInputStream(entry));

        } catch (IOException ex) {
            throw new InvalidAddonException(ex);
        } catch (YAMLException ex) {
            throw new InvalidAddonException(ex);
        } finally {
            try {
                if (jarFile != null)
                    jarFile.close();

                if (stream != null)
                    stream.close();

            } catch (IOException ex) {}
        }
    }


    private synchronized void initialize(Addon addon) throws InvalidAddonException {
        if(addon == null)
            throw new InvalidAddonException("initializing addon cannot be null");

        if(addon.getClass().getClassLoader() != this)
            throw new InvalidAddonException("cannot initialize plugin outside of this class loader");

        if(this.addon.isLoaded())
            throw new InvalidAddonException("addon already initialized!");

        addon.load(this, this.dataFolder);
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public Addon getAddon() {
        return addon;
    }
}
