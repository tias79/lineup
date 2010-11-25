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

import java.util.HashMap;

import lineup.rule.LabelRule;
import lineup.rule.RuleId;
import lineup.rule.RuleOutput;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link LabelRule}.
 *
 */
public class LabelRuleTest {

	private LabelRule rule;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		rule = new LabelRule();
	}

	/**
	 * Test method for {@link lineup.rule.LabelRule#execute(java.util.Map)}.
	 */
	@Test
	public void testExecute() {
		rule.setLabel("label");
		
		RuleOutput output = rule.execute(new HashMap<RuleId, String>());
		assertEquals("label", output.getNameValue());
		assertNull(output.getVariables());
	}

	/**
	 * Test method for {@link lineup.rule.LabelRule#getRequiredParameters()}.
	 */
	@Test
	public void testGetRequiredParameters() {
		assertEquals(0, rule.getRequiredParameters().size());
	}

}
