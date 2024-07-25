package juego;

import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Color;
import java.awt.Image;

public class Mikasa {

	double x;
	double y;
	double angulo;
	boolean voltear;
	boolean disparo;
	boolean kyojina;
	boolean inv;
	Image imagen;
	Image flecha;

	public Mikasa(double x, double y) {
		this.x = x;
		this.y = y;
		this.flecha = Herramientas.cargarImagen("flecha.png");
	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(flecha, x, y, angulo);

		if (this.kyojina) {
			this.imagen = Herramientas.cargarImagen("kyojina.png");
			entorno.dibujarImagen(imagen, x, y, angulo);
		}

		else {

			if (this.voltear) {
				this.imagen = Herramientas.cargarImagen("mikasaIZQ.png");
			}

			else {
				this.imagen = Herramientas.cargarImagen("mikasaDER.png");
			}

			entorno.dibujarImagen(imagen, x, y, 0.0);
		}

	}

	public void girar(double modificador) {
		this.angulo = this.angulo + modificador;
		if (this.angulo < 0) {
			this.angulo += 2 * Math.PI;

		}
		if (this.angulo >= 2 * Math.PI) {
			this.angulo -= 2 * Math.PI;

		}

	}

	public void moverAdelante() {
		this.x += Math.cos(this.angulo) * 5;
		this.y += Math.sin(this.angulo) * 5;

		if (this.x > 780) {
			this.x = 780;
		}

		if (this.x < 20) {
			this.x = 20;
		}

		if (this.y > 580) {
			this.y = 580;
		}

		if (this.y < 20) {
			this.y = 20;
		}

	}

	public boolean colisionConObstaculo(Obstaculo obs) {
		if (obs != null) {
			return ((this.x - obs.x) * (this.x - obs.x)) + ((this.y - obs.y) * (this.y - obs.y)) < 3000;
		}

		return false;

	}

	public boolean colisionConSuero(Suero suero) {
		if (suero != null) {
			return ((this.x - suero.x) * (this.x - suero.x)) + ((this.y - suero.y) * (this.y - suero.y)) < 500;
		}

		return false;

	}
	
	public boolean colisionConCorazon(Corazon corazon) {
		if (corazon != null) {
			return ((this.x - corazon.x) * (this.x - corazon.x)) + ((this.y - corazon.y) * (this.y - corazon.y)) < 500;
		}

		return false;

	}

}
