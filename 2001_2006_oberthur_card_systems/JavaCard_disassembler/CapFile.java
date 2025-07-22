package fr.netattitude.cappuccino;

import java.io.*;
import java.util.jar.*;

abstract class CapFile extends File implements ComponentConst  {

	protected 	Component[]	_components;

	static ConstantPoolComponent	con = null;
	static ImportComponent			imp = null;
	static ClassComponent			cla = null;
	static MethodComponent			met = null;
	static DescriptorComponent 		des = null;

	public CapFile(String name, ExportJarReader ejr, byte type) {
		super(name, ejr, type);
		_components = new Component[COMPONENT_MAX];
	}

	public void dump() {
		super.dump();
	}

	protected void dumpComponents() {
		for (byte i=0; i<_components.length; i++) {
			if (_components[i] != null) {
				_components[i].dump();
			}
		}
	}

	protected void postProcess() throws java.io.IOException {
		// Did we find the Descriptor Component?
		for (int i=0;i<_components.length;i++) {
			if (_components[i] != null) {
				switch (_components[i].getTag()) {
					case COMP_CLASS:
						cla = (ClassComponent)_components[i];
						break;
					case COMP_METHOD:
						met = (MethodComponent)_components[i];
						break;
					case COMP_DESC:
						des = (DescriptorComponent)_components[i];
						break;
				}
			}
		}

		if (des != null) {
			// Post-process Class & Method components
			cla.postProcess();
			met.postProcess();
		}
	}

	public void show(boolean all) {

		super.show(all);

		for (int i=0;i<_components.length;i++) {
			if (_components[i] != null) {
				switch (_components[i].getTag()) {
					case COMP_IMPORT:
						imp = (ImportComponent)_components[i];
						break;
					case COMP_CONST:
						con = (ConstantPoolComponent)_components[i];
						break;
					case COMP_CLASS:
						cla = (ClassComponent)_components[i];
						break;
					case COMP_METHOD:
						met = (MethodComponent)_components[i];
						break;
					case COMP_DESC:
						des = (DescriptorComponent)_components[i];
						break;
				}
			}
		}

		if (des == null) {
			Util.err("Can't find Descriptor Component");
		}

		// Get each class in the Descriptor Component
		for (byte i=0; i< des.getTotalClassCount(); i++) {
			Disassembler	d = new Disassembler();
			byte[] 			bytecode;

			Util.printlnTab(des.getClassDefinition(i), 0);
			if (!des.isInterface(i)) {
				Util.printlnTab("// Fields", 1);
				for (short j=0; j<des.getFieldCount(i); j++) {
					Util.printlnTab(des.getFieldDefinition(i, j), 1);
				}
			}

			System.out.println();
			Util.printlnTab("// Methods", 1);
			for (short j=0; j<des.getMethodCount(i); j++) {
				Util.printlnTab(des.getMethodDefinition(i, j), 1);
				if (!des.isInterface(i)) {
					bytecode = des.getMethodBytecode(i,j);
					d.doIt(bytecode, 2, des.getMethodArgCount(i,j));
					System.out.println();
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public static String getClassName(short cpoolIndex) {
		StringBuffer	name 	= new StringBuffer();
		ClassRef 		cref 	= con.getClassRef(cpoolIndex);

		if (cref.isInternal()) {
			name.append(cla.getClassInfo(cref.getInternalRef()).getClassName());
		}
		else {
			String packName = imp.getName(cref.getPackageToken());
			ExportFile exp	= File.exports.findPackage(packName);
			if (exp == null) {
				Util.warn("can't find information on package " + packName);
			}
			else {
				name.append(packName);
				name.append(".");
				name.append(exp.getClassName(cref.getClassToken()));
			}
		}
		return name.toString();
	}

	public static String getInterfaceName(short cpoolIndex) {
		StringBuffer	name 	= new StringBuffer();
		ClassRef 		cref 	= con.getClassRef(cpoolIndex);

		if (cref.isInternal()) {
			name.append(cla.getInterfaceInfo(cref.getInternalRef()).getInterfaceName());
		}
		else {
			String packName = imp.getName(cref.getPackageToken());
			ExportFile exp	= File.exports.findPackage(packName);
			if (exp == null) {
				Util.warn("can't find information on package " + packName);
			}
			else {
				name.append(packName);
				name.append(".");
				name.append(exp.getClassName(cref.getClassToken()));
			}
		}
		return name.toString();
	}

	public static String getVirtualMethodName(short cpoolIndex) {
		StringBuffer	name 	= new StringBuffer();
		ClassRef 		cref 	= con.getClassRef(cpoolIndex);
		byte			token	= con.getToken(cpoolIndex);

		return getVirtualMethodName(cref, token);
	}

	private static String getVirtualMethodName(ClassRef cref, byte token) {
		StringBuffer	name 	= new StringBuffer();

		if (cref.isInternal()) {
			ClassInfo ci = cla.getClassInfo(cref.getInternalRef());
			if (ci.definesMethodToken(token)) {
				short offset = ci.getMethodOffset(token);
				name.append(des.getMethodName(offset));
			}
			else {
				name.append(getVirtualMethodName(ci.getSuperClass(), token));
			}
		}
		else {
			String packName = imp.getName(cref.getPackageToken());
			ExportFile exp	= File.exports.findPackage(packName);
			if (exp == null) {
				Util.warn("can't find information on package " + packName);
			}
			else {
				name.append(packName);
				name.append(".");
				String className = exp.getClassName(cref.getClassToken());
				name.append(className);
				name.append(".");
				name.append(exp.getFullMethodName(packName, className, token));
			}
		}
		name.append("()");
		return name.toString();
	}

	public static String getMethodName(short cpoolIndex) {
		StringBuffer	name 	= new StringBuffer();
		ClassRef 		cref 	= con.getClassRef(cpoolIndex);
		byte			token	= con.getToken(cpoolIndex);

		if (cref.isInternal()) {
				name.append(des.getMethodName(cref.getInternalRef()));
		}
		else {
			String packName = imp.getName(cref.getPackageToken());
			ExportFile exp	= File.exports.findPackage(packName);
			if (exp == null) {
				Util.warn("can't find information on package " + packName);
			}
			else {
				name.append(packName);
				name.append(".");
				String className = exp.getClassName(cref.getClassToken());
				name.append(className);
				name.append(".");
				name.append(exp.getFullMethodName(packName, className, token));
			}
		}
		name.append("()");
		return name.toString();
	}
}