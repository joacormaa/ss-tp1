import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

df = pd.read_csv('../output/0.0position.csv')
df.sort_values(by=['time'], inplace=True)

x = df['time']
y = []
yerr = []

for i in range(0, x.size):
    aux = df.iloc[i, 1:11]
    mean = np.mean(aux)
    mse = aux.std()
    y.append(mean)
    yerr.append(mse)

fig = plt.figure()
plt.title('Prueba 1')
plt.xlabel('time')
plt.ylabel('Orden')
plt.errorbar(x, y, lw=0.5, ms=0.5, marker='.', yerr=yerr,
             elinewidth=1, capsize=5, label='uplims=True, lolims=True')
plt.legend(loc='lower right')
plt.xticks(np.arange(0, 500, step=40))
plt.grid(ls='--')
plt.show()

