#ifndef __LIBERATION_SERVEUR_H
#define __LIBERATION_SERVEUR_H

// La classe LiberationServeur gere le mecanisme de liberation d'un serveur
// Cette classe derive de Evenement.

#include "Evenemen.h"

class Restaurant;
class Serveur;

class LiberationServeur : public Evenement {

   private:
      Serveur * _serveur;
        // serveur a liberer.

   public:
      LiberationServeur(Restaurant *, double, Serveur *);
	// On precise le serveur a liberer, l'heure a laquelle il est libere
        // et le restaurant.

      virtuals(LiberationServeur);
      ostream& printOn (ostream& os) const;

      void traiter();
	// Le Serveur est libere et sert le premier client de la file
	// d'attente si celle-ci n'est pas vide.
};


inline LiberationServeur :: LiberationServeur (Restaurant * resto,
					       double t, Serveur * serv)
   : Evenement (resto,t)
{  _serveur = serv;
}

inline ostream& LiberationServeur :: printOn (ostream& os) const {
   return os << "Liberation Serveur";
}

#endif
