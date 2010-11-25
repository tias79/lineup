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

import lineup.rule.ConcatRule;
import lineup.rule.RuleId;
import lineup.rule.RuleOutput;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


/**
 * Tests for {@link ConcatRule}.
 *
 */
public class ConcatRuleTest {

	private ConcatRule rule;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		rule = new ConcatRule();
	}

	/**
	 * Test method for {@link lineup.rule.AbstractRule#trigger()}.
	 */
	@Test
	public void testTrigger() {
		final RuleId name = new RuleId("concatRule");
		
		final RuleId part1Name = new RuleId("part1");
		final String part1Value = "aaa";
		final RuleId part2Name = new RuleId("part2");
		final String part2Value = "bbb";
		final RuleId part3Name = new RuleId("part3");
		final String part3Value = "ccc";

		rule.setId(name);

		rule.addPart(3, part3Name);
		rule.addPart(1, part1Name);
		rule.addPart(2, part2Name);

		Map<RuleId, String> requiredParameters = new HashMap<RuleId, String>();
		requiredParameters.put(part1Name, part1Value);
		requiredParameters.put(part2Name, part2Value);
		requiredParameters.put(part3Name, part3Value);
		RuleOutput ruleOutput = rule.execute(requiredParameters);
		
		assertEquals(part1Value + part2Value + part3Value, ruleOutput.getNameValue());
		assertNull(ruleOutput.getVariables());
	}

}
