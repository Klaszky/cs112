package friends;

public class Person {
	String name;		// the person's name
	Friend firstFriend; // the first friend in the list of this
					    // person's friends
	Person nextPerson;  // the next person in the list of people
	
	public Person(String name, Person nextPerson) {
		this.name = name;
		this.nextPerson = nextPerson;
		}

	// A string representing the list of friends of this person.  
	// O(number of friends in the list)
	public String friendString(){
		
		String s = "";
		
		if(this.firstFriend == null)
		{
			return s;
		}
		else
		{
			for(Friend f = this.firstFriend; f != null; f = f.nextFriend)
			{
				if(s.length() == 0)
				{
					s = s + f.who.name;
				}
				else
				{
					s = s + " " + f.who.name;
				}
			}
			
			return s;
		}
	
	}
	
	// add friend as a friend of this person
	// O(1)
	public void addFriend(Person friend){
		
		if(this.firstFriend == null)
		{
			this.firstFriend = new Friend(friend, null);
		}
		else
		{
			this.firstFriend = new Friend(friend, this.firstFriend);
		}
	}
	
	// remove Person friend as a friend of this person
	// if friend is not a friend of this person, does nothing
	// O(number of friends of this person)
	public void removeFriend(Person friend){
		
		if(this.firstFriend == null)
		{
			return;
		}
		
		if(this.firstFriend.who.name.equals(friend.name))
		{
			this.firstFriend = this.firstFriend.nextFriend;
			return;
		}
		
		for(Friend f = this.firstFriend; f != null; f = f.nextFriend)
		{
			if(f.nextFriend.who.name.equals(friend.name))
			{
				f.nextFriend = f.nextFriend.nextFriend;
				return;
			}
		}
		
		return;
	
		
	}
}
