//
	static char sccsId[] =

	"@(#)SortedCollection.C	3.2	12/15/92";

///////////////////////////////////////////////////////////////////////////

#include "SortedCo.h"

// ADD : ajoute une "Magnitude" a la place convenable.
//	Message d'erreur si l'Objet argument n'est pas une magnitude
Object* SortedCollection :: add (const Object*) {
	return error("add","Object is not sortable",0);
} 

Magnitude* SortedCollection :: add (const Magnitude* m) {
	int siz = size();
	int i =0;
	if (!lt)
		for ( i; i < siz && ((Magnitude*)at(i))->lessThan(m); i++);
	else
		for ( i; i < siz && lt(at(i),m); i++);
	if (i == siz) return (Magnitude*)OrderedCollection::add(m);
	return (Magnitude*)OrderedCollection::addBefore(m,at(i));
};

// EOF
///////////////////////////////////////////////////////////////////////////
