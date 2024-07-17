import chess.svg

board = chess.Board(chess.STARTING_BOARD_FEN)
board.push_uci('e2e4')
svg_text = chess.svg.board(board, size=900)

print(board.fen())

with open('boardToSVG/out/board.svg', 'w') as f:
    f.write(svg_text)
