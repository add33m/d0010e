package lab2.toolClasses;

// Basic Vector2
public class Vector2 {
  public final int x;
  public final int y;

  public Vector2(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Vector2 add(Vector2 v) {
    return new Vector2(this.x + v.x, this.y + v.y);
  }

  public Vector2 subtract(Vector2 v) {
    return new Vector2(this.x - v.x, this.y - v.y);
  }

  public float distance(Vector2 v) {
    return (float) Math.sqrt(Math.pow(this.x - v.x, 2) + Math.pow(this.y - v.y, 2));
  }
}