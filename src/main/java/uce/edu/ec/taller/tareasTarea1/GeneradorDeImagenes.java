package uce.edu.ec.taller.tareasTarea1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GeneradorDeImagenes {

	static final int ANCHO = 800;
	static final int ALTO = 600;
	static final int MASCARA = 0xFF; // 255, limita cada canal a 8 bits
 
	public static void main(String[] args) {
		try {
			//crea la carpeta de salida si no existe
			new File("img2").mkdirs();
 
			guardar(gradienteHorizontal(), "img2/1_gradiente_horizontal.png");
			guardar(gradienteRadial(), "img2/2_gradiente_radial.png");
			guardar(tablero(), "img2/3_tablero.png");
			guardar(anillosConcentricos(), "img2/4_anillos.png");
			guardar(ondasPlasma(), "img2/5_ondas_plasma.png");
 
			System.out.println("Las 5 imagenes se generaron con exito");
		} catch (IOException e) {
			System.out.println("Error al guardar la imagen: " + e.getMessage());
		}
	}
 
 
	//Combinamos R, G, B en un solo entero
	static int crearPixel(int r, int g, int b) {
		// limitamos cada canal al rango 0-255
		r = Math.max(0, Math.min(255, r)) & MASCARA;
		g = Math.max(0, Math.min(255, g)) & MASCARA;
		b = Math.max(0, Math.min(255, b)) & MASCARA;
		return (r << 16) | (g << 8) | b;
	}
 
	//funcion para crear la nueva imagen
	static BufferedImage nuevaImagen() {
		return new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);
	}
 
	//Funcion para guardar las imagenes
	static void guardar(BufferedImage imagen, String ruta) throws IOException {
		ImageIO.write(imagen, "png", new File(ruta));
	}
 
	//IMAGEN1: gradiente horizontal
	//El rojo crece de izquierda a derecha y el azul decrece.
	static BufferedImage gradienteHorizontal() {
		BufferedImage img = nuevaImagen();
		for (int x = 0; x < ANCHO; x++) {
			for (int y = 0; y < ALTO; y++) {
				int r = x * 255 / (ANCHO - 1);
				int g = 0;
				int b = 255 - r;
				img.setRGB(x, y, crearPixel(r, g, b));
			}
		}
		return img;
	}
 
	//IMAGEN2: gradiente radial
	//El color depende de la distancia al centro: blanco en el centro, azul oscuro en el borde.
	static BufferedImage gradienteRadial() {
		BufferedImage img = nuevaImagen();
		double cx = ANCHO / 2.0;
		double cy = ALTO / 2.0;
		double maxDist = Math.sqrt(cx * cx + cy * cy); // distancia del centro a una esquina
 
		for (int x = 0; x < ANCHO; x++) {
			for (int y = 0; y < ALTO; y++) {
				double dist = Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy));
				double t = dist / maxDist; // 0 en el centro, 1 en la esquina
				int r = (int) (255 * (1 - t));
				int g = (int) (255 * (1 - t));
				int b = (int) (255 * (1 - t * 0.5)); // el azul se mantiene mas alto
				img.setRGB(x, y, crearPixel(r, g, b));
			}
		}
		return img;
	}
 
	//IMAGEN3: tablero de ajedrez
	//Se alternan dos colores segun la paridad de (columna + fila) de cada casilla.
	static BufferedImage tablero() {
		BufferedImage img = nuevaImagen();
		int lado = 50; //tamano de cada casilla en pixeles
		for (int x = 0; x < ANCHO; x++) {
			for (int y = 0; y < ALTO; y++) {
				boolean par = ((x / lado) + (y / lado)) % 2 == 0;
				int pixel = par ? crearPixel(27, 58, 107) : crearPixel(240, 240, 240);
				img.setRGB(x, y, pixel);
			}
		}
		return img;
	}
 
	//IMAGEN4: anillos concentricos
	// Se usa el modulo de la distancia al centro para repetir franjas circulares.
	static BufferedImage anillosConcentricos() {
		BufferedImage img = nuevaImagen();
		double cx = ANCHO / 2.0;
		double cy = ALTO / 2.0;
		int grosor = 20; // ancho de cada anillo
 
		for (int x = 0; x < ANCHO; x++) {
			for (int y = 0; y < ALTO; y++) {
				double dist = Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy));
				int anillo = (int) (dist / grosor);
				int pixel;
				if (anillo % 3 == 0) {
					pixel = crearPixel(220, 50, 50); // rojo
				} else if (anillo % 3 == 1) {
					pixel = crearPixel(250, 200, 40); // amarillo
				} else {
					pixel = crearPixel(40, 120, 220); // azul
				}
				img.setRGB(x, y, pixel);
			}
		}
		return img;
	}
 
	//IMAGEN5: ondas tipo "plasma"
	// Se suman funciones seno en x, en y y en la distancia al centro,
	// y el resultado se usa para generar cada canal con un desfase distinto.
	static BufferedImage ondasPlasma() {
		BufferedImage img = nuevaImagen();
		double cx = ANCHO / 2.0;
		double cy = ALTO / 2.0;
 
		for (int x = 0; x < ANCHO; x++) {
			for (int y = 0; y < ALTO; y++) {
				double dist = Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy));
				double v = Math.sin(x / 40.0) + Math.sin(y / 30.0) + Math.sin(dist / 25.0);
				// v esta entre -3 y 3; lo normalizamos a 0-1
				double t = (v + 3) / 6.0;
 
				int r = (int) (255 * Math.sin(Math.PI * t));
				int g = (int) (255 * Math.sin(Math.PI * t + 2.0) * 0.5 + 127);
				int b = (int) (255 * (1 - t));
				img.setRGB(x, y, crearPixel(r, g, b));
			}
		}
		return img;
	}

}
