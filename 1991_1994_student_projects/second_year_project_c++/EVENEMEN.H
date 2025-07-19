#ifndef __EVENEMENT_H
#define __EVENEMENT_H

// La classe abstraite Evenement decrit les evenements qui se produisent
// dans le restaurant.
// Les evenements se produisent par ordre chronologique.
// Evenement derive de Magnitude pour que SortedCollection soit accessible.

#include "Magnitud.h"

class Restaurant;

class Evenement : public Magnitude {

protected:
   double _heure;
        // Heure a laquelle se produit l'evenement.
   Restaurant * _fastfood;

public:
   virtuals(Evenement);

   Evenement(Restaurant *, double);
   // On indique l'heure a laquelle se produit l'evenement et
   // le restaurant ou il se produit.

   virtual void traiter() = 0;

   double heure() const;

   int lessThan (const Magnitude * m) const;
   // Permet d'ordonner les evenements par ordre chronologique.

};

inline Evenement :: Evenement(Restaurant * resto, double h) {
	_heure = h;
	_fastfood = resto;
}

inline double Evenement :: heure() const { return _heure;}

inline int Evenement :: lessThan (const Magnitude * m) const {
    return _heure < ((Evenement *)m) -> _heure;
}

#endif
