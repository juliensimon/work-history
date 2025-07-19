//
	static char sccsId[] =

	"@(#)Object.C	3.2	12/15/92";

//	Implementation de la classe "Object". Fonctions definies :
//
//		- "hash" : par defaut, retourne l'adresse du receveur
//		- "printOn" : par defaut, retourne "a <classeName>"
//		- "error" : envoie un message d'erreur, stoppe eventuellement
//
///////////////////////////////////////////////////////////////////////////

#include <stdlib.h>
#include <string.h>
#include "Object.h"

const Error * 	Nil = new Error;


// HASH : par defaut, l'adresse du receveur
hashValueType Object :: hash() const {
	return hashValueType(this); 
} // HASH


// PRINTON : par defaut, un "a" suivi du nom de la classe d'appartenance
ostream& Object :: printOn(ostream& os) const {
	return os << "a " << className();
} // PRINTON


// VIRTISEQUAL : teste l'egalite des adresses des 2 objets
int Object :: virtIsEqual(const Object* o) const {
	return this == o;
} // VIRTISEQUAL


// ERROR : affiche la classe d'appartenance, le selecteur,
//	l'adresse du receveur, puis un message decrivant le type d'erreur
//	Si "stp" est non nul, termine l'execution du programme
//	Retourne le receveur
Object* Object :: error(char* sel, char* msg, int stp) const {
	cerr 	<< "\nclass  : " << className()
		<< "\nmethod : " << sel
		<< "\naddress: " << int (this) << '\n'
		<< msg << '\n';
	if (stp) exit (stp);
	return (Object*)this;
} // ERROR

// EOF
///////////////////////////////////////////////////////////////////////////

