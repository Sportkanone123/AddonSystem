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

package de.sportkanone123.addonsystem.exceptions;

public class InvalidAddonDescriptionException extends Exception{
    public InvalidAddonDescriptionException(String message) {
        super(message);
    }

    public InvalidAddonDescriptionException(Throwable cause) {
        super(cause);
    }

    public InvalidAddonDescriptionException(Throwable cause, String message) {
        super(message, cause);
    }
}
