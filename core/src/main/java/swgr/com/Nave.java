package swgr.com;

import java.awt.Graphics;
import java.util.Random;

public abstract class Nave {
    final protected Integer ANCHO = 76;
    final protected Integer ALTO = 80;
    Integer EspANCHO;
    Integer EspALTO;

    Integer x;
    Integer y;

    Boolean vivo=true;

    private Random random = new Random();

    public Nave() {

    }

    public Nave(Integer x,Integer y) {
        this.x = x;
        this.y = y;
    }

    public Nave(Integer x,Integer y, Integer EspANCHO, Integer EspALTO) {
        this.x = x;
        this.y = y;
        this.EspANCHO = EspANCHO;
        this.EspALTO = EspALTO;
    }

    public void spawn(Integer EspANCHO,NaveJefeFinal nave) {
        int max=EspANCHO-nave.getAncho()-nave.getAncho()-nave.getAncho()-30;
        x = random.nextInt(max - 50 + 1) + 50;
        //System.out.println(x + " max: " + max);
    }

    public abstract void moverse();

    public abstract void destruirse();

    public abstract void paint(Graphics g);
}
