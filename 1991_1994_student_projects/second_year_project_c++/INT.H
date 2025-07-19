//
// @(#)Int.h	3.2	12/15/92
//
// La classe de base pour les tests des collections.
// A noter la surcharge de "operator int" pour la conversion Int->int
//	(le constructeur se chargeant de la conversion dans l'autre sens)
//
///////////////////////////////////////////////////////////////////////////

#ifndef __INT_H
#define __INT_H

#include <math.h>
#include "Magnitude.h"

// INT
class Int : public Magnitude {
protected:
	int 		_val;
public:
	
	// les virtuelles classiques heritees d'"Object"

	classType 	isA		() const { return IntClass; }
	char * 		className	() const { return "Int"; }
	int 		virtIsEqual	(const Object* o) const { 
	  	return _val == ((Int*)o)->_val; 
	}


	// la hash value d'un entier est sa valeur absolue

	hashValueType 	hash		() const { return abs(_val);  }


	// les operateurs de conversion Int <--> int

			Int		(int i = 0) { _val = i; }
			operator int() { return _val; }


	// on affiche un Int par sa valeur

	ostream& printOn(ostream& os) const { return os << _val; }


	// la comparaison se fait egalement par les valeurs

	int 		lessThan 	(const Magnitude* m) const { 
	  	return _val < ((Int*)m)->_val; 
	}
};

#endif

// EOF
///////////////////////////////////////////////////////////////////////////
