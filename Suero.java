package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Suero {

	double x;
	double y;
	double radio;
	Image imagen;

	public Suero(double x, double y, double radio) {
		this.x = x;
		this.y = y;
		this.radio = radio;
		this.imagen = Herramientas.cargarImagen("suero.png");
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, 0);
		// entorno.dibujarCirculo(x, y, radio, Color.GREEN);
	}

	public boolean colisionConObstaculo(Obstaculo obs) {

		if (obs != null) {
			return ((this.x - obs.x) * (this.x - obs.x)) + ((this.y - obs.y) * (this.y - obs.y)) < 1000;
		}

		return false;

	}

}
