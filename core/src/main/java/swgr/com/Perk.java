package swgr.com;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.ImageIcon;

public class Perk {

    private Random random = new Random();
    private final Integer tipo=random.nextInt(3 - 1 + 1) + 1;
    private final Integer VELY=40;
    private NaveInvasora nave;
    private Integer x;
    private Integer y;
    private final Integer ANCHO=67;
    private final Integer ALTO=67;
    private Boolean desaparece=false;
    private Integer EspAncho;
    private Integer EspAlto;


    public Perk(NaveInvasora nave,Integer EspAncho,Integer EspAlto) {
        this.nave = nave;
        this.EspAncho = EspAncho;
        this.EspAlto = EspAlto;
        this.x = x=nave.getX();
        this.y=	y=nave.getY();
    }


    public void paint(Graphics g){
        //System.out.println("tipo: " + tipo + " desaparece: " + desaparece);
        if(!desaparece) {
            if(tipo==1) {
                ImageIcon perk = new ImageIcon(getClass().getResource("/imagenes/bombaPerk.png"));
                g.drawImage(perk.getImage(), x, y, ANCHO, ALTO, null);
            } else if(tipo==2) {
                ImageIcon perk = new ImageIcon(getClass().getResource("/imagenes/vidaPerk.png"));
                g.drawImage(perk.getImage(), x, y, ANCHO, ALTO, null);
            } else if(tipo==3) {
                ImageIcon perk = new ImageIcon(getClass().getResource("/imagenes/dobledisparo.png"));
                g.drawImage(perk.getImage(), x, y, ANCHO, ALTO, null);
            }
            moverse();
        }
    }

    void moverse() {
        if(y<=EspAlto) {
            y+=2;
            return;
        }
    }


    public void choque(NaveInvadida nave) {
        if(!desaparece) {
            Rectangle2D perkReact = getBoundsPerk();
            Rectangle2D naveReact = nave.getBoundsNaveInvadida();
            if(perkReact.intersects(naveReact)) {
                if(tipo==1) {
                    nave.vidaMenos();
                    nave.vidaMenos();
                    desaparece=true;
                } else if(tipo==2) {
                    nave.vidaMas();
                    desaparece=true;
                } else if(tipo==3) {
                    nave.disparoDoble();
                    desaparece=true;
                }
            }
        }
    }

    public Rectangle2D getBoundsPerk() {
        return new Rectangle2D.Double(x, y, ANCHO, ALTO);
    }

    public NaveInvasora getNave() {
        return nave;
    }
}

