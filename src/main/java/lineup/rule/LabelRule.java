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

import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableSet;


/**
 *
 *
 */
public class LabelRule extends AbstractRule {

	private String label = new String(); 
	
	/* (non-Javadoc)
	 * @see mvd.Rule#execute(java.util.Map)
	 */
	public RuleOutput execute(Map<RuleId, String> inputs) {
		return new RuleOutput(label);
	}

	/* (non-Javadoc)
	 * @see mvd.Rule#getRequiredParameters()
	 */
	public Set<RuleId> getRequiredParameters() {
		return ImmutableSet.of();
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
