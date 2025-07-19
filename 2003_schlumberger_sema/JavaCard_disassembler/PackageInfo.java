package fr.netattitude.cappuccino;

import java.io.*;

class PackageInfo {
		private byte 	_minor_version;
		private byte 	_major_version;
		private byte 	_aid_length;
		private byte	_aid[];

		public PackageInfo(DataInputStream dis) throws java.io.IOException {
			_minor_version 	= dis.readByte();
			_major_version 	= dis.readByte();
			_aid_length		= dis.readByte();
			_aid			= new byte[_aid_length];
			File.readBytes(dis, _aid);
		}

		public void dump(int level) {
			Util.printlnTab("Package minor version: " + _minor_version, level);
			Util.printlnTab("Package major version: " + _major_version, level);
			Util.printlnTab("Package AID length: "	+ _aid_length	, level);
			Util.printlnTab("Package AID: " + Util.toHex(_aid, 0, _aid_length), level);
		}

		public void dump(byte i) {
			System.out.println("Package #" + i);
			dump((int)1);
		}

		public String getAid() {
			return Util.toHex(_aid, 0, _aid.length);
		}
}