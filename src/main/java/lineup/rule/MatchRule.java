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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;


/**
 * 
 * 
 */
public class MatchRule extends AbstractRule {

	private RuleId expression;

	private RuleId input;
	
	private Map<Integer, RuleId> assigns = new HashMap<Integer, RuleId>();
	
	public Set<RuleId> getRequiredParameters() {
		Set<RuleId> required = new HashSet<RuleId>();
		required.add(input);
		required.add(expression);

		return ImmutableSet.copyOf(required);
	}
	
	public RuleOutput execute(Map<RuleId, String> parameters) {

		String expressionValue = parameters.get(expression);
		Pattern p = Pattern.compile(expressionValue);

		String inputValue = parameters.get(input);
		Matcher m = p.matcher(inputValue);
		
		if (m.find()) {
			Map<RuleId, String> variables = new HashMap<RuleId, String>();
			
			for (int i = 0; i < m.groupCount(); i++) {
				if (i >= assigns.size()) {
					break;
				}
				
				variables.put(assigns.get(i+1), m.group(i+1));
			}
		
			RuleOutput ruleOutput = new RuleOutput(m.group(), ImmutableMap.copyOf(variables));
			return ruleOutput;
		}
		
		RuleOutput ruleOutput = new RuleOutput(null);
		return ruleOutput;
	}

	public RuleId getExpression() {
		return expression;
	}
	
	public void setExpression(RuleId expression) {
		this.expression = expression;
	}
	
	public Map<Integer, RuleId> getAssigns() {
		return ImmutableMap.copyOf(assigns);
	}
	
	public void addAssign(int index, RuleId name) {
		assigns.put(index, name);
	}

	public RuleId getInput() {
		return input;
	}	
	
	public void setInput(RuleId input) {
		this.input = input;
	}
}
