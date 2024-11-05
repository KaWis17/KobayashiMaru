import matplotlib.pyplot as plt

# Define match results for multiple engines
results = {
    '1': {'Wins': 3, 'Losses': 3, 'Draws': 3},
    '2': {'Wins': 3, 'Losses': 3, 'Draws': 3},
    '3': {'Wins': 3, 'Losses': 3, 'Draws': 3},
    '4': {'Wins': 3, 'Losses': 3, 'Draws': 3},
    '5': {'Wins': 3, 'Losses': 3, 'Draws': 3},
    '6': {'Wins': 3, 'Losses': 3, 'Draws': 3},
    '7': {'Wins': 3, 'Losses': 3, 'Draws': 3},
    '8': {'Wins': 3, 'Losses': 3, 'Draws': 3},
}

# Define different names for left and right sides
engine_names = {
    '1': {'left': 'AlfaBeta', 'right': 'Biblioteka'},
    '2': {'left': 'Tablice Figur', 'right': 'AlfaBeta'},
    '3': {'left': 'Stany Ciche', 'right': 'Tablice Figur'},
    '4': {'left': 'Ochrona Króla', 'right': 'Stany Ciche'},
    '5': {'left': 'Sortowanie ruchów', 'right': 'Ochrona Króla'},
    '6': {'left': 'Struktura pionów', 'right': 'Sortowanie ruchów'},
    '7': {'left': 'Transpozycje', 'right': 'Struktura pionów'},
    '8': {'left': 'Mobilność', 'right': 'Transpozycje'},
}

# Data for plotting
engines = list(results.keys())
wins = [results[engine]['Wins'] for engine in engines]
losses = [results[engine]['Losses'] for engine in engines]
draws = [results[engine]['Draws'] for engine in engines]
totals = [wins[i] + losses[i] + draws[i] for i in range(len(engines))]

# Calculate percentages
win_percentages = [wins[i] / totals[i] * 100 for i in range(len(engines))]
loss_percentages = [losses[i] / totals[i] * 100 for i in range(len(engines))]
draw_percentages = [draws[i] / totals[i] * 100 for i in range(len(engines))]

# Create horizontal bar chart
fig, ax = plt.subplots(figsize=(21, 9))  # Increase figure size

bar_width = 0.5
bar_positions = range(len(engines))

# Reverse the order of the bars
engines.reverse()
wins.reverse()
losses.reverse()
draws.reverse()
win_percentages.reverse()
loss_percentages.reverse()
draw_percentages.reverse()

# Plot bars with rounded edges and darker colors
bars_wins = ax.barh(bar_positions, wins, color='#006400', edgecolor='black', height=bar_width, label='Wins', linewidth=1, capstyle='round')
bars_losses = ax.barh(bar_positions, losses, left=wins, color='#8B0000', edgecolor='black', height=bar_width, label='Losses', linewidth=1, capstyle='round')
bars_draws = ax.barh(bar_positions, draws, left=[i+j for i,j in zip(wins, losses)], color='#00008B', edgecolor='black', height=bar_width, label='Draws', linewidth=1, capstyle='round')

# Add labels and title with larger font size
ax.set_yticks(bar_positions)
ax.set_yticklabels([engine_names[engine]['left'] for engine in engines], fontsize=12)
ax.set_xlabel('Number of Matches', fontsize=14)
ax.set_title('Wyniki meczów pomiędzy wersjami silnika', fontsize=20)

# Add different engine names on the left and right sides, adjusted to fit inside the image
for i, engine in enumerate(engines):
    ax.text(-0.5, i, engine_names[engine]['left'], va='center', ha='right', fontsize=12)
    ax.text(sum([wins[i], losses[i], draws[i]]) + 0.5, i, engine_names[engine]['right'], va='center', ha='left', fontsize=12)

# Display percentages on each part of the bar with larger and more visible font size
for i, (bar_win, bar_loss, bar_draw) in enumerate(zip(bars_wins, bars_losses, bars_draws)):
    ax.text(bar_win.get_width() / 2, bar_win.get_y() + bar_win.get_height() / 2, f'{win_percentages[i]:.1f}%', va='center', ha='center', color='white', fontsize=12, fontweight='bold')
    ax.text(bar_win.get_width() + bar_loss.get_width() / 2, bar_loss.get_y() + bar_loss.get_height() / 2, f'{loss_percentages[i]:.1f}%', va='center', ha='center', color='white', fontsize=12, fontweight='bold')
    ax.text(bar_win.get_width() + bar_loss.get_width() + bar_draw.get_width() / 2, bar_draw.get_y() + bar_draw.get_height() / 2, f'{draw_percentages[i]:.1f}%', va='center', ha='center', color='white', fontsize=12, fontweight='bold')

# Hide axes
ax.axis('off')

# Display the chart
plt.show()