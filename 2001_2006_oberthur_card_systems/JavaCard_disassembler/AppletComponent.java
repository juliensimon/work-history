package fr.netattitude.cappuccino;

import java.io.*;

class AppletComponent extends Component {
	private byte 	_count;
	private Applet	_applets[];

	private class Applet {
		private byte	_aid_length;
		private byte	_aid[];
		private short	_install_method_offset;

		public Applet(DataInputStream dis) throws java.io.IOException {
			_aid_length				= dis.readByte();
			_aid					= new byte[_aid_length];
			File.readBytes(dis, _aid);
			_install_method_offset 	= dis.readShort();
		}

		public void dump(int i) {
			System.out.println("Applet #" + i);
			System.out.println("\t AID length: " + _aid_length);
			System.out.println("\t AID: " + Util.toHex(_aid, 0, _aid_length));
			System.out.println("\t install method offset: " + _install_method_offset);
		}
	}

	public AppletComponent(DataInputStream dis) throws java.io.IOException {
		super(dis);
		_count = dis.readByte();
		if (_count != 0) {
			_applets = new Applet[_count];
			for (byte i=0; i<_count; i++) {
				_applets[i] = new Applet(dis);
			}
		}
	}

	public void dump() {
		super.dump();
		System.out.println("Applet count: "+ _count);
		for (byte i=0; i<_count; i++) {
			_applets[i].dump(i);
		}
		System.out.println();
	}
}