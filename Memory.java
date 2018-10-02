import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

public class Memory {

    private String[][] playerDeck;
    private String[][] backingDeck;
    private int numCards;

    public Memory(int n) {


        String[][] deck = new String[n+1][n+1];
        numCards = n * n;
        String[] alphabet =
            ("AABBCCDDEEFFGGHHIIJJKKLLMMNNOOPPQQRRSSTTUUVVWWXXYYZZ").split("");
        alphabet = Arrays.copyOfRange(alphabet, 0, numCards );
        List<String> cards = new ArrayList<>(Arrays.asList(alphabet));

        //Initializing two arrays to use for random coordinate generation
        List<Integer> arr1 = new ArrayList<>();
        for(int i = 0; i < n; i++){
            arr1.add(i);
        }
        List<Integer> arr2 = arr1;
        Collections.shuffle(arr1);
        Collections.shuffle(arr2);

        //Plugging the letters randomly into the 2D array
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                Collections.shuffle(cards);
                int x = arr1.get(i);
                int y = arr2.get(j);
                deck[y][x] = cards.get(0);
                cards = cards.subList(1, cards.size());
            }

        }

        this.backingDeck = deck;


        //The array visible to the user
        String[][] playerDeck = new String[n][n];
        for(String[] array : playerDeck) {
            Arrays.fill(array, "+");
        }
        this.playerDeck = playerDeck;
    }

    private String[][] getPlayerDeck() {
        return this.playerDeck;
    }

    private String[][] getBackingDeck() {
        return this.backingDeck;
    }

    private int getNumCards() {
        return this.numCards;
    }

    private void printDeck(String[][] deck) {
        for(String[] row : deck) {
            System.out.println(Arrays.toString(row));
        }
    }



    public static void main(String[] args) {

        System.out.println("Welcome to the CMD Memory game!\n"
            + "This is a single player game." +
            "\nDo you want to have 2, 4, or 6 rows?");

        Scanner inputScanner = new Scanner(System.in);
        String integer = inputScanner.nextLine();
        int anInt = Integer.parseInt(integer);
        if( anInt == 2 || anInt == 4|| anInt ==6) {

        } else {
            System.out.println("You entered an invalid number.");
            boolean valid = false;

            while(valid == false) {
                System.out.println("Enter 2, 4, or 6.");
                integer = inputScanner.nextLine();
                if(Integer.parseInt(integer) == 2
                    || Integer.parseInt(integer)== 4
                    || Integer.parseInt(integer)==6) {
                    break;
                }
            }
        }

        Memory game = new Memory(Integer.parseInt(integer));
        System.out.println("Instructions:\nThe cards are represented by plus "
            +"signs. \nTo flip a card, enter the row number followed by the "
            + "column number. \nFor example, the card on row 1 column 2 would be"
            + " 12. \nLet's play!");

        //Beginning of game
        int remaining = game.getNumCards()/2;
        String[][] tempPD = new String[Integer.parseInt(integer)][Integer.parseInt(integer)];
            for(String[] array : tempPD) {
                Arrays.fill(array, "+");
            }

        while(remaining > 0 ) {
            String[][] bDeck = game.getBackingDeck();
            String[][] pDeck = game.getPlayerDeck();
            game.printDeck(pDeck);


            String card1 = "";
            String card2 = "";
            for (int i = 0; i < 2; i++) {

                if (i == 0) {
                    System.out.println("Choose a card to flip");
                    String coordinate = inputScanner.nextLine();
                    int x = Integer.parseInt(coordinate.substring(0,1));
                    int y = Integer.parseInt(coordinate.substring(1,2));
                    // if(Arrays.asList(found).contains(bDeck[x][y])) {
                    //     System.out.println("You already found this card!");
                    //     break;
                    // } else {
                        pDeck[x][y]= bDeck[x][y];
                        card1 = bDeck[x][y];
                        game.printDeck(pDeck);
                    // }

                } else {
                    System.out.println("Choose a second card to flip");
                    String coordinate = inputScanner.nextLine();
                    int x = Integer.parseInt(coordinate.substring(0,1));
                    int y = Integer.parseInt(coordinate.substring(1,2));
                    pDeck[x][y] = bDeck[x][y];
                    card2 = bDeck[x][y];
                    game.printDeck(pDeck);
                    if (card1.equals(card2)) {
                        System.out.println("Correct match!");
                        for(int j = 0; j < Integer.parseInt(integer); j++) {
                            tempPD[j] = pDeck[j].clone();
                        }
                        remaining--;
                    } else {
                        System.out.println("Wrong match.");
                        for(int j = 0; j < Integer.parseInt(integer); j++) {
                            pDeck[j] = tempPD[j].clone();
                        }
                    }
                }
            }
        }
        System.out.println("You found them all! Thanks for playing :)");
    }

}