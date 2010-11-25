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

import lineup.rule.BasicRuleService;
import lineup.rule.Rule;
import lineup.rule.RuleId;
import lineup.rule.RuleOutput;
import lineup.rule.RuleService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Tests for {@link BasicRuleService}.
 *
 */
@RunWith(JMock.class)
public class BasicRuleServiceTest {

	private Mockery context = new JUnit4Mockery();
	
	private Rule rule1;
	
	private RuleService service;
	
	private Set<Rule> rules = new HashSet<Rule>();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		rule1 = context.mock(Rule.class, "rule1");
		rules.add(rule1);
		
		service = new BasicRuleService();
		service.setRules(rules);
	}
	/**
	 * Test method for {@link lineup.rule.BasicRuleService#applyRules(java.lang.String)}.
	 */
	@Test
	public void testApplyRules1() {
		
		final String inputString = "inputString";
		
		final String outputString = "outputString";
		
		final RuleId rule1Id = new RuleId("#output");
		
		final Set<RuleId> rule1RequiredParameters = new HashSet<RuleId>();
		rule1RequiredParameters.add(new RuleId("#input"));
		
		final Map<RuleId, String> rule1Inputs = new HashMap<RuleId, String>();
		rule1Inputs.put(new RuleId("#input"), inputString);
		
		final RuleOutput rule1RuleOutput = new RuleOutput(outputString, null);
		
		context.checking(new Expectations() {
			{
				one(rule1).getRequiredParameters(); will(returnValue(rule1RequiredParameters));
				one(rule1).execute(rule1Inputs); will(returnValue(rule1RuleOutput));
				allowing(rule1).getId(); will(returnValue(rule1Id));
			}
		});
		
		service.applyRules(inputString);
	}

	/**
	 * Test method for {@link lineup.rule.BasicRuleService#applyRules(java.lang.String)}.
	 */
	@Test
	public void testApplyRules2() {
		final String inputString = "inputString";
		
		final String outputString = "outputString";
		
		final RuleId rule1Id = new RuleId("test");
		
		final Set<RuleId> rule1RequiredParameters = new HashSet<RuleId>();
		rule1RequiredParameters.add(new RuleId("#input"));
		
		final Map<RuleId, String> rule1Inputs = new HashMap<RuleId, String>();
		rule1Inputs.put(new RuleId("#input"), inputString);
		
		final RuleOutput rule1RuleOutput = new RuleOutput(outputString, null);
		
		context.checking(new Expectations() {
			{
				one(rule1).getRequiredParameters(); will(returnValue(rule1RequiredParameters));
				one(rule1).execute(rule1Inputs); will(returnValue(rule1RuleOutput));
				allowing(rule1).getId(); will(returnValue(rule1Id));
			}
		});
		
		service.applyRules(inputString);
	}
	
	/**
	 * Test method for {@link lineup.rule.BasicRuleService#applyRules(java.lang.String)}.
	 */
	@Test
	public void testApplyRules3() {
		final String inputString = "inputString";
				
		final Set<RuleId> rule1RequiredParameters = new HashSet<RuleId>();
		rule1RequiredParameters.add(new RuleId("test"));
		
		final Map<RuleId, String> rule1Inputs = new HashMap<RuleId, String>();
		rule1Inputs.put(new RuleId("test"), inputString);
		
		context.checking(new Expectations() {
			{
				one(rule1).getRequiredParameters(); will(returnValue(rule1RequiredParameters));
			}
		});
		
		service.applyRules(inputString);
	}	
}
