package poly;

import java.io.*;
import java.util.StringTokenizer;

/**
 * This class implements a term of a polynomial.
 * 
 * @author runb-cs112
 *
 */
class Term {
	/**
	 * Coefficient of term.
	 */
	public float coeff;
	
	/**
	 * Degree of term.
	 */
	public int degree;
	
	/**
	 * Initializes an instance with given coefficient and degree.
	 * 
	 * @param coeff Coefficient
	 * @param degree Degree
	 */
	public Term(float coeff, int degree) {
		this.coeff = coeff;
		this.degree = degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		return other != null &&
		other instanceof Term &&
		coeff == ((Term)other).coeff &&
		degree == ((Term)other).degree;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (degree == 0) {
			return coeff + "";
		} else if (degree == 1) {
			return coeff + "x";
		} else {
			return coeff + "x^" + degree;
		}
	}
}

/**
 * This class implements a linked list node that contains a Term instance.
 * 
 * @author runb-cs112
 *
 */
class Node {
	
	/**
	 * Term instance. 
	 */
	Term term;
	
	/**
	 * Next node in linked list. 
	 */
	Node next;
	
	/**
	 * Initializes this node with a term with given coefficient and degree,
	 * pointing to the given next node.
	 * 
	 * @param coeff Coefficient of term
	 * @param degree Degree of term
	 * @param next Next node
	 */
	public Node(float coeff, int degree, Node next) {
		term = new Term(coeff, degree);
		this.next = next;
	}
}

/**
 * This class implements a polynomial.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Pointer to the front of the linked list that stores the polynomial. 
	 */ 
	Node poly;
	
	/** 
	 * Initializes this polynomial to empty, i.e. there are no terms.
	 *
	 */
	public Polynomial() {
		poly = null;
	}
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param br BufferedReader from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 */
	public Polynomial(BufferedReader br) throws IOException {
		String line;
		StringTokenizer tokenizer;
		float coeff;
		int degree;
		
		poly = null;
		
		while ((line = br.readLine()) != null) {
			tokenizer = new StringTokenizer(line);
			coeff = Float.parseFloat(tokenizer.nextToken());
			degree = Integer.parseInt(tokenizer.nextToken());
			poly = new Node(coeff, degree, poly);
		}
	}
	
	
	/**
	 * Returns the polynomial obtained by adding the given polynomial p
	 * to this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial to be added
	 * @return A new polynomial which is the sum of this polynomial and p.
	 */
	public Polynomial add(Polynomial p) {
		
		// This polynomial will be with is returned to the calling method
		Polynomial returnFront = new Polynomial();
		
		// Grabbing this node to work with so I don't accidentally delete anything.
		Node front = this.poly;
		
		
		while(front != null && p.poly != null)
		{
			if(front.term.degree == p.poly.term.degree)
			{
				if((front.term.coeff + p.poly.term.coeff) != 0)
				{
					returnFront.poly = addToEnd(returnFront.poly, new Node(front.term.coeff + p.poly.term.coeff, front.term.degree, null));
					front = front.next;
					p.poly = p.poly.next;	
				}
				else
				{
					front = front.next;
					p.poly = p.poly.next;
				}
			}
			else if(front.term.degree > p.poly.term.degree)
			{
				returnFront.poly = addToEnd(returnFront.poly, new Node(p.poly.term.coeff, p.poly.term.degree, null));
				p.poly = p.poly.next;
				
			}
			else
			{
				returnFront.poly = addToEnd(returnFront.poly, new Node(front.term.coeff, front.term.degree, null));
				front = front.next;
			}
		}

		while(p.poly != null)
		{
			returnFront.poly = addToEnd(returnFront.poly, new Node(p.poly.term.coeff, p.poly.term.degree, null));
			p.poly = p.poly.next;
		}
		
		while(front != null)
		{
			returnFront.poly = addToEnd(returnFront.poly, new Node(front.term.coeff, front.term.degree, null));
			front = front.next;			
		}
		return returnFront;
	}
	
	/**
	 * Returns the polynomial obtained by multiplying the given polynomial p
	 * with this polynomial - DOES NOT change this polynomial
	 * 
	 * @param p Polynomial with which this polynomial is to be multiplied
	 * @return A new polynomial which is the product of this polynomial and p.
	 */
	public Polynomial multiply(Polynomial p) 
	{
		Polynomial finalP = new Polynomial();
		Polynomial temp = new Polynomial();
		Node n = p.poly;
		for(Node polyPtr = this.poly; polyPtr != null; polyPtr = polyPtr.next)
		{
			temp = new Polynomial();
			
			for(Node ptr = n; ptr != null; ptr = ptr.next)
			{
				temp.poly = addToEnd(temp.poly, mult(polyPtr, ptr));				
			}
	
			finalP = addV2(finalP, temp);
		}
		
		return finalP;
	}
	
	/**
	 * Evaluates this polynomial at the given value of x
	 * 
	 * @param x Value at which this polynomial is to be evaluated
	 * @return Value of this polynomial at x
	 */
	public float evaluate(float x) 
	{
		float sum = 0;
		Node front = this.poly;
		while(front != null)
		{
			sum += (front.term.coeff * power(x, front.term.degree));
			front = front.next;
		}
		
		return sum;
	}
	
//----------------------------------------------------------------------------
//-----------------Helper Methods---------------------------------------------
//----------------------------------------------------------------------------
	
	private float power(float base, int power)
	{
		float toReturn = 1;
		if(power == 0)
		{
			return 1;
		}
		
		for(int i = 0; i < power; i++)
		{
			toReturn *= base;
		}
		
		return toReturn;
	}
	
	private Node addToEnd(Node front, Node n)
	{
		if(front == null)
		{
			return n;
		}
		
		for(Node ptr = front; ptr != null; ptr = ptr.next)
		{
			if(ptr.next == null)
			{
				ptr.next = n;
				break;
			}
		}
		
		return front;
	}
	
	private Node mult(Node n, Node m)
	{
		if(n == null || m == null)
		{
			return null;
		}
		else
		{
			return new Node((n.term.coeff * m.term.coeff), (n.term.degree + m.term.degree), null);
		}
	}
	
	public Polynomial addV2(Polynomial q, Polynomial p) {
		/** FIX THIS METHOD **/
		Polynomial returnFront = new Polynomial();
		Node m = q.poly, n = p.poly;
		while(m != null && n != null)
		{
			if(m.term.degree == n.term.degree)
			{
				if((m.term.coeff + n.term.coeff) != 0)
				{
					returnFront.poly = addToEnd(returnFront.poly, new Node(m.term.coeff + n.term.coeff, m.term.degree, null));
					m = m.next;
					n = n.next;	
				}
				else
				{
					m = m.next;
					n = n.next;
				}
			}
			else if(m.term.degree > n.term.degree)
			{
				returnFront.poly = addToEnd(returnFront.poly, new Node(n.term.coeff, n.term.degree, null));
				n = n.next;
				
			}
			else
			{
				returnFront.poly = addToEnd(returnFront.poly, new Node(m.term.coeff, m.term.degree, null));
				m = m.next;
			}
		}

		while(n != null)
		{
			returnFront.poly = addToEnd(returnFront.poly, new Node(n.term.coeff, n.term.degree, null));
			n = n.next;
		}
		
		while(m != null)
		{
			returnFront.poly = addToEnd(returnFront.poly, new Node(m.term.coeff, m.term.degree, null));
			m = m.next;			
		}
		

		return returnFront;
	}
	
//----------------------------------------------------------------------------
//-----------------/Helper Methods---------------------------------------------
//----------------------------------------------------------------------------	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String retval;
		
		if (poly == null) {
			return "0";
		} else {
			retval = poly.term.toString();
			for (Node current = poly.next ;
			current != null ;
			current = current.next) {
				retval = current.term.toString() + " + " + retval;
			}
			return retval;
		}
	}
}
