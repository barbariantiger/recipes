package nl.maikel.service;

import nl.maikel.exception.RecipeNotFoundException;
import nl.maikel.model.Recipe;

import java.util.List;

public interface RecipeService {
    Recipe createRecipe(Recipe recipe);

    Recipe readRecipe(String id) throws RecipeNotFoundException;

    Recipe updateRecipe(String id, Recipe recipe) throws RecipeNotFoundException;

    List<Recipe> listAllRecipes();

    void deleteRecipe(String id) throws RecipeNotFoundException;
}
