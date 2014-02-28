/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package parabola;

/**
 *
 * @author alexgarza
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.Vector;
import java.lang.Math;

public class Tiro extends JFrame implements Runnable, KeyListener, MouseListener
{
	private static final long serialVersionUID = 1L;
	// Se declaran las variables.
	private int direccion;    // Direccion del elefante
	private int vidas;    // vidas del elefante
	private int puntaje;   // el puntaje de usuario.
	private final int MIN = -5;    //Limite minimo al generar el numero random. 
	private final int MAX = 6;    //Limite maximo al generar el numero random.
	private static final int WIDTH = 1000;    //Ancho del JFrame
	private static final int HEIGHT = 600;    //Alto del JFrame
	private Image fondo;
        private boolean info;
        private Image pausaImagen;
        private Image infoImagen;
        private Image creditos;
        private Image dbImage;	// Imagen a proyectar
	private Image gameover;	// Imagen al finalizar el juego.
	private Graphics dbg;	// Objeto grafico
	private SoundClip explosion; //sonido explosion
    private SoundClip moneda; //sonido explosion
    private SoundClip fondoM;
	private int gravity;
        private boolean semueve;
        private int perdidos;
        private int i;
        private int vel;
        private int velX,velY;
        
	private Bueno barril;    // Objeto de la clase Elefante
	
	private Malo banana;    //Objeto de la clase Raton
	
	
	private String nombreArchivo;    //Nombre del archivo.
	private String[] arr;    //Arreglo del archivo divido.
	private long tiempoActual;	//Tiempo de control de la animación
        private boolean pausa;
        private boolean click;
        private boolean sonidillo;
        private int clickX; 
        private int clickY; 
        
        public Tiro() {
        init();
        start();
    }
        public void init() {
        direccion = 0; //inicia estático
        click = false; //inicia sin click
        pausa = false;  //se inicia sin pausa
        vidas = 5; // cantidad inicial de vidas
        puntaje = 0; // socre inicial
        nombreArchivo = "puntajes.txt"; // nombre del archivo a modificar donde se guardara la informacion del juego
        sonidillo = true; // boooleana apra prender sonido
        gravity = 1; //gravity del jeugo
        semueve = true; //booleana para ver si ya empezo a moverse
        info = false; //booleana para desplegar instrucciones
        perdidos = 0;// cantidad de perdidos
        velX = (int)(Math.random() * 5 + 13); // posiciones de velocidad x
        velY = (int)(Math.random() * 12 + 15); //posiciones de velocidad y
        setSize(1024, 640);  //se redimenciona el applet
        setBackground(Color.white);  //fondo blanco del applet
        addKeyListener(this);  //se añade el keyListener al applet
        addMouseListener(this);  //se añade el mouseListeenr al applet
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cerrar aplicación al cerrar ventana
        
        //URL's de las imágenes de ambas animaciones y los sonidos
        fondo = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/fondo.jpg"));
        
        pausaImagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/pause.gif"));
        infoImagen = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/info.gif"));
        creditos = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/Creditos.gif"));
        Image barril0 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/frame_000.gif"));
	Image barril1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/frame_001.gif"));
	Image barril2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/frame_002.gif"));
	Image barril3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/frame_003.gif"));
	Image barril4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/frame_004.gif"));
	Image barril5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/frame_005.gif"));
	Image barril6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/frame_006.gif"));
	Image barril7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/frame_007.gif"));
        
        
        
	Image banana1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b1.gif"));
	Image banana2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b2.gif"));
        Image banana3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b3.gif"));
        Image banana4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b4.gif"));
        Image banana5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b5.gif"));
        Image banana6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b6.gif"));
        Image banana7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b7.gif"));
        Image banana8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b8.gif"));
        Image banana9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b9.gif"));
        Image banana10 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b10.gif"));
        Image banana11 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b11.gif"));
        Image banana12 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/b12.gif"));
        
        
        explosion = new SoundClip("sounds/monkey.wav"); //sonido de explosion
        moneda = new SoundClip("sounds/money.wav");  //sonido de explosion
        fondoM = new SoundClip("sounds/junglerock.mp3");  //sonido de explosion
        
        //Se crea un nuevo objeto bueno y se añaden los cuadros de animación
        barril = new Bueno(getWidth() / 2, getHeight() - 50, barril0);
        barril.sumaCuadro(barril0, 75);
        barril.sumaCuadro(barril1, 75);
        barril.sumaCuadro(barril2, 75);
        barril.sumaCuadro(barril3, 75);
        barril.sumaCuadro(barril4, 75);
        barril.sumaCuadro(barril5, 75);
        barril.sumaCuadro(barril6, 75);
        barril.sumaCuadro(barril7, 75);
       
        
        // del objeto malo se crea la banana y se anima.
        banana = new Malo(50, getHeight() - 100, banana1, velX, velY);
        
        banana.sumaCuadro(banana1, 100);
        banana.sumaCuadro(banana2, 100);
        banana.sumaCuadro(banana3, 100);
        banana.sumaCuadro(banana4, 100);
        banana.sumaCuadro(banana5, 100);
        banana.sumaCuadro(banana6, 100);
        banana.sumaCuadro(banana7, 100);
        banana.sumaCuadro(banana8, 100);
        banana.sumaCuadro(banana9, 100);
        banana.sumaCuadro(banana10, 100);
        banana.sumaCuadro(banana11, 100);
       
        
        }
        
         public void start() {
        //Crea el thread
        Thread hilo = new Thread(this);
	//Inicializa el thread
        hilo.start();
    }
         
         @Override
    public void run () {
        tiempoActual = System.currentTimeMillis();
        
        while (vidas > 0) {
            
            if (sonidillo) {
                if (!fondoM.getLooping()) {
                    fondoM.setLooping(true);
                    fondoM.play(); 
                }
            }
            else {
                fondoM.setLooping(false);
                fondoM.stop();
            }

            // Se ejecutará siempre
            //Verifica si el juego está en pausa, de ser así, no actualizará
            //de lo contrario, actualizará
            if (!pausa) {
                actualiza();
                checaColision();
            }
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            
            try	{
                // El thread se duerme.
                Thread.sleep (20);
            }
            
            catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
        }
    }
    
    /**
    
      * Metodo <I>actualiza</I> sobrescrito de la clase 
    * En este metodo se actualizan las posciciones de la barril
    *  y de la banana. asi como actualizar las booleanas
    * 
    */
    public void actualiza() {
         //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
         long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
         
         //Guarda el tiempo actual
       	 tiempoActual += tiempoTranscurrido;
         
         //Actualiza la animación con base en el tiempo transcurrido
         if (direccion != 0) {
             barril.actualiza(tiempoTranscurrido);
         }
         
         
         //Actualiza la animación con base en el tiempo transcurrido para cada malo
         if (click) {
             banana.actualiza(tiempoTranscurrido);
         }
         
         
         
         
         //Actualiza la posición de cada malo con base en su velocidad
         //banana.setPosY(banana.getPosY() + banana.getVel());
         
         
         
         if (banana.getPosX() != 50 || banana.getPosY() != getHeight() - 100) {
             semueve = false;
         }
         
         if (click) { // si click es true hara movimiento parabolico
             banana.setPosX(banana.getPosX() + banana.getVelX());
             banana.setPosY(banana.getPosY() - banana.getVelY());
             banana.setVelY(banana.getVelY() - gravity);
         }
         
         if (direccion == 1) { // velocidad de las barrils entre menos vidas menor el movimiento
             barril.setPosX(barril.getPosX() - vidas - 2);
         }
         
         else if (direccion == 2) {
             barril.setPosX(barril.getPosX() + vidas + 2);
         }
    }
    
    /**
     * Metodo usado para checar las colisiones del objeto tierra y asteroide
     * con las orillas del <code>JFrame</code> y entre sí.
     */
    public void checaColision() {
        //Verifica que la barril no choque con el applet por la derecha
        if (barril.getPosX() + barril.getAncho() > getWidth()) {
            barril.setPosX(getWidth() - barril.getAncho());
        }
        
        //Verifica que barril no choque con el applet por la izquierda
        if (barril.getPosX() < getWidth() / 2) {
            barril.setPosX(getWidth() / 2);
        }
        
        //Verifica que cada objeto malo no choque con el caballo
        if (barril.intersecta(banana)) {
            if (sonidillo) {
                moneda.play();  //reproducre sonidillo de choque corecto           
            }
            velX = (int)(Math.random() * 5 + 13); //genera nueva velocidad x
            velY = (int)(Math.random() * 12 + 15); // genera nueva veolicdad y
            banana.setContador(banana.getContador() + 1);
            banana.setPosX(50);// pone la espera en la posicion original
            banana.setPosY(getHeight() - 100); // pone la banana en la posicion original
            banana.setVelY(velY);//valor de velocidad
            puntaje += 2; // aumenta el score si intersecta
            click = false;
            semueve = true;
        }
        
        //Verifica que cada objeto malo choque con el applet
        if (banana.getPosY() + banana.getAlto() > getHeight()) {
            if (sonidillo) {
                explosion.play();  //reproducre sonidillo de bala           
            }
            velX = (int)(Math.random() * 5 + 13);
            velY = (int)(Math.random() * 12 + 15);
            banana.setPosX(50);
            banana.setPosY(getHeight() - 100);
            banana.setVelY(velY);
            perdidos++;
            click = false;
            semueve = true;

        }
        
        if (perdidos == 3) {
            vidas--;
            perdidos = 0;
        }
    }
    
    public void leeArchivo() throws IOException {
        BufferedReader fileIn;
                try {
                    fileIn = new BufferedReader(new FileReader(nombreArchivo));
                } catch (FileNotFoundException e){
                    File puntos = new File(nombreArchivo);
                    PrintWriter fileOut = new PrintWriter(puntos);
                    fileOut.println("512,0,512,100,0,0");
                    fileOut.close();
                    fileIn = new BufferedReader(new FileReader(nombreArchivo));
                }
                String dato = fileIn.readLine();
                while(dato != null) {
                    
                      arr = dato.split(",");
                      barril.setPosX(Integer.parseInt(arr[0])); //pos x de barril
                      barril.setPosY(Integer.parseInt(arr[1])); // pos y de barril
                      banana.setPosX(Integer.parseInt(arr[2])); // pos x de banana
                      banana.setPosY(Integer.parseInt(arr[3])); // posy de banana
                      banana.setVelX(Integer.parseInt(arr[4])); //vel de banana en x
                      banana.setVelY(Integer.parseInt(arr[5]));// vel de banana en y
                      vidas = (Integer.parseInt(arr[6])); // vidas
                      puntaje = (Integer.parseInt(arr[7])); //score actual
                      perdidos = (Integer.parseInt(arr[8])); //perdidos acomulados
                      if ((Integer.parseInt(arr[9])) == 1) { //checar respectivas booleanas
                          click = true;
                      }
                      else {
                          click = false;
                      }
                      if ((Integer.parseInt(arr[10])) == 1) {
                          sonidillo = true;
                      }
                      else {
                          sonidillo = false;
                      }
                      
                      dato = fileIn.readLine();
                }
                fileIn.close();
        }
    public void grabaArchivo() throws IOException {
            PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
            String graba; //graba el archivo de (barrilposx,barrilposy,bananaposx,bananaposy,velocidadx,veloidady,vidas, score , choque,click,sonidillo)
            graba = Integer.toString(barril.getPosX()) + "," + Integer.toString(barril.getPosY()) + "," + Integer.toString(banana.getPosX()) + "," + Integer.toString(banana.getPosY()) + "," + Integer.toString(banana.getVelX()) + "," + Integer.toString(banana.getVelY()) + "," + Integer.toString(vidas)+ "," + Integer.toString(puntaje) + "," + Integer.toString(perdidos);
            if (click) {
                graba += ",1";
            }
            else {
                graba += ",0";
            }
            
            if (sonidillo) {
                graba += ",1";
            }
            else {
                graba += ",0";
            }
            fileOut.println(graba);
            fileOut.close();
    }
    
    /**
     * Método <I>keyPressed<I/> de la clase <code>KeyListener</code>
     * @param e es el <code>evento</code> del teclado
     */
    @Override
    public void keyPressed(KeyEvent e) {
        // Verifica si se oprime la tecla P para pausar o reanudar el juego
        if (e.getKeyCode() == KeyEvent.VK_P) {
            //Se cambia el estado de la variable pausa dependiendo de su
            //valor actual y desaparece el letrero de desaparece
            pausa = !pausa;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_I) {
            //Se cambia el estado de la variable pausa dependiendo de su
            //valor actual y desaparece el letrero de desaparece
            info = !info;
            pausa = !pausa;
        }

        if (e.getKeyCode() == KeyEvent.VK_G) { //tecla para grabar el juego
            if (!info) {
                try {
                    grabaArchivo();
            } catch(IOException f) {
                    System.out.println("");
              }
            }
        }
        
        if (e.getKeyCode() == KeyEvent.VK_C) { // tecla para cargar el juego anterior
            try {
                leeArchivo();
            } catch(IOException f) {
                System.out.println("");
            }
        }

        //Se cambia la dirección del bueno con base en la tecla oprimida
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            direccion = 1;
        }
        
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direccion = 2;
        }
    }
    
    /**
     * Método <I>keyReleased<I/> de la clase <code>KeyListener</code>
     * @param e es el <code>evento</code> del teclado
     */
    @Override
    public void keyReleased(KeyEvent e) { //detener direcciones
        if (e.getKeyCode() == KeyEvent.VK_LEFT) { 
            direccion = 0;
        }
        
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direccion = 0;
        }
        
                if (e.getKeyCode() == KeyEvent.VK_S) {//encender apagar musica y sonidillos.
            sonidillo = !sonidillo;
        }
    }
    
    /**
     * Método <I>KeyTyped<I/> de la clase <code>KeyListener</code>
     * @param e es el <code>evento</code> del teclado
     */
    @Override
    public void keyTyped(KeyEvent e) {
        
    }
    
    /**
     * Método <I>mouseClicked<I/> de la clase <code>MouseListener</code>
     * @param e es el <code>evento</code> del mouse
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //verifica que el click haya sido dentro del objeto caballo
        if (banana.clickDentro(e.getX(), e.getY())) {
            //cambia el estado de la variable click
            if (semueve) {
                click = true;
            }
        }
    }
    
    /**
     * Método <I>mouseEntered<I/> de la clase <code>MouseListener</code>
     * @param e es el <code>evento</code> del mouse
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        
    }
   
    /**
     * Método <I>mouseExited<I/> de la clase <code>MouseListener</code>
     * @param e es el <code>evento</code> del mouse
     */
    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    /**
     * Método <I>mousePressed<I/> de la clase <code>MouseListener</code>
     * @param e es el <code>evento</code> del mouse
     */
    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    /**
     * Método <I>mouseReleased<I/> de la clase <code>MouseListener</code>
     * @param e es el <code>evento</code> del mouse
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    /**
     * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>,
     * En este metodo lo que hace es actualizar el contenedor
     * @param g es el <code>objeto grafico</code> usado para dibujar.
     */
    @Override
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null){
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }

	// Actualiza la imagen de fondo.
	dbg.setColor(getBackground ());
	dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);

	// Actualiza el Foreground.
	dbg.setColor(getForeground());
	paint1(dbg);
        
	// Dibuja la imagen actualizada
	g.drawImage(dbImage, 0, 0, this);
    }
    
    /**
     * El método <I>paint1</I> muestra en pantalla la animación
     * @param g
    */
    public void paint1(Graphics g) {
        g.setColor(Color.white);
        //Verifica que los objetos existan
        if (barril != null && banana != null ) { 
            
            if (info) {
                g.drawImage(infoImagen, 0, 0, getWidth(), getHeight(), this);
            }

            else {

                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
                
                // Dibuja el caballo
                g.drawImage(barril.getImagen(), barril.getPosX(), barril.getPosY(), this);
                //Dibuja los objetos malos
                g.drawImage(banana.getImagen(), banana.getPosX(), banana.getPosY(), this);
                //Verifica que haya desaparecido un objeto malo y dibuja el mensaje desaparece
                g.drawString("Vidas: " + vidas + "   Puntos: " + puntaje, getWidth() - 200, 50);
                if (pausa) {
                     //Dibuja el mensaje de pausado
                    g.drawImage(pausaImagen, getWidth() / 2 - 202, getHeight() / 2 - 197, 405, 392, this);
                }  
            }
            
            if (vidas <= 0) {
                g.drawImage(creditos, 0, 0, getWidth(), getHeight() , this);
            }
        }
        
        else {
            //Da un mensaje mientras se carga el dibujo
            g.drawString("No se cargo la imagen..", 20, 20);
        }
    }
        
    
}

