package fr.netattitude.cappuccino;

import java.io.*;

class ClassInfo implements ComponentConst {

	private class ImplementedInterfaceInfo {
		private ClassRef	_ref;
		private byte		_count;
		private byte		_index[];

		private ImplementedInterfaceInfo(DataInputStream dis) throws java.io.IOException {
			_ref 	= new ClassRef(dis);
			_count 	= dis.readByte();
			if (_count != 0) {
				_index = new byte[_count];
				File.readBytes(dis, _index);
			}
		}

		private void dump(short i) {
			System.out.println("\t\tImplemented interface #" + i);
			_ref.dump(3);
			System.out.println("\t\t\tCount: "
				+ Util.toHex(_count));
			if (_count != 0) {
				System.out.println("\t\t\tIndex: "
					+ new String(Util.toHex(_index, 0, _index.length)));
			}
		}

		private short getSize() {
			return (short)(_count+3);
		}
	}

	private byte						_flags;
	private byte						_interface_count;
	private ClassRef					_superclass_ref;
	private byte						_declared_instance_size;
	private byte						_first_reference_token;
	private byte						_reference_count;
	private byte						_public_method_table_base;
	private byte						_public_method_table_count;
	private byte						_package_method_table_base;
	private byte						_package_method_table_count;
	private short						_public_virtual_method_table[];
	private short						_package_virtual_method_table[];
	private ImplementedInterfaceInfo 	_interfaces[];

	private short						_offset;
	private String						_name;

	public ClassInfo(DataInputStream dis, short offset, String name) throws java.io.IOException {
		byte tmp 			= dis.readByte();
		_flags				= (byte)((byte)(tmp >> 4) & 0x0F);
		_interface_count		= (byte)(tmp & 0x0F);
		_superclass_ref			= new ClassRef(dis);
		_declared_instance_size		= dis.readByte();
		_first_reference_token		= dis.readByte();
		_reference_count		= dis.readByte();
		_public_method_table_base	= dis.readByte();
		_public_method_table_count	= dis.readByte();
		_package_method_table_base	= dis.readByte();
		_package_method_table_count	= dis.readByte();
		if (_public_method_table_count != 0) {
			_public_virtual_method_table = new short[_public_method_table_count];
			for (byte i=0; i<_public_virtual_method_table.length; i++) {
				_public_virtual_method_table[i] = dis.readShort();
			}
		}
		if (_package_method_table_count != 0) {
			_package_virtual_method_table = new short[_package_method_table_count];
			for (byte i=0; i<_package_virtual_method_table.length; i++) {
				_package_virtual_method_table[i] = dis.readShort();
			}
		}
		if (_interface_count != 0) {
			_interfaces = new ImplementedInterfaceInfo[_interface_count];
			for (byte i=0; i<_interfaces.length; i++) {
				_interfaces[i] = new ImplementedInterfaceInfo(dis);
			}
		}
		// Offset of the ClassInfo object inside the array
		_offset = offset;
		// Class name
		_name	= name;
	}

	public boolean definesMethodToken(byte token) {
		if ((token & (byte)0x80) == 0) {
			return (token >= _public_method_table_base);
		}
		else {
			return (token >= _package_method_table_base);
		}
	}

	public short getMethodOffset(byte token) {
		if ((token & (byte)0x80) == 0) {
			return _public_virtual_method_table[token-_public_method_table_base];
		}
		else {
			return _package_virtual_method_table[token-_package_method_table_base];
		}
	}

	public byte getInterfaceMethodToken(ClassRef inter, byte token) {
		for (byte i=0; i<_interface_count; i++) {
			if (_interfaces[i].equals(inter)) {
				return _interfaces[i]._index[token];
			}
		}
		return (byte)-1;
	}

	public short getSize() {
		short size = 10;
		for (byte i=0;i<_interface_count;i++) {
			size += _interfaces[i].getSize();
		}
		size += (2*_public_method_table_count);
		size += (2*_package_method_table_count);
		return size;
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

	private void setOffset(short offset) {
		_offset = offset;
	}

	public ClassRef getSuperClass() {
		return _superclass_ref;
	}

	public String getClassName() {
		return _name;
	}

	public void dump(short i) {
		System.out.println("Class #" + i);
		System.out.println("\tClassInfo offset: "
			+ Util.toHex(_offset));
		System.out.println("\tFlags: "
			+ Util.toHex(_flags) + getFlags());
		System.out.println("\tSuper class: ");
		_superclass_ref.dump(2);
		System.out.println("\tInstance size: "
			+ Util.toHex(_declared_instance_size));
		System.out.println("\tFirst ref token: "
			+ Util.toHex(_first_reference_token));
		System.out.println("\tRef count: "
			+ Util.toHex(_reference_count));

		System.out.println("\tPublic method table base: "
			+ Util.toHex(_public_method_table_base));
		System.out.println("\tPublic method table count: "
			+ Util.toHex(_public_method_table_count));
		if (_public_method_table_count != 0) {
			for (byte j=0; j<_public_method_table_count; j++) {
				System.out.println("\t\tPublic method #"
					+ j + ": " + Util.toHex(_public_virtual_method_table[j]));
			}
		}

		System.out.println("\tPackage method table base: "
			+ Util.toHex(_package_method_table_base));
		System.out.println("\tPackage method table count: "
			+ Util.toHex(_package_method_table_count));
		if (_package_method_table_count != 0) {
			for (byte j=0; j<_package_method_table_count; j++) {
				System.out.println("\t\tPackage method #"
					+ j + ": " + Util.toHex(_package_virtual_method_table[j]));
			}
		}

		System.out.println("\tInterface count: "
			+ Util.toHex(_interface_count));
		if (_interface_count != 0) {
			for (byte j=0; j<_interface_count; j++) {
				_interfaces[j].dump(j);
			}
		}
	}
}