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

package de.sportkanone123.addonsystem;

import de.sportkanone123.addonsystem.exceptions.InvalidAddonException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/*
 * Just an example for how to use this library.
 */
public class AddonSystem extends JavaPlugin {
    private static final File ADDON_FOLDER = new File("plugins/AddonSystem/addons");

    AddonManager addonManager;

    @Override
    public void onEnable() {
        System.out.println("[System] Loading plugin ...");

        try {
            System.out.println("[System] Loading addons ...");

            addonManager = new AddonManager(ADDON_FOLDER, true);
            addonManager.loadAddons();
        } catch (InvalidAddonException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        addonManager.unloadAddons();
    }
}
