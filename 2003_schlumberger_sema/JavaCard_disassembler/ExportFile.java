package fr.netattitude.cappuccino;

import java.io.*;

class ExportFile extends File {

	// Constant pool types
	private final static byte	CONSTANT_UTF8		=	(byte)1;
	private final static byte	CONSTANT_INTEGER	=	(byte)3;
	private final static byte	CONSTANT_CLASSREF	=	(byte)7;
	private final static byte	CONSTANT_PACKAGE	=	(byte)13;

	// Package flags
	private final static byte	P_ACC_LIBRARY		= 	(byte)1;

	// Class flags
	private final static short	C_ACC_PUBLIC		= 	(short)0x0001;
	private final static short	C_ACC_FINAL			= 	(short)0x0010;
	private final static short	C_ACC_INTERFACE		= 	(short)0x0200;
	private final static short	C_ACC_ABSTRACT		= 	(short)0x0400;
	private final static short	C_ACC_SHAREABLE		= 	(short)0x0800;

	// Method/field flags
	private final static short	MF_ACC_PUBLIC		= 	(short)0x0001;
	private final static short	MF_ACC_PROTECTED	= 	(short)0x0004;
	private final static short	MF_ACC_STATIC		= 	(short)0x0008;
	private final static short	MF_ACC_FINAL		= 	(short)0x0010;
	private final static short	MF_ACC_ABSTRACT		= 	(short)0x0400;

	private CPoolInfo getCPoolInstance(DataInputStream dis) throws java.io.IOException {
		byte tag = dis.readByte();
		switch (tag) {
			case CONSTANT_UTF8:
				return new CPoolInfoUTF8(dis);
			case CONSTANT_INTEGER:
				return new CPoolInfoInteger(dis);
			case CONSTANT_CLASSREF:
				return new CPoolInfoClassRef(dis);
			case CONSTANT_PACKAGE:
				return new CPoolInfoPackage(dis);
			default:
				Util.err(Util.toHex(tag) + " is an unknown constant type");
				System.exit(-1);
				return null;
		}
	}

	private abstract class CPoolInfo {
		private byte 	_tag;
		private String	_tagName;

		protected CPoolInfo() {
		}

		protected CPoolInfo(byte tag) {
			_tag = tag;
			switch (tag) {
				case CONSTANT_UTF8:
					_tagName = "CONSTANT_UTF8";
					break;
				case CONSTANT_INTEGER:
					_tagName = "CONSTANT_INTEGER";
					break;
				case CONSTANT_CLASSREF:
					_tagName = "CONSTANT_CLASSREF";
					break;
				case CONSTANT_PACKAGE:
					_tagName = "CONSTANT_PACKAGE";
					break;
				default:
					Util.err(Util.toHex(tag) + " is an unknown constant type");
					System.exit(-1);
					break;
			}
		}

		private void dump(short i) {
			System.out.println("\tConstant pool info "
				+ Util.toHex(i));
			System.out.println("\t\tType: "
				+ Util.toHex(_tag) + " (" + _tagName + ")");
		}
	}

	private class CPoolInfoPackage extends CPoolInfo {
		private byte	_flags;
		private short	_name_index;
		private byte	_minor;
		private byte	_major;
		private byte	_aid_length;
		private byte	_aid[];

		private CPoolInfoPackage(DataInputStream dis) throws java.io.IOException {
			super(CONSTANT_PACKAGE);
			_flags			= dis.readByte();
			_name_index		= dis.readShort();
			_minor			= dis.readByte();
			_major			= dis.readByte();
			_aid_length		= dis.readByte();
			if (_aid_length != 0) {
				_aid = new byte[_aid_length];
				File.readBytes(dis, _aid);
			}
		}

		private void dump(short i) {
			super.dump(i);
			System.out.println("\t\tFlags: "
				+ Util.toHex(_flags) + getFlags());
			System.out.println("\t\tName index: "
				+ Util.toHex(_name_index));
			System.out.println("\t\tMinor: "
				+ _minor);
			System.out.println("\t\tMajor: "
				+ _major);
			System.out.println("\t\tAID length: "
				+ _aid_length);
			System.out.println("\t\tAID: "
				+ Util.toHex(_aid, 0, _aid.length));
		}

		private String getFlags() {
			StringBuffer sb = new StringBuffer(" ( ");
			if ((_flags & P_ACC_LIBRARY)!=0) {
				sb.append("ACC_LIBRARY ");
			}
			sb.append(")");
			return sb.toString();
		}
	}

	private class CPoolInfoClassRef extends CPoolInfo {
		private short	_name_index;

		private CPoolInfoClassRef(DataInputStream dis) throws java.io.IOException {
			super(CONSTANT_CLASSREF);
			_name_index = dis.readShort();
		}

		private void dump(short i) {
			super.dump(i);
			System.out.println("\t\tName index: "
				+ Util.toHex(_name_index));
		}
	}

	private class CPoolInfoInteger extends CPoolInfo {
		private int		_bytes;

		private CPoolInfoInteger(DataInputStream dis) throws java.io.IOException {
			super(CONSTANT_INTEGER);
			_bytes = dis.readInt();
		}

		private void dump(short i) {
			super.dump(i);
			System.out.println("\t\tBytes: "
				+ _bytes);
		}
	}

	private class CPoolInfoUTF8 extends CPoolInfo {
		private short	_length;
		private byte	_bytes[];

		private CPoolInfoUTF8(DataInputStream dis) throws java.io.IOException {
			super(CONSTANT_UTF8);
			_length = dis.readShort();
			if (_length != 0) {
				_bytes = new byte[_length];
				File.readBytes(dis, _bytes);
			}
		}

		private void dump(short i) {
			super.dump(i);
			System.out.println("\t\tLength: "
				+ Util.toHex(_length));
			System.out.println("\t\tBytes: "
				+ new String(_bytes, 0, _bytes.length));
		}
	}

	private class ClassInfo {

		private class FieldInfo {

			private class AttrInfo {
				private short	_a_attr_name_index;
				private int		_a_attr_length;
				private short	_a_constant_value_index;

				private AttrInfo(DataInputStream dis) throws java.io.IOException {
					_a_attr_name_index			= dis.readShort();
					_a_attr_length				= dis.readInt();
					_a_constant_value_index		= dis.readShort();
				}

				private void dump(short i) {
					System.out.println("\t\t\t\tAttribute #"
						+ Util.toHex(i));
					System.out.println("\t\t\t\t\tName index: "
						+ Util.toHex(_a_attr_name_index));
					System.out.println("\t\t\t\t\tLength: "
						+ _a_attr_length);
					System.out.println("\t\t\t\t\tValue: "
						+ Util.toHex(_a_constant_value_index));
				}

				private String getValue() {
					int value = ((CPoolInfoInteger)_cpool[_a_constant_value_index])._bytes;
					return Util.toHex(value);
				}
			}

			private short		_f_token;
			private short		_f_access_flags;
			private short		_f_name_index;
			private short		_f_descriptor_index;
			private short		_f_attribute_count;
			private AttrInfo	_f_attrs[];

			private FieldInfo(DataInputStream dis) throws java.io.IOException {
				_f_token			= (short)((short)0x00FF & (byte)dis.readByte());
				_f_access_flags		= dis.readShort();
				_f_name_index		= dis.readShort();
				_f_descriptor_index	= dis.readShort();
				_f_attribute_count	= dis.readShort();
				if (_f_attribute_count != 0) {
					_f_attrs = new AttrInfo[_f_attribute_count];
					for (short i=0;i<_f_attrs.length; i++) {
						_f_attrs[i] = new AttrInfo(dis);
					}
				}
			}

			private void dump(short i) {
				System.out.println("\t\t\tField #"
					+ Util.toHex(i));
				System.out.println("\t\t\t\tToken: "
					+ Util.toHex(_f_token));
				System.out.println("\t\t\t\tFlags: "
					+ Util.toHex(_f_access_flags) + getFlags(_f_access_flags));
				System.out.println("\t\t\t\tName index: "
					+ Util.toHex(_f_name_index));
				System.out.println("\t\t\t\tDescriptor index: "
					+ Util.toHex(_f_descriptor_index));

				System.out.println("\t\t\t\tAttribute count: "
					+ Util.toHex(_f_attribute_count));
				if (_f_attribute_count != 0) {
					for (short j=0;j<_f_attrs.length; j++) {
						_f_attrs[j].dump(j);
					}
				}
			}

			private String getDefinition() {
				StringBuffer 	sb = new StringBuffer();
				// Get flags
				if ((_f_access_flags & MF_ACC_PUBLIC)!=0) {
					sb.append("public ");
				}
				if ((_f_access_flags & MF_ACC_PROTECTED)!=0) {
					sb.append("protected ");
				}
				if ((_f_access_flags & MF_ACC_FINAL)!=0) {
					sb.append("final ");
				}
				if ((_f_access_flags & MF_ACC_STATIC)!=0) {
					sb.append("static ");
				}
				// Get descriptor
				String desc = getDescription(
					new String(((CPoolInfoUTF8)_cpool[_f_descriptor_index])._bytes));
				sb.append(desc);
				sb.append(" ");
				// Get name
				sb.append (new String(((CPoolInfoUTF8)_cpool[_f_name_index])._bytes));
				// Get default value, if any
				if (_f_attribute_count != 0) {
					// JCVM says that attribute count is 1 for final static fields,
					// 0 for all others
					sb.append(" = (");
					sb.append(desc);
					sb.append(")");
					sb.append(_f_attrs[0].getValue());
				}
				return sb.toString();
			}

			private boolean isStatic() {
				return ((_f_access_flags & MF_ACC_STATIC)!=0);
			}
		}

		private class MethodInfo {
			private short		_m_token;
			private short		_m_access_flags;
			private short		_m_name_index;
			private short		_m_descriptor_index;

			private boolean 	_m_isConstructor;

			private MethodInfo(DataInputStream dis) throws java.io.IOException {
				_m_token				= (short)((short)0x00FF & (byte)dis.readByte());
				_m_access_flags			= dis.readShort();
				_m_name_index			= dis.readShort();
				_m_descriptor_index		= dis.readShort();
			}

			private void dump(short i) {
				System.out.println("\t\t\tMethod #"
					+ Util.toHex(i));
				System.out.println("\t\t\t\tToken: "
					+ Util.toHex(_m_token));
				System.out.println("\t\t\t\tFlags: "
					+ Util.toHex(_m_access_flags) + getFlags(_m_access_flags));
				System.out.println("\t\t\t\tName index: "
					+ Util.toHex(_m_name_index));
				System.out.println("\t\t\t\tDescriptor index: "
					+ Util.toHex(_m_descriptor_index));
			}

			private String getMethodName() {
				return new String(((CPoolInfoUTF8)_cpool[_m_name_index])._bytes);
			}

			private String getDefinition() {
				StringBuffer sb = new StringBuffer();
				// Get flags
				if ((_m_access_flags & MF_ACC_PUBLIC)!=0) {
					sb.append("public ");
				}
				if ((_m_access_flags & MF_ACC_PROTECTED)!=0) {
					sb.append("protected ");
				}
				if ((_m_access_flags & MF_ACC_ABSTRACT)!=0) {
					sb.append("abstract ");
				}
				if ((_m_access_flags & MF_ACC_FINAL)!=0) {
					sb.append("final ");
				}
				if ((_m_access_flags & MF_ACC_STATIC)!=0) {
					sb.append("static ");
				}
				// Get signature
				String name = getMethodName();
				if (name.compareTo("<init>") == 0) {
					name = getClassName();
				}
				String desc = new String(((CPoolInfoUTF8)_cpool[_m_descriptor_index])._bytes);
				sb.append(getSignature(name, desc));
				return sb.toString();
			}

			private boolean isConstructor() {
				if (!_m_isConstructor) {
					if (getMethodName().compareTo("<init>") == 0) {
						_m_isConstructor = true;
					}
				}
				return _m_isConstructor;
			}

			private boolean isStatic() {
				return ((_m_access_flags & MF_ACC_STATIC)!=0);
			}
		}

		private byte		_token;
		private short		_access_flags;
		private short		_name_index;
		private short		_export_supers_count;
		private short		_supers[];
		private byte		_export_interfaces_count;
		private short		_interfaces[];
		private short		_export_fields_count;
		private FieldInfo	_fields[];
		private short		_export_methods_count;
		private MethodInfo	_methods[];

		private boolean		_interface_filter[];

		private ClassInfo(DataInputStream dis) throws java.io.IOException {
			_token					= dis.readByte();
			_access_flags			= dis.readShort();
			_name_index				= dis.readShort();

			_export_supers_count	= dis.readShort();
			_supers = new short[_export_supers_count];
			for (short i=0; i<_supers.length; i++) {
					_supers[i] = dis.readShort();
				}

			_export_interfaces_count = dis.readByte();
			if (_export_interfaces_count != 0) {
				_interfaces 		= new short[_export_interfaces_count];
				for (byte i=0; i<_interfaces.length; i++) {
					_interfaces[i] = dis.readShort();
				}
			}

			_export_fields_count	= dis.readShort();
			if (_export_fields_count != 0) {
				_fields = new FieldInfo[_export_fields_count];
				for (short i=0; i<_fields.length; i++) {
					_fields[i]	= new FieldInfo(dis);
				}
			}

			boolean isInterface = isInterface();
			_export_methods_count	= dis.readShort();
			if (_export_methods_count != 0) {
				_methods = new MethodInfo[_export_methods_count];
				for (short i=0; i<_methods.length; i++) {
					_methods[i]	= new MethodInfo(dis);
				}
			}
		}

		private void dump(short i) {
			System.out.println("\tClass #"
				+ i);
			System.out.println("\t\tToken: "
				+ Util.toHex(_token));
			System.out.println("\t\tFlags: "
				+ Util.toHex(_access_flags) + getClassFlags());
			System.out.println("\t\tName index: "
				+ Util.toHex(_name_index));

			System.out.println("\t\tSupers count: "
				+ Util.toHex(_export_supers_count));
			for (short j=0; j<_export_supers_count; j++) {
				System.out.println("\t\t\tSuper #"
					+ Util.toHex(j) + ": " + Util.toHex(_supers[j]));
			}

			System.out.println("\t\tInterface count: "
				+ Util.toHex(_export_interfaces_count));
			if (_export_interfaces_count != 0) {
				for (short j=0; j<_export_interfaces_count; j++) {
					System.out.println("\t\t\tInterface #"
						+ Util.toHex(j) + ": " + Util.toHex(_interfaces[j]));
				}
			}

			System.out.println("\t\tField count: "
				+ Util.toHex(_export_fields_count));
			if (_export_fields_count != 0) {
				for (short j=0; j<_export_fields_count; j++) {
					_fields[j].dump(j);
				}
			}

			System.out.println("\t\tMethod count: "
				+ Util.toHex(_export_methods_count));
			if (_export_methods_count != 0) {
				for (short j=0; j<_export_methods_count; j++) {
					_methods[j].dump(j);
				}
			}
		}

		private short getMaxMethodToken() {
			short maxToken = -1;
			if (_export_methods_count != 0) {
				for (short i=0; i<_export_methods_count; i++) {
					// Ignore static methods
					if ((_methods[i]._m_access_flags & MF_ACC_STATIC) == 0) {
						if (_methods[i]._m_token > maxToken) {
							maxToken = _methods[i]._m_token;
						}
					}
				}
			}
			return maxToken;
		}

		private short getMaxFieldToken() {
			short maxToken = -1;
			if (_export_fields_count != 0) {
				for (short i=0; i<_export_fields_count; i++) {
					// Ignore static fields
					if ((_fields[i]._f_access_flags & MF_ACC_STATIC) == 0) {
						if (_fields[i]._f_token > maxToken) {
							maxToken = _fields[i]._f_token;
						}
					}
				}
			}
			return maxToken;
		}

		private String getMethodName(byte token) {
			if (_export_methods_count != 0) {
				for (short i=0; i<_export_methods_count; i++) {
					if (_methods[i]._m_token == token) {
						return _methods[i].getMethodName();
					}
				}
			}
			return null;
		}


		private String getFlags(short flags) {
			StringBuffer sb = new StringBuffer(" ( ");
			if ((flags & MF_ACC_PUBLIC)!=0) {
				sb.append("ACC_PUBLIC ");
			}
			if ((flags & MF_ACC_PROTECTED)!=0) {
				sb.append("ACC_PROTECTED ");
			}
			if ((flags & MF_ACC_STATIC)!=0) {
				sb.append("ACC_STATIC ");
			}
			if ((flags & MF_ACC_FINAL)!=0) {
				sb.append("ACC_FINAL ");
			}
			if ((flags & MF_ACC_ABSTRACT)!=0) {
				sb.append("ACC_ABSTRACT ");
			}
			sb.append(")");
			return sb.toString();
		}

		private String getClassFlags() {
			StringBuffer sb = new StringBuffer(" ( ");
			if ((_access_flags & C_ACC_PUBLIC	)!=0) {
				sb.append("ACC_PUBLIC ");
			}
			if ((_access_flags & C_ACC_FINAL)!=0) {
				sb.append("ACC_FINAL ");
			}
			if ((_access_flags & C_ACC_INTERFACE)!=0) {
				sb.append("ACC_INTERFACE ");
			}
			if ((_access_flags & C_ACC_ABSTRACT)!=0) {
				sb.append("ACC_ABSTRACT ");
			}
			if ((_access_flags & C_ACC_SHAREABLE)!=0) {
				sb.append("ACC_SHAREABLE ");
			}
			sb.append(")");
			return sb.toString();
		}

		private boolean isInterface() {
			return ((_access_flags & C_ACC_INTERFACE)!=0);
		}

		private String getClassName() {
			CPoolInfoClassRef c
				= (CPoolInfoClassRef)_cpool[_name_index];
			String s
				= new String(((CPoolInfoUTF8)_cpool[c._name_index])._bytes);
			return s.substring(s.lastIndexOf('/')+1);
		}

		private class SuperClassInfo {
			private String 	_name;
			private short  	_max_method_token;
			private short  	_max_field_token;
		}

		private SuperClassInfo getSuperClass() {
			SuperClassInfo 		sc = new SuperClassInfo();
			CPoolInfoClassRef 	c;
			String 				s, superClass = null;
			short				maxToken = -1, currentMaxToken = -1;

			// If current class is an interface, there is no superclass
			if (isInterface()) {
				sc._name				= null;
				sc._max_method_token 	= -1;
				sc._max_field_token		= -1;
			}
			else {
				// JCVM spec says that super classes are in no particular order.
				// However, the Sun converter orders them from least to most specific.
				// Anyway, let's be careful :-)
				ClassInfo ci = null;
				for (short i=0; i<_export_supers_count; i++) {
					// Get superclass name
					s = getFullClassName(_supers[i]);
					// Ignore java.lang.Object
					if (s.compareTo("java.lang.Object") == 0) {
						continue;
					}
					// Get class info
					ci = getClassInfo(s);
					if (ci == null) {
						// FUBAR. We don't have the export file...
						// Return fully qualified name and set tokens to 0.
						sc._name				= s;
						sc._max_method_token 	= -1;
						sc._max_field_token		= -1;
					}
					else {
						// Get max method token.
						currentMaxToken = ci.getMaxMethodToken();
						if (currentMaxToken > maxToken) {
							// This class is higher in the class tree, so keep it
							sc._name				= trimPackageName(s);
							sc._max_method_token 	= currentMaxToken;
							sc._max_field_token		= ci.getMaxFieldToken();
						}
					}
				}
			}
			return sc;
		}

		private void filterInterfaces(boolean all) {
			_interface_filter = new boolean[_export_interfaces_count];
			for (byte i=0; i<_interface_filter.length; i++) {
				_interface_filter[i] = true;
			}
			if (all) {
				return;
			}
			for (byte i=0; i<_interface_filter.length; i++) {
				for (byte j=0; j<i; j++) {
					if (isSuperInterface(_interfaces[i], _interfaces[j])) {
						_interface_filter[i] = false;
					}
					if (isSuperInterface(_interfaces[j], _interfaces[i])) {
						_interface_filter[j] = false;
					}
				}
			}
		}

		private boolean isSuperInterface(short i1, short i2) {
			String 		s1 	= getFullClassName(i1);
			String 		s2 	= getFullClassName(i2);
			ClassInfo	ci2;

			s1 = trimPackageName(s1);

			// System.out.println("Analyzing " + s1 + " and " + s2);

			// Find info on potential super interface
			ci2 = getClassInfo(s2);
			if (ci2 == null) {
				// Can't go any further
				return false;
			}
			String supers2 = ci2.getInterfaces(true);

			//System.out.println(s2 + " supers: " + supers2);

			// If interface2 doesn't have any super interface,
			// interface1 can't be one of them. Duh.
			if (supers2 == null) {
				//System.out.println(s1 + " isn't a super interface of " + s2);
				return false;
			}
			// If interface1 is listed in the super interfaces of interface2,
			// interface1 is one of them. Yahoo!
			if (   (supers2.compareTo(s1) == 0)
				|| (supers2.regionMatches(0, s1+",", 0, s1.length()))
				|| (supers2.endsWith(s1))) {
				//System.out.println(s1 + " is a super interface of " + s2);
				return true;
			}
			return false;
		}

		private String getInterfaces(boolean all) {
			StringBuffer 		sb = new StringBuffer();
			String 				s;
			if (_export_interfaces_count != 0) {
				filterInterfaces(all);
				for (byte i=0; i<_export_interfaces_count; i++) {
					if (_interface_filter[i]) {
						s = getFullClassName(_interfaces[i]);
						sb.append(trimPackageName(s));
						if (i != _export_interfaces_count-1) {
							sb.append(", ");
						}
					}
				}
				return sb.toString();
			}
			return null;
		}

		private boolean definesMethod(MethodInfo m) {
			String s = m.getDefinition();
			if (_export_methods_count != 0) {
				for (short i=0; i<_export_methods_count; i++) {
					if (s.compareTo(_methods[i].getDefinition()) == 0) {
						return true;
					}
				}
			}
			return false;
		}

		private String getDefinition(String superClass, boolean all) {
			StringBuffer 	sb = new StringBuffer();
			String			s;
			if ((_access_flags & C_ACC_PUBLIC	)!=0) {
				sb.append("public ");
			}
			if ((_access_flags & C_ACC_FINAL)!=0) {
				sb.append("final ");
			}
			if (isInterface()) {
				// Interface
				sb.append("interface ");
				sb.append(getClassName());
				s = getInterfaces(all);
				if (s != null) {
					sb.append(" extends ");
					sb.append(s);
				}
			}
			else {
				// Class
				if ((_access_flags & C_ACC_ABSTRACT)!=0) {
					sb.append("abstract ");
				}
				sb.append("class ");
				sb.append(getClassName());
				if ( (superClass != null) && (superClass.compareTo("") != 0) ) {
					sb.append(" extends ");
					sb.append(superClass);
				}
				s = getInterfaces(all);
				if (s != null) {
					sb.append(" implements ");
					sb.append(s);
				}
			}
			return sb.toString();
		}
	}

	private int			_magic;
	private byte		_minor;
	private byte		_major;
	private short		_cpool_count;
	private CPoolInfo	_cpool[];
	private short		_this_package;
	private byte		_export_class_count;
	private ClassInfo	_classes[];

	public ExportFile(String name, ExportJarReader ejr, DataInputStream dis) throws java.io.IOException {
		super(name, ejr, EXP_FILE);
		_magic				= dis.readInt();
		if (_magic != 0xFACADE) {
			Util.err(name + " isn't an export file");
		}
		_minor				= dis.readByte();
		_major				= dis.readByte();
		_cpool_count		= dis.readShort();
		if (_cpool_count != 0) {
			_cpool = new CPoolInfo[_cpool_count];
			for (short i=0; i<_cpool.length; i++) {
				_cpool[i] = getCPoolInstance(dis);
			}
		}
		_this_package		= dis.readShort();
		_export_class_count	= dis.readByte();
		if (_export_class_count != 0) {
			_classes		= new ClassInfo[_export_class_count];
			for (byte i=0; i<_classes.length; i++) {
				_classes[i]	= new ClassInfo(dis);
			}
		}
	}

	public String getPackageName() {
		CPoolInfoPackage p
			= (CPoolInfoPackage)_cpool[_this_package];
		String s
			= new String(((CPoolInfoUTF8)_cpool[p._name_index])._bytes);
		return s.replace('/', '.');
	}

	private String getPackageAid() {
		CPoolInfoPackage p
			= (CPoolInfoPackage)_cpool[_this_package];
		return Util.toHex(p._aid, 0, p._aid.length);
	}

	private ClassInfo getInterfaceInfo(short inter) {
		// Find interface name first
		return getClassInfo(getFullClassName(inter));
	}

	public String getClassName(byte token) {
		return _classes[token].getClassName();
	}

	public String getFullMethodName(String packName, String className, byte token) {
		return getClassInfo(packName+"."+className).getMethodName(token);
	}

	private String getFullClassName(short index) {
		CPoolInfoClassRef 	cp = (CPoolInfoClassRef)_cpool[index];
		String 				s1 	= new String(((CPoolInfoUTF8)_cpool[cp._name_index])._bytes);
		return s1.replace('/', '.');
	}

	private ClassInfo getClassInfo(String s) {
		String 		pack 	= s.substring(0, s.lastIndexOf('.'));
		ExportFile	exp;
		ClassInfo	ci		= null;

		//System.out.println("Looking for class info on " + s);

		// Find class info for each interface
		if (pack.compareTo(getPackageName()) == 0) {
			//System.out.println("Looking in " + getPackageName());
			// If class is in this package, look no further
			for (byte i=0; i<_export_class_count; i++) {
				s = trimPackageName(s);
				if (s.compareTo(_classes[i].getClassName()) == 0) {
					//System.out.println("Found locally");
					ci = _classes[i];
					break;
				}
			}
		}
		else {
			// If not, we need external export files
			exp = exports.findPackage(pack);
			if (exp != null) {
				ci 	= exp.getClassInfo(s);
			}
		}
		if (ci == null) {
			Util.warn("can't find information on "	+ s);
		}
		return ci;
	}

	public void dump() {
		super.dump();
		System.out.println("Magic: "
			+ Util.toHex(_magic));
		System.out.println("Minor: "
			+ _minor);
		System.out.println("Major: "
			+ _major);

		System.out.println("Constant pool count: "
			+ Util.toHex(_cpool_count));
		if (_cpool_count != 0) {
			for (short i=0; i<_cpool_count; i++) {
				_cpool[i].dump(i);
			}
		}
		System.out.println("This package: "
			+ Util.toHex(_this_package));

		System.out.println("Export class count: "
			+ Util.toHex(_export_class_count));
		if (_export_class_count != 0) {
			for (short i=0; i<_export_class_count; i++) {
				_classes[i].dump(i);
			}
		}
	}

	public void show(boolean all) {
		super.show(all);
		// Get package information
		CPoolInfoPackage p = (CPoolInfoPackage)_cpool[_this_package];
		String s = new String(((CPoolInfoUTF8)_cpool[p._name_index])._bytes);
		System.out.println("Package name:\t" + getPackageName());
		System.out.println("Package AID:\t" + getPackageAid());
		System.out.println("Major, minor:\t"
			+ _major + "," + _minor);
		System.out.println("Class count:\t"
			+ _export_class_count);
		System.out.println();

		// Get class information
		for (byte i=0; i<_export_class_count; i++) {
			ClassInfo 					ci = _classes[i];
			ClassInfo.SuperClassInfo 	sc = ci.getSuperClass();
			System.out.println(ci.getDefinition(sc._name, all));

			// Get field information
			for (short j=0; j<ci._export_fields_count; j++) {
				ClassInfo.FieldInfo f = ci._fields[j];
				// System.out.println(f._f_token + " " + sc._max_method_token);
				System.out.println("\t" + f.getDefinition());
			}

			// Get method information
			for (short j=0; j<ci._export_methods_count; j++) {
				ClassInfo.MethodInfo m = ci._methods[j];
				// System.out.println(m._m_token + " " + sc._max_method_token);
				if (all) {
					System.out.println("\t" + m.getDefinition());
				}
			 	else {
			 		// Always show constructors
					if (m.isConstructor()) {
				 		System.out.println("\t" + m.getDefinition());
				 		continue;
				 	}

			 		boolean defined = false, hidden = false;
			 		if (m.isStatic()) {
			 			defined = true;
			 			// Hide static methods defined in super classes
				 		for (byte k=0; k<ci._export_supers_count; k++) {
		 					ClassInfo cl = getInterfaceInfo(ci._supers[k]);
		 					if (cl == null) {
		 						// We're missing information on an external package,
		 						// show method by default
		 						defined = true;
		 						break;
		 					}
							// If method is defined in a super class, hide it
		 					if (cl.definesMethod(m)) {
		 						hidden = true;
							}
						}
				 	}
				 	else {
				 		// Hide virtual methods defined in super interfaces
				 		for (byte k=0; k<ci._export_interfaces_count; k++) {
		 					ClassInfo inter 	= getInterfaceInfo(ci._interfaces[k]);
		 					if (inter == null) {
		 						// We're missing information on an external package,
		 						// show method by default
		 						defined = true;
		 						break;
		 					}

		 					String interName	= inter.getClassName();
		 					String interList	= ci.getInterfaces(false);

		 					//System.out.println("Inspecting " + interName + ", list=" + interList);

		 					if (inter.definesMethod(m)) {
		 						//System.out.println(interName + " defines " + m.getDefinition());
		 						defined = true;
								// XXX is this OK?
			 					if (interList.regionMatches(0, interName, 0, interName.length())) {
			 						//System.out.println("Hiding " + m.getDefinition());
			 						hidden = true;
			 						break;
			 					}
			 				}
						}
					}
					if (!hidden) {
						if (defined) {
							System.out.println("\t" + m.getDefinition());
							continue;
						}
				 		if (m._m_token > sc._max_method_token) {
				 			System.out.println("\t" + m.getDefinition());
				 			continue;
				 		}
				 	}
				}
			}
			System.out.println();
		}
	}

	private String trimPackageName(String s) {
		// If package name matches, remove it
		if ( s.startsWith(getPackageName())) {
			return s.substring(s.lastIndexOf('.')+1);
		}
		else {
			return s;
		}
	}

	private String getDescription(String s) {
		//System.out.println("Unmangling " +s);
		if (s.compareTo("V") == 0) {
			return "void";
		}
		if (s.compareTo("Z") == 0) {
			return "boolean";
		}
		if (s.compareTo("[Z") == 0) {
			return "boolean[]";
		}
		if (s.compareTo("B") == 0) {
			return "byte";
		}
		if (s.compareTo("[B") == 0) {
			return "byte[]";
		}
		if (s.compareTo("S") == 0) {
			return "short";
		}
		if (s.compareTo("[S") == 0) {
			return "short[]";
		}
		if (s.compareTo("I") == 0) {
			return "int";
		}
		if (s.compareTo("[I") == 0) {
			return "int[]";
		}
		// Handle references
		if (s.charAt(0) == 'L') {
			String 	cla = s.substring(1, s.indexOf(';'));
			return trimPackageName(cla.replace('/', '.'));
		}
		if (s.charAt(0) == '[') {
			String 	cla = getDescription(s.substring(1));
			return cla+"[]";
		}
		// Default
		Util.err(s + " is an unknown type descriptor");
		System.exit(-1);
		return null;
	}

	private String getSignature(String name, String desc) {
		StringBuffer sb = new StringBuffer();

		//System.out.println("Demangling " + name + " " + desc);
		// Get return value
		sb.append(getDescription(desc.substring(desc.lastIndexOf(')')+1)));
		sb.append(" ");
		sb.append(name);
		sb.append("(");
		int current = 1;
		while (true) {
			if (desc.charAt(current) == ')') {
				if (sb.toString().lastIndexOf(',') != -1) {
					sb.delete(sb.length()-2, sb.length());
				}
				sb.append(")");
				break;
			}
			else
			if (desc.charAt(current) == '[') {
				if (desc.charAt(current+1) == 'L') {
					int mark = desc.indexOf(';', current+1);
					sb.append(getDescription(desc.substring(current+1, mark+1)));
					current = mark+1;
				}
				else {
					sb.append(getDescription(desc.substring(current, current+2)));
					current += 2;
				}
			}
			else
			if (desc.charAt(current) == 'L') {
				int mark = desc.indexOf(';', current);
				sb.append(getDescription(desc.substring(current, mark+1)));
				current = mark+1;
			}
			else {
				sb.append(getDescription(desc.substring(current, current+1)));
				current ++;
			}
			sb.append(", ");
		}
		return sb.toString();
	}
}