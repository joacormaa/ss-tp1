import pandas as pd
import numpy as np
import matplotlib.pyplot as plt


paths = [];


LABELS = [
    '0.15',
    '0.175',
    '0.2',
    '0.225',
    '0.25'
]

simulation_runs = 5

for i in range(len(LABELS)):
    sublist = []
    for j in range(simulation_runs):
        sublist.append('../data/'+LABELS[i] + '_' + str(j) + '_kineticEnergy.csv')
    paths.append(sublist)

for i in range(len(paths)):
    mean_vals = []
    std_vals = []
    df = []
    for j in range(len(paths[i])):
        if j != 1 | i != 0:
            csv = pd.read_csv(paths[i][j])
            csv.sort_values(by=['times'], inplace=True)
            df.append(csv['ke'])
    df = np.array(df)
    for j in range(len(df[i])):
        mean_vals.append(np.mean(df[:,j]))
        std_vals.append(np.std(df[:,j]))

    x = pd.read_csv(paths[i][0])
    x.sort_values(by=['times'], inplace=True)
    x = x['times']
    y = mean_vals
    y_err = std_vals

    n=3

    plt.errorbar(x[::n], y[::n], yerr=y_err[::n], lw=0.5, ms=0.5, marker='.', c='blue', elinewidth=1, capsize=5, label=LABELS[i]+'m')
    plt.ylabel('Energía [J]')
    plt.xlabel('Tiempo [s]')
    plt.ylim(0, 5)
    plt.xlim(0, 8)
    plt.legend(loc='upper right', bbox_to_anchor=(1, 1))
    plt.show()



mean_vals = []
std_vals = []
for i in range(len(paths)):
    df =[]
    for j in range(len(paths[i])):
        if j != 1 | i != 0:
            csv = pd.read_csv(paths[i][j])
            csv.sort_values(by=['times'], inplace=True)
            df.append(csv[csv['times'] > 2]['ke'])
    df = np.array(df)
    mean_vals.append(np.mean(df))
    std_vals.append(np.std(df))

x = LABELS
y = mean_vals
y_err = std_vals


plt.errorbar(x, y, yerr=y_err, lw=0.5, ms=0.5, marker='.', c='blue', elinewidth=1, capsize=5)
plt.ylabel('EK (equilibrio) [J]')
plt.xlabel('Ancho de abertura [m]')
plt.ylim(0, 3)
plt.show()