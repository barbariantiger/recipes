package nl.maikel;

import nl.maikel.model.Recipe;
import nl.maikel.repository.RecipeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecipesBookApplicationTests {

	private MockMvc mockMvc;
	private RecipeRepository repository;
	private List<Recipe> recipes;

	@Autowired
	private RecipesBookApplicationTests(MockMvc mockMvc, RecipeRepository repository) {
		this.mockMvc = mockMvc;
		this.repository = repository;
	}

	@BeforeEach
	public void setup() {
		List<String> ingredients = Arrays.asList("Ingredient 1", "Ingredient 2", "Ingredient 3");
		recipes = IntStream.range(1, 4).mapToObj(i ->
				new Recipe(LocalDateTime.now(), i % 2 == 0,
						(byte) i, ingredients, "Instruction " + i))
				.collect(toList());
		repository.saveAll(recipes);
	}

	@AfterEach
	public void tearDown() {
		repository.deleteAll();
	}

	@Test
	public void whenCreateRecipeThenRecipe() throws Exception {
		String content = "{ \"suitableFor\": 2, \"vegetarian\": true, \"ingredients\": [ \"1kg of something\" ], " +
				"\"instructions\": \"Mix this with that\" }";
		this.mockMvc.perform(post("/recipes")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.version", is(0)))
				.andExpect(jsonPath("$.suitableFor", is(2)))
				.andExpect(jsonPath("$.vegetarian", is(true)))
				.andExpect(jsonPath("$.ingredients[0]", is("1kg of something")))
				.andExpect(jsonPath("$.instructions", is("Mix this with that")));
	}

	@Test
	public void givenZeroSuitableForWhenCreateRecipeThenException() throws Exception {
		String content = "{ \"suitableFor\": 0, \"vegetarian\": true, \"ingredients\": [ \"1kg of something\" ], " +
				"\"instructions\": \"Mix this with that\" }";
		this.mockMvc.perform(post("/recipes")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenNullVegetarianWhenCreateRecipeThenException() throws Exception {
		String content = "{ \"suitableFor\": 1, \"ingredients\": [ \"1kg of something\" ], " +
				"\"instructions\": \"Mix this with that\" }";
		this.mockMvc.perform(post("/recipes")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenNullIngredientsWhenCreateRecipeThenException() throws Exception {
		String content = "{ \"suitableFor\": 1, \"vegetarian\": true , " +
				"\"instructions\": \"Mix this with that\" }";
		this.mockMvc.perform(post("/recipes")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenBlankInstructionsWhenCreateRecipeThenException() throws Exception {
		String content = "{ \"suitableFor\": 2, \"vegetarian\": true, \"ingredients\": [ \"1kg of something\" ], " +
				"\"instructions\": \"\" }";
		this.mockMvc.perform(post("/recipes")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void givenRecipeExistsWhenReadRecipeThenRecipe() throws Exception {
		this.mockMvc.perform(get("/recipes/{id}", this.recipes.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	public void givenUnexistentRecipeWhenReadRecipeThenException() throws Exception {
		this.mockMvc.perform(get("/recipes/{id}", "unexistentId")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	public void givenExistingRecipeWhenUpdateRecipeThenRecipe() throws Exception {
		String updatedContent = "{ \"suitableFor\": 2, \"vegetarian\": true, " +
				"\"ingredients\": [ \"1kg of something\" ], \"instructions\": \"Just mix all the ingredients.\" }";
		this.mockMvc.perform(put("/recipes/{id}", this.recipes.get(0).getId())
				.content(updatedContent)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.version", is(1)))
				.andExpect(jsonPath("$.suitableFor", is(2)))
				.andExpect(jsonPath("$.vegetarian", is(true)))
				.andExpect(jsonPath("$.ingredients[0]", is("1kg of something")))
				.andExpect(jsonPath("$.instructions", is("Just mix all the ingredients.")));
	}

	@Test
	public void whenFindAllRecipesThenRecipesList() throws Exception {
		this.mockMvc.perform(get("/recipes")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)));
	}

	@Test
	public void givenExistingRecipeWhenDeleteRecipeThenRecipeDeleted() throws Exception {
		this.mockMvc.perform(delete("/recipes/{id}", recipes.get(0).getId())
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNoContent());
	}

	@Test
	public void givenUnexistentRecipeWhenDeleteRecipeThenRecipeDeleted() throws Exception {
		this.mockMvc.perform(delete("/recipes/{id}", "unexistentId")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}
}
