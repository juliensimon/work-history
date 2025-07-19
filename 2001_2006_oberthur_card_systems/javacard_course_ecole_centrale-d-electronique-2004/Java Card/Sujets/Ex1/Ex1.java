package com.oberthurcs.formation.exercices;

import javacard.framework.*;

public class Ex1 extends Applet {
	
	public static final byte INS_SET_COUNTER	= (byte) 0x02;
	public static final byte INS_GET_COUNTER	= (byte) 0x04;
	public static final byte INS_INC_COUNTER	= (byte) 0x06;
	public static final byte INS_DEC_COUNTER	= (byte) 0x08;
	
	public static void install( byte[] bArray, short bOffset, byte bLength ) 
	{
		(new Ex1()).register();
	}
							
	public void process(APDU apdu)
	{
	}
}