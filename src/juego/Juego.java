package juego;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Mikasa mikasa;
	private Kyojines[] kyojines;
	private Obstaculo[] obstaculos;
	private Laser laser;
	private Suero suero;
	private Corazon corazon;
	private boolean jugando;
	private boolean gano;
	private int eliminados;
	private int timer;
	private int kyojinaTimer;
	private int invTimer;
	private Image fondo;
	private Image ganaste;
	private Image perdiste;
	private int vida;

	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this,"Juan Vernon Petre, Joaquin Flores y Jorge Jimenez - Grupo 10 - Attack on Titan", 800, 600);
		fondo = Herramientas.cargarImagen("fondo.png");
		mikasa = new Mikasa(400, 300);
		kyojines = new Kyojines[4];
		obstaculos = new Obstaculo[6];
		obstaculos[0] = new Obstaculo(110, 180, 80);
		obstaculos[1] = new Obstaculo(390, 150, 80);
		obstaculos[2] = new Obstaculo(650, 180, 80);
		obstaculos[3] = new Obstaculo(110, 450, 80);
		obstaculos[4] = new Obstaculo(390, 480, 80);
		obstaculos[5] = new Obstaculo(650, 450, 80);
		eliminados = 0;
		ganaste = Herramientas.cargarImagen("ganaste.png");
		perdiste = Herramientas.cargarImagen("perdiste.png");
		vida = 2;

		spawnearKyojines();
		spawnearSuero();
		spawnearCorazon();

		jugando = true;
		gano = false;

		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
		if (jugando == true) {
			entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0);

			if (mikasa != null) {
				mikasa.dibujarse(entorno);

				for (int i = 0; i < obstaculos.length; i++) {

					if (mikasa.colisionConObstaculo(obstaculos[i])) {

						if (mikasa.x < obstaculos[i].x) {
							mikasa.x -= obstaculos[i].radio / 3;
						}

						if (mikasa.x > obstaculos[i].x) {
							mikasa.x += obstaculos[i].radio / 3;
						}

						if (mikasa.y < obstaculos[i].y) {
							mikasa.y -= obstaculos[i].radio / 3;
						}

						if (mikasa.y > obstaculos[i].y) {
							mikasa.y += obstaculos[i].radio / 3;
						}

					}

				}
				if (mikasa.colisionConCorazon(corazon)) {
					corazon = null;
					vida++;
					Herramientas.cargarSonido("collect.wav").start();
				}

				if (mikasa.colisionConSuero(suero)) {
					suero = null;
					mikasa.kyojina = true;
					mikasa.inv = false;
					Herramientas.cargarSonido("collect.wav").start();
				}

				if (mikasa.kyojina) {
					kyojinaTimer++;

					if (kyojinaTimer >= 450) {
						spawnearSuero();
						mikasa.kyojina = false;
						kyojinaTimer = 0;
					}

				}

				if (mikasa.inv) {
					invTimer++;

					if (invTimer >= 120) {
						mikasa.inv = false;
						invTimer = 0;
					}

				}

			}

			for (int i = 0; i < kyojines.length; i++) {

				if (kyojines[i] != null) {

					kyojines[i].dibujarse(entorno);
					kyojines[i].cambiarAngulo(mikasa);

					if ((mikasa != null) && (mikasa.kyojina)) {
						kyojines[i].escapar();
					}

					else {
						kyojines[i].mover();
					}

					if (kyojines[i].colisionConMikasa(mikasa)) {

						if (mikasa.kyojina) {
							spawnearSuero();
							mikasa.kyojina = false;
							kyojinaTimer = 0;
							kyojines[i] = null;
							eliminados++;
							Herramientas.cargarSonido("eliminado.wav").start();
						}

						else {

							if (!mikasa.inv) {

								if (vida > 0) {
									vida--;
									mikasa.inv = true;
								}

								else {
									mikasa = null;
									jugando = false;
									gano = false;
									System.out.println("perdiste");
									Herramientas.cargarSonido("perdiste.wav").start();
								}

							}

						}

					}

					for (int j = 0; j < obstaculos.length; j++) {

						if (kyojines[i] != null) {

							if (kyojines[i].colisionConObstaculo(obstaculos[j])) {

								if (kyojines[i].x < obstaculos[j].x) {
									kyojines[i].x -= obstaculos[j].radio / 3;
								}

								if (kyojines[i].x > obstaculos[j].x) {
									kyojines[i].x += obstaculos[j].radio / 3;
								}

								if (kyojines[i].y < obstaculos[j].y) {
									kyojines[i].y -= obstaculos[j].radio / 3;
								}

								if (kyojines[i].y > obstaculos[j].y) {
									kyojines[i].y += obstaculos[j].radio / 3;
								}

							}

						}

					}

					if (kyojines[i] != null) {

						if (kyojines[i].colisionConLaser(laser)) {
							laser = null;
							kyojines[i] = null;
							eliminados++;
							Herramientas.cargarSonido("eliminado.wav").start();
						}

					}

				}

			}

			// Chequear si hay que spawnear kyojines

			if (cantidadKyojines(kyojines) <= 3) {

				timer++;

				if (timer >= 300) {
					spawnearKyojines();
					timer = 0;
				}
			}

			// Ganar la partida

			if (cantidadKyojines(kyojines) == 0) {
				jugando = false;
				gano = true;
				System.out.println("ganaste");
				Herramientas.cargarSonido("ganaste.wav").start();
			}

			for (int i = 0; i < obstaculos.length; i++) {
				if (obstaculos[i] != null) {
					obstaculos[i].dibujarse(entorno);
				}
			}

			if (mikasa != null) {

				if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
					mikasa.girar(Herramientas.radianes(1));
					mikasa.voltear = false;
				}

				if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
					mikasa.girar(Herramientas.radianes(-1));
					mikasa.voltear = true;
				}

				if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
					mikasa.moverAdelante();
				}

				if ((entorno.estaPresionada(entorno.TECLA_ESPACIO)) && (!mikasa.disparo)) {
					laser = new Laser(mikasa.x, mikasa.y, mikasa.angulo);
					mikasa.disparo = true;
					Herramientas.cargarSonido("disparo.wav").start();
				}

			}

			if (laser != null) {
				laser.dibujarse(entorno);
				laser.mover();

				if ((laser.x < -20) || (laser.x > 820) || (laser.y < -20) || (laser.y > 620)) {
					laser = null;
				}

				else {

					for (int i = 0; i < obstaculos.length; i++) {

						if (laser.colisionConObstaculo(obstaculos[i])) {
							laser = null;
							break;
						}

					}

				}

			}

			else {

				if (mikasa != null) {
					mikasa.disparo = false;
				}

			}

			if (suero != null) {
				suero.dibujarse(entorno);
			}

			if (corazon != null) {
				corazon.dibujarse(entorno);
			}

		}
		entorno.cambiarFont("Arial", 20, Color.RED);
		entorno.escribirTexto("Eliminados: " + eliminados, 0, 20);
		entorno.cambiarFont("Arial", 20, Color.RED);
		entorno.escribirTexto("Vidas : " + vida, 720, 20);

		if (gano == true) {
			entorno.dibujarImagen(ganaste, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);
		}

		else {
			if (jugando == false) {
				entorno.dibujarImagen(perdiste, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);

			}
		}

	}

	// metodos adicionales

	public void spawnearKyojines() {
		for (int i = 0; i < kyojines.length; i++) {
			Random random = new Random();
			int pos = random.nextInt(4);
			int x = 0;
			int y = 0;

			// izq
			if (pos == 0) {
				x = 0;
				y = random.nextInt(300);
			}

			// arriba
			else if (pos == 1) {
				x = random.nextInt(400);
				y = 0;
			}

			// derecha
			else if (pos == 2) {
				x = 800;
				y = random.nextInt(300) + 300;

			}

			// abajo
			else if (pos == 3) {
				x = random.nextInt(400) + 400;
				y = 600;
			}

			if (kyojines[i] == null) {
				kyojines[i] = new Kyojines(x, y);
			}

		}

	}

	public int cantidadKyojines(Kyojines[] kyo) {
		int cant = 0;

		for (int i = 0; i < kyo.length; i++) {

			if (kyo[i] != null) {
				cant++;
			}

		}

		return cant;

	}

	public void spawnearSuero() {
		Random random = new Random();
		int pos = random.nextInt(4);
		int x = 0;
		int y = 0;

		// izq
		if (pos == 0) {
			x = random.nextInt(400);
			y = random.nextInt(600);
		}

		// arriba
		else if (pos == 1) {
			x = random.nextInt(800);
			y = random.nextInt(300);
		}

		// derecha
		else if (pos == 2) {
			x = random.nextInt(400) + 400;
			y = random.nextInt(600);

		}

		// abajo
		else if (pos == 3) {
			x = random.nextInt(800);
			y = random.nextInt(300) + 300;
		}

		suero = new Suero(x, y, 50);

		for (int i = 0; i < obstaculos.length; i++) {
			if (suero.colisionConObstaculo(obstaculos[i])) {
				suero.y += 50;
			}
		}

	}

	public void spawnearCorazon() {
		Random random = new Random();
		int pos = random.nextInt(4);
		int x = 0;
		int y = 0;

		// izq
		if (pos == 0) {
			x = random.nextInt(400);
			y = random.nextInt(600);
		}

		// arriba
		else if (pos == 1) {
			x = random.nextInt(800);
			y = random.nextInt(300);
		}

		// derecha
		else if (pos == 2) {
			x = random.nextInt(400) + 400;
			y = random.nextInt(600);

		}

		// abajo
		else if (pos == 3) {
			x = random.nextInt(800);
			y = random.nextInt(300) + 300;
		}

		corazon = new Corazon(x, y, 50);

		for (int i = 0; i < obstaculos.length; i++) {
			if (corazon.colisionConObstaculo(obstaculos[i])) {
				corazon.y += 50;
			}

		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
}
