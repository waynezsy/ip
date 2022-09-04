package duke.task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import duke.DukeException;

/**
 * The TaskList class represents a list of tasks.
 */
public class TaskList {
    /** An ArrayList storing the tasks for the instance of TaskList. */
    private final ArrayList<Task> tasks;

    /**
     * Constructs a TaskList containing the tasks in a specified array list of Tasks.
     *
     * @param tasks The specified array list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Constructs a TaskList with the specified tasks.
     * @param tasks The tasks to place in the TaskList initially.
     *     The number of arguments is variable and may be zero.
     */
    public TaskList(Task ... tasks) {
        this.tasks = new ArrayList<>(Arrays.asList(tasks));
    }

    /**
     * Returns a string describing the number of remaining tasks.
     *
     * @return A string describing the number of remaining tasks.
     */
    public String getCountStatement() {
        int count = tasks.size();
        if (count == 1) {
            return "Now you have 1 task in your list.";
        } else {
            return "Now you have " + count + " tasks in your list.";
        }
    }

    /**
     * Creates a new TaskList of every Task which description contains
     * a specified keyword (or phrase).
     *
     * @param keyword The specified keyword (or phrase) within the descriptions of tasks.
     * @return A new TaskList of every task which description contains a specified keyword (or phrase).
     */
    public TaskList getAllContaining(String keyword) {
        ArrayList<Task> matchingList = tasks.stream()
                    .filter(x -> x.contains(keyword))
                    .collect(Collectors.toCollection(ArrayList::new));
        return new TaskList(matchingList);
    }

    /**
     * Creates a new TaskList of every Task that occurs by/at a specified date in the
     * original TaskList.
     *
     * @param date The specified date by/at which the tasks to find occur.
     * @return A new TaskList of every task that occurs by/at a specified date.
     */
    public TaskList getAllOnDate(LocalDate date) {
        assert date != null : "TaskList::allOnDate invoked with null argument.";
        ArrayList<Task> matchingList = tasks.stream()
                .filter(x -> x.isOnDate(date))
                .collect(Collectors.toCollection(ArrayList::new));
        return new TaskList(matchingList);
    }

    /**
     * Converts every Task stored in the TaskList into an array of strings containing the data of each Task.
     * These strings can then be stored in the hard disk.
     *
     * @return An array of strings representing each Task in the TaskList as data
     *     that can be stored in the hard disk.
     */
    public String[] convertAllToData() {
        return tasks.stream()
                .map(Task::toData)
                .toArray(String[]::new);
    }

    /**
     * Returns an array of strings representing each duke.task.Task
     * stored in the TaskList.
     *
     * @return An array of strings representing each task in the TaskList.
     */
    public String[] convertAllToString() {
        return tasks.stream()
                .map(Task::toString)
                .toArray(String[]::new);
    }

    /**
     * Adds a Task to the TaskList.
     *
     * @param task The specified Task to add to the TaskList.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a Task from the TaskList.
     *
     * @param numbers The number order of the specified task to delete.
     * @return The task that was deleted.
     * @throws DukeException when the task corresponding to the specified number does not exist.
     */
    public ArrayList<Task> delete(Integer[] numbers) throws DukeException {
        assert numbers.length != 0 : "TaskList::delete invoked with empty array.";
        Arrays.sort(numbers);
        ArrayList<Task> deletedTasks = new ArrayList<>();
        try {
            for (int i = 0; i < numbers.length; i++) {
                Task task = tasks.get(numbers[i] - i - 1);
                tasks.remove(task);
                deletedTasks.add(task);
            }
            return deletedTasks;
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("There's a specified task that does not exist.\n"
                    + "The other tasks have been deleted.");
        }
    }

    /**
     * Marks a task as done.
     *
     * @param numbers The numbers (in the list) of the specified tasks to mark as done.
     * @return The tasks that were marked as done.
     * @throws DukeException when the task corresponding to the specified number does not exist.
     */
    public ArrayList<Task> mark(Integer[] numbers) throws DukeException {
        assert numbers.length != 0 : "TaskList::mark invoked with empty array.";
        Arrays.sort(numbers);
        ArrayList<Task> markedTasks = new ArrayList<>();
        try {
            for (Integer number : numbers) {
                Task task = tasks.get(number - 1);
                task.markAsDone();
                markedTasks.add(task);
            }
            return markedTasks;
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("There's a specified task that does not exist.\n"
                    + "The other tasks have been marked.");
        }
    }

    /**
     * Marks a task as not done.
     *
     * @param numbers The numbers (in the list) of the specified tasks to mark as not done.
     * @return The tasks that were marked as not done.
     * @throws DukeException when the task corresponding to the specified number does not exist.
     */
    public ArrayList<Task> unmark(Integer[] numbers) throws DukeException {
        assert numbers.length != 0 : "TaskList::unmark invoked with empty array.";
        Arrays.sort(numbers);
        ArrayList<Task> unmarkedTasks = new ArrayList<>();
        try {
            for (Integer number : numbers) {
                Task task = tasks.get(number - 1);
                task.markAsNotDone();
                unmarkedTasks.add(task);
            }
            return unmarkedTasks;
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("There's a specified task that does not exist.\n"
                    + "The other tasks have been unmarked.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj instanceof TaskList) {
            TaskList taskList = (TaskList) obj;
            if (this.tasks == taskList.tasks) {
                return true;
            }
            return this.tasks.equals(taskList.tasks);
        } else {
            return false;
        }
    }
}
