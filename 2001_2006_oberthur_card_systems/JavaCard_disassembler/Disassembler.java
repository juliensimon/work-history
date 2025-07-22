package fr.netattitude.cappuccino;

class Disassembler implements ComponentConst {

	private JCStack st;

	public Disassembler() {
		st = new JCStack();
	}

	public void doIt(byte[] bArray, int level, byte argCount) {
		short inc = 0;
		short cpoolIndex, offset, sval;
		byte tmp;
		String s;

		st.init(argCount);

		//Util.toHexLines(bArray, offset, length, level);

		for (short i=0; i<bArray.length; i++) {
			short ins = (short)(bArray[i] & 0x00FF);
			st.dump();
			Util.printTab(Util.toHex(i) + ": " + Bytecode.bc[ins], level);
			//System.out.println("disassembling " + ins);

			switch (bArray[i]) {

				case (byte)0x10:	// bspush
					inc = 1;
					System.out.println(" " + Util.toHex(bArray[i+1]));
					st.push(Util.toHex((bArray[i+1])));
					break;

				case (byte)0x11:	// sspush
					inc = 2;
					sval = Util.makeShort(bArray[i+1], bArray[i+2]);
					System.out.println(" " + Util.toHex(sval));
					st.push(Util.toHex(sval));
					break;

				case (byte)0x1C:	// sload_<n>
				case (byte)0x1D:
				case (byte)0x1E:
				case (byte)0x1F:
					inc = 0;
					System.out.println();
					st.push(st.load((byte)(bArray[i]-0x1C)));
					break;

				case (byte)0x29:	// sstore
					inc = 1;
					System.out.println(" " + Util.toHex(bArray[i+1]));
					st.store(bArray[i+1], (String)st.pop());
					break;

				case (byte)0x2F:	// sstore_<n>
				case (byte)0x30:
				case (byte)0x31:
				case (byte)0x32:
					inc = 0;
					System.out.println();
					st.store((byte)(bArray[i]-0x2F), (String)st.pop());
					break;

				case (byte)0x4F:	// sshr
					inc = 0;
					System.out.println();
					s = (String)st.pop();
					st.push((String)st.pop() + " >> " + s);
					break;

				case (byte)0x53:	// sand
					inc = 0;
					System.out.println();
					st.push((String)st.pop() + " & " + (String)st.pop());
					break;

				case (byte)0x5B:	// s2b
					inc = 0;
					System.out.println();
					st.push("(byte)(" + (String)st.pop() + ")");
					break;

				case (byte)0x60:	// if<cond>
				case (byte)0x61:
				case (byte)0x62:
				case (byte)0x63:
				case (byte)0x64:
				case (byte)0x65:
				case (byte)0x66: 	// ifnull
				case (byte)0x67:	// ifnonull
				case (byte)0x68:	// if_acmp<cond>
				case (byte)0x69:	// if_acmp<cond>
				case (byte)0x6A:	// if_scmp<cond>
				case (byte)0x6B:
				case (byte)0x6C:
				case (byte)0x6D:
				case (byte)0x6E:
				case (byte)0x6F:
					inc = 1;
					System.out.print(" " + Util.toHex(bArray[i+1]));
					// Show branch address
					System.out.println(" // " + Util.toHex((short)(i+bArray[i+1])));
					st.pop();
					break;

				case (byte)0x70:
					inc = 1;
					System.out.print(" " + Util.toHex(bArray[i+1]));
					// Show branch address
					System.out.println(" // " + Util.toHex((short)(i+bArray[i+1])));
					break;

				case (byte)0x73:	// stableswitch
					inc = stableswitch(bArray, i, level);
					break;

				case (byte)0x75:	// slookupswitch
					inc = slookupswitch(bArray, i, level);
					break;

				case (byte)0x74:	// itableswitch
				case (byte)0x76:	// ilookupswitch
					System.out.println("damn! " + Util.toHex(ins));
					System.exit(-1);
					break;

				case (byte)0x8B:	// invokevirtual
					inc = 2;
					cpoolIndex = Util.makeShort(bArray[i+1], bArray[i+2]);
					System.out.print(" " + Util.toHex(cpoolIndex));
					System.out.println(" // " + CapFile.getVirtualMethodName(cpoolIndex));
					break;

				case (byte)0x8C:	// invokespecial
				case (byte)0x8D:	// invokestatic
					inc = 2;
					cpoolIndex = Util.makeShort(bArray[i+1], bArray[i+2]);
					System.out.print(" " + Util.toHex(cpoolIndex));
					System.out.println(" // " + CapFile.getMethodName(cpoolIndex));
					break;

				case (byte)0x8E:	// invokeinterface
					{
						inc = 4;
						byte token = bArray[i+1];
						cpoolIndex = Util.makeShort(bArray[i+2], bArray[i+3]);

						String className= CapFile.getInterfaceName(cpoolIndex);
						System.out.print(" " + Util.toHex(token)
							+ " " + Util.toHex(cpoolIndex) + " " + Util.toHex(bArray[i+4]));
						System.out.println(" // " + className);
						System.exit(-1);
					}
					break;

				case (byte)0x8F:	// new
				case (byte)0x91:	// anewarray
					inc = 2;
					cpoolIndex = Util.makeShort(bArray[i+1], bArray[i+2]);
					System.out.print(" " + Util.toHex(cpoolIndex));
					System.out.println(" // " + CapFile.getClassName(cpoolIndex));
					break;

				case (byte)0x90:	// newarray
					inc = 1;
					System.out.print(" " + Util.toHex(bArray[i+1]));
					System.out.println(" // " + typeNames[bArray[i+1]]);
					break;

				case (byte)0x94:	// checkcast
				case (byte)0x95:	// instanceof
					inc = 3;
					switch (bArray[i+1]) {
						case BOOLEAN_A_TYPE:
						case BYTE_A_TYPE:
						case SHORT_A_TYPE:
						case INT_A_TYPE:
							System.out.print(" " + Util.toHex(bArray[i+1]));
							System.out.println(" // " + typeNames[bArray[i+1]]);
							break;

						default:
							cpoolIndex = Util.makeShort(bArray[i+2], bArray[i+3]);
							System.out.print(" " + Util.toHex(cpoolIndex));
							System.out.println(" // " + CapFile.getClassName(cpoolIndex));
							break;
					}
					break;


				case (byte)0x98: 	// if<cond>_w
				case (byte)0x99:
				case (byte)0x9A:
				case (byte)0x9B:
				case (byte)0x9C:
				case (byte)0x9D:
				case (byte)0x9E: 	// ifnull_w
				case (byte)0x9F:	// ifnonull_w
				case (byte)0xA0:	// if_acmp<cond>_w
				case (byte)0xA1:
				case (byte)0xA2: 	// ifscomp<cond>_w
				case (byte)0xA3:
				case (byte)0xA4:
				case (byte)0xA5:
				case (byte)0xA6:
				case (byte)0xA7:
					inc = 2;
					offset = Util.makeShort(bArray[i+1], bArray[i+2]);
					System.out.print(" " + Util.toHex(offset));
					// Show branch address
					System.out.println(" // " + Util.toHex((short)(i+offset)));
					break;

				case (byte)0xFE:
				case (byte)0xFF:
					Util.printlnTab("impdep ???", level);
					break;

				default:
					byte nargs = Bytecode.bc_ops[ins];

					switch (nargs) {
						case (byte)0:
							break;
						case (byte)2:
							System.out.print(" " + Util.toHex(Util.makeShort(bArray[i+1], bArray[i+2])));
							break;
						default:
							for (short j=0; j<nargs; j++) {
								System.out.print(" " + Util.toHex(bArray[i+j+1]));
							}
							break;
					}
					System.out.println();
					inc = nargs;
					break;
			}
			i += inc;
		}
	}

	private short stableswitch(byte[] bArray, short offset, int level) {
		short def	= Util.makeShort(bArray[offset+1], bArray[offset+2]);
		short lo 	= Util.makeShort(bArray[offset+3], bArray[offset+4]);
		short hi 	= Util.makeShort(bArray[offset+5], bArray[offset+6]);
		short count	= (short)(hi-lo+1);

		//Util.printTab(Util.toHex(offset) + ": " + Bytecode.bc[bArray[offset]], level);
		System.out.print(" " + Util.toHex(def) + "/" + Util.toHex(lo) + "/" + Util.toHex(hi) + " ");
		short base = (short)(offset+7);
		for (short j=0; j<count; j++) {
			short off = Util.makeShort(bArray[base+(2*j)], bArray[base+1+(2*j)]);
			System.out.print(" " + Util.toHex(off));
		}
		System.out.println();
		return (short)(6+(2*count));
	}

	private short slookupswitch(byte[] bArray, short offset, int level) {
		short def		= Util.makeShort(bArray[offset+1], bArray[offset+2]);
		short npairs 	= Util.makeShort(bArray[offset+3], bArray[offset+4]);

		//Util.printTab(Util.toHex(offset) + ": " + Bytecode.bc[bArray[offset]], level);
		System.out.print(" " + Util.toHex(def) + "/" + Util.toHex(npairs));
		short base = (short)(offset+5);
		for (short j=0; j<npairs; j++) {
			short match 	= Util.makeShort(bArray[base+(4*j)], 	bArray[base+1+(4*j)]);
			short off	 	= Util.makeShort(bArray[base+2+(2*j)], 	bArray[base+3+(2*j)]);
			System.out.print(" " + Util.toHex(match)+ "/" + Util.toHex(off));
		}
		System.out.println();
		return (short)(4+(2*npairs));
	}
}