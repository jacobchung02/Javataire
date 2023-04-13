package javataire;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Class that manages shuffling, drawing, and moving cards.
 * Responsible for checking whether moves will work, as well as
 * keeping track of the cards in play.
 */
class Deck
{
    Stack<Card> stock = new Stack<>();
    Stack<Card> waste = new Stack<>();

    private final List<Stack<Card>> tableau = new ArrayList<>();
    private final List<Stack<Card>> foundations = new ArrayList<>();
    private final Random random = new Random();

    private Stack<Card> selected = new Stack<>();

    /**
	 * Create a new Deck.
	 */
	public void initialize() 
    {
		stock.clear();
		for (Suit suit : Suit.values()) 
        {
			for (Value value : Value.values()) 
            {
				stock.push(new Card(suit, value));
			}
		}
	}

    /**
	 * Set each stack on the tableau in classic Solitaire fashion.
	 */
	public void createTableau() 
    {
		tableau.clear();
		for (int i = 0; i < 7; i++) 
        {
			Stack<Card> stack = new Stack<>();
			for (int j = 0; j < i + 1; j++) 
            {
				stack.push(stock.pop());
			}
			tableau.add(stack);
		}
	}

    /**
	 * Swap cards. Used for shuffling.
	 */
	public void swap(int left, int right) 
    {
		Card temp = stock.get(left);
		stock.set(left, stock.get(right));
		stock.set(right, temp);
	}

    /**
	 * Shuffle the Deck by swapping randomly.
	 */
	public void shuffle() 
    {
		for (int i = 0; i < 52; i++) 
        {
            swap(random.nextInt(52 - i), 51 - i);
		}
	}

	/**
	 * Returns card from the top of the deck. Used for setting cards down on the initial draw.
	 */
	public Card placeDown()
	{
		return stock.pop();
	}

    /**
	 * Flip the card at the top of the stock, then place it in the waste (face-up).
	 */
	public void popStock() 
    {
		waste.push(stock.pop());
	}

    /**
	 * Take the cards from waste and restock them.
	 */
	public void reset() 
    {
		for (int i = 0; i < waste.size(); i++) 
        {
			stock.push(waste.pop());
		}
	}

    /**
     * Check if placing child over parent would work in the foundations.
	 */
	public boolean checkFoundationMove(Card parent, Card child) 
    {
		if (parent == null) 
        {
			return child.getValue() == Value.ACE;
		}
		if (parent.getSuit() != child.getSuit()) 
        {
			return false;
		}
		if (parent.getValue().ordinal() != child.getValue().ordinal() - 1) 
        {
			return false;
		}
		return true;
	}

    /**
     * Check if placing one card over another would work in the tableau.
	 * Using .ordinal()  
     */
	public boolean checkTableauMove(Card parent, Card child) 
    {
		if (parent == null) 
        {
			return child.getValue() == Value.KING;
		}
		if (parent.getColor() == child.getColor()) 
        {
			return false;
		}
		if (parent.getValue().ordinal() != child.getValue().ordinal() + 1) 
        {
			return false;
		}
		return true;
	}

    /**
     * Verifies if game is won by checking win condition of foundations.
     */
    public boolean isWon() 
    {
		for (Stack<Card> stack : foundations) 
        {
			// Stack size == 13 for a win.
			if (stack.size() != 13) 
            {
				return false;
			}
		}
		return true;
	}

    /**
     * Move the selected card to the specified Stack.
     * Returns false until win condition is satisfied.
     */
	public void moveCards(Stack<Card> stack) 
    {
		for (Card card : selected) 
        {
			waste.remove(card);
			for (Stack<Card> boardStack : tableau) 
            {
				boardStack.remove(card);
			}
			for (Stack<Card> foundStack : foundations) 
            {
				foundStack.remove(card);
			}
			stack.push(card);
		}
	}

    /**
     * Set Card's status to selected and add it to the stack of selected cards.
     */
	public void selectCard(Card card) 
    {
		card.select();
		selected.add(card);
	}

    /**
     * Revert Cards' statuses to unselected and remove selected from its stack.
     */
	public void deselectCards() 
    {
		if (!selected.isEmpty()) 
        {
			for (Card card : selected) 
            {
				card.select();
			}
			selected.clear();
		}
	}
}
