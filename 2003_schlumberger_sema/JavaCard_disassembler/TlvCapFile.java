package fr.netattitude.cappuccino;

import java.io.*;
import java.util.zip.*;
import java.util.jar.*;

class TlvCapFile extends CapFile {

	public TlvCapFile(String name, ExportJarReader ejr, BufferedInputStream bis) {
		super(name, ejr, TLV_FILE);
		try {
			DataInputStream 	dis			= new DataInputStream(bis);
			byte				compType;

			Class[] argsClass	= new Class[1];
			argsClass[0]		= Class.forName("java.io.DataInputStream");
			Object argsObjs[] 	= new Object[1];
			argsObjs[0] 		= dis;
			Class				compClass;

			while (dis.available() != 0) {
				dis.mark(2);
				compType = dis.readByte();
				dis.reset();
				// Create new component
				compClass
					= Class.forName("fr.netattitude.cappuccino."+ componentClassNames[compType-1]);
				_components[compType-1]
					= (Component)compClass.getConstructor(argsClass).newInstance(argsObjs);
			}
			postProcess();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void dump() {
		super.dump();
		System.out.println();
		dumpComponents();
	}

	public static void toJar(String in, String out) throws java.io.IOException {
		DataInputStream		dis =
			new DataInputStream(new BufferedInputStream(new FileInputStream(in)));
		JarOutputStream 	jos	=
			new JarOutputStream(new BufferedOutputStream(new FileOutputStream(out)));

		while (dis.available() != 0) {
			dis.mark(4);
			byte 	tag 	= dis.readByte();
			short	size 	= dis.readShort();
			dis.reset();

			byte 	data[] = new byte[size+3];
			File.readBytes(dis, data);

			jos.putNextEntry(new ZipEntry("cappuccino/javacard/"+componentFiles[tag-1]));

			System.out.println("Writing component " + componentNames[tag-1]
						+ " (" + size + " bytes)");

			jos.write(data, 0, data.length);
		}
		jos.close();
		dis.close();
		System.out.println("Done.");
	}
}