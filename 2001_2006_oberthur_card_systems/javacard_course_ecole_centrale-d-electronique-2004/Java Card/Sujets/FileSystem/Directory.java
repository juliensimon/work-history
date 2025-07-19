package com.oberthurcs.formation.filesystem;

import javacard.framework.*;

/** La classe Directory implemente un repertoire qui stocke d'autres fichiers (repertoires et fichiers binaires)
 *	Un repertoire peut contenir jusqu'a 127 fichiers.<BR>
 *	Les fichiers sont stockés dans un tableau d'objets File.<BR>
 *	Directory herite de la classe File.
 */
public class Directory extends File 
{
		/** Capacite du repertoire */
	private byte capacity;
	
		/** Tableau des fichiers contenus dans le repertoire. Ce tableau n'est pas trie :
		 *	les fichiers sont ranges dans l'ordre ou ils sont ajoutes.<BR>
		 *	Lorsqu'un fichier est supprime, tous les elements suivants sont
		 *	decales d'un cran vers la gauche.
		 */
	private File[] fileList;
	
		/** Nombre de fichiers contenus dans le repertoire */
	private byte count;
	
		/** Type de fichier : repertoire = 0 */
	public static final byte TYPE = (byte) 0;

		/** Retourne le type du fichier : repertoire = 0 */
	public byte getType() { return TYPE; }
	
		/** Constructeur du repertoire.
		 *	@param _id identifiant du repertoire
		 *	@param _capacity capacite du repertoire
		 */
	protected Directory(short _id, byte _capacity)
	{
		super(_id);
		capacity = _capacity;
		fileList = new File[_capacity];
	}
	
		/** Retourne la capacite du repertoire */
	public byte getMaxSize() { return capacity; }

		/** Retourne le nombre de fichiers contenus dans le repertoire */
	public byte getCurrentSize() { return count; }
	
		/** Ajoute un fichier dans le repertoire. Les fichiers sont stockés
		 *	dans l'ordre ou ils sont ajoutes.
		 *	@param file le fichier a ajouter
		 *	@return true si l'ajout a reussi
		 *	@throws ISOException avec les raisons suivantes
		 *	<UL>
		 *		<LI><CODE>ISO7816.SW_FILE_INVALID (0x6983)</CODE> :
		 *			<UL>
		 *				<LI>fichier invalide
		 *				<LI>un fichier avec cet identifiant existe deja dans le repertoire
		 *				<LI>le fichier est deja contenu dans un autre repertoire
		 *				<LI>une circularite a ete detectee
		 *			</UL>
		 *		<LI><CODE>ISO7816.SW_FILE_FULL (0x6A84)</CODE> : le repertoire est plein
		 *	</UL>
		 */
	public boolean add(File file) {
		byte index = 0;

		if ( file == null )
			ISOException.throwIt(ISO7816.SW_FILE_INVALID);
			
		if ( get(file.getId()) != null )
			ISOException.throwIt(ISO7816.SW_FILE_INVALID);
			
		if ( file.parent != null )
			ISOException.throwIt(ISO7816.SW_FILE_INVALID);

		if ( getCurrentSize() == capacity )
			ISOException.throwIt(ISO7816.SW_FILE_FULL);
			
		File f = this;
		RootDirectory mf = RootDirectory.getInstance();
		do {
			if ( f == file ) ISOException.throwIt(ISO7816.SW_FILE_INVALID);
			f = f.parent;
		} while ( (f != null) && (f != mf) );

		for (index=0; index<capacity; index++)
			if ( fileList[index] == null ) break;
		
		f = this;	
		file.parent = f;
		fileList[count++] = file;
		return true;
	}
	
		/** Supprime un fichier du repertoire. Les fichiers suivants sont decales vers la gauche.
		 *	@param file le fichier a supprimer
		 *	@return true si la suppression a reussi
		 *	@throws ISOException avec les raisons suivantes
		 *	<UL>
		 *		<LI><CODE>ISO7816.SW_FILE_INVALID (0x6983)</CODE> : fichier invalide
		 *		<LI><CODE>ISO7816.SW_FILE_NOT_FOUND (0x6A82)</CODE> : le fichier n'existe pas dans le repertoire
		 *	</UL>
		 */
	public boolean remove(File file) 
	{
		byte index;

		if ( file == null )
			ISOException.throwIt(ISO7816.SW_FILE_INVALID);
			
		for (index=0; index<count; index++)
		{
			if ( fileList[index] == file )
			{
				return remove(index);
			}
		}
		if ( index == capacity ) ISOException.throwIt(ISO7816.SW_FILE_NOT_FOUND);
		return false;
	}

		/** Supprime le fichier a l'index donne. Les fichiers suivants sont decales vers la gauche.
		 *	@param index l'index du fichier a supprimer
		 *	@return true si la suppression a reussi
		 *	@throws ISOException avec les raisons suivantes
		 *	<UL>
		 *		<LI><CODE>ISO7816.SW_FILE_INVALID (0x6983)</CODE> : le fichier a la position donnee est invalide
		 *	</UL>
		 */
	public boolean remove(byte index) 
	{
		File file = fileList[index];
		
		if ( file == null )
			ISOException.throwIt(ISO7816.SW_FILE_INVALID);
			
		file.parent = null;
		for (byte j=index; j<count-1; j++)
			fileList[j] = fileList[j+1];
		fileList[count-1] = null;
		count--;
		return true;
	}

		/** Retourne le fichier a l'index donne.
		 *	@param index l'index du fichier a supprimer
		 *	@return la reference fichier a l'index donne,
		 *			ou <CODE>null</CODE> si aucun fichier n'existe à la position donnée
		 */
	public File get(byte index) 
	{
		return fileList[index];
	}

		/** Recupere la liste des identifiants des fichiers contenus dans le repertoire.
		 *	@param buffer tableau d'octets ou ecrire la liste des identifiants
		 *	@param offset offset dans le buffer destination
		 *	@return le nombre d'octets ecrits dans <CODE>buffer</CODE>
		 */
	public short listDir(byte[] buffer, short offset)
	{
		byte size = getCurrentSize();
		short length = 0;
		for (byte i=0; i < size; i++)
		{
			offset = Util.setShort(buffer, offset, get(i).getId());
			length += 2;
		}
		return length;
	}

		/** Retourne le fichier ayant l'identifiant specifie..
		 *	@param fileId identifiant du fichier a rechercher
		 *	@return la reference fichier ayant cet identifiant,
		 *			ou <CODE>null</CODE> si le fichier n'a pas ete trouve
		 */
	public File get(short fileId)
	{
		short index;
		File f = null;
		for (index=0; index<capacity; index++) 
		{
			f = fileList[index];
			if ( (f != null) && (f.getId() == fileId) )
				break;
		}
		return f;
	}

	
		/** Retourne la taille en octets du fichier binaire. */	
	public short getSize() {
		short result = 0;
		File f = null;
		for (byte i=0; i<count; i++)
		{
			f = fileList[i];
			result += f.getSize();
		}
		return result;
	}

}


