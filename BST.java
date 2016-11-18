import java.util.*;

public class BST
{
	private static class Node
	{
		public final int value;
		private Node left;
		private Node right;
		private Node parent;
		
		//For prinint
		private int spacesToLeft = 0;
		
		public Node(int value)
		{
			this.value = value;
		}
		
		public void setParent(Node parent)
		{
			this.parent = parent;
		}
		
		public void setLeft(Node left)
		{
			this.left = left;
		}
		
		public void setRight(Node right)
		{
			this.right = right;
		}
				
		public Node getParent()
		{
			return parent;
		}
		
		public Node getLeft()
		{
			return left;
		}
				
		public Node getRight()
		{
			return right;
		}
		
		public int height()
		{
			Node parent = getParent();
			int height = 0;
			
			while (parent != null)
			{
				parent = parent.getParent();
				++height;
			}
			 
			return height;
		}
		
		public String toString()
		{
			return Integer.toString(value);
		}
	}
	
	private static interface Action
	{
		void doThing(Node node);
	}
	
	private Node root;
	private int nodeCount;
	
	public BST (int root)
	{
		this.root = new Node(root);
		nodeCount = 0;
	}
	
	/**
	 * Return the height of the first node with given value;
	 */
	public int findHeight(int num)
	{
		//Find the node with given value
		Node iter = root;
		do
		{
			if (num == iter.value)
				return iter.height();
			else if (num > iter.value) //search right portion
				iter = iter.right;
			else
				iter = iter.left;
				
		}
		while (iter != null);
		
		return -1; //Can't find
	}
	
	//If same val with parent => add to left
	public void add(int i)
	{
		++nodeCount;
		Node node = new Node(i);
		Node iter, next = root;
		int height = 0;
		boolean setLeft;
		
		do
		{
			iter = next;
			if (i > iter.value) //search right portion
			{				
				next = iter.getRight();		
				setLeft = false;
			}
			else //search left portion
			{
				next = iter.getLeft();
				setLeft = true;
			}	
			height++;
		}
		while (next != null);
		
		//Reach leaf
		node.setParent(iter);
		if (setLeft)
		{
			iter.setLeft(node);
		}
		else
		{
			iter.setRight(node);
		}				
	}
	
	public void printTree()
	{
		//TODO: similar to dfs but do not pop while traversing cuz need to know how many spaces needed
		//Level n has 2^n nodes at most => need 5*2^n space		
		//So if a tree has n levels => the root need to have 5 * 2 ^ (n-1) spaces to the left
		//Suppose parent have 5 to the left
		//Left Child = parent - 2.5
		//Left Child = parent + 2.5
		
		Map<Integer, List<Node>> levelMap = new HashMap<Integer, List<Node>>();
		dfs(new Action()
		{
			private Map<Integer, List<Node>> map = null;
			
			@Override
			public void doThing(Node node)
			{
				//Need to determine how many spaces ea node need.
				int h = node.height();
				
				if (!map.containsKey(h))
					map.put(h, new ArrayList<Node>());
				
				map.get(h).add(node);
				
			}
			
			private Action init(Map<Integer, List<Node>> map)
			{
				this.map = map;
				return this;
			}
		}.init(levelMap));
		/*
		for (int h = 0; h < levelMap.size(); ++h)
		{
			if (levelMap.containsKey(h))
			{
				int padding = node.value > node.getParent().value()
								? 2.5
								- 2.5;
				int space = 5 * Math.pow(2, h - 2) + padding;
				
				
				//printinf
				for (int i = 0; i < space; ++i)
					System.out.print(" ");
				System.out.print
				
				
				
				//End of ea level:
				System.out.println();
				
			}
		}
		*/
	}
	
	/**
	 * Do BFS
	 * Also left to right
	 */
	public void bfs(Action action)
	{
		Deque<Node> queue = new ArrayDeque<Node>();
		queue.add(root);
		Node iter;
		int visitOrder = 0;
		
		//FIFO
		while (!queue.isEmpty())
		{
			iter = queue.pop(); //Pop front - first element
			action.doThing(iter);
			
			if (iter.getLeft() != null)
				queue.add(iter.getLeft()); //add to tail
			
			if (iter.getRight() != null)
				queue.add(iter.getRight()); //add to tail
		}
	}
	
	/**
	 * In-order DFS 
	 * Left-Parent-Right
	 */
	public void dfs(Action action)
	{
		Deque<Node> stack = new ArrayDeque<Node>();
		Node iter = root;

		//LIFO
		while (!stack.isEmpty() || iter != null)
		{		
			//Going left
			if (iter != null)
			{
				stack.push(iter); //push on top of stack
				iter = iter.getLeft();
			}
			else //Reach left most
			{
				iter = stack.pop(); //remove front
				action.doThing(iter);
				iter = iter.getRight();
			}
		}
	}
	
	public static void main(String[] args)
	{
		BST tree = new BST(9);
		tree.add(5);
		tree.add(15);
		tree.add(4);
		tree.add(6);
		tree.add(8);
		tree.add(10);
		
//		tree.printTree();
		System.out.printf("Node %d has height %d\n", 9, tree.findHeight(9));
		System.out.printf("Node %d has height %d\n", 0, tree.findHeight(0));
		System.out.printf("Node %d has height %d\n", 4, tree.findHeight(4));
		System.out.printf("Node %d has height %d\n", 6, tree.findHeight(6));
		System.out.printf("Node %d has height %d\n", 8, tree.findHeight(8));
		System.out.printf("Node %d has height %d\n", 10, tree.findHeight(10));
		System.out.printf("Node %d has height %d\n", 15, tree.findHeight(15));
	}
}
