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
import java.awt.Image;
 
public class Elefante extends Animal{

	/**
	 * Metodo constructor que hereda los atributos de la clase <code>Animal</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto elefante.
	 * @param posY es el <code>posiscion en y</code> del objeto elefante.
	 * @param image es la <code>imagen</code> del objeto elefante.
	 */
	public Elefante(int posX,int posY,Image image){
		super(posX,posY,image);	
	}
}
