import matplotlib.pyplot as plt

def create_bar_chart(player1_name, player2_name, wins_player1, wins_player2, draws, output_file):
    # Calculate totals and percentages
    total_games = wins_player1 + wins_player2 + draws
    win_percentage_player1 = (wins_player1 / total_games) * 100
    win_percentage_player2 = (wins_player2 / total_games) * 100
    draw_percentage = (draws / total_games) * 100

    # Create horizontal bar chart
    fig, ax = plt.subplots(figsize=(10, 2))
    bar_width = 0.05  # Adjusted bar width to make it thinner
    bar_positions = [0]

    # Plot bars
    bars_wins_player1 = ax.barh(bar_positions, [wins_player1], color='#006400', edgecolor='black', height=bar_width, label=f'Wygrane: {player1_name} ({win_percentage_player1:.1f}%)', linewidth=1, capstyle='round')
    bars_wins_player2 = ax.barh(bar_positions, [wins_player2], left=[wins_player1], color='#8B0000', edgecolor='black', height=bar_width, label=f'Wygrane: {player2_name} ({win_percentage_player2:.1f}%)', linewidth=1, capstyle='round')
    bars_draws = ax.barh(bar_positions, [draws], left=[wins_player1 + wins_player2], color='#00008B', edgecolor='black', height=bar_width, label=f'Remisy ({draw_percentage:.1f}%)', linewidth=1, capstyle='round')

    # Add labels and title
    ax.set_yticks(bar_positions)
    ax.set_yticklabels([player1_name], fontsize=12)
    ax.set_xlabel('Number of Games', fontsize=14)

    # Add player names on the left and right sides
    ax.text(-0.5, 0, player1_name, va='center', ha='right', fontsize=12)
    ax.text(total_games + 0.5, 0, player2_name, va='center', ha='left', fontsize=12)

    # Hide axes
    ax.axis('off')

    # Add legend
    ax.legend(loc='upper center', bbox_to_anchor=(0.5, -0.05), ncol=3, fontsize=12)

    # Adjust figure size to keep bar length constant
    fig.tight_layout(pad=2.0)

    # Save the chart as a PNG image
    plt.savefig(output_file, bbox_inches='tight')
    plt.close()

# Example usage
create_bar_chart('Transpozycje', 'Poprzednia wersja', 8, 6, 11, 'game_results3.png')