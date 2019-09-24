import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.metrics import mean_squared_error as mse


x = [0.01, 0.001, 0.0006, 0.0001, 0.00005]
methods = ['ANALYTICAL','VERLET','BEEMAN','GPCO5']

vals = [[0 for i in range(len(methods))] for j in range(len(x))]

dfe = pd.read_csv('../output/ANALYTICALParticle01.csv')
dfe.sort_values(by=['time'], inplace=True)
vals[0][0] = dfe['position']

df1e = pd.read_csv('../output/VERLETParticle01.csv')
df1e.sort_values(by=['time'], inplace=True)
vals[0][1] = df1e['position']

df2e = pd.read_csv('../output/BEEMANParticle01.csv')
df2e.sort_values(by=['time'], inplace=True)
vals[0][2] = df2e['position']

df3e = pd.read_csv('../output/GPCO5Particle01.csv')
df3e.sort_values(by=['time'], inplace=True)
vals[0][3] = df3e['position']

dfa = pd.read_csv('../output/ANALYTICALParticle001.csv')
dfa.sort_values(by=['time'], inplace=True)
vals[1][0] = dfa['position']

df1a = pd.read_csv('../output/VERLETParticle001.csv')
df1a.sort_values(by=['time'], inplace=True)
vals[1][1] = df1a['position']


df2a = pd.read_csv('../output/BEEMANParticle001.csv')
df2a.sort_values(by=['time'], inplace=True)
vals[1][2] = df2a['position']

df3a = pd.read_csv('../output/GPCO5Particle001.csv')
df3a.sort_values(by=['time'], inplace=True)
vals[1][3] = df3a['position']

dfb = pd.read_csv('../output/ANALYTICALParticle0006.csv')
dfb.sort_values(by=['time'], inplace=True)
vals[2][0] = dfb['position']

df1b = pd.read_csv('../output/VERLETParticle0006.csv')
df1b.sort_values(by=['time'], inplace=True)
vals[2][1] = df1b['position']

df2b = pd.read_csv('../output/BEEMANParticle0006.csv')
df2b.sort_values(by=['time'], inplace=True)
vals[2][2] = df2b['position']

df3b = pd.read_csv('../output/GPCO5Particle0006.csv')
df3b.sort_values(by=['time'], inplace=True)
vals[2][3] = df3b['position']

dfc = pd.read_csv('../output/ANALYTICALParticle0001.csv')
dfc.sort_values(by=['time'], inplace=True)
vals[3][0] = dfc['position']

df1c = pd.read_csv('../output/VERLETParticle0001.csv')
df1c.sort_values(by=['time'], inplace=True)
vals[3][1] = df1c['position']

df2c = pd.read_csv('../output/BEEMANParticle0001.csv')
df2c.sort_values(by=['time'], inplace=True)
vals[3][2] = df2c['position']

df3c = pd.read_csv('../output/GPCO5Particle0001.csv')
df3c.sort_values(by=['time'], inplace=True)
vals[3][3] = df3c['position']

dfd = pd.read_csv('../output/ANALYTICALParticle00005.csv')
dfd.sort_values(by=['time'], inplace=True)
vals[4][0] = dfd['position']

df1d = pd.read_csv('../output/VERLETParticle00005.csv')
df1d.sort_values(by=['time'], inplace=True)
vals[4][1] = df1d['position']

df2d = pd.read_csv('../output/BEEMANParticle00005.csv')
df2d.sort_values(by=['time'], inplace=True)
vals[4][2] = df2d['position']

df3d = pd.read_csv('../output/GPCO5Particle00005.csv')
df3d.sort_values(by=['time'], inplace=True)
vals[4][3] = df3d['position']

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


for i in range(len(x)):
    expected = vals[i][0]
    for j in range(len(methods)):
        vals[i][j] = vals[i][j].sub(expected)


yval = [[0 for i in range(len(methods))] for j in range(len(x))]
yerr = [[0 for i in range(len(methods))] for j in range(len(x))]

yval = np.array(yval)
yerr = np.array(yerr)

yval.dtype = 'float64'
yerr.dtype = 'float64'

#Errores Normalizados

for i in range(len(x)):
    for j in range(len(methods)):
        curr = vals[i][j]
        yval[i][j] = np.mean(np.power(curr, 2))
        yerr[i][j] = np.std(curr)

print(yval)
print(yerr)
fig = plt.figure()
plt.title('Oscilador')
plt.xlabel('dt')
plt.ylabel('Error (m)')

#con error
plt.errorbar(x, yval[:,0], yerr=yerr[:,0], lw=0.5, ms=0.5, marker='.', c='red', elinewidth=1, capsize=5, label=methods[0])
plt.errorbar(x, yval[:,1], yerr=yerr[:,1], lw=0.5, ms=0.5, marker='.', c='green', elinewidth=1, capsize=5, label=methods[1])
plt.errorbar(x, yval[:,2], yerr=yerr[:,2], lw=0.5, ms=0.5, marker='.', c='blue', elinewidth=1, capsize=5, label=methods[2])
plt.errorbar(x, yval[:,3], yerr=yerr[:,3], lw=0.5, ms=0.5, marker='.', c='purple', elinewidth=1, capsize=5, label=methods[3])

#sin error
#plt.plot(x, yVerlet, lw=0.5, ms=0.5, marker='.', c='red', label='VERLET')
#plt.plot(x, yBeeman, lw=0.5, ms=0.5, marker='.', c='green', label='BEEMAN')
#plt.plot(x, yGPCo5, lw=0.5, ms=0.5, marker='.', c='purple', label='GPCo5')

plt.legend(loc='upper right')
plt.yscale("log")
plt.xscale("log")
plt.xticks(x)
plt.grid(ls='--')

plt.show()


