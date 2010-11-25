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
 * Image metadata {@link RuleConfigurationParser}.
 *
 */
public class ImageMetadataRuleConfigurationParser implements
		RuleConfigurationParser {

	private RuleIdGenerator ruleIdGenerator;

	@Inject
	public ImageMetadataRuleConfigurationParser(RuleIdGenerator ruleIdGenerator) {
		this.ruleIdGenerator = ruleIdGenerator;
	}
	
	/* (non-Javadoc)
	 * @see mvd.rule.RuleConfigurationParser#parseElement(org.jdom.Element)
	 */
	public List<Rule> parseElement(Element elem) throws ConfigurationException {
		
		if (!elem.getName().equals("imageMetadata")) {
			return null;
		}

		List<Rule> rules = new ArrayList<Rule>();

		ImageMetadataRule rule = new ImageMetadataRule();
		rules.add(rule);

		String idAttr = elem.getAttributeValue("id");
		if (idAttr == null) {
			throw new ConfigurationException("ImageMetadata rule needs id attribute!");
		}
		rule.setId(idAttr != null ? new RuleId(idAttr) : null);

		Element inputElem = elem.getChild("input");
		if (inputElem == null) {
			throw new ConfigurationException("ImageMetadata rule needs input element!");
		}
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

			String name = possibleAssign.getAttributeValue("tag");
			if (name == null) {
				throw new ConfigurationException("ImageMetadata rule assign element needs tag attribute!");
			}
			RuleId id = new RuleId(possibleAssign.getAttributeValue("id"));
			if (id == null) {
				throw new ConfigurationException("ImageMetadata rule assign element needs id attribute!");
			}

			rule.addAssign(name, id);
		}

		return rules;
	}

}
