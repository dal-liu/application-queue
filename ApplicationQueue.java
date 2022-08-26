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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array-based heap implementation of a priority queue containing Applications. Guarantees the
 * min-heap invariant, so that the Application at the root should have the lowest score, and
 * children always have a higher or equal score as their parent. The root of a non-empty queue is
 * always at index 0 of this array-heap.
 */
public class ApplicationQueue implements PriorityQueueADT<Application>, Iterable<Application> {
  private Application[] queue; // array min-heap of applications representing this priority queue
  private int size;            // size of this priority queue

  /**
   * Creates a new empty ApplicationQueue with the given capacity
   *
   * @param capacity Capacity of this ApplicationQueue
   * @throws IllegalArgumentException with a descriptive error message if the capacity is not a
   *                                  positive integer
   */
  public ApplicationQueue(int capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException(
        "Tried to create ApplicationQueue with non-positive capacity.");
    }

    queue = new Application[capacity];
    size = 0;
  }

  /**
   * Checks whether this ApplicationQueue is empty
   *
   * @return {@code true} if this ApplicationQueue is empty
   */
  @Override public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Returns the size of this ApplicationQueue
   *
   * @return the size of this ApplicationQueue
   */
  @Override public int size() {
    return size;
  }

  /**
   * Adds the given Application to this ApplicationQueue and use the percolateUp() method to
   * maintain min-heap invariant of ApplicationQueue. Application should be compared using the
   * Application.compareTo() method.
   *
   * @param o Application to add to this ApplicationQueue
   * @throws NullPointerException  if the given Application is null
   * @throws IllegalStateException with a descriptive error message if this ApplicationQueue is
   *                               full
   */
  @Override public void enqueue(Application o) {
    if (o == null) {
      throw new NullPointerException("Tried to enqueue a null Application.");
    }
    if (size >= queue.length) {
      throw new IllegalStateException("Tried to enqueue to a full queue.");
    }

    queue[size] = o;
    percolateUp(size);
    size++;
  }

  /**
   * Removes and returns the Application at the root of this ApplicationQueue, i.e. the Application
   * with the lowest score.
   *
   * @return the Application in this ApplicationQueue with the smallest score
   * @throws NoSuchElementException with a descriptive error message if this ApplicationQueue is
   *                                empty
   */
  @Override public Application dequeue() {
    if (size == 0) {
      throw new NoSuchElementException("Tried to dequeue from an empty queue.");
    }

    Application toReturn = queue[0];
    queue[0] = queue[size - 1];
    queue[size - 1] = null;
    size--;
    if (size > 0) {
      percolateDown(0);
    }
    return toReturn;
  }

  /**
   * An implementation of percolateDown() method. Restores the min-heap invariant of a given subtree
   * by percolating its root down the tree. If the element at the given index does not violate the
   * min-heap invariant (it is due before its children), then this method does not modify the heap.
   * Otherwise, if there is a heap violation, then swap the element with the correct child and
   * continue percolating the element down the heap.
   *
   * This method may be implemented recursively OR iteratively.
   *
   * @param i index of the element in the heap to percolate downwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  private void percolateDown(int i) {
    if (i < 0 || i >= size) {
      throw new IndexOutOfBoundsException("Tried to percolate down at an out of bounds index.");
    }

    int childIndex = 2 * i + 1;
    int score = queue[i].getScore();
    while (childIndex < size) {
      int minScore = score;
      int minIndex = -1;
      for (int j = 0; j < 2 && j + childIndex < size; j++) {
        if (queue[j + childIndex].getScore() < minScore) {
          minScore = queue[j + childIndex].getScore();
          minIndex = j + childIndex;
        }
      }
      if (minScore == score) {
        return;
      } else {
        swap(minIndex, i);
        i = minIndex;
        childIndex = 2 * i + 1;
      }
    }
  }

  /**
   * An implementation of percolateUp() method. Restores the min-heap invariant of the tree by
   * percolating a leaf up the tree. If the element at the given index does not violate the min-heap
   * invariant (it occurs after its parent), then this method does not modify the heap. Otherwise,
   * if there is a heap violation, swap the element with its parent and continue percolating the
   * element up the heap.
   *
   * This method may be implemented recursively OR iteratively.
   *
   * Feel free to add private helper methods if you need them.
   *
   * @param i index of the element in the heap to percolate upwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  private void percolateUp(int i) {
    if (i < 0 || i >= queue.length) {
      throw new IndexOutOfBoundsException("Tried to percolate up at an out of bounds index.");
    }

    while (i > 0) {
      int parentIndex = (i - 1) / 2;
      if (queue[i].getScore() >= queue[parentIndex].getScore()) {
        return;
      } else {
        swap(parentIndex, i);
        i = parentIndex;
      }
    }
  }

  /**
   * Swaps the places of 2 Applications in queue
   *
   * @param index1 index of first Application to swap
   * @param index2 index of second Application to swap
   */
  private void swap(int index1, int index2) {
    Application temp = queue[index1];
    queue[index1] = queue[index2];
    queue[index2] = temp;
  }

  /**
   * Returns the Application at the root of this ApplicationQueue, i.e. the Application with the
   * lowest score.
   *
   * @return the Application in this ApplicationQueue with the smallest score
   * @throws NoSuchElementException if this ApplicationQueue is empty
   */
  @Override public Application peek() {
    if (size == 0) {
      throw new NoSuchElementException("Tried to peek at an empty queue.");
    }

    return queue[0];
  }

  /**
   * Returns a deep copy of this ApplicationQueue containing all of its elements in the same order.
   * This method does not return the deepest copy, meaning that you do not need to duplicate
   * applications. Only the instance of the heap (including the array and its size) will be
   * duplicated.
   *
   * @return a deep copy of this ApplicationQueue. The returned new application queue has the same
   * length and size as this queue.
   */
  public ApplicationQueue deepCopy() {
    ApplicationQueue toReturn = new ApplicationQueue(size);
    for (int i = 0; i < size; i++) {
      toReturn.enqueue(queue[i]);
    }
    return toReturn;
  }

  /**
   * Returns a String representing this ApplicationQueue, where each element (application) of the
   * queue is listed on a separate line, in order from the lowest score to the highest score.
   *
   * This implementation is provided.
   *
   * @return a String representing this ApplicationQueue
   * @see Application#toString()
   * @see ApplicationIterator
   */
  @Override public String toString() {
    StringBuilder val = new StringBuilder();

    for (Application a : this) {
      val.append(a).append("\n");
    }

    return val.toString();

  }

  /**
   * Returns an Iterator for this ApplicationQueue which proceeds from the lowest-scored to the
   * highest-scored Application in the queue.
   *
   * This implementation is provided.
   *
   * @return an Iterator for this ApplicationQueue
   * @see ApplicationIterator
   */
  @Override public Iterator<Application> iterator() {
    return new ApplicationIterator(this);
  }
}
