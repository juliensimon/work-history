#
# Makefile pour compiler cette cochonnerie de these
#

CHAPS	=	article.tex

BIB	=	abbrev.bib amoeba.bib chorus.bib dos.bib mach.bib masix.bib \
		osf1.bib posix.bib reseaux.bib systemes.bib unix.bib v.bib

ARTICLE	=	article.ps
RAPPORT	=	rapport.ps

.SUFFIXES:	.bib .bbl .dvi .fig .ps .tex

.dvi.ps:
	dvips $* -o $*.ps

.fig.ps:
	fig2dev -Lps < $*.fig > $*.ps

.fig.tex:
	fig2dev -Llatex -l2 < $*.fig > $*.tex

.tex.bbl:
	latex $*
	bibtex $*

all:		$(ARTICLE) $(RAPPORT)

article.dvi:		$(CHAPS) $(FIGS) article.bbl
	latex article
	latex article
	latex article

rapport.dvi:		$(CHAPS) $(FIGS) rapport.bbl
	latex rapport
	latex rapport
	latex rapport

clean:
	# cleanfig
	rm -f *~ *.aux *.dvi *.lof *.log *.toc *.bbl *.blg *.ps

