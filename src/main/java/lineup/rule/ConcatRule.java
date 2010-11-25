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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * Concat {@link Rule}.
 * 
 */
public class ConcatRule extends AbstractRule {

	private Map<Integer, RuleId> parts = new TreeMap<Integer, RuleId>();

	public List<RuleId> getParts() {
		return ImmutableList.copyOf(parts.values());
	}

	/**
	 * @param order
	 * @param name
	 */
	public void addPart(int order, RuleId name) {
		parts.put(order, name);
	}

	/* (non-Javadoc)
	 * @see lineup.rule.Rule#execute(java.util.Map)
	 */
	public RuleOutput execute(Map<RuleId, String> parameters) {

		String str = "";

		Collection<RuleId> c = parts.values();
		Iterator<RuleId> it = c.iterator();
		while (it.hasNext()) {
			str += parameters.get(it.next());
		}

		RuleOutput ruleOutput = new RuleOutput(str);

		return ruleOutput;

	}

	/* (non-Javadoc)
	 * @see lineup.rule.Rule#getRequiredParameters()
	 */
	public Set<RuleId> getRequiredParameters() {
		Set<RuleId> set = new HashSet<RuleId>(parts.values());
		return ImmutableSet.copyOf(set);
	}
}
