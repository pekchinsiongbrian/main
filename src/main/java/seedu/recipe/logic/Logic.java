package seedu.recipe.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.recipe.commons.core.GuiSettings;
import seedu.recipe.logic.commands.CommandResult;
import seedu.recipe.logic.commands.exceptions.CommandException;
import seedu.recipe.logic.parser.exceptions.ParseException;
import seedu.recipe.model.ReadOnlyRecipeBook;
import seedu.recipe.model.cooked.Record;
import seedu.recipe.model.plan.PlannedDate;
import seedu.recipe.model.recipe.Recipe;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the RecipeBook.
     *
     * @see seedu.recipe.model.Model#getRecipeBook()
     */
    ReadOnlyRecipeBook getRecipeBook();

    /** Returns an unmodifiable view of the filtered list of recipes */
    ObservableList<Recipe> getFilteredRecipeList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getRecipeBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns an unmodifiable view of cooked records
     * @return list
     */
    ObservableList<Record> getFilteredRecordList();

    /**
     * Returns an unmodifiable view of the scheduled recipes.
     */
    ObservableList<PlannedDate> getFilteredPlannedList();

}