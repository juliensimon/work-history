//
// @(#)classes.h	3.2	12/15/92
//
// Les classes de la librairie C++ :
//
//	le type "classType" est un simple type enumere
//
//	seules les classes concretes sont mentionnees, les autres - non
//		instanciees - n'en ont pas besoin
//
///////////////////////////////////////////////////////////////////////////

typedef enum {

 ErrorClass,

// les classes derivees de "Collection"

 SetClass,
 OrderedCollectionClass,
 SortedCollectionClass,

// les classes derivees de "Magnitude"

 IntClass,
 FloatClass,

// les classes de l'application "files d'attente"

 ArriveeClientClass,
 ClientClass,
 EvenementClass,
 FileAttenteClass,
 LiberationServeurClass,
 RestaurantClass,
 ServeurClass,
 TempsClass,
 
 
 DummyClass
} classType;

// EOF
///////////////////////////////////////////////////////////////////////////










