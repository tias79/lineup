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

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import lineup.rule.PrintStreamRule;
import lineup.rule.RuleId;
import lineup.rule.RuleOutput;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link PrintStreamRule}.
 *
 */
public class PrintStreamRuleTest {

    private Mockery context = new Mockery() {{
        setImposteriser(ClassImposteriser.INSTANCE);
    }};

    private PrintStream printStream;
    
	public PrintStreamRule rule;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		printStream = context.mock(PrintStream.class);
		
		rule = new PrintStreamRule(printStream);
	}

	/**
	 * Test method for {@link lineup.rule.PrintStreamRule#execute(java.util.Map)}.
	 */
	@Test
	public void testExecute() {
		final RuleId input = new RuleId("input");
		final String inputString = "test";
		
		rule.setInput(input);
		
		Map<RuleId, String> parameters = new HashMap<RuleId, String>();
		parameters.put(input, inputString);
		
		context.checking(new Expectations() {
			{
				one(printStream).println(with(inputString));
			}
		});
		
		
		
		RuleOutput output = rule.execute(parameters);
		assertEquals(inputString, output.getNameValue());
		assertNull(output.getVariables());
	}

	/**
	 * Test method for {@link lineup.rule.PrintStreamRule#getRequiredParameters()}.
	 */
	@Test
	public void testGetRequiredParameters() {
		final RuleId input = new RuleId("input");
		rule.setInput(input);
		
		assertEquals(1, rule.getRequiredParameters().size());
		assertTrue(rule.getRequiredParameters().contains(input));
	}

}
