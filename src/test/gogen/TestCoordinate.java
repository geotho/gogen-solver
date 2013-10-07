package test.gogen;

import static gogen.Coordinate.newCoordinate;
import static gogen.Coordinate.coordsAtDist;
import gogen.Coordinate;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class TestCoordinate {
  @Test
  public void testCoordsAtDist() {
    Coordinate testCoord = newCoordinate(1, 1);
    
    Set<Coordinate> expected = new HashSet<Coordinate>();
    for (int i = 0; i <= 2; i++) {
      for (int j = 0; j <= 2; j++) {
        expected.add(newCoordinate(i,j));
      }
    }
    assertEquals(expected, coordsAtDist(testCoord, 1));
        
    for (int i = 0; i <= 3; i++) {
      for (int j = 0; j <= 3; j++) {
        expected.add(newCoordinate(i,j));
      }
    }
    assertEquals(expected, coordsAtDist(testCoord, 2));
  }
}
