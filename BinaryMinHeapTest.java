import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class BinaryMinHeapTest {

    private BinaryMinHeap<Integer> heap;

    @Before
    public void setUp() {
        heap = new BinaryMinHeap<>(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void add_NullElement_ShouldThrowIllegalArgumentException() {
        heap.add(null);
    }

    @Test
    public void add_OneElement_ShouldBeRoot() {
        heap.add(10);
        assertEquals("Added element should be root", Integer.valueOf(10), heap.get());
    }


    @Test
    public void add_MultipleElements_ShouldMaintainMinHeapProperty() {
        heap.add(10);
        heap.add(5);
        heap.add(15);

        // Check that elements are returned in ascending order, which confirms the min-heap property
        assertEquals("First removed element should be the smallest", Integer.valueOf(5), heap.remove());
        assertEquals("Second removed element should be the next smallest", Integer.valueOf(10), heap.remove());
        assertEquals("Third removed element should be the largest", Integer.valueOf(15), heap.remove());
    }


    @Test
    public void add_ElementsWithEqualPriority_ShouldHandleEqualityGracefully() {
        heap.add(10);
        heap.add(10);

        // Remove elements and check if they are equal, indicating correct handling of duplicates
        assertEquals("First removed element should match", Integer.valueOf(10), heap.remove());
        assertEquals("Second removed element should match and be equal to the first", Integer.valueOf(10), heap.remove());
        assertTrue("Heap should be empty after removing both elements", heap.isEmpty());
    }


    @Test
    public void add_IncreasingOrder_ShouldKeepMinAtRoot() {
        heap.add(1);
        heap.add(2);
        heap.add(3);

        // The remove method should return the smallest element, which is the root of the heap
        assertEquals("Root should still be the minimum element", Integer.valueOf(1), heap.remove());

        // Optionally, you can add further assertions to check the order of remaining elements
        assertEquals("Next element should be in increasing order", Integer.valueOf(2), heap.remove());
        assertEquals("Last element should be in increasing order", Integer.valueOf(3), heap.remove());
    }


    @Test
    public void add_DecreasingOrder_ShouldReorderToMaintainHeap() {
        heap.add(3);
        heap.add(2);
        heap.add(1);

        // Removing the root element to check if it's the minimum
        assertEquals("Root should be the new minimum element", Integer.valueOf(1), heap.remove());
    }


    @Test
    public void add_DuplicateValues_ShouldAcceptDuplicates() {
        heap.add(2);
        heap.add(2);

        // Removing the root element to check if the heap handles duplicates correctly
        assertEquals("Heap should accept duplicate values", Integer.valueOf(2), heap.remove());
        assertEquals("Heap should still contain the duplicate value", Integer.valueOf(2), heap.remove());
    }


    @Test
    public void add_LargeNumberOfElements_ShouldResizeInternallyWithoutError() {
        for (int i = 0; i < 1000; i++) {
            heap.add(i);
        }
        assertTrue("Heap should resize internally without error", heap.size() == 1000);
    }

    @Test
    public void add_ElementLowerThanCurrentMin_ShouldBecomeNewRoot() {
        heap.add(10);
        heap.add(5);

        // The remove method should return the smallest element, which is now the new root
        assertEquals("New element lower than current min should become new root", Integer.valueOf(5), heap.remove());
    }


    @Test
    public void add_ElementHigherThanAll_ShouldNotBecomeRoot() {
        heap.add(1);
        heap.add(2);
        heap.add(3);
        heap.add(4);

        // Removing the root element to check if it remains the same after adding a higher element
        assertEquals("Root should not change when adding element higher than all", Integer.valueOf(1), heap.remove());
    }


    @Test
    public void add_ElementsToExceedInitialCapacity_ShouldResizeCorrectly() {
        for (int i = 0; i < 15; i++) {
            heap.add(i);
        }
        assertTrue("Heap should resize correctly when initial capacity is exceeded", heap.size() == 15);
    }
    @Test(expected = NoSuchElementException.class)
    public void get_OnEmptyHeap_ShouldThrowNoSuchElementException() {
        heap.get(); // Should throw NoSuchElementException
    }


    @Test
    public void get_AfterAddingOneElement_ShouldReturnThatElement() {
        heap.add(10);
        assertEquals("get() should return the element that was added", Integer.valueOf(10), heap.get());
    }

    @Test
    public void get_AfterAddingMultipleElements_ShouldAlwaysReturnMinimum() {
        heap.add(10);
        heap.add(5);
        heap.add(15);
        assertEquals("get() should always return the minimum element", Integer.valueOf(5), heap.get());
    }

    @Test
    public void get_MultipleCalls_ShouldReturnSameResultWithoutRemoval() {
        heap.add(10);
        heap.add(5);
        Integer firstCall = heap.get();
        Integer secondCall = heap.get();
        assertEquals("Multiple calls to get() should return the same result", firstCall, secondCall);
    }

    @Test
    public void get_AfterRemovingRoot_ShouldReturnNewRoot() {
        heap.add(10);
        heap.add(5);
        heap.add(15);
        heap.remove(); // This should remove the root element (5)
        assertEquals("get() should return the new root after removal", Integer.valueOf(10), heap.get());
    }

    @Test(expected = NoSuchElementException.class)
    public void remove_OnEmptyHeap_ShouldThrowNoSuchElementException() {
        heap.remove(); // Should throw NoSuchElementException
    }


    @Test
    public void remove_OnHeapWithOneElement_ShouldEmptyHeap() {
        heap.add(10);
        heap.remove();
        assertTrue("Heap should be empty after removing the only element", heap.isEmpty());
    }

    @Test
    public void remove_OnHeapWithMultipleElements_ShouldMaintainMinHeapProperty() {
        heap.add(10);
        heap.add(5);
        heap.add(15);
        assertEquals("First removal should remove the minimum element", Integer.valueOf(5), heap.remove());

        // After removing the smallest element, the next smallest should be at the root
        assertEquals("Next removal should remove the next smallest element", Integer.valueOf(10), heap.remove());

        // Finally, the largest element should be removed last
        assertEquals("Final removal should remove the largest element", Integer.valueOf(15), heap.remove());
    }


    @Test
    public void remove_OnHeapWithDuplicateValues_ShouldRemoveOneInstance() {
        heap.add(10);
        heap.add(10);
        heap.remove();
        assertFalse("Heap should not be empty after removing one instance of a duplicate", heap.isEmpty());
        assertEquals("Heap should still contain one instance of the duplicate value", Integer.valueOf(10), heap.get());
    }

    @Test
    public void remove_SequentialRemoves_ShouldAlwaysReturnSortedOrder() {
        heap.add(10);
        heap.add(5);
        heap.add(15);
        assertEquals("First remove should return the smallest element", Integer.valueOf(5), heap.remove());
        assertEquals("Second remove should return the next smallest element", Integer.valueOf(10), heap.remove());
        assertEquals("Third remove should return the largest element", Integer.valueOf(15), heap.remove());
    }

    @Test
    public void remove_AllElementsOneByOne_ShouldEventuallyEmptyHeap() {
        heap.add(10);
        heap.add(5);
        heap.add(15);
        heap.remove();
        heap.remove();
        heap.remove();
        assertTrue("Heap should be empty after removing all elements", heap.isEmpty());
    }

    @Test
    public void remove_AddAfterRemove_ShouldMaintainHeapProperty() {
        heap.add(10);
        heap.remove();
        heap.add(20);
        assertEquals("Heap should maintain min-heap property after add after remove", Integer.valueOf(20), heap.get());
    }

    @Test
    public void clear_OnEmptyHeap_ShouldDoNothing() {
        heap.clear();
        assertTrue("Clearing an empty heap should do nothing", heap.isEmpty());
    }

    @Test
    public void clear_OnNonEmptyHeap_ShouldRemoveAllElements() {
        heap.add(10);
        heap.clear();
        assertTrue("Clearing a non-empty heap should remove all elements", heap.isEmpty());
    }

    @Test
    public void clear_AfterSequentialAddsAndRemoves_ShouldStillClearHeap() {
        heap.add(10);
        heap.remove();
        heap.add(20);
        heap.clear();
        assertTrue("Clearing a heap after adds and removes should clear the heap", heap.isEmpty());
    }

    @Test
    public void clear_TwiceInARow_ShouldNotAffectHeap() {
        heap.add(10);
        heap.clear();
        heap.clear();
        assertTrue("Clearing a heap twice in a row should not affect the heap", heap.isEmpty());
    }

    @Test
    public void size_OnEmptyHeap_ShouldReturnZero() {
        assertEquals("Size of an empty heap should be 0", 0, heap.size());
    }

    @Test
    public void size_AfterAdditions_ShouldReturnCorrectSize() {
        heap.add(10);
        heap.add(20);
        assertEquals("Size should reflect number of additions", 2, heap.size());
    }

    @Test
    public void size_AfterRemovals_ShouldDecreaseProperly() {
        heap.add(10);
        heap.add(20);
        heap.remove();
        assertEquals("Size should decrease after removals", 1, heap.size());
    }

    @Test
    public void size_AfterClear_ShouldReturnToZero() {
        heap.add(10);
        heap.add(20);
        heap.clear();
        assertEquals("Size should be 0 after clearing the heap", 0, heap.size());
    }

    @Test
    public void size_AfterRandomAddsAndRemoves_ShouldReflectCorrectSize() {
        heap.add(10);
        heap.add(20);
        heap.remove();
        heap.add(30);
        assertEquals("Size should reflect random additions and removals", 2, heap.size());
    }

    @Test
    public void size_IncreaseBeyondIntegerMaxValue_ShouldHandleOverflow() {
        // This test is more theoretical, as it's impractical to actually add Integer.MAX_VALUE elements
        // due to memory constraints, but it's important to know how your heap would handle it.
        // You might use mocking to simulate this condition.
    }

    @Test
    public void isEmpty_OnNewHeap_ShouldReturnTrue() {
        assertTrue("A new heap should be empty", heap.isEmpty());
    }

    @Test
    public void isEmpty_AfterAddingElements_ShouldReturnFalse() {
        heap.add(10);
        assertFalse("Heap should not be empty after adding elements", heap.isEmpty());
    }

    @Test
    public void isEmpty_AfterClearingHeap_ShouldReturnTrue() {
        heap.add(10);
        heap.clear();
        assertTrue("Heap should be empty after clearing", heap.isEmpty());
    }

    @Test
    public void isEmpty_AfterRemovingAllElements_ShouldReturnTrue() {
        heap.add(10);
        heap.remove();
        assertTrue("Heap should be empty after removing all elements", heap.isEmpty());
    }

    @Test
    public void isEmpty_AfterClearingAndAddingOneElement_ShouldReturnFalse() {
        heap.clear();
        heap.add(10);
        assertFalse("Heap should not be empty after adding an element post-clearing", heap.isEmpty());
    }

    @Test
    public void iterator_OnEmptyHeap_ShouldNotHaveNext() {
        Iterator<Integer> iterator = heap.iterator();
        assertFalse("Iterator should not have next on an empty heap", iterator.hasNext());
    }

    @Test
    public void iterator_OnNonEmptyHeap_ShouldIterateAllElementsInAscendingOrder() {
        heap.add(10);
        heap.add(5);
        heap.add(15);
        Iterator<Integer> iterator = heap.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(5), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(10), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(15), iterator.next());
        assertFalse(iterator.hasNext());
    }





    @Test(expected = ConcurrentModificationException.class)
    public void iterator_ConcurrentModification_ShouldThrowConcurrentModificationException() {
        heap.add(10);
        Iterator<Integer> iterator = heap.iterator();
        heap.add(20);
        iterator.next(); // This should throw ConcurrentModificationException
    }

    @Test(expected = NoSuchElementException.class)
    public void iterator_NextAfterEndReached_ShouldThrowNoSuchElementException() {
        heap.add(10);
        Iterator<Integer> iterator = heap.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        iterator.next(); // This should throw NoSuchElementException
    }

    @Test(expected = UnsupportedOperationException.class)
    public void iterator_PreviousAfterBeginReached_ShouldThrowUnsupportedOperationException() {
        // This test assumes the iterator is a ListIterator, which may not be the case.
        // Adjust accordingly if your iterator is not a ListIterator.
        heap.add(10);
        ListIterator<Integer> iterator = (ListIterator<Integer>) heap.iterator();
        while (iterator.hasPrevious()) {
            iterator.previous();
        }
        iterator.previous(); // This should throw UnsupportedOperationException
    }

    @Test
    public void iterator_CallNextWithoutHasNext_ShouldReturnElementsSequentially() {
        heap.add(10);
        heap.add(5);
        Iterator<Integer> iterator = heap.iterator();
        assertEquals(Integer.valueOf(5), iterator.next()); // Assuming the heap is min-heap
        assertEquals(Integer.valueOf(10), iterator.next());
    }

    @Test
    public void iterator_AfterModifyingHeap_ShouldNotReflectChanges() {
        heap.add(10);
        heap.add(5);
        Iterator<Integer> iterator = heap.iterator();
        heap.add(3);
        assertTrue(iterator.hasNext());
        assertEquals("Iterator should return the next element from its snapshot", Integer.valueOf(5), iterator.next());
    }

    @Test
    public void siftUp_OnRoot_ShouldDoNothing() {
        heap.add(10);
        heap.add(5);
        heap.add(15);

        heap._siftUp(0); // Assuming root is at index 0 and _siftUp is the method name

        // Check if the root element is still the same
        assertEquals("Root element should remain the same", Integer.valueOf(5), heap.remove());

        // Further checks to confirm the heap order is maintained
        assertEquals("Next element should follow heap order", Integer.valueOf(10), heap.remove());
        assertEquals("Last element should follow heap order", Integer.valueOf(15), heap.remove());
    }


    @Test
    public void siftUp_OnLeafElement_ShouldReorderHeap() {
        heap.add(10);
        heap.add(5);
        heap._siftUp(heap.size() - 1); // Assuming the last element is a leaf

        // Verify the order of elements to ensure the heap is reordered correctly
        assertEquals("First removal should return the smallest element", Integer.valueOf(5), heap.remove());
        assertEquals("Second removal should return the next smallest element", Integer.valueOf(10), heap.remove());
    }


    @Test
    public void siftUp_OnMiddleElement_ShouldReorderHeap() {
        heap.add(10);
        heap.add(15);
        heap.add(5);
        heap._siftUp(1); // Assuming index 1 is a middle element

        // Verify the order of elements to ensure the heap is reordered correctly
        assertEquals("First removal should return the smallest element", Integer.valueOf(5), heap.remove());
        assertEquals("Second removal should return the next smallest element", Integer.valueOf(10), heap.remove());
        assertEquals("Third removal should return the largest element", Integer.valueOf(15), heap.remove());
    }


    // ... Similar implementations for other siftUp test cases

    @Test
    public void siftDown_OnRootWithCorrectHeap_ShouldDoNothing() {
        heap.add(5);
        heap.add(10);
        heap._siftDown(0); // Assuming root is at index 0

        // Verify the order of elements
        assertEquals("First removal should return the smallest element", Integer.valueOf(5), heap.remove());
        assertEquals("Second removal should return the next smallest element", Integer.valueOf(10), heap.remove());
    }


    @Test
    public void siftDown_OnRootWithIncorrectHeap_ShouldReorderHeap() {
        heap.add(10);
        heap.add(5); // Incorrect order
        heap._siftDown(0);

        // Verify the order of elements
        assertEquals("First removal should return the smallest element", Integer.valueOf(5), heap.remove());
        assertEquals("Second removal should return the largest element", Integer.valueOf(10), heap.remove());
    }


    @Test
    public void siftDown_OnLeaf_ShouldDoNothing() {
        heap.add(5);
        heap.add(10);
        heap._siftDown(heap.size() - 1); // Assuming the last element is a leaf

        // Verify the order of elements
        assertEquals("First removal should return the smallest element", Integer.valueOf(5), heap.remove());
        assertEquals("Second removal should return the largest element", Integer.valueOf(10), heap.remove());
    }


    @Test
    public void maintainCompleteTreeProperty_AfterMultipleOperations() {
        heap.add(10);
        heap.add(5);
        heap.add(15);
        heap.remove(); // Should remove 5, the minimum element

        // Check if remaining elements are in the correct min-heap order
        assertEquals("First removal should return the next smallest element", Integer.valueOf(10), heap.remove());
        assertEquals("Second removal should return the largest element", Integer.valueOf(15), heap.remove());

        // Optionally, add more elements and repeat to further verify
        heap.add(20);
        heap.add(5);
        assertEquals("After additional operations, smallest element should be at the root", Integer.valueOf(5), heap.remove());
    }


    @Test
    public void maintainMinHeapProperty_AfterEachAdd() {
        heap.add(10);
        assertEquals("Root should be 10 after first add", Integer.valueOf(10), heap.remove());

        heap.add(5);
        heap.add(15);
        assertEquals("Root should be 5 after adding 5 and 15", Integer.valueOf(5), heap.remove());

        heap.add(12);
        assertEquals("Root should be 12 after adding 12", Integer.valueOf(12), heap.remove());
    }


    @Test
    public void maintainMinHeapProperty_AfterEachRemove() {
        heap.add(10);
        heap.add(5);
        heap.add(15);

        assertEquals("Root should be 5 before removal", Integer.valueOf(5), heap.remove());
        assertEquals("Root should be 10 after removing 5", Integer.valueOf(10), heap.remove());

        heap.add(8);
        assertEquals("Root should be 8 after adding 8 and removing 10", Integer.valueOf(8), heap.remove());
    }


    @Test(expected = ClassCastException.class)
    public void add_InvalidType_ShouldThrowClassCastExceptionIfNotComparable() {
        heap.add((Integer) new Object()); // Assuming heap is of type Integer
    }

    @Test(expected = ConcurrentModificationException.class)
    public void iterator_FailFastBehaviorOnModification_ShouldThrowConcurrentModificationException() {
        Iterator<Integer> it = heap.iterator();
        heap.add(10);
        it.next(); // Should throw ConcurrentModificationException
    }




    @Test(expected = IllegalArgumentException.class)
    public void add_NullElementWithNonNullElementsPresent_ShouldThrowException() {
        heap.add(null); // Should throw IllegalArgumentException
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void siftUp_And_siftDown_OnOutOfBoundsIndex_ShouldThrowIndexOutOfBoundsException() {
        heap._siftUp(-1); // Should throw IndexOutOfBoundsException
        heap._siftDown(heap.size()); // Should throw IndexOutOfBoundsException
    }

    @Test(expected = IllegalArgumentException.class)
    public void exception_ConstructorWithIllegalCapacity_ShouldThrowException() {
        new BinaryMinHeap<Integer>(-1); // Should throw IllegalArgumentException
    }

    @Test(expected = NoSuchElementException.class)
    public void exception_CallingRemoveOnEmptyHeap_ShouldThrowNoSuchElementException() {
        heap.remove(); // Should throw NoSuchElementException
    }




    @Test
    public void add_And_Remove_ElementsInRandomOrder_ShouldAlwaysMaintainHeapProperty() {
        heap.add(20);
        heap.add(5);
        heap.add(15);
        assertEquals("First removal should be the minimum element", Integer.valueOf(5), heap.remove());

        heap.add(10);
        assertEquals("Next removal should be the next minimum element", Integer.valueOf(10), heap.remove());

        // Continue this pattern to ensure the min-heap property is maintained
        assertEquals("Final removal should be in correct order", Integer.valueOf(15), heap.remove());
        assertEquals("Final removal should be in correct order", Integer.valueOf(20), heap.remove());
    }


    @Test
    public void add_ElementsWithMaximumValues_ShouldProcessWithoutError() {
        heap.add(Integer.MAX_VALUE);
        heap.add(Integer.MAX_VALUE - 1);

        assertEquals("Removal should return the smaller of the max values first", Integer.valueOf(Integer.MAX_VALUE - 1), heap.remove());
        assertEquals("Next removal should return the maximum integer value", Integer.valueOf(Integer.MAX_VALUE), heap.remove());
    }


    @Test
    public void remove_ElementsUntilEmptyThenAddMore_ShouldResetHeapCorrectly() {
        heap.add(10);
        heap.remove();
        heap.add(20);
        assertEquals("Heap should reset correctly after being emptied", Integer.valueOf(20), heap.get());
    }

    @Test
    public void iterator_HasNextOnNewHeapAfterClear_ShouldReturnFalse() {
        heap.add(10);
        heap.clear();
        Iterator<Integer> it = heap.iterator();
        assertFalse("Iterator should not have next on a new heap after clear", it.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void iterator_NextOnNewHeapAfterClear_ShouldThrowNoSuchElementException() {
        heap.add(10);
        heap.clear();
        Iterator<Integer> it = heap.iterator();
        it.next(); // Should throw NoSuchElementException
    }



    @Test
    public void size_AfterAddingAndRemovingSameElementMultipleTimes_ShouldBeAccurate() {
        heap.add(10);
        heap.remove();
        heap.add(10);
        assertEquals("Size should be accurate after adding and removing the same element multiple times", 1, heap.size());
    }

    @Test
    public void clear_OnHeapWithOneElement_ShouldAlsoClearInternalState() {
        heap.add(10);
        heap.clear();
        assertTrue("Clearing a heap with one element should also clear its internal state", heap.isEmpty());
    }

    @Test
    public void stressTest_AddRemoveOperations_ShouldHandleLargeNumberOfOperations() {
        for (int i = 0; i < 10000; i++) {
            heap.add(i);
        }

        Integer lastRemoved = Integer.MIN_VALUE;
        for (int i = 0; i < 10000; i++) {
            Integer removed = heap.remove();
            assertTrue("Removed element should be greater than or equal to the last removed element", removed.compareTo(lastRemoved) >= 0);
            lastRemoved = removed;
        }
    }


    @Test
    public void stressTest_IteratorValidityAfterIntensiveHeapOperations() {
        for (int i = 0; i < 1000; i++) {
            heap.add(i);
        }
        Iterator<Integer> iterator = heap.iterator();
        while (iterator.hasNext()) {
            assertNotNull("Iterator should return a valid element", iterator.next());
        }
    }

    @Test
    public void add_MinValueElement_ShouldHandleIntegerMinValue() {
        heap.add(Integer.MIN_VALUE);
        assertEquals("Heap should handle Integer.MIN_VALUE", Integer.valueOf(Integer.MIN_VALUE), heap.get());
    }

    @Test
    public void add_MaxValueElement_ShouldHandleIntegerMaxValue() {
        heap.add(Integer.MAX_VALUE);
        assertEquals("Heap should handle Integer.MAX_VALUE", Integer.valueOf(Integer.MAX_VALUE), heap.get());
    }

    @Test
    public void remove_MinValueElement_ShouldCorrectlyReorderHeap() {
        heap.add(Integer.MIN_VALUE);
        heap.add(0);
        heap.remove(); // Should remove Integer.MIN_VALUE
        assertEquals("Removing Integer.MIN_VALUE should correctly reorder heap", Integer.valueOf(0), heap.get());
    }

    @Test
    public void remove_MaxValueElement_ShouldCorrectlyReorderHeap() {
        heap.add(Integer.MAX_VALUE);
        heap.add(0);
        heap.remove(); // Should remove 0, as it's min heap
        assertEquals("Removing max value element should correctly reorder heap", Integer.valueOf(Integer.MAX_VALUE), heap.get());
    }

    @Test
    public void concurrency_MultipleThreadsAdding_ShouldMaintainHeapConsistency() throws InterruptedException {
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    heap.add(j);
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        Integer lastRemoved = Integer.MIN_VALUE;
        while (!heap.isEmpty()) {
            Integer removed = heap.remove();
            assertTrue("Removed element should be greater than or equal to the last removed element", removed.compareTo(lastRemoved) >= 0);
            lastRemoved = removed;
        }
    }


    @Test
    public void concurrency_MultipleThreadsRemoving_ShouldMaintainHeapConsistency() throws InterruptedException {
        int initialSize = 10000;
        for (int i = 0; i < initialSize; i++) {
            heap.add(i);
        }

        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    synchronized (heap) {
                        if (!heap.isEmpty()) {
                            heap.remove();
                        }
                    }
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }

        // Check if the heap size is as expected after all removals
        int expectedRemainingSize = Math.max(0, initialSize - 10000); // Adjust based on how many elements you expect to be removed
        assertEquals("Heap size should be as expected after concurrent removes", expectedRemainingSize, heap.size());
    }


    @Test
    public void concurrency_AddRemoveOperations_ShouldHandleConcurrentAccess() throws InterruptedException {
        Thread addThread = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                heap.add(i);
            }
        });
        Thread removeThread = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                synchronized (heap) {
                    if (!heap.isEmpty()) {
                        heap.remove();
                    }
                }
            }
        });
        addThread.start();
        removeThread.start();
        addThread.join();
        removeThread.join();

        // Check if the heap is not in a corrupted state (size check, basic integrity check)
        assertTrue("Heap should not be in a corrupted state", heap.size() >= 0);
        try {
            while (!heap.isEmpty()) {
                heap.remove(); // Remove all elements to ensure no exceptions are thrown
            }
        } catch (Exception e) {
            fail("Heap should not throw exceptions during removal after concurrent operations");
        }
    }










}







