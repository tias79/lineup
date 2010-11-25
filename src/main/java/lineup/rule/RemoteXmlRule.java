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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * 
 * 
 */
public class RemoteXmlRule extends AbstractRule {

	private Logger logger = Logger.getLogger(RemoteXmlRule.class);

	private Map<RuleId, String> assigns = new HashMap<RuleId, String>();

	private RuleId input;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvd.Rule#execute(java.util.Map)
	 */
	public RuleOutput execute(Map<RuleId, String> inputs) {

		InputStream in;
		Document doc = null;

		String url = inputs.get(input);

		logger.debug("Open remote XML URL: " + url);

		try {
			in = new URL(url).openStream();

			SAXBuilder parser = new SAXBuilder();
			doc = parser.build(in);
		} catch (IOException ex) {
			logger.error("Failed to open stream to remote URL.", ex);
		} catch (JDOMException ex) {
			logger.error("Failed to parse XML at remote URL.", ex);
		}

		if (doc == null) {
			return new RuleOutput(null);
		}

		Map<RuleId, String> variables = new HashMap<RuleId, String>();
		for (RuleId id : assigns.keySet()) {

			XPath sourceXPath;
			try {
				sourceXPath = XPath.newInstance(assigns.get(id));

				Object source = sourceXPath.selectSingleNode(doc);

				if (source instanceof Element) {
					variables.put(id, ((Element) source).getText());
				}

			} catch (JDOMException ex) {
				logger.error("Error parsing XML at remote URL.", ex);
			}
		}

		return new RuleOutput(inputs.get(input), ImmutableMap.copyOf(variables));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvd.Rule#getRequiredParameters()
	 */
	public Set<RuleId> getRequiredParameters() {
		Set<RuleId> requiredParameters = new HashSet<RuleId>();
		requiredParameters.add(input);
		return ImmutableSet.copyOf(requiredParameters);
	}

	/**
	 * @param id
	 * @param xpath
	 */
	public void addAssign(RuleId id, String xpath) {
		assigns.put(id, xpath);
	}

	/**
	 * @param input
	 */
	public void setInput(RuleId input) {
		this.input = input;
	}

	/**
	 * @return
	 */
	public RuleId getInput() {
		return input;
	}
}
