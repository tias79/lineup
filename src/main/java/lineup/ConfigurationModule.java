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

import java.util.Set;

import lineup.rule.BasicRuleIdGenerator;
import lineup.rule.RuleIdGenerator;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;

/**
 * Guice {@link Configuration} module.
 * 
 */
public class ConfigurationModule extends AbstractModule {

	private String[] configurationFiles;

	public ConfigurationModule(String[] configurationFiles) {
		this.configurationFiles = configurationFiles;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	public void configure() {
		bind(String[].class).annotatedWith(Names.named("ConfigurationFiles"))
				.toInstance(configurationFiles);

		bind(ConfigurationParser.class).to(JDOMConfigurationParser.class);

		bind(RuleIdGenerator.class).to(BasicRuleIdGenerator.class);

		bind(new TypeLiteral<Set<Configuration>>() {
		}).toProvider(ConfigurationProvider.class);
	}
}
