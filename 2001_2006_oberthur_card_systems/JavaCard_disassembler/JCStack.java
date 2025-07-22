package fr.netattitude.cappuccino;

import java.util.*;

class JCStack extends Stack {

	String locals[];

	public JCStack() {
		super();
	}

	public void dump() {
		ListIterator i = listIterator();
		Util.printTab("<", 3);
		while (i.hasNext()) {
			Util.printTab((String)i.next(), 0);
			if (i.hasNext()) {
				Util.printTab(" | ", 0);
			}
		}
		Util.printlnTab(">", 0);

		Util.printTab("", 3);
		for (int j=1; j<locals.length;j++) {
			if (locals[j] != null) {
				Util.printTab("var"+j+"="+locals[j]+" ", 0);
			}
		}
		System.out.println();
	}

	public void init(byte argCount) {
		locals = new String[16];
		store((byte)0, "this");
		for (byte i=1; i<=argCount; i++) {
			String argName = "p"+i;
			//push(argName);
			store(i, argName);
		}
	}

	public void store(byte var, String s) {
		//System.out.println("Storing " + s + " at index " + var);
		locals[var] = s;
	}

	public String load(byte var) {
		return locals[var];
	}
}
		