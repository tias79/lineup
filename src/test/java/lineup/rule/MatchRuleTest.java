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

import lineup.rule.MatchRule;
import lineup.rule.RuleId;
import lineup.rule.RuleOutput;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;


/**
 * Tests for {@link MatchRule}.
 * 
 */
public class MatchRuleTest {

	private MatchRule rule;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		rule = new MatchRule();
	}

	/**
	 * Test method for {@link lineup.rule.MatchRule#trigger()}.
	 */
	@Test
	public void testTrigger() {
		final RuleId input = new RuleId("input");
		final RuleId exp = new RuleId("label1");
		final RuleId assign1 = new RuleId("match1");
		final RuleId assign2 = new RuleId("match2");

		rule.setExpression(exp);
		rule.setInput(input);

		rule.addAssign(1, assign1);
		rule.addAssign(2, assign2);

		Map<RuleId, String> inputs = new HashMap<RuleId, String>();
		inputs.put(input, "test10test99test");
		inputs.put(exp, "test([0-9]*)test([0-9]*)test");
		
		RuleOutput ruleOutput = rule.execute(inputs);
		
		assertEquals("test10test99test", ruleOutput.getNameValue());
		assertNotNull(ruleOutput.getVariables());
		assertEquals("10", ruleOutput.getVariables().get(assign1));
		assertEquals("99", ruleOutput.getVariables().get(assign2));
	}

}
