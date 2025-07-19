package fr.netattitude.cappuccino;

import java.io.*;

class ImportComponent extends Component {
	private byte 		_count;
	private PackageInfo	_packages[];

	public ImportComponent(DataInputStream dis) throws java.io.IOException {
		super(dis);
		_count = dis.readByte();
		if (_count != 0) {
			_packages = new PackageInfo[_count];
			for (byte i=0; i<_count; i++) {
				_packages[i] = new PackageInfo(dis);
			}
		}
	}

	public void dump() {
		super.dump();
		System.out.println("Package count: "+ _count);
		for (byte i=0; i<_count; i++) {
			_packages[i].dump(i);
		}
		System.out.println();
	}

	public String getAid(byte token) {
		return _packages[token].getAid();
	}

	public String getName(byte token) {
		String aid = _packages[token].getAid();
		for (int i=0; i<wellKnownAids.length; i++) {
			if (aid.compareTo(Util.toHex(wellKnownAids[i], 0, wellKnownAids[i].length)) == 0) {
				return wellKnownNames[i];
			}
		}
		return null;
	}
}