#ifndef __SERVEUR_H
#define __SERVEUR_H

// La classe Serveur decrit un serveur du restaurant.

#include "Object.h"

class Restaurant;
class Client;

class Serveur : public Object {
  
   protected:
      double _tempsMoyenService;
      Restaurant * _fastfood;
      double _tempsTotalService;
      int _nbClients;
        // nombre de clients servis par le serveur.
      int _libre;
      // booleen indiquant si le serveur est libre ou non.
 
   public:
      virtuals(Serveur);

      Serveur(Restaurant *, double);
      // On indique le temps moyen de service du serveur et le restaurant.

      double tempsMoyenService() const;

      double tempsTotalService() const;

      double pourcentageUtilisation() const;

      int nbClientsServis() const;

      int estLibre() const;

      void servir(Client *);

      void liberer();
};

inline double Serveur :: tempsMoyenService() const {return _tempsMoyenService;}
inline double Serveur :: tempsTotalService() const {return _tempsTotalService;}
inline int Serveur :: nbClientsServis() const {return _nbClients;}
inline int Serveur :: estLibre() const {return _libre;}
inline void Serveur :: liberer() {_libre = 1;}

#endif
