
FIGS= netmsg.ps shared_memory.ps mapped_driver.ps library.ps port.ps portset.ps old_filter.ps new_filter.ps packet+lib.ps message.ps norma.ps untyped_message.ps

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


all:	net.ps  


net.dvi:	net.tex net.bib $(FIGS)

clean:
	rm -f *.aux *.dvi *.ps *.log *.bbl *.blg *.bib.bak.*

