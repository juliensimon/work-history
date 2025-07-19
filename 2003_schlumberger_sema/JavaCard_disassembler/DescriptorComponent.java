package fr.netattitude.cappuccino;

import java.io.*;

class DescriptorComponent extends Component {

	private class ClassDescriptorInfo {

		private class FieldDescriptorInfo {
			private byte		_f_token;
			private byte		_f_access_flags;
			private ClassRef	_f_ref;
			private byte		_f_info;
			private short		_f_type;

			private FieldDescriptorInfo(DataInputStream dis) throws java.io.IOException {
				_f_token			= dis.readByte();
				_f_access_flags 	= dis.readByte();
				_f_ref				= new ClassRef(dis);
				_f_info				= dis.readByte();
				_f_type				= dis.readShort();
			}

			private void dump(short i) {
				System.out.println("\t\tField " + Util.toHex(i));
				System.out.println("\t\t\tToken: " + Util.toHex((byte)(_f_token)));
				System.out.println("\t\t\tAccess flags: "
					+ Util.toHex(_f_access_flags) + getAccessFlags());

				_f_ref.dump(3);
				System.out.println("\t\t\tField token: " + Util.toHex(_f_info));

				if (_f_type < 0) {
					// Primitive type
					short tmp = (short)(_f_type & 0x7FFF);
					System.out.println("\t\t\tType: "
						+ Util.toHex(tmp) + " (" + typeNames[tmp] + ")");
				}
				else {
					// Reference type
					System.out.println("\t\t\tType: reference "  + Util.toHex(_f_type));
				}
			}

			private String getAccessFlags() {
				StringBuffer sb = new StringBuffer(" ( ");
				//System.out.println("Flags: " + _f_access_flags);
				// Get flags
				if ((_f_access_flags & ACC_PUBLIC)!=0) {
					sb.append("ACC_PUBLIC ");
				}
				if ((_f_access_flags & ACC_PRIVATE)!=0) {
					sb.append("ACC_PRIVATE ");
				}
				if ((_f_access_flags & ACC_PROTECTED)!=0) {
					sb.append("ACC_PROTECTED ");
				}
				if ((_f_access_flags & ACC_STATIC)!=0) {
					sb.append("ACC_STATIC ");
				}
				if ((_f_access_flags & ACC_FINAL)!=0) {
					sb.append("ACC_FINAL ");
				}
				sb.append(")");
				return new String(sb);
			}

			private String getFieldDefinition(byte cla, short fie) {
				StringBuffer 	sb = new StringBuffer();

				// Get flags
				if ((_f_access_flags & ACC_PUBLIC)!=0) {
					sb.append("public ");
				}
				if ((_f_access_flags & ACC_PRIVATE)!=0) {
					sb.append("private ");
				}
				if ((_f_access_flags & ACC_PROTECTED)!=0) {
					sb.append("protected ");
				}
				if ((_f_access_flags & ACC_STATIC)!=0) {
					sb.append("static ");
				}
				if ((_f_access_flags & ACC_FINAL)!=0) {
					sb.append("final ");
				}

				//System.out.println("Field type: " + Util.toHex(_f_type));

				String s = null;
				// Get type
				if (_f_type < 0) {
					// Primitive type
					sb.append(typeNames[(short)(_f_type & 0x7FFF)]);
				}
				else {
					TypeDescriptorInfo.TypeDesc t = _types.getType(_f_type);
					//t.dump((byte)i);

					// Look at first nibble
					byte nib = (byte)((byte)(t._types[0] >> 4) & 0x0F);

					boolean refArray = false;
					switch (nib) {
						case REF_A_TYPE:
							refArray = true;
						case REF_TYPE:
							sb.append(t.getReferenceDescription((byte)1, refArray));
							break;
						default:
							// Primitive array types
							sb.append(typeNames[nib]);
							break;
					}
				}
				// Get name
				sb.append(" f" + cla + "_" + fie);
				return sb.toString();
			}
		}

		private class MethodDescriptorInfo {
			private byte	_m_token;
			private byte	_m_access_flags;
			private short	_m_method_offset;
			private short	_m_type_offset;
			private short	_m_bytecode_count;
			private short	_m_exc_handler_count;
			private short	_m_exc_handler_index;

			private MethodDescriptorInfo(DataInputStream dis) throws java.io.IOException {
				_m_token				= dis.readByte();
				_m_access_flags 		= dis.readByte();
				_m_method_offset		= dis.readShort();
				_m_type_offset			= dis.readShort();
				_m_bytecode_count		= dis.readShort();
				_m_exc_handler_count	= dis.readShort();
				_m_exc_handler_index	= dis.readShort();
			}

			private void dump(short i) {
				System.out.println("\t\tMethod " + Util.toHex(i));
				System.out.println("\t\t\tToken: " +
					Util.toHex(_m_token));
				System.out.println("\t\t\tAccess flags: "
					+ Util.toHex(_m_access_flags) + getAccessFlags());
				System.out.println("\t\t\tMethod offset: "
					+ Util.toHex(_m_method_offset));
				System.out.println("\t\t\tType offset: "
					+ Util.toHex(_m_type_offset));
				System.out.println("\t\t\tBytecode count: "
					+ Util.toHex(_m_bytecode_count));
				System.out.println("\t\t\tException handler count: "
					+ Util.toHex(_m_exc_handler_count));
				System.out.println("\t\t\tException handler index: "
					+ Util.toHex(_m_exc_handler_index));
			}

			private String getAccessFlags() {
				StringBuffer sb = new StringBuffer(" ( ");
				if ((_m_access_flags & ACC_PUBLIC)!=0) {
					sb.append("ACC_PUBLIC ");
				}
				if ((_m_access_flags & ACC_PRIVATE)!=0) {
					sb.append("ACC_PRIVATE ");
				}
				if ((_m_access_flags & ACC_PROTECTED)!=0) {
					sb.append("ACC_PROTECTED ");
				}
				if ((_m_access_flags & ACC_STATIC)!=0) {
					sb.append("ACC_STATIC ");
				}
				if ((_m_access_flags & ACC_FINAL)!=0) {
					sb.append("ACC_FINAL ");
				}
				if ((_m_access_flags & ACC_INIT)!=0) {
					sb.append("ACC_INIT ");
				}
				if ((_m_access_flags & ACC_ABSTRACT)!=0) {
					sb.append("ACC_ABSTRACT ");
				}
				sb.append(")");
				return new String(sb);
			}

			private String getMethodDefinition(byte cla, short met) {
				StringBuffer 	sb = new StringBuffer();

				// Get flags
				if ((_m_access_flags & ACC_PUBLIC)!=0) {
					sb.append("public ");
				}
				if ((_m_access_flags & ACC_PRIVATE)!=0) {
					sb.append("private ");
				}
				if ((_m_access_flags & ACC_PROTECTED)!=0) {
					sb.append("protected ");
				}
				if ((_m_access_flags & ACC_STATIC)!=0) {
					sb.append("static ");
				}
				if ((_m_access_flags & ACC_FINAL)!=0) {
					sb.append("final ");
				}
				if ((_m_access_flags & ACC_ABSTRACT)!=0) {
					sb.append("abstract ");
				}

				TypeDescriptorInfo.TypeDesc t = _types.getType(_m_type_offset);

				//t.dump((byte)met);

				// Store nibbles in byte array
				byte[] nibbles = new byte[t._nibble_count];
				for (byte j=0;j<t._nibble_count;j++) {
					byte tmp;
					if (j%2 == 0) {
						nibbles[j] = (byte)((t._types[j/2] >> 4) & 0x0F);
					}
					else {
						nibbles[j] = (byte)(t._types[j/2] & 0x0F);
					}
				}

				// Get name
				if ((_m_access_flags & ACC_INIT)!=0) {
					sb.append(" Class"+ cla);
				}
				else {
					sb.append(new String(" m" + cla + "_" + met));
				}
				sb.append("(");

				// Dump type info
				for (byte j=0;j<t._nibble_count;j++) {
					boolean refArray = false;

					byte nib;
					if (j%2 == 0) {
						nib = (byte)((t._types[j/2] >> 4) & 0x0F);
					}
					else {
						nib = (byte)(t._types[j/2] & 0x0F);
					}

					//System.out.println("Param type: " + Util.toHex(nib));

					String s;
					switch (nib) {
						case REF_A_TYPE:
							refArray = true;
						case REF_TYPE:
							s = t.getReferenceDescription((byte)(j+1), refArray);
							j+=4;
							break;
						default:
							s = typeNames[nib];
							break;
					}
					if (j!=t._nibble_count-1) {
						byte argNum = (byte)(j+1);
						sb.append(s + " p" + argNum + ", ");
					}
					else {
						sb.insert(sb.toString().lastIndexOf("  "), " "+s);
					}
				}
				sb.append(")");
				return sb.toString();
			}
		}

		private byte					_token;
		private byte					_access_flags;
		private ClassRef				_this_class_ref;
		private byte					_interface_count;
		private short					_field_count;
		private short					_method_count;
		private short					_interfaces[];
		private FieldDescriptorInfo		_fields[];
		private MethodDescriptorInfo	_methods[];

		private ClassDescriptorInfo(DataInputStream dis) throws java.io.IOException {
			_token				= dis.readByte();
			_access_flags 		= dis.readByte();
			_this_class_ref		= new ClassRef(dis);
			_interface_count	= dis.readByte();
			_field_count		= dis.readShort();
			_method_count		= dis.readShort();
			if (_interface_count != 0) {
				_interfaces = new short[_interface_count];
				for (byte i=0;i<_interface_count;i++) {
					_interfaces[i] = dis.readShort();
				}
			}
			if (_field_count != 0) {
				_fields = new FieldDescriptorInfo[_field_count];
				for (short i=0; i<_field_count; i++) {
					_fields[i] = new FieldDescriptorInfo(dis);
				}
			}
			if (_method_count != 0) {
				_methods = new MethodDescriptorInfo[_method_count];
				for (short i=0; i<_method_count; i++) {
					_methods[i] = new MethodDescriptorInfo(dis);
				}
			}
		}

		private void dump(byte i) {
			System.out.println("Class "
				+ Util.toHex(i));
			System.out.println("\tToken: "
				+ Util.toHex(_token));
			System.out.println("\tAccess flags: "
				+ Util.toHex(_access_flags) + getAccessFlags());
			System.out.println("\tClass reference: ");
			_this_class_ref.dump(2);
			System.out.println("\tInterface count: "
				+ Util.toHex(_interface_count));
			if (_interface_count != 0) {
				for (byte j=0;j<_interface_count;j++) {
					System.out.println("\t\tInterface "
						+ Util.toHex(j));
					new ClassRef(_interfaces[j]).dump(3);
				}
			}
			System.out.println("\tField count: "
				+ Util.toHex(_field_count));
			if (_field_count != 0) {
				for (byte j=0;j<_field_count;j++) {
					_fields[j].dump(j);
				}
			}
			System.out.println("\tMethod count: " + Util.toHex(_method_count));
			if (_method_count != 0) {
				for (byte j=0;j<_method_count;j++) {
					_methods[j].dump(j);
				}
			}
		}

		private String getAccessFlags() {
			StringBuffer sb = new StringBuffer(" ( ");
			if ((_access_flags & ACC_PUBLIC)!=0) {
				sb.append("ACC_PUBLIC ");
			}
			if ((_access_flags & ACC_FINAL)!=0) {
				sb.append("ACC_FINAL ");
			}
			if ((_access_flags & ACC_INTERFACE)!=0) {
				sb.append("ACC_INTERFACE ");
			}
			if ((_access_flags & ACC_ABSTRACT)!=0) {
				sb.append("ACC_ABSTRACT ");
			}
			sb.append(")");
			return new String(sb);
		}

		private boolean isInterface() {
			return ( (_access_flags & ACC_INTERFACE) !=0 );
		}

		private StringBuffer getDefinition(byte i) {
			StringBuffer 	sb = new StringBuffer();

			// Get flags
			if ((_access_flags & ACC_PUBLIC)!=0) {
				sb.append("public ");
			}
			if ((_access_flags & ACC_PROTECTED)!=0) {
				sb.append("protected ");
			}
			if ((_access_flags & ACC_PRIVATE)!=0) {
				sb.append("private ");
			}
			if ((_access_flags & ACC_FINAL)!=0) {
				sb.append("final ");
			}
			String name = null;
			if (isInterface()) {
				// Interface
				sb.append("interface ");
				name = CapFile.cla.getInterfaceInfo(_this_class_ref.getInternalRef()).getInterfaceName();
			}
			else {
				// Class
				if ((_access_flags & ACC_ABSTRACT)!=0) {
					sb.append("abstract ");
				}
				sb.append("class ");
				ClassInfo ci = CapFile.cla.getClassInfo(_this_class_ref.getInternalRef());
				if (ci == null) {
					Util.warn("can't find internal reference " + Util.toHex(_this_class_ref.getInternalRef()));
				}
				else {
					name= ci.getClassName();
				}
			}
			sb.append(name + " ");


			return sb;
		}
	}

	private class TypeDescriptorInfo {

		private class TypeDesc {
			private byte	_nibble_count;
			private byte 	_types[];

			private short	_offset;

			private TypeDesc(DataInputStream dis, short offset) throws java.io.IOException {
				_nibble_count = dis.readByte();
				if (_nibble_count != 0) {
					_types = new byte[(_nibble_count/2) + (_nibble_count%2)];
				}
				File.readBytes(dis, _types);
				_offset = offset;
			}

			private byte getSize() {
				return (byte)(_types.length + 1);
			}

			private String getReferenceDescription(byte first, boolean refArray) {
				StringBuffer sb = new StringBuffer();
				String className;

				//System.out.println(Util.toHex(_types, 0, _types.length));

				// Store nibbles in byte array
				byte[] nibbles = new byte[_nibble_count];
				for (byte j=0;j<_nibble_count;j++) {
					byte tmp;
					if (j%2 == 0) {
						nibbles[j] = (byte)((byte)(_types[j/2] >> 4) & 0x0F);
					}
					else {
						nibbles[j] = (byte)(_types[j/2] & 0x0F);
					}
				}

				short ref = (short)(
						( (byte)( (nibbles[first]<<4)	+ nibbles[first+1]) <<8 )
					+ 	( (byte)( (nibbles[first+2]<<4) + nibbles[first+3])     ) );

				//System.out.println(Util.toHex(ref));

				// Decode nibbles
				ClassRef typeRef = new ClassRef(ref);
				if (typeRef.isInternal()) {
					ClassInfo ci = CapFile.cla.getClassInfo(typeRef.getInternalRef());
					if (ci == null) {
						Util.warn("can't find internal reference " + Util.toHex(typeRef.getInternalRef()));
					}
					else {
						className = ci.getClassName();
						sb.append(className);
						if (refArray) {
							sb.append("[]");
						}
					}
				}
				else {
					String packName = CapFile.imp.getName(typeRef.getPackageToken());
					//System.out.println("Package name: " + packName);
					ExportFile exp	= File.exports.findPackage(packName);
					if (exp == null) {
						Util.warn("can't find information on package " + packName);
					}
					else {

						className = exp.getClassName(typeRef.getClassToken());
						//System.out.println("Class name: " + className);
						className = packName + "." + className;
						sb.append(className);
						if (refArray) {
							sb.append("[]");
						}
					}
				}
				return sb.toString();
			}

			private void dump(byte i) {
				System.out.println("\t\tType description "
					+ Util.toHex(i));
				System.out.println("\t\t\tType offset: "
					+ Util.toHex(_offset));
				System.out.println("\t\t\tNibble count: "
					+ Util.toHex(_nibble_count));

				// Store nibbles in byte array
				byte[] nibbles = new byte[_nibble_count];
				for (byte j=0;j<_nibble_count;j++) {
					byte tmp;
					if (j%2 == 0) {
						nibbles[j] = (byte)((byte)(_types[j/2] >> 4) & 0x0F);
					}
					else {
						nibbles[j] = (byte)(_types[j/2] & 0x0F);
					}
				}

				// Dump type info
				for (byte j=0;j<nibbles.length;j++) {
					System.out.println("\t\t\tType "
							+ Util.toHex(j) + ": " + nibbles[j] + " (" + typeNames[nibbles[j]] + ")");
					if ((nibbles[j] == REF_TYPE) || (nibbles[j] == REF_A_TYPE)) {
						byte b0	= (byte)((nibbles[j+1]<<4) + nibbles[j+2]);
						byte b1	= (byte)((nibbles[j+3]<<4) + nibbles[j+4]);
						new ClassRef(Util.makeShort(b0, b1)).dump(4);
						j += 4;
					}
				}
			}
		}

		private short 		_t_cpool_count;
		private short 		_t_cpool_types[];
		private TypeDesc 	_t_typedesc[];
		private byte		_t_typedesc_count;

		private TypeDescriptorInfo(DataInputStream dis) throws java.io.IOException {
			_t_cpool_count = dis.readShort();
			if (_t_cpool_count != 0) {
				_t_cpool_types 	= new short[_t_cpool_count];
				for (short i=0;i<_t_cpool_count;i++) {
					_t_cpool_types[i] = dis.readShort();
				}
				_t_typedesc = new TypeDesc[255];

				short offset = (short)(2*(_t_cpool_count+1));
				for (short i=0;i<_t_typedesc.length;i++) {
					try {
						_t_typedesc[i] = new TypeDesc(dis, offset);
						offset += _t_typedesc[i].getSize();
						_t_typedesc_count++;
					}
					catch (EOFException e) {
						break;
					}
				}
			}
		}

		private void dump() {
			System.out.println("\t\tConstant pool type count: "
				+ Util.toHex(_t_cpool_count));
			if (_t_cpool_count != 0) {
				for (short i=0;i<_t_cpool_count;i++) {
					System.out.println("\t\t\tConstant pool type "
						+ Util.toHex(i) + " : " + Util.toHex(_t_cpool_types[i]));
				}
				System.out.println("\t\tType count: "
					+ Util.toHex(_t_typedesc_count));
				for (byte i=0;i<_t_typedesc_count;i++) {
					_t_typedesc[i].dump(i);
				}
			}
		}

		private TypeDesc getType(short offset) {
			//System.out.println("Looking for offset " + Util.toHex(offset));
			for (byte i=0; i<_t_typedesc_count; i++) {
				//System.out.println("Comparing to " + Util.toHex(_t_typedesc[i]._offset));
				if (_t_typedesc[i]._offset == offset) {
					//System.out.println("Found");
					return _t_typedesc[i];
				}
			}
			return null;
		}
	}

	private byte 					_class_count;
	private ClassDescriptorInfo		_classes[];
	private TypeDescriptorInfo		_types;

	// Post-processed data
	private byte	_classes_count;
	private byte	_interfaces_count;
	private byte	_method_count;
	private short	_offsets[];
	private short	_bytecode_count[];
	private String	_names[];

	public DescriptorComponent(DataInputStream dis) throws java.io.IOException {
		super(dis);
		_class_count = dis.readByte();
		if (_class_count != 0) {
			_classes = new ClassDescriptorInfo[_class_count];
			for (byte i=0; i<_class_count; i++) {
				_classes[i] = new ClassDescriptorInfo(dis);
			}
		}
		_types = new TypeDescriptorInfo(dis);

		// Post-process data to help parse Class and Method components
		for (byte i=0; i<_class_count; i++) {
			if (_classes[i].isInterface()) {
				_interfaces_count ++;
			}
			else {
				_classes_count ++;
				_method_count += _classes[i]._method_count;
			}
		}
		//System.out.println("method count: " + _method_count);

		_offsets 			= new short[_method_count];
		_bytecode_count 	= new short[_method_count];
		_names				= new String[_method_count];

		byte tmp = 0;
		for (byte j=0; j<_class_count; j++) {
			if (!_classes[j].isInterface()) {
				for (byte k=0; k<_classes[j]._method_count; k++) {
					_offsets[tmp] 			= _classes[j]._methods[k]._m_method_offset;
					_bytecode_count[tmp] 	= _classes[j]._methods[k]._m_bytecode_count;
					_names[tmp]				= new String("m" + j + "_" + k);
					tmp++;
				}
			}
		}

		//System.out.println("Offsets : ");
		//System.out.println(Util.toHex(_offsets, 0, _offsets.length));
		//System.out.println("Bytecode counts : ");
		//System.out.println(Util.toHex(_bytecode_count, 0, _offsets.length));
	}

	public void dump() {
		super.dump();
		System.out.println("Class count: "+ Util.toHex(_class_count));
		for (byte i=0; i<_class_count; i++) {
			_classes[i].dump(i);
		}
		_types.dump();
		System.out.println();
	}

	public short getFieldCount(byte cla) {
		return _classes[cla]._field_count;
	}

	public String getFieldDefinition(byte cla, short fie) {
		return _classes[cla]._fields[fie].getFieldDefinition(cla, fie);
	}

	public byte getTotalClassCount() {
		return _class_count;
	}

	public byte getInterfaceCount() {
		return _interfaces_count;
	}

	public byte getClassCount() {
		return _classes_count;
	}

	public boolean isInterface(byte cla) {
		return _classes[cla].isInterface();
	}

	public String getClassDefinition(byte cla) {
		boolean 			extend 	= false;
		ClassDescriptorInfo cdi 	= _classes[cla];

		// Get basic definition
		StringBuffer sb = cdi.getDefinition(cla);
		if (cdi.isInterface()) {
			// Get interface info
			InterfaceInfo intInfo = CapFile.cla.getInterfaceInfo(cdi._this_class_ref.getInternalRef());
			byte intCount = intInfo.getInterfaceCount();
			if (intCount != 0) {
				String intName;
				sb.append("implements ");
				for (byte j=0; j<intCount; j++) {
					ClassRef intRef = intInfo.getSuperInterface(j);
					if (intRef.isInternal()) {
						intName = CapFile.cla.getInterfaceInfo(intRef.getInternalRef()).getInterfaceName();
						sb.append(intName);
						if (j!=intCount-1) {
							sb.append(", ");
						}
					}
					else {
						String packName = CapFile.imp.getName(intRef.getPackageToken());
						ExportFile exp	= File.exports.findPackage(packName);
						if (exp == null) {
							Util.warn("can't find information on package " + packName);
						}
						else {
							intName = exp.getClassName(intRef.getClassToken());
							intName = packName + "." + intName;
							sb.append(intName);
							if (j!=intCount-1) {
								sb.append(", ");
							}
						}
					}
				}
			}
		}
		else {
			// Get class info
			ClassInfo claInfo = CapFile.cla.getClassInfo(cdi._this_class_ref.getInternalRef());
			if (claInfo == null) {
				Util.warn("can't find internal reference " + Util.toHex(cdi._this_class_ref.getInternalRef()));
				return sb.toString();
			}
			// Get super class info
			ClassRef scRef = claInfo.getSuperClass();
			String scName;
			if (scRef.isInternal()) {
				scName = CapFile.cla.getClassInfo(scRef.getInternalRef()).getClassName();
				sb.append("extends ");
				sb.append(scName);
				extend = true;
			}
			else {
				String packName = CapFile.imp.getName(scRef.getPackageToken());
				ExportFile exp	= File.exports.findPackage(packName);
				if (exp == null) {
					Util.warn("can't find information on package " + packName);
				}
				else {
					scName = exp.getClassName(scRef.getClassToken());
					scName = packName + "." + scName;
					if (scName.compareTo("java.lang.Object") != 0) {
						sb.append("extends ");
						sb.append(scName);
						extend = true;
					}
				}
			}

			// Take care of interfaces
			if (cdi._interface_count != 0) {
				String intName;
				if (extend) {
					sb.append(", ");
				}
				sb.append("implements ");
				for (byte j=0; j<cdi._interface_count; j++) {
					ClassRef intRef = new ClassRef(cdi._interfaces[j]);
					if (intRef.isInternal()) {
						intName = CapFile.cla.getInterfaceInfo(intRef.getInternalRef()).getInterfaceName();
						sb.append(intName);
						if (j!=cdi._interface_count-1) {
							sb.append(", ");
						}
					}
					else {
						String packName = CapFile.imp.getName(intRef.getPackageToken());
						ExportFile exp	= File.exports.findPackage(packName);
						if (exp == null) {
							Util.warn("can't find information on package " + packName);
						}
						else {
							intName = exp.getClassName(intRef.getClassToken());
							intName = packName + "." + intName;
							sb.append(intName);
							if (j!=cdi._interface_count-1) {
								sb.append(", ");
							}
						}
					}
				}
			}
		}

		return new String(sb);
	}


	public short getMethodCount() {
		return _method_count;
	}

	public short getMethodCount(byte cla) {
		return _classes[cla]._method_count;
	}

	public short getMethodOffset(short i) {
		return _offsets[i];
	}

	public String getMethodName(short offset) {
		//System.out.println("Looking for " + Util.toHex(offset));
		for (byte i=0; i<_method_count; i++) {
			//System.out.println(Util.toHex(_offsets[i]));
			if (_offsets[i] == offset) {
				return _names[i];
			}
		}
		return null;
	}

	public short getBytecodeCount(short i) {
		return _bytecode_count[i];
	}

	public String getMethodDefinition(byte cla, short met) {
		return _classes[cla]._methods[met].getMethodDefinition(cla, met);
	}

	public byte[] getMethodBytecode(byte cla, short met) {
		short offset = _classes[cla]._methods[met]._m_method_offset;
		return CapFile.met.getMethodBytecode(offset);
	}

	public byte getMethodArgCount(byte cla, short met) {
		short offset = _classes[cla]._methods[met]._m_method_offset;
		return CapFile.met.getMethodArgCount(offset);
	}
}