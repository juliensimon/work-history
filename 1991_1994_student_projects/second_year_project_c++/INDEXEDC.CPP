//
	static char sccsId[] =

	"@(#)IndexedCollection.C	3.2	12/15/92";

///////////////////////////////////////////////////////////////////////////

#include "IndexedC.h"

///////////////////////////////////////////////////////////////////////////
//
// PROTECTED PART
//
///////////////////////////////////////////////////////////////////////////

// ERRORINBOUNDS : envoie un message d'erreur lorsque l'index est incorrect
//	(l'envoi provoque l'interruption de l'execution)
Object*	IndexedCollection :: errorInBounds (char * sel,int index) const {
	char msg[80];
	sprintf(msg, "index %d is outside of collection bounds\n", index);
	return error(sel,msg,1);
} // ERRORINBOUNDS


// CHECKINDEX : Verifie que l'indice passe en argument est coherent.
//	(indices relatifs)
void IndexedCollection :: checkIndex (char* sel,int index) const {
	if ((index < 0) || (index >= size())) errorInBounds(sel,index);
} // CHECKINDEX


// INDEXOF : retourne l'indice relatif associe a l'argument. -1 si inconnu
int IndexedCollection :: indexOf (const Object* o) const {
	int sz = size();
	for (int i = 0; i < sz; i++)
		if (at(i)->isEqual(o)) return i;
	return -1;
} // INDEXOF

///////////////////////////////////////////////////////////////////////////
//
// PUBLIC PART
//
///////////////////////////////////////////////////////////////////////////


// FIRST : retourne le premier Objet de la collection.
//	Message d'erreur si vide.
Object*	IndexedCollection :: first () const {
	return at(0);
} // FIRST


// LAST : retourne le dernier Objet de la collection.
//	Message d'erreur si vide.
Object*	IndexedCollection :: last () const {
	return at(size() - 1);
} // LAST


// EOF
///////////////////////////////////////////////////////////////////////////
