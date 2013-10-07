package gogen;

import java.util.HashSet;
import java.util.Set;

public class Coordinate {
  private final int x, y;
  public static Coordinate[][] coordinates = new Coordinate[5][5];
  
  private Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }
  
  public int x(){
    return x;
  }
  
  public int y(){
    return y;
  }
  
  public static Coordinate newCoordinate(int x, int y) {
    if (y < 0 || x < 0 || x > 4 || y > 4) {
      return null;
    }
    if (coordinates[y][x] == null) {
      coordinates[y][x] = new Coordinate(x, y);
    }
    return coordinates[y][x];
  }
  
  public static Set<Coordinate> populateWithAllCoords() {
    Set<Coordinate> coords = new HashSet<Coordinate>();
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        coords.add(newCoordinate(i,j));
      }
    }
    return coords;
  }
  
//  @Override
//  public int hashCode() {
//    final int prime = 31;
//    int result = 1;
//    result = prime * result + x;
//    result = prime * result + y;
//    return result;
//  }
//
//  @Override
//  public boolean equals(Object obj) {
//    if (this == obj)
//      return true;
//    if (obj == null)
//      return false;
//    if (getClass() != obj.getClass())
//      return false;
//    Coordinate other = (Coordinate) obj;
//    if (x != other.x)
//      return false;
//    if (y != other.y)
//      return false;
//    return true;
//  }

  public int distanceTo(Coordinate c) {
    if ((x == -1) || (c.x == -1)) return -1; 
    int deltaX = Math.abs(this.x - c.x);
    int deltaY = Math.abs(this.y - c.y);
    return Math.max(deltaX, deltaY);
  }
  
  public static Set<Coordinate> coordsAtDist(Coordinate c, int d) {
    return coordsAtDist(c, d, new HashSet<Coordinate>());
  }
  
  public Set<Coordinate> coordsAtDist(int d) {
    return coordsAtDist(this, d);
  }
  
  private static Set<Coordinate> coordsAtDist(Coordinate c, int d, Set<Coordinate> set) {
    if (c == null) {
      return set;
    }
    if (d == 0) {
      set.add(c);
    } else {
      for (int i = -1; i <= 1; i++) {
        for (int j = -1; j <= 1; j++) {
          set.addAll(coordsAtDist(newCoordinate(c.x+i,c.y+j), d-1, set));
        }
      }
    }
    return set;
  }
}
