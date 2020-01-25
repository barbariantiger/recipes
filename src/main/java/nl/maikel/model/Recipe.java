package nl.maikel.model;

import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Document
public class Recipe {

    @Id
    private String id;
    private LocalDateTime createdOn;
    @NotNull(message = "Is it a vegetarian dish?")
    private Boolean vegetarian;
    @NotNull(message = "For how many people is it suitable for?")
    private Byte suitableFor;
    @NotNull(message = "Please provide at least one ingredient.")
    private List<String> ingredients;
    @NotBlank(message = "Please provide the cooking instructions of the dish.")
    private String instructions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public Byte getSuitableFor() {
        return suitableFor;
    }

    public void setSuitableFor(Byte suitableFor) {
        this.suitableFor = suitableFor;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(id, recipe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", createdOn=" + createdOn +
                ", vegetarian=" + vegetarian +
                ", suitableFor=" + suitableFor +
                ", ingredients=" + ingredients +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
