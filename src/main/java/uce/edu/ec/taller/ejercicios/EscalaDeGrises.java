package uce.edu.ec.taller.ejercicios;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class EscalaDeGrises {

	public static void main(String[] args) {
        File imagenOriginal = new File("img/Camaro.jpg");
        File imagenGris = new File("img/CamaroGris.jpg");
        final int MASCARA = 0xFF;

        try {
            BufferedImage imagen = ImageIO.read(imagenOriginal);
            int ancho = imagen.getWidth();
            int alto = imagen.getHeight();
            BufferedImage imagen2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < ancho; x++) {
                for (int y = 0; y < alto; y++) {
                    int pixel = imagen.getRGB(x, y);

                    int r = (pixel >> 16) & MASCARA;
                    int g = (pixel >> 8) & MASCARA;
                    int b = pixel & MASCARA;
                    
                    int gris = (r + g + b)/3;
               

                    int pixel2 = (gris << 16) | (gris << 8) | gris;
                    imagen2.setRGB(x, y, pixel2);
                }
            }

            // Se guarda UNA sola vez, fuera de los bucles
            ImageIO.write(imagen2, "jpg", imagenGris);
            System.out.println("Imagen clonada con éxito");

        } catch (IOException e) {
            System.out.println("Error de la imagen: " + e.getMessage());
        }
    }
}
