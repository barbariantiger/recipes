package nl.maikel.service;

import nl.maikel.exception.RecipeNotFoundException;
import nl.maikel.model.Recipe;

public interface RecipeService {
    Recipe createRecipe(Recipe recipe);
    Recipe readRecipe(String id) throws RecipeNotFoundException;

    Recipe updateRecipe(String id, Recipe recipe) throws RecipeNotFoundException;
}
