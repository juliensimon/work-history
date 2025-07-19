package fr.netattitude.cappuccino;

import java.io.*;

class HeaderComponent extends Component {

	private static byte 	ACC_INT 	= (byte)0x01;
	private static byte 	ACC_EXPORT 	= (byte)0x02;
	private static byte 	ACC_APPLET 	= (byte)0x04;

	// Members
	private int 			_magic;
	private byte 			_minor_version;
	private byte 			_major_version;
	private byte 			_flags;
	private PackageInfo 	_package_info;

	public HeaderComponent(DataInputStream dis) throws java.io.IOException {
		super(dis);
		_magic 			= dis.readInt();
		if (_magic != 0xDECAFFED) {
			Util.err("file isn't a CAP file");
		}
		_minor_version 	= dis.readByte();
		_major_version 	= dis.readByte();
		_flags 		= dis.readByte();
		_package_info	= new PackageInfo(dis);
	}

	public void dump() {
		super.dump();
		System.out.println("Magic: "
			+ Util.toHex(_magic));
		System.out.println("Minor version: "
			+ _minor_version);
		System.out.println("Major version: "
			+ _major_version);
		System.out.println("Flags: "
			+ Util.toHex(_flags) + getFlags());
		_package_info.dump(0);
		System.out.println();
	}

	private String getFlags() {
		String sb = new String(" ( ");
		if ((_flags & ACC_INT)!=0) {
			sb += "ACC_INT ";
		}
		if ((_flags & ACC_EXPORT)!=0) {
			sb += "ACC_EXPORT ";
		}
		if ((_flags & ACC_APPLET)!=0) {
			sb += "ACC_APPLET ";
		}
		sb += ")";
		return sb;
	}
}