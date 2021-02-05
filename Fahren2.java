/////////////////////////////////////////
//Praktikum Grundlagen der Programmierung
//  Aufgabe 4 
/////////////////////////////////////////

public class Fahren2 {
  public static void main(String[] args) {
    new RosiBeispiel();
  }
}

class RosiBeispiel extends Rosi {

  public void los() {

    programmBezeichnung = "Fahren in einer Linie mit zwei Sensoren";
    LichtSensor lsl, lsr, lsrh, ksm, ksli, ksla, ksri, ksra;

    lsl = new LichtSensor(1);
    lsl.positionieren(20, 4);
    lsr = new LichtSensor(2);
    lsr.positionieren(20, -4);
    lsrh = new LichtSensor(3);
    lsrh.positionieren(10, -16);
    ksm = new LichtSensor(4); // Kotrollsensor mitte
    ksm.positionieren(-5, 0);
    ksli = new LichtSensor(5); // Kotrollsensor links innen
    ksli.positionieren(-5, -10);
    ksla = new LichtSensor(6); // Kotrollsensor links außen
    ksla.positionieren(-5, -20);
    ksri = new LichtSensor(7); // Kotrollsensor rechts innen
    ksri.positionieren(-5, 10);
    ksra = new LichtSensor(8); // Kotrollsensor rechts außen
    ksra.positionieren(-5, 20);

    hintergrundBild("linie");
    positionieren(-40, 250, 0);

    setDisplayOn();

    fertig();

    Prozess pr = new Prozess(1) {
      int geschwindigkeit = 0;

      public void Aktion() {
        fahren(geschwindigkeit);

        int boost = 79;

        int taktgeber = 100;
        int lauftakt = (int) (laufzeit());

        if (ksla.hell() && ksli.hell() && ksm.hell() && ksri.hell() && ksra.hell()) {
          if (lauftakt % taktgeber == 0) {
            display.clear(true);
            geschwindigkeit = goSlow(geschwindigkeit);
            text("Geschwindigkeit: " + (geschwindigkeit), 5, 60);
          }
          if (geschwindigkeit == 0 && laufzeit() > 4000)
            programmBeenden();
        } else {
          if (lsr.hell() && lsl.hell()) {
            if (lsrh.hell()) {
              linksDrehen(boost);
            } else {
              rechtsDrehen(boost);
            }
          } else {
            if (lsr.hell()) {
              linksDrehen(boost);
            }
            if (lsl.hell()) {
              rechtsDrehen(boost);
              rechts(boost);
            }
          }
          if (lauftakt % (taktgeber * 20) == 0) {
            display.clear(true);
            geschwindigkeit = goFast(geschwindigkeit);
            text("Geschwindigkeit: " + (geschwindigkeit), 5, 60);
          }
        }

      }
    };
    pr.starten();
  }

  public static int goFast(int currentSpeed) {
    currentSpeed = currentSpeed * 110 / 100 + 1;
    return currentSpeed;
  }

  public static int goSlow(int currentSpeed) {
    if (currentSpeed * 90 / 100 <= 100) {
      if (currentSpeed - 20 <= 0) {
        return 0;
      } else {
        currentSpeed = currentSpeed - 8;
        return currentSpeed;
      }
    } else {
      currentSpeed = currentSpeed * 90 / 100;
      return currentSpeed;
    }
  }
}