public class Adder
{

	private static LinkedList<Integer> add(LinkedList<Integer> num1, LinkedList<Integer> num2)
	{
		LinkedList<Integer> result = new LinkedList<Integer>();
		if (num1.size() > num2.size())
			doAdd(num2, num1, result);
		else
			doAdd(num1, num2, result);
		
		return result;
	}
	
	private static void doAdd(LinkedList<Integer> shortNum, LinkedList<Integer> longNum, LinkedList<Integer> result)
	{
		int carry = 0, sum;
		while (!shortNum.isEmpty())
		{
			sum = shortNum.poll() + longNum.poll() + carry;
			if (sum >= 10)
			{
				result.add(sum % 10);
				carry = sum / 10;
			}
			else
			{
				result.add(sum);
				carry = 0;
			}
		}
		
		while (!longNum.isEmpty())
		{
			sum = longNum.poll() + carry;
			if (sum >= 10)
			{
				result.add(sum % 10);
				carry = sum / 10;
			}
			else
			{
				result.add(sum);
				carry = 0;
			}
		}
		
		//If there's still carry
		if (carry > 0)
			result.add(carry);
	}
}