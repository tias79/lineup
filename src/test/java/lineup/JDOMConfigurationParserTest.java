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

import lineup.ConfigurationException;
import lineup.ConfigurationParser;
import lineup.JDOMConfigurationParser;
import lineup.input.InputConfigurationParser;
import lineup.rule.RuleConfigurationParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.TypeSafeMatcher;
import org.jdom.Element;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for {@link JDOMConfigurationParser}.
 * 
 */
@RunWith(JMock.class)
public class JDOMConfigurationParserTest {

	private Mockery context = new JUnit4Mockery();
	
	private InputConfigurationParser inputParser;
	
	private RuleConfigurationParser ruleParser;
	
	private ConfigurationParser parser;

	public class ElementEqualsMatcher extends TypeSafeMatcher<Element> {
	    private String elemName;

	    public ElementEqualsMatcher(String elemName) {
	        this.elemName = elemName;
	    }

	    public boolean matchesSafely(Element otherElem) {
	        return otherElem.getName().equals(elemName);
	    }

	    public void describeTo(Description description) {
	        description.appendText("an element named ").appendValue(elemName);
	    }
	}	
	
	@Factory
	public static TypeSafeMatcher<Element> anElementEquals(String elemName) {
	    return new JDOMConfigurationParserTest().new ElementEqualsMatcher(elemName);
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		inputParser = context.mock(InputConfigurationParser.class);
		Set<InputConfigurationParser> inputConfigurationParsers = new HashSet<InputConfigurationParser>();
		inputConfigurationParsers.add(inputParser);
		
		ruleParser = context.mock(RuleConfigurationParser.class);
		Set<RuleConfigurationParser> ruleConfigurationParsers = new HashSet<RuleConfigurationParser>();
		ruleConfigurationParsers.add(ruleParser);
		
		parser = new JDOMConfigurationParser(inputConfigurationParsers, ruleConfigurationParsers);
		
	}

	/**
	 * Test method for
	 * {@link lineup.JDOMConfigurationParser#parseConfiguration(java.lang.String)}
	 * .
	 * @throws ConfigurationException 
	 * @throws IOException 
	 */
	@Test
	public void testParseConfiguration() throws IOException, ConfigurationException {
		InputStream configurationStream = ClassLoader
		.getSystemResourceAsStream("JDOMConfigurationParser1.xml");
		
		context.checking(new Expectations() {
			{
				one(inputParser).parseElement(with(anElementEquals("input1"))); will(returnValue(null));
				one(inputParser).parseElement(with(anElementEquals("input2"))); will(returnValue(null));
				
				one(ruleParser).parseElement(with(anElementEquals("rule1"))); will(returnValue(null));
				one(ruleParser).parseElement(with(anElementEquals("rule2"))); will(returnValue(null));
			}
		});
		
		parser.parseConfiguration(configurationStream);
	}
}
