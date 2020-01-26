package nl.maikel;

import nl.maikel.exception.RecipeNotFoundException;
import nl.maikel.model.Recipe;
import nl.maikel.service.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@WebMvcTest
public class RecipeUnitTest {

    @MockBean
    private RecipeServiceImpl recipeService;
    private List<Recipe> recipes;

    @BeforeEach
    private void setup() throws RecipeNotFoundException {
        List<String> ingredients = Arrays.asList("Ingredient 1", "Ingredient 2", "Ingredient 3");
        recipes = IntStream.range(1, 4).mapToObj(i ->
                new Recipe(LocalDateTime.now(), i % 2 == 0,
                        (byte) i, ingredients, "Instruction " + i))
                .collect(toList());
        when(recipeService.listAllRecipes()).thenReturn(recipes);
        when(recipeService.readRecipe(any())).thenReturn(recipes.get(0));
    }

    @Test
    public void givenTwoEqualRecipesWhenEqualsThenTrue() throws RecipeNotFoundException {
        assertTrue(recipeService.readRecipe("fakeId").equals(recipes.get(0)));
    }

    @Test
    public void givenTwoDifferentRecipeWhenEqualsThenFalse() throws RecipeNotFoundException {
        assertTrue(recipeService.readRecipe("fakeId").equals(recipes.get(1)));
    }

    @Test
    public void givenTwoEqualRecipesWhenHashCodeThenTrue() throws RecipeNotFoundException {
        assertTrue(recipeService.readRecipe("fakeId").hashCode() == recipes.get(0).hashCode());
    }

    @Test
    public void givenTwoDifferentRecipesWhenHashCodeThenFalse() throws RecipeNotFoundException {
        assertTrue(recipeService.readRecipe("fakeId").hashCode() == recipes.get(1).hashCode());
    }
}
