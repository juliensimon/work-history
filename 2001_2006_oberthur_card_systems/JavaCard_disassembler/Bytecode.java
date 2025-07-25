package fr.netattitude.cappuccino;

class Bytecode {

	public static String bc[] = {
		"nop",
		"aconst_null",
		"sconst_m1",
		"sconst_0",
		"sconst_1",
		"sconst_2",
		"sconst_3",
		"sconst_4",
		"sconst_5",
		"iconst_m1",
		"iconst_0",
		"iconst_1",
		"iconst_2",
		"iconst_3",
		"iconst_4",
		"iconst_5",
		"bspush",
		"sspush",
		"bipush",
		"sipush",
		"iipush",
		"aload",
		"sload",
		"iload",
		"aload_0",
		"aload_1",
		"aload_2",
		"aload_3",
		"sload_0",
		"sload_1",
		"sload_2",
		"sload_3",
		"iload_0",
		"iload_1",
		"iload_2",
		"iload_3",
		"aaload",
		"baload",
		"saload",
		"iaload",
		"astore",
		"sstore",
		"istore",
		"astore_0",
		"astore_1",
		"astore_2",
		"astore_3",
		"sstore_0",
		"sstore_1",
		"sstore_2",
		"sstore_3",
		"istore_0",
		"istore_1",
		"istore_2",
		"istore_3",
		"aastore",
		"bastore",
		"sastore",
		"iastore",
		"pop",
		"pop2",
		"dup",
		"dup2",
		"dup_x",
		"swap_x",
		"sadd",
		"iadd",
		"ssub",
		"isub",
		"smul",
		"imul",
		"sdiv",
		"idiv",
		"srem",
		"irem",
		"sneg",
		"ineg",
		"sshl",
		"ishl",
		"sshr",
		"ishr",
		"sushr",
		"iushr",
		"sand",
		"iand",
		"sor",
		"ior",
		"sxor",
		"ixor",
		"sinc",
		"iinc",
		"s2b",
		"s2i",
		"i2b",
		"i2s",
		"icmp",
		"ifeq",
		"ifne",
		"iflt",
		"ifge",
		"ifgt",
		"ifle",
		"ifnull",
		"ifnonnull",
		"if_acmpeq",
		"if_acmpne",
		"if_scmpeq",
		"if_scmpne",
		"if_scmplt",
		"if_scmpge",
		"if_scmpgt",
		"if_scmple",
		"goto",
		"jsr",
		"ret",
		"stableswitch",
		"itableswitch",
		"slookupswitch",
		"ilookupswitch",
		"areturn",
		"sreturn",
		"ireturn",
		"return",
		"getstatic_a",
		"getstatic_b",
		"getstatic_s",
		"getstatic_i",
		"putstatic_a",
		"putstatic_b",
		"putstatic_s",
		"putstatic_i",
		"getfield_a",
		"getfield_b",
		"getfield_s",
		"getfield_i",
		"putfield_a",
		"putfield_b",
		"putfield_s",
		"putfield_i",
		"invokevirtual",
		"invokespecial",
		"invokestatic",
		"invokeinterface",
		"new",
		"newarray",
		"anewarray",
		"arraylength",
		"athrow",
		"checkcast",
		"instanceof",
		"sinc_w",
		"iinc_w",
		"ifeq_w",
		"ifne_w",
		"iflt_w",
		"ifge_w",
		"ifgt_w",
		"ifle_w",
		"if_nullw",
		"ifnonnull_w",
		"if_acmpeq_w",
		"if_acmpne_w",
		"if_scmpeq_w",
		"if_scmpne_w",
		"if_scmplt_w",
		"if_scmpge_w",
		"if_scmpgt_w",
		"if_scmple_w",
		"goto_w",
		"getfield_a_w",
		"getfield_b_w",
		"getfield_s_w",
		"getfield_i_w",
		"getfield_a_this",
		"getfield_b_this",
		"getfield_s_this",
		"getfield_i_this",
		"putfield_a_w",
		"putfield_b_w",
		"putfield_s_w",
		"putfield_i_w",
		"putfield_a_this",
		"putfield_b_this",
		"putfield_s_this",
		"putfield_i_this"
	};


	public static byte bc_ops[] = {
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)1,
		(byte)2,
		(byte)1,
		(byte)2,
		(byte)4,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)1,
		(byte)1,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)2,
		(byte)2,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)2,
		(byte)1,
		(byte)-1,
		(byte)-1,
		(byte)-1,
		(byte)-1,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)0,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)4,
		(byte)2,
		(byte)1,
		(byte)2,
		(byte)0,
		(byte)0,
		(byte)3,
		(byte)3,
		(byte)3,
		(byte)3,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)2,
		(byte)1,
		(byte)1,
		(byte)1,
		(byte)1
	};
}