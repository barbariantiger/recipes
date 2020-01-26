package nl.maikel.service;

import nl.maikel.exception.RecipeNotFoundException;
import nl.maikel.model.Recipe;
import nl.maikel.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository repository;

    public RecipeServiceImpl(RecipeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        recipe.setCreatedOn(LocalDateTime.now());
        return repository.save(recipe);
    }

    @Override
    public Recipe readRecipe(String id) throws RecipeNotFoundException {
        return repository.findById(id).orElseThrow(RecipeNotFoundException::new);
    }

    @Override
    public Recipe updateRecipe(String id, Recipe updatedRecipe) throws RecipeNotFoundException {
        Recipe actualRecipe = this.repository.findById(id)
                .orElseThrow(RecipeNotFoundException::new);
        actualRecipe.setVegetarian(updatedRecipe.isVegetarian());
        actualRecipe.setSuitableFor(updatedRecipe.getSuitableFor());
        actualRecipe.setInstructions(updatedRecipe.getInstructions());
        actualRecipe.setIngredients(updatedRecipe.getIngredients());
        return this.repository.save(actualRecipe);
    }
}
