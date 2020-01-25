package nl.maikel.service;

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
}
