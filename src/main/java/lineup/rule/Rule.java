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

import java.util.Map;
import java.util.Set;

/**
 * 
 *
 */
public interface Rule {

	/**
	 * Get required ID in parameters.
	 * 
	 * @return
	 */
	Set<RuleId> getRequiredParameters();

	/**
	 * Execute rule with parameters. All required parameters need to be
	 * included.
	 * 
	 * @param paramters In parameters.
	 * @return Out parameters.
	 */
	RuleOutput execute(Map<RuleId, String> parameters);

	/**
	 * Set rule ID.
	 * 
	 * @param id
	 *            ID.
	 */
	public void setId(RuleId id);

	/**
	 * Get rule ID.
	 * 
	 * @return ID.
	 */
	public RuleId getId();
}
