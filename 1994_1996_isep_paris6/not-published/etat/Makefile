
FIGS= apsyscomp.ps apsyssimp.ps machus.ps sprite.ps structosf1ik.ps structosf1mk.ps

.SUFFIXES:    .bib .dvi .fig .ps .tex


.fig.ps:
	fig2dev -Lps $*.fig > $*.ps
	
.dvi.ps:
	dvips -D600 -t a4 $*.dvi -o $*.ps

.tex.dvi:
	latex $*.tex 
	bibtex $*
	latex $*.tex
	latex $*.tex

all:	etat.ps 

etat.dvi:	etat.tex etat.bib $(FIGS)

clean:
	rm -f *.aux *.dvi *.ps *.log *.bbl *.blg *.bib.bak.*

