import matplotlib.pyplot as plt
import numpy as np

# Manually define the ELO range and corresponding percentage of games won
elo_range = np.array([1500, 1600, 1700, 1800, 1900, 2000])
percentage_won = np.array([100, 87.5, 83.3, 75, 50, 0])

# Create the plot
plt.figure(figsize=(10, 6))
plt.plot(elo_range, percentage_won, label='Win Percentage', marker='o')

# Add vertical lines from the X-axis up to each data point
for x, y in zip(elo_range, percentage_won):
    plt.vlines(x, ymin=0, ymax=y, colors='b', linestyles='dotted')

# Add a dashed line at 50%
plt.axhline(y=50, color='r', label='50% Line')

# Check if 50% is in the percentage_won array
if 50 in percentage_won:
    middle_x_point = elo_range[np.where(percentage_won == 50)[0][0]]
else:
    # Find the indices of the points just above and just below 50%
    above_50_index = np.where(percentage_won > 50)[0][-1] if np.any(percentage_won > 50) else None
    below_50_index = np.where(percentage_won < 50)[0][0] if np.any(percentage_won < 50) else None

    if above_50_index is not None and below_50_index is not None:
        elo_above_50 = elo_range[above_50_index]
        percentage_above_50 = percentage_won[above_50_index]
        elo_below_50 = elo_range[below_50_index]
        percentage_below_50 = percentage_won[below_50_index]

        # Calculate the middle x point using linear interpolation
        middle_x_point = elo_below_50 + (elo_above_50 - elo_below_50) * (50 - percentage_below_50) / (percentage_above_50 - percentage_below_50)

        print(f"The point just above 50% is at index {above_50_index} with ELO value {elo_above_50} and percentage {percentage_above_50}.")
        print(f"The point just below 50% is at index {below_50_index} with ELO value {elo_below_50} and percentage {percentage_below_50}.")
        print(f"The middle x point between values closest to 50% is: {middle_x_point}")
    else:
        print("No points found above or below 50%.")

# Add the middle point to the plot
plt.scatter(middle_x_point, 50, color='g', zorder=5, label='Middle Point (50%)')

# Add a vertical line from the X-axis to the middle point
plt.vlines(middle_x_point, ymin=0, ymax=50, colors='g', linestyles='dotted')

# Add a label near the middle point indicating its value
plt.text(middle_x_point, 58, f'{middle_x_point:.2f}', ha='center', color='g')

# Add labels and title
plt.xlabel('ELO')
plt.ylabel('Percentage of Games Won')
plt.title('Stockfish ELO vs Percentage of Games Won (8 games per ELO)')
plt.legend()

# Display the plot
plt.show()