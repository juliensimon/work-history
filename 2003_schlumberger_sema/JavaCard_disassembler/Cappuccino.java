package fr.netattitude.cappuccino;

import java.io.*;
import java.util.jar.*;

public class Cappuccino implements ComponentConst {

	private final static String		_version	= "Cappuccino v0.2, 27-Aug-2001";

	public static boolean			_flag_all;
	public static boolean			_flag_silent;

	private static void usage() {
		System.out.println("Usage: java <properties> -jar cappucino.jar <options>\n");

		System.out.println("Options:");
		System.out.println("\t-aid");
			System.out.println("\t\tList well-known AIDs");
		System.out.println("\t-dump <input file>");
			System.out.println("\t\tDump file (CAP or EXP)");
		System.out.println("\t-show <input file> -exp <export JAR file>");
			System.out.println("\t\tShow file (CAP or EXP)");
		System.out.println("\t-jar <input file> <output file>");
			System.out.println("\t\tConvert a TLV CAP file to JAR");
		System.out.println("\t-tlv <input file> <output file>");
			System.out.println("\t\tConvert a JAR CAP file to TLV");
		System.out.println("\t-version");
			System.out.println("\t\tPrint version and exit");

		System.out.println("Properties:");
		System.out.println("\t-Dall");
			System.out.println("\t\tDon't filter inherited methods");
		System.out.println("\t-Dsilent");
			System.out.println("\t\tDon't show warnings");

		System.exit(-1);
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			usage();
		}

		String s = System.getProperty("silent");
		if (s != null) {
			_flag_silent = true;
		}
		s = System.getProperty("all");
		if (s != null) {
			_flag_all = true;
		}

		System.out.println();

		try {

			// -version
			if (args[0].compareTo("-version") == 0) {
				if (args.length == 1) {
					System.out.println(_version);
					System.out.println("Copyright Netattitude, 2001");
					System.out.println("E-mail:\tjsimon@netattitude.fr");
					System.out.println("WWW:\thttp://www.netattitude.fr");
					System.exit(0);
				}
			}

			// -aid
			if (args[0].compareTo("-aid") == 0) {
				if (args.length == 1) {
					for (int i=0; i<wellKnownNames.length; i++) {
						System.out.println(wellKnownNames[i]);
						System.out.println(Util.toHex(wellKnownAids[i], 0, wellKnownAids[i].length));
						System.out.println();
					}
					System.exit(0);
				}
			}

			// -dump
			if (args[0].compareTo("-dump") == 0) {
				if (args.length == 2) {
					File.getInstance(args[1], null).dump();
					System.exit(0);
				}
			}

			// -show
			if (args[0].compareTo("-show") == 0) {
				if (args.length == 4) {
					if (args[2].compareTo("-exp") == 0) {
						// Read export files from JAR file
						ExportJarReader ejr = new ExportJarReader(args[3]);
						File.getInstance(args[1], ejr).show(_flag_all);
						System.exit(0);
					}
				}
			}

			// -tlv
			if (args[0].compareTo("-tlv") == 0) {
				if (args.length == 3) {
					switch (File.getFileType(args[1])) {
						case TLV_FILE:
							Util.err(args[1] + " is already a TLV file");
							System.exit(-1);
							break;

						case JAR_FILE:
							JarCapFile.toTlv(args[1], args[2]);
							System.exit(0);
							break;

						default:
							Util.err(args[1] + " has unknown type");
							System.exit(-1);
							break;
					}
				}
			}

			// -jar
			if (args[0].compareTo("-jar") == 0) {
				if (args.length == 3) {
					switch (File.getFileType(args[1])) {
						case JAR_FILE:
							Util.err(args[1] + " is already a JAR file");
							System.exit(-1);
							break;

						case TLV_FILE:
							TlvCapFile.toJar(args[1], args[2]);
							System.exit(0);
							break;

						default:
							Util.err(args[1] + " has unknown type");
							System.exit(-1);
							break;
					}
				}
			}

			// default
			usage();
			System.exit(0);
	}
	catch (FileNotFoundException e) {
		Util.err(e.getMessage());
	}
	catch (Exception e) {
		e.printStackTrace();
		Util.err(e.getMessage());
	}
	}
}

