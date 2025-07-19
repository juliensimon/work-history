#ifndef __CLIENT_H
#define __CLIENT_H

// La classe Client definit un client du restaurant.

#include "Object.h"

class Client : public Object {

   protected:
      double _heureArrivee;

   public:
      virtuals(Client);

      Client(double);
        // On indique l'heure d'arrivee du client.

      double heureArrivee() const;
};

inline double Client :: heureArrivee() const { return _heureArrivee;}
inline Client :: Client(double h) { _heureArrivee = h;}

#endif
