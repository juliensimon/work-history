package fr.netattitude.cappuccino;

import java.io.*;

class ClassRef {
	private boolean	_isInternal;
	private short	_internalRef;
	private byte	_packageTok;
	private byte	_classTok;

	public ClassRef(short s) {
		if (s >= 0) {
			_isInternal 	= true;
			_internalRef 	= s;
		}
		else {
			_isInternal = false;
			s = (short)(s & (short)0x7FFF);
			_packageTok	= (byte)((byte)(s >> 8) & 0x00FF);
			_classTok	= (byte)(s & 0x00FF);
			//System.out.println("tok=" + Util.toHex(_packageTok) + " " + Util.toHex(_classTok));
		}
	}

	public ClassRef(byte[] bArray, int offset) {
		this(Util.makeShort(bArray[offset], bArray[offset+1]));
	}

	public ClassRef(DataInputStream dis) throws java.io.IOException {
		this(dis.readShort());
	}

	public boolean isInternal() {
		return _isInternal;
	}

	public short getInternalRef() {
		return _internalRef;
	}

	public byte getPackageToken() {
		return _packageTok;
	}

	public byte getClassToken() {
		return _classTok;
	}

	public boolean equals(ClassRef cref) {
		if (_isInternal) {
			return (_internalRef == cref._internalRef);
		}
		else {
			return ((_packageTok == cref._packageTok) && (_classTok == cref._classTok));
		}
	}

	public void dump(int level) {
		if (isInternal()) {
			Util.printlnTab(new String("Internal reference: "
				+ Util.toHex(_internalRef)), level);
		}
		else {
			Util.printlnTab(new String("Package token:\t"
				+ Util.toHex(_packageTok)), level);
			Util.printlnTab(new String("Class token:\t"
				+ Util.toHex(_classTok)), level);
		}
	}
}