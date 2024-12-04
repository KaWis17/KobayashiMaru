import chess.svg

# Create a new board
board = chess.Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")

# Make a move
# Convert square names to integer indices
tail = chess.parse_square('e2')
head = chess.parse_square('e4')

# Create an arrow using integer indices
arrow = chess.svg.Arrow(tail=tail, head=head)

# Generate SVG with the arrow
svg_text = chess.svg.board(board, size=900, arrows=[arrow])

# Print the board's FEN
print(board.fen())

# Write the SVG to a file
with open('boardToSVG/out/board.svg', 'w') as f:
    f.write(svg_text)

# import matplotlib.pyplot as plt
# import numpy as np
#
# # Define the whitePawns array
# whitePawns = [
#     -50,-40,-30,-30,-30,-30,-40,-50,
#     -40,-20,  0,  5,  5,  0,-20,-40,
#     -30,  5, 10, 15, 15, 10,  5,-30,
#     -30,  0, 15, 20, 20, 15,  0,-30,
#     -30,  5, 15, 20, 20, 15,  5,-30,
#     -30,  0, 10, 15, 15, 10,  0,-30,
#     -40,-20,  0,  0,  0,  0,-20,-40,
#     -50,-40,-30,-30,-30,-30,-40,-50,
# ]
#
# # Reshape the array to an 8x8 matrix
# whitePawns_matrix = np.array(whitePawns).reshape(8, 8)
#
# # Create a heatmap
# plt.figure(figsize=(8, 8))
# plt.imshow(whitePawns_matrix, cmap='coolwarm', interpolation='nearest', alpha=0.6)  # Set alpha for transparency
#
# # Add labels and title
# plt.title('Mapa cieplna czarnych skoczków')
# plt.colorbar(label='Value')
#
# # Display the heatmap
# plt.savefig("./boardToSVG/out/czarneSkoczki.png")