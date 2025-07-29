package swgr.com;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

public class DisparoNaveInvadida extends Disparo{

    Boolean desaparece=false;

    public DisparoNaveInvadida(Integer x,Integer y, Integer EspANCHO, Integer EspALTO, Integer ancho_nave, Integer alto_nave) {
        super(x,y,EspANCHO,EspALTO,ancho_nave,alto_nave);
    }

    @Override
    public void paint(Graphics g) {
        if(!desaparece) {
            ImageIcon disparo = new ImageIcon(getClass().getResource("/imagenes/disparoDefensor.png"));
            g.drawImage(disparo.getImage(), x+ancho_nave/2-4, y-alto_nave/8, ANCHO, ALTO, null);
            moverse();
        }
    }

    public void desaparecer() {
        desaparece=true;
    }

    @Override
    public void moverse() {
        if(y>0&&y<=EspALTO-ALTO*2&&!desaparece) {
            y-=velY;
        } else {
            desaparece=true;
        }
    }

    public void choque(NaveInvasora nave) {
        if(nave.getVivo()&&!desaparece) {
            Rectangle2D disparoReact = new Rectangle2D.Double(x+ancho_nave/2-4, y-alto_nave/8, ANCHO, ALTO);
            Rectangle2D naveReact = nave.getBoundsNaveInvasora();

            if(disparoReact.intersects(naveReact)) {
                nave.destruirse();
                desaparece=true;
            }
        }
    }

    public Rectangle2D getBoundsDisparoND() {
        return new Rectangle2D.Double(x+ancho_nave/2-4, y-alto_nave/8, ANCHO, ALTO);
    }
}
