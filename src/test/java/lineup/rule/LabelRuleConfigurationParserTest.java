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
package lineup.rule;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import lineup.ConfigurationException;
import lineup.rule.LabelRule;
import lineup.rule.LabelRuleConfigurationParser;
import lineup.rule.Rule;
import lineup.rule.RuleConfigurationParser;
import lineup.rule.RuleId;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link LabelRuleConfigurationParser}.
 * 
 */
public class LabelRuleConfigurationParserTest {

	private RuleConfigurationParser parser;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		parser = new LabelRuleConfigurationParser();
	}

	/**
	 * Test method for
	 * {@link lineup.rule.LabelRuleConfigurationParser#parseElement(org.jdom.Element)}
	 * .
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws ConfigurationException 
	 */
	@Test
	public void testParseElement() throws JDOMException, IOException, ConfigurationException {
		InputStream configurationStream = ClassLoader
				.getSystemResourceAsStream("rule/labelRule1.xml");
		SAXBuilder saxParser = new SAXBuilder();
		Document doc = saxParser.build(configurationStream);
		
		List<Rule> rules = parser.parseElement(doc.getRootElement());
		
		assertEquals(1, rules.size());

		LabelRule rule = (LabelRule) rules.get(0);
		assertEquals(new RuleId("label1"), rule.getId());
		assertEquals("Test", rule.getLabel());

	}

}
