import pandas as pd
import numpy as np
import matplotlib.pyplot as plt


paths = []


LABELS = [
    '10.0',
    '75.0',
    '140.0',
    '205.0',
    '270.0'
]

simulation_runs = 1

for i in range(len(LABELS)):
    sublist = []
    for j in range(simulation_runs):
        sublist.append('../data/0.0_'+LABELS[i] + '_' + str(j) + '_kineticEnergy.csv')
    paths.append(sublist)

for i in range(len(paths)):
    mean_vals = []
    std_vals = []
    df = []
    for j in range(len(paths[i])):
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

    n=5


    fig = plt.figure()
    ax = plt.axes()
    ax.set_yscale('log')
    plt.plot(x[::n], y[::n], yerr=y_err[::n], lw=0.5, ms=0.5, marker='.', c='blue', elinewidth=1, capsize=5)
    ax.set_xlabel('Tiempo [s]')
    ax.set_ylabel('Energía [J]')
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


fig = plt.figure()
ax = plt.axes()
ax.set_yscale('log')
plt.errorbar(x, y, yerr=y_err, lw=0.5, ms=0.5, marker='.', c='blue', elinewidth=1, capsize=5)
ax.set_xlabel('Gamma [s]')
ax.set_ylabel('Energía Residual [J]')
plt.show()