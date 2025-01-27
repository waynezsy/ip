package nyanduke.task;

import java.time.LocalDate;

import nyanduke.NyanDukeException;
import nyanduke.Ui;

/**
 * The Todo class represents a task
 * without any date/time attached to it.
 */
public class Todo extends Task {
    /**
     * Constructs a new Todo with a specified description.
     *
     * @param description A string specifying the description of the Todo task.
     * @throws NyanDukeException when the description is an empty String.
     */
    public Todo(String description) throws NyanDukeException {
        super(description);
        if (description.equals("")) {
            throw new NyanDukeException(Ui.ERROR_TODO);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Returns the representation of a Todo task when stored in a data file on the hard disk.
     *
     * @return A string representing the Todo task as it is stored on a data file on the hard disk.
     */
    @Override
    public String toData() {
        return "T | " + super.toData() + "\n";
    }

    /**
     * Checks if the Todo task should be done on a specific date.
     *
     * @param date The specified date to check.
     * @return false.
     */
    @Override
    public boolean isOnDate(LocalDate date) {
        assert date != null : "Todo::isOnDate invoked with null argument.";
        return false;
    }
}
