reset
fontsize = 12

#set terminal epslatex size 3.5,2.62 color colortext
#set output 'ProxyScalability.tex'

#set term postscript enhanced eps fontsize
set terminal postscript eps size 3.5,2.62 enhanced color linewidth 2

set output 'ProxyScalability.eps'

set title "Response Time x Proxies Amount" 
set title  offset character 0, 0, 0 font "Helvetica, 16" norotate

set style fill transparent solid 0.5 border 0
#set style histogram errorbars gap 2 lw 1
#set style data histogram

set boxwidth 0.5

set grid ytics
set xlabel "Proxy Amount"
set xrange [1.5:08.75]

set ylabel "Average Response Time (in ms)"
set yrange [0:*]


plot 'ProxyScalability.data' using 1:2:3:xtic(1) t "Avg. Response Time" linecolor rgb "#FF0000" w boxerrorbars
