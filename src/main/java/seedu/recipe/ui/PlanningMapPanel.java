package seedu.recipe.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.logging.Logger;

import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.recipe.commons.core.LogsCenter;
import seedu.recipe.commons.util.StringUtil;
import seedu.recipe.model.recipe.Name;
import seedu.recipe.model.recipe.Recipe;
import seedu.recipe.model.recipe.Time;
import seedu.recipe.model.recipe.ingredient.Quantity;
import seedu.recipe.model.recipe.ingredient.Unit;
import seedu.recipe.model.recipe.ingredient.Vegetable;

/**
 * Panel containing the list of recipes.
 */
public class PlanningMapPanel extends UiPart<Region> {
    private static final String FXML = "PlanningMapPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(RecipeListPanel.class);

    //@FXML
    //private ListView<Recipe> planningMapView;

    @FXML
    private GridPane calendarPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label monthHeader;

    public PlanningMapPanel(LocalDate date, List<Recipe> recipes) {
        super(FXML);

        Calendar cal = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("Asia/Singapore");
        cal.setTimeZone(tz);
        Locale singaporeLocale = new Locale("en", "SGP");

        int numDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        monthHeader.setText(cal.getDisplayName(cal.MONTH, cal.LONG, singaporeLocale));

        //Date currentDate = cal.getTime();
        //monthHeader.setText(cal.get(Calendar.MONTH));
        //System.out.println("" + cal.get(cal.DAY_OF_MONTH) + cal.get(cal.MONTH) + cal.get(cal.YEAR));
        calendarPane.setGridLinesVisible(true);
        calendarPane.addRow(0,
                new Label("Sunday"),
                new Label("Monday"),
                new Label("Tuesday"),
                new Label("Wednesday"),
                new Label("Thursday"),
                new Label("Friday"),
                new Label("Saturday"));
        int dayOfWeek = cal.getFirstDayOfWeek() - 1;
        int weekOfMonth = 1;
        System.out.println(numDaysInMonth);

        List<Recipe> stubRecipes = new ArrayList<>();
        Set<Vegetable> set = new TreeSet<>();
        set.add(new Vegetable("vegetableee", new Quantity(30, Unit.GRAM)));

        stubRecipes.add(recipes.get(0));
        /*stubRecipes.add(new Recipe(new Name("hi"), new Time("15"), new TreeSet<>().add(""), set,
                new TreeSet<>(), new TreeSet<>(), new TreeSet<>(), new ArrayList<>(), null, false));*/

        for (int i = 1; i <= numDaysInMonth; i++) {
            PlanningDayCard card = new PlanningDayCard(stubRecipes, i);
            calendarPane.add(card.getDayCard(), dayOfWeek, weekOfMonth);
            //calendarPane.add(new Label("" + i), dayOfWeek, weekOfMonth);/

            dayOfWeek++;
            if (dayOfWeek > 6) {
                dayOfWeek = 0;
                weekOfMonth++;
            }
        }

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Recipe} using a {@code RecipeCard}.
     */
    class PlanningListViewCell extends ListCell<Recipe> {
        @Override
        protected void updateItem(Recipe recipe, boolean empty) {
            super.updateItem(recipe, empty);
            if (empty || recipe == null) {
                setGraphic(null);
                setText(null);
            } else {
                try {
                    setGraphic(new PlanningMapCard(recipe, getIndex() + 1).getRoot());
                } catch (IOException e) {
                    logger.warning("Failed to favourites icon : " + StringUtil.getDetails(e));
                }
            }
        }
    }

}
