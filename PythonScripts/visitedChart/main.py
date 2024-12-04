import matplotlib.pyplot as plt
import numpy as np

# Define the data
data = {
    "Start pos": [1, 2, 3, 4, 5, 6, 7, 8],
    "Wersja podstawowa": [20, 400, 8902, 197281, 4865609, 119060324, 3195901860, 84998978956],
    "Alfa-beta": [20, 186, 2262, 20596, 223840, 3349766, 20668442, 275274306],
    "Quiescence": [20, 194, 2279, 23119, 225836, 1606833, 19449096, 183000753],
    "Move ordering": [20, 214, 2360, 20428, 173183, 1019119, 7934005, 57778837],
    "Estimation": [20, 214, 2741, 23597, 199062, 1245427, 9078322, 70097202],
    "Transposition": [20, 214, 2360, 17481, 123575, 615267, 3923917, 23360242]
}

start_pos = data["Start pos"][3:]
values = {key: np.array(value[3:]) for key, value in data.items() if key != "Start pos"}

# Create a line chart
fig, ax = plt.subplots(figsize=(12, 8))

# Plot each dataset
for label, value in values.items():
    ax.plot(start_pos, value, marker='o', label=label)

# Set y-axis to logarithmic scale
ax.set_yscale('log')

# Add labels, title, and legend
ax.set_xlabel('Start pos')
ax.set_ylabel('Values')
ax.set_title('Liczba odwiedzonych wierzchołków dla pozycji startowej')
ax.legend()

# Save the chart as an image file
plt.savefig('chess_algorithms_comparison.png')
plt.show()