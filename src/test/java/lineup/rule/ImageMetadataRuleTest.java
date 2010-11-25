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

import static org.junit.Assert.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import lineup.rule.ImageMetadataRule;
import lineup.rule.RuleId;
import lineup.rule.RuleOutput;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for {@link ImageMetadataRule}.
 */
public class ImageMetadataRuleTest {

	private ImageMetadataRule rule;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		rule = new ImageMetadataRule();
	}

	/**
	 * Test method for {@link lineup.rule.ImageMetadataRule#execute(java.util.Map)}.
	 */
	@Test
	public void testExecute() {
		final RuleId input = new RuleId("input");

		rule.setInput(input);
		rule.addAssign("Make", new RuleId("M"));

		URL url = ClassLoader.getSystemResource("rule/imageMetadataRule1.jpg");
		
		Map<RuleId, String> inputs = new HashMap<RuleId, String>();
		inputs.put(input, url.getFile());
					
		RuleOutput output = rule.execute(inputs);
		
		assertEquals("FUJIFILM", output.getVariables().get(new RuleId("M")));
	}

}
