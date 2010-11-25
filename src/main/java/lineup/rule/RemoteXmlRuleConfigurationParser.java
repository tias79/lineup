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
 * 
 *
 */
public class RemoteXmlRuleConfigurationParser implements
		RuleConfigurationParser {

	private RuleIdGenerator ruleIdGenerator;

	@Inject
	public RemoteXmlRuleConfigurationParser(RuleIdGenerator ruleIdGenerator) {
		this.ruleIdGenerator = ruleIdGenerator;
	}
	
	/* (non-Javadoc)
	 * @see mvd.rule.RuleConfigurationParser#parseElement(org.jdom.Element)
	 */
	public List<Rule> parseElement(Element elem) throws ConfigurationException {
		
		if (!elem.getName().equals("remoteXml")) {
			return null;
		}
		
		List<Rule> rules = new ArrayList<Rule>();

		RemoteXmlRule rule = new RemoteXmlRule();
		rules.add(rule);

		String idAttr = elem.getAttributeValue("id");
		if (idAttr == null) {
			throw new ConfigurationException("RemoteXml rule needs it attribute!");
		}
		rule.setId(new RuleId(idAttr));

		Element inputElem = elem.getChild("input");
		if (inputElem.getAttribute("ref") != null) {
			rule.setInput(new RuleId(inputElem.getAttributeValue("ref")));
		} else {
			LabelRule labelRule = new LabelRule();
			rules.add(labelRule);
			labelRule.setId(ruleIdGenerator.nextId());
			labelRule.setLabel(inputElem.getText());
			rule.setInput(labelRule.getId());
		}

		List<?> possibleAssigns = elem.getChildren("assign");
		for (Object obj : possibleAssigns) {
			Element possibleAssign = (Element) obj;

			String name = possibleAssign.getAttributeValue("xpath");
			if (name == null) {
				throw new ConfigurationException("RemoteXml rule assign needs xpath attribute!");
			}
			String assignId = possibleAssign.getAttributeValue("id");
			if (assignId == null) {
				throw new ConfigurationException("RemoteXml rule assign needs id attribute!");
			}
			RuleId id = new RuleId(assignId);

			rule.addAssign(id, name);
		}

		return rules;
	}

}
