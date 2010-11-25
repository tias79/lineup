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
import java.util.List;

import lineup.ConfigurationException;

import org.jdom.Element;

/**
 *
 *
 */
public class LabelRuleConfigurationParser implements RuleConfigurationParser {

	/* (non-Javadoc)
	 * @see mvd.rule.RuleConfigurationParser#parseElement(org.jdom.Element)
	 */
	public List<Rule> parseElement(Element elem) throws ConfigurationException {
		
		if (!elem.getName().equals("label")) {
			return null;
		}
		
		List<Rule> rules = new ArrayList<Rule>();
		
		LabelRule rule = new LabelRule();
		rules.add(rule);

		String idAttr = elem.getAttributeValue("id");
		if (idAttr == null) {
			throw new ConfigurationException("Label rule needs id attribute!");
		}
		rule.setId(new RuleId(idAttr));
		rule.setLabel(elem.getText());
		
		return rules;
	}

}
