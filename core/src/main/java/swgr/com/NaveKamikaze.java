package swgr.com;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class NaveKamikaze extends NaveInvasora{
    private Integer temp=100;
    private int aux=0;
    private Integer xLanzamiento;
    private Integer yLanzamiento;
    private List<Integer> movimientosX = new ArrayList<>();
    private List<Integer> movimientosY = new ArrayList<>();

    public NaveKamikaze(Integer x, Integer y, Integer EspANCHO, Integer EspALTO, swgrPrincipal swgrPrincipal) {
        super(x, y, EspANCHO, EspALTO, swgrPrincipal);
    }

    private Integer recorreX=0;
    private Integer recorreY=0;

    @Override
    public void paint(Graphics g) {
        if(vivo) {
            ImageIcon nave = new ImageIcon(getClass().getResource("/imagenes/navekamikaze.png"));
            g.drawImage(nave.getImage(), x, y, ANCHO, ALTO, null);
            temp--;
            if(temp<=0&&aux==2&&vivo) {
                int aux=0;
                try {
                    if(recorreY<movimientosY.size()&&swgrPrincipal.getNaveInvadida().getVivo()) {
                        x+=movimientosX.get(recorreX);
                        recorreX++;
                        x+=movimientosX.get(recorreX);
                        recorreX++;
                        aux=1;
                    }
                } catch(IndexOutOfBoundsException e) {y+=2;}
                try {
                    if(recorreY<movimientosY.size()&&swgrPrincipal.getNaveInvadida().getVivo()) {
                        y+=movimientosY.get(recorreY);
                        recorreY++;
                        y+=movimientosY.get(recorreY);
                        recorreY++;
                        aux=1;
                    }
                } catch(IndexOutOfBoundsException e) {y+=2;}
                if(aux==0) {
                    y+=2;
                }
            }
        }
        if(y>=EspALTO) {
            vivo=false;
            //System.out.println("vivo: " + vivo);
        }
    }

    @Override
    public void moverse() {
        if(aux==0) {
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
            if((y+ALTO)-20>=EspALTO) {
                vivo=false;;
            }
        }
        if(aux==1) {
            int posX=x,posY=y;
            while(!(posX==xLanzamiento&&posY==yLanzamiento)) {
                if(posX<xLanzamiento) {
                    posX+=1;
                    movimientosX.add(1);
                } else if(posX>xLanzamiento) {
                    posX-=1;
                    movimientosX.add(-1);
                }
                if(posY<yLanzamiento) {
                    posY+=1;
                    movimientosY.add(1);
                } else if(posY>yLanzamiento) {
                    posY-=1;
                    movimientosY.add(-1);
                }
            }
            aux=2;
        }
        if(vivo&&y>=EspALTO-EspALTO/2-EspALTO/8&&aux==0&&swgrPrincipal.getNaveInvadida().getVivo()) {
            xLanzamiento=swgrPrincipal.getNaveInvadida().getX();
            yLanzamiento=swgrPrincipal.getNaveInvadida().getY();
            aux=1;
        }
        //System.out.println("espAncho: " + (EspANCHO-ANCHO-ANCHO/4) + "temp: "+temp+" moveH: "+ swgrPrincipal.getMovimientoH() +" moveV: " + swgrPrincipal.getMovimientoV() + " x: " + x);
    }



    public void reset() {
        temp=100;
    }
}
