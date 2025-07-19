
Bag variableSubclass: #Promotion
  instanceVariableNames: 
    'nomPromotion listeEleves '
  classVariableNames: ''
  poolDictionaries: '' !

!Promotion class methods !

new

|retour liste|

retour:=super new.
liste:= OrderedCollection new.
retour ecrireNom: 'fantome';
       ecrireListeEleves: liste.

^retour! !


!Promotion methods !

ajouterElevePar: aProfesseur

" Ajoute un eleve dans la promotion si aProfesseur est gestionnaire "

| eleve confirmation |

aProfesseur estGestionnaire
    ifTrue: [eleve:= Eleve new.
             confirmation:='n'.
             [confirmation asLowerCase = 'o']
                whileFalse: [eleve ecrireNom: ('Nom du nouvel eleve:'
                                                saisirParDefaut: '').
                             confirmation:= 'vous confirmez ? (o/n)'
                                             saisirParDefaut: 'o'].
             listeEleves add: eleve ]

    ifFalse: [ 'vous n''avez pas les droits necessaires
                pour cette manipulation' afficher ]!

ecrireListeEleves: anOrderedCollection

" Affecte a la liste des eleves celle passee en parametre "

    listeEleves:= anOrderedCollection!

ecrireNom: aString

" Affecte au nom de la promotion la chaine passee en parametre "

    nomPromotion:=aString!

lireListeEleves

" Retourne la liste des eleves "

    ^listeEleves!

lireNomPromotion

" Retourne le nom de la promotion "

    ^nomPromotion!

supprimerElevePar: aProfesseur

" Supprime un eleve de la promotion si:
    - l'eleve y appartient
    - aProfesseur est gestionnaire "

| nom confirmation selection|

aProfesseur estGestionnaire
    ifTrue: [confirmation:='n'.
             [confirmation asLowerCase = 'o']
                whileFalse: [nom:= ('Nom de l''eleve a retirer:'
                                     saisirParDefaut: '').
                             confirmation:= 'vous confirmez ? (o/n)'
                                             saisirParDefaut: 'o'
                            ].
             selection:= listeEleves select: [:e| e lireNom = nom].
             selection isEmpty
                ifTrue: ['l''eleve n''est pas dans cette promotion'
                          afficher ]
                ifFalse: [listeEleves remove: (selection at: 1)
                                      ifAbsent:[nil]]
            ]
    ifFalse: [ 'vous n''avez pas les droits necessaires
                pour cette manipulation' afficher ]! !
