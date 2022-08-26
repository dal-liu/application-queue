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

import java.util.NoSuchElementException;

/**
 * This class implements unit test methods to check the correctness of Application,
 * ApplicationIterator, ApplicationQueue and OpenPosition classes in the assignment.
 *
 */
public class OpenPositionTester {

  /**
   * This method tests and makes use of the Application constructor, getter methods,
   * toString() and compareTo() methods.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testApplication() {
    // create an Application with valid input
    {
      try {
        new Application("name", "name@email", 0);
      } catch (Exception e) {
        System.out.println("Error: Application() threw an exception with valid input.");
        return false;
      }
    }
    // create an Application with invalid input:
    { // blank name
      try {
        new Application("", "name@email", 0);
        System.out.println("Error: Application() failed to throw an exception with blank name.");
        return false;
      } catch (IllegalArgumentException e) {
        // Works as intended
      } catch (Exception e) {
        System.out.println("Error: Application() threw the wrong exception with blank name.");
        return false;
      }
    }
    { // null email
      try {
        new Application("name", null, 0);
        System.out.println("Error: Application() failed to throw an exception with null email.");
        return false;
      } catch (IllegalArgumentException e) {
        // Works as intended
      } catch (Exception e) {
        System.out.println("Error: Application() threw the wrong exception with null email.");
        return false;
      }
    }
    { // no @ email
      try {
        new Application("name", "email", 0);
        System.out.println("Error: Application() failed to throw an exception with no @ email.");
        return false;
      } catch (IllegalArgumentException e) {
        // Works as intended
      } catch (Exception e) {
        System.out.println("Error: Application() threw the wrong exception with no @ email.");
        return false;
      }
    }
    { // too many @ email
      try {
        new Application("name", "name@@email", 0);
        System.out.println("Error: Application() failed to throw an exception with too many @ "
            + "email.");
        return false;
      } catch (IllegalArgumentException e) {
        // Works as intended
      } catch (Exception e) {
        System.out.println("Error: Application() threw the wrong exception with too many @ email.");
        return false;
      }
    }
    { // invalid score
      try {
        new Application("name", "name@email", -1);
        System.out.println("Error: Application() failed to throw an exception with invalid score.");
        return false;
      } catch (IllegalArgumentException e) {
        // Works as intended
      } catch (Exception e) {
        System.out.println("Error: Application() threw the wrong exception with invalid score.");
        return false;
      }
    }
    // verify getters
    { // test getName()
      String expected = "name";
      Application application = new Application(expected, "name@email", 0);
      String actual = application.getName();
      if (!expected.equals(actual)) {
        System.out.println("Error: getName() returned " + actual + " instead of " + expected);
        return false;
      }
    }
    { // test getEmail()
      String expected = "name@email";
      Application application = new Application("name", expected, 0);
      String actual = application.getEmail();
      if (!expected.equals(actual)) {
        System.out.println("Error: getEmail() returned " + actual + " instead of " + expected);
        return false;
      }
    }
    { // test getScore()
      int expected = 0;
      Application application = new Application("name", "name@email", expected);
      int actual = application.getScore();
      if (expected != actual) {
        System.out.println("Error: getScore() returned " + actual + " instead of " + expected);
        return false;
      }
    }
    // verify compareTo
    { // less than other
      Application application1 = new Application("name1", "name1@email", 0);
      Application application2 = new Application("name2", "name2@email", 100);
      int actual = application1.compareTo(application2);
      if (actual >= 0) {
        System.out.println("Error: compareTo() returned a positive integer instead of negative.");
        return false;
      }
    }
    { // equal to other
      Application application1 = new Application("name1", "name1@email", 50);
      Application application2 = new Application("name2", "name2@email", 50);
      int expected = 0;
      int actual = application1.compareTo(application2);
      if (expected != actual) {
        System.out.println("Error: compareTo() returned " + actual + " instead of " + expected);
        return false;
      }
    }
    { // greater than other
      Application application1 = new Application("name1", "name1@email", 100);
      Application application2 = new Application("name2", "name2@email", 0);
      int actual = application1.compareTo(application2);
      if (actual <= 0) {
        System.out.println("Error: compareTo() returned a negative integer instead of positive.");
        return false;
      }
    }
    // verify toString
    {
      Application application = new Application("name", "name@email", 0);
      String expected = "name:name@email:0";
      String actual = application.toString().trim();
      if (!expected.equals(actual)) {
        System.out.println("Error: compareTo() returned " + actual + " instead of " + expected);
        return false;
      }
    }
    return true;
  }

  /**
   * This method tests and makes use of the ApplicationIterator class.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testApplicationIterator() {
    // create an ApplicationQueue with capacity at least 3
    // and at least 3 Applications with different scores
    ApplicationQueue applications = new ApplicationQueue(5);
    Application app1 = new Application("name1", "name1@email", 50);
    Application app2 = new Application("name2", "name2@email", 100);
    Application app3 = new Application("name3", "name3@email", 0);
    Application app4 = new Application("name4", "name4@email", 25);
    Application app5 = new Application("name5", "name5@email", 75);
    // add those Applications to the queue
    applications.enqueue(app1);
    applications.enqueue(app2);
    applications.enqueue(app3);
    applications.enqueue(app4);
    applications.enqueue(app5);
    // verify that iterating through the queue gives you the applications in order of
    // INCREASING score
    int currScore = applications.peek().getScore();
    for (Application application : applications) {
      int nextScore = application.getScore();
      if (currScore > nextScore) {
        System.out.println("Error: did not iterate in order of increasing score.");
        return false;
      }
      currScore = nextScore;
    }
    return true;
  }

  /**
   * This method tests and makes use of the enqueue() and dequeue() methods
   * in the ApplicationQueue class.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testEnqueueDequeue() {
    // create an ApplicationQueue with capacity 3
    // and at least 4 Applications with different scores
    ApplicationQueue applications = new ApplicationQueue(3);
    Application app1 = new Application("name1", "name1@email", 50);
    Application app2 = new Application("name2", "name2@email", 100);
    Application app3 = new Application("name3", "name3@email", 0);
    Application app4 = new Application("name4", "name4@email", 25);
    // enqueue an invalid value (null)
    try {
      applications.enqueue(null);
      System.out.println("Error: enqueue() failed to throw an exception with null value.");
      return false;
    } catch (NullPointerException e) {
      // Works as intended
    } catch (Exception e) {
      System.out.println("Error: enqueue() threw the wrong exception with null value.");
      return false;
    }
    // enqueue one valid application
    try {
      applications.enqueue(app1);
    } catch (Exception e) {
      System.out.println("Error: enqueue() threw an exception with valid application.");
      return false;
    }
    // enqueue two more valid applications
    applications.enqueue(app2);
    applications.enqueue(app3);
    // enqueue one more application (exceeds capacity)
    try {
      applications.enqueue(app4);
      System.out.println("Error: enqueue() failed to throw an exception when exceeds capacity.");
      return false;
    } catch (IllegalStateException e) {
      // Works as intended
    } catch (Exception e) {
      System.out.println("Error: enqueue() threw the wrong exception.");
      return false;
    }
    // dequeue one application (should be lowest score)
    try {
      Application dequeued = applications.dequeue();
      if (dequeued.getScore() != 0) {
        System.out.println("Error: dequeue() dequeued the wrong Application.");
        return false;
      }
    } catch (Exception e) {
      System.out.println("Error: dequeue() threw an exception on a non-empty queue.");
      return false;
    }
    // dequeue all applications
    applications.dequeue();
    applications.dequeue();
    if (!applications.isEmpty()) {
      System.out.println("Error: dequeue() failed to change the size of the queue.");
      return false;
    }
    // dequeue from an empty queue
    try {
      applications.dequeue();
      System.out.println("Error: dequeue() failed to throw an exception from an empty queue.");
      return false;
    } catch (NoSuchElementException e) {
      // Works as intended
    } catch (Exception e) {
      System.out.println("Error: dequeue() threw the wrong exception from an empty queue.");
      return false;
    }
    return true;
  }

  /**
   * This method tests and makes use of the common methods (isEmpty(), size(), peek())
   * in the ApplicationQueue class.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testCommonMethods() {
    // create an ApplicationQueue with 0 capacity (should fail)
    try {
      new ApplicationQueue(0);
      System.out.println("Error: ApplicationQueue() failed to throw an exception with invalid "
        + "capacity.");
      return false;
    } catch (IllegalArgumentException e) {
      // Works as intended
    } catch (Exception e) {
      System.out.println("Error: ApplicationQueue() threw the wrong exception with invalid "
        + "capacity.");
      return false;
    }
    // create an ApplicationQueue with capacity 3
    // and at least 3 Applications with different scores
    ApplicationQueue applications = new ApplicationQueue(3);
    Application app1 = new Application("name1", "name1@email", 50);
    Application app2 = new Application("name2", "name2@email", 100);
    Application app3 = new Application("name3", "name3@email", 0);
    // verify the methods' behaviors on an empty queue
    if (!applications.isEmpty()) { // test isEmpty()
      System.out.println("Error: isEmpty() failed to return true for empty queue.");
      return false;
    }
    int expectedSize = 0;
    int actualSize = applications.size();
    if (expectedSize != actualSize) { // test size()
      System.out.println("Error: size() returned " + actualSize + " instead of " + expectedSize);
      return false;
    }
    try { // test peek()
      applications.peek();
      System.out.println("Error: peek() failed to throw an exception on an empty queue.");
      return false;
    } catch (NoSuchElementException e) {
      // Works as intended
    } catch (Exception e) {
      System.out.println("Error: peek() threw the wrong exception on an empty queue.");
      return false;
    }
    // add one Application and verify the methods' behaviors
    applications.enqueue(app1);
    if (applications.isEmpty()) { // test isEmpty()
      System.out.println("Error: isEmpty() failed to return false for non-empty queue.");
      return false;
    }
    expectedSize = 1;
    actualSize = applications.size();
    if (expectedSize != actualSize) { // test size()
      System.out.println("Error: size() returned " + actualSize + " instead of " + expectedSize);
      return false;
    }
    try {
      int expectedScore = 50;
      int actualScore = applications.peek().getScore();
      if (expectedScore != actualScore) { // test peek()
        System.out.println("Error: peek() returned application with score " + actualScore + " "
          + "instead of " + expectedScore);
        return false;
      }
    } catch (Exception e) {
      System.out.println("Error: peek() threw an exception on a non-empty queue.");
      return false;
    }
    // add the rest of the Applications and verify the methods' behaviors
    applications.enqueue(app2);
    applications.enqueue(app3);
    if (applications.isEmpty()) { // test isEmpty()
      System.out.println("Error: isEmpty() failed to return false for non-empty queue.");
      return false;
    }
    expectedSize = 3;
    actualSize = applications.size();
    if (expectedSize != actualSize) { // test size()
      System.out.println("Error: size() returned " + actualSize + " instead of " + expectedSize);
      return false;
    }
    try {
      int expectedScore = 0;
      int actualScore = applications.peek().getScore();
      if (expectedScore != actualScore) { // test peek()
        System.out.println("Error: peek() returned application with score " + actualScore + " "
          + "instead of " + expectedScore);
        return false;
      }
    } catch (Exception e) {
      System.out.println("Error: peek() threw an exception on a non-empty queue.");
      return false;
    }
    return true;
  }

  /**
   * This method tests and makes use of OpenPosition class.
   *
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testOpenPosition() {
    // create an OpenPosition with 0 capacity (should fail)
    try {
      new OpenPosition("position", 0);
      System.out.println("Error: OpenPosition() failed to throw an exception with 0 capacity.");
      return false;
    } catch (IllegalArgumentException e) {
      // Works as intended
    } catch (Exception e) {
      System.out.println("Error: OpenPosition() threw the wrong exception with 0 capacity.");
      return false;
    }
    // create an OpenPosition with capacity 3
    // and at least 5 Applications with different scores
    OpenPosition openPosition = new OpenPosition("position", 3);
    Application app1 = new Application("name1", "name1@email", 50);
    Application app2 = new Application("name2", "name2@email", 100);
    Application app3 = new Application("name3", "name3@email", 0);
    Application app4 = new Application("name4", "name4@email", 25);
    Application app5 = new Application("name5", "name5@email", 75);
    // verify that the 3 MIDDLE-scoring Applications can be added
    // don't use the highest and lowest scoring applications YET
    if (!openPosition.add(app1)) {
      System.out.println("Error: add() failed to add application with score 50.");
      return false;
    }
    if (!openPosition.add(app5)) {
      System.out.println("Error: add() failed to add application with score 75.");
      return false;
    }
    if (!openPosition.add(app4)) {
      System.out.println("Error: add() failed to add application with score 25.");
      return false;
    }
    // verify that getApplications returns the correct value for your input
    String expectedApps = "name4:name4@email:25\nname1:name1@email:50\nname5:name5@email:75";
    String actualApps = openPosition.getApplications().trim();
    if (!expectedApps.equals(actualApps)) {
      System.out.println("Error: getApplications() returned\n" + actualApps + "\ninstead of\n"
        + expectedApps);
      return false;
    }
    // verify that the result of getTotalScore is the sum of all 3 Application scores
    int expectedTotal = 150;
    int actualTotal = openPosition.getTotalScore();
    if (expectedTotal != actualTotal) {
      System.out.println("Error: getTotalScore() returned " + actualTotal + " instead of "
        + expectedTotal);
      return false;
    }
    // verify that the lowest-scoring application is NOT added to the OpenPosition
    if (openPosition.add(app3)) {
      System.out.println("Error: add() added the lowest-scoring application.");
      return false;
    }
    // verify that the highest-scoring application IS added to the OpenPosition
    if (!openPosition.add(app2)) {
      System.out.println("Error: add() failed to add application with score 100.");
      return false;
    }
    // verify that getApplications has changed correctly
    expectedApps = "name1:name1@email:50\nname5:name5@email:75\nname2:name2@email:100";
    actualApps = openPosition.getApplications().trim();
    if (!expectedApps.equals(actualApps)) {
      System.out.println("Error: getApplications() returned\n" + actualApps + "\ninstead of\n"
        + expectedApps);
      return false;
    }
    // verify that the result of getTotalScore has changed correctly
    expectedTotal = 225;
    actualTotal = openPosition.getTotalScore();
    if (expectedTotal != actualTotal) {
      System.out.println("Error: getTotalScore() returned " + actualTotal + " instead of "
        + expectedTotal);
      return false;
    }
    return true;
  }

  /**
   * This method calls all the test methods defined and implemented in your OpenPositionTester class.
   *
   * @return true if all the test methods defined in this class pass, and false otherwise.
   */
  public static boolean runAllTests() {
    return testApplication() && testApplicationIterator()
      && testEnqueueDequeue() && testCommonMethods()
      && testOpenPosition();
  }

  /**
   * Driver method defined in this OpenPositionTester class
   *
   * @param args input arguments if any.
   */
  public static void main(String[] args) {
    System.out.print(runAllTests());
  }
}
