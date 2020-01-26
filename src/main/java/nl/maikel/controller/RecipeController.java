package nl.maikel.controller;

import nl.maikel.exception.RecipeNotFoundException;
import nl.maikel.model.Recipe;
import nl.maikel.service.RecipeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private RecipeServiceImpl service;

    public RecipeController(RecipeServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> createRecipe(@RequestBody Recipe recipe) {
        return new ResponseEntity<>(this.service.createRecipe(recipe), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readRecipe(@PathVariable("id") String id) throws RecipeNotFoundException {
        return new ResponseEntity<>(this.service.readRecipe(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable("id") String id, @RequestBody Recipe recipe)
            throws RecipeNotFoundException {
        return new ResponseEntity<>(this.service.updateRecipe(id, recipe), HttpStatus.OK);
    }
}
