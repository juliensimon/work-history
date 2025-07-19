package com.oberthurcs.formation.filesystem;

import javacard.framework.*;

/** La classe File est la classe de base pour tous les fichiers.
 *	Directory et BinaryFile heritent tous les deux de la classe File.<BR>
 *	En outre, cette classe implemente l'interface Resource et donc la methode getSize().<BR>
 *	C'est une classe abstratire : elle ne peut donc pas être instanciee.
 */
public abstract class File implements Resource {

		/** L'identifiant du fichier doit etre unique dans le repertoire. */
	protected short id;

		/** Le parent est le repertoire qui contient le fichier. */
	protected File parent;

		/** Constructeur de la classe fichier.
		 *	@param _id Identifiant du fichier
		 */
	public File(short _id) {
		id = _id;
	}
	
		/** Retourne l'identifiant du fichier. */
	public short getId() { return id; }
		/** Retourne le parent du fichier. */
	public File getParent() { return parent; }

	abstract public short getSize();

		/** Retourne le type du fichier (BinaryFile, Directory...)*/
	abstract public byte getType();

		/** Cette methode cree une instance d'un fichier correspondant au type, identifiant et
		 *	taille demandes.
		 *
		 *	@param fileType type de fichier
		 *	@param fileId identifiant du fichier
		 *	@param fileSize taille du fichier
		 *	@return la reference vers le nouveau fichier
		 *	@throws ISOException dans le cas suivant :<BR>
		 *		<code>ISO7816.SW_WRONG_DATA (0x6A80)</code> : si la taille du fichier est incorrecte
		 */
	static public File getInstance(byte fileType, short fileId, byte fileSize) {
		
		switch ( fileType ) {
			case Directory.TYPE :
				if ( fileSize <= 0 )
					ISOException.throwIt(ISO7816.SW_WRONG_DATA);
					
				return new Directory(fileId, fileSize);
					
			case BinaryFile.TYPE :
			{
				short size = (short)(fileSize & 0x00FF);
				if ( (size <= 0) || (size > BinaryFile.MAX_SIZE ) )
					ISOException.throwIt(ISO7816.SW_WRONG_DATA);
				return new BinaryFile(fileId, size);
			}
				
					
			default :
				ISOException.throwIt(ISO7816.SW_WRONG_DATA);
		}
		return null;
			
	}
		

}
