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

import org.jdom.Element;

import com.google.inject.Inject;

import lineup.ConfigurationException;

/**
 *
 *
 */
public class MoveRuleConfigurationParser implements RuleConfigurationParser {

	private RuleIdGenerator ruleIdGenerator;

	@Inject
	public MoveRuleConfigurationParser(RuleIdGenerator ruleIdGenerator) {
		this.ruleIdGenerator = ruleIdGenerator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvd.rule.RuleConfigurationParser#parseElement(org.jdom.Element)
	 */
	public List<Rule> parseElement(Element elem) throws ConfigurationException {

		if (!elem.getName().equals("move")) {
			return null;
		}
		List<Rule> rules = new ArrayList<Rule>();

		MoveRule moveRule = new MoveRule();
		rules.add(moveRule);

		String idAttr = elem.getAttributeValue("id");
		if (idAttr == null) {
			throw new ConfigurationException("Move rule needs id attribute!");
		}
		moveRule.setId(new RuleId(idAttr));
		
		Element fromElem = elem.getChild("from");
		if (fromElem == null) {
			throw new ConfigurationException("Move rule needs from element!");
		}
		if (fromElem.getAttribute("ref") != null) {
			moveRule.setFrom(new RuleId(fromElem.getAttributeValue("ref")));
		} else {
			LabelRule labelRule = new LabelRule();
			rules.add(labelRule);
			labelRule.setId(ruleIdGenerator.nextId());
			labelRule.setLabel(fromElem.getText());
			moveRule.setFrom(labelRule.getId());
		}

		Element toElem = elem.getChild("to");
		if (toElem == null) {
			throw new ConfigurationException("Move rule needs to element!");
		}
		if (toElem.getAttribute("ref") != null) {
			moveRule.setTo(new RuleId(toElem.getAttributeValue("ref")));
		} else {
			LabelRule labelRule = new LabelRule();
			rules.add(labelRule);
			labelRule.setId(ruleIdGenerator.nextId());
			labelRule.setLabel(toElem.getText());
			moveRule.setTo(labelRule.getId());
		}

		return rules;
	}
}
