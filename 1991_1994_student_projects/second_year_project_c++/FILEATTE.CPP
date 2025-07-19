// FileAttente.C

#include "Restaura.h"
#include "FileAtte.h"

FileAttente :: FileAttente(Restaurant * resto) {
   _longueurMax = 0;
   _aire = 0;
   _nbTotalClients = 0;
   _heureDernierEvenement = 0;
   _tempsTotalAttente = 0;
   _fastfood = resto;
}

FileAttente :: ~FileAttente() {
   for (int i = 0; i < _file.size(); i++)
      delete _file.at(i);
}

double FileAttente :: longueurMoy () const {
// Calcul de la longueur moyenne de la file d'attente a l'aide de
// l'aire que l'on divise par la duree totale de simulation.

   double longueurMoy;
   longueurMoy = _aire/_fastfood -> dureeReelle();
   return longueurMoy;
}

void FileAttente :: ajouter (Client * c) {
   _nbTotalClients++;
   double heureCourante = _fastfood -> horloge() -> heure();

   _aire += _file.size() * ( heureCourante - _heureDernierEvenement);
	   // mise a jour de l'aire.
   _file.addLast(c);
   _heureDernierEvenement = heureCourante;
        // mise a jour de l'heure du dernier evenement.

   if (_longueurMax < _file.size())
              _longueurMax = _file.size();
        // mise a jour de la longueur maximale de la file si necessaire.
}

Client * FileAttente :: retirer () {
   Client * c;
   double heureCourante = _fastfood->horloge()->heure();

   if (! _file.isEmpty()) {
      _aire += _file.size() * (heureCourante - _heureDernierEvenement);
      c = (Client *) _file.removeFirst();
        // Retrait du premier client de la file.

      _tempsTotalAttente += ( heureCourante - c->heureArrivee() );
        // On augmente le temps total d'attente.

      _heureDernierEvenement = heureCourante;
        //mMise a jour de l'heure du dernier evenement.
   }
   else c = 0;

   return c;
}
