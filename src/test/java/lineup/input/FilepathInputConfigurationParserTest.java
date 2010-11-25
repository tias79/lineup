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

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import lineup.input.FilepathInput;
import lineup.input.FilepathInputConfigurationParser;
import lineup.input.Input;
import lineup.input.InputConfigurationParser;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link FilepathInputConfigurationParser}.
 * 
 */
public class FilepathInputConfigurationParserTest {

	private InputConfigurationParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new FilepathInputConfigurationParser();
	}

	/**
	 * Test method for
	 * {@link lineup.input.FilepathInputConfigurationParser#parseElement(org.jdom.Element)}
	 * .
	 * 
	 * @throws IOException
	 * @throws JDOMException
	 */
	@Test
	public void testParseElement() throws JDOMException, IOException {

		InputStream configurationStream = ClassLoader
				.getSystemResourceAsStream("input/filepathInput1.xml");
		SAXBuilder saxParser = new SAXBuilder();
		Document doc = saxParser.build(configurationStream);

		Input input = parser.parseElement(doc.getRootElement());

		assertTrue(input instanceof FilepathInput);
		FilepathInput filepathInput = (FilepathInput) input;
		assertEquals("test", filepathInput.getName());
		Set<File> paths = filepathInput.getPaths();
		assertEquals(1, paths.size());
		assertEquals(new File("/home/test1").getAbsolutePath(), paths
				.iterator().next().getAbsolutePath());
				
	}

}
