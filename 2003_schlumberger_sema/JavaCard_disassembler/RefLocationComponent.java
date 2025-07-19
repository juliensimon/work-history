package fr.netattitude.cappuccino;

import java.io.*;

class RefLocationComponent extends Component {

	private short 	_byte_index_count;
	private byte	_offsets[];
	private short 	_byte2_index_count;
	private byte	_offsets2[];

	public RefLocationComponent(DataInputStream dis) throws java.io.IOException {
		super(dis);
		_byte_index_count 	= dis.readShort();
		if (_byte_index_count != 0) {
			_offsets = new byte[_byte_index_count];
			for (short i=0; i<_byte_index_count; i++) {
				_offsets[i] = dis.readByte();
			}
		}
		_byte2_index_count 	= dis.readShort();
		if (_byte2_index_count != 0) {
			_offsets2 = new byte[_byte2_index_count];
			for (short i=0; i<_byte2_index_count; i++) {
				_offsets2[i] = dis.readByte();
			}
		}
	}

	public void dump() {
		super.dump();
		System.out.println("Byte index count: "
			+ Util.toHex(_byte_index_count));
		for (short i=0; i<_byte_index_count; i++) {
			System.out.println("\tOffset "
				+ Util.toHex(i) +": " + Util.toHex(_offsets[i]));
		}
		System.out.println("Byte2 index count: "
			+ Util.toHex(_byte2_index_count));
		for (short i=0; i<_byte2_index_count; i++) {
			System.out.println("\tOffset "
				+ Util.toHex(i) +": " + Util.toHex(_offsets2[i]));
		}
		System.out.println();
	}
}