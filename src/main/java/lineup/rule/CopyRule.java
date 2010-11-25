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

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.google.common.collect.ImmutableSet;

/**
 * Copy {@link Rule}.
 * 
 */
public class CopyRule extends AbstractRule {

	private Logger logger = Logger.getLogger(CopyRule.class);

	private RuleId from;

	private RuleId to;

	/**
	 * @return the from
	 */
	public RuleId getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(RuleId from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public RuleId getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(RuleId to) {
		this.to = to;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvd.rule.Rule#execute(java.util.Map)
	 */
	public RuleOutput execute(Map<RuleId, String> inputs) {

		File fromFile = new File(inputs.get(from));
		File toFile = new File(inputs.get(to));

		logger.debug("Copying file from \""
				+ fromFile.getAbsolutePath() + "\" to \""
				+ toFile.getAbsolutePath() + "\"");
		
		try {
			FileUtils.copyFile(fromFile, toFile);
		} catch (IOException ex) {
			logger.error("I/O error copying file from \""
					+ fromFile.getAbsolutePath() + "\" to \""
					+ toFile.getAbsolutePath() + "\"!");
			return null;
		}

		RuleOutput output = new RuleOutput(toFile.getAbsolutePath());
		return output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvd.rule.Rule#getRequiredParameters()
	 */
	public Set<RuleId> getRequiredParameters() {
		Set<RuleId> requiredParameters = new HashSet<RuleId>();
		requiredParameters.add(from);
		requiredParameters.add(to);
		return ImmutableSet.copyOf(requiredParameters);
	}

}
