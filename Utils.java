package foo;

import static java.util.Map.Entry;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.List;

import java.util.Random;

public class Utils
{
	public static String arrayToString(int[] nums, int maxIdx)
	{
		StringBuilder sb = new StringBuilder("[");
		int max = maxIdx < nums.length ? maxIdx : nums.length;
		
		for (int i = 0; i < max; ++i)
			sb.append(nums[i]).append(",");
		return sb.append("]").toString();
	}
	
	public static int[] genNums(int count)
	{
		int[] nums = new int[count];
		Random rand = new Random(System.currentTimeMillis());
		int max = count / 1; //max num is 10% of the number of num; just to make sure there're dupes
		
		for (int i = 0; i < count; ++i)
			nums[i] = rand.nextInt(max);
		
		return nums;
	}
	
	public static Map<Integer, Integer> getFreqMap(int[] nums)
	{
		Map<Integer, Integer> ret = new HashMap<>();
		for (int num : nums)
		{
			if (!ret.containsKey(num))
				ret.put(num, 1);
			else
				ret.put(num, ret.get(num) + 1);
		}
		return ret;
	}
	
	
	public static void doSortByFreq(List<Entry<Integer, Integer>> entries, int i, int j)
	{
		if (i < j)
		{
			int piv = custPartition(entries, i, j);
			doSortByFreq(entries, i, piv - 1);
			doSortByFreq(entries, piv + 1, j);
		}
	}
		
	public static void countSort(List<Entry<Integer, Integer>> entries, int i, int j, int k)
	{
		if (i < j) 
		{
            int pos = custPartition(entries, i, j);
			if (pos == k - 1)
				return;
			else if (pos >= k) //has more than required => only need considering left partition
				countSort(entries, i, pos - 1, k);
			else //not have enough; need some more till k => only need considering right partition
				countSort(entries, pos + 1, j, k);
        }
	}
	
    public static int custPartition(List<Entry<Integer, Integer>> entries, int i, int j) 
	{
        int pivot = entries.get(j).getValue(); 
        int newPivIdx = i - 1; //swap pos - the pos that piv will eventually end  up at

        for(int k = i; k < j; k++) 
		{
            if(entries.get(k).getValue() > pivot) 
			{
                newPivIdx++;
                custSwap(entries, k, newPivIdx);
            }
        }

		//Swap the pivot to its final pos
        custSwap(entries, j, newPivIdx + 1);
        return newPivIdx + 1; //final pos of piv
    }
	
	public static void custSwap(List<Entry<Integer, Integer>> entries, int i, int j)
	{
		Entry tmp = entries.get(i);
		entries.set(i, entries.get(j));
		entries.set(j, tmp);
	}
	
	//Sorting in non decreasing order
    public static void quickSort(int[] arr, int i, int j) 
	{
        if(i < j) 
		{
            int pos = partition(arr, i, j);
            quickSort(arr, i, pos - 1);
            quickSort(arr, pos + 1, j);
        }
    }
	
    public static int partition(int[] arr, int i, int j) 
	{
        int pivot = arr[j]; 
        int newPivIdx = i - 1; //swap pos - the pos that piv will eventually end  up at

        for(int k = i; k < j; k++) 
		{
            if(arr[k] > pivot) 
			{
                newPivIdx++;
                swap(arr, k, newPivIdx);
            }
        }

		//Swap the pivot to its final pos
        swap(arr, j, newPivIdx + 1);
        return newPivIdx + 1; //final pos of piv
    }

    public static void swap(int[] arr, int a, int b)
	{
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

	/**
	 * Sort in DECREASING order
	 */
	public static void mergeSort(int[] nums, int start, int end)
	{
		if (start >= end)
		{
			return; //nothing to do; base case
		}
		
		if (start + 1 == end) //only 2 element
		{
			if (nums[start] < nums[end])
			{
				swap(nums, start, end);
			}
			return;
		}
		
		int[] ret = new int[nums.length];
		
		//Sub-sort		
		int idx = start, leftIdx = start, rightIdx = start + (end - start) / 2 + 1;
		int rightStart = rightIdx;
		mergeSort(nums, start, rightIdx - 1);
		mergeSort(nums, rightIdx, end);
		
		//Merge the two sub-arays
		int tmp; //buffer
		System.out.printf("\nMerging range [%d, %d] (", start, rightStart - 1);
		for (int i = start; i < rightIdx; ++i)
			System.out.printf("%d, ", nums[i]);
		System.out.printf(") and range [%d, %d] (", rightStart, end);
		for (int i = rightStart; i <= end; ++i)
			System.out.printf("%d, ", nums[i]);
		
		while (idx <= end && leftIdx <= rightStart - 1 && rightIdx <= end)
		{
			while (leftIdx <= rightStart - 1 && nums[leftIdx] > nums[rightIdx])
			{
				ret[idx] = nums[leftIdx];
				++leftIdx;
				++idx;
			}
			
			while (rightIdx <= end && nums[rightIdx] >= nums[leftIdx])
			{
				ret[idx] = nums[rightIdx];
				++rightIdx;			
				++idx;	
			}
		}
		
		for (int i = start; i <= end; ++i)
			nums[i] = ret[i];
	}
	
	
}