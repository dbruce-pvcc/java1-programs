/**
 * A player in a game of crazy eights.
 */
public class Player {

    private String name;
    private Hand hand;

    /**
     * Constructs a player with an empty hand.
     */
    public Player(String name) {
        this.name = name;
        this.hand = new Hand(name);
    }

    /**
     * Gets the player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the player's hand.
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Checks whether two cards match.
     */
    public static boolean cardMatches(Card card1, Card card2) {
        return card1.getSuit() == card2.getSuit()
            || card1.getRank() == card2.getRank()
            || card1.getRank() == 8;
    }

    /**
     * Searches the player's hand for a matching card.
     */
    public Card searchForMatch(Card prevdiscard) {
        for (int idx = 0; idx < hand.size(); idx = idx + 1) {
            Card card = hand.getCard(idx);
            if (cardMatches(card, prevdiscard)) {
                return hand.popCard(idx);
            }
        }
        return null;
    }

    /**
     * Draws cards until a match is found.
     */
    public Card drawForMatch(Eights eights, Card prevdiscard) {
        while (true) {
            Card card = eights.drawCard();
            System.out.println(name + " draws " + card);
            if (cardMatches(card, prevdiscard)) {
                return card;
            }
            hand.addCard(card);
        }
    }

    /**
     * Removes and returns a legal card from the player's hand.
     */
    public Card play(Eights eights, Card prevdiscard) {
        Card card = searchForMatch(prevdiscard);
        if (card == null) {
            card = drawForMatch(eights, prevdiscard);
        }
        return card;
    }

    /**
     * Calculates the player's score (penalty points).
     */
    public int score() {
        int sum = 0;
        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.getCard(i);
            int rank = card.getRank();
            if (rank == 8) {
                sum -= 20;
            } else if (rank > 10) {
                sum -= 10;
            } else {
                sum -= rank;
            }
        }
        return sum;
    }

    /**
     * Displays the player's hand.
     */
    public void display() {
        hand.display();
    }

    /**
     * Displays the player's name and score.
     */
    public void displayScore() {
        System.out.println(name + " has " + score() + " points");
    }

}
