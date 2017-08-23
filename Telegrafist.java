import java.util.concurrent.CountDownLatch;
import krypto.*;


public class Telegrafist implements Runnable{

  Telegrafist(Monitor monitor, Kanal kanal){
    this.monitor = monitor;
    this.kanal = kanal;
  }
  private java.lang.String melding;
  private Kanal kanal;
  private Monitor monitor;
  public static int ANTALL_TELEGRAFIST = 0;

  @Override
  public void run(){

    ANTALL_TELEGRAFIST++;
    int id = kanal.hentId();
    int meldingNummer = 1;
    melding = kanal.lytt();


    while (melding != null){
      Melding m = new Melding(meldingNummer, id, melding);
      monitor.settInn(m);
      meldingNummer++;
      melding = kanal.lytt();
    }

    ANTALL_TELEGRAFIST--;
    try{
      Oblig6.VENTER.signalAll();
    } catch (Exception e) {}

  }


}
