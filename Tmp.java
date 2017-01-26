import java.util.*;

public class Tmp
{
	public static void main(String[] args)
	{
	    findMaxSubContArray(args);
	}


    private static void findMaxSubContArray(String[] args)
    {
	if (args.length != 2)
	{
	    System.out.println("Invalid number of args. Usage Foo <length of array> <max value>");
	    System.exit(1);
	}

	int max = Integer.MIN_VALUE, maxStartIdx = 0, maxLen = 0;
	int numCount = Integer.parseInt(args[0]);
	int[] nums = genNums(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

	//Col reps the index the sub array starts at
	//Row reps the len; row[0] == len 1; ... row[n-1] == len n
	int sumTable[][] = new int[numCount][numCount];

	for (int col = 0; col < numCount; ++col)
        {
	    sumTable[0][col] = nums[col];
	    if (max < nums[col])
	    {
		max = nums[col];
		maxStartIdx = col;
		maxLen = 1;
	    }
	}


	for (int row = 1; row < numCount; ++row)
	{
	    for (int col = 0; col < numCount; ++col)
	    {
		if (col + row < numCount)
		{
		    sumTable[row][col] = sumTable[row - 1][col] + nums[col + row];
		    if (max < sumTable[row][col])
		    {
			max = sumTable[row][col];
			maxStartIdx = col;
			maxLen = row + 1;			  
		    }
		}		
	    }
	}

	System.out.printf("Array: ");
	for (int i = 0; i < nums.length; ++i)
	    System.out.printf("%d, ", nums[i]);

	System.out.printf("\nSubarray with largest sum = %d; startIdx = %d; len = %d\n", max, maxStartIdx, maxLen);
    }

    private static int[] genNums(int ct, int maxValue)
    {
	int[] nums = new int[ct];
	Random rand = new Random(System.currentTimeMillis());
	Random signRand = new Random(System.currentTimeMillis());

	for (int i = 0; i < ct; ++i)
	    nums[i] = rand.nextInt(maxValue) * (signRand.nextInt(100) % 2 == 1 ? -1 : 1);

	return nums;
    }

	private static void uniqueCommonProb(String[] args)
	{
	    System.out.println("Arg len = " + args.length);

		if (args.length != 2)
		{
			System.out.println("Must provide 2 strings");
			System.exit(1);
		}

		doFind(args[0], args[1]);
	}


	private static void doFind(String str1, String str2)
	{
		Set<Character> common = new HashSet<Character>();
		Set<Character> unique = new HashSet<Character>();

		//O(n)
		for (int iter = 0; iter < str1.length(); ++iter)
			unique.add(str1.charAt(iter));

		//O(n)
		char c;
		for (int iter = 0; iter < str2.length(); ++iter)
		{
			c = str2.charAt(iter);
			if (unique.contains(c))
			{
				unique.remove(c);
				common.add(c);
			}
		}

		//=>Total: O(n)
		System.out.printf("Common: %s\n", common);
		System.out.printf("Unique: %s\n", unique);

	}

    private static void balanceParenProb(String[] args)
    {
	if (args.length != 1)
        {
	    System.out.println("Must provide one string");
	    System.exit(1);
	}

	String str = args[0];
	Deque<Character> stack = new ArrayDeque<Character>();
	for (int iter = 0; iter < str.length(); ++iter)
	{
	    if (str.charAt(iter) == '(')
		stack.push('(');
	    else if (str.charAt(iter) == ')')
	    {
		if (stack.size() > 0)
		    stack.pop();
		else
		{
		    stack.push('(');
		    break;
		}
	    }
	}

	System.out.printf("Balance? %s\n", stack.isEmpty());
    }

    private static void implStack()
    {
	MyStack<Integer> stack = new MyStack<Integer>();
	stack.push(0);
	stack.push(1);
	stack.push(2);

	System.out.println(stack.pop());
	System.out.println(stack.pop());
	System.out.println(stack.pop());
    }


    private static class MyStack<E>
    {

	Queue<E> queue = new ArrayDeque<E>();

	/*
	  keep one queue but as soon as a new element goes in rmove the thead and add it to the tail
	  keep goi9ng 
	  
	  so for more than one-=L:bascially as soon as we add a new element => remove ALL element before it and andd to the mndn fo teh que.f
         */
	    
	public void push(E e)
	{
	    
	    int size = queue.size();
	    queue.add(e);
	    for (int i = 0; i < size; ++i)
	     {
		 E tmp = queue.remove();
		 queue.add(tmp);
	     }
	}

	public E pop()
	{
	    return queue.remove();
	}
    }

}
