//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    P10 Open Position
// Course:   CS 300 Spring 2022
//
// Author:   Daniel Liu
// Email:    daliu2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    (name of your pair programming partner)
// Partner Email:   (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment.
//   ___ We have both read and understand the course Pair Programming Policy.
//   ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons:         NONE
// Online Sources:  NONE
//
///////////////////////////////////////////////////////////////////////////////

/**
 * A application handler of an open position using priority queue. Only saves a new Application
 * when the queue is not full, or when it can replace older, lower-scored ones with its higher
 * scores.
 */
public class OpenPosition {
  private String positionName;
  private ApplicationQueue applications; // the priority queue of all applications
  private int capacity;                  // the number of vacancies

  /**
   * Creates a new open position with the given capacity
   *
   * @param capacity the number of vacancies of this position
   * @throws IllegalArgumentException with a descriptive error message if the capacity is not a
   *                                  positive integer
   */
  public OpenPosition(String positionName, int capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException("Tried to create open position with non-positive capacity"
        + ".");
    }

    this.positionName = positionName;
    this.capacity = capacity;
    applications = new ApplicationQueue(capacity);
  }

  /**
   * Returns the name of this open position
   *
   * @return the name of this open position
   */
  public String getPositionName() { return this.positionName; }

  /**
   * Tries to add the given Application to the priority queue of this position.  return
   * False when the new Application has a lower score than the lowest-scored Application
   * in the queue.
   *
   * @return Whether the given Application was added successfully
   */
  public boolean add(Application application) {
    if (capacity == applications.size()) {
      if (applications.peek().getScore() < application.getScore()) {
        applications.dequeue();
      } else {
        return false;
      }
    }
    applications.enqueue(application);
    return true;
  }

  /**
   * Returns the list of Applications in the priority queue.
   *
   * @return The list of Applications in the priority queue, in increasing order of the
   * scores.
   */
  public String getApplications() {
    return applications.toString();
  }

  /**
   * Returns the total score of Applications in the priority queue.
   *
   * @return The total score of Applications in the priority queue.
   */
  public int getTotalScore() {
    int total = 0;
    for (Application app : applications) {
      total += app.getScore();
    }
    return total;
  }


}
