import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

#p1 es el vecino
#P1 s=0.02282671001766542
#plt.scatter([0.35803729379571025], [0.10891005789500094], s=0.02282671001766542, marker='o', label='P1')
plt.plot([0.35803729379571025, 0.35803729379571025-1.0000558944844862], [0.10891005789500094, 0.10891005789500094-0.1970151555702459], lw=0.3, marker='.', label='P1')
#P2 , m
#plt.scatter([0.31472973419663275], [0.12792321214382832], s=0.02447230357007887, marker='o', label='P2')
plt.plot([0.31472973419663275, 0.31472973419663275+1.0000000000065512], [0.12792321214382832, 0.12792321214382832-0.642928998569048], lw=0.3, marker='.', label='P2')
#tan force
plt.plot([0.31472973419663275, 0.31472973419663275-0.05109749850717864], [0.12792321214382832, 0.12792321214382832-0.11638826114819385], lw=0.1, marker='.', label='Tan Force')
#tan versor
plt.plot([0.31472973419663275, 0.31472973419663275+0.4019914677747791], [0.12792321214382832, 0.12792321214382832+0.9156434130360349], lw=0.1, marker='.', label='Tan Vec')
#rel vel sobre p2
plt.plot([0.31472973419663275, 0.31472973419663275+2.0000558944910374], [0.12792321214382832, 0.12792321214382832-0.44591384299880205], lw=0.1, marker='.', label='Rel Vel')


print("Diferencia en vel X:", 1.0000000000065512+1.0000558944844862)
print("Diferencia en vel Y:", -0.642928998569048+0.1970151555702459)
plt.legend()
plt.show()