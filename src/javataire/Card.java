package javataire;

class Card 
{
	// Each Card object has an assigned suit and value.
    private final Suit suit;
    private final Value value;

	// Determines card visibility and if card is currently chosen.
	private boolean flipped = false;
	private boolean selected = false;

    /**
	 * Constructor
     */
	Card(Suit suit, Value value) 
	{
		this.suit = suit;
		this.value = value;
	}

	// Getter for Suit.
	Suit getSuit()
	{
		return suit;
	}

	// Getter for Value.
	Value getValue()
	{
		return value;
	}

	// Getter for Color. Either black or red depending on Suit.
	Color getColor()
	{
		if (suit == Suit.CLUBS || suit == Suit.SPADES) return Color.BLACK;
		else return Color.RED;
	}

	// Getter for name of Card. Used for drawing images.
	String getName() 
	{
		return value.toString() + "_of_" + suit.toString().toLowerCase();
	}

	// Flip Card to make it visible.
	void flip()
	{
		flipped = !flipped;
	}

	// Getter for "flipped" status of Card.
	boolean isFlipped()
	{
		return flipped;
	}

	// Select/deselect current Card.
	void select()
	{
		selected = !selected;
	}

	// Getter for "selected" status of Card.
	boolean isSelected()
	{
		return selected;
	}
}
