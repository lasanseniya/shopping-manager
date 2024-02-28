package utilities;

import models.Product;
import java.util.List;

/**
 * Implements the sorting algorithm to be used in the program.
 * Algorithm used: Quick Sort algorithm
 * Time complexity: O(n log n)
 *`
 * ________________________________________________________________________________________________
 * References: // <a href="https://www.programiz.com/java-programming/examples/quick-sort">...</a>
 *             // <a href="https://youtu.be/Vtckgz38QHs?si=JgPDwimm7DM04opD">...</a>
 * ________________________________________________________________________________________________
 *
 */
public class QuickSort {
    /**
     * This method sorts the array using the quick sort algorithm
     *`
     * @param array List of products to be sorted (Type: List)
     * @param start Starting index of the array (Type: int)
     * @param end Ending index of the array (Type: int)
     * @return The sorted array (Type: List)
     */
    public static List<Product> quickSort(List<Product> array, int start, int end){

        if (end <= start) {  // Check whether the array is empty or has only one element
            return array;
        }

        int pivot = partition(array, start, end);  // finding the pivot

        quickSort(array, start, pivot - 1);  // sorting the left side of the pivot

        quickSort(array, pivot + 1, end);  // sorting the right side of the pivot

        return array;  // returning the sorted array
    }

    /**
     * This method finds the pivot and sorts the array
     *`
     * @param array List of products to be sorted (Type: List)
     * @param start Starting index of the array (Type: int)
     * @param end Ending index of the array (Type: int)
     * @return The location of the pivot (Type: int)
     */
    public static int partition(List<Product> array, int start, int end){

        String pivot = array.get(end).getProductID(); // pivot is the last element of the array

        int i  = start - 1;  // pointer to keep track of the last element

        for (int j = start; j <= end; j++){
            if (array.get(j).getProductID().compareTo(pivot) < 0){
                i++;

                // swapping the smaller element with the current element
                Product temp = array.get(i);  // temp = smaller element
                array.set(i, array.get(j));  // smaller element = current element
                array.set(j, temp);  // current element = temp
            }

        }

        // swapping the pivot with the smaller element of the array
        i++;
        Product temp = array.get(i);
        array.set(i, array.get(end));
        array.set(end, temp);

        return i;  // location of the pivot
    }
}