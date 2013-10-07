package gogen;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static gogen.Coordinate.newCoordinate;


public class Solver {

  private static Map<Character, Node> net;
  private static final String SAMPLE_WORDS =
      "box,frog,hemp,jars,link,pelt,quickly,rope,saw,vaulted,warp,wink";
  private static final String SAMPLE_PLACES = "vbxwqdnth";
  private static final String[] words = SAMPLE_WORDS.split(",");
  private static Map<Coordinate, Node> grid;
  private static Set<Character> added;
  private static Set<Character> unadded;
  
  public static void main(String[] args) {
    initialize();
    int dist;
    Set<Character> unaddedCopy, addedCopy;
    Set<Coordinate> possiblePlaces;
    while (unadded.size() > 0) {
      unaddedCopy = new HashSet<Character>(unadded); 
      for (char unAddedChar : unaddedCopy) {
        possiblePlaces = Coordinate.populateWithAllCoords();
        addedCopy = new HashSet<Character>(added); 
        for (char addedChar : addedCopy) {
          dist = getOrMakeNode(addedChar).shortestPathLength(getOrMakeNode(unAddedChar));
          Set<Coordinate> distantCoords = getOrMakeNode(addedChar).coord.coordsAtDist(dist);
          possiblePlaces.retainAll(distantCoords);
          possiblePlaces.removeAll(grid.keySet());
          if (possiblePlaces.size() == 1) {
            insertIntoGrid(possiblePlaces.iterator().next(), getOrMakeNode(unAddedChar)); 
          } 
        }
      }
    }
  }
  
  private static void buildNodeNet() {
    net = new HashMap<Character, Node>();
    Node currentNode;
    char currentChar;
    for (String word : words) {
      for (int i = 0; i < word.length(); i++) {
        currentChar = word.charAt(i);
        currentNode = getOrMakeNode(currentChar);
        if (i != 0) {
          currentNode.addConnection(getOrMakeNode(word.charAt(i-1)));
        }
      }
    }
  }
  
  private static void buildGrid() {
    grid = new HashMap<Coordinate, Node>();
    int counter = 0;
    for (int y = 0; y < 5; y += 2) {
      for (int x = 0; x < 5; x += 2) {
        insertIntoGrid(newCoordinate(x, y), getOrMakeNode(SAMPLE_PLACES.charAt(counter++)));
      }
    }
  }
  
  private static void insertIntoGrid(Coordinate c, Node n) {
    System.out.println("Inserting " + n.letter + " into (" + c.x() + ", " + c.y() + ").");
    n.setCoordinate(c);
    grid.put(c, n);
    added.add(n.letter);
    unadded.remove(n.letter);
  }
  
  //TODO(geotho) Refactor into Node.java.
  private static Node getOrMakeNode(char c) {
    Node n;
    if (net.containsKey(c)) {
      n = net.get(c);
    } else {
      n = new Node(c);
      net.put(c, n);
    }
    return n;
  }
  
  private static void initialize() {
    added = new HashSet<Character>();
    unadded = new HashSet<Character>();
    for (char c = 'a'; c <= 'y'; c++) {
      unadded.add(c);
    }
    buildNodeNet();
    buildGrid();
  }
  
  private static String gridToString() {
    String s = "";
    char c = '_';
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        try {
          c = grid.get(newCoordinate(j,i)).letter;
        } catch (NullPointerException e) {
          c = '_';
        } finally {
          s = s + c;
        }
      }
      s = s + "\n";
    }
    return s;
  }

}
