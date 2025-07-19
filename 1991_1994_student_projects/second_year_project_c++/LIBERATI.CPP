// LiberationServeur.C

#include "Liberati.h"
#include "Restaura.h"
#include "Serveur.h"
#include "FileAtte.h"

void LiberationServeur :: traiter () {

   _serveur -> liberer();
   // on libere le serveur.

   FileAttente * fa = _fastfood -> fileAttente();

   if (! fa -> estVide())
   // Si la file d'attente n'est pas vide,
      _serveur -> servir(fa -> retirer());
      // alors le serveur sert le premier client de la file.

}

