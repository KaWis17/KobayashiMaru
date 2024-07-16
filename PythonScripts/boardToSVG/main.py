import chess.svg

board = chess.Board(chess.STARTING_BOARD_FEN)
svg_text = chess.svg.board(board, size=900)

with open('boardToSVG/out/board.svg', 'w') as f:
    f.write(svg_text)
