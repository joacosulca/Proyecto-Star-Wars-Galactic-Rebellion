package swgr.com;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

public class NaveInvasoraDisparadora extends NaveInvasora{

    Random random = new Random();
    int min = 80;
    int max = 300;
    int tempDisparo=random.nextInt(max - min + 1) + min;
    List<DisparoNaveInvasora> disparos = new ArrayList<>();

    public NaveInvasoraDisparadora(Integer x, Integer y, Integer EspANCHO, Integer EspALTO, swgrPrincipal swgrPrincipal) {
        super(x, y, EspANCHO, EspALTO, swgrPrincipal);
        this.swgrPrincipal = swgrPrincipal;
    }

    @Override
    public void paint(Graphics g) {
        if(vivo) {
            ImageIcon nave = new ImageIcon(getClass().getResource("/imagenes/naveenemigadisparadora.png"));
            g.drawImage(nave.getImage(), x, y, ANCHO, ALTO, null);
            temp--;
            tempDisparo--;
        }
        for(int x=0;x<disparos.size();x++) {
            disparos.get(x).paint(g);
        }

        if(x>EspANCHO||y>EspALTO) {
            vivo=false;
        }
        choque();
    }

    @Override
    public void moverse() {
        if(vivo&&x>=EspANCHO-ANCHO-ANCHO/4&&swgrPrincipal.getMovimientoH()) {
            swgrPrincipal.setMovimientoH(false);
            swgrPrincipal.setMovimientoV(true);
        }
        if(vivo&&x<=0&&!swgrPrincipal.getMovimientoH()) {
            swgrPrincipal.setMovimientoH(true);
            swgrPrincipal.setMovimientoV(true);
        }
        if(vivo&&swgrPrincipal.getMovimientoH()) {
            x+=5;
        } else if(vivo&&!swgrPrincipal.getMovimientoH()) {
            x-=5;
        }
        if(temp<=0){
            reset();
        }
        if(vivo&&tempDisparo<=0) {
            disparar();
        }
        //System.out.println("espAncho: " + (EspANCHO-ANCHO-ANCHO/4) + "temp: "+temp+" moveH: "+ swgrPrincipal.getMovimientoH() +" moveV: " + swgrPrincipal.getMovimientoV() + " x: " + x);
    }
    //Rectangle2D DisparoNaveInvadidaHitbox = new Rectangle2D.Double(x, y, ANCHO, ALTO);
    private void disparar() {
        DisparoNaveInvasora disparo = new DisparoNaveInvasora(x,y,EspANCHO,EspALTO,ANCHO,ALTO);
        disparos.add(disparo);
        tempDisparo=random.nextInt(max - min + 1) + min;
    }
    @Override
    public void destruirse() {
        vivo=false;
    }

    public void reset() {
        temp=100;
    }

    public void choque() {
        if(swgrPrincipal.getNaveInvadida().getVidas()>0) {
            for(int x=0;x<disparos.size();x++) {
                if(!disparos.get(x).getDesaparece()) {
                    Rectangle2D disparoReact = disparos.get(x).getBoundsDisparo();
                    Rectangle2D naveReact = swgrPrincipal.getNaveInvadida().getBoundsNaveInvadida();
                    if(disparoReact.intersects(naveReact)){
                        swgrPrincipal.getNaveInvadida().vidaMenos();
                        disparos.get(x).desaparecer();
                        //System.out.println(swgrPrincipal.getNaveInvadida().getVidas()); TODO BORRAR
                    }
                }
            }
        }
    }


    public Rectangle2D getBoundsNaveInvasora() {
        return new Rectangle2D.Double(x, y, ANCHO, ALTO);
    }

    public Boolean getVivo() {
        return vivo;
    }
    public void setVivo(Boolean vivo) {
        this.vivo = vivo;
    }
    public Integer getY() {
        return y;
    }
    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getX() {
        return x;
    }
    public void setX(Integer x) {
        this.x = x;
    }

    public List<DisparoNaveInvasora> getDisparos() {
        return disparos;
    }
}
