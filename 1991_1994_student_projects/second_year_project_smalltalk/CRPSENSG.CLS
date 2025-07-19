
OrderedCollection variableSubclass: #CorpsEnseignant
  instanceVariableNames: ''
  classVariableNames: ''
  poolDictionaries: '' !

!CorpsEnseignant class methods !

new

| retour |
retour:= super new.

^retour! !


!CorpsEnseignant methods !

ajouterGestionnaire

" Ajoute un gestionnaire au corps enseignant
  s'il n'esiste pas deja "

| gestionnaire confirmation |

self isEmpty
    ifTrue: [ gestionnaire:= Gestionnaire new.
              confirmation:= 'n'.
              [confirmation asLowerCase = 'o']
                  whileFalse: [ gestionnaire ecrireNom:
                                 ('entrez le nom du futur gestionnaire'
                                   saisirParDefaut:'').
                               confirmation:= 'vous confirmez? (o/n)'
                                               saisirParDefaut:'o'].
              self add: gestionnaire .
              ^gestionnaire  ]

    ifFalse: ['vous n''avez pas le droit d''entrer
                 un second gestionnaire' afficher ]!

ajouterProfesseurPar: aProfesseur

" Ajoute un professeur  au corps enseignant
  si aProfesseur est gestionnaire "


| professeur confirmation |

aProfesseur estGestionnaire
    ifTrue: [professeur := Professeur new.
             confirmation := 'n'.
             [confirmation asLowerCase = 'o']
                 whileFalse: [ professeur ecrireNom:
                                ('entrez le nom du nouveau professeur'
                                  saisirParDefaut:'').
                              confirmation:= 'vous confirmez? (o/n)'
                                              saisirParDefaut:'o'].

              self add: professeur .
              ^professeur ]

    ifFalse: ['vous n''avez pas les droits necessaires
               pour cette manipulation' afficher ]! !
