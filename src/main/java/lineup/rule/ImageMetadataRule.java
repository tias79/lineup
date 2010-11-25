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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * Image metadata {@link Rule}.
 * 
 */
public class ImageMetadataRule extends AbstractRule {

	private Logger logger = Logger.getLogger(ImageMetadataRule.class);

	private RuleId input;

	private Map<String, RuleId> assigns = new HashMap<String, RuleId>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvd.Rule#getRequiredParameters()
	 */
	public Set<RuleId> getRequiredParameters() {
		Set<RuleId> required = new HashSet<RuleId>();
		required.add(input);

		return ImmutableSet.copyOf(required);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvd.Rule#execute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public RuleOutput execute(Map<RuleId, String> parameters) {
		Map<RuleId, String> variables = new HashMap<RuleId, String>();

		String filePath = parameters.get(input);

		logger.debug("Open file looking for image metadata: " + filePath);

		File imageFile = new File(filePath);
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(imageFile);

			Iterator directories = metadata.getDirectoryIterator();
			while (directories.hasNext()) {
				Directory directory = (Directory) directories.next();
				Iterator fileTags = directory.getTagIterator();
				while (fileTags.hasNext()) {
					Tag tag = (Tag) fileTags.next();

					if (assigns.containsKey(tag.getTagName())) {
						try {
							logger.trace(tag.getTagName() + " => " + tag.getDescription());
							variables.put(assigns.get(tag.getTagName()), tag
									.getDescription());
						} catch (MetadataException ex) {
							variables.put(assigns.get(tag.getTagName()), "");
							logger.error(
									"Failed to extract image metadata from file: "
											+ filePath, ex);
						}
					}
				}
			}

		} catch (ImageProcessingException e) {
			return new RuleOutput(null, null);
		}

		return new RuleOutput(filePath, ImmutableMap.copyOf(variables));
	}

	/**
	 * @return
	 */
	public Map<String, RuleId> getAssigns() {
		return Collections.unmodifiableMap(assigns);
	}

	/**
	 * @param tagName
	 * @param id
	 */
	public void addAssign(String tagName, RuleId id) {
		assigns.put(tagName, id);
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
		return this.input;
	}
}
