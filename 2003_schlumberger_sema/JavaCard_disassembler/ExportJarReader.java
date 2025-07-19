package fr.netattitude.cappuccino;

import java.io.*;
import java.util.jar.*;

class ExportJarReader {
	private ExportFile		_files[];

	public ExportJarReader(String jarFile) throws java.io.IOException {
		JarEntry 	e;
		String		fullName, fileName, packageName;
		int			fileCount = 0;

		// Count files and allocate file array
		JarInputStream jis = new JarInputStream(new BufferedInputStream(new FileInputStream(jarFile)));
		while ((e = jis.getNextJarEntry()) != null) {
			fullName		= e.getName();
			fileName 		= fullName.substring(fullName.lastIndexOf("/")+1);
			if (fileName.endsWith(".exp")) {
				//System.out.println(fileName);
				fileCount ++;
			}
		}
		_files = new ExportFile[fileCount];
		jis.close();

		// Create each file

		jis = new JarInputStream(new BufferedInputStream(new FileInputStream(jarFile)));
		DataInputStream dis = new DataInputStream(jis);

		fileCount = 0;
		while ((e = jis.getNextJarEntry()) != null) {
			fullName 		= e.getName();
			fileName 		= fullName.substring(fullName.lastIndexOf("/")+1);
			if (fileName.endsWith(".exp")) {
				_files[fileCount++] = new ExportFile(fullName, this, dis);
			}
		}
	}

	public int getFileCount() {
		return _files.length;
	}

	public ExportFile getFile(int i) {
		return _files[i];
	}

	public ExportFile findPackage(String s) {
		for (int i=0; i<_files.length; i++) {
			//System.out.println("EJR looking in " + _files[i].getPackageName());
			if (_files[i].getPackageName().compareTo(s) == 0) {
				//System.out.println("EJR found!");
				return _files[i];
			}
		}
		return null;
	}
}