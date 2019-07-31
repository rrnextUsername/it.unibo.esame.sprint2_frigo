inventario( frigo , [(torta,cibo),(panuozzi,cibo),(cacca,cibo)] ).

showResourceModel :- 
	output("RESOURCE MODEL ---------- "),
	showResources,
	output("--------------------------").
		
showResources :- 
	inventario( F , C ),
 	output( inventario( F , C ) ),
	fail.
showResources.			

output( M ) :- stdout <- println( M ).
initResourceTheory :- output("resourceModel loaded").
:- initialization(initResourceTheory).