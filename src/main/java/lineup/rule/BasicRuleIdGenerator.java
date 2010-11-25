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

import com.google.inject.Singleton;

/**
 * {@link RuleIdGenerator} implementation.
 *
 */
@Singleton
public class BasicRuleIdGenerator implements RuleIdGenerator {

	private String prefix;
	
	private int counter;
	
	public BasicRuleIdGenerator() {
		this.prefix = "__generated_id_";
		this.counter = 1;
	}
	
	/* (non-Javadoc)
	 * @see mvd.IDGenerator#nextId()
	 */
	public RuleId nextId() {
		return new RuleId(prefix + counter++);
	}

}
