package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import entorno.Entorno;
import entorno.Herramientas;

public class Kyojines {

	double x;
	double y;
	double angulo;
	double radio;
	Image imagen;

	public Kyojines(int x, int y) {
		this.x = x;
		this.y = y;
		this.imagen = Herramientas.cargarImagen("img/kyojinAV.png");

	}

	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, angulo);
		//entorno.dibujarCirculo(x, y, 60, Color.YELLOW);

	}

	public void mover() {
		this.x += Math.cos(this.angulo) * 0.5;
		this.y += Math.sin(this.angulo) * 0.5;
		this.imagen = Herramientas.cargarImagen("img/kyojinAV.png");

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

	public void cambiarAngulo(Mikasa mikasa) {
		if (mikasa != null) {
			this.angulo = Math.atan2(mikasa.y - this.y, mikasa.x - this.x);
		}

	}

	public void escapar() {
		this.imagen = Herramientas.cargarImagen("img/kyojinESC.png");
		this.x -= Math.cos(this.angulo) * 0.5;
		this.y -= Math.sin(this.angulo) * 0.5;

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

	public boolean colisionConMikasa(Mikasa mikasa) {
		if (mikasa != null) {
			return ((this.x - mikasa.x) * (this.x - mikasa.x)) + ((this.y - mikasa.y) * (this.y - mikasa.y)) < 500;
		}

		return false;

	}

	public boolean colisionConObstaculo(Obstaculo obs) {
		if (obs != null) {
			return ((this.x - obs.x) * (this.x - obs.x)) + ((this.y - obs.y) * (this.y - obs.y)) < 3000;
		}

		return false;

	}

	public boolean colisionConLaser(Laser laser) {
		if (laser != null) {
			return ((this.x - laser.x) * (this.x - laser.x)) + ((this.y - laser.y) * (this.y - laser.y)) < 500;
		}

		return false;

	}

}