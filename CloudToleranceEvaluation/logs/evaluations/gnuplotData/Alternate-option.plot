reset
fontsize = 12

#set terminal epslatex size 3.5,2.62 color colortext
#set output 'Alternate.tex'

#set term postscript enhanced eps fontsize
set terminal postscript eps size 3.5,2.62 enhanced color linewidth 2

set output 'Alternate.eps'

set title "Observed Composition Faults -\n Web Service Fail-Stop Failures" 
set title  offset character 0, 0, 0 font "Helvetica, 16" norotate

set style fill solid 1.00 border 0
set style histogram errorbars gap 2 lw 1
set style data histogram

#set xtics rotate by -45
set grid ytics
set xlabel "WS Reliability"
set ylabel "Observed Errors"
set yrange [0:120]

#plot for [COL=2:6:5]  'observedErrors.data' using COL title columnheader(COL) 

plot 'observedErrors.data' using 2:7:xtic(1) t columnheader(2) linecolor rgb "#FF0000", "" using 7:7 t columnheader(7) lt 1 lc rgb "#00FF00"
