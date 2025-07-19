package com.oberthurcs.formation.exercices;

import javacard.framework.*;
import com.oberthurcs.formation.filesystem.*;

public class Ex4 extends Applet {
	
	public static final byte INS_SELECT_FILE	= (byte) 0xA4;
	public static final byte INS_CREATE_FILE	= (byte) 0xE0;
	public static final byte INS_DELETE_FILE	= (byte) 0xE4;
	public static final byte INS_READ_BINARY	= (byte) 0xB0;
	public static final byte INS_UPDATE_BINARY	= (byte) 0xD6;
	public static final byte INS_LIST_DIR		= (byte) 0xE8;
	
	public static final byte INS_VERIFY_PIN		= (byte) 0x20;
	public static final byte INS_CLEAR_DIR		= (byte) 0x00;
	
	private RootDirectory masterFile;
	
	// XXX : Utiliser un tableau d'objets transient au lieu de 2 variables membres
//	private Directory currentDir;
//	private File currentFile;
	private OwnerPIN pin;
	
	protected Ex4()
	{
		masterFile = RootDirectory.getInstance();
		pin = new OwnerPIN((byte) 3, (byte) 4);		// Try counter = 3; PIN size = 4
		byte[] pinValue = new byte[] { (byte) 0x01, (byte) 0x02, (byte) 0x03, (byte) 0x04};
		pin.update(pinValue, (short) 0, (byte) pinValue.length);
	}

	public boolean select()
	{
		setCurrentFile(masterFile);
		return true;
	}
	
	public static void install( byte[] bArray, short bOffset, byte bLength )
	{
		
		(new Ex4()).register();
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

		if ( ! pin.isValidated() )
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);

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
			
			case INS_CLEAR_DIR : processClearDir(apdu);
				break;
			
			default :
				ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
				break;
		}
	}


	private void setCurrentFile(File selectedFile)
	{
		if ( selectedFile instanceof Directory )
		{
			currentDir = (Directory) selectedFile;
			currentFile = null;
		}
		else
			currentFile = selectedFile;
	}

	
	private void processVerifyPin(APDU apdu)
	{
		byte[] buffer = apdu.getBuffer();
		
		if ( Util.getShort(buffer, ISO7816.OFFSET_P1) != 0 )
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
			
		short length =  apdu.setIncomingAndReceive();
		
		if ( pin.getTriesRemaining() == 0 )
			ISOException.throwIt(ISO7816.SW_CONDITIONS_NOT_SATISFIED);
		
		if ( ! pin.check(buffer, ISO7816.OFFSET_CDATA, (byte)length) )
			ISOException.throwIt(ISO7816.SW_WRONG_DATA);
	}
	
	private void processSelectFile(APDU apdu)
	{
		byte[] buffer = apdu.getBuffer();
		
		byte P1 = buffer[ISO7816.OFFSET_P1];
		byte P2 = buffer[ISO7816.OFFSET_P2];
		boolean returnData = false;
		File selectedFile = null;

		switch ( P2 )
		{
			case 0x00 : returnData = true;
				break;
			case 0x0C : returnData = false;
				break;
			default :
				ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		
		switch ( P1 )
		{
			case 0x00 :
				switch ( buffer[ISO7816.OFFSET_LC] ) 
				{
					case 0 :
						selectedFile = masterFile;
						break;
						
					case 2 :
					{
						apdu.setIncomingAndReceive();
							
						short efid = Util.getShort(buffer, ISO7816.OFFSET_CDATA);
					
						if ( (selectedFile = currentDir.get(efid)) == null)
							ISOException.throwIt(ISO7816.SW_FILE_NOT_FOUND);
						
						break;
					}
					default :
						ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
				}
				break;
				
			case 0x03 :
				selectedFile = currentDir.getParent();
				break;
				
			default:
				ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);
		}
		
		setCurrentFile(selectedFile);
		
		if ( returnData )
		{
			short offset = 0;
			
			buffer[offset++] = selectedFile.getType();
			offset = Util.setShort(buffer, offset, selectedFile.getId());
			offset = Util.setShort(buffer, offset, selectedFile.getSize());
			offset = Util.setShort(buffer, offset, selectedFile.getParent().getId());
			apdu.setOutgoingAndSend((short) 0, offset);
		}
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
	
	private void processClearDir(APDU apdu)
	{
		byte[] buffer = apdu.getBuffer();
		
		if ( Util.getShort(buffer, ISO7816.OFFSET_P1) != 0 )
			ISOException.throwIt(ISO7816.SW_INCORRECT_P1P2);

		if ( buffer[ISO7816.OFFSET_LC] != 0 )
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
			
		byte size = currentDir.getCurrentSize();
		short offset = 0;
		
			// We'll clear the directory starting at the last element.
			// That way, the elements will not be shifted to the left after removal
			// and the transaction buffer will not overflow too quickly.
		JCSystem.beginTransaction();
		for (byte i=(byte)(size-1); i >= 0; i--)
		{
//			currentDir.remove((byte)0);
			currentDir.remove(i);
		}
		setCurrentFile(currentDir);
		JCSystem.commitTransaction();
	}

}