package com.ks.wordsearchapi.services;

import com.ks.wordsearchapi.enums.Direction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class WordGridService {

    public char[][] generateGrid(int gridSize, List<String> words) {

        List<Coordinate> coordinates = new ArrayList<>();

        char [][] contents = new char[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                coordinates.add(new Coordinate(i, j));
                contents[i][j] = '-';
            }
        }
        for (String word : words) {
            Collections.shuffle(coordinates);
            for (Coordinate coordinate : coordinates) {
                Direction selectedDirection = getDirectionForFit(contents, word, coordinate);
                if (selectedDirection != null) {
                    insertWord(contents, word, coordinate, selectedDirection);
                    break;
                }
            }
        }

        randomFileGrid(contents, gridSize);
        return contents;
    }

    public void displayGrid(char[][] contents, int gridSize) {

    }



    private void randomFileGrid(char[][] contents, int gridSize) {
        String allCapLetters = "ABSCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (contents[i][j] == '-') {
                    int randomIndex = ThreadLocalRandom.current().nextInt(0, allCapLetters.length());
                    contents[i][j] = allCapLetters.charAt(randomIndex);
                }
            }
            System.out.println();
        }
    }

    private Direction getDirectionForFit(char[][] contents, String word, Coordinate coordinate) {
        int gridSize = contents.length;
        List<Direction> directions = Arrays.asList(Direction.values());
        Collections.shuffle(directions);
        for (Direction direction : directions) {
            if (doesFit(contents, word, coordinate, direction))
                return direction;
        }
        return null;
    }

    private boolean doesFit(char[][] contents, String word, Coordinate coordinate, Direction direction) {
        int gridSize = contents.length;
        int wordLength = word.length();
        switch (direction) {
            case HORIZONTAL:
                if (coordinate.y + wordLength > gridSize)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x][coordinate.y + i] != '-')
                        return false;
                }
                break;
            case VERTICAL:
                if (coordinate.x + wordLength > gridSize)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x + i][coordinate.y] != '-')
                        return false;
                }
                break;
            case DIAGONAL:
                if (coordinate.y + wordLength > gridSize || coordinate.x + wordLength > gridSize)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x + i][coordinate.y + i] != '-')
                        return false;
                }
                break;
            case HORIZONTAL_INVERSE:
                if (coordinate.y - wordLength < 0)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x][coordinate.y - i] != '-')
                        return false;
                }
                break;
            case VERTICAL_INVERSE:
                if (coordinate.x - wordLength < 0)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x - i][coordinate.y] != '-')
                        return false;
                }
                break;
            case DIAGONAL_INVERSE:
                if (coordinate.y - wordLength < 0 || coordinate.x - wordLength < 0)
                    return false;
                for (int i = 0; i < wordLength; i++) {
                    if (contents[coordinate.x - i][coordinate.y - i] != '-')
                        return false;
                }
                break;
        }
        return true;
    }

    private void insertWord(char[][] contents, String word, Coordinate coordinate, Direction direction) {
        int x = coordinate.x;
        int y = coordinate.y;
        switch (direction) {
            case HORIZONTAL:
                for (char c : word.toCharArray()) {
                    contents[x][y++] = c;
                }
                break;
            case VERTICAL:
                for (char c : word.toCharArray()) {
                    contents[x++][y] = c;
                }
                break;
            case DIAGONAL:
                for (char c : word.toCharArray()) {
                    contents[x++][y++] = c;
                }
                break;
            case HORIZONTAL_INVERSE:
                for (char c : word.toCharArray()) {
                    contents[x][y--] = c;
                }
                break;
            case VERTICAL_INVERSE:
                for (char c : word.toCharArray()) {
                    contents[x--][y] = c;
                }
                break;
            case DIAGONAL_INVERSE:
                for (char c : word.toCharArray()) {
                    contents[x--][y--] = c;
                }
                break;
        }
    }

    private class Coordinate {
        int x;
        int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}


