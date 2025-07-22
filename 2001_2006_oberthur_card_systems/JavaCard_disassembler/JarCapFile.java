package fr.netattitude.cappuccino;

import java.io.*;
import java.util.jar.*;
import java.lang.reflect.*;

class JarCapFile extends CapFile {

	private static String		_packageName;

	public JarCapFile(String name, ExportJarReader ejr, BufferedInputStream bis) {
		super(name, ejr, JAR_FILE);

		try {
			JarInputStream 		jis 	= new JarInputStream(bis);
			DataInputStream 	dis		= new DataInputStream(jis);
			JarEntry 			e;
			String				fullName, fileName;

			Class[] argsClass	= new Class[1];
			argsClass[0]		= Class.forName("java.io.DataInputStream");
			Object argsObjs[] 	= new Object[1];
			argsObjs[0] 		= dis;
			Class				compClass;

			boolean				done = false;

			while ((e = jis.getNextJarEntry()) != null) {
				fullName 		= e.getName();
				fileName 		= fullName.substring(fullName.lastIndexOf("/")+1);

				// Store package name
				if (!done) {
					_packageName 	= fullName.substring(0, fullName.lastIndexOf("javacard/")-1);
					_packageName	= _packageName.replace('/','.');
					done 			= true;
				}

				// Build components
				for (int i=0;i<componentNames.length;i++) {
					if (fileName.compareToIgnoreCase(componentFiles[i]) == 0) {
						//System.out.println("Building " + componentClassNames[i]);
						compClass
							= Class.forName("fr.netattitude.cappuccino."+ componentClassNames[i]);
						_components[i]
							= (Component)compClass.getConstructor(argsClass).newInstance(argsObjs);
						break;
					}
				}
			}
			postProcess();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dump() {
		super.dump();
		System.out.println("Package name: " + _packageName + "\n");
		dumpComponents();
	}

	public static void toTlv(String in, String out) throws java.io.IOException {
		JarInputStream 		jis;
		DataInputStream 	dis;
		DataOutputStream	dos = new DataOutputStream(
			new BufferedOutputStream(new FileOutputStream(out)));

		JarEntry 			e;
		String				fullName, fileName;

		for (int i=0;i<COMPONENT_MAX;i++) {
			jis = new JarInputStream (new BufferedInputStream(new FileInputStream(in)));
			dis = new DataInputStream(jis);
			while ((e = jis.getNextJarEntry()) != null) {
				fullName 		= e.getName();
				fileName 		= fullName.substring(fullName.lastIndexOf("/")+1);
				if (fileName.compareToIgnoreCase(componentFilesRefOrder[i]) == 0) {
					byte tag 	= dis.readByte();
					short size	= dis.readShort();
					byte[] raw 	= new byte[size];

					//System.out.println("Writing component " + componentNames[tag-1]
					//	+ " (" + size + " bytes)");

					dos.writeByte(tag);
					dos.writeShort(size);

					if (size != 0) {
						File.readBytes(dis, raw);
						dos.write(raw, 0, raw.length);
					}
				}
			}
			dis.close();
		}
		dos.close();
		System.out.println("Done.");
	}
}