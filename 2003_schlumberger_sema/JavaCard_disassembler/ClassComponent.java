package fr.netattitude.cappuccino;

import java.io.*;

class ClassComponent extends Component {

	private InterfaceInfo	_interfaces[];
	private ClassInfo		_classes[];

	private byte			_interface_count;
	private byte			_class_count;

	public ClassComponent(DataInputStream dis) throws java.io.IOException {
		super(dis);
		readRaw(dis);
	}

	public void postProcess() throws java.io.IOException {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(_raw));

		short offset = 0;

		_interface_count = CapFile.des.getInterfaceCount();
		if (_interface_count != 0) {
			_interfaces = new InterfaceInfo[_interface_count];
			for (byte i=0; i<_interface_count; i++) {
				_interfaces[i] = new InterfaceInfo(dis, offset, "Interface"+i);
				offset += _interfaces[i].getSize();
			}
		}

		_class_count = CapFile.des.getClassCount();
		if (_class_count != 0) {
			_classes	= new ClassInfo[_class_count];
			for (byte i=0; i<_class_count; i++) {
				byte classNumber = (byte)(i+_interface_count);
				_classes[i] = new ClassInfo(dis, offset, "Class"+classNumber);
				offset += _classes[i].getSize();
			}
		}
		dis.close();
	}

	public ClassInfo getClassInfo(short offset) {
		//System.out.println("Looking for offset " + Util.toHex(offset));
		for (byte i=0; i<_class_count; i++) {
			//System.out.println("Comparing to " + Util.toHex(_classes[i].getOffset()));
			if (_classes[i].getOffset() == offset) {
				//System.out.println("Found");
				return _classes[i];
			}
		}
		return null;
	}

	public InterfaceInfo getInterfaceInfo(short offset) {
		//System.out.println("Looking for offset " + Util.toHex(offset));
		for (byte i=0; i<_interface_count; i++) {
			//System.out.println("Comparing to " + Util.toHex(_interfaces[i].getOffset()));
			if (_interfaces[i].getOffset() == offset) {
				//System.out.println("Found");
				return _interfaces[i];
			}
		}
		return null;
	}

	public void dump() {
		super.dump();

		System.out.println("Interface count: "
			+ Util.toHex(_interface_count));
		for (byte i=0;i<_interface_count;i++) {
			_interfaces[i].dump(i);
		}

		System.out.println("Class count: "
			+ Util.toHex(_class_count));
		for (byte i=0;i<_class_count;i++) {
			_classes[i].dump(i);
		}

		System.out.println();
	}
}