package com.oberthurcs.formation.filesystem;

import javacard.framework.*;

/** L'interface SharedDirectory definit les services des repertoires que l'on souhaite
 *	partager entre differentes applets.<BR>
 */

public interface SharedDirectory extends Shareable {

		/** Recupere la liste des identifiants des fichiers contenus dans le repertoire.
		 *	@param buffer tableau d'octets ou ecrire la liste des identifiants
		 *	@param offset offset dans le buffer destination
		 *	@return le nombre d'octets ecrits dans <CODE>buffer</CODE>
		 */
	public short listDir(byte[] buffer, short offset) ;
	
}

