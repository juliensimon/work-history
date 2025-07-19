
Bag subclass: #Qcm
  instanceVariableNames: 
    'numeroQcm titreQcm listeQuestions notes '
  classVariableNames: ''
  poolDictionaries: '' !

!Qcm class methods !

new

|questionnaire liste|

questionnaire := super new.
liste := OrderedCollection new.
questionnaire ecrireTitre: '';
              ecrireNumero: '0';
              ecrireQuestions: liste.

^questionnaire! !


!Qcm methods !

afficherResultatsEleve: anEleve

" Affiche tous les resultats concernant anEleve "

    'resultats du QCM intitule:' afficher.
    titreQcm afficher.
    notes afficherResultatsEleve: (anEleve lireNom)!

afficherResultatsProfesseur: aProfesseur

" Affiche les resultats du Qcm si aProfesseur en est l'auteur "

    'resultats du QCM intitule:' afficher.
    aProfesseur lireListeQcm includes: self
    ifTrue: [ titreQcm afficher.
              notes afficherResultatsProfesseur: (aProfesseur lireNom)]
    ifFalse: ['Vous n''etes pas l''auteur de ce QCM.']!

ecrireNumero: aString

" Affecte au numero la chaine passee en parametre "

    numeroQcm := aString.!

ecrireQuestions: anOrderedCollection

"affecte a la liste des questions celle passee en parametre "

    listeQuestions := anOrderedCollection.!

ecrireTitre: aString

"affecte au titre du QCM la chaine passee en parametre "

    titreQcm := aString.!

editerQcm: aProfesseur

" Propose une correction du QCM mais uniquement par le professeur
  l'ayant cree.  Si le QCM est vide, cree de nouvelles questions "

|choix question|

aProfesseur lireListeQcm includes: self
    ifTrue:[listeQuestions isEmpty
        ifTrue: ['La liste des questions est vide.' afficher.
                  self nouveauQcmPar: aProfesseur ]
        ifFalse: [listeQuestions do:
                    [:q| q afficherQuestion.
                     'Voulez-vous modifier cette question ?' ouiOuNon
                        ifTrue: [q editerQuestion] ].
                  'Voulez-vous ajouter des questions ?' ouiOuNon
                     ifTrue: [ self nouveauQcmPar: aProfesseur ]
                 ]
           ]
ifFalse: ['Vous n''etes pas l''auteur de ce QCM.' afficher].!

lireNumero

" Retourne le numero du Qcm"

    ^numeroQcm!

lireTitre

" Retourne le tire du Qcm"

    ^titreQcm!

nouveauQcmPar: aProfesseur

" Ajoute ou cree de nouvelles questions a un QCM
  si aProfesseur en est l'auteur "

| question |

aProfesseur lireListeQcm includes: self
    ifTrue: [
             ['Voulez-vous ajouter une question ?' ouiOuNon]
                 whileTrue: [question:= Question new.
                             question nouvelleQuestion.
                             listeQuestions add: question]
            ]

    ifFalse: ['Vous n''etes pas l''auteur de ce QCM.' afficher]!

passerQcm: anEleve

" Fait passer le QCM a un eleve si celui-ci ne l'a pas deja passe.
  Met automatiquement a jour les statistiques du QCM "

| note temps score |
note:=0.
temps:=0.

anEleve lireListeQcm includes: self lireNumero

    ifTrue: ['vous avez deja passe ce QCM .' afficher ]
    ifFalse: [listeQuestions do: [:q|
                                  score:= q poserQuestion.
                                  note := note + score at: 1.
                                  temps := temps + score at: 2].
             notes ecrireListeResultats:
                   ( notes lireListeResultats add:
                   #((anEleve lireNom:) note temps) ).
             anEleve ajouterQcm: numeroQcm.
             ].

^ #(note temps)! !
