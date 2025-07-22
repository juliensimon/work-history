package fr.netattitude.cappuccino;

import java.io.*;

class ExportComponent extends Component {

	private class ClassExportInfo {

		private short 	_class_offset;
		private byte	_static_field_count;
		private byte	_static_method_count;
		private short	_static_fields_offsets[];
		private short	_static_methods_offsets[];

		private ClassExportInfo(DataInputStream dis) throws java.io.IOException {
			_class_offset 			= dis.readShort();
			_static_field_count 	= dis.readByte();
			_static_method_count	= dis.readByte();
			if (_static_field_count != 0) {
				_static_fields_offsets = new short[_static_field_count];
				for (byte i=0;i<_static_field_count;i++) {
					_static_fields_offsets[i] = dis.readShort();
				}
			}
			if (_static_method_count != 0) {
				_static_methods_offsets = new short[_static_method_count];
				for (byte i=0;i<_static_method_count;i++) {
					_static_methods_offsets[i] = dis.readShort();
				}
			}
		}

		private void dump(byte i) {
			System.out.println("Class #"
				+ Util.toHex(i));
			System.out.println("\tStatic field count: "
				+ Util.toHex(_static_field_count));
			for (byte j=0;j<_static_field_count;j++) {
					System.out.println("\t\tField #"
						+ Util.toHex(j) + " offset: " + Util.toHex(_static_fields_offsets[j]));
			}
			System.out.println("\tStatic method count: "
				+ Util.toHex(_static_method_count));
			for (byte j=0;j<_static_method_count;j++) {
					System.out.println("\t\tMethod #"
						+ Util.toHex(j) +" offset: " + Util.toHex(_static_methods_offsets[j]));
			}
		}
	}

	private byte 				_class_count;
	private ClassExportInfo		_class_exports[];

	public ExportComponent(DataInputStream dis) throws java.io.IOException {
		super(dis);
		_class_count = dis.readByte();
		if (_class_count != 0) {
			_class_exports = new ClassExportInfo[_class_count];
			for (byte i=0; i<_class_count; i++) {
				_class_exports[i] = new ClassExportInfo(dis);
			}
		}
	}

	public void dump() {
		super.dump();
		System.out.println("Class count: "+ Util.toHex(_class_count));
		for (byte i=0; i<_class_count; i++) {
			_class_exports[i].dump(i);
		}
		System.out.println();
	}
}