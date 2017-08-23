import krypto.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.CountDownLatch;

public class Oblig6{
  public static Lock laas = new ReentrantLock();
  public static Condition VENTER = laas.newCondition();

  public static void main(String[] args) {

    //Oppretter en ny Operasjonssentral
    int antallTelegrafer = 3;
    Operasjonssentral ops = new Operasjonssentral(antallTelegrafer);
    Kanal[] kanaler = ops.hentKanalArray();

    // Lager to monitorer
    Monitor kryptertMld = new Monitor();
    Monitor dekryptertMld = new Monitor();

    //Starter opp Telegrafister
    for (int i = 0; i < kanaler.length; i++) new Thread(
    new Telegrafist(kryptertMld, kanaler[i])).start();

    System.out.println("Starter alle Telegrafister");

    //Starter opp Kryptografer
    for (int k = 0; k < 100; k++) new Thread(
    new Kryptograf(kryptertMld, dekryptertMld)).start();

    System.out.println("Starter alle Kryptografer");

    while(Telegrafist.ANTALL_TELEGRAFIST > 0) try {VENTER.await();} catch (Exception e) {}
    System.out.println("Telegrafister er ferdig");

    //Starter Operasjonslederen nar alle Kryptografer er ferdig
    while(Kryptograf.ANTALL_KRYPTOGRAF > 0) try {VENTER.await();} catch (Exception e) {}
    System.out.println("Kryptografer er ferdig");

    //Oppstart av Operasjonslederen
    new Thread(new Operasjonslederen(dekryptertMld)).start();
    System.out.println("Starter Operasjonslederen");

  }
}
