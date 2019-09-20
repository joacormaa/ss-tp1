import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

df = pd.read_csv('../output/BEEMANParticle.csv')
df.sort_values(by=['time'], inplace=True)

df1 = pd.read_csv('../output/VERLETParticle.csv')
df1.sort_values(by=['time'], inplace=True)

x = df['time'].iloc[0:100]
y = df['position'].iloc[0:100]

x1 = df1['time'].iloc[0:500]
y1 = df1['position'].iloc[0:500]

fig = plt.figure()
plt.title('Oscilador')
plt.xlabel('Tiempo (s)')
plt.ylabel('Posicion (m)')

plt.plot(x, y, lw=0.5, ms=0.5, marker='o', label='BEEMAN\n')
#plt.plot(x1, y1, lw=0.5, ms=0.5, marker='.', color='c', label='VERLET\n')
plt.legend(loc='upper right')
plt.xticks(np.arange(0, 10, step=0.5))
plt.grid(ls='--')
plt.show()