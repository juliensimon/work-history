package com.oberthurcs.formation.filesystem;

import javacard.framework.*;

/** La classe BinaryFile implemente un fichier binaire qui stocke des octets dans un tableau.
 *	Un fichier binaire peut contenir jusqu'a 255 octets.<BR>
 *	Il herite de la classe File.
 */
public class BinaryFile extends File {

		/** Taille maximale du fichier binaire = 255 octets */
	public static final short MAX_SIZE = 255;
		/** Type de fichier : fichier binaire = 1 */
	public static final byte TYPE = (byte) 1;
	
		/** Donnees contenues dans le fichier. */
	private byte[] contents;
	
		/** Constructeur du fichier binaire.
		 *	@param _id identifiant du fichier binaire
		 *	@param _size taille du fichier binaire
		 */
	protected BinaryFile(short _id, short _size) {
		super(_id);
		contents = new byte[_size];
	}
	
		/** Retourne le type du fichier : fichier binaire = 1 */
	public byte getType() { return TYPE; }

		/** Retourne la taille en octets du fichier binaire. */	
	public short getLength() { return (short) contents.length; }

		/** Lit <CODE>len</CODE> octets dans le fichier binaire a partir de l'offset <CODE>srcOff</CODE> 
		 *	et les ecrit dans le tableau <CODE>dst</CODE> a l'offset <CODE>dstOff</CODE>
		 *	@param dst tableau d'octets ou extraire les donnees
		 *	@param dstOff offset de depart dans <CODE>dst</CODE>
		 *	@param srcOff offset ou commencer la recuperation des donnees
		 *	@param len longueur des donnees a lire
		 *	@throws ISOException avec la raison suivante
		 *	<UL>
		 *		<LI><CODE>ISO7816.SW_WRONG_P1P2 (0x6B00)</CODE> : <CODE>srcOff</CODE> et <CODE>len</CODE> sont incompatibles
		 *	</UL>
		 */	
	public void read(byte[] dst, short dstOff, short srcOff, short len) {
										
		if ( (srcOff < 0) || (srcOff > contents.length) || (srcOff + len > contents.length) )
			ISOException.throwIt(ISO7816.SW_WRONG_P1P2);

			
		Util.arrayCopyNonAtomic(contents, srcOff, dst, dstOff, len);
	}
	
		/** Ecrit <CODE>len</CODE> octets depuis le tableau <CODE>src</CODE> a l'offset <CODE>srcOff</CODE>
		 *	et les ecrit dans le fichier binaire a l'offset <CODE>dstOff</CODE> 
		 *	@param src tableau d'octets contenant les donnees a ecrire
		 *	@param srcOff offset de depart dans <CODE>src</CODE>
		 *	@param dstOff offset ou commencer a ecrire les donnees
		 *	@param len longueur des donnees a ecrire
		 *	@throws ISOException avec la raison suivante
		 *	<UL>
		 *		<LI><CODE>ISO7816.SW_WRONG_P1P2 (0x6B00)</CODE> : <CODE>dstOff</CODE> et <CODE>len</CODE> sont incompatibles
		 *	</UL>
		 */	
	public void update(byte[] src, short srcOff, short dstOff, short len) {

		if ( (dstOff < 0) || (dstOff > contents.length) || (dstOff + len > contents.length) )
			ISOException.throwIt(ISO7816.SW_WRONG_P1P2);
			
		Util.arrayCopy(src, srcOff, contents, dstOff, len);
	}
		
		/** Retourne la taille en octets du fichier binaire. */	
	public short getSize() { return getLength(); }
}
