
Bag variableSubclass: #Eleve
  instanceVariableNames: 
    'nomEleve listeQcmPasses '
  classVariableNames: ''
  poolDictionaries: '' !

!Eleve class methods !

new

|retour liste|
    retour:= super new.
    liste:= OrderedCollection new.
    retour ecrireNom: 'noName';
           ecrireListeQcm: liste.

^retour! !


!Eleve methods !

ajouterQcm: aNumero

" Ajoute le numero du QCM termine dans la liste des QCM passes "

    listeQcmPasses addLast: aNumero!

coucou

self aff: 'Salut bande de nazes !!'!

ecrireListeQcm: anOrderedCollection

" Affecte a la liste des QCM celle passee en parametre "

    listeQcmPasses:=anOrderedCollection!

ecrireNom: aString

" Affecte au nom de l'eleve  la chaine passee en parametre "

    nomEleve:=aString!

lireListeQcm

" Retourne la liste des QCM passes par l'eleve "

    ^listeQcmPasses!

lireNom

" Retourne le nom de l'eleve "

    ^nomEleve!

menuEleve


^Menu
    labels: 'Passer un Q.C.M.\Voir vos resultats\Coucou' withCrs
    lines: Array new
    selectors: #(passerQcm voirResultats coucou)!

travailEleve


^ 'Bon courage !!'!

travailler

|topPane |


topPane := TopPane new label: 'Eleve'.


topPane addSubpane:
     (TextPane new
        model: self;
        name: #travailEleve;
        menu: #menuEleve
     ).

topPane dispatcher open scheduleWindow! !
