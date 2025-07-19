//
// @(#)OrderedCollection.h	3.2	12/15/92
//
// Une "OrderedCollection" est representee, outre par un vecteur d'Objets
//	qui peut croitre dynamiquement, par deux entiers qui relient les 
//	indices relatifs aux absolus.
//
///////////////////////////////////////////////////////////////////////////

#ifndef __ORDCOLLECT_H
#define __ORDCOLLECT_H

#include "IndexedCollection.h"

class OrderedCollection : public IndexedCollection {
protected:

	// La structure

	int 		_first, _last, _maxSize;
	Object ** 	_vec;

	// Les utilitaires

	void 		grow();
	void		putSpaceAtStart();
	void		putSpaceAtEnd();
	void		removeIndex(int);
	void 		putSpaceAfter(int);

public:

	// Les virtuelles pures heritees de "Object"

	virtuals(OrderedCollection)

	ostream&	printOn		(ostream&) const;
	int		virtIsEqual	(const Object*) const;

	// Les virtuelles pures heritees de "Collection"
	// en cas d'erreur,  retournent le receveur

	int 		includes 	(const Object*) const;
	Object* 	add 		(const Object*); 
	Object* 	addFirst	(const Object*);
	Object*		addLast		(const Object*);
	Object*		addBefore	(const Object*, const Object*);
	Object*		addAfter	(const Object*, const Object*);
	Object* 	remove 		(const Object*);
	Object*		removeFirst	();
	Object*		removeLast	();
	Object* 	forEach 	(funPtr, void* = 0) const;

	// L'interface de la classe

	Object*		at		(int i) const; 
	Object*		atPut		(int i, const Object*);

	
	// Les constructeurs
	OrderedCollection(int = BaseCollectionSize);


	// Le destructeur

	~OrderedCollection() { delete _vec; }
};

#endif

// EOF
///////////////////////////////////////////////////////////////////////////
