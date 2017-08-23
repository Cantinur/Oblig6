import krypto.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Kryptograf implements Runnable{


  Kryptograf(Monitor monitor, Monitor dekryptertMonitor){
    this.monitor = monitor;
    this.dekryptertMonitor = dekryptertMonitor;
  }

  private Lock laas = new ReentrantLock();

  private Melding kryptertMld;
  private Monitor dekryptertMonitor;
  private Monitor monitor;
  public static int ANTALL_KRYPTOGRAF = 0;



  @Override
  public void run(){
    kryptertMld = monitor.taUt();
    ANTALL_KRYPTOGRAF++;
    while (kryptertMld != null){

      //Henter ut kryptert melding informasjon.
      int id = kryptertMld.kanalID();
      int nummer = kryptertMld.hentNummer();
      String kryptert = kryptertMld.toString();


      String d = Kryptografi.dekrypter(kryptert);
      Melding dekryptertMld = new Melding(nummer, id, d);
      dekryptertMonitor.settInn(dekryptertMld);
      kryptertMld = monitor.taUt();
    }
    ANTALL_KRYPTOGRAF--;
    try{
      Oblig6.VENTER.signalAll();
    } catch (Exception e) {}
  }
}
