//
	static char sccsId[] =

	"@(#)OrderedCollection.C	3.2	12/15/92";

///////////////////////////////////////////////////////////////////////////

#include "OrderedC.h"


///////////////////////////////////////////////////////////////////////////
//
// PROTECTED PART
//
///////////////////////////////////////////////////////////////////////////

// GROW : le vecteur grandit d'un facteur 1/3, plus 10 elements
//	Appelee uniquement lorsque _first == 0.
void OrderedCollection :: grow() {
	int siz = size();
	Object ** tmp = _vec;
	_maxSize = (_maxSize * 4 / 3) + 10;
	_vec = new Object* [_maxSize];
	for (int index = 0; index < siz; index++)
		_vec[index] = tmp[index];
	for (index = siz; index < _maxSize; index++)
		_vec[index] = NIL;
	delete tmp;
} // GROW


// PUTSPACEATSTART : reserve de la place en debut de vecteur
//	suppose que _first == 0 !
void OrderedCollection :: putSpaceAtStart() {
	int siz, end;
	if (_last == _maxSize - 1) grow();
	siz = size();
	end = (_maxSize + _last + 1) / 2; // on centre 
	for (int i = 0; i < siz; i++)
		_vec[end - i] = _vec[_last - i];
	_first = end - _last;
	_last = end;
	for (i = 0; i < _first; i++)
		_vec[i] = NIL;
} // PUTSPACEATSTART


// PUTSPACEATEND : reserve de la place apres le dernier element
//	Suppose que _last == _maxSize - 1
void OrderedCollection :: putSpaceAtEnd() {
	if (! _first) { grow(); return; }
	int siz = size();
	int start = _first / 2;
	for (int i = 0; i < siz; i++)
		_vec[start+i] = _vec[_first+i];
	_first = start;
	_last = _first + siz - 1;
	for (i = _last + 1; i < _maxSize; i++)
		_vec[i] = NIL;	
} // PUTSPACEATEND


// REMOVEINDEX : detache l'element situe a la position absolue indiquee
//	et decalle les suivants pour boucher le trou.
void OrderedCollection :: removeIndex(int index) {
	checkIndex("removeIndex",index - _first);
	for (int i = index; i <= _last; i++)
		_vec[i] = _vec[i+1];
	_vec[_last--] = NIL;
} // REMOVEINDEX


// PUTSPACEAFTER : alloue une place pour introduire un Objet apres la
//	position relative indiquee en argument.
void OrderedCollection :: putSpaceAfter(int index) {
	if (_last == _maxSize - 1) putSpaceAtEnd();
	if (index == -1) {
		if (! _first) putSpaceAtStart();
		_first--; 
	} else {
		_last++;
		for (int i = _last; i >  _first + index; i--)
			_vec[i] = _vec[i-1];
	}
} // PUTSPACEAFTER


///////////////////////////////////////////////////////////////////////////
//
// PUBLIC PART
// 
///////////////////////////////////////////////////////////////////////////


// PRINTON : une simple boucle
ostream& OrderedCollection :: printOn	(ostream& os) const {
	os << "( ";
	for (int i = _first; i <= _last; i++)
		os << _vec[i] << ' ';
	return os << ')';	
} // PRINTON


// INCLUDES : teste si l'element considere  est dans la collection
int OrderedCollection :: includes (const Object* o) const {
	for (int i = _first; i <= _last; i++)
		if (_vec[i]->isEqual(o)) return 1;
	return 0;
} // INCLUDES


// ADDLAST : ajoute l'Objet argument en fin de vecteur
Object* OrderedCollection :: addLast (const Object* o) {
	if (_last == _maxSize - 1) putSpaceAtEnd();
	_vec[++_last] = (Object*)o;
	_size++;
	return (Object*)o;
} // ADDLAST


// ADDFIRST : ajoute l'Objet considere en tete de vecteur
Object*	OrderedCollection :: addFirst	(const Object* o) {
	if (! _first) putSpaceAtStart();
	_size++;	
	return _vec[--_first] = (Object*)o;
} // ADDFIRST


// ADDBEFORE : ajoute le premier argument juste avant le second
//	dans le vecteur. Ajoute en fin de vecteur si le second
//	est inexistant.
Object* OrderedCollection :: addBefore 
		(const Object* toAdd, const Object* before) {
	for (int i = _first; i <= _last && ! _vec[i]->isEqual(before); i++);
	if (i == _last+1) return add(toAdd);
	int pos = i-_first;
	putSpaceAfter(pos-1);
	_size++;
	return _vec[pos+_first] = (Object*) toAdd;
} // ADDBEFORE


// ADDAFTER : ajoute le premier argument juste apres le second
//	dans le vecteur. 
Object* OrderedCollection :: addAfter 
		(const Object* toAdd, const Object* after) {
	for (int i = _first; i <= _last && ! _vec[i]->isEqual(after); i++);
	if (i == _last+1) return add(toAdd);
	putSpaceAfter(i-_first);
	_size++;
	return _vec[i+1] = (Object*) toAdd;
} // ADDAFTER


// REMOVE : detruit l'Objet indique
//	Pas de message s'il n'appartient pas au vecteur
Object* OrderedCollection :: remove (const Object* o) {
	for (int i = _first; i <= _last; i++)
		if (_vec[i]->isEqual(o)) removeIndex(i);
	_size--;
	return (Object*)o;
} // REMOVE


// REMOVEFIRST : retire le premier element du vecteur et le retourne
Object*	OrderedCollection :: removeFirst () {
	if (_first > _last) return error("removeFirst","empty collection",1);
	Object * res = _vec[_first];
	_size--;
	_vec[_first++] = NIL;
	return res;
} // REMOVEFIRST


// REMOVELAST : detruit le dernier element du vecteur
Object* OrderedCollection :: removeLast	() {
	if (_first > _last) return error("removeLast","empty collection",1);
	Object * res = _vec[_last];
	_size--;
	_vec[_last--] = NIL;
	return res;
} // REMOVELAST


// FOREACH : itere un traitement sur les elements du vecteur
Object* OrderedCollection :: forEach (funPtr f,  void* lPars) const {
	for (int i = _first; i <= _last; i++)
		(*f)(_vec[i], lPars);
	return (Object*)this;
} // FOREACH


// LES CONSTRUCTEURS
OrderedCollection :: OrderedCollection(int defSiz) : IndexedCollection() {
	_maxSize = defSiz;
	_first = 0;
	_last = -1;
	_vec = new Object * [_maxSize];
	for (int i = 0; i < _maxSize; i++) _vec[i] = NIL;
}


// AT : retourne l'Objet situe en Ieme position (relative)
Object*	OrderedCollection :: at	(int i) const { 
	checkIndex("at",i); 
	return _vec[ i + _first ]; 
} // AT

// ATPUT : place l'Objet indique en Ieme position dans la collection
//	(positions relatives)
Object*	OrderedCollection :: atPut (int i, const Object* o) {
	checkIndex("atPut",i); 
	return _vec[i + _first] = (Object*) o; 
} // ATPUT

// VIRTISEQUAL : renvoie 0 par defaut
int OrderedCollection :: virtIsEqual (const Object*) const { return 0; }
// VIRTISEQUAL

// ADD : ajoute en fin par defaut
Object* OrderedCollection :: add (const Object* o) { return addLast(o); }
// ADD


// EOF
///////////////////////////////////////////////////////////////////////////
