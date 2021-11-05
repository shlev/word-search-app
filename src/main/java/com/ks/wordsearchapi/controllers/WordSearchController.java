package com.ks.wordsearchapi.controllers;

import com.ks.wordsearchapi.services.WordGridService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wordGrid")
public class WordSearchController {

    @Autowired
    WordGridService wordGridService;

    @GetMapping
    public String createWordGrid(@RequestParam int gridSize, @RequestParam List<String> words) {
        char[][] grid = wordGridService.generateGrid(gridSize, words);
        String gridToString = "";
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                gridToString += grid[i][j] + " ";
            }
            gridToString+="\r\n";
        }
        return gridToString;
    }
}
