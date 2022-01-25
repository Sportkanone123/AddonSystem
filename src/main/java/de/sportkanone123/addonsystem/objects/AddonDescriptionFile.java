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
 * AddonDescriptionFile contains parts of Bukkit by The Bukkit Project.
 * Authors: https://github.com/Bukkit/Bukkit/graphs/contributors, GitHub: https://github.com/Bukkit/Bukkit
 */

package de.sportkanone123.addonsystem.objects;

import de.sportkanone123.addonsystem.exceptions.InvalidAddonDescriptionException;
import org.bukkit.plugin.PluginAwareness;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.AbstractConstruct;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import javax.imageio.stream.FileImageInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

public class AddonDescriptionFile {
    private static final ThreadLocal<Yaml> YAML = new ThreadLocal<Yaml>() {
        @Override
        protected Yaml initialValue() {
            return new Yaml(new SafeConstructor() {
                {
                    yamlConstructors.put(null, new AbstractConstruct() {
                        @Override
                        public Object construct(final Node node) {
                            if (!node.getTag().startsWith("!@")) {
                                return SafeConstructor.undefinedConstructor.construct(node);
                            }
                            return new PluginAwareness() {
                                @Override
                                public String toString() {
                                    return node.toString();
                                }
                            };
                        }
                    });
                    for (final PluginAwareness.Flags flag : PluginAwareness.Flags.values()) {
                        yamlConstructors.put(new Tag("!@" + flag.name()), new AbstractConstruct() {
                            @Override
                            public PluginAwareness.Flags construct(final Node node) {
                                return flag;
                            }
                        });
                    }
                }
            });
        }
    };

    String name, version, description, author, mainClass;

    public AddonDescriptionFile(InputStream inputStream) throws InvalidAddonDescriptionException {
        loadFromMap(inputStreamAsMap(YAML.get().load(inputStream)));
    }

    private void loadFromMap(Map<?,?> map) throws InvalidAddonDescriptionException{
        try {
            name = map.get("name").toString();

            if (!name.matches("^[A-Za-z0-9 _.-]+$")) {
                throw new InvalidAddonDescriptionException("name '" + name + "' contains invalid characters.");
            }

            name = name.replace(' ', '_');
        } catch (NullPointerException ex) {
            throw new InvalidAddonDescriptionException(ex, "name is not defined");
        } catch (ClassCastException ex) {
            throw new InvalidAddonDescriptionException(ex, "name is of wrong type");
        }

        try {
            version = map.get("version").toString();
        } catch (NullPointerException ex) {
            throw new InvalidAddonDescriptionException(ex, "version is not defined");
        } catch (ClassCastException ex) {
            throw new InvalidAddonDescriptionException(ex, "version is of wrong type");
        }

        try {
            mainClass = map.get("main").toString();
        } catch (NullPointerException ex) {
            throw new InvalidAddonDescriptionException(ex, "main is not defined");
        } catch (ClassCastException ex) {
            throw new InvalidAddonDescriptionException(ex, "main is of wrong type");
        }

        try {
            description = map.get("description").toString();
        } catch (NullPointerException ex) {
            throw new InvalidAddonDescriptionException(ex, "description is not defined");
        } catch (ClassCastException ex) {
            throw new InvalidAddonDescriptionException(ex, "description is of wrong type");
        }

        try {
            author = map.get("author").toString();
        } catch (NullPointerException ex) {
            throw new InvalidAddonDescriptionException(ex, "author is not defined");
        } catch (ClassCastException ex) {
            throw new InvalidAddonDescriptionException(ex, "author is of wrong type");
        }
    }

    private Map<?,?> inputStreamAsMap(Object object) throws InvalidAddonDescriptionException{
        if (object instanceof Map) {
            return (Map<?,?>) object;
        }
        throw new InvalidAddonDescriptionException(object + " is not properly structured.");
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public String getMainClass() {
        return mainClass;
    }
}
