from bokeh.models import BoxAnnotation
from bokeh.plotting import figure, output_file, show
import json

systemLength = 100
particleRadius = 1
particleRC = 1
frontierRadius = 2*particleRadius + particleRC
focusId = 0

with open('input2.json') as f:
    data = json.load(f)

myX = []
myY = []

focusX = data[focusId]['X']
focusY = data[focusId]['Y']
focusNeighborsX = []
focusNeighborsY = []

for item in data[focusId]['Neighbours']:
    focusNeighborsX.append(data[item]['X'])
    focusNeighborsY.append(data[item]['Y'])

for item in data:
    myX.append(item['X'])
    myY.append(item['Y'])

output_file('index.html')

p = figure(
    plot_width=700,
    plot_height=700
)
green_box = BoxAnnotation(bottom=0, top=100, left=0, right=100, fill_color='green', fill_alpha=0.1)
p.add_layout(green_box)

p.circle(myX, myY, size=particleRadius, color="navy", alpha=0.5)
r = p.circle([focusX],  [focusY], size=particleRadius, color="red", alpha=0.5)
glyph = r.glyph
glyph.size = frontierRadius
glyph.fill_alpha = 0.2
glyph.line_color = "firebrick"
glyph.line_dash = [6, 3]
glyph.line_width = 2

p.circle(focusNeighborsX, focusNeighborsY, size=10, color="black", alpha=0.5)

show(p)
