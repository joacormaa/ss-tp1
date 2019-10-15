import pandas as pd
import numpy as np
import matplotlib.pyplot as plt


paths = []


LABELS = [
    '10.0',
    '75.0',
    '140.0',
    '205.0',
    '270.0',
    '500.0',
    '700.0'
]

COLORS = [
    'blue',
    'red',
    'green',
    'purple',
    'black',
    'teal',
    'pink'
]

simulation_runs = 1

for i in range(len(LABELS)):
    sublist = []
    for j in range(simulation_runs):
        sublist.append('../data/0.0_'+LABELS[i] + '_' + str(j) + '_kineticEnergy.csv')
    paths.append(sublist)

for i in range(len(paths)):

    csv = pd.read_csv(paths[i][0])
    csv.sort_values(by=['times'], inplace=True)

    x = csv['times'].iloc[1:]
    y = csv['ke'].iloc[1:]
    n=5
    plt.plot(x[::n], y[::n], lw=1.0, ms=5.0, marker='.', c=COLORS[i], label='y = '+LABELS[i])

ax = plt.axes()
ax.set_yscale('log')
ax.set_xlabel('Tiempo [s]')
ax.set_ylabel('KE [J]')
plt.legend(loc='lower left')
plt.show()