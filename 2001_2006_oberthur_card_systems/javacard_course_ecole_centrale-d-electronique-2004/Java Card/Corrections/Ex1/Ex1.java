package com.oberthurcs.formation.exercices;

import javacard.framework.*;

public class Ex1 extends Applet {
	
	public static final byte INS_SET_COUNTER	= (byte) 0x02;
	public static final byte INS_GET_COUNTER	= (byte) 0x04;
	public static final byte INS_INC_COUNTER	= (byte) 0x06;
	public static final byte INS_DEC_COUNTER	= (byte) 0x08;
	
	private short counter;
	
	public static void install( byte[] bArray, short bOffset, byte bLength ) 
	{
		(new Ex1()).register();
	}
							
	public void process(APDU apdu)
	{
		byte[] buffer = apdu.getBuffer();
		short Lc = buffer[ISO7816.OFFSET_LC];
		
		if (selectingApplet()) return;
		
		if ( buffer[ISO7816.OFFSET_CLA] != (byte) 0x80 )
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);

		if ( Util.getShort(buffer, ISO7816.OFFSET_P1) != 0 )
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);

		
		switch ( buffer[ISO7816.OFFSET_INS] )
		{
			case INS_SET_COUNTER :
				if ( Lc != 2 )
					ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);

				Lc = apdu.setIncomingAndReceive();
				counter = Util.getShort(buffer, ISO7816.OFFSET_CDATA);
				break;
				
			case INS_GET_COUNTER :
				Util.setShort(buffer, (short) 0, counter);
				apdu.setOutgoingAndSend((short) 0, (short) 2);
				break;
				
			case INS_INC_COUNTER :
				if ( Lc != 1 )
					ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
				Lc = apdu.setIncomingAndReceive();
				counter += buffer[ISO7816.OFFSET_CDATA];
				break;
				
			case INS_DEC_COUNTER :
				if ( Lc != 1 )
					ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
				Lc = apdu.setIncomingAndReceive();
				counter -= buffer[ISO7816.OFFSET_CDATA];
				break;
				
			default :
				ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
				break;
			
		}
		
		
	}
}