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

public class Raton extends Animal {

	/**
	 * Metodo constructor que hereda los atributos de la clase <code>Animal</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto raton.
	 * @param posY es el <code>posiscion en y</code> del objeto raton.
	 * @param image es la <code>imagen</code> del objeto raton.
	 */
	public Raton(int posX,int posY,Image image){
		super(posX,posY,image);
	}
}

