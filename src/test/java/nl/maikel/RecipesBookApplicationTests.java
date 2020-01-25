package nl.maikel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RecipesBookApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private String content = "{" +
			"\"suitableFor\": 2, " +
			"\"vegetarian\": true, " +
			"\"ingredients\": [ \"1kg of something\" ], " +
			"\"instructions\": \"Mix this with that\" " +
			"}";

	@Test
	public void whenCreateRecipeThenRecipe() throws Exception {
		this.mockMvc.perform(post("/recipes")
				.content(content)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.suitableFor", is(2)))
				.andExpect(jsonPath("$.vegetarian", is(true)))
				.andExpect(jsonPath("$.ingredients[0]", is("1kg of something")))
				.andExpect(jsonPath("$.instructions", is("Mix this with that")));
	}
}
