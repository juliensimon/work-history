
Bag variableSubclass: #Professeur
  instanceVariableNames: 
    'nomProfesseur listeQcm listePromotions '
  classVariableNames: ''
  poolDictionaries: '' !

!Professeur class methods !

new

|retour l1 l2|

    retour:=super new.
    l1:= OrderedCollection new.
    l2:= OrderedCollection new.
    retour ecrireNom: 'noName';
           ecrireListeQcm: l1;
           ecrireListePromotion: l2.

^retour! !


!Professeur methods !

ajouterPromotionPar: aProfesseur

" Ajoute une promotion au professeur si aProfesseur est gestionnaire "

| promotion confirmation |

aProfesseur estGestionnaire
    ifTrue: [promotion:= Promotion new.
             confirmation:='n'.
             [confirmation asLowerCase = 'o']
                whileFalse: [promotion ecrireNom:
                             ('Nom d''une nouvelle promotion pour ce professeur:'
                               saisirParDefaut: '').
                             confirmation:= 'vous confirmez'
                                             saisirPar Defaut: 'o']
             listePromotions add: (promotion lireNom).
             ^promotion ]

    ifFalse: [ 'vous n''avez pas les droits necessaires
                pour cette manipulation' afficher ]!

ajouterQcmPar: aProfesseur

" Ajoute un QCM au professeur si aProfesseur est gestionnaire "

| qcm confirmation |

aProfesseur estGestionnaire
    ifTrue: [qcm:= Qcm new.
             confirmation:='n'.
             [confirmation asLowerCase = 'o']
                whileFalse: [qcm ecrireNom: ('Nom du nouveau QCM:'
                                                saisirParDefaut: '').
                             confirmation:= 'vous confirmez'
                                             saisirParDefaut: 'o'].
              listeQcm add: qcm .
              ^qcm ]

    ifFalse: [ 'vous n''avez pas les droits necessaires
                pour cette manipulation' afficher ]!

ecrireListePromotion: anOrderedCollection

" Affecte a la liste des promotions celle passee en parametre "

    listePromotions:= anOrderedCollection!

ecrireListeQcm: anOrderedCollection

" Affecte a la liste des QCM celle passee en parametre "

    listeQcm:= anOrderedCollection!

ecrireNom: aString

" Affecte au nom du professeur la chaine passee en parametre "

    nomProfesseur:= aString!

estGestionnaire

" Indique que le professeur n'est pas gestionnaire "

    nil.

^false!

lireListePromotions

" Retourne la liste des promotions o— enseigne le professeur "

    ^listePromotions!

lireListeQcm

" Retourne la liste des QCM que le professeur a ecrit "

    ^listeQcm!

lireNom

" Retourne le nom du professeur "

    ^nomProfesseur!

supprimerPromotionPar: aProfesseur

" Supprime la promotion a indiquer si:
    - aProfesseur est gestionnaire
    - le professeur y enseigne "

| selection confirmation nom |

aProfesseur estGestionnaire
    ifTrue: [ confirmation:='n'.
             [confirmation asLowerCase = 'o']
                whileFalse: [nom:= ('Nom de la promotion a retirer:'
                                     saisirParDefaut: '').
                             confirmation:= 'vous confirmez ? (o/n)'
                                             saisirParDefaut: 'o'
                            ].
             selection:= listePromotions select: [:p| p = nom].
             selection isEmpty
                ifTrue: ['la promotion n''a pas ce professeur ' afficher ]
                ifFalse: [ listePromotions remove: (selection at: 1)
                                           ifAbsent:  [ nil ]]
            ]

    ifFalse: [ 'vous n''avez pas les droits necessaires
                pour cette manipulation' afficher ]!

supprimerQcmPar: aProfesseur

" Supprime le QCM demande si:
    - il existe
    - aProfesseur en est l'auteur "

| selection confirmation nom |

aProfesseur estGestionnaire
    ifTrue: [ confirmation:='n'.
             [confirmation asLowerCase = 'o']
                whileFalse: [nom:= ('Nom du qcm a retirer:'
                                     saisirParDefaut: '').
                             confirmation:= 'vous confirmez ? (o/n)'
                                             saisirParDefaut: 'o'
                            ].
             selection:= listeQcm select: [:q| q lireNom = nom].
             selection isEmpty
                ifTrue: ['la promotion n''a pas ce QCM ' afficher ]
                ifFalse: [ listeQcm remove: (selection at: 1)
                                           ifAbsent:  [ nil ]]
            ]

    ifFalse: [ 'vous n''avez pas les droits necessaires
                pour cette manipulation' afficher ]! !
