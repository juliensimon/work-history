

.SUFFIXES:    .bib .dvi .fig .ps .tex


.fig.ps:
	fig2dev -Lps $*.fig > $*.ps
	
.dvi.ps:
	dvips -t a4 -D600 $*.dvi -o $*.ps

.tex.dvi:
	latex $*.tex 
	bibtex $*
	latex $*.tex
	latex $*.tex


all:	athens.ps

pdf:	athens.ps
	ps2pdf athens.ps athens.pdf


athens.dvi:	athens.tex masix2-us.ps comm2.ps libs.ps generic-create.ps

clean:
	rm -f *.aux *.dvi *.ps *.log *.bbl *.blg *.pdf
