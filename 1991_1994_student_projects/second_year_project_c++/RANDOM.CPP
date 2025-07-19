//
	static char sccsId[] =

	"@(#)Random.C	3.2	12/15/92";

///////////////////////////////////////////////////////////////////////////

#include <math.h>
#include "Random.h"

long Random :: ValInit	= 10000;
long Random :: Raison	= (long) pow(5,5);	// 5 ** 5
long Random :: Max	= (long) pow(2,13);

// GET : renvoie un nombre reel aleatoire centre sur l'argument
double Random :: get (double mediane) {
	ValInit = (ValInit * Raison) % Max;
	return 0 - (mediane * log(double(ValInit)/Max));
} // GET

// EOF
///////////////////////////////////////////////////////////////////////////

