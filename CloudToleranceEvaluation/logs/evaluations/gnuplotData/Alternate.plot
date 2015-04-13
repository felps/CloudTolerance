#!/usr/bin/gnuplot -persist
#
#    
#    	G N U P L O T
#    	Version 4.6 patchlevel 0    last modified 2012-03-04 
#    	Build System: Linux x86_64
#    
#    	Copyright (C) 1986-1993, 1998, 2004, 2007-2012
#    	Thomas Williams, Colin Kelley and many others
#    
#    	gnuplot home:     http://www.gnuplot.info
#    	faq, bugs, etc:   type "help FAQ"
#    	immediate help:   type "help"  (plot window: hit 'h')

#set terminal epslatex size 3.5,2.62 color colortext
#set output 'Alternate.tex'
set terminal postscript eps size 3.5,2.62 enhanced color linewidth 2
set output 'Alternate.eps'

#unset clip points
#set clip one
#unset clip two
#set bar 1.000000 front
#set border 31 front linetype -1 linewidth 1.000
#set timefmt z "%d/%m/%y,%H:%M"
#set zdata 
#set timefmt y "%d/%m/%y,%H:%M"
#set ydata 
#set timefmt x "%d/%m/%y,%H:%M"
#set xdata 
#set timefmt cb "%d/%m/%y,%H:%M"
#set timefmt y2 "%d/%m/%y,%H:%M"
#set y2data 
#set timefmt x2 "%d/%m/%y,%H:%M"
#set x2data 
set boxwidth 0.9 relative
set style fill   solid 0.20 border lt -1
set style rectangle back fc lt -1 fillstyle   solid 1.00 border lt -1
#set style circle radius graph 0.02, first 0, 0 
#set style ellipse size graph 0.05, 0.03, first 0 angle 0 units xy
#set dummy x,y
#set format x "% g"
#set format y "% g"
#set format x2 "% g"
#set format y2 "% g"
#set format z "% g"
#set format cb "% g"
#set format r "% g"
#set angles radians
#unset grid
#set raxis
set key title "Test Cases"
#set key inside right top vertical Right noreverse enhanced autotitles nobox
set key noinvert samplen 4 spacing 1 width 0 height 0 
#set key maxcolumns 0 maxrows 0
#set key noopaque
unset label
unset arrow
#set style increment default
#unset style line
#unset style arrow
set style histogram clustered gap 1 title  offset character 0, 0, 0
unset logscale
set offsets 0, 0, 0, 0
set pointsize 1
set pointintervalbox 1
set encoding default
unset polar
unset parametric
unset decimalsign
set view 60, 30, 1, 1
set samples 100, 100
set isosamples 10, 10
set surface
unset contour
set clabel '%8.3g'
set mapping cartesian
set datafile separator whitespace
unset hidden3d
set cntrparam order 4
set cntrparam linear
set cntrparam levels auto 5
set cntrparam points 5
set size ratio 0 1,1
set origin 0,0
set style data histograms
#set style function lines
#set xzeroaxis linetype -2 linewidth 1.000
#set yzeroaxis linetype -2 linewidth 1.000
#set zzeroaxis linetype -2 linewidth 1.000
#set x2zeroaxis linetype -2 linewidth 1.000
#set y2zeroaxis linetype -2 linewidth 1.000
set ticslevel 0.5
set mxtics default
set mytics default
set mztics default
set mx2tics default
set my2tics default
set mcbtics default
set noxtics
unset xtics
set ytics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 autojustify
set ytics autofreq  norangelimit
set ztics border in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autojustify
set ztics autofreq  norangelimit
set nox2tics
set noy2tics
set cbtics border in scale 1,0.5 mirror norotate  offset character 0, 0, 0 autojustify
set cbtics autofreq  norangelimit
set rtics axis in scale 1,0.5 nomirror norotate  offset character 0, 0, 0 autojustify
set rtics autofreq  norangelimit
set title "Observed Composition Faults -\n Web Service Fail-Stop Failures" 
set title  offset character 0, 0, 0 font "Helvetica, 16" norotate
set timestamp bottom 
set timestamp "" 
set timestamp  offset character 0, 0, 0 font "" norotate
set rrange [ * : * ] noreverse nowriteback
set trange [ * : * ] noreverse nowriteback
set urange [ * : * ] noreverse nowriteback
set vrange [ * : * ] noreverse nowriteback
set xlabel "WS Reliability" 
set xlabel  offset character 0, 0, 0 font "" textcolor lt -1 norotate
set x2label "" 
set x2label  offset character 0, 0, 0 font "" textcolor lt -1 norotate
set xrange [ * : * ] noreverse nowriteback
set x2range [ 91.00000 : 100.0000 ] noreverse nowriteback
set ylabel "Observed errors" 
set ylabel  offset character 0, 0, 0 font "" textcolor lt -1 rotate by -270
set y2label "" 
set y2label  offset character 0, 0, 0 font "" textcolor lt -1 rotate by -270
set yrange [ -12.0000 : 130.000 ] noreverse nowriteback
set y2range [ -12.0000 : 108.000 ] noreverse nowriteback
set zlabel "" 
set zlabel  offset character 0, 0, 0 font "" textcolor lt -1 norotate
set zrange [ * : * ] noreverse nowriteback
set cblabel "" 
set cblabel  offset character 0, 0, 0 font "" textcolor lt -1 rotate by -270
set cbrange [ * : * ] noreverse nowriteback
set zero 1e-08
set lmargin  -1
set bmargin  -1
set rmargin  -1
set tmargin  -1
set locale "en_US.UTF-8"
set pm3d explicit at s
set pm3d scansautomatic
set pm3d interpolate 1,1 flush begin noftriangles nohidden3d corners2color mean
set palette positive nops_allcF maxcolors 0 gamma 1.5 color model RGB 
set palette rgbformulae 7, 5, 15
set colorbox default
set colorbox vertical origin screen 0.9, 0.2, 0 size screen 0.05, 0.6, 0 front bdefault
set style boxplot candles range  1.50 outliers pt 7 separation 1 labels auto unsorted
set loadpath 
set fontpath 
set psdir
set fit noerrorvariables
GNUTERM = "wxt"
COL = 4
POINT = 100
set xtics ('100' 0,'99' 1,'98' 2,'97' 3,'96' 4,'95' 5,'94' 6,'93' 7,'92' 8,'91' 9);
plot for [COL=2:7:5]  'observedErrors.data' using COL title columnheader(COL) \
#    EOF
