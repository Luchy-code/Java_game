package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Corazon {

	double x;
	double y;
	double radio;
	Image imagen;

	public Corazon(double x, double y, double radio) {
		this.x = x;
		this.y = y;
		this.radio = radio;
		this.imagen = Herramientas.cargarImagen("img/corazon.png");
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, 0);
	}

	public boolean colisionConObstaculo(Obstaculo obs) {

		if (obs != null) {
			return ((this.x - obs.x) * (this.x - obs.x)) + ((this.y - obs.y) * (this.y - obs.y)) < 1000;
		}

		return false;

	}

}
