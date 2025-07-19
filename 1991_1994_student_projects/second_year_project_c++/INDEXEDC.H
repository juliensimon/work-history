//
// @(#)IndexedCollection.h	3.2	12/15/92
//
// Dans une "IndexedCollection", les elements sont reperes par un entier
//	representant leur place dans le vecteur d'Objets.
//
// Les indices sont ici relatifs : la facon dont ils sont relies aux indices
//	absolus depend de la nature de la classe derivee.
// 
// On derive deux sortes de classes de "IndexedCollection", suivant que le
//	nombre d'elements est fixe lors de la creation ("FixedSizeCollection")
//	ou non ("OrderedCollection")
//
///////////////////////////////////////////////////////////////////////////

#ifndef __IDXCOLLECT_H
#define __IDXCOLLECT_H

#include <stdio.h>
#include "Collection.h"

// INDEXEDCOLLECTION
class IndexedCollection : public Collection {
protected:
	
	// Quelques utilitaires

	Object*		errorInBounds	(char*,int) const;
	void 		checkIndex	(char*,int) const;
	int		indexOf		(const Object*) const;

public:

	// L'interface de la classe

	virtual Object*	at	(int) const = 0;		
	virtual Object*	atPut	(int, const Object*) = 0;

	IndexedCollection () : Collection() {}

	Object*		first		() const;
	Object*		last		() const;
};

#endif

// EOF
///////////////////////////////////////////////////////////////////////////
