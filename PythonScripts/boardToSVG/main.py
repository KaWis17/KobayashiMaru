import chess.svg

# Create a new board
board = chess.Board("r7/8/8/8/8/8/8/R1R5 w - - 0 1")

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