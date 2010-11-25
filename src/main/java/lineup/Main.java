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
import java.util.Set;

import lineup.input.FilepathInputModule;
import lineup.input.Input;
import lineup.input.StdinInputModule;
import lineup.rule.ConcatRuleModule;
import lineup.rule.ImageMetadataRuleModule;
import lineup.rule.LabelRuleModule;
import lineup.rule.MatchRuleModule;
import lineup.rule.PrintRuleModule;
import lineup.rule.RemoteXmlRuleModule;
import lineup.rule.RuleService;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Main class.
 * 
 */
public class Main {

	private static Logger logger = Logger.getLogger(Main.class);

	/**
	 * @param args
	 *            Command line parameters.
	 */
	public static void main(String[] args) {

		// create the command line parser
		CommandLineParser parser = new PosixParser();

		// create the Options
		Options options = new Options();
		options.addOption(null, "dry-run", false,
				"Complete run without actually moving any files.");
		options.addOption("h", "help", false, "Print this message.");

		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			// validate that block-size has been set
			if (line.hasOption("dry-run")) {
			}

			if (line.hasOption("help") || line.getArgs().length == 0) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("mvd", options);
			}

			parseConfigurations(line.getArgs());
		} catch (ParseException exp) {
			System.out.println("Unexpected exception:" + exp.getMessage());
		}

	}

	private static void parseConfigurations(String[] configFiles) {

		Injector injector = Guice.createInjector(new ConfigurationModule(
				configFiles), new RuleModule(), new LabelRuleModule(),
				new PrintRuleModule(), new ImageMetadataRuleModule(),
				new ConcatRuleModule(), new MatchRuleModule(),
				new RemoteXmlRuleModule(), new FilepathInputModule(),
				new StdinInputModule());

		Set<Configuration> configurations = injector.getProvider(
				ConfigurationProvider.class).get().get();

		RuleService ruleService = injector.getInstance(RuleService.class);

		for (Configuration config : configurations) {

			ruleService.setRules(config.getRules());

			for (Input input : config.getInputs()) {
				try {
					String line = input.readLine();
					while (line != null) {
						ruleService.applyRules(line);
						line = input.readLine();
					}
				} catch (IOException ex) {
					logger
							.error("Failed to read input: " + input.getName(),
									ex);
				}
			}
		}
	}
}
