package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Laser {

	double x;
	double y;
	double angulo;
	double radio;
	Image imagen;

	public Laser(double x, double y, double angulo) {
		this.x = x;
		this.y = y;
		this.angulo = angulo;
		this.imagen = Herramientas.cargarImagen("img/laser.png");

	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, angulo);
		// entorno.dibujarCirculo(x, y, 20, Color.BLUE);
	}

	public void mover() {
		this.x += Math.cos(this.angulo) * 7;
		this.y += Math.sin(this.angulo) * 7;

	}

	public boolean colisionConObstaculo(Obstaculo obs) {
		if (obs != null) {
			return ((this.x - obs.x) * (this.x - obs.x)) + ((this.y - obs.y) * (this.y - obs.y)) < 1000;
		}

		return false;

	}

}
