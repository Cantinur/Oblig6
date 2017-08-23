import krypto.*;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.Lock;


public class Operasjonslederen implements Runnable{

  Operasjonslederen(Monitor monitor){
    this.monitor = monitor;
  }

  private String[] kanalA = new String[200];
  private String[] kanalB = new String[200];
  private String[] kanalC = new String[200];
  private Monitor monitor;


  public void run(){
    sorterILister();
    skrivTilFil("Kanal1", kanalA);
    skrivTilFil("Kanal2", kanalB);
    skrivTilFil("Kanal3", kanalC);
    System.out.println("Filer er klare ");
  }


  private void sorterILister(){
    Melding melding = monitor.taUt();

    while(melding != null){
      if (melding.kanalID() == 1) settInnIKoe(melding, kanalA);
      else if (melding.kanalID() == 2) settInnIKoe(melding, kanalB);
      else if (melding.kanalID() == 3) settInnIKoe(melding, kanalC);
      else System.out.println("Melding uten riktig kanal");

      melding = monitor.taUt();
    }
  }


  private void settInnIKoe(Melding melding, String[] kanal){
    int plass = melding.hentNummer();
    kanal[plass] = melding.toString();
  }


  private void skrivTilFil(String navn, String[] kanal){
    try{
      PrintWriter p = new PrintWriter(new File(navn+".txt"), "utf-8");
      p.write("----"+ navn +"----" + "\n");

      for (int i = 0; i < kanal.length; i++)
      if (kanal[i] != null) p.write("\n" + kanal[i] + "\n");

      p.close();
    } catch (Exception e) {
      System.out.println("Noe gikk galt med å skrive fil");
      System.exit(0);
    }
  }
}
