package org.example.Engine.Args;

public class Config {

    public static boolean DEBUG_ON = true;

    /* ---------- OPENING LIBRARY ---------- */
    public static boolean OPENING_LIBRARY_ON = false;
    public static String OPENING_LIBRARY_LOCATION = "/Users/kawis/Developer/KobayashiMaru/ChessEngine/resources/library.txt";

    /* ---------- ALPHA BETA PRUNING ---------- */
    public static boolean ALPHA_BETA_ON = true;

    /* ---------- QUIESCENCE SEARCH ---------- */
    public static boolean QUIESCENCE_SEARCH_ON = true;

    /* ---------- STATIC MOVE ORDERING ---------- */
    public static boolean STATIC_MOVE_ORDERING_ON = true;

    /* ---------- ZOBRIST HASHING ---------- */
    public static boolean ZOBRITS_HASHING_ON = true;

    /* ---------- TRANSPOSITION TABLE ---------- */
    public static boolean TRANSPOSITION_TABLE_ON = true;

    /* ---------- ESTIMATION WINDOW ---------- */
    public static boolean ESTIMATION_WINDOW = true;

    /* ---------- MOVE EXTENSIONS ---------- */
    public static boolean MOVE_EXTENSIONS_ON = true;

}
