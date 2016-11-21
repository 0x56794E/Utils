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
		
		@Override
		public String toString()
		{
			return Integer.toString(value);
		}
		
		//Use default equals and hashcode??
		@Override
		public boolean equals(Object obj)
		{
			if (!(obj instanceof Node))
				return false;
			
			Node that = (Node) obj;
			
			return that.value == this.value
					&& (that.parent == null ? this.parent == null : that.parent.value == this.parent.value)
					&& (that.left == null ? this.left == null : that.left.value == this.left.value)
					&& (that.right == null ? this.right == null : that.right.value == this.right.value);
		}
		
		@Override
		public int hashCode()
		{
			int hash = 17;
			hash = hash * 31 + value;
			
			if (parent != null)
				hash = hash * 31 + parent.value;
			
			if (left != null)
				hash = hash * 31 + left.value;
			
			if (right != null)
				hash = hash * 31 + right.value;
			
			return hash;
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
	
	private static class SpaceCounter implements Action
	{
		private static class SpaceData
		{
			public final Node node;
			private int spaceCount;
			
			public SpaceData parent;
			public SpaceData left;
			public SpaceData right;
			
			public SpaceData(Node node)
			{
				this.node = node;
			}
			
			public void setSpaceCount(int spaceCount)
			{
				this.spaceCount = spaceCount;
			}
			
			public int getSpaceCount()
			{
				return spaceCount;
			}
		}
		private Map<Integer, List<Node>> levelMap = new HashMap<Integer, List<Node>>();
		
		@Override
		public void doThing(Node node)
		{
			//Need to determine how many spaces ea node need.
			int h = node.height();
			
			if (!levelMap.containsKey(h))
				levelMap.put(h, new ArrayList<Node>());
			
			levelMap.get(h).add(node);
		}
		
		
		
		/* 
		UNIT = 6. => longest row needs 8 * 6 spaces 
		maxHeight = 3 => 2^3 = 8 nodes  (0-based index)
		
		=> height 0 has 1 nodes => 8 * UNIT / 2
		
		=> height = 1 has 2 nodes => First node: 8*UNIT / 2^(1+1)
		=> height = 2: First node: 
		
						9
			5						15
	4			6			14			16
a		b	c		8	x		y	z		q
		
		
		 */
		public void print()
		{
			int maxHeight = Collections.max(levelMap.keySet());
			Map<Node, Integer> spaceMap = new HashMap<Node, Integer>();
			
			//TODO: similar to dfs but do not pop while traversing cuz need to know how many spaces needed
			//Level n has 2^n nodes at most => need 6*2^n space		
			//So if a tree has n levels => the root need to have 6 * 2 ^ (n-1) spaces to the left
			//Suppose parent have 6 to the left
			//Left Child = parent - 3
			//Left Child = parent + 3
			
			//Root node
			int spaceCount = 6 * (int) Math.pow(2, maxHeight - 1);
			spaceMap.put(levelMap.get(0).get(0), spaceCount);
			
			System.out.printf("%" + spaceCount + "s\n", levelMap.get(0).get(0));
			
			for (int height = 1; height <= maxHeight; ++height)
			{
				List<Node> nodes = levelMap.get(height);
				
				//Left most child:			
				spaceCount = spaceMap.get(nodes.get(0).getParent());
				spaceCount = nodes.get(0).value < nodes.get(0).getParent().value 
								? spaceCount - 3 //is left child
								: spaceCount + 3; //is right child
				
				System.out.printf("%" + (spaceCount == 0 ? "" : spaceCount) + "s", nodes.get(0));
				spaceMap.put(nodes.get(0), spaceCount);
				
				for (int i = 1; i < nodes.size(); ++i)
				{
					spaceCount = spaceMap.get(nodes.get(i).getParent());
					spaceCount = nodes.get(i).value < nodes.get(i).getParent().value 
								? 0 //is left child
								: 6; //is right child
								
					spaceMap.put(nodes.get(i), spaceCount);
					System.out.printf("%" + (spaceCount == 0 ? "" : spaceCount) + "s", nodes.get(i));
				}
				
				System.out.println();
			}
		}		
	}
		
	public void printTree()
	{
		SpaceCounter action = new SpaceCounter();
		
		//Categorize node by level/height
		dfs(action);
		action.print();
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
	
	
	private static void testHashCode()
	{
		
		//Testing hashcode
		Node n1 = new Node(5);
		Node n2 = new Node(15);
		Node n3 = new Node(5);
		Node n4 = new Node(4);
		Node n5 = new Node(8);
		Node n6 = new Node(10);
		Node n7 = new Node(16);
		Node n8 = new Node(16);
		
		System.out.printf("Hash code of %s is %d\n", n1, n1.hashCode());
		System.out.printf("Hash code of %s is %d\n", n2, n2.hashCode());
		System.out.printf("Hash code of %s is %d\n", n3, n3.hashCode());
		System.out.printf("Hash code of %s is %d\n", n4, n4.hashCode());
		System.out.printf("Hash code of %s is %d\n", n5, n5.hashCode());
		System.out.printf("Hash code of %s is %d\n", n6, n6.hashCode());
		System.out.printf("Hash code of %s is %d\n", n7, n7.hashCode());
		System.out.printf("Hash code of %s is %d\n", n8, n8.hashCode());
		
		n1.setLeft(n3);
		n1.setRight(n2);
		n2.setParent(n1);
		n3.setParent(n1);
		n3.setLeft(n4);
		n4.setParent(n3);
		n2.setLeft(n5);
		n5.setParent(n2);
		n5.setRight(n6);
		n6.setParent(n5);
		n2.setRight(n7);
		n7.setParent(n2);
		n8.setParent(n7);
		n7.setLeft(n8);
		
		System.out.printf("\nHash code of %s is %d\n", n1, n1.hashCode());
		System.out.printf("Hash code of %s is %d\n", n2, n2.hashCode());
		System.out.printf("Hash code of %s is %d\n", n3, n3.hashCode());
		System.out.printf("Hash code of %s is %d\n", n4, n4.hashCode());
		System.out.printf("Hash code of %s is %d\n", n5, n5.hashCode());
		System.out.printf("Hash code of %s is %d\n", n6, n6.hashCode());
		System.out.printf("Hash code of %s is %d\n", n7, n7.hashCode());
		System.out.printf("Hash code of %s is %d\n", n8, n8.hashCode());
	}
	
	public static void main(String[] args)
	{
	
		BST tree = new BST(9);
		tree.add(5);
		tree.add(15);
		tree.add(4);
		tree.add(6);
		tree.add(8);
		tree.add(16);
		
		
		//testHashCode();
		tree.printTree();
		
/*
		System.out.printf("Node %d has height %d\n", 9, tree.findHeight(9));
		System.out.printf("Node %d has height %d\n", 0, tree.findHeight(0));
		System.out.printf("Node %d has height %d\n", 4, tree.findHeight(4));
		System.out.printf("Node %d has height %d\n", 6, tree.findHeight(6));
		System.out.printf("Node %d has height %d\n", 8, tree.findHeight(8));
		System.out.printf("Node %d has height %d\n", 10, tree.findHeight(10));
		System.out.printf("Node %d has height %d\n", 15, tree.findHeight(15));
*/
	}
}
