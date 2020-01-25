package nl.maikel.controller;

import nl.maikel.model.Recipe;
import nl.maikel.service.RecipeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
