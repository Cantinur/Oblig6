import java.util.Iterator;
import java.util.NoSuchElementException;

public class Koe<T> extends Stabel<T>{
  private Node bak;

  @Override
  public void settInn(T element){
    Node n = new Node(element);
    if (erTom()){
      foran = n;
      bak = n;
    } else{
      bak.neste = n;
      n.forrige = bak;
      bak = n;
    }
    teller++;
  }
}
