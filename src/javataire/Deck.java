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
    Stack<Card> stock = new Stack<>();  // Stack of cards in stock pile.
    Stack<Card> waste = new Stack<>();  // Stack of cards in waste pile.
	Stack<Card> selected = new Stack<>();  // Stack of selected card(s).

    List<Stack<Card>> tableau = new ArrayList<>();  // Holds each stack of cards in tableau.
    List<Stack<Card>> foundations = new ArrayList<>();  // Holds each stack of cards for the four foundations.
    
	private final Random random = new Random();  // Used for shuffling.

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
	 * Take the cards from waste and restock them.
	 */
	public void reStock() 
    {
		for (int i = 0; i < waste.size(); i++) 
        {
			stock.push(waste.pop());
		}
	}

    /**
     * Verifies if game is won by checking win condition of foundations.
     */
    public boolean checkWin() 
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
	public void deselectAll() 
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
