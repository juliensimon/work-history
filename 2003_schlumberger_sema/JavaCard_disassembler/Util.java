package fr.netattitude.cappuccino;

class Util implements ComponentConst {

	private final static String hex[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
		"A", "B", "C", "D", "E", "F" };

	private static String _toHex(byte b) {
		short s  = (short)(0x00FF & b);
		return (hex[s/16] + hex[s%16]);
	}
	public static String toHex(byte b) {
		return "0x" + _toHex(b);
	}

	public static String _toHex(short s) {
		byte b1 = (byte)(s >> 8);
		byte b2 = (byte)(s & 0x00FF);
		return (_toHex(b1) + _toHex(b2));
	}

	public static String toHex(short s) {
		return "0x" + _toHex(s);
	}

	public static String toHex(int i) {
		short s1 = (short)(i >> 16);
		short s2 = (short)(i & 0x0000FFFF);
		return "0x" + _toHex(s1) + _toHex(s2);
	}

	public static String toHex(byte[] bArray, int offset, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i=offset; i<offset+length; i++) {
			sb.append(toHex(bArray[i]));
			if (i != (offset+length-1)) {
				sb.append(":");
			}
		}
		return new String(sb);
	}

	public static void toHexLines(byte[] bArray, short offset, short length, int level) {
		for (short i=offset; i<offset+length; i++) {
			if ((i % 16) == 0) {
				System.out.println();
				printTab(Util.toHex(i) + ": ", level);
			}
			System.out.print(Util._toHex(bArray[i]) + " ");
		}
		System.out.println();
		System.out.println();
	}

	public static String toHex(short[] bArray, int offset, int length) {
		StringBuffer sb = new StringBuffer();
		for (int i=offset; i<offset+length; i++) {
			sb.append(toHex(bArray[i]));
			if (i != (offset+length-1)) {
				sb.append(":");
			}
		}
		return new String(sb);
	}

	public static short compareUnsigned(byte a, byte b) {
		short c = (short)(a & 0x00FF);;
		short d = (short)(b & 0x00FF);
		return (short)(c-d);
	}

	public static void warn(String s) {
		if (!Cappuccino._flag_silent) {
			System.out.println("Cappuccino warning: " + s);
		}
	}

	public static void err(String s) {
		System.out.println("Cappuccino error: " + s);
		System.exit(-1);
	}

	public static void printlnTab(String s, int level) {
		printTab(s, level);
		System.out.println();

	}

	public static void printTab(String s, int level) {
		for (byte i=0; i<level; i++) {
			System.out.print("\t");
		}
		System.out.print(s);

	}

	public static short makeShort(byte hi, byte lo) {
		return (short)((hi<<8) + (lo&0x00FF));
	}
}