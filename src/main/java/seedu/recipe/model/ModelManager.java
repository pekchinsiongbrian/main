package seedu.recipe.model;

import static java.util.Objects.requireNonNull;
import static seedu.recipe.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.recipe.commons.core.GuiSettings;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.model.plan.PlannedBook;
import seedu.recipe.model.plan.PlannedRecipe;
import seedu.recipe.model.plan.ReadOnlyPlannedBook;
import seedu.recipe.model.recipe.Recipe;

/**
 * Represents the in-memory model of the recipe book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final RecipeBook recipeBook;
    private final PlannedBook plannedBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Recipe> filteredRecipes;
    private final VersionedRecipeBook states;
    // private final FilteredList<PlannedRecipe> filteredPlannedRecipes;
    private final SortedList<PlannedRecipe> sortedPlannedRecipes;
    private final FilteredList<PlannedRecipe> filteredPlannedRecipes;
    //private final SortedList<PlannedRecipe> sortedPlannedRecipes;

    /**
     * Initializes a ModelManager with the given recipeBook and userPrefs.
     */
    public ModelManager(ReadOnlyRecipeBook recipeBook, ReadOnlyPlannedBook plannedBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(recipeBook, userPrefs);

        logger.fine("Initializing with recipe book: " + recipeBook + " and user prefs " + userPrefs);

        this.recipeBook = new RecipeBook(recipeBook);
        this.plannedBook = new PlannedBook(plannedBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredRecipes = new FilteredList<>(this.recipeBook.getRecipeList());
        this.states = new VersionedRecipeBook(recipeBook);
        //sortedPlannedRecipes = new FilteredList<>(this.plannedBook.getPlannedList());
        sortedPlannedRecipes = new SortedList<>(this.plannedBook.getPlannedList());
        filteredPlannedRecipes = new FilteredList<>(sortedPlannedRecipes);
    }

    public ModelManager() {
        this(new RecipeBook(), new PlannedBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getRecipeBookFilePath() {
        return userPrefs.getRecipeBookFilePath();
    }

    @Override
    public void setRecipeBookFilePath(Path recipeBookFilePath) {
        requireNonNull(recipeBookFilePath);
        userPrefs.setRecipeBookFilePath(recipeBookFilePath);
    }

    //=========== RecipeBook ================================================================================

    @Override
    public void setRecipeBook(ReadOnlyRecipeBook recipeBook) {
        this.recipeBook.resetData(recipeBook);
    }

    @Override
    public ReadOnlyRecipeBook getRecipeBook() {
        return recipeBook;
    }

    @Override
    public boolean hasRecipe(Recipe recipe) {
        requireNonNull(recipe);
        return recipeBook.hasRecipe(recipe);
    }

    @Override
    public void deleteRecipe(Recipe target) {
        recipeBook.removeRecipe(target);
    }

    @Override
    public void favouriteRecipe(Recipe target) {
        recipeBook.favouriteRecipe(target);
    }

    @Override
    public void unfavouriteRecipe(Recipe target) {
        recipeBook.unfavouriteRecipe(target);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        recipeBook.addRecipe(recipe);
        updateFilteredRecipeList(PREDICATE_SHOW_ALL_RECIPES);
    }

    @Override
    public void setRecipe(Recipe target, Recipe editedRecipe) {
        requireAllNonNull(target, editedRecipe);
        recipeBook.setRecipe(target, editedRecipe);
    }

    @Override
    public boolean canUndo(int numberOfUndo) {
        return states.canUndo(numberOfUndo);
    }

    @Override
    public boolean canRedo(int numberOfRedo) {
        return states.canRedo(numberOfRedo);
    }

    @Override
    public void commitRecipeBook() {
        states.commit(new RecipeBook(recipeBook));
    }

    @Override
    public void undoRecipeBook(int numberOfUndo) {
        setRecipeBook(states.undo(numberOfUndo));
    }

    @Override
    public void redoRecipeBook(int numberOfRedo) {
        setRecipeBook(states.redo(numberOfRedo));
    }

    //=========== PlannedBook ================================================================================

    @Override
    public ReadOnlyPlannedBook getPlannedBook() {
        return plannedBook;
    }

    //=========== Filtered Recipe List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Recipe} backed by the internal list of
     * {@code versionedRecipeBook}
     */
    @Override
    public ObservableList<Recipe> getFilteredRecipeList() {
        return filteredRecipes;
    }

    @Override
    public void updateFilteredRecipeList(Predicate<Recipe> predicate) {
        requireNonNull(predicate);
        filteredRecipes.setPredicate(predicate);
    }

    //=========== Plan Recipe List Accessors =============================================================

    @Override
    public void addPlannedRecipe(PlannedRecipe plannedRecipe) {
        plannedBook.addPlannedRecipe(plannedRecipe);
    }

    @Override
    public void addPlannedMapping(Recipe recipe, PlannedRecipe plannedRecipe) {
        plannedBook.addPlannedMapping(recipe, plannedRecipe);
    }

    @Override
    public void removeAllPlannedMappingForRecipe(Recipe recipe) {
        plannedBook.removeAllPlannedMappingForRecipe(recipe);
    }

    @Override
    public void setPlannedRecipe(Recipe target, Recipe editedRecipe) {
        plannedBook.setPlannedRecipe(target, editedRecipe);
    }

    @Override
    public ObservableList<PlannedRecipe> getFilteredPlannedList() {
        System.out.println("called");
        //return new SortedList<>(sortedPlannedRecipes); // sortedPlannedRecipes; todo
        return sortedPlannedRecipes;
    }

    @Override
    public void updateFilteredPlannedList(Predicate<PlannedRecipe> predicate) {
        requireNonNull(predicate);
        System.out.println("called2");
        filteredPlannedRecipes.setPredicate(predicate);
    }


    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return recipeBook.equals(other.recipeBook)
                && plannedBook.equals(other.plannedBook)
                && userPrefs.equals(other.userPrefs)
                && filteredRecipes.equals(other.filteredRecipes);
    }



}
