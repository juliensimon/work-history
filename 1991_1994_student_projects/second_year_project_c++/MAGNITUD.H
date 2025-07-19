//
// @(#)Magnitude.h	3.2	12/15/92
//
// "Magnitude" est la classe de base de tous les Objets comparables.
//
// On compare deux magnitudes par la fonction virtuelle (pure) "lessThan"
//	(a preciser dans chaque classe derivee)
//
///////////////////////////////////////////////////////////////////////////

#ifndef __MAGNITUDE_H
#define __MAGNITUDE_H

#include "Object.h"

// MAGNITUDE
class Magnitude : public Object {
public:

	// La raison d'etre de la classe "Magnitude"

 	virtual int 	lessThan	(const Magnitude*) const = 0;  


}; // MAGNITUDE

#endif

// EOF
///////////////////////////////////////////////////////////////////////////
