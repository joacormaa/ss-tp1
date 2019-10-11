#Asume un archivo de entrada con el tiempo de salida de cada particula
import numpy as np
import matplotlib.pyplot as plt
import scipy.stats as stats

PATHS = [
    '../data/0.15_70.0_1e-5_exitTimes.csv',
    '../data/0.183_70.0_1e-5_exitTimes.csv',
    '../data/0.216_70.0_1e-5_exitTimes.csv',
    '../data/0.25_70.0_1e-5_exitTimes.csv'
]
LABELS = [
    'D = 15cm',
    'D = 18cm',
    'D = 22cm',
    'D = 25cm'
]

NUMBER_OF_WINDOWS = 50
WINDOW_SIZE = 200

def get_sliding_window_measures(path):
    times = np.genfromtxt(path)
    total_windows = times.size - WINDOW_SIZE + 1
    if total_windows < NUMBER_OF_WINDOWS:
        raise ValueError('the amount of particles measured is smaller than the minimum required')

    sliding_window_means = np.zeros(total_windows)
    sliding_window_stds = np.zeros(total_windows)

    #convertir de tiempo acumulado a diferencias
    deltas = np.diff(times)

    #iterar el sliding window
    for i in range(0, total_windows):
        sliding_window_means[i] = 1/np.mean(deltas[i:i+WINDOW_SIZE-1])
        sliding_window_stds[i] = 1/np.std(deltas[i:i+WINDOW_SIZE-1])

    return times[WINDOW_SIZE-1:], sliding_window_means, sliding_window_stds


total_measures = []
for i in range(len(PATHS)):
    times, means, stds = get_sliding_window_measures(PATHS[i])
    m, b, r, _, _ = stats.linregress(times, means)
    plt.plot(times, means, 'o', markersize=1, label=LABELS[i])
    plt.plot([times[0], times[-1]], [m*times[0] + b, m*times[-1] + b])
    print('m: {:.2E} - b: {:.2E} - r {:.2E}'.format(m, b, r))
    print('mean: {:.2E} - std: {:.2E}'.format(np.mean(means), np.std(means)))

plt.ylabel('Caudal en partículas por segundo')
plt.xlabel('Tiempo [s]')
plt.ylim(300, 1200)
plt.legend(loc='upper right')
plt.show()
