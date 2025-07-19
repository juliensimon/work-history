//
// @(#)SortedCollection.h	3.2	12/15/92
//
// Une"SortedCollection" se distingue de "OrderedCollection" par le fait 
//	que l'insertion ne peut se faire que suivant une relation d'ordre
//	definie sur les elements (qui sont donc des instances d'une des
//	classes derivees de "Magnitude". 
//
// Les fonctions membres "atPut", "addFirst" etc. sont donc surchargees
//	pour etre rendues inoperantes.
//
///////////////////////////////////////////////////////////////////////////

#ifndef __SORCOLLECT_H
#define __SORCOLLECT_H

#include "OrderedCollection.h"
#include "Magnitude.h"

typedef int (*Comparator)(const Object*,const Object*);

// SORTEDCOLLECTION
class SortedCollection : public OrderedCollection {
protected:

	// empeche l'ajout d'elements sauf a l'endroit determine par
	// la relation d'ordre.
	Object*		forbid	(char* sel) 
		{ return error(sel,"unauthorized operation\n",0); }

#define FORBID(method,args) 					\
	Object* 	method	args				\
		{ return forbid(#method ); }
		
	Comparator lt;

public:

	// Les virtuelles pures heritees de "Object"
	
	virtuals(SortedCollection)

	// Les fonctions membres de "OrderedCollection" qu'il faut
	// surcharger.

	Object* 	add 		(const Object*);
	Magnitude*	add		(const Magnitude*);

	FORBID(addFirst,(const Object*));
	FORBID(addLast,(const Object*));
	FORBID(addBefore,(const Object*,const Object*));
	FORBID(addAfter,(const Object*,const Object*));
	FORBID(atPut,(int,const Object*));

	// Les constructeurs

	SortedCollection(Comparator f = 0, int s = BaseCollectionSize)
		: OrderedCollection(s) { lt = f; }

};

#endif

// EOF
///////////////////////////////////////////////////////////////////////////




  
