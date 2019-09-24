import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

dfa = pd.read_csv('../output/ANALYTICALParticle001.csv')
dfa.sort_values(by=['time'], inplace=True)

df1a = pd.read_csv('../output/VERLETParticle001.csv')
df1a.sort_values(by=['time'], inplace=True)

df2a = pd.read_csv('../output/BEEMANParticle001.csv')
df2a.sort_values(by=['time'], inplace=True)

df3a = pd.read_csv('../output/GPCO5Particle001.csv')
df3a.sort_values(by=['time'], inplace=True)

dfb = pd.read_csv('../output/ANALYTICALParticle0006.csv')
dfb.sort_values(by=['time'], inplace=True)

df1b = pd.read_csv('../output/VERLETParticle0006.csv')
df1b.sort_values(by=['time'], inplace=True)

df2b = pd.read_csv('../output/BEEMANParticle0006.csv')
df2b.sort_values(by=['time'], inplace=True)

df3b = pd.read_csv('../output/GPCO5Particle0006.csv')
df3b.sort_values(by=['time'], inplace=True)

dfc = pd.read_csv('../output/ANALYTICALParticle0001.csv')
dfc.sort_values(by=['time'], inplace=True)

df1c = pd.read_csv('../output/VERLETParticle0001.csv')
df1c.sort_values(by=['time'], inplace=True)

df2c = pd.read_csv('../output/BEEMANParticle0001.csv')
df2c.sort_values(by=['time'], inplace=True)

df3c = pd.read_csv('../output/GPCO5Particle0001.csv')
df3c.sort_values(by=['time'], inplace=True)

dfd = pd.read_csv('../output/ANALYTICALParticle00005.csv')
dfd.sort_values(by=['time'], inplace=True)

df1d = pd.read_csv('../output/VERLETParticle00005.csv')
df1d.sort_values(by=['time'], inplace=True)

df2d = pd.read_csv('../output/BEEMANParticle00005.csv')
df2d.sort_values(by=['time'], inplace=True)

df3d = pd.read_csv('../output/GPCO5Particle00005.csv')
df3d.sort_values(by=['time'], inplace=True)

dfe = pd.read_csv('../output/ANALYTICALParticle01.csv')
dfe.sort_values(by=['time'], inplace=True)

df1e = pd.read_csv('../output/VERLETParticle01.csv')
df1e.sort_values(by=['time'], inplace=True)

df2e = pd.read_csv('../output/BEEMANParticle01.csv')
df2e.sort_values(by=['time'], inplace=True)

df3e = pd.read_csv('../output/GPCO5Particle01.csv')
df3e.sort_values(by=['time'], inplace=True)

'''
time = 10
stepa = 0.001
stepb = 0.0006
stepc = 0.0001
stepd = 0.00005
stepe = 0.01
cuta = int(time/stepa)
cutb = int(time/stepb)
cutc = int(time/stepc)
cutd = int(time/stepd)
cute = int(time/stepe)
'''

x = [0.01, 0.001, 0.0006, 0.0001, 0.00005]

yVerlet = []
yBeeman = []
yGPCo5 = []
yVerletErr = []
yBeemanErr = []
yGPCo5Err = []
#ANALYTICAL
ya = dfa['position']
yb = dfb['position']
yc = dfc['position']
yd = dfd['position']
ye = dfe['position']

#VERLET
y1a = df1a['position']
y1a_e = abs(y1a.subtract(ya))
y1b = df1b['position']
y1b_e = abs(y1b.subtract(yb))
y1c = df1c['position']
y1c_e = abs(y1c.subtract(yc))
y1d = df1d['position']
y1d_e = abs(y1d.subtract(yd))
y1e = df1e['position']
y1e_e = abs(y1e.subtract(ye))

#BEEMAN
y2a = df2a['position']
y2a_e = abs(y2a.subtract(ya))
y2b = df2b['position']
y2b_e = abs(y2b.subtract(yb))
y2c = df2c['position']
y2c_e = abs(y2c.subtract(yc))
y2d = df2d['position']
y2d_e = abs(y2d.subtract(yd))
y2e = df2e['position']
y2e_e = abs(y2e.subtract(ye))

#GPCo5
y3a = df3a['position']
y3a_e = abs(y3a.subtract(ya))
y3b = df3b['position']
y3b_e = abs(y3b.subtract(yb))
y3c = df3c['position']
y3c_e = abs(y3c.subtract(yc))
y3d = df3d['position']
y3d_e = abs(y3d.subtract(yd))
y3e = df3e['position']
y3e_e = abs(y3e.subtract(ye))

#Errores Normalizados
yVerlet.append(np.mean(y1e_e))
yVerletErr.append(y1e_e.std())
yVerlet.append(np.mean(y1a_e))
yVerletErr.append(y1a_e.std())
yVerlet.append(np.mean(y1b_e))
yVerletErr.append(y1b_e.std())
yVerlet.append(np.mean(y1c_e))
yVerletErr.append(y1c_e.std())
yVerlet.append(np.mean(y1d_e))
yVerletErr.append(y1d_e.std())

yBeeman.append(np.mean(y2e_e))
yBeemanErr.append(y2e_e.std())
yBeeman.append(np.mean(y2a_e))
yBeemanErr.append(y2a_e.std())
yBeeman.append(np.mean(y2b_e))
yBeemanErr.append(y2b_e.std())
yBeeman.append(np.mean(y2c_e))
yBeemanErr.append(y2c_e.std())
yBeeman.append(np.mean(y2d_e))
yBeemanErr.append(y2d_e.std())

yGPCo5.append(np.mean(y3e_e))
yGPCo5Err.append(y3e_e.std())
yGPCo5.append(np.mean(y3a_e))
yGPCo5Err.append(y3a_e.std())
yGPCo5.append(np.mean(y3b_e))
yGPCo5Err.append(y3b_e.std())
yGPCo5.append(np.mean(y3c_e))
yGPCo5Err.append(y3c_e.std())
yGPCo5.append(np.mean(y3d_e))
yGPCo5Err.append(y3d_e.std())

fig = plt.figure()
plt.title('Oscilador')
plt.xlabel('dt')
plt.ylabel('Error (m)')

#con error
'''
plt.errorbar(x, yVerlet, yerr=yVerletErr, lw=0.5, ms=0.5, marker='.', c='red', elinewidth=1, capsize=5, label='VERLET')
plt.errorbar(x, yBeeman, yerr=yBeemanErr, lw=0.5, ms=0.5, marker='.', c='green', elinewidth=1, capsize=5, label='BEEMAN')
plt.errorbar(x, yGPCo5, yerr=yGPCo5Err, lw=0.5, ms=0.5, marker='.', c='purple', elinewidth=1, capsize=5, label='GPCo5')
'''
#sin error
'''
plt.plot(x, yVerlet, lw=0.5, ms=0.5, marker='.', c='red', label='VERLET')
plt.plot(x, yBeeman, lw=0.5, ms=0.5, marker='.', c='green', label='BEEMAN')
plt.plot(x, yGPCo5, lw=0.5, ms=0.5, marker='.', c='purple', label='GPCo5')
'''

plt.plot(df2c['time'], y2c_e, label='BEEMAN')

plt.legend(loc='upper right')
plt.yscale("log")
plt.xticks(x)
plt.grid(ls='--')

print(x)
print(yVerlet)
print(yBeeman)
print(yGPCo5)

plt.show()


