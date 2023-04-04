package app.controller;

import app.model.Board;
import app.model.Library;

import java.io.Serializable;

public class GameStatus implements Serializable {
    public Board board;
    public Library library;
    public GameStatus(Board b, Library l){board = b; library = l;}
}
