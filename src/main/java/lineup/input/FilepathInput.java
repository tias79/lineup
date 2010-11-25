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
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * Filepath {@link Input}.
 * 
 */
public class FilepathInput implements Input {

	private Queue<File> fileQueue = new LinkedList<File>();
	private Queue<File> dirQueue = new LinkedList<File>();

	private String name;

	/**
	 * @param file
	 */
	public void addPath(File file) {
		if (file.isDirectory()) {
			dirQueue.add(file);
		} else {
			fileQueue.add(file);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvd.Input#readLine()
	 */
	public String readLine() throws IOException {

		if (!fileQueue.isEmpty()) {
			File tmpFile = fileQueue.poll();
			return tmpFile.getAbsolutePath();
		}

		if (!dirQueue.isEmpty()) {
			File tmpDir = dirQueue.poll();

			String[] list = tmpDir.list();
			for (String subFilename : list) {
				if (subFilename.equals(".") || subFilename.equals("..")) {
					continue;
				}
				File tmpSubFile = createFile(tmpDir, subFilename);
				addPath(tmpSubFile);
			}

			System.out.println(tmpDir.getAbsolutePath());
			return tmpDir.getAbsolutePath();
		}

		return null;

	}

	/**
	 * @param file
	 * @param name
	 * @return
	 */
	protected File createFile(File file, String name) {
		return new File(file, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvd.input.Input#getName()
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public Set<File> getPaths() {
		Set<File> paths = new HashSet<File>();
		paths.addAll(dirQueue);
		paths.addAll(fileQueue);

		return ImmutableSet.copyOf(paths);
	}
}
