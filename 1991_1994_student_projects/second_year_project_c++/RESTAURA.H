#ifndef __RESTAURANT_H
#define __RESTAURANT_H

// La classe Restaurant decrit la structure d'un restaurant et en permet
// la simulation.

#include <String.h>
#include "Object.h"
#include "Temps.h"
#include "FileAtte.h"

class Serveur;

class Restaurant : public Object {

   private:
      int _nbServeurs;
        // Nombre de serveurs dans le restaurant.
      Serveur ** _tableauServeurs;
        // tableau de pointeurs sur les serveurs.
      double _dureePrevue;
        // duree prevue de la simulation.
      double _tempsMoyArrivee;
	// temps moyen qui s'ecoule entre l'arrivee de deux clients.
      Temps _horloge;
	// horloge permettant de gerer l'ecoulement du temps.
      FileAttente  _fileAttente;
        // file d'attente du restaurant.

      
   public:
      Restaurant ( int nbServeurs, double dureePrevue,
			double tpsMoyArrClt);

      virtuals(Restaurant);

      ~Restaurant();
      // Le destructeur detruit tous les serveurs apres simulation.

      void initServeurs();
      // initialise le temps moyen de service des serveurs.

      int nbServeurs() const;

      double dureePrevue() const;

      double dureeReelle() const;
	// renvoie la duree reelle de la simulation.
	// elle peut depasser la duree prevue, car tous les clients qui font la
	// queue doivent etre servis.

      double tempsMoyArrivee() const;

      Temps * horloge();

      FileAttente * fileAttente();

      Serveur * serveurLibre() const;
	// renvoie un serveur libre choisi au hasard.
	// si aucun n'est libre, renvoie zero.

      void go ();
	// debute la simulation.


};

inline int Restaurant :: nbServeurs() const {
   return _nbServeurs;
}

inline double Restaurant :: dureePrevue() const {return _dureePrevue;}

inline double Restaurant :: dureeReelle()  const {
   if (_horloge.simulationTerminee())
      return _horloge.heure();
   else
      return _dureePrevue;
}

inline double Restaurant :: tempsMoyArrivee() const {return _tempsMoyArrivee;}
inline Temps * Restaurant :: horloge() {return &_horloge;}
inline FileAttente * Restaurant :: fileAttente() {return &_fileAttente;}

#endif
