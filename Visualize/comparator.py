import pandas as pd
import numpy as np
import matplotlib.pyplot as plt


derail_vals = ['1.0','1.5','2.0','2.5','3.0','3.5','4.0','4.5','5.0']
num_vals=range(0,25)


time_vs_derail = []
time_vs_derail_std = []

distance_vs_derail = []
distance_vs_derail_std = []

optimum_distance_vs_derail = []
optimum_distance_vs_derail_std = []

speed_vs_derail = []
speed_vs_derail_std = []


for derail in derail_vals:
    time_taken=[]
    distance=[]
    optimum_distance=[]
    speed=[]

    for num in num_vals:
        df = pd.read_csv('../output_'+derail+'/metrics_'+str(num)+'.csv')
        df.sort_values(by=['time'], inplace=True)

        time = df['time']
        dist = df['distance']
        s = df['speed']
        x = df['x']
        y = df['y']

        time_taken.append(time.iloc[-1])
        distance.append(np.sum(dist.iloc[:]))
        optimum_distance.append(np.hypot(x[0]-x.iloc[-1],y[0]-y.iloc[-1]))
        speed.append(np.mean(s))

    time_vs_derail.append(np.mean(time_taken))
    time_vs_derail_std.append(np.std(time_taken))

    distance_vs_derail.append(np.mean(distance))
    distance_vs_derail_std.append(np.std(distance))

    optimum_distance_vs_derail.append(np.mean(optimum_distance))
    optimum_distance_vs_derail_std.append(np.std(optimum_distance))

    speed_vs_derail.append(np.mean(speed))
    speed_vs_derail_std.append(np.std(speed))





fig = plt.figure()
plt.xlabel('D')
plt.ylabel('T')

#con error
plt.errorbar(derail_vals, time_vs_derail, yerr=time_vs_derail_std, lw=0.5, ms=4, marker='.', c='purple', elinewidth=0.5, capsize=5)
plt.show()

fig = plt.figure()

plt.ylabel('d')

#con error
plt.errorbar(derail_vals, distance_vs_derail, yerr=distance_vs_derail_std, lw=0.5, ms=4, marker='.', c='green', elinewidth=0.5, capsize=5)
plt.show()

fig = plt.figure()
plt.ylabel('Optimum Distance')

#con error
plt.errorbar(derail_vals, optimum_distance_vs_derail, yerr=optimum_distance_vs_derail_std, lw=0.5, ms=4, marker='.', c='blue', elinewidth=0.5, capsize=5)
plt.show()

fig = plt.figure()
plt.ylabel('v')

plt.errorbar(derail_vals, speed_vs_derail, yerr=speed_vs_derail_std, lw=0.5, ms=4, marker='.', c='red', elinewidth=0.5, capsize=5)
plt.show()

plt.ylabel('Error en T')

plt.plot(derail_vals, time_vs_derail_std, markersize=5, marker='.', linewidth=0.5, c='purple')
plt.show()

plt.ylabel('Error en d')

plt.plot(derail_vals, distance_vs_derail, markersize=5, marker='.', linewidth=0.5, c='green')
plt.show()

plt.ylabel('Error en v')

plt.plot(derail_vals, speed_vs_derail_std, markersize=5, marker='.', linewidth=0.5, c='red')
plt.show()