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

import java.io.IOException;
import java.io.InputStream;

import lineup.input.Input;
import lineup.input.InputConfigurationParser;
import lineup.input.InputStreamInput;
import lineup.input.StdinInputConfigurationParser;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for {@link StdinInputConfigurationParser}.
 * 
 */
@RunWith(JMock.class)
public class StdinInputConfigurationParserTest {

	private Mockery context = new JUnit4Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};
	
	private InputConfigurationParser parser;

	private InputStream inputStream;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		inputStream = context.mock(InputStream.class);
		
		this.parser = new StdinInputConfigurationParser(inputStream);
	}

	/**
	 * Test method for
	 * {@link lineup.input.StdinInputConfigurationParser#parseElement(org.jdom.Element)}
	 * .
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	@Test
	public void testParseElement() throws JDOMException, IOException {
		InputStream configurationStream = ClassLoader
				.getSystemResourceAsStream("input/stdinInput1.xml");
		SAXBuilder saxParser = new SAXBuilder();
		Document doc = saxParser.build(configurationStream);

		Input input = parser.parseElement(doc.getRootElement());

		assertTrue(input instanceof InputStreamInput);
		InputStreamInput inputStreamInput = (InputStreamInput) input;
		assertEquals("test", inputStreamInput.getName());
	}

}
