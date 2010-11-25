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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;


/**
 * {@link RuleService} implementation.
 * 
 */
public class BasicRuleService implements RuleService {

	private Logger logger = Logger.getLogger(BasicRuleService.class);
	
	private Set<Rule> rules;

	/*
	 * (non-Javadoc)
	 * 
	 * @see filerenamer.RuleService#applyRules(java.lang.String)
	 */
	public void applyRules(String input) {

		Map<RuleId, String> variables = new HashMap<RuleId, String>();
		variables.put(new RuleId("#input"), input);

		List<Rule> tmpRules = new ArrayList<Rule>(rules);

		boolean atLeastOneGo;
		do {
			Map<RuleId, String> newVariables = new HashMap<RuleId, String>();

			atLeastOneGo = false;
			for (int i = 0; i < tmpRules.size(); i++) {
				if (tmpRules.get(i) == null) {
					continue;
				}

				Set<RuleId> deps = tmpRules.get(i).getRequiredParameters();

				Map<RuleId, String> inputs = new HashMap<RuleId, String>();
				boolean go = true;
				for (RuleId dep : deps) {
					String value = variables.get(dep);

					if (value == null) {
						go = false;
						break;
					}

					inputs.put(dep, value);
				}

				if (go) {
					RuleOutput output = tmpRules.get(i).execute(inputs);
					if (output.getVariables() != null) {
						newVariables.putAll(output.getVariables());
					}
					
					if (tmpRules.get(i).getId() != null) {
						variables.put(tmpRules.get(i).getId(), output.getNameValue());
					}

					tmpRules.set(i, null);
					atLeastOneGo = true;
				}
			}

			variables.putAll(newVariables);
			
			for (RuleId id : variables.keySet()) {
				logger.debug(id + "=" + variables.get(id));
			}
			
		} while (atLeastOneGo);

	}

	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}

}
