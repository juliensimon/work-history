#ifndef __FILE_ATTENTE_H
#define __FILE_ATTENTE_H

// La classe FileAttente decrit la file des clients attendant d'etre servis.

#include "OrderedC.h"
#include "Client.h"

class Restaurant;

class FileAttente : public Object {
  
   private:
      Restaurant *      _fastfood;
      OrderedCollection _file;
	// file d'attente des clients.
      int _nbTotalClients;
        // nombre de clients entres dans le restaurant.
      double _heureDernierEvenement;
	// heure du dernier evenement
      int _longueurMax;
	// longueur maximale de la file d'attente.
      double _aire;
	// Aire de la courbe representant le nombre de clients en attente
	// en fonction du temps.
      double _tempsTotalAttente;
	// Somme des temps d'attente de tous les clients qui sont
	// passes dans la file.

   public:
      FileAttente(Restaurant * resto);
      virtuals(FileAttente);
      ~FileAttente();
      int      nbTotalClientsFile() const;
      int      longueur()      const;
      int      longueurMax ()  const;
      double   longueurMoy ()  const;
      double   aireAttente()   const;
      int      estVide()       const;
      void     ajouter(Client *);
      Client * retirer();
};

inline int FileAttente :: nbTotalClientsFile () const {return _nbTotalClients;}
inline int FileAttente :: longueur () const {return _file.size();}
inline int FileAttente :: longueurMax () const {return _longueurMax;}
inline double FileAttente :: aireAttente () const {return _aire;}
inline int FileAttente :: estVide () const {return (_file.isEmpty());}

#endif
