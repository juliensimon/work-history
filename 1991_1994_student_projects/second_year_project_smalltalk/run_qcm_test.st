"QCM System Test Script for Squeak CLI"
"Run this with: /Applications/Squeak6.0-22148-64bit-All-in-One.app/Contents/MacOS/Squeak /Applications/Squeak6.0-22148-64bit-All-in-One.app/Contents/Resources/Squeak6.0-22148-64bit.image run_qcm_test.st"

"First, define our classes"
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
		listeQcmPasses addLast: aNumero
	].
	
	ecrireListeQcm: anOrderedCollection [
		listeQcmPasses := anOrderedCollection
	].
	
	ecrireNom: aString [
		nomEleve := aString
	].
	
	lireListeQcm [
		^listeQcmPasses
	].
	
	lireNom [
		^nomEleve
	]
]!

Object subclass: #Professeur
	instanceVariableNames: 'nomProfesseur listeQcm listePromotions'
	classVariableNames: ''
	poolDictionaries: ''
	category: 'QCM'!

Professeur class extend [
	new [
		| retour l1 l2 |
		retour := super new.
		l1 := OrderedCollection new.
		l2 := OrderedCollection new.
		retour ecrireNom: 'noName'.
		retour ecrireListeQcm: l1.
		retour ecrireListePromotion: l2.
		^retour
	]
]!

Professeur extend [
	ecrireListePromotion: anOrderedCollection [
		listePromotions := anOrderedCollection
	].
	
	ecrireListeQcm: anOrderedCollection [
		listeQcm := anOrderedCollection
	].
	
	ecrireNom: aString [
		nomProfesseur := aString
	].
	
	estGestionnaire [
		^false
	].
	
	lireListePromotions [
		^listePromotions
	].
	
	lireListeQcm [
		^listeQcm
	].
	
	lireNom [
		^nomProfesseur
	]
]!

"Now run the test"
Transcript clear.
Transcript show: '=== QCM System Test ==='; cr.
Transcript show: 'Loading classes...'; cr.

| e p |
e := Eleve new.
e ecrireNom: 'juju'.
e ajouterQcm: 1.
e ajouterQcm: 3.

Transcript show: 'Student name: '; show: e lireNom; cr.
Transcript show: 'QCM list: '; show: e lireListeQcm; cr.

p := Professeur new.
p ecrireNom: 'boud'.
Transcript show: 'Professor name: '; show: p lireNom; cr.
Transcript show: 'Is manager: '; show: p estGestionnaire; cr.

Transcript show: '=== Test completed successfully! ==='; cr.
Transcript show: 'Your 1991-1994 Smalltalk project is running on macOS!'; cr.

"Exit the image"
Smalltalk quit. 