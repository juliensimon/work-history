package fr.netattitude.cappuccino;

import java.io.*;

abstract class File implements ComponentConst {
	protected 	static ExportJarReader		exports;

	private 	String 						_name;
	private 	byte						_type;

	public File(String name, ExportJarReader ejr, byte type) {
		_name 		= name;
		_type 		= type;
		exports		= ejr;
	}

	public static byte getFileType(String fileName) throws java.io.IOException {
		byte 			type;
		int 			magic;
		DataInputStream dis
			= new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)));

		dis.mark(16);
		// Is this a JAR file ?
		magic = dis.readInt();
		if (magic == 0x504B0304) {
			dis.close();
			return JAR_FILE;
		}

		dis.reset();
		dis.skip(3);
		// Is this a TLV file ?
		magic = dis.readInt();
		if (magic == 0xDECAFFED) {
			dis.close();
			return TLV_FILE;
		}

		dis.reset();
		dis.mark(5);
		// Is this an EXP file ?
		magic = dis.readInt();
		if (magic == 0x00FACADE) {
			dis.close();
			return EXP_FILE;
		}

		dis.close();
		return UNK_FILE;
	}

	public static File getInstance(String fileName, ExportJarReader ejr) throws java.io.IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName));
		byte type = getFileType(fileName);
		switch (type) {
			case JAR_FILE:
				return new JarCapFile(fileName, ejr, bis);

			case TLV_FILE:
				return new TlvCapFile(fileName, ejr, bis);

			case EXP_FILE:
				return new ExportFile(fileName, ejr, new DataInputStream(bis));

			default:
				System.out.println("File type is unknown");
				System.exit(-1);
				return null;
		}
	}

	public void dump() {
		System.out.println("File name:\t" + _name);
		System.out.println("File type:\t" + fileTypes[_type]);
		System.out.println();
	}

	public void show(boolean all) {
		System.out.println("File name:\t" + _name);
		System.out.println("File type:\t" + fileTypes[_type]);
		System.out.println();
	}

	public static void readBytes(DataInputStream dis, byte[] bArray)
		throws java.io.IOException {
		for (int i=0; i<bArray.length; i++) {
			bArray[i] = dis.readByte();
		}
	}
}