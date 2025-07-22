#
# Makefile pour compiler cette cochonnerie de these
#

CHAPS	=	biel.tex 

FIGS	=	masix2-us.ps

ARTICLE	=	biel.ps biel.pdf

.SUFFIXES:	.bib .dvi .fig .ps .tex

.dvi.ps:
	dvips -t a4 -D600 $* -o $*.ps

.fig.ps:
	fig2dev -Lps  $*.fig > $*.ps

.fig.tex:
	fig2dev -Llatex -l2  $*.fig > $*.tex

all:		$(ARTICLE)

biel.dvi:		$(CHAPS) $(FIGS) 
	latex biel
	bibtex biel
	latex biel
	latex biel

biel.pdf:		biel.dvi
	dvipdf biel.dvi biel.pdf

clean:
	# cleanfig
	rm -f *~ *.aux *.dvi *.lof *.log *.toc *.bbl *.blg *.ps *.pdf

