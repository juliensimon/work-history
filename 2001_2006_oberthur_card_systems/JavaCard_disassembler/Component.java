package fr.netattitude.cappuccino;

import java.io.*;
import java.util.jar.*;

abstract class Component implements ComponentConst {
	// Members
	private byte 	_tag;
	private short	_size;
	protected byte	_raw[];

	public Component(DataInputStream dis) throws java.io.IOException {
		_tag 	= dis.readByte();
		_size 	= dis.readShort();
	}

	public byte getTag() {
		return _tag;
	}

	public void dump() {
		System.out.println("Component tag: "
			+ Util.toHex(_tag) + " ("+ componentNames[_tag-1] +")");
		System.out.println("Component size: "
			+ Util.toHex(_size));
	}

	public short getSize() {
		return _size;
	}

	public void readRaw(DataInputStream dis) throws java.io.IOException {
		if (_size != 0) {
			_raw = new byte[_size];
			for (short i=0; i<_size;i++) {
				_raw[i] = dis.readByte();
			}
		}
	}
}