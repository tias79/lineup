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

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lineup.input.Input;
import lineup.rule.Rule;

/**
 * Configuration value object.
 * 
 * A configuration consists of multiple {@link Input} objects and multiple
 * {@link Rule} objects.
 * 
 */
public class Configuration {

	private Set<Input> inputs = new HashSet<Input>();

	private Set<Rule> rules = new HashSet<Rule>();

	/**
	 * Add {@link Input} to configuration.
	 * 
	 * @param input
	 *            Input value object.
	 */
	public void addInput(Input input) {
		inputs.add(input);
	}

	/**
	 * Get all inputs contained by configuration.
	 * 
	 * @return Input value objects.
	 */
	public Set<Input> getInputs() {
		return ImmutableSet.copyOf(inputs);
	}

	/**
	 * Add {@link Rule} to configuration,
	 * 
	 * @param rule
	 *            Rule value object.
	 */
	public void addRule(Rule rule) {
		rules.add(rule);
	}

	/**
	 * Get all rules contained by configuration.
	 * 
	 * @return Rule value objects.
	 */
	public Set<Rule> getRules() {
		return ImmutableSet.copyOf(rules);
	}
}
