package com.oberthurcs.formation.filesystem;

import javacard.framework.*;

/** La classe SharedRootDirectory implemente le repertoire racine de maniere partagee. <BR>
 *	Cette classe implemente le motif de conception Singleton.<BR>
 *	<B>A completer lors de l'exercice 5</B>
 */


public class SharedRootDirectory {

		/** Constructeur. Celui-ci est protected afin de ne pas pouvoir etre
		 *	appele en dehors du package.<BR>
		 *	On utilise la methode getInstance() pour recuperer la reference.
		 */
	protected SharedRootDirectory()  {
			// XXX
	}
	
		/** Cette methode retourne l'instance singleton du repertoire racine partage. 
		 *	Celle-ci est creee au premier appel de getInstance, puis elle est
		 *	simplement retournee lors de tous les appels successifs a cette methode.
		 *	@return l'instance du repertoire racine partage
		 */
	public static RootDirectory getInstance() {
			// XXX
		return null;
	}

	
}


