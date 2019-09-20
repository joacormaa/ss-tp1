import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

df = pd.read_csv('../output/BEEMANParticle.csv')
df.sort_values(by=['time'], inplace=True)

df1 = pd.read_csv('../output/VERLETParticle.csv')
df1.sort_values(by=['time'], inplace=True)

df2 = pd.read_csv('../output/ANALYTICALParticle.csv')
df2.sort_values(by=['time'], inplace=True)

df3 = pd.read_csv('../output/GPCO5Particle.csv')
df3.sort_values(by=['time'], inplace=True)

time = 10
step = 0.0001
cut = int(time/step)


x = df['time'].iloc[0:cut]
y = df['position'].iloc[0:cut]

x1 = df1['time'].iloc[0:cut]
y1 = df1['position'].iloc[0:cut]

x2 = df2['time'].iloc[0:cut]
y2 = df2['position'].iloc[0:cut]

x3 = df3['time'].iloc[0:cut]
y3 = df3['position'].iloc[0:cut]

fig = plt.figure()
plt.title('Oscilador')
plt.xlabel('Tiempo (s)')
plt.ylabel('Posicion (m)')

plt.plot(x, y, lw=0.5, ms=0.5, marker='o', label='BEEMAN\n')
plt.plot(x1, y1, lw=0.5, ms=0.5, marker='.', color='red', label='VERLET\n')
plt.plot(x2, y2, lw=0.5, ms=0.5, marker='.', color='green', label='ANALYTICAL\n')
plt.plot(x3, y3, lw=0.5, ms=0.5, marker='.', color='purple', label='GPCO5\n')
plt.legend(loc='upper right')
plt.xticks(np.arange(0, time, step=0.5))
plt.grid(ls='--')
plt.show()