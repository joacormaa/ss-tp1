#Asume un archivo de entrada con el tiempo de salida de cada particula
import numpy as np
import matplotlib.pyplot as plt

ENERGY_PATHS = [
    '../data/0.15_70.0_1e-5_kineticEnergy.csv',
    '../data/0.183_70.0_1e-5_kineticEnergy.csv',
    '../data/0.216_70.0_1e-5_kineticEnergy.csv',
    '../data/0.25_70.0_1e-5_kineticEnergy.csv'
]
TIMES_PATHS = [
    '../data/0.15_70.0_1e-5_times.csv',
    '../data/0.183_70.0_1e-5_times.csv',
    '../data/0.216_70.0_1e-5_times.csv',
    '../data/0.25_70.0_1e-5_times.csv'
]
LABELS = [
    'D = 15cm',
    'D = 18cm',
    'D = 22cm',
    'D = 25cm'
]
# ENERGY_PATHS = [
#     '../data/0.0_7.0_1e-5_kineticEnergy.csv',
#     '../data/0.0_70.0_1e-5_kineticEnergy.csv',
#     '../data/0.0_90.0_1e-5_kineticEnergy.csv',
#     '../data/0.0_140.0_1e-5_kineticEnergy.csv',
#     '../data/0.0_700.0_1e-5_kineticEnergy.csv'
# ]
# TIMES_PATHS = [
#     '../data/0.0_7.0_1e-5_times.csv',
#     '../data/0.0_70.0_1e-5_times.csv',
#     '../data/0.0_90.0_1e-5_times.csv',
#     '../data/0.0_140.0_1e-5_times.csv',
#     '../data/0.0_700.0_1e-5_times.csv'
# ]
# LABELS = [
#     'Gamma = 7 kg/s',
#     'Gamma = 70 kg/s',
#     'Gamma = 90 kg/s',
#     'Gamma = 140 kg/s',
#     'Gamma = 700 kg/s'
# ]

total_measures = []
for i in range(len(ENERGY_PATHS)):
    times = np.genfromtxt(TIMES_PATHS[i])
    energy = np.genfromtxt(ENERGY_PATHS[i])
    times = times[1:-3]
    energy = energy[1:]
    ma = (energy[:-3] + energy[1:-2] + energy[2:-1] + energy[3:]) / 4
    # for j in range(1, energy.size):
    #     ma.append(ma[j-1] * 0.1 + energy[j] * 0.5)
    plt.plot(times, ma, "-o",markersize=3, label=LABELS[i])
    # for j in range(times.size):
    #     if ma[j] < 1e-7:
    #         print('tiempo reposo: {:.2E}'.format(times[j]))
    #         break

##plt.semilogy([0.1, 9.9], [1e-7, 1e-7], markersize=3)
plt.ylabel('Energía [J]')
plt.xlabel('Tiempo [s]')
plt.ylim(0, 25)
#plt.xlim(0, 3)
plt.legend(loc='upper right', bbox_to_anchor=(1, 1))
plt.show()
