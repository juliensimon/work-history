// Fastfood.C
// PROGRAMME PRINCIPAL

#include "Restaura.h"
#include "iostream.h"
#include "String.h"
#include <stdlib.h>
#include "new.h"

void PlusDeMemoire() {
   cout << "Pas assez de m�moire.\n";
   exit(-1);
}

int main () {
   double dureePrevue;
   int nbServeurs;
   double tempsMoyenArriveeClients;

   std::set_new_handler(PlusDeMemoire);

   do {
      cout << "Duree de la simulation : ";
      cin >> dureePrevue;
      dureePrevue = dureePrevue * 60;
   } while (dureePrevue <= 0);

   do {
      cout << "Nombre de serveurs : ";
      cin >> nbServeurs;
   } while (nbServeurs < 1);

   do {
      cout << "Intervalle de temps entre l'arrivee de deux clients : ";
      cin >> tempsMoyenArriveeClients;
   } while ((tempsMoyenArriveeClients <= 0)
		|| (tempsMoyenArriveeClients > dureePrevue));

   Restaurant resto((int) nbServeurs, dureePrevue,tempsMoyenArriveeClients);

   resto.go();
	 // Lancement de la simulation.
   return 0;
}

