package gogen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

import static gogen.Coordinate.newCoordinate;

public class Node {
  public final char letter;
  public HashMap<Character, Node> connections;
  public Coordinate coord;
  
  
  public Node(char letter) {
    connections = new HashMap<Character, Node>();
    this.letter = letter;
    coord = newCoordinate(-1, -1);
  }
  
  public void addConnection(Node n) {
    connections.put(n.letter, n);
    n.connections.put(this.letter, this);
  }
  
  public HashMap<Character, Node> distantNodes(int n) {
    HashMap<Character, Node> nodesAtDist = new HashMap<Character, Node>();
    HashMap<Character, Node> lesserNodes;
    if (n < 0) {
      return null;
    } else if (n == 0) {
      nodesAtDist.put(this.letter, this);
      return nodesAtDist;
    } else if (n == 1) {
      return connections;
    } else {
      for (Node node : this.distantNodes(n-1).values()) {
        nodesAtDist.putAll(node.distantNodes(1));
      }
      for (int x = n-1; x >= 0; x--){
        lesserNodes = distantNodes(x);
        for (char c : lesserNodes.keySet()) {
          nodesAtDist.remove(c);
        }
      }
      return nodesAtDist;
    }
  }
  
  public String distantNodesAsString(int n) {
    HashMap<Character, Node> map = distantNodes(n);
    String s = "";
    TreeSet<Character> chars = new TreeSet<Character>(map.keySet());
    for (char c : chars) {
      s = s + c;
    }
    return s;
  }
  
  public void setCoordinate(Coordinate coord) {
    this.coord = coord;
  }
  
  private static Queue<Node> shortestPath(Node start, Node end, Queue<Node> path) {
    Queue<Node> pathCopy = new LinkedList<Node>(path);
    pathCopy.add(start);
    if (start == end) {
      return pathCopy;
    }
    Queue<Node> shortest = null;
    for (Node n : start.connections.values()) {
      if (!pathCopy.contains(n)) {
        Queue<Node> newpath = shortestPath(n, end, pathCopy);
        if (newpath != null) {
          if ((shortest == null) || (newpath.size() < shortest.size())) {
            shortest = newpath;
          }
        }
      }
    }
    return shortest;
  }
  
  public static int shortestPathLength(Node start, Node end) {
    return shortestPath(start, end, new LinkedList<Node>()).size()-1;
  }
  
  public int shortestPathLength(Node end) {
    return shortestPathLength(this, end);
  }
}
