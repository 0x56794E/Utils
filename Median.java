public class Median
{
	
	public static void main(String[] args)
	{
		int[] nums1 = {1, 3};
		int[] nums2 = {2};
		
		System.out.println("Median is " + median(nums1, nums2));
	}
	
	private static double median2(int[] a, int[] b)
	{
		int aLen = a.length;
		int bLen = b.length;
		
		int aMed = (aLen % 2 == 0 
					? 1.0 * (a[aLen/2] + a[aLen/2 - 1]) / 2 
					: a[aLen/2];
		int bMed = (bLen % 2 == 0 
					? 1.0 * (b[bLen/2] + b[bLen/2 - 1]) / 2 
					: b[bLen/2];
					
		if (aMed == bMed) return aMed;
		if (aMed < bMed)
		{
			//Left of a and right of b are final
			//med lies btw [med(a), med(b)]
			
		}
		else
		{
			//Right of a
		}
		
	}
	
	//Binary search
	private static double findMedian(int[] a, int startA, int endA, int[] b, int startB, int endB)
	{
		double aMed, bMed;
		
		if (endA - startA <= 1) //base case: only have 2 or less
			aMed = 1.0 * (a[startA] + a[endA]) / 2;
		
		if (endB - startB <= 1)
			bMed = 1.0 * (b[startB] + b[endB]) / 2;
		
		//Determine ther ange
	}
	
	/**
	 * nums1 and nums2 are sorted
	 */
	private static double median(int[] nums1, int[] nums2)
	{
		int size = nums1.length + nums2.length;
		int[] tmp = new int[size];
		
		int leftIdx = 0, rightIdx = 0;
		for (int i = 0; i < size; ++i)
		{
			if (rightIdx >= nums2.length
				|| leftIdx < nums1.length && nums1[leftIdx] <= nums2[rightIdx])
			{
				tmp[i] = nums1[leftIdx];
				++leftIdx;
			}
			else if (leftIdx >= nums1.length
					 || rightIdx < nums2.length && nums2[rightIdx] <= nums1[leftIdx])
			{
				tmp[i] = nums2[rightIdx];
				++rightIdx;
			}
		}
		
		int idx = size / 2;
		if (size % 2 == 0) //is even => avg of mid 2
		{
			return (tmp[idx] + tmp[idx - 1]) * 1.0 / 2;
		}
		else
			return tmp[idx];
	}
}