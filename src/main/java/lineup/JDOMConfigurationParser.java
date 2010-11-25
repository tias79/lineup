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
package lineup;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import lineup.input.Input;
import lineup.input.InputConfigurationParser;
import lineup.rule.Rule;
import lineup.rule.RuleConfigurationParser;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.google.inject.Inject;

/**
 * {@link Configuration} parser implementation using JDOM.
 * 
 */
public class JDOMConfigurationParser implements ConfigurationParser {

	private Set<InputConfigurationParser> inputConfigurationParsers;

	private Set<RuleConfigurationParser> ruleConfigurationParsers;

	@Inject
	public JDOMConfigurationParser(
			Set<InputConfigurationParser> inputConfigurationParsers,
			Set<RuleConfigurationParser> ruleConfigurationParsers) {
		this.inputConfigurationParsers = inputConfigurationParsers;
		this.ruleConfigurationParsers = ruleConfigurationParsers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see filerenamer.ConfigurationParser#parseConfiguration(java.lang.String)
	 */
	public Configuration parseConfiguration(InputStream configurationStream)
			throws IOException, ConfigurationException {

		Configuration configuration = new Configuration();

		SAXBuilder parser = new SAXBuilder();
		try {
			Document doc = parser.build(configurationStream);

			XPath inputXPath = XPath.newInstance("//input/*");
			List<?> inputs = inputXPath.selectNodes(doc);

			for (Object obj : inputs) {
				if (obj instanceof Element) {
					Element input = (Element) obj;

					for (InputConfigurationParser inputParser : inputConfigurationParsers) {
						Input tmpInput = inputParser.parseElement(input);
						if (tmpInput != null) {
							configuration.addInput(tmpInput);
							break;
						}
					}
				}
			}

			XPath rulesXPath = XPath.newInstance("//rules/*");
			List<?> rules = rulesXPath.selectNodes(doc);

			for (Object obj : rules) {
				if (obj instanceof Element) {
					Element rule = (Element) obj;

					for (RuleConfigurationParser ruleParser : ruleConfigurationParsers) {
						List<Rule> tmpRules = ruleParser.parseElement(rule);
						if (tmpRules != null) {
							for (Rule tmpRule : tmpRules) {
								configuration.addRule(tmpRule);
							}
							break;
						}
					}
				}
			}

		} catch (JDOMException ex) {
			throw new ConfigurationException(
					"Error parsing XML in configuration file!", ex);
		}

		return configuration;
	}
}
