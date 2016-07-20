package sizebst;

/**
 * Class SizeBST represents a Binary Search Tree that can also be used, for any integer j,
 *  to answer the question "how many numbers in the tree are less than or equal to j" in worst 
 *  case time h where h is the height of the tree (not the number of nodes).
 * 
 *  The actual nodes of the tree are of class SizeBSTN.  SizeBST represents the overall tree.
 *  IF instance variable rootNode is null, the tree is empty, otherwise rootNode is the root
 *  of the tree of SizeBSTN's
 * @author lou
 *
 */
public class SizeBST {

	public static void main(String[] args )
	{
//		Random rand = new Random();
//		int maxNum = 1000;
//		SizeBST tree1 = new SizeBST(null);
//		tree1.insert(40);
//		tree1.insert(20);
//		tree1.insert(60);
//		tree1.insert(10);
//		tree1.insert(30);
//		tree1.insert(50);
//		tree1.insert(55);
//		tree1.insert(53);
//		System.out.println(tree1);
//		System.out.println(tree1.numLEq(160));
		
//		for(int i = 0; i < 101; i++)
//		{
//			tree1.insert(rand.nextInt(maxNum));
//		}
//		
//		System.out.println("empty: "+tree1);
//		tree1.insert(40);
//		System.out.println(tree1);
//		
//		tree1.insert(32);
//		System.out.println(tree1);
//		tree1.insert(69);
//		
//		System.out.println(tree1);
//		tree1.insert(30);
//		System.out.println(tree1);
//		tree1.insert(24);
//		System.out.println(tree1);
//		tree1.insert(10);
//		System.out.println(tree1);
//		tree1.insert(25);
//		System.out.println(tree1);		
//		tree1.insert(100);
//		System.out.println(tree1);
//		
//		
//		tree1.insert(41);
//		System.out.println(tree1);
//		tree1.insert(33);
//		System.out.println(tree1);
//		tree1.insert(36);
//		System.out.println(tree1);
//		tree1.insert(45);
//		System.out.println(tree1);
//		tree1.insert(44);
//		System.out.println(tree1);
//		tree1.insert(65);
//		System.out.println(tree1);
//		tree1.insert(64);
//		System.out.println(tree1);
//		tree1.insert(62);
//		System.out.println(tree1);
//		
//		System.out.println(tree1.numLEq(40));
//		System.out.println(tree1.search(43));

	
	}
	
	SizeBSTN rootNode;

	public SizeBST(SizeBSTN root)
	{
		rootNode =  root;
	}

	public String toString(){
		if (rootNode == null)
		{
			return "(null)";
		}
		else 
		{
			return "(" + SizeBSTN.nodeString(rootNode) + ")";
		}
	}

	/**
	 * @param target the number to search for
	 * @return true iff target is in this tree
	 */
	public boolean search(int target)
	{
		SizeBSTN found =  SizeBSTN.getNode(this.rootNode, target);
		if(found.data == target)
		{
			return true;
		}
		
		return false;
	}

	/**
	 * insert newData into tree;  if already there, do not change tree
	 * @param newData int to insert
	 */
	public void insert(int newData)
	{
		if(this.rootNode == null)
		{
			this.rootNode = new SizeBSTN(newData);
			return;
		}
		SizeBSTN found =  SizeBSTN.getNode(this.rootNode, newData);
		if(found.data == newData)
		{
			return;
		}
		else
		{
			if(found.data > newData)
			{
				found.LSubtree = new SizeBSTN(newData);
				SizeBSTN.getNodeIncr(this.rootNode, newData);
			}
			else
			{
				found.RSubtree = new SizeBSTN(newData);
				SizeBSTN.getNodeIncr(this.rootNode, newData);
				
			}
		}
	}

	/**
	 * @return returns how many numbers in the tree are less than or equal to target.  Returns -1 if tree is empty
	 * @param target
	 */
	public int numLEq(int target)
	{
		return SizeBSTN.sumNodesLeq(this.rootNode, target);
	}

}
