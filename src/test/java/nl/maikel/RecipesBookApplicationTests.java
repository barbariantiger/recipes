package nl.maikel;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecipesBookApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void whenCreateRecipeThenRecipe() throws Exception {
		String content = "{ \"suitableFor\": 2, \"vegetarian\": true, \"ingredients\": [ \"1kg of something\" ], " +
				"\"instructions\": \"Mix this with that\" }";
		this.mockMvc.perform(post("/recipes")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated())
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
		String content = "{ \"suitableFor\": 2, \"vegetarian\": true, \"ingredients\": [ \"1kg of something\" ], " +
				"\"instructions\": \"Mix this with that\" }";
		this.mockMvc.perform(post("/recipes")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated())
				.andDo(result -> this.mockMvc.perform(get("/recipes/{id}",
						result.getResponse().getContentAsString().split("\"")[3])
						.contentType(MediaType.APPLICATION_JSON_VALUE))
						.andExpect(status().isOk()));
	}

	@Test
	public void givenRecipeNotExistsWhenReadRecipeThenException() throws Exception {
		this.mockMvc.perform(get("/recipes/{id}", "inexistentId")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	public void givenExistingRecipeWhenUpdateRecipeThenRecipe() throws Exception {
		String content = "{ \"suitableFor\": 2, \"vegetarian\": true, \"ingredients\": [ \"1kg of something\" ], " +
				"\"instructions\": \"Mix this with that\" }";
		String updatedContent = "{ \"suitableFor\": 1, \"vegetarian\": true, \"ingredients\": [ \"1kg of something\" ], " +
				"\"instructions\": \"Mix this with that\" }";
		this.mockMvc.perform(post("/recipes")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated())
				.andDo(result -> this.mockMvc.perform(put("/recipes/{id}",
						result.getResponse().getContentAsString().split("\"")[3])
						.content(updatedContent)
						.contentType(MediaType.APPLICATION_JSON_VALUE))
						.andExpect(status().isOk()));
	}
}
