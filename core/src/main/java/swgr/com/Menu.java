package swgr.com;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JPanel implements ActionListener {

    private JFrame frame = new JFrame("Space Invaders");
    private Integer ANCHO = 600;
    private Integer ALTO = 700;
    private Timer timer;
    private Image fondoImage;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Menu window = new Menu();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Menu() {
        initialize();
        timer = new Timer(10, this);
        timer.start();
    }

    public void correr() {
        initialize();
    }

    private void initialize() {
        frame.setBounds(0, 0, ANCHO, ALTO);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        fondoImage = new ImageIcon(getClass().getResource("/gif/fondo.gif")).getImage();

        this.setBounds(0, 0, ANCHO, ALTO);
        this.setOpaque(false);
        frame.setContentPane(this);
        this.setLayout(null);

        JLabel Titulo = new JLabel("Space Invaders");
        Titulo.setForeground(Color.WHITE);
        Titulo.setFont(new Font("Arial", Font.BOLD, 60));
        Titulo.setBounds(85, 93, 489, 114);
        this.add(Titulo);

        JButton Jugar = new JButton("Jugar");
        Jugar.setForeground(Color.WHITE);
        Jugar.setOpaque(false);
        Jugar.setContentAreaFilled(false);
        Jugar.setFont(new Font("Arial", Font.BOLD, 40));
        Jugar.setBounds(102, 324, 395, 74);
        Jugar.setRequestFocusEnabled(true);
        Jugar.setFocusable(false);
        Jugar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        Jugar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                swgrPrincipal swgrPrincipal = new swgrPrincipal();
                swgrPrincipal.correr();
            }
        });
        this.add(Jugar);

        JButton Salir = new JButton("Salir");
        Salir.setForeground(Color.WHITE);
        Salir.setOpaque(false);
        Salir.setContentAreaFilled(false);
        Salir.setFont(new Font("Arial", Font.BOLD, 40));
        Salir.setBounds(102, 440, 395, 74);
        Salir.setRequestFocusEnabled(true);
        Salir.setFocusable(false);
        Salir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }
        });
        Salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }

        });
        this.add(Salir);

        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(fondoImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
