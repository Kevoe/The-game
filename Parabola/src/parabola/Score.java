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

public class Score extends JFrame implements Runnable, KeyListener
{
	private static final long serialVersionUID = 1L;
	// Se declaran las variables.
	private int direccion;    // Direccion del elefante
	private int vidas;    // vidas del elefante
	private int score;   // el puntaje de usuario.
	private final int MIN = -5;    //Limite minimo al generar el numero random. 
	private final int MAX = 6;    //Limite maximo al generar el numero random.
	private static final int WIDTH = 1000;    //Ancho del JFrame
	private static final int HEIGHT = 600;    //Alto del JFrame
	private Image dbImage;	// Imagen a proyectar
	private Image gameover;	// Imagen al finalizar el juego.
	private Graphics dbg;	// Objeto grafico
	private SoundClip sonido;    // Objeto SoundClip
	private SoundClip rat;    // Objeto SoundClip
	private SoundClip bomb;    //Objeto SoundClip
	private SoundClip musica;    //musica de fondo
	private Elefante dumbo;    // Objeto de la clase Elefante
	private Animacion animElefante;	//Animación del elefante
	private Raton[] raton;    //Objeto de la clase Raton
	private Animacion animRaton;	//Animación del ratón
	private Vector vec;    // Objeto vector para agregar el puntaje.
	private String nombreArchivo;    //Nombre del archivo.
	private String[] arr;    //Arreglo del archivo divido.
	private long tiempoActual;	//Tiempo de control de la animación
       

    /**
     * Creates a new instance of <code>Score</code>.
     */
	public Score() {
		nombreArchivo = "Puntaje.txt";
		vec = new Vector();
		score = 0;
		vidas = 10;    // Le asignamos un valor inicial al vidas
		direccion = 4;    // Direccion hacia la derecha
		int posX = (int) (Math.random() *(WIDTH / 4));    // posicion en x es un cuarto del JFrame
		int posY = (int) (Math.random() *(HEIGHT / 4));    // posicion en y es un cuarto del JFrame
		
		//Se cargan las imágenes(cuadros) para la animación del elefante
		Image elefante1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/elefante1.png"));
		Image elefante2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/elefante2.png"));
		Image elefante3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/elefante3.png"));
		Image elefante4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/elefante4.png"));
		Image elefante5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/elefante5.png"));
		Image elefante6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/elefante6.png"));
		
		//Se crea la animación
		animElefante = new Animacion();
		animElefante.sumaCuadro(elefante1, 100);
		animElefante.sumaCuadro(elefante2, 100);
		animElefante.sumaCuadro(elefante3, 100);
		animElefante.sumaCuadro(elefante4, 100);
		animElefante.sumaCuadro(elefante5, 100);
		animElefante.sumaCuadro(elefante6, 100);
		
		dumbo = new Elefante(posX,posY,elefante1);
		
		raton = new Raton[3];    //se crea y define el arreglo raton.
		
		//Se cargan las imágenes(cuadros) para la animación del ratón
		Image raton1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse1.png"));
		Image raton2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse2.png"));
		Image raton3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse3.png"));
		Image raton4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse4.png"));
		Image raton5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse5.png"));
		Image raton6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse6.png"));
		Image raton7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse7.png"));
		Image raton8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse8.png"));
		Image raton9 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse9.png"));
		Image raton10 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse10.png"));
		Image raton11 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse11.png"));
		Image raton12 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/mouse12.png"));
		
		//Se crea la animación
		animRaton = new Animacion();
		animRaton.sumaCuadro(raton1, 100);
		animRaton.sumaCuadro(raton2, 100);
		animRaton.sumaCuadro(raton3, 100);
		animRaton.sumaCuadro(raton4, 100);
		animRaton.sumaCuadro(raton5, 100);
		animRaton.sumaCuadro(raton6, 100);
		animRaton.sumaCuadro(raton7, 100);
		animRaton.sumaCuadro(raton8, 100);
		animRaton.sumaCuadro(raton9, 100);
		animRaton.sumaCuadro(raton10, 100);
		animRaton.sumaCuadro(raton11, 100);
		animRaton.sumaCuadro(raton12, 100);

		for(int i=0;i<raton.length;i++){
			int posrX = (int) (Math.random() *(WIDTH / 4)) + WIDTH / 2;    //posision x es tres cuartos del JFrame
			int posrY = (int) (Math.random() *(HEIGHT / 4)) + HEIGHT / 2;    //posision y es tres cuartos del JFrame
			raton[i] = new Raton(posrX,posrY,raton1);
		}
		
		addKeyListener(this);
		sonido = new SoundClip("elephant.wav");
        rat = new SoundClip("mice.wav");
	 	musica = new SoundClip("elephant_greeting.wav");
	 	bomb = new SoundClip("Explosion.wav");
		gameover = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("gameover.jpg"));	
		musica.setLooping(true);
		musica.play();

		// Declaras un hilo
		Thread t = new Thread (this);
		// Empieza el hilo
		t.start ();
    }

	/** 
	 * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
     * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se incrementa
     * la posicion en x o y dependiendo de la direccion, finalmente 
     * se repinta el <code>JFrame</code> y luego manda a dormir el hilo.
     * 
     */
	public void run () {
		//Tiempo actual del sistema
		tiempoActual = System.currentTimeMillis();
		while (vidas>0) {
			actualiza();    //actualiza la posicion del raton.
			checaColision();    //checa colision del elefante y raton ademas de con el JFrane.
			repaint();    // Se actualiza el <code>JFrame</code> repintando el contenido.
			try	{
				// El thread se duerme.
				Thread.sleep (20);
			}
			catch (InterruptedException ex)	{
				System.out.println("Error en " + ex.toString());
			}
		}
		musica.stop();    //para la musica al terminarse las vidas.
		// pide el nombre de usuario
		String nombre = JOptionPane.showInputDialog("Cual es tu nombre?");
		JOptionPane.showMessageDialog(null,"El puntaje de " + nombre + " es: " + score,"PUNTAJE", JOptionPane.PLAIN_MESSAGE);
		try{
			leeArchivo();    //lee el contenido del archivo
			vec.add(new Puntaje(nombre,score));    //Agrega el contenido del nuevo puntaje al vector.
			grabaArchivo();    //Graba el vector en el archivo.
		}catch(IOException e){
			System.out.println("Error en " + e.toString());
		}
	}
	
	/**
	 * Metodo usado para actualizar la posicion del objeto raton.
	 * 
	 */
	public void actualiza(){
		//Determina el tiempo que ha transcurrido desde que el JFrame inicio su ejecución
         long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
            
         //Guarda el tiempo actual
       	 tiempoActual += tiempoTranscurrido;
       	 
       	 //Actualiza la animación del elefante en base al tiempo transcurrido
       	 animElefante.actualiza(tiempoTranscurrido);
		
		//Actualiza la animación del ratón en base al tiempo transcurrido
         animRaton.actualiza(tiempoTranscurrido);
		
		score += 5;  // el score se acumula en 5
		switch(direccion){
			case 1: { //se mueve hacia arriba con la flecha arriba.
				dumbo.setPosY(dumbo.getPosY() - (11 - vidas) * 2);
				break;    	
			}     
			case 2: { //se mueve hacia abajo con la flecha abajo.
				dumbo.setPosY(dumbo.getPosY() + (11 - vidas) * 2);
				break;    	
			} 
			case 3: { //se mueve hacia izquierda con la flecha izquierda.
				dumbo.setPosX(dumbo.getPosX() - (11 - vidas) * 2);
				break;    	
			}    
			case 4: { //se mueve hacia derecha con la flecha derecha.
				dumbo.setPosX(dumbo.getPosX() + (11 - vidas) * 2);
				break;    	
			}			
		}
		
		for(int i=0;i<raton.length;i++)	{
			//Acutalizo la posicion del raton
			if(dumbo.getPosX() > raton[i].getPosX()){
				raton[i].setPosX(raton[i].getPosX() + (11 - vidas));
			}else{
				raton[i].setPosX(raton[i].getPosX() - (11 - vidas));
			}	
			if(dumbo.getPosY() > raton[i].getPosY()){
				raton[i].setPosY(raton[i].getPosY() + (11 - vidas));
			}else{
				raton[i].setPosY(raton[i].getPosY() - (11 - vidas));
			}
		}
	}
	
	/**
	 * Metodo usado para checar las colisiones del objeto elefante y raton
	 * con las orillas del <code>Applet</code>.
	 */
	public void checaColision(){
		//Colision del elefante con el JFrame dependiendo a donde se mueve.
		switch(direccion){
			case 1: { //se mueve hacia arriba con la flecha arriba.
				if(dumbo.getPosY() < 0) {
					direccion = 2;
					sonido.play();
				}
				break;    	
			}     
			case 2: { //se mueve hacia abajo con la flecha abajo.
				if(dumbo.getPosY() + dumbo.getAlto() > getHeight()) {
					direccion = 1;
					sonido.play();
				}
				break;    	
			} 
			case 3: { //se mueve hacia izquierda con la flecha izquierda.
				if(dumbo.getPosX() < 0) {
					direccion = 4;
					sonido.play();
				}
				break;    	
			}    
			case 4: { //se mueve hacia derecha con la flecha derecha.
				if(dumbo.getPosX() + dumbo.getAncho() > getWidth()) {
					direccion = 3;
					sonido.play();
				}
				break;    	
			}			
		}
			
		//checa colision con el applet para cada raton
		for(int i=0;i<raton.length;i++)	{
			if (raton[i].getPosX() + raton[i].getAncho() > getWidth()){
				raton[i].setPosX(raton[i].getPosX() - (11 - vidas));
				rat.play();
			}
			if (raton[i].getPosX() < 0){
				raton[i].setPosX(raton[i].getPosX() + (11 - vidas));
				rat.play();
			}
			if (raton[i].getPosY() + raton[i].getAlto()  > getHeight()){
				raton[i].setPosY(raton[i].getPosY() - (11 - vidas));
				rat.play();
			}	
			if (raton[i].getPosY() < 0){
				raton[i].setPosY(raton[i].getPosY() + (11 - vidas));
				rat.play();
			}

		}

		for(int i=0;i<raton.length;i++)	{

			//Colision entre objetos
			if( dumbo.intersecta(raton[i]) ){
				bomb.play();   //sonido al colisionar
				//El elefante se mueve al azar en la mitad superior izquierda del JFrame.
				dumbo.setPosX((int)( Math.random() * (WIDTH/2 - dumbo.getAncho())));    
				dumbo.setPosY((int)( Math.random() * (HEIGHT/2 - dumbo.getAlto())));
				//Los ratones se mueven al azar en la mitad inferior derecha del JFrame.
				for (int j = 0; j < 3; j++){
					raton[j].setPosX((int)( Math.random() * WIDTH/2) + WIDTH/2 - raton[i].getAncho());
					raton[j].setPosY((int)( Math.random() * HEIGHT/2) + HEIGHT/2 - raton[i].getAlto());
				}
				vidas--;
				break;
			}
		}
	}
	
	/**
	 * Metodo <I>keyPressed</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al presionar cualquier la tecla.
	 * @param e es el <code>evento</code> generado al presionar las teclas.
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {    //Presiono flecha arriba
			direccion = 1;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {    //Presiono flecha abajo
			direccion = 2;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {    //Presiono flecha izquierda
			direccion = 3;
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {    //Presiono flecha derecha
			direccion = 4;
		}
	}
    
    /**
	 * Metodo <I>keyTyped</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al presionar una tecla que no es de accion.
	 * @param e es el <code>evento</code> que se genera en al presionar las teclas.
	 */
    public void keyTyped(KeyEvent e){
    	
    }
    
    /**
	 * Metodo <I>keyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al soltar la tecla presionada.
	 * @param e es el <code>evento</code> que se genera en al soltar las teclas.
	 */
    public void keyReleased(KeyEvent e){
    	
    }
    
	/**
	 * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>,
	 * heredado de la clase Container.<P>
	 * En este metodo se dibuja la imagen con la posicion actualizada,
	 * ademas que cuando la imagen es cargada te despliega una advertencia.
	 * @param g es el <code>objeto grafico</code> usado para dibujar.
	 */
	public void paint(Graphics g) {
		// Inicializan el DoubleBuffer
		if (dbImage == null) {
			dbImage = createImage(this.getSize().width, this.getSize().height);
			dbg = dbImage.getGraphics ();
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
	 * Metodo <I>paint1</I> 
	 * En este metodo se dibuja la imagen con la posicion actualizada,
	 * ademas que cuando la imagen es cargada te despliega una advertencia.
	 * @param g es el <code>objeto grafico</code> usado para dibujar.
	 */
    public void paint1 (Graphics g){
    	if (vidas>0) {
    		g.setColor(Color.yellow);
    		g.fillRect(0, 0, WIDTH, HEIGHT);
    		if (dumbo!=null) {
    			for(int i=0;i<raton.length;i++)	{
    				//Dibuja la imagen en la posicion actualizada
					if(raton[i] != null){
	    				g.drawImage(animElefante.getImagen(), dumbo.getPosX(),dumbo.getPosY(), this);
	    				g.drawImage(animRaton.getImagen(), raton[i].getPosX(),raton[i].getPosY(), this);
	    				g.setColor(Color.black);
	    				g.setFont(new Font("Serif", Font.BOLD, 18));
	    				g.drawString("| " + vidas + " | "  + score + " |", 50, 50);
					}
    			}
    		} else {
    			//Da un mensaje mientras se carga el dibujo	
    			g.drawString("No se cargo la imagen..",20,20);
    		}
    	} else {
    		g.drawImage(gameover,0,0, this);
    	}

    }
	
    /**
     * Metodo que lee a informacion de un archivo y lo agrega a un vector.
     *
     * @throws IOException
     */
    public void leeArchivo() throws IOException{
    	BufferedReader fileIn;
    	try{
    		fileIn = new BufferedReader(new FileReader(nombreArchivo));
    	} catch (FileNotFoundException e){
    		File puntos = new File(nombreArchivo);
    		PrintWriter fileOut = new PrintWriter(puntos);
    		fileOut.println("100,demo");
    		fileOut.close();
    		fileIn = new BufferedReader(new FileReader(nombreArchivo));
    	}
    	String dato = fileIn.readLine();

    	while(dato != null) {
    		arr = dato.split(",");
    		int num = (Integer.parseInt(arr[0]));
    		String nom = arr[1];
    		vec.add(new Puntaje(nom, num));
    		dato = fileIn.readLine();
    	}
    	fileIn.close();
    }

    /**
     * Metodo que agrega la informacion del vector al archivo.
     *
     * @throws IOException
     */
    public void grabaArchivo() throws IOException{
    	PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
    	for (int i=0; i<vec.size(); i++) {
    		Puntaje x;
    		x = (Puntaje) vec.get(i);
    		fileOut.println(x.toString());
    	}
    	fileOut.close();	
    }
	
    /**
     * Metodo principal
     *
     * @param args es un arreglo de tipo <code>String</code> de linea de comandos
     */
    public static void main(String[] args) {
    	// TODO code application logic here
    	Score score = new Score();
    	score.setSize(WIDTH, HEIGHT);
    	score.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	score.setVisible(true);
    }
}

