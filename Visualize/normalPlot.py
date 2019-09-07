import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

df = pd.read_csv('../output/metrics.csv')
df.sort_values(by=['time'], inplace=True)

x = df['time']
y = df['temperature']


fig = plt.figure()
plt.title('Temperatura vs Tiempo')
plt.xlabel('Tiempo (s)')
plt.ylabel('Temperatura (K)')

plt.plot(x, y, lw=0.5, ms=0.5, marker='o', label='N = 20\nV0 = 0.021\n')
plt.legend(loc='upper center')
plt.xticks(np.arange(0, 271, step=30))
plt.yticks(np.arange(1e19, 1.1e19, step=1.0e17))
plt.grid(ls='--')
plt.show()
