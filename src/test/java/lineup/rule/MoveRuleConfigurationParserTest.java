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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import lineup.ConfigurationException;
import lineup.rule.LabelRule;
import lineup.rule.MoveRule;
import lineup.rule.MoveRuleConfigurationParser;
import lineup.rule.Rule;
import lineup.rule.RuleId;
import lineup.rule.RuleIdGenerator;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for {@link MoveRuleConfigurationParser}.
 *
 */
@RunWith(JMock.class)
public class MoveRuleConfigurationParserTest {

	private Mockery context = new JUnit4Mockery();

	private RuleIdGenerator ruleIdGenerator;

	private MoveRuleConfigurationParser parser;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		ruleIdGenerator = context.mock(RuleIdGenerator.class);

		parser = new MoveRuleConfigurationParser(ruleIdGenerator);
	}

	/**
	 * Test method for
	 * {@link lineup.rule.MoveRuleConfigurationParser#parseElement(org.jdom.Element)}
	 * .
	 * 
	 * @throws IOException
	 * @throws JDOMException
	 * @throws ConfigurationException
	 */
	@Test
	public void testParseElement() throws JDOMException, IOException,
			ConfigurationException {
		context.checking(new Expectations() {
			{
				one(ruleIdGenerator).nextId();
				will(returnValue(new RuleId("1")));
			}
		});

		InputStream configurationStream = ClassLoader
				.getSystemResourceAsStream("rule/moveRule1.xml");
		SAXBuilder saxParser = new SAXBuilder();
		Document doc = saxParser.build(configurationStream);

		List<Rule> rules = parser.parseElement(doc.getRootElement());
		
		assertEquals(2, rules.size());

		assertTrue(rules.get(0) instanceof MoveRule);
		MoveRule copyRule = (MoveRule) rules.get(0);
		assertTrue(rules.get(1) instanceof LabelRule);
		LabelRule labelRule = (LabelRule) rules.get(1);
		
		assertEquals(new RuleId("move1"), copyRule.getId());
		assertEquals(new RuleId("toid"), copyRule.getTo());
		assertEquals(labelRule.getId(), copyRule.getFrom());
	}

}
