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
package lineup.input;

import java.io.InputStream;

import lineup.StdinProvider;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;

/**
 * Guice stdin {@link Input} module.
 * 
 */
public class StdinInputModule extends AbstractModule {

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
 */
	@Override
	protected void configure() {
		Multibinder<InputConfigurationParser> inputConfigurationParserBinder = Multibinder
				.newSetBinder(binder(), InputConfigurationParser.class);

		inputConfigurationParserBinder.addBinding().to(
				StdinInputConfigurationParser.class);
		
		bind(InputStream.class)
        .annotatedWith(Names.named("STDIN"))
        .toProvider(StdinProvider.class);
	}
}
