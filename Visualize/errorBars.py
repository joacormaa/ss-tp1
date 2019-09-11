import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

df = pd.read_csv('../output/balance.csv')
df.sort_values(by=['Temp'], inplace=True)

x = df['Temp']
y = []
yerr = []

for i in range(0, x.size):
    aux = df.iloc[i, 1:200]
    mean = np.mean(aux)
    mse = aux.std()
    y.append(mean)
    yerr.append(mse)

fig = plt.figure()
plt.title('Temperatura vs Presión')
plt.xlabel('Temperatura (K)')
plt.ylabel('Presión (N/m)')
plt.errorbar(x, y, lw=0.5, ms=0.5, marker='.', yerr=yerr,
             elinewidth=1, capsize=5, label='N = 30')
plt.legend(loc='lower right')
plt.xticks(np.arange(0, 2.5e20, step=6e19))
plt.grid(ls='--')
plt.show()

