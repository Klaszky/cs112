package friends;

public class Friend {
	Friend nextFriend;	// next Friend object
	Person who;			// Person object who is the friend
	
	Friend(Person who, Friend nextFriend) {
		this.who = who;
		this.nextFriend = nextFriend;
	}
}
