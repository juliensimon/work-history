package fr.netattitude.cappuccino;

import java.io.*;

class MethodComponent extends Component {

	private class ExceptionHandlerInfo {
		private short _start_offset;
		private short _bitfield;
		private short _handler_offset;
		private short _catch_type_index;

		private ExceptionHandlerInfo(DataInputStream dis) throws java.io.IOException {
			_start_offset	= dis.readShort();
			_bitfield		= dis.readShort();
			_handler_offset	= dis.readShort();
			_handler_offset	= dis.readShort();
		}

		private void dump(byte i) {
			System.out.println("\tExc handler #" +
				Util.toHex(i));
			System.out.println("\t\tStart offset: " +
				Util.toHex(_start_offset));
			System.out.println("\t\tBitfield: " +
				Util.toHex(_bitfield));
			System.out.println("\t\tHandler offset: " +
				Util.toHex(_handler_offset));
			System.out.println("\t\tCatch type index: " +
				Util.toHex(_catch_type_index));
		}
	}

	private class MethodInfo {

		private class MethodHeaderInfo {
			private byte	_flags;
			private byte	_max_stack;
			private byte	_nargs;
			private byte	_max_locals;

			private MethodHeaderInfo(DataInputStream dis) throws java.io.IOException {
				byte tmp 	= dis.readByte();
				_flags 		= (byte)((byte)(tmp >> 4) & 0x0F);

				if ((_flags & M_ACC_EXTENDED) == 0) {
					_max_stack	= (byte)(tmp & 0x0F);
					tmp = dis.readByte();
					_nargs 		= (byte)((byte)(tmp >> 4) & 0x0F);
					_max_locals	= (byte)(tmp & 0x0F);
				}
				else {
					_max_stack	= dis.readByte();
					_nargs 		= dis.readByte();
					_max_locals	= dis.readByte();
				}
			}

			private void dump() {
				System.out.println("\t\tMethod header");
				System.out.println("\t\t\tFlags: " + Util.toHex(_flags) + getFlags());
				System.out.println("\t\t\tMax stack: " + Util.toHex(_max_stack));
				System.out.println("\t\t\tNargs: " + Util.toHex(_nargs));
				System.out.println("\t\t\tMax locals: " + Util.toHex(_max_locals));
			}

			private String getFlags() {
				String sb = new String(" ( ");
				if ((_flags & M_ACC_ABSTRACT)!=0) {
					sb += "ACC_ABSTRACT ";
				}
				if ((_flags & M_ACC_EXTENDED)!=0) {
					sb += "ACC_EXTENDED ";
				}
				sb += ")";
				return sb;
			}
		}

		private MethodHeaderInfo	_method_header;
		private byte				_bytecodes[];

		private short				_offset;
		private short				_bytecode_count;
		private String				_name;

		private MethodInfo(DataInputStream dis, short i) throws java.io.IOException {
			_method_header = new MethodHeaderInfo(dis);

			_offset 		= CapFile.des.getMethodOffset(i);
			_bytecode_count	= CapFile.des.getBytecodeCount(i);

			_bytecodes = new byte[_bytecode_count];
			File.readBytes(dis, _bytecodes);
		}

		private void dump(short i) {
			System.out.println("\tMethod #"
				+ Util.toHex(i));
			_method_header.dump();
			System.out.println("\t\tMethod offset: "
				+ Util.toHex(_offset));
			System.out.print("\t\tBytecode count: "
				+ Util.toHex(_bytecode_count));
			Util.toHexLines(_bytecodes, (short)0, (short)_bytecodes.length, 4);
		}
	}

	private byte					_handler_count;
	private ExceptionHandlerInfo	_exception_handlers[];
	private MethodInfo				_methods[];
	private short					_method_count;

	public MethodComponent(DataInputStream dis) throws java.io.IOException {
		super(dis);
		readRaw(dis);
	}

	public byte[] getMethodBytecode(short offset) {
		for (short i=0; i<_method_count; i++) {
			if (_methods[i]._offset == offset) {
				return _methods[i]._bytecodes;
			}
		}
		Util.err("can't find method offset " + Util.toHex(offset));
		return null;
	}

	public byte getMethodArgCount(short offset) {
		for (short i=0; i<_method_count; i++) {
			if (_methods[i]._offset == offset) {
				return _methods[i]._method_header._nargs;
			}
		}
		Util.err("can't find method offset " + Util.toHex(offset));
		return (byte)-1;
	}

	public void postProcess() throws java.io.IOException {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(_raw));

		_handler_count = dis.readByte();
		if (_handler_count != 0) {
			_exception_handlers = new ExceptionHandlerInfo[_handler_count];
			for (short i=0; i<_handler_count; i++) {
				_exception_handlers[i] = new ExceptionHandlerInfo(dis);
			}
		}
		//System.out.println("Component method count: " + _method_count);
		//System.out.println("Helper method count: " + helper.getMethodCount());
		_methods = new MethodInfo[CapFile.des.getMethodCount()];
		for (short i=0;i<_methods.length;i++) {
			_methods[i] = new MethodInfo(dis, i);
			_method_count++;
		}
		dis.close();
	}

	public void dump() {
		super.dump();

		System.out.println("Handler count: " + Util.toHex(_handler_count));
		for (byte i=0; i<_handler_count; i++) {
			_exception_handlers[i].dump(i);
		}

		System.out.println("Method count: " + Util.toHex(_method_count));
		for (short i=0;i<_method_count;i++) {
			_methods[i].dump(i);
		}

		System.out.println();
	}
}