// Serveur.C

#include "Serveur.h"
#include "Restaura.h"
#include "Random.h"
#include "Liberati.h"
#include "Temps.h"

Serveur :: Serveur(Restaurant * resto, double tms) {
	_tempsMoyenService = tms;
	_fastfood        = resto;
	_tempsTotalService = 0;
	_nbClients         = 0;
	_libre             = 1;
	
}

double Serveur :: pourcentageUtilisation() const {
	return (100 * _tempsTotalService / (_fastfood -> dureeReelle()));
}

void Serveur :: servir(Client * c) {

	Temps * T = _fastfood -> horloge();
        double t = Random :: get(_tempsMoyenService);
                // tirage aleatoire du temps de service.

        _nbClients ++;

        _libre = 0;
                // Le serveur est occupe.

        _tempsTotalService += t;
                // Incrementation du temps total de service.

	T -> ajouter(new LiberationServeur(_fastfood, t + T->heure(), this ));
                // On stocke l'heure de liberation du serveur.

        delete c;
                // Une fois qu'il a ete servi, on supprime le client.
}
