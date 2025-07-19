// Restaurant.C

#include <iostream.h>
#include <stdlib.h>
#include <String.h>
#include <math.h>
#include "Restaura.h"
#include "FileAtte.h"
#include "Temps.h"
#include "Random.h"
#include "Serveur.h"
#include "ArriveeC.h"
#ifndef RAND_MAX
#define RAND_MAX 32767
#endif

void Restaurant :: initServeurs() {
   double tempsMoyenService;

   for (int i = 0; i < _nbServeurs; i++) {
	 do {
	    cout << "Temps Moyen de Service du serveur nø " << (i+1) << " : ";
	    cin >> tempsMoyenService;
	 } while (tempsMoyenService <= 0);

	_tableauServeurs[i] = new Serveur(this, tempsMoyenService);
	      // creation du ieme serveureur.
   }
}

Restaurant :: Restaurant (int nbServeurs, double dureePrevue,
				double tempsMoyenArriveeClient)
: _fileAttente(this)
{
   _nbServeurs      = nbServeurs;
   _dureePrevue     = dureePrevue;
   _tempsMoyArrivee = tempsMoyenArriveeClient;
   _tableauServeurs = new Serveur* [_nbServeurs];
   initServeurs();
}

Restaurant :: ~Restaurant() {
   delete [] _tableauServeurs;
}

Serveur * Restaurant :: serveurLibre() const {
   int j=0;
   int nbServeursLibres = 0;
   Serveur ** tmpserveur;
	   // tableau de pointeurs les serveurs libres.
   Serveur * serveur;

   tmpserveur = new Serveur* [_nbServeurs];
   for (int i = 0; i < _nbServeurs; i++) {
      if (_tableauServeurs[i] -> estLibre()) {
	 tmpserveur[nbServeursLibres++] = _tableauServeurs[i];
		 // Si des serveurs sont libres, on les met dans le tableau.
      }
   }
   if (nbServeursLibres) {
      j = (int) (nbServeursLibres * (double(rand())/RAND_MAX));
	// choix d'un serveur libre.
      serveur = tmpserveur[j];
   }
   else serveur = 0;
   return serveur;
}

void Restaurant :: go () {
   Evenement * evenement;
   Serveur * serveur;
   int nombreTotalClients = 0;
   double heureArriveePremierClient = Random :: get (_tempsMoyArrivee);

   _horloge.ajouter( new ArriveeClient(this, heureArriveePremierClient) );
	// Generation de l'arrivee du premier client.
   while ( ! _horloge.simulationTerminee() ) {
      // Boucle de traitement des evenements.
      evenement = _horloge.suivant();
      evenement -> traiter();
      if (evenement != 0) {
	 delete evenement;
	      // Desallocation de l'evenement lorsque celui-ci a ete traite.
      }
   }

// Affichage des resultats de la simulation
   cout << "\nResultats de la simulation sur le restaurant\n\n";
   cout << "Duree prevue    : " << _dureePrevue << " minutes \n";
   cout << "Duree effective : " << _horloge.heure() << " minutes\n\n\n";
   cout << "Resultats par serveur\n\n";

   for (int i = 0; i < _nbServeurs; i++) {
      serveur = _tableauServeurs[i];
      cout << "Serveur nø " << (i+1) << ":\n";
      cout << "   Temps moyen de service    : "
	   << ceill(serveur->tempsMoyenService()) << " minute(s)\n";
      cout << "   Temps total de service    : "
	   << ceill(serveur->tempsTotalService()) << " minutes\n";
      cout << "   Nombre de clients servis  : "
	   << serveur->nbClientsServis() << "\n";
      cout << "   Pourcentage d'utilisation : "
	   << serveur->pourcentageUtilisation() << " %\n\n" ;
      nombreTotalClients += serveur->nbClientsServis();
      // calcul du nombre total de clients servis.
   }

   cout << "Nombre total de clients servis          : "
	<< nombreTotalClients << "\n";
   cout << "Nombre total de clients qui ont attendu : "
	<< _fileAttente.nbTotalClientsFile() << "\n";
   cout << "Longueur moyenne de la file d'attente   : "
	<< ceill(_fileAttente.longueurMoy()) << " client(s)\n";
   cout << "Longueur maximale de la file d'attente  : "
	<< ceill(_fileAttente.longueurMax()) << " client(s)\n" ;
   cout << "Temps moyen d'attente dans la file      : "
	<< _fileAttente.aireAttente()/nombreTotalClients << " minute(s)\n\n" ;

}
