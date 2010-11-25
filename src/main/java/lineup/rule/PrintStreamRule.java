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

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * 
 *
 */
public class PrintStreamRule extends AbstractRule {

	private PrintStream printStream;
	
	private RuleId input;

	public PrintStreamRule(PrintStream printStream) {
		this.printStream = printStream;
	}
	
	/* (non-Javadoc)
	 * @see lineup.rule.Rule#execute(java.util.Map)
	 */
	public RuleOutput execute(Map<RuleId, String> parameters) {

		printStream.println(parameters.get(input));
		
		return new RuleOutput(parameters.get(input));
		
	}

	/* (non-Javadoc)
	 * @see lineup.rule.Rule#getRequiredParameters()
	 */
	public Set<RuleId> getRequiredParameters() {
		Set<RuleId> set = new HashSet<RuleId>();
		set.add(input);
		return ImmutableSet.copyOf(set);
	}

	/**
	 * @return the input
	 */
	public RuleId getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(RuleId input) {
		this.input = input;
	}
}