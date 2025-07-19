// Temps.C

#include "Temps.h"

Temps :: Temps () { _heureCourante = 0;}

void Temps :: ajouter(Evenement * evenement) { _gestEvt.add(evenement);}

Evenement * Temps :: suivant() {
   Evenement * evenement;
   if (_gestEvt.isEmpty()) {
      evenement = (Evenement *) 0;
      // on renvoie 0 s'il n'y a plus d'evenements.
   }
   else {
	   evenement = (Evenement *) _gestEvt.removeFirst();
      _heureCourante = evenement -> heure();
      // L'heure courante est l'heure du dernier evenement retire.
   }

   return evenement;
}

int Temps:: simulationTerminee () const { return _gestEvt.isEmpty();}

