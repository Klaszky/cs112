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
	
	public static void main(String[] args ){
		SizeBST tree1 = new SizeBST(null);
		System.out.println("empty: "+tree1);
		tree1.insert(40);
		System.out.println("40 "+tree1);
		System.out.println(tree1);
		
		tree1.insert(32);
		System.out.println(tree1);
		tree1.insert(69);
		
		System.out.println(tree1);
		tree1.insert(30);
		System.out.println(tree1);
		tree1.insert(24);
		System.out.println(tree1);
		tree1.insert(10);
		System.out.println(tree1);
		tree1.insert(25);
		System.out.println(tree1);		
		tree1.insert(100);
		System.out.println(tree1);
		
		
		System.out.println(tree1.numLEq(99));
		System.out.println(tree1.search(43));
		
	}
	
	SizeBSTN rootNode;

	public SizeBST(SizeBSTN root){
		rootNode =  root;
	}

	public String toString(){
		if (rootNode == null)
			return "(null)";
		else {
			return "("+ SizeBSTN.nodeString(rootNode) + ")";
		}
	}

	/**
	 * @param target the number to search for
	 * @return true iff target is in this tree
	 */
	public boolean search(int target){
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
	public void insert(int newData){
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
			}
			else
			{
				found.RSubtree = new SizeBSTN(newData);
			}
		}
	}

	/**
	 * @return returns how many numbers in the tree are less than or equal to target.  Returns -1 if tree is empty
	 * @param target
	 */
	public int numLEq(int target){
		return SizeBSTN.sumNodesLeq(this.rootNode, target);
	}

}
