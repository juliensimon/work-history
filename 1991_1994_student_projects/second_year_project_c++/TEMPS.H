#ifndef __TEMPS_H
#define __TEMPS_H

// La classe Temps permet de gerer le temps.
// Un temps est un double, exprime en minutes.

#include "Object.h"
#include "SortedCo.h"
#include "Evenemen.h"

class Temps : public Object{

 protected:
    double _heureCourante;
    SortedCollection _gestEvt;
        // liste d'evenements.

  public:
    virtuals(Temps);

    Temps ();

    void ajouter (Evenement *);

    Evenement * suivant ();
        // On recupere le prochain evenement de la liste.

    double heure() const;
       // renvoie l'heure courante.

    int simulationTerminee() const;
    // booleen indiquant la fin de la simulation

};

inline double Temps :: heure() const {return _heureCourante;}

#endif
