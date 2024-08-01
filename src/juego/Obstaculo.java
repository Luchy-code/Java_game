package juego;

import entorno.Entorno;
import entorno.Herramientas;

//import java.awt.Color;
import java.awt.Image;

public class Obstaculo {

	double x;
	double y;
	double radio;
	Image imagen;
	double angulo;

	public Obstaculo(double x, double y, double radio) {
		this.x = x;
		this.y = y;
		this.radio = radio;
		this.imagen = Herramientas.cargarImagen("img/arbusto.png");
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, angulo);
		// entorno.dibujarCirculo(x, y, radio, Color.GRAY);
	}

}
