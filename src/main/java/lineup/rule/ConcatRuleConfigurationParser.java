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

import com.google.inject.Inject;

/**
 * Concat {@link RuleConfigurationParser}.
 *
 */
public class ConcatRuleConfigurationParser implements RuleConfigurationParser {

	private RuleIdGenerator ruleIdGenerator;

	@Inject
	public ConcatRuleConfigurationParser(RuleIdGenerator ruleIdGenerator) {
		this.ruleIdGenerator = ruleIdGenerator;
	}
	
	/* (non-Javadoc)
	 * @see mvd.rule.RuleConfigurationParser#parseElement(org.jdom.Element)
	 */
	public List<Rule> parseElement(Element elem) throws ConfigurationException {
		
		if (!elem.getName().equals("concat")) {
			return null;
		}
		
		List<Rule> rules = new ArrayList<Rule>();

		ConcatRule concatRule = new ConcatRule();
		rules.add(concatRule);

		String idAttr = elem.getAttributeValue("id");
		if (idAttr == null) {
			throw new ConfigurationException("Concat rule needs id attribute!");
		}
		concatRule.setId(new RuleId(idAttr));

		List<?> possibleParts = elem.getChildren("part");
		for (Object obj : possibleParts) {
			Element possiblePart = (Element) obj;

			int order = -1;
			try {
				order = Integer.parseInt(possiblePart.getAttributeValue("ord"));
			} catch (NumberFormatException ex) {
				throw new ConfigurationException("Concat rule part needs ord attribute set to an integer value!", ex);
			}

			String refAttr = possiblePart.getAttributeValue("ref");

			if (refAttr != null) {
				concatRule.addPart(order, new RuleId(refAttr));
			} else {
				LabelRule labelRule = new LabelRule();
				labelRule.setId(ruleIdGenerator.nextId());
				labelRule.setLabel(possiblePart.getText());
				rules.add(labelRule);
				concatRule.addPart(order, labelRule.getId());
			}
		}

		return rules;
	}

}
