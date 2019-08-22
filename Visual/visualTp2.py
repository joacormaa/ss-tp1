import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.metrics import mean_squared_error

df = pd.read_csv('../output/noise.csv')
df.sort_values(by=['noise'], inplace=True)

x = df['noise']
y = []
yerr = []

for i in range(0, x.size):
    aux = df.iloc[i, 1:11]
    mean = np.mean(aux)
    #mean_array = np.full(10, mean)
    mse = aux.std()
    y.append(mean)
    yerr.append(mse)

fig = plt.figure()
plt.title('Prueba 1')
plt.xlabel('Noise')
plt.ylabel('Orden')
plt.errorbar(x, y, lw=0.5, ms=0.5, marker='.', yerr=yerr,
             elinewidth=1, capsize=5, label='uplims=True, lolims=True')
plt.legend(loc='lower right')
plt.xticks(np.arange(0, 5.5, step=0.5))
plt.grid(ls='--')
plt.show()

