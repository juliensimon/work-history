With Normalisation;
Use Normalisation;

Package Simulation is

Procedure Creer_Joueur (Joueur :In T_Joueur);

Function Sauvegarder (Joueur : In T_Joueur) Return String;

Procedure Restaurer (Joueur : In T_Joueur ; Nom_Fichier : In String);

Procedure Jouer (Joueur : In T_Joueur ; Double : In Boolean);

Function Choix_Chance (Joueur : In T_Joueur) Return Boolean;

Function Choix_Prison (Joueur : In T_Joueur ;
                       Nb_Tour_Prison : In Positive) Return Boolean;

Function Utiliser_Carte (Joueur : In T_Joueur ;
                         Nb_Tour_Prison : In Positive) Return Boolean;

Function Troc_Possible Return Boolean;

Function Reponse_Troc_S (Joueur : In T_Joueur ;
                         Propriete_Joueur : In T_Proprietes;
                         Joueur_Ordinateur : In T_Joueur;
                         Propriete_Ordinateur : In T_Proprietes) Return Boolean;

Procedure Jouer_Troc (Joueur : In T_Joueur);

End Simulation;
