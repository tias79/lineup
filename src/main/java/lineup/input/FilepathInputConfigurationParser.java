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
package lineup.input;

import java.io.File;
import java.util.List;

import org.jdom.Element;

/**
 * Filepath {@link InputConfigurationParser}.
 * 
 */
public class FilepathInputConfigurationParser implements
		InputConfigurationParser {

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvd.InputConfigurationParser#parseNode(org.w3c.dom.Node)
	 */
	@SuppressWarnings("unchecked")
	public Input parseElement(Element elem) {
		
		if (!elem.getName().equals("filepath")) {
			return null;
		}
		
		FilepathInput input = new FilepathInput();
		input.setName(elem.getAttribute("name").getValue());
		List<Element> pathElements = (List<Element>)elem.getChildren("path");
		for (Element inputElem : pathElements) {
			File tmpFile = new File(inputElem.getText());
			input.addPath(tmpFile);
		}
		
		return input;
	}
}
