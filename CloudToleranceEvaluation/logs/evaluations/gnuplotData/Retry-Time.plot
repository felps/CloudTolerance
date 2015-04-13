reset
fontsize = 12

#set terminal epslatex size 3.5,2.62 color colortext
#set output 'Retry-Time.tex'

#set term postscript enhanced eps fontsize
set terminal postscript eps size 3.5,2.62 enhanced color linewidth 2

set output 'Retry-Time.eps'

set title "Impact on Response Time due to Retry FTT Usage" 
set title  offset character 0, 0, 0 font "Helvetica, 16" norotate

set style fill solid 1.00 border 0
set style histogram errorbars gap 2 lw 1
set style data histogram

#set xtics rotate by -45
set grid ytics
set xlabel "WS Reliability"
set ylabel "Average Response Time (in ms)"
set yrange [0:90]

plot 'observedErrors-Time.data' using 2:3:xtic(1) t columnheader(2) linecolor rgb "#FF0000", "" using 10:11 t columnheader(10) lt 1 lc rgb "#00FF00"
