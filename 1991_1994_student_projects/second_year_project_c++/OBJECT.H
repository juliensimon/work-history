//
// @(#)Object.h	3.2	12/15/92
//
// la classe abstraite "Object", racine de l'arborescence. A la base, une
//	instance de "Object" sait :
//
//	- Dire la classe a laquelle elle appartient : "isA()"
//	- Retourner le nom de sa classe d'appartenance : "className()"
//	- Retourner une valeur unique la representant : "hash()"
//	- S'afficher dans un flux de sortie donne : "printOn()"
//	- Dire si elle est egale a un autre Objet : "isEqual()" 
//		(les deux doivent etre de la meme classe)
//
// les fonctions membres suivantes DOIVENT etre definies lors de la
//	creation d'une nouvelle classe concrete:
//	
//	- isA
//	- className
//
//	Pour ce faire, la macro "virtuals", prenant comme argument le nom de
//		la classe concernee, cree automatiquement les deux premieres
//		fonction ci-dessus ( la troisieme dependant de la classe en
//		question ). Cette macro est bien entendu redefinissable dans 
//		tout fichier de declaration de sous-classe qui le necessite.
//
///////////////////////////////////////////////////////////////////////////

#ifndef __OBJECT_H
#define __OBJECT_H

#include "classes.h"
#include <iostream.h>
#include <string.h>

// type de retour des fonctions "hash" (re-)definies dans toutes les classes
typedef unsigned int hashValueType;

///////////////////////////////////////////////////////////////////////////
//
// macros de definition semi-automatique des virtuelles pures heritees
//
//	(redefinissable dans tout `.h')
//

#define virtuals(classname)						\
	classType 	isA() const { return classname##Class ; }	\
	char * 		className() const { return #classname ; }	


// OBJECT
class Object {
public:

	// Les fonctions virtuelles pures
	// a surcharger dans toute classe concrete

	virtual classType 	isA() const = 0;			
	virtual	char * 		className() const = 0;			



	// "isEqual" n'a pas besoin d'etre virtuelle, puisque
	// "className" et "virtIsEqual" le sont

	int isEqual (const Object*) const;

	// Les fonctions virtuelles implementees dans la classe (cf object.C)


	// comportement par defaut : affiche "a <className>"
	virtual ostream& 	printOn		(ostream&) const;

	// comportement par defaut : retourne l'adresse du receveur
	virtual hashValueType 	hash		() const;

	// retourne le receveur
	Object* 		error		(char*, char*, int) const;

	// comportement par defaut : teste l' egalite physique. 
	virtual	int 		virtIsEqual(const Object* o) const;

	// surcharge de "<<" pour des sorties homogenes
	friend ostream& operator << (ostream&, const Object*);

	// Le destructeur virtuel est indispensable si on souhaite que les
	// Objets soient correctement detruits
	virtual ~Object() {}

}; // OBJECT

inline int Object :: isEqual (const Object* o) const { 
	if (this == o) return 1;
	return ((isA() == o->isA()) && virtIsEqual(o));
}

inline ostream& operator << (ostream& os, const Object* o){ 
	return o->printOn(os);
}


///////////////////////////////////////////////////////////////////////////

// ERROR

// Nil est la seule instance de la classe "Error"
class Error; 	// obligatoire puisqu'on se sert de Nil dans la definition
	 	// de la classe

extern const Error * Nil;
#define NIL (Object*) Nil


class Error : public Object {
public:

	// Les virtuelles pures heritees de "Object"

	virtuals(Error)

	int 	virtIsEqual	(const Object* o) const { return o == NIL; }


	// Nil se represente a l'affichage par "NIL"

	ostream& printOn(ostream& os) const { return os << "NIL"; }
};

#endif

// EOF
///////////////////////////////////////////////////////////////////////////
