import java.util.*;

public class LongestSubStringFinder
{
	public static void main (String[] args)
	{
		String str = genString(Integer.parseInt(args[0]));
		
		long bfTime = System.currentTimeMillis();
		String bf = longestSubBruteforce(str);
		bfTime = System.currentTimeMillis() - bfTime;
		
		
		long wTime = System.currentTimeMillis();
		String window = longestSubWindow(str);
		wTime = System.currentTimeMillis() - wTime;
		
		long wTime2 = System.currentTimeMillis();
		String window2 = longestSubWindow2(str);
		wTime2 = System.currentTimeMillis() - wTime2;
		
		System.out.printf("String = %s\nLongest w/o dupe = %s; Took %d ms\nWindow2 = %s; Took %d ms\nBrute force = %s; Took %d ms\n",
						str,
						window, wTime,
						window2, wTime2,
						bf, bfTime);
	}
	
	
     public static String longestSubWindow2(String s) 
	 {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
		int maxI = 0, maxJ = 0; 
        while (i < n && j < n) 
		{
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j)))
			{
                set.add(s.charAt(j++));
				if (j - i > ans)
				{
					ans = j - i;
					maxI = i;
					maxJ = j;
				}
                //ans = Math.max(ans, j - i);
            }
            else 
			{
                set.remove(s.charAt(i++));
            }
        }
        return s.substring(maxI, maxJ);
    }
	
	/**
	 * Bruteforce approach
	 */
	private static String longestSubBruteforce(String str)
	{
		int max = 0, len, maxIdx = 0;
		for (int i = 0; i < str.length(); ++i)
		{
			len = longestLen(str, i);
			if (len > max)
			{
				max = len;
				maxIdx = i;
			}
		}
		
		return str.substring(maxIdx, maxIdx + max);
	}
	
	/*
	 * Length of longest substr w/o dupes starting at this pos
	 * Bruteforce
	 */
	private static int longestLen(String str, int startChar)
	{
		int len = 0;
		Set<Character> chars = new HashSet<Character>();
		for (int i = startChar; i < str.length() && !chars.contains(str.charAt(i)); ++i)
		{
			++len;
			chars.add(str.charAt(i));
		}		
		return len;	
	}
	
	
	private static String genString(int len)
	{
		Random rand = new Random(System.currentTimeMillis());
		//int len = rand.nextInt(maxLen) + 10; //at least 10
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; ++i)
			sb.append((char)(rand.nextInt(26) + 'a'));
		
		return sb.toString();
	}
}