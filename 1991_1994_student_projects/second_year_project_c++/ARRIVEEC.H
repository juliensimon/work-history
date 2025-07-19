#ifndef __ARRIVEECLIENT_H
#define __ARRIVEECLIENT_H

// La classe ArriveeClient gere l'arrivee d'un nouveau client dans le
// restaurant.
// Elle d‚rive de la classe Evenement.

#include "Evenemen.h"

class Restaurant;

class ArriveeClient : public Evenement {

 public:

    ArriveeClient(Restaurant *, double t);
            // On indique l'heure d'arrivee du client et le restaurant dans
            // lequel il arrive.
      
    virtuals(ArriveeClient);
    ostream& printOn (ostream& os) const;

    // L'arrivee d'un client entraine le calcul de l'heure d'arrivee
    // du client suivant si l'heure de fermeture n'est pas atteinte.
    // De plus, si un ou plusieurs serveurs sont libres, celui-ci se dirigera
    // vers l'un d'eux hasard.
    // Si tous les serveurs sont occupes, il va dans la file d'attente.
    void traiter ();

};

inline ostream& ArriveeClient :: printOn (ostream& os) const {
   return os << "Arrivee client";
   // affichage de l'evenement ArriveeClient
}

#endif
