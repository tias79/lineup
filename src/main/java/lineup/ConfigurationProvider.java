/**
 * 
 * LineUp
 * Copyright (C) 2010  Mattias Eklöf
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * a long with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 */
package lineup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * Guice {@link Configuration} provider.
 * 
 */
public class ConfigurationProvider implements Provider<Set<Configuration>> {

	private Logger logger = Logger.getLogger(ConfigurationProvider.class);

	private String[] args;

	private ConfigurationParser configurationParser;

	@Inject
	public ConfigurationProvider(@Named("ConfigurationFiles") String[] configurationFiles,
			ConfigurationParser configurationParser) {
		this.args = configurationFiles;
		this.configurationParser = configurationParser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.Provider#get()
	 */
	public Set<Configuration> get() {

		Set<Configuration> configs = new HashSet<Configuration>();
		for (String configFile : args) {
			try {
				Configuration config = configurationParser
						.parseConfiguration(new FileInputStream(configFile));
				configs.add(config);

				logger.info("Successfully parsed configuration file: "
						+ configFile);
			} catch (FileNotFoundException ex) {
				logger.error("Configuration file not found: " + configFile, ex);
			} catch (IOException ex) {
				logger.error("Error reading configuration file: " + configFile, ex);
			} catch (ConfigurationException ex) {
				logger.error("Error parsing configuration file: " + configFile, ex);
			}
		}

		return configs;
	}
}
