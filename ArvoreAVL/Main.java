// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
  public static void main(String[] args) {
    ArvoreAVL b = new ArvoreAVL();
    b.add(1);
    b.add(2);
    b.add(3);
    b.add(4);
    b.add(5);
    b.add(6);
    b.add(7);
    b.add(8);
    b.add(9);
    System.out.println(b.height(b.getRoot()));
    System.out.println(b.positionsWidth().toString());
    System.out.println("---------------------");
    b.GeraDOT();

    ArvoreAVL c = new ArvoreAVL();
    c.add(9);
    c.add(8);
    c.add(7);
    c.add(6);
    c.add(5);
    c.add(4);
    c.add(3);
    c.add(2);
    c.add(1);
    System.out.println(c.height(c.getRoot()));
    System.out.println(c.positionsCentral().toString());
    System.out.println("---------------------");
    c.GeraDOT();
  }
}
