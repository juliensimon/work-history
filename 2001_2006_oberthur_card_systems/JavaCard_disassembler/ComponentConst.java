package fr.netattitude.cappuccino;

interface ComponentConst {

	// File types
	public final static byte		JAR_FILE	= (byte)0;
	public final static byte		TLV_FILE	= (byte)1;
	public final static byte		EXP_FILE	= (byte)2;
	public final static byte		UNK_FILE	= (byte)3;

	public final static String		fileTypes[] = {
		"JAR", "TLV", "EXP", "Unknown"
	};

	public static final byte COMP_IMPORT	= (byte)4;
	public static final byte COMP_CONST		= (byte)5;
	public static final byte COMP_CLASS		= (byte)6;
	public static final byte COMP_METHOD	= (byte)7;
	public static final byte COMP_DESC		= (byte)11;
	public static final byte COMPONENT_MAX 	= (byte)11;

	// Component names
	public final static String[] componentNames = {
		"Header", "Directory", "Applet", "Import", "Constant Pool", "Class", "Method",
		"Static Field", "Reference Location", "Export", "Descriptor"
	};

	// Component class names
	public final static String[] componentClassNames = {
		"HeaderComponent", "DirectoryComponent", "AppletComponent", "ImportComponent",
		"ConstantPoolComponent", "ClassComponent", "MethodComponent", "StaticFieldComponent",
		"RefLocationComponent", "ExportComponent", "DescriptorComponent"
	};

	// Component files
	public final static String[] componentFiles = {
		"Header.cap", "Directory.cap", "Applet.cap", "Import.cap", "ConstantPool.cap",
		"Class.cap", "Method.cap", "StaticField.cap", "RefLocation.cap", "Export.cap",
		"Descriptor.cap"
	};

	public final static String[] componentFilesRefOrder = {
		"Header.cap", "Directory.cap", "Import.cap", "Applet.cap",  "Class.cap", "Method.cap",
		"StaticField.cap", "Export.cap", "ConstantPool.cap", "RefLocation.cap",
		"Descriptor.cap"
	};

	// Field/Method/Class qualifiers
	public final static byte ACC_PUBLIC		= (byte)0x01;
	public final static byte ACC_PRIVATE	= (byte)0x02;
	public final static byte ACC_PROTECTED	= (byte)0x04;
	public final static byte ACC_STATIC		= (byte)0x08;
	public final static byte ACC_FINAL		= (byte)0x10;
	public final static byte ACC_INTERFACE	= (byte)0x40;
	public final static byte ACC_ABSTRACT	= (byte)0x80;
	public final static byte ACC_INIT		= (byte)0x80;

	// Method flags
	public final static byte M_ACC_ABSTRACT	= (byte)0x04;
	public final static byte M_ACC_EXTENDED	= (byte)0x08;

	// Class & interface flags
	public final static byte C_ACC_INTERFACE	= (byte)0x04;
	public final static byte C_ACC_SHAREABLE	= (byte)0x08;

	// Data types
	public final static byte PADDING_TYPE	= (byte)0;
	public final static byte VOID_TYPE		= (byte)1;
	public final static byte BOOLEAN_TYPE	= (byte)2;
	public final static byte BYTE_TYPE		= (byte)3;
	public final static byte SHORT_TYPE		= (byte)4;
	public final static byte INT_TYPE		= (byte)5;
	public final static byte REF_TYPE		= (byte)6;
	public final static byte BOOLEAN_A_TYPE	= (byte)10;
	public final static byte BYTE_A_TYPE	= (byte)11;
	public final static byte SHORT_A_TYPE	= (byte)12;
	public final static byte INT_A_TYPE		= (byte)13;
	public final static byte REF_A_TYPE		= (byte)14;

	public final static String typeNames[] = { "padding", "void",
		"boolean", "byte", "short", "int", "Object", "", "", "",
		"boolean[]", "byte[]", "short[]", "int[]", "Object[]"
	};

	// Package names & AIDs
	public final static byte 	wellKnownAids[][] = {
		{ (byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x62,
			(byte)0x00, (byte)0x01 },
		{ (byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x62,
			(byte)0x01, (byte)0x01 },
		{ (byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x62,
			(byte)0x01, (byte)0x02 },
		{ (byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x62,
			(byte)0x02, (byte)0x01 },
		{ (byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x03,
			(byte)0x00, (byte)0x00 },
		{ (byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09,
			(byte)0x00, (byte)0x03, (byte)0xFF, (byte)0xFF, (byte)0xFF,
			(byte)0xFF, (byte)0x89, (byte)0x10, (byte)0x70, (byte)0x00, (byte)0x01 },
		{ (byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09,
			(byte)0x00, (byte)0x03, (byte)0xFF, (byte)0xFF, (byte)0xFF,
			(byte)0xFF, (byte)0x89, (byte)0x10, (byte)0x70, (byte)0x00, (byte)0x02 }
	};

	public final static String 	wellKnownNames[] = {
			"java.lang",
			"javacard.framework",
			"javacard.security",
			"javacardx.crypto",
			"visa.openplatform",
			"sim.access",
			"sim.toolkit"
	};
}
