package seedu.recipe.model.plan;

import java.util.Objects;

import seedu.recipe.model.Date;
import seedu.recipe.model.recipe.Recipe;

public class Plan implements Comparable<Plan> {
    private Recipe recipe;
    private Date date;

    public Plan(Recipe recipe, Date date) {
        this.recipe = recipe;
        this.date = date;
    }

    /**
     * Returns true if it is planned on {@code onDate}.
     */
    public boolean isOnDate(Date onDate) {
        return date.isOnDate(onDate);
    }

    public Plan setRecipe(Recipe recipe) {
        return new Plan(recipe, this.date);
    }

    public Date getDate() {
        return date;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public int compareTo(Plan other) {
        return date.compareTo(other.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipe, date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Plan // instanceof handles nulls
                && date.equals(((Plan) other).date) // state check
                && recipe.equals(((Plan) other).recipe));
    }

    @Override
    public String toString() {
        return date.toString() + "\n" + recipe.toString();
    }
}
