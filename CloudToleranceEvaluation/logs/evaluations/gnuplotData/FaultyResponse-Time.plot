reset
fontsize = 12

#set terminal epslatex size 3.5,2.62 color colortext
#set output 'observedErrors-Time.tex'

#set term postscript enhanced eps fontsize
set terminal postscript eps size 3.5,2.62 enhanced color linewidth 2

set output 'FaultyResponse-Time.eps'

set title "Impact on Response Time due to NVP FTT Usage" 
set title  offset character 0, 0, 0 font "Helvetica, 16" norotate

set style fill solid 1.00 border 0
set style histogram errorbars gap 2 lw 1
set style data histogram

#set xtics rotate by -45
set grid ytics
set xlabel "WS Reliability"
set ylabel "Average Response Time (in ms)"
set yrange [0:*]

plot 'observedErrors-Time.data' using 6:7:xtic(1) t columnheader(6) linecolor rgb "#FF0000", "" using 8:9 t columnheader(8) lt 1 lc rgb "#00FF00"
