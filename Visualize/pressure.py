import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

df = pd.read_csv('../output/0.021position.csv')
df.sort_values(by=['time'], inplace=True)

x = df['time']

param = 10
max_x =int(x[x.size-1])+1
agrupador = max_x/param

entry = []
entry.append(df['pressure0'])
entry.append(df['pressure1'])
entry.append(df['pressure2'])
entry.append(df['pressure3'])
entry.append(df['pressure4'])
entry.append(df['pressure5'])
entry.append(df['pressure6'])
entry.append(df['pressure7'])
entry.append(df['pressure8'])
entry.append(df['pressure9'])
entry.append(df['pressure10'])
entry.append(df['pressure11'])
entry.append(df['pressure12'])
entry.append(df['pressure13'])
entry.append(df['pressure14'])
entry.append(df['pressure15'])
entry.append(df['pressure16'])
entry.append(df['pressure17'])
entry.append(df['pressure18'])
entry.append(df['pressure19'])
entry.append(df['pressure20'])
entry.append(df['pressure21'])
entry.append(df['pressure22'])
entry.append(df['pressure23'])
entry.append(df['pressure24'])
entry.append(df['pressure25'])
entry.append(df['pressure26'])
entry.append(df['pressure27'])
entry.append(df['pressure28'])
entry.append(df['pressure29'])

val = []
for i in range(0, len(entry)):
    appendable = []
    for j in range(0, param):
        appendable.append(0)
    val.append(appendable)

for i in range(0, x.size):
    index = int(x[i]/agrupador)
    for j in range(0, len(entry)):
        val[j][index] += entry[j][i]


xpoints = []

for i in range(0,param):
    xpoints.append(i*agrupador)

y = []
yerr = []

for i in range(0, param):
    arr = val[:][i]
    y.append(np.mean(arr)/agrupador)
    yerr.append(np.std(arr)/agrupador)





fig = plt.figure()
plt.title('Presión vs Tiempo')
plt.xlabel('Tiempo (s)')
plt.ylabel('Presión (N/m)')
plt.errorbar(xpoints, y, lw=0.5, ms=0.5, marker='.', yerr=yerr,
            elinewidth=1, capsize=5, label='N = 20\nV0 = 0.021\n')
plt.legend(loc='upper right')
plt.xticks(np.arange(0, max_x, step=agrupador))
plt.grid(ls='--')
plt.show()