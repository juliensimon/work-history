package fr.netattitude.cappuccino;

import java.io.*;

class DirectoryComponent extends Component {

	private short	 			_component_sizes[];
	private StaticFieldSizeInfo	_static_field_size;
	private byte				_import_count;
	private byte				_applet_count;
	private byte				_custom_count;
	private CustomComponentInfo	_custom_components[];

	private class StaticFieldSizeInfo {
		private short _image_size;
		private short _array_init_count;
		private short _array_init_size;

		private StaticFieldSizeInfo(DataInputStream dis) throws java.io.IOException {
			_image_size			= dis.readShort();
			_array_init_count	= dis.readShort();
			_array_init_size	= dis.readShort();
		}

		private void dump() {
			System.out.println("Static field image size: "
				+ Util.toHex(_image_size));
			System.out.println("Static array count: "
				+ Util.toHex(_array_init_count));
			System.out.println("Static array size: "
				+ Util.toHex(_array_init_size));
		}
	}

	private class CustomComponentInfo {
		private byte	_component_tag;
		private short	_size;
		private byte	_aid_length;
		private byte	_aid[];

		private CustomComponentInfo(DataInputStream dis) throws java.io.IOException {
			_component_tag 	= dis.readByte();
			_size			= dis.readShort();
			_aid_length		= dis.readByte();
			_aid			= new byte[_aid_length];
			File.readBytes(dis, _aid);
		}

		private void dump(byte i) {
			System.out.println("Custom component #"
				+ Util.toHex(i));
			System.out.println("\t tag: "
				+ Util.toHex(_component_tag));
			System.out.println("\t size: "
				+ Util.toHex(_size));
			System.out.println("\t AID length: "
				+ Util.toHex(_aid_length));
			System.out.println("\t AID: "
				+ Util.toHex(_aid, 0, _aid_length));
		}
	}

	public DirectoryComponent(DataInputStream dis) throws java.io.IOException {
		super(dis);
		_component_sizes = new short[COMPONENT_MAX];
		for (byte i=0;i<_component_sizes.length;i++) {
			_component_sizes[i] = dis.readShort();
		}
		_static_field_size = new StaticFieldSizeInfo(dis);
		_import_count = dis.readByte();
		_applet_count = dis.readByte();
		_custom_count = dis.readByte();
		if (_custom_count != 0) {
			_custom_components = new CustomComponentInfo[_custom_count];
			for (byte i=0; i<_custom_count; i++) {
				_custom_components[i] = new CustomComponentInfo(dis);
			}
		}
	}

	public void dump() {
		super.dump();
		for (byte i=0;i<_component_sizes.length;i++) {
			System.out.println(componentNames[i]+" component size: " +
				Util.toHex(_component_sizes[i]));
		}
		_static_field_size.dump();
		System.out.println("Import count: "
			+ Util.toHex(_import_count));
		System.out.println("Applet count: "
			+ Util.toHex(_applet_count));
		System.out.println("Custom count: "
			+ Util.toHex(_custom_count));
		for (byte i=0; i<_custom_count; i++) {
			_custom_components[i].dump(i);
		}
		System.out.println();
	}
}