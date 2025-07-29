package swgr.com;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

public class NaveJefeFinal extends NaveInvasora{

    private Integer ANCHO;
    private Integer ALTO;
    private Integer vida=100;
    private Boolean perdiendo=false;
    private Boolean cargado=false;
    private Boolean cargando=false;
    private Boolean disparando=false;
    private Integer tempDestrutyendose=300;
    private Integer tempVidaPerdidaJefe=9;
    private Integer tempEntreRayo=200;
    private Integer tempCarga=100;
    private Integer tempDisparo=100;
    private ImageIcon explosion;
    private List<DisparoNaveInvasora> disparos = new ArrayList<>();

    public NaveJefeFinal (Integer x, Integer y, Integer EspANCHO, Integer EspALTO, swgrPrincipal swgrPrincipal) {
        super(x, y, EspANCHO, EspALTO, swgrPrincipal);
        this.EspANCHO=EspANCHO;
        this.EspALTO=EspALTO;
        ANCHO=300;
        ALTO=300;
        this.spawn(EspANCHO, this);
        y=10;
    }

    @Override
    public void paint(Graphics g) {
        //System.out.println(vida);
        if(perdiendo==true&&vivo) {
            explosion = new ImageIcon(getClass().getResource("/gif/explosion1.gif"));
            g.drawImage(explosion.getImage(), x, y, ANCHO, ALTO, null);
            tempDestrutyendose--;
            if(tempDestrutyendose<=0) {
                vivo=false;
            }
        } else if(vivo) {
            if(tempEntreRayo>0) {
                ImageIcon nave = new ImageIcon(getClass().getResource("/imagenes/estrelladelamuerteboss.png"));
                g.drawImage(nave.getImage(), x, y, ANCHO, ALTO, null);
                temp--;
                tempVidaPerdidaJefe--;
                tempEntreRayo--;
            }

            for(DisparoNaveInvasora disparo : disparos) {
                disparo.paint(g);
            }

            if(tempEntreRayo<=0&&!cargado) {
                cargado=true;
            }
            if(cargado&&tempEntreRayo<=0) {
                ImageIcon nave = new ImageIcon(getClass().getResource("/imagenes/estrelladelamuertecargandoataque.png"));
                g.drawImage(nave.getImage(), x, y, ANCHO, ALTO, null);
                tempCarga--;
                if(tempCarga<=0) {
                    disparando=true;
                }
            }
            if(disparando) {
                disparos.add(new DisparoNaveInvasora(x-ANCHO/2+ANCHO/4,y+ALTO/2,EspANCHO,EspALTO,ANCHO/2,ALTO/3,ANCHO,ALTO,"/imagenes/rayolaserestrelladelamuerte.png"));
                tempDisparo--;
                if(tempDisparo<=0) {
                    disparando=false;
                    cargando=false;
                    tempEntreRayo=200;
                    tempCarga=100;
                    tempDisparo=100;
                }
            }
        }
        impacto(swgrPrincipal.getNaveInvadida());
        moverse();
        disparar();
    }

    public void moverse(){
        if((!(cargando||disparando))&&!perdiendo) {
            if(vivo&&x>=EspANCHO-ANCHO+ANCHO/10&&swgrPrincipal.getMovimientoH()) {
                swgrPrincipal.setMovimientoH(false);
                swgrPrincipal.setMovimientoV(true);
            }
            if(vivo&&x<=-60&&!swgrPrincipal.getMovimientoH()) {
                swgrPrincipal.setMovimientoH(true);
                swgrPrincipal.setMovimientoV(true);
            }
            if(vivo&&swgrPrincipal.getMovimientoH()) {
                x+=1;
            } else if(vivo&&!swgrPrincipal.getMovimientoH()) {
                x-=1;
            }
            if(temp<=0){
                reset();
            }
        }
    }

    public void impacto(NaveInvadida nave) {
        if(vivo&&!perdiendo) {
            for(int z=0;z<nave.getDisparos().size();z++) {
                Rectangle2D jefeReact = new Rectangle2D.Double(x,y,ANCHO,ALTO-ALTO/10);
                Rectangle2D disparoReact = nave.getDisparos().get(z).getBoundsDisparo();
                if(jefeReact.intersects(disparoReact)) {
                    vidaMenos();
                    nave.getDisparos().get(z).desaparecer();
                }
            }
        }
        if(vivo&&!perdiendo) {
            if(swgrPrincipal.getNaveInvadida().getVidas()>0) {
                for(int x=0;x<disparos.size();x++) {
                    //System.out.println("-x:"+disparos.get(x).getX());
                    if(!disparos.get(x).getDesaparece()) {
                        DisparoNaveInvasora disparo = disparos.get(x);
                        Rectangle2D disparoReact = disparo.getBoundsDisparoJefe();
                        Rectangle2D naveReact = nave.getBoundsNaveInvadida();
                        if(disparoReact.intersects(naveReact)){
                            nave.vidaMenos();
                            disparos.get(x).desaparecer();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void destruirse() {

    }

    public void vidaMenos(){
        if(vida>0&&tempVidaPerdidaJefe<=0) {
            vida--;
            tempVidaPerdidaJefe=9;
        } else if(vida<=0) {
            perdiendo=true;
        }
    }

    private Random random = new Random();
    private int max=300;
    private int min=150;
    private int tempDisparo1=random.nextInt(max - min + 1) + min;
    private int tempDisparo2=random.nextInt(max - min + 1) + min;
    private int tempDisparo3=random.nextInt(max - min + 1) + min;
    private int tempDisparo4=random.nextInt(max - min + 1) + min;

    public void disparar() {
        if(tempDisparo1<=0) {
            disparos.add(new DisparoNaveInvasora(x-ANCHO/4,y+ALTO-ALTO/2-ALTO/4,EspANCHO,EspALTO,ANCHO,ALTO));
            tempDisparo1=random.nextInt(max - min + 1) + min;
        }
        if(tempDisparo2<=0) {
            disparos.add(new DisparoNaveInvasora(x-ANCHO/2,y+ALTO-ALTO/2-ALTO/8,EspANCHO,EspALTO,ANCHO,ALTO));
            tempDisparo2=random.nextInt(max - min + 1) + min;
        }
        if(tempDisparo3<=0) {
            disparos.add(new DisparoNaveInvasora(x+ANCHO/2,y+ALTO-ALTO/2-ALTO/8,EspANCHO,EspALTO,ANCHO,ALTO));
            tempDisparo3=random.nextInt(max - min + 1) + min;
        }
        if(tempDisparo4<=0) {
            disparos.add(new DisparoNaveInvasora(x+ANCHO/4,y+ALTO-ALTO/2-ALTO/4,EspANCHO,EspALTO,ANCHO,ALTO));
            tempDisparo4=random.nextInt(max - min + 1) + min;
        }
        tempDisparo1--;
        tempDisparo2--;
        tempDisparo3--;
        tempDisparo4--;
    }

}
