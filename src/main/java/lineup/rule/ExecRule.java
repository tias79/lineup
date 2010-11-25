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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Exec {@link Rule}.
 * 
 */
public class ExecRule extends AbstractRule {

	private Logger logger = Logger.getLogger(ExecRule.class);

	private RuleId input;

	private RuleId stdout = null;

	private RuleId stderr = null;

	private RuleId charset;

	/**
	 * @return the stdout
	 */
	public RuleId getStdout() {
		return stdout;
	}

	/**
	 * @param stdout
	 *            the stdout to set
	 */
	public void setStdout(RuleId stdout) {
		this.stdout = stdout;
	}

	/**
	 * @return the stderr
	 */
	public RuleId getStderr() {
		return stderr;
	}

	/**
	 * @param stderr
	 *            the stderr to set
	 */
	public void setStderr(RuleId stderr) {
		this.stderr = stderr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lineup.rule.Rule#execute(java.util.Map)
	 */
	public RuleOutput execute(Map<RuleId, String> inputs) {

		String cmd = inputs.get(getInput());

		logger.debug("Executing command: " + cmd);

		Process process;
		try {
			process = Runtime.getRuntime().exec(cmd);
		} catch (IOException ex) {
			logger.error("Failed to execute command: " + cmd);

			return new RuleOutput(null);
		}

		Charset charsetDecoder = null;
		try {
			charsetDecoder = Charset.forName(inputs.get(charset));
		} catch (IllegalCharsetNameException ex) {
			logger.fatal("Illegal charset name!", ex);
			throw new RuntimeException(
					"There was a configuration error detected at run time!");
		}

		Map<RuleId, String> variables = new HashMap<RuleId, String>();

		if (stdout != null) {
			try {
				InputStream stdinInputStream = process.getInputStream();
				variables.put(stdout, convertInputStreamToString(
						stdinInputStream, charsetDecoder));
			} catch (IOException ex) {
				logger.error("Error getting stdin from process: " + cmd);
			}
		}

		if (stderr != null) {
			try {
				InputStream stderrInputStream = process.getErrorStream();
				variables.put(stderr, convertInputStreamToString(
						stderrInputStream, charsetDecoder));
			} catch (IOException ex) {
				logger.error("Error getting stderr from process: " + cmd);
			}
		}

		RuleOutput output = new RuleOutput(new Integer(process.exitValue())
				.toString(), ImmutableMap.copyOf(variables));
		return output;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lineup.rule.Rule#getRequiredParameters()
	 */
	public Set<RuleId> getRequiredParameters() {
		Set<RuleId> requiredParameters = new HashSet<RuleId>();
		requiredParameters.add(input);
		requiredParameters.add(charset);
		return ImmutableSet.copyOf(requiredParameters);
	}

	public void setInput(RuleId input) {
		this.input = input;
	}

	public RuleId getInput() {
		return input;
	}

	private String convertInputStreamToString(InputStream inputStream,
			Charset charsetDecoder) throws IOException {

		if (inputStream != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(
						inputStream, charsetDecoder));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				inputStream.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	public void setCharset(RuleId charset) {
		this.charset = charset;
	}

	public RuleId getCharset() {
		return charset;
	}
}
