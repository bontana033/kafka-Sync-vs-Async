import matplotlib.pyplot as plt
import numpy as np


x_axis = [0, 50000, 100000, 150000, 200000, 250000, 300000, 350000, 400000, 450000, 500000, 550000, 600000, 650000, 700000, 750000, 800000, 850000, 900000, 950000, 1000000]
sync = [0, 20214, 25551, 49910, 78199, 91980, 108205, 134473, 149268, 169372, 190329, 194262, 220392, 251763, 261064, 291141, 300554, 325421, 347399, 369120, 388382]
async2 = [0, 172, 65, 98, 119, 145, 213, 272, 240, 238, 281, 652, 343, 403, 422, 540, 440, 474, 558, 533, 548]

plt.xlabel("amount of transmissions", labelpad=15) 
plt.ylabel("turnaround time(ms)", labelpad=15) 
plt.title("Kafka Synchronous vs Asynchronous", fontsize=20, pad=30)


plt.plot(x_axis, sync, 'ro-', label='sync')
plt.plot(x_axis, async2, 'bo-', label='async')
current_values = plt.gca().get_yticks()
plt.gca().set_yticklabels(['{:,.0f}'.format(x) for x in current_values])
current_values = plt.gca().get_xticks()
plt.gca().set_xticklabels(['{:,.0f}'.format(x) for x in current_values])

for i, v in enumerate(x_axis):
    plt.text(v, sync[i], sync[i],                
             fontsize = 9, 
             color='black',
             horizontalalignment='right', 
             verticalalignment='bottom')  

for i, v in enumerate(x_axis):
    plt.text(v, async2[i], async2[i],                
             fontsize = 9, 
             color='black',
             horizontalalignment='right', 
             verticalalignment='bottom')  


plt.legend()
plt.tight_layout()
plt.show()

