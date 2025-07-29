package swgr.com;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class NaveInvadida extends Nave {

    private Integer vidas = 10;
    private final Integer XVEL = 5;

    private ImageIcon explosion;
    private Boolean dobleDisparo=false;

    private Boolean perdiendo=false;

    private boolean aPressed = false;
    private boolean dPressed = false;
    private boolean wPressed = false;

    private Integer tempExplotando=200, tempExplotandoAux=tempExplotando;
    private Integer temp=10;
    private Integer tiempoEntreDisparo=20;

    List<DisparoNaveInvadida> disparos = new ArrayList<>();

    public NaveInvadida(Integer EspANCHO, Integer EspALTO) {
        this.EspALTO = EspALTO;
        this.EspANCHO = EspANCHO;
        x = EspANCHO / 2 - ANCHO/2;
        y = EspALTO-ALTO-ALTO/2-ALTO/8;
    }

    public NaveInvadida(Integer x,Integer y, Integer EspANCHO, Integer EspALTO) {
        this.x = x;
        this.y = y;
        this.EspANCHO = EspANCHO;
        this.EspALTO = EspALTO;
    }

    @Override
    public void paint(Graphics g) {
        if(perdiendo&&vivo) {
            explosion = new ImageIcon(getClass().getResource("/gif/explosion1.gif"));
            g.drawImage(explosion.getImage(), x, y, ANCHO, ALTO, null);
        } else if(vivo) {
            ImageIcon nave = new ImageIcon(getClass().getResource("/imagenes/naveDefensora.png"));
            g.drawImage(nave.getImage(), x, y, ANCHO, ALTO, null);
        }
        for(int x=0;x<disparos.size();x++) {
            disparos.get(x).paint(g);
        }
    }

    @Override
    public void moverse() {
        if(vivo&&!perdiendo) {
            if(temp>0) {
                temp-=1;
            }
            if (aPressed) {
                if (x > 10) {
                    x -= XVEL;
                }
            }
            if (dPressed) {
                if (x < EspANCHO-ANCHO-25) {
                    x += XVEL;
                }
            }
            if (wPressed) {
                if (temp==0) {
                    disparar();
                }
            }
        }
    }

    public void disparar() {
        if(!dobleDisparo) {
            DisparoNaveInvadida disparo = new DisparoNaveInvadida(x,y,EspANCHO,EspALTO,ANCHO,ALTO);
            disparos.add(disparo);
            temp=tiempoEntreDisparo;
        } else {
            DisparoNaveInvadida disparo = new DisparoNaveInvadida(x-10,y,EspANCHO,EspALTO,ANCHO,ALTO);
            DisparoNaveInvadida disparo2 = new DisparoNaveInvadida(x+10,y,EspANCHO,EspALTO,ANCHO,ALTO);
            disparos.add(disparo);
            disparos.add(disparo2);
            temp=tiempoEntreDisparo;
        }
    }

    @Override
    public void destruirse() {
        explotando();
    }
    public void explotando() {
        perdiendo=true;
        tempExplotando--;
        if(tempExplotando<=0) {
            vivo=false;
        }
    }

    public void choqueNaves(NaveInvasora naveInvasora) {
        if(vivo&&naveInvasora.getVivo()) {
            Rectangle2D naveReact = new Rectangle2D.Double(x,y,ANCHO,ALTO);
            Rectangle2D naveInvasoraReact = naveInvasora.getBoundsNaveInvasora();
            if(naveReact.intersects(naveInvasoraReact)) {
                vidas=0;
                destruirse();
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) aPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_D) dPressed = true;
        if (e.getKeyCode() == KeyEvent.VK_W||e.getKeyCode() == KeyEvent.VK_SPACE) wPressed = true;
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) aPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_D) dPressed = false;
        if (e.getKeyCode() == KeyEvent.VK_W||e.getKeyCode() == KeyEvent.VK_SPACE) wPressed = false;
    }

    public Rectangle2D getBoundsNaveInvadida() {
        return new Rectangle2D.Double(x, y, ANCHO, ALTO);
    }

    public Integer getVidas() {
        return vidas;
    }
    public Boolean getVivo() {
        return vivo;
    }

    public Integer getAncho() {
        return ANCHO;
    }
    public Integer getAlto() {
        return ALTO;
    }

    public Integer getEspAncho() {
        return EspANCHO;
    }
    public Integer getEspAlto() {
        return EspALTO;
    }
    public Integer getX() {
        return x;
    }
    public Integer getY() {
        return y;
    }
    public void reaparecer() {
        x = EspANCHO / 2 - ANCHO/2;
        y = EspALTO-ALTO-ALTO/2-ALTO/8;
    }
    public void disparoDoble() {
        dobleDisparo=true;
    }

    public void vidaMenos() {
        vidas--;
        if(vidas==0){
            destruirse();
        }
    }
    public void vidaMas() {
        vidas++;
    }

    public List<DisparoNaveInvadida> getDisparos() {
        return disparos;
    }

    public void reset() {
        vivo=true;
        perdiendo=false;
        x=EspANCHO/2-ANCHO/2;
        vidas=3;
        tempExplotando = tempExplotandoAux;
        dobleDisparo=false;
    }
}
