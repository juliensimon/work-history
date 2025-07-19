package com.oberthurcs.formation.exercices.client;

import javacard.framework.*;
import com.oberthurcs.formation.filesystem.*;

public class Ex5_Client extends Applet {
	
	public static final byte INS_LIST_DIR		= (byte) 0xE8;

	protected byte[] serverAIDbytes = {	(byte)0xA0, (byte)0x00, (byte)0x00, (byte)0x00, 
									(byte)0x77, (byte)0xFF, (byte)0xFF, (byte)0xFF, 
									(byte)0x05, (byte)0x01 };
									
	
	protected Ex5_Client()
	{	// XXX : Implementer le constructeur
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
			// XXX : Récupérer le Root Directory partagé par le serveur
			// et renvoyer la liste des fichiers qu'il contient.
	}
}