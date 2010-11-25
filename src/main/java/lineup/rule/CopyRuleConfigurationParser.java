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
import lineup.ConfigurationParser;

import org.jdom.Element;

import com.google.inject.Inject;

/**
 * {@link CopyRule} {@link ConfigurationParser}.
 * 
 */
public class CopyRuleConfigurationParser implements RuleConfigurationParser {

	private RuleIdGenerator ruleIdGenerator;

	@Inject
	public CopyRuleConfigurationParser(RuleIdGenerator ruleIdGenerator) {
		this.ruleIdGenerator = ruleIdGenerator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvd.rule.RuleConfigurationParser#parseElement(org.jdom.Element)
	 */
	public List<Rule> parseElement(Element elem) throws ConfigurationException {

		if (!elem.getName().equals("copy")) {
			return null;
		}
		List<Rule> rules = new ArrayList<Rule>();

		CopyRule copyRule = new CopyRule();
		rules.add(copyRule);

		String idAttr = elem.getAttributeValue("id");
		if (idAttr == null) {
			throw new ConfigurationException("Copy rule needs id attribute!");
		}
		copyRule.setId(new RuleId(idAttr));
		
		Element fromElem = elem.getChild("from");
		if (fromElem == null) {
			throw new ConfigurationException("Copy rule needs from element!");
		}
		if (fromElem.getAttribute("ref") != null) {
			copyRule.setFrom(new RuleId(fromElem.getAttributeValue("ref")));
		} else {
			LabelRule labelRule = new LabelRule();
			rules.add(labelRule);
			labelRule.setId(ruleIdGenerator.nextId());
			labelRule.setLabel(fromElem.getText());
			copyRule.setFrom(labelRule.getId());
		}

		Element toElem = elem.getChild("to");
		if (toElem == null) {
			throw new ConfigurationException("Copy rule needs to element!");
		}
		if (toElem.getAttribute("ref") != null) {
			copyRule.setTo(new RuleId(toElem.getAttributeValue("ref")));
		} else {
			LabelRule labelRule = new LabelRule();
			rules.add(labelRule);
			labelRule.setId(ruleIdGenerator.nextId());
			labelRule.setLabel(toElem.getText());
			copyRule.setTo(labelRule.getId());
		}

		return rules;
	}
}
