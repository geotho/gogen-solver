package test.gogen;

import static org.junit.Assert.*;

import java.util.HashMap;

import gogen.Node;

import org.junit.Test;

public class NodeTest {
  Node a, b, c, d, e, f, g, h, i, j;

  @Test
  public void testAddConnection() {
    Node n = new Node('c');
    n.addConnection(new Node('a'));
    assertEquals(1, n.connections.size());
    
    n.addConnection(new Node('b'));
    assertEquals(2, n.connections.size());
    
    n.addConnection(new Node('a'));
    assertEquals(2, n.connections.size());
  }

  @Test
  public void testDistantNodes() {
    resetNodes();
    a.addConnection(b);
    b.addConnection(c);
    b.addConnection(d);
    d.addConnection(e);

    assertEquals("a", a.distantNodesAsString(0));
    assertEquals("b", a.distantNodesAsString(1));
    assertEquals("cd", a.distantNodesAsString(2));
    assertEquals("e", a.distantNodesAsString(3));
    
    resetNodes();
    a.addConnection(b);
    a.addConnection(c);
    a.addConnection(d);
    b.addConnection(c);
    b.addConnection(d);
    c.addConnection(d);
    b.addConnection(e);
    c.addConnection(e);
    d.addConnection(e);

    assertEquals("a", a.distantNodesAsString(0));
    assertEquals("bcd", a.distantNodesAsString(1));
    assertEquals("e", a.distantNodesAsString(2));
  }
  
  @Test
  public void testShortestPathLength() {
    resetNodes();
    a.addConnection(b);
    b.addConnection(c);
    c.addConnection(d);
    d.addConnection(e);

    assertEquals(0, Node.shortestPathLength(a, a));
    assertEquals(1, Node.shortestPathLength(a, b));
    assertEquals(2, Node.shortestPathLength(a, c));
    assertEquals(3, Node.shortestPathLength(a, d));
    assertEquals(4, Node.shortestPathLength(a, e));
    assertEquals(Node.shortestPathLength(e, a), Node.shortestPathLength(a, e));
    
    resetNodes();
    a.addConnection(b);
    a.addConnection(c);
    a.addConnection(d);
    b.addConnection(c);
    b.addConnection(d);
    c.addConnection(d);
    b.addConnection(e);
    c.addConnection(e);
    d.addConnection(e);
    
    assertEquals(1, Node.shortestPathLength(a, b));
    assertEquals(1, Node.shortestPathLength(a, c));
    assertEquals(1, Node.shortestPathLength(a, d));
    assertEquals(2, Node.shortestPathLength(a, e));
  }
  
  private void resetNodes() {
    a = new Node('a');
    b = new Node('b');
    c = new Node('c');
    d = new Node('d');
    e = new Node('e');
    f = new Node('f');
    g = new Node('g');
    h = new Node('h');
    i = new Node('i');
    j = new Node('j');
  }
  
}
