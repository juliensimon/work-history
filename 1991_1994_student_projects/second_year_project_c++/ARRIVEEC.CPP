// ArriveeClient.C

#include "Restaura.h"
#include "ArriveeC.h"
#include "Temps.h"
#include "Client.h"
#include "Random.h"
#include "Serveur.h"

ArriveeClient :: ArriveeClient(Restaurant * resto, double t)
   : Evenement(resto,t)
{}


void ArriveeClient :: traiter() {

   Serveur * serveur;
   Temps * T = _fastfood -> horloge();
   Client * client = new Client(T -> heure());
        // Creation du client qui doit arriver a l'heure indiquee.

   double heureArriveeClientSuivant =
   T->heure() + Random :: get(_fastfood -> tempsMoyArrivee());
        // Calcul de l'heure d'arrivee du prochain client.

   serveur = _fastfood -> serveurLibre();
	// renvoie un serveur libre (s'il y en a un).

   if (heureArriveeClientSuivant < _fastfood -> dureePrevue())
      T -> ajouter(new ArriveeClient(_fastfood, heureArriveeClientSuivant));
      // Creation et ajout dans la liste des evenements de
      // l'arrivee du prochain client, si l'heure de son arrivee
      // ne depasse pas l'heure de fermeture prevue.

   if (serveur)
      serveur -> servir(client);
      // on sert le client s'il y a un serveur libre.
   else
      _fastfood -> fileAttente() -> ajouter(client);
      // sinon, il fait la queue.
}
