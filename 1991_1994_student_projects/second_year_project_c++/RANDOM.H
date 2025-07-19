//
// @(#)Random.h	3.2	12/15/92
//
// "Random" est une classe abstraite dont le seul but est de generer
//	des nombres aleatoires a la demande.
//
// Les nombres generes suivent une loi de Poisson centree sur la valeur
//	donnee en argument du "next"
//
///////////////////////////////////////////////////////////////////////////

#ifndef __RANDOM_H
#define __RANDOM_H
  
#include "Object.h"

// RANDOM
class Random {
protected:
	static long ValInit;			// valeur initiale
	static long Raison;			// raison de la suite
	static long Max;			// valeur maxi de la suite

public:

	static double get(double = 1.0);	// arg. : valeur mediane

};

#endif

// EOF
///////////////////////////////////////////////////////////////////////////
