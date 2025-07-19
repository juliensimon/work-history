package fr.netattitude.cappuccino;

import java.io.*;

class StaticFieldComponent extends Component {

	private class ArrayInitInfo {
		private byte	_type;
		private short	_count;
		private byte	_values[];

		private ArrayInitInfo(DataInputStream dis) throws java.io.IOException {
			_type 	= dis.readByte();
			_count	= dis.readShort();
			if (_count != 0) {
				_values = new byte[_count];
				File.readBytes(dis, _values);
			}
		}

		private void dump(short i) {
			System.out.println("Array init #"
				+ Util.toHex(i));
			System.out.println("\tType: "
				+ Util.toHex(_type) + " (" + typeNames[_type] + ")");
			System.out.println("\tCount: "
				+ Util.toHex(_count));
			System.out.print("Values: ");
			Util.toHex(_values, 0, _count);
			System.out.println();
		}

	}

	private short 			_image_size;
	private short			_reference_count;
	private short			_array_init_count;
	private ArrayInitInfo	_array_init[];
	private short			_default_value_count;
	private short			_non_default_value_count;
	private byte			_non_default_values[];

	public StaticFieldComponent(DataInputStream dis) throws java.io.IOException {
		super(dis);
		_image_size 		= dis.readShort();
		_reference_count	= dis.readShort();
		_array_init_count	= dis.readShort();
		if (_array_init_count != 0) {
			_array_init = new ArrayInitInfo[_array_init_count];
			for (short i=0; i<_array_init_count; i++) {
				_array_init[i] = new ArrayInitInfo(dis);
			}
		}
		_default_value_count 		= dis.readShort();
		_non_default_value_count 	= dis.readShort();
		if (_non_default_value_count != 0) {
			_non_default_values = new byte[_non_default_value_count];
		}
	}

	public void dump() {
		super.dump();
		System.out.println("Image size: "
			+ Util.toHex(_image_size));
		System.out.println("Reference count: "
			+ Util.toHex(_reference_count));
		System.out.println("Array init count: "
			+ Util.toHex(_array_init_count));
		for (short i=0; i<_array_init_count; i++) {
			_array_init[i].dump(i);
		}
		System.out.println("Default value count: "
			+ Util.toHex(_default_value_count));
		System.out.println("Non default value count: "
			+ Util.toHex(_non_default_value_count));
		System.out.println("Default values: ");
		System.out.println(new String(Util.toHex(_non_default_values, 0, _non_default_value_count)));
		System.out.println();
	}
}