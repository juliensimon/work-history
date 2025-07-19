
Bag subclass: #Statistiques
  instanceVariableNames: 
    'triAlpha triNote triTemps listeResultats '
  classVariableNames: ''
  poolDictionaries: '' !

!Statistiques class methods !

new

|stat liste|

liste := SortedCollection new.

stat := super new;
        ecrireListeResultats: liste;
        ecrireTriAlpha: [:x :y| y at: 1 > x at: 1.];
        ecrireTriNote:  [:x :y| y at: 2 > x at: 2.];
        ecrireTriTemps: [:x :y| x at: 3 > y at: 3.].

^stat! !


!Statistiques methods !

afficherResultatsEleve: aString

" Affiche les resultats de l'eleve au QCM s'il l'a passe "

| resultat classement note temps |

resultat := listeResultats
                detect: [:r| r at: 1 = aString]
                ifNone: ['Vous n''avez pas passe ce QCM.' afficher].

resultat notNil
    ifTrue: [note:= resultat at: 2 asString.
             temps := (resultat at: 3)/1000 asString.

             listeResultats classer: 'n'.
             classement := listeResultats indexOf: resultat asString.
             'Votre note est :' afficher.
             note afficher.
             'Votre classement est :' afficher.
             classement afficher.

             listeResultats classer: 't'.
             classement := listeResultats indexOf: resultat asString.
             'Votre temps est :' afficher.
             'Votre classment est :' afficher.
             classement afficher.
            ]!

afficherResultatsProfesseur

" Affiche les statistiques sur le QCM pour tous les eleves
  qui l'ont passe "

 | nom note temps classement |

    classement:=0.
    listeResultats classer: 'n'.
    listeResultats do: [:r| classement:= classement+1.
                            classement afficher.
                            r afficher ].

    classement:=0.
    listeResultats classer: 't'.
    listeResultats do: [:r| classement:=classement+1.
                            classement afficher.
                            r afficher.
                       ]!

classer: aString

"classe la liste des eleves ayant passe le QCM par :
    - ordre alphabetique si aString vaut 'a' ou 'A'.
    - note decroissante si aString vaut 'n' ou 'N'.
    - temps croissant si aString vaut 't' ou 'T'."

|bloc|

aString asLowerCase = 'a'
    ifTrue:  [bloc := triAlpha]
    ifFalse: [aString asLowerCase='n'
                ifTrue: [bloc := triNote]
                ifFalse:[bloc := triTemps]].

listeResultats sortBlock: bloc.!

ecrireResultats: aSortedCollection

" Affecte a la liste des resultats celle passee en parametre "

    listeResultats := aSortedCollection.!

ecrireTriAlpha: aBlock

" Affecte au tri de la liste l'ordre alphabetique "

    triAlpha := aBlock.!

ecrireTriNote: aBlock

" Affecte au tri de la liste l'ordre par notes"

    triNote := aBlock.!

ecrireTriTemps: aBlock

" Affecte au tri de la liste l'ordre par temps de reponse"

    triTemps := aBlock.!

lireResultats

" Retourne la liste des resultats du QCM "

    ^listeResultats!

moyenneNotes

" Calcule la moyenne des notes des eleves qui ont passe le QCM "

|somme|

    somme:=0.
    listeResultats do: [:x| somme := somme + (x at: 2)].

^(somme / listeResultats size)!

moyenneTemps

" Calcule la moyenne des temps des eleves qui ont passe le QCM "

|somme|

    somme:=0.
    listeResultats do: [:x| somme := somme +( x at: 3)].

^(somme / listeResultats size)! !
