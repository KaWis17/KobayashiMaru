import chess.pgn
import io


# Example usage
pgn_string = """


[Event "My Tournament"]
[Site "?"]
[Date "2024.09.29"]
[Round "1"]
[White "KobayashiMaruBookQuies"]
[Black "stockfish.sh"]
[Result "0-1"]
[ECO "A00"]
[GameDuration "00:08:28"]
[GameEndTime "2024-09-29T01:56:15.337 GMT+2"]
[GameStartTime "2024-09-29T01:47:47.089 GMT+2"]
[Opening "Dunst (Sleipner-Heinrichsen) Opening"]
[PlyCount "102"]
[Termination "stalled connection"]
[TimeControl "5/move"]

1. Nc3 {0.00/4 4.8s} e5 {+0.05/22 5.0s} 2. Nf3 {+0.20/4 4.8s} d6 {-0.32/21 5.0s}
3. d4 {+0.80/4 4.8s} Nd7 {-0.57/23 5.0s} 4. e4 {+0.70/4 4.8s}
Ngf6 {-0.56/23 5.0s} 5. Bd3 {+0.50/3 4.8s} Be7 {-0.45/22 5.0s}
6. O-O {+0.70/4 4.8s} c6 {-0.43/21 5.0s} 7. dxe5 {+2.10/3 4.8s}
Nxe5 {-0.18/22 5.0s} 8. Nxe5 {+1.80/3 4.8s} dxe5 {-0.10/23 5.0s}
9. Bg5 {+1.60/4 4.8s} h6 {+0.06/21 5.0s} 10. Bxf6 {+1.90/4 4.8s}
gxf6 {+0.33/23 5.0s} 11. Qh5 {+2.40/4 4.8s} Be6 {-0.33/21 5.0s}
12. Rfd1 {+2.00/3 4.8s} Qb6 {+0.09/22 5.0s} 13. Rdb1 {+2.00/3 4.8s}
Rg8 {+0.50/20 5.0s} 14. Qxh6 {+1.90/3 4.8s} Kd7 {-0.02/20 5.0s}
15. a3 {+2.90/3 4.8s} Rh8 {+0.13/20 5.0s} 16. Qe3 {+2.90/4 4.8s}
Qxe3 {-0.17/20 5.0s} 17. fxe3 {-6.00/5 4.8s} Rh5 {-0.12/20 5.0s}
18. Be2 {+3.00/4 4.8s} Rh4 {-0.06/21 5.0s} 19. g3 {+2.80/4 4.8s}
Rg8 {-0.09/22 5.0s} 20. Bf3 {+2.50/4 4.8s} Rhh8 {+0.42/21 5.0s}
21. b4 {+2.60/4 4.8s} f5 {+0.33/21 5.0s} 22. exf5 {+2.20/5 4.8s}
Bc4 {+0.24/23 5.0s} 23. e4 {+3.10/4 4.8s} Bd8 {-2.21/22 5.0s}
24. Rd1+ {+3.70/4 4.8s} Kc8 {-2.66/23 5.0s} 25. Na4 {+3.60/4 4.8s}
Bb5 {-2.62/22 5.0s} 26. Nc5 {+2.90/4 4.8s} Bg5 {-2.73/23 5.0s}
27. Re1 {+3.60/4 4.8s} Bd2 {-1.98/22 5.0s} 28. Red1 {+3.60/4 4.8s}
Bc3 {-1.99/22 5.0s} 29. Rab1 {+3.50/4 4.8s} Rh7 {-2.01/22 5.0s}
30. a4 {+3.60/4 4.8s} Bc4 {-2.02/23 5.0s} 31. Kg2 {+3.40/4 4.8s}
f6 {-1.00/22 5.0s} 32. a5 {+3.20/4 4.8s} b6 {-0.77/23 5.0s}
33. Na4 {+3.60/4 4.8s} Rgh8 {-3.85/24 5.0s} 34. Nxc3 {+2.70/4 4.8s}
Rxh2+ {-3.41/24 5.0s} 35. Kg1 {+5.30/6 4.8s} Kb7 {-3.45/23 5.0s}
36. Rd7+ {+5.70/5 4.8s} Kc8 {-5.14/23 5.0s} 37. Rxa7 {+6.00/5 4.8s}
R8h6 {-5.37/24 5.0s} 38. Rb2 {+7.10/5 4.8s} Rd2 {-5.59/24 5.0s}
39. axb6 {+7.00/5 4.8s} Rhh2 {-5.63/24 5.0s} 40. Rc7+ {+7.80/5 4.8s}
Kb8 {-5.33/27 5.0s} 41. Rxc6 {+7.10/5 4.8s} Bd3 {-4.61/25 5.0s}
42. Rxf6 {+7.60/5 4.8s} Rhf2 {-5.22/24 5.0s} 43. Rf8+ {+8.30/5 4.8s}
Kb7 {-8.23/33 5.0s} 44. Rf7+ {+8.90/5 4.8s} Kxb6 {-7.39/26 5.0s}
45. Na4+ {+8.70/5 4.8s} Ka6 {-8.55/25 5.0s} 46. Nc5+ {+7.70/6 4.8s}
Kb5 {-8.51/28 5.0s} 47. Nxd3 {+9.00/6 4.8s} Rxf3 {-9.58/25 5.0s}
48. c4+ {+9.20/6 4.8s} Ka6 {-11.57/32 5.0s} 49. Nc5+ {+8.60/7 4.8s}
Kb6 {-M6/245 0.015s} 50. Na4+ {+8.80/5 4.8s} Ka6 {-M4/245 0.007s}
51. Nc5+ {+7.70/1 4.8s} Kb6 {-M6/245 0.010s, White's connection stalls} 0-1



"""


def pgn_to_lan():
    # Read the PGN string
    pgn_io = io.StringIO(pgn_string)
    # Read the first game in the PGN string
    game = chess.pgn.read_game(pgn_io)

    # List to store moves in LAN
    lan_moves = []

    # Iterate through all moves in the game
    for move in game.mainline_moves():
        lan_moves.append(move.uci())

    print(len(lan_moves))
    return ' '.join(lan_moves)


if __name__ == '__main__':
    print("position startpos moves " + pgn_to_lan())

