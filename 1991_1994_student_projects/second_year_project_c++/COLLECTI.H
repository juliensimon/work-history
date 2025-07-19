//
// @(#)Collection.h	3.2	12/15/92
//
// La classe de base pour tous les regroupements d'Objets. Une instance de
//	"Collection" doit pouvoir :
//	
//	- Dire si elle contient un Objet donne : "includes"
//	- ajouter et retirer un Objet : "add", "remove"
//	- Effectuer un traitement sur tous ses elements : "forEach"
//	- Dire si elle est vide : "isEmpty"
//	- Dire combien elle detient d'elements : "size"
//
// On part du principe qu'une "Collection" ne possede pas ses elements : ce
// 	n'est pas a elle d'en assurer la gestion memoire.
//
// C'est une classe abstraite, on a donc des virtuelles pures
//
///////////////////////////////////////////////////////////////////////////

#ifndef __COLLECT_H
#define __COLLECT_H

#include "Object.h"

// le type "funPtr" est un pointeur sur une fonction prenant deux arguments,
//	un pointeur sur un Objet et un pointeur generique  (liste des args)
typedef void (*funPtr) (Object*, void*);


// COLLECTION
class Collection : public Object {

protected:	

	enum { BaseCollectionSize = 10 };
	int	_size;

public:

	virtual int 	includes 	(const Object*) const = 0;	
	virtual Object* add 		(const Object*) = 0;		
	virtual Object* remove 		(const Object*) = 0;		
	virtual Object* forEach 	(funPtr, void* = 0) const = 0;	



	// l'interface propre a "Collection", liaison statique	

	int 		isEmpty 	() const;
	int 		size	 	() const;


	// le constructeur 

			Collection() : _size(0) {}


}; // COLLECTION

inline int Collection :: size() const { return _size; }
inline int Collection :: isEmpty() const { return _size == 0; }

	
#endif

// EOF
///////////////////////////////////////////////////////////////////////////
