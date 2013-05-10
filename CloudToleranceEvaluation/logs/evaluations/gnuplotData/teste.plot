dx=5.
n=2
total_box_width_relative=0.75
gap_width_relative=0.1
d_width=(gap_width_relative+total_box_width_relative)*dx/2.
reset
set term png truecolor
set output "profit.png"
set xlabel "Year"
set ylabel "Profit(Million Dollars)"
set grid
set boxwidth total_box_width_relative/n relative
set style fill transparent solid 0.5 noborder
plot "observedErrors.data" u 1:2 w boxes lc rgb"green" notitle,\
     "observedErrors.data" u ($1+d_width):3 w boxes lc rgb"red" notitle
