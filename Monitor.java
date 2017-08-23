import krypto.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Monitor{

  private Koe<Melding> meldingListe = new Koe<Melding>();

  private Lock laas = new ReentrantLock();
  private Condition ingen = laas.newCondition();


  public void settInn(Melding melding){
    laas.lock();
    try{
      meldingListe.settInn(melding);

      ingen.signalAll();

    } catch (Exception e){
      System.out.println("Noe gikk galt med a sette inn i monitoren");
      System.exit(1);
    }finally{
      laas.unlock();
    }
  }


  public Melding taUt(){
    laas.lock();
    try{
      while(meldingListe.erTom() && Telegrafist.ANTALL_TELEGRAFIST > 0) ingen.await();

      return meldingListe.fjern();

    } catch (Exception e) {
      System.out.println("Noe gikk galt med a ta ut av monitoren");
      System.exit(1);
    }finally{
      laas.unlock();
    }
    return new Melding(0,0, "Noe gikk galt");
  }
}
