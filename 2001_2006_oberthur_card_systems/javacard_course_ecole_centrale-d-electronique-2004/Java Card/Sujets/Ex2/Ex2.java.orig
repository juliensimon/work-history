package com.oberthurcs.formation.exercices;

import javacard.framework.*;
import com.oberthurcs.formation.filesystem.*;

public class Ex2 extends Applet {
	
	public static final byte INS_SELECT_FILE	= (byte) 0xA4;
	public static final byte INS_CREATE_FILE	= (byte) 0xE0;
	public static final byte INS_DELETE_FILE	= (byte) 0xE4;
	public static final byte INS_READ_BINARY	= (byte) 0xB0;
	public static final byte INS_UPDATE_BINARY	= (byte) 0xD6;
	public static final byte INS_LIST_DIR		= (byte) 0xE8;
	
	public static final byte INS_VERIFY_PIN		= (byte) 0x20;
	
	private Directory currentDir;
	private File currentFile;
	
	protected Ex2()
	{
	}

	public boolean select()
	{
		// XXX
		return true;
	}
	
	public static void install( byte[] bArray, short bOffset, byte bLength )
	{
		
		(new Ex2()).register();
	}
							
	public void process(APDU apdu)
	{
		byte[] buffer = apdu.getBuffer();
		short Lc = buffer[ISO7816.OFFSET_LC];
		byte ins = buffer[ISO7816.OFFSET_INS];
		
		if (selectingApplet()) return;
		
		if ( buffer[ISO7816.OFFSET_CLA] != (byte) 0x80 )
			ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);

		if ( ins == INS_VERIFY_PIN ) {
			processVerifyPin(apdu);
			return;
		}

		// XXX : Verifier que le PIN a été validé avant de continuer

		switch ( ins )
		{
			
			case INS_SELECT_FILE : processSelectFile(apdu);
				break;
			
			case INS_CREATE_FILE : processCreateFile(apdu);
				break;
			
			case INS_DELETE_FILE : processDeleteFile(apdu);
				break;
			
			case INS_READ_BINARY : processReadBinary(apdu);
				break;

			case INS_UPDATE_BINARY : processUpdateBinary(apdu);
				break;
			
			case INS_LIST_DIR : processListDir(apdu);
				break;

			default :
				ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
				break;
		}
	}


		// XXX
	private void setCurrentFile(File selectedFile)
	{
	}

		// XXX
	private void processVerifyPin(APDU apdu)
	{
	}

		// XXX
	private void processSelectFile(APDU apdu)
	{
	}
	
	private void processCreateFile(APDU apdu)
	{
		byte[] buffer = apdu.getBuffer();
		
		if ( Util.getShort(buffer, ISO7816.OFFSET_P1) != 0 )
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
			
		if ( apdu.setIncomingAndReceive() != 4 )
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
			
		byte fileType	= buffer[ISO7816.OFFSET_CDATA];
		short fileId	= Util.getShort(buffer, (short)(ISO7816.OFFSET_CDATA + 1));
		byte fileSize	= buffer[ISO7816.OFFSET_CDATA + 3];
		
		File newFile = File.getInstance(fileType, fileId, fileSize);
		currentDir.add(newFile);
		setCurrentFile(newFile);
	}
	
	private void processDeleteFile(APDU apdu)
	{
		byte[] buffer = apdu.getBuffer();
		
		if ( Util.getShort(buffer, ISO7816.OFFSET_P1) != 0 )
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
			
		if ( apdu.setIncomingAndReceive() != 0 )
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
		
		currentDir.remove(currentFile);
		setCurrentFile(currentFile.getParent());
	}
	
	private void processReadBinary(APDU apdu)
	{
		byte[] buffer = apdu.getBuffer();
		short offset = Util.getShort(buffer, ISO7816.OFFSET_P1);
		
		short length = apdu.setOutgoing();
		
		if ( (currentFile == null) || (! (currentFile instanceof BinaryFile) ) )
			ISOException.throwIt(ISO7816.SW_FILE_INVALID);
		
		((BinaryFile)currentFile).read(buffer, (short) 0, offset, length);
		apdu.setOutgoingLength(length);
		apdu.sendBytes((short) 0, length);
	}

	private void processUpdateBinary(APDU apdu)
	{
		byte[] buffer = apdu.getBuffer();
		short offset = Util.getShort(buffer, ISO7816.OFFSET_P1);

		if ( (currentFile == null) || (! (currentFile instanceof BinaryFile) ) )
			ISOException.throwIt(ISO7816.SW_FILE_INVALID);

		short length = apdu.setIncomingAndReceive();
		
		((BinaryFile)currentFile).update(buffer, ISO7816.OFFSET_CDATA, offset, length);

	}
	
	private void processListDir(APDU apdu)
	{
		byte[] buffer = apdu.getBuffer();
		
		if ( Util.getShort(buffer, ISO7816.OFFSET_P1) != 0 )
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
			
		short length = currentDir.listDir(buffer, (short) 0);
		apdu.setOutgoingAndSend((short) 0, length);
	}
}