
Bag variableSubclass: #Question
  instanceVariableNames: 
    'enonceQuestion nbPoints tempsImparti listeReponses '
  classVariableNames: ''
  poolDictionaries: '' !

!Question class methods !

new

|question liste|

    liste := OrderedCollection new.
    question := super new.
    question ecrireEnonce: '';
             ecrirePoints: #(1 -1 0);
             ecrireTemps: 30;
             ecrireReponses: liste.

^question! !


!Question methods !

afficherQuestion

" Affiche l'enonce de la question puis ses reponses possibles
  affectees de leur numero "

    enonceQuestion afficher.

    listeReponses do: [:r|
                       ( listeReponses indexOf: r ) asString afficher.
                       r afficherReponse].!

ecrireEnonce: aString

" Affecte a l'enonce de la question la chaine passee en parametre "

    enonceQuestion := aString.!

ecrirePoints: anArray

" Affecte a nbPoints un tableau de trois SmallInteger indiquant :
    - le nombre de points en cas de bonne reponse.
    - le nombre de points en cas de mauvaise reponse.
    - le nombre de points en cas de non-reponse. "

    nbPoints := anArray.!

ecrireReponses: anOrderedCollection

" Affecte a la liste des questions celle passee en parametre "

    listeReponses := anOrderedCollection.!

ecrireTemps: aSmallInteger

" Affecte a tempsImparti le nombre de secondes passe en parametre "

|tempsPositif|

    tempsPositif := aSmallInteger positive.

    tempsPositif
        ifTrue: [tempsImparti := aSmallInteger.].

^tempsPositif!

editerPoints

" Modifie le bareme  de la question "

    nbPoints at: 1 put: ('reponse correcte:' saisirParDefaut: '0')
                          asInteger .
    nbPoints at: 2 put: ('reponse fausse:' saisirParDefaut: '0')
                          asInteger .
    nbPoints at: 3 put: ('sans reponse:' saisirParDefaut: '0')
                          asInteger .!

editerQuestion

" Modifie l'enonce, le bareme, le temps imparti
  et les reponses possibles de la question "

| r tableau |

listeReponses isEmpty
    ifTrue: ['La liste des reponses est vide.' afficher]
    ifFalse: [ enonceQuestion afficher.
               'Voulez-vous modifier l''enonce ?' ouiOuNon
                   ifTrue: [enonceQuestion:=
                            ('Entrez l''enonce.' saisirParDefaut: '')].

               self afficherQuestion.
               'Voulez-vous modifier les reponses ?' ouiOuNon
                    ifTrue: [ listeReponses do: [:r| r editerReponse]].

               nbPoints afficher.
               'Voulez-vous modifier le bareme de la question ?' ouiOuNon
                    ifTrue: [self editerPoints ].

               tempsImparti afficher.
               'Voulez-vous modifier le temps imparti ?'ouiOuNon
                    ifTrue: [self editerTemps ].
             ].

self afficherQuestion.
['Voulez-vous ajouter une reponse ?' ouiOuNon ]
     whileTrue: [r := Reponse new.
                 r editerReponse.
                 listeReponses add: r.
                ]!

editerTemps

" Modifie le temps imparti a une question "

    tempsImparti:= ('temps imparti (en secondes):' saisirParDefaut: 30)
                    asInteger .!

lireEnonce

" Retourne l'enonce de la question"

    ^enonceQuestion!

lirePoints

" Retourne le nombre de points "

    ^nbPoints!

lireReponses

" Retourne la liste des reponses proposees a l'eleve"

    ^listeReponses!

lireTemps

" Retourne le temps imparti pour repondre a la question"

    ^tempsImparti!

nouvelleQuestion

" Cree et saisit une question ainsi que tous ses parametres "

| r b |

    enonceQuestion:= ('Entrez l''enonce.' saisirParDefaut: '').
    enonceQuestion afficher.

    nbPoints afficher.
     'Voulez-vous modifier le bareme de la question ?' ouiOuNon
        ifTrue: [self editerPoints ] .

    tempsImparti afficher.
    ('Voulez-vous modifier le temps imparti ?' ouiOuNon )
        ifTrue: [self editerTemps ].

    ['Voulez-vous ajouter une reponse ?' ouiOuNon ]
        whileTrue: [r := Reponse new.
                    r editerReponse.
                    listeReponses add: r.
                    r afficherReponse ]!

poserQuestion

" Affiche la question et ses reponses possibles puis
  demande une reponse en temps limite. si celui-ci
  est depasse, toute reponse est consideree fausse .
  La correction est automatique "

| tempsEcoule choix score retour |

choix :=-1.
retour:= #(0 40).

self afficherQuestion.
'temps limite' afficher.
tempsImparti afficher.

tempsEcoule := Time millisecondsToRun:
               [ [choix <= listeReponses size and: [ choix positive ] ]
                      whileFalse:
                          [choix:= ( 'Entrez le numero de la reponse.'
                           saisirParDefaut: '' ) asInteger ]
                ]

tempsEcoule > (1000*tempsImparti)
    ifTrue: ['Vous n''avez pas repondu dans le temps imparti.' afficher.
              score:= nbPoints at: 2]
    ifFalse: [ choix = 0
                ifTrue: [score:= nbPoints at: 3]
                ifFalse: [(listeReponses at: choix) estBonne
                             ifTrue: [score:= nbPoints at: 1]
                             ifFalse: [score:= nbPoints at: 2]
                        ]
             ].
retour at: 1 put: score;
       at: 2 put: tempsEcoule.
retour afficher.  "pour les tests"

 ^retour! !
