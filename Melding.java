public class Melding{
  Melding(int nummer, int kanalId, String info){
    this.kanalId = kanalId;
    this.info = info;
    this.nummer = nummer;
  }

  private int kanalId;
  private int nummer;
  private String info;

  @Override
  public String toString(){
    return info;
  }

  public int kanalID(){
    return kanalId;
  }

  public int hentNummer(){
    return nummer;
  }
}
