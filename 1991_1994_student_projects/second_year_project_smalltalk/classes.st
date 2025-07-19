"Basic Smalltalk classes for the QCM system"

Object subclass: #Eleve
    instanceVariableNames: 'nomEleve listeQcmPasses'
    classVariableNames: ''
    poolDictionaries: ''
    category: 'QCM'!

Eleve class extend [
    new [
        | retour liste |
        retour := super new.
        liste := OrderedCollection new.
        retour ecrireNom: 'noName'.
        retour ecrireListeQcm: liste.
        ^retour
    ]
]!

Eleve extend [
    ajouterQcm: aNumero [
        "Ajoute le numero du QCM termine dans la liste des QCM passes"
        listeQcmPasses addLast: aNumero
    ]
    
    ecrireListeQcm: anOrderedCollection [
        "Affecte a la liste des QCM celle passee en parametre"
        listeQcmPasses := anOrderedCollection
    ]
    
    ecrireNom: aString [
        "Affecte au nom de l'eleve la chaine passee en parametre"
        nomEleve := aString
    ]
    
    lireListeQcm [
        "Retourne la liste des QCM passes par l'eleve"
        ^listeQcmPasses
    ]
    
    lireNom [
        "Retourne le nom de l'eleve"
        ^nomEleve
    ]
]!

"OrderedCollection class (basic implementation)"
Object subclass: #OrderedCollection
    instanceVariableNames: 'contents'
    classVariableNames: ''
    poolDictionaries: ''
    category: 'Collections'!

OrderedCollection class extend [
    new [
        ^super new initialize
    ]
]!

OrderedCollection extend [
    initialize [
        contents := Array new: 10.
        ^self
    ]
    
    addLast: anObject [
        contents := contents copyWith: anObject
    ]
    
    add: anObject [
        ^self addLast: anObject
    ]
    
    isEmpty [
        ^contents size = 0
    ]
    
    size [
        ^contents size
    ]
    
    do: aBlock [
        contents do: aBlock
    ]
    
    select: aBlock [
        | result |
        result := OrderedCollection new.
        self do: [:each | (aBlock value: each) ifTrue: [result add: each]].
        ^result
    ]
    
    includes: anObject [
        ^contents includes: anObject
    ]
    
    remove: anObject ifAbsent: aBlock [
        | index |
        index := contents indexOf: anObject.
        index = 0 
            ifTrue: [^aBlock value]
            ifFalse: [
                contents := contents copyFrom: 1 to: index-1,
                           copyFrom: index+1 to: contents size.
                ^anObject
            ]
    ]
    
    at: index [
        ^contents at: index
    ]
]! 