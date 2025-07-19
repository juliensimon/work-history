package com.oberthurcs.formation.exercices.client;

import javacard.framework.*;
import com.oberthurcs.formation.filesystem.*;

public class Ex5_Client extends Applet {
	
	public static final byte INS_LIST_DIR		= (byte) 0xE8;

	protected byte[] serverAIDbytes = {	(byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, 
									(byte)0x77, (byte)0xFF, (byte)0xFF, (byte)0xFF, 
									(byte)0x05, (byte)0x01 };
									
	protected AID serverAID;
	
	protected Ex5_Client()
	{
		serverAID = new AID(serverAIDbytes, (short) 0, (byte) serverAIDbytes.length);
	}

	public boolean select()
	{
		return true;
	}
	
	public static void install( byte[] bArray, short bOffset, byte bLength )
	{
		
		(new Ex5_Client()).register();
	}
							
	public void process(APDU apdu)
	{
		byte[] buffer = apdu.getBuffer();
		short Lc = buffer[ISO7816.OFFSET_LC];
		
		if (selectingApplet()) return;
		
		if ( buffer[ISO7816.OFFSET_CLA] != (byte) 0x80 )
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);

		switch ( buffer[ISO7816.OFFSET_INS] )
		{
			case INS_LIST_DIR : processListDir(apdu);
				break;
			
			default :
				ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
				break;
		}
	}

	
	private void processListDir(APDU apdu)
	{
		byte[] buffer = apdu.getBuffer();
		
		if ( Util.getShort(buffer, ISO7816.OFFSET_P1) != 0 )
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);

 		SharedDirectory sharedMasterFile
 			= (SharedDirectory) JCSystem.getAppletShareableInterfaceObject(serverAID, (byte)0);

			// This will result in a security exception
// 		SharedRootDirectory sharedMasterFile
// 			= (SharedRootDirectory) JCSystem.getAppletShareableInterfaceObject(serverAID, (byte)0);
		
			
		short length = sharedMasterFile.listDir(buffer, (short) 0);
		apdu.setOutgoingAndSend((short) 0, length);
	}

}