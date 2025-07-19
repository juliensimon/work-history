package fr.netattitude.cappuccino;

import java.io.*;

class InterfaceInfo implements ComponentConst {
	private byte		_flags;
	private byte		_interface_count;
	private ClassRef	_superinterfaces[];

	private short		_offset;
	private String		_name;

	public InterfaceInfo(DataInputStream dis, short offset, String name) throws java.io.IOException {
		byte tmp 			= dis.readByte();
		_flags				= (byte)((byte)(tmp >> 4) & 0x0F);
		_interface_count	= (byte)(tmp & 0x0F);
		if (_interface_count != 0) {
			_superinterfaces = new ClassRef[_interface_count];
			for (byte i=0; i<_superinterfaces.length; i++) {
				_superinterfaces[i] = new ClassRef(dis);
			}
		}
		// Offset of the ClassInfo object inside the array
		_offset = offset;
		// Class name
		_name	= name;
	}

	public short getSize() {
		return (short)(2*(_interface_count)+1);
	}

	private String getFlags() {
		StringBuffer sb = new StringBuffer(" ( ");
		if ((_flags & C_ACC_INTERFACE)!=0) {
			sb.append("ACC_INTERFACE ");
		}
		if ((_flags & C_ACC_SHAREABLE)!=0) {
			sb.append("ACC_SHAREABLE ");
		}
		sb.append(")");
		return new String(sb);
	}

	public short getOffset() {
		return _offset;
	}

	public String getInterfaceName() {
		return _name;
	}

	public byte getInterfaceCount() {
		return _interface_count;
	}

	public ClassRef getSuperInterface(byte i) {
		return _superinterfaces[i];
	}

	public void dump(short i) {
		System.out.println("Interface #"
			+ i);
		System.out.println("\tInterfaceInfo offset: "
			+ Util.toHex(_offset));
		System.out.println("\tFlags: "
			+ Util.toHex(_flags) + getFlags());
		System.out.println("\tInterfaces count: " + Util.toHex(_interface_count));
		for (byte j=0; j<_interface_count; j++) {
			System.out.println("\t\tInterface #"
				+ i );
			_superinterfaces[i].dump(3);
		}
	}
}