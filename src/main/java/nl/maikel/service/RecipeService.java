package nl.maikel.service;

import nl.maikel.exception.RecipeNotFoundException;
import nl.maikel.model.Recipe;

import java.util.List;

public interface RecipeService {

    /**
     * Inserts a new recipe into the database
     *
     * @param recipe The new recipe to be inserted
     * @return The recipe just created
     */
    Recipe createRecipe(Recipe recipe);

    /**
     * Search a recipe by id
     *
     * @param id The id of the recipe to be search
     * @return The recipe, if it exists
     * @throws RecipeNotFoundException Thrown if the id is not found
     */
    Recipe readRecipe(String id) throws RecipeNotFoundException;

    /**
     * Updates a recipe, provided that the id exists
     *
     * @param id     The id of the recipe to be updated
     * @param recipe The recipe with the updated fields
     * @return The updated recipe
     * @throws RecipeNotFoundException Thrown if the id is not found
     */
    Recipe updateRecipe(String id, Recipe recipe) throws RecipeNotFoundException;

    /**
     * List all recipes in the system
     *
     * @return All Recipes
     */
    List<Recipe> listAllRecipes();

    void deleteRecipe(String id) throws RecipeNotFoundException;
}
