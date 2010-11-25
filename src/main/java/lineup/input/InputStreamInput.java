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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * {@link InputStream} {@link Input}.
 *
 */
public class InputStreamInput implements Input {

	private BufferedReader reader;
	
	private String name;

	public InputStreamInput(InputStream inputStream) {
		reader = new BufferedReader(new InputStreamReader(inputStream));
	}
	
	/* (non-Javadoc)
	 * @see mvd.input.Input#getName()
	 */
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see mvd.input.Input#readLine()
	 */
	public String readLine() throws IOException {
		return reader.readLine();
	}
}
