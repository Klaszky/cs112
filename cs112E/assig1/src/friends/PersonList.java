package friends;

// represents a list of people as a linked list of Person objects
public class PersonList {
	Person firstPerson;		// First Person object in the list

	public PersonList( ) {
		this.firstPerson = null;
	}
	
	// finds Person object in this list with given name
	// if none exists, returns null.  Runs in O(number of persons in this list) 
	public Person lookup(String name){
		
		if(this.firstPerson == null)
		{
			return null;
		}
		
		for(Person p = this.firstPerson; p != null; p = p.nextPerson)
		{
			if(p.name.equals(name))
			{
				return p;
			}
		}
		
		return null;
	}
	
	// creates a new Person object with name and adds it to the list of 
	// Person objects.  Runs in O(1)
	public Person addPerson(String name){
		
		this.firstPerson = new Person(name, this.firstPerson);
		return this.firstPerson;
		}

}
