package fr.netattitude.cappuccino;

import java.io.*;

class ConstantPoolComponent extends Component {
	private short 		_count;
	private CpInfo		_constant_pool[];

	// Constant types
	private final static byte CLASS_REF				= (byte)1;
	private final static byte INSTANCE_FIELD_REF	= (byte)2;
	private final static byte VIRTUAL_METHOD_REF	= (byte)3;
	private final static byte SUPER_METHOD_REF		= (byte)4;
	private final static byte STATIC_FIELD_REF		= (byte)5;
	private final static byte STATIC_METHOD_REF		= (byte)6;

	// Constant names
	private final static String constantNames[] = {
		"Class", "Instance field", "Virtual method", "Super method",
		"Static field", "Static method" };

	private class CpInfo {
		private byte 		_tag;
		private byte		_ref[];
		private ClassRef	_cref;

		private CpInfo(DataInputStream dis) throws java.io.IOException {
			_tag 	= dis.readByte();
			_ref = new byte[3];
			File.readBytes(dis, _ref);

			switch (_tag) {
				case CLASS_REF:
				case INSTANCE_FIELD_REF:
				case VIRTUAL_METHOD_REF:
				case SUPER_METHOD_REF:
					_cref = new ClassRef(_ref, 0);
					break;

				case STATIC_FIELD_REF:
				case STATIC_METHOD_REF:
					if (_ref[0] == (byte)0) {
						_cref = new ClassRef(_ref, 1);
					}
					else {
						_cref = new ClassRef(_ref, 0);
					}
					break;
			}
		}

		private void dump(short i) {
			System.out.println("Constant "
				+ Util.toHex(i));
			System.out.println("\tTag: "
				+ Util.toHex(_tag) + " (" + constantNames[_tag-1] + ")");
			switch (_tag) {
				case CLASS_REF:
					_cref.dump(1);
					break;

				case INSTANCE_FIELD_REF:
				case VIRTUAL_METHOD_REF:
				case SUPER_METHOD_REF:
					_cref.dump(1);
					if (_ref[2] >= 0) {
						// public or protected
						System.out.println("\tToken: "
							+ Util.toHex(_ref[2]));
					}
					else {
						// package visible
						System.out.println("\tToken: "
							+ Util.toHex((byte)(_ref[2] & 0x7F)) + " (package visible)");
					}
					break;

				case STATIC_FIELD_REF:
				case STATIC_METHOD_REF:
					_cref.dump(1);
					if (_ref[0] != (byte)0) {
						System.out.println("\tToken: " + Util.toHex(_ref[2]));
					}
					break;
			}
		}
	}

	public ConstantPoolComponent(DataInputStream dis) throws java.io.IOException {
		super(dis);
		_count = dis.readShort();
		if (_count != 0) {
			_constant_pool = new CpInfo[_count];
			for (short i=0; i<_count; i++) {
				_constant_pool[i] = new CpInfo(dis);
			}
		}
	}

	public void dump() {
		super.dump();
		System.out.println("Constant count: "+ Util.toHex(_count));
		for (short i=0; i<_count; i++) {
			_constant_pool[i].dump(i);
		}
		System.out.println();
	}

	public ClassRef getClassRef(short offset) {
		return _constant_pool[offset]._cref;
	}

	public byte getToken(short offset) {
		return _constant_pool[offset]._ref[2];
	}
}