import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements KeyListener {
        
    private final int TOTAL_IMG=6;
    private final int AN_DELAY=120;
    protected ImageIcon images[];

    public int x=512;
    public int y=384;
    private int xx=0;
    private int yy=0;
    private int speed = 0;
    
    private boolean UP = true;
    private boolean DOWN = false;
    private boolean LEFT = false;
    private boolean RIGTH = false;
    private boolean FIRE = false;
    private boolean MOVE = false;
        
    private Timer animationTimer;	

    public Game(){
        images =new ImageIcon[TOTAL_IMG];
        images[0]=new ImageIcon(getClass().getResource("images/up.gif"));
        images[1]=new ImageIcon(getClass().getResource("images/down.gif"));
        images[2]=new ImageIcon(getClass().getResource("images/rigth.gif"));
        images[3]=new ImageIcon(getClass().getResource("images/left.gif"));
        images[4]=new ImageIcon(getClass().getResource("images/bg.jpg"));
        images[5]=new ImageIcon(getClass().getResource("images/fire.gif"));
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        images[4].paintIcon(this,g,0,0);
        
        g.setColor(Color.WHITE);
        g.setFont( new Font("SansSerif", Font.BOLD, 20 ) );
        g.drawString("Year 2007",250,20);
        
        if(UP && !LEFT && !RIGTH && !DOWN) {
            images[0].paintIcon(this,g,x,y);
        } else if (!UP && DOWN && !LEFT && !RIGTH) {
            images[1].paintIcon(this,g,x,y);
        } else if (!LEFT && RIGTH && !UP && !DOWN) { 
            images[2].paintIcon(this,g,x,y);
        } else if (LEFT && !RIGTH && !UP && !DOWN) {
            images[3].paintIcon(this,g,x,y);
        }
        
        /*if (FIRE) {
            images[5].paintIcon(this,g,xx,yy);
        }
        */
    }
            
    public void startAnimation(){
        if(animationTimer == null) {
            animationTimer=new Timer(AN_DELAY,new TimerHandler());
            animationTimer.start();
        } else {
            if(!animationTimer.isRunning()) {
                animationTimer.restart();
            }
        }
    }
    
    public void stopAnimation(){
        animationTimer.stop();
    }
    
    
    private void resetDirections(boolean up, boolean left, boolean down, boolean right) {
        UP = up;
        DOWN = down;
        LEFT = left;
        RIGTH = right;
    }
    
    public void keyPressed( KeyEvent event ) {
        switch(event.getKeyCode()){
            case KeyEvent.VK_UP&0xff: {
                speed = 5;
                MOVE = true;
                
                resetDirections(true, false, false, false);
            } break;
            
            case KeyEvent.VK_LEFT&0xff: {
                speed = 5;
                MOVE = true;
                
                resetDirections(false, true, false, false);
            } break;
            
            case KeyEvent.VK_RIGHT&0xff: {
                speed = 5;
                MOVE = true;
                
                resetDirections(false, false, false, true);

            } break;
            
            case KeyEvent.VK_DOWN&0xff: {
                speed = 5;
                MOVE = true;
                
                resetDirections(false, false, true, false);
            } break;
            
            case KeyEvent.VK_SPACE&0xff: {
                speed = 0;
                
                //FIRE = true;
            } break;
            
            case KeyEvent.VK_ESCAPE: {
                stopAnimation();
                
                int result = JOptionPane.showConfirmDialog(null,"Do you really want to exit game?", "Warning", 
                                JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                if(result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    startAnimation();
                } 
            } break;
            
            default : System.out.println("Default Key Pressed!!!");
        }
    } 
         
    public void keyReleased( KeyEvent event ) {
        speed = 0;
        MOVE = false;
    } 
    
    public void keyTyped( KeyEvent event ) {} 
    
    public void Up(){
        if(UP && !DOWN && !LEFT && !RIGTH) {
            y-= 2 * speed;
            speed++;
        }
    }
    
    public void Down() {
        if (!UP && DOWN && !LEFT && !RIGTH) {
            y += 2 * speed;
            speed++;
        }
    }
        
    public void Left() {
        if (!UP && !DOWN && LEFT && !RIGTH) {
            x -= 2 * speed;
            speed++;
        }
    }
    
    public void Rigth() {
        if (!UP && !DOWN && !LEFT && RIGTH) {
            x += 2*speed;
            speed++;
        }
    }
   
    public void Fire() {
        yy = y;
        
        if (FIRE) {
            if (speed>300) { FIRE=false; speed=0;yy=y;} yy-= 2*speed; speed=speed+7; 
        }
    }
    private class TimerHandler implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            if (MOVE) {
                Up();
                Left();
                Rigth();
                Down();
                Fire();
            }
            
            repaint();
        }
    }
}
        
class Main {
    public static void main(String args[]){
        JFrame frame =new JFrame("Game Frame");
        Game Tanks=new Game();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(Tanks);
        frame.addKeyListener(Tanks);
        frame.setVisible(true);
        frame.setSize(1024,768);
        frame.setResizable(false);
        Tanks.startAnimation();
    }
}
	