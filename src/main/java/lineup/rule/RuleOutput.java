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

import java.util.Collections;
import java.util.Map;

/**
 * 
 *
 */
public class RuleOutput {

	private String nameValue;
	
	private Map<RuleId, String> variables;

	public RuleOutput(String nameValue, Map<RuleId, String> variables) {
		this.nameValue = nameValue;
		this.variables = variables;
	}

	public RuleOutput(String nameValue) {
		this.nameValue = nameValue;
	}	
	
	public String getNameValue() {
		return nameValue;
	}

	public Map<RuleId, String> getVariables() {
		if (variables == null) {
			return null;
		}
		
		return Collections.unmodifiableMap(variables);
	}
	
}
