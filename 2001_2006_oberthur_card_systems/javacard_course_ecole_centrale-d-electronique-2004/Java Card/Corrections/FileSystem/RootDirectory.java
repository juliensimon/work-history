package com.oberthurcs.formation.filesystem;

import javacard.framework.*;

/** La classe RootDirectory implemente le repertoire racine. L'instance de celui-ci est unique
 *	et son identifiant est 0x3F00, sa capacite est de 127 fichiers.<BR>
 *	Le repertoire racine est son propre parent.<BR>
 *	Cette classe implemente le motif de conception Singleton.
 */

public class RootDirectory extends Directory {

		/** Identifiant du repertorie racine : 0x3F00 */
	public static final short ROOT_ID = (short) 0x3F00;

		/** Instance du repertoire racine. */
	protected static RootDirectory singleton;

		/** Constructeur. Celui-ci est protected afin de ne pas pouvoir etre
		 *	appele en dehors du package.<BR>
		 *	On utilise la methode getInstance() pour recuperer la reference.
		 */
	protected RootDirectory()  {
		super(ROOT_ID, (byte) 127);
		parent = this;
	}
	
		/** Cette methode retorune l'instance singleton du repertoire racine. 
		 *	Celle-ci est creee au premier appel de getInstance, puis elle est
		 *	simplement retournee lors de tous les appels successifs a cette methode.
		 *	@return l'instance du repertoire racine
		 */
	public static RootDirectory getInstance() {
		if (singleton == null) {
			try {
				singleton = new RootDirectory();
			}
			catch (ISOException e) {
			}
		}
		return singleton;
	}
	
}


