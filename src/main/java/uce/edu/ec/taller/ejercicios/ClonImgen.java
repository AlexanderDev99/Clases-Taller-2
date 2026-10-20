package uce.edu.ec.taller.ejercicios;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ClonImgen {

	public static void main(String[] args) {
        File imagenOriginal = new File("img/Camaro.jpg");
        File imagenClon = new File("img/CamaroClon.jpg");
        final int MASCARA = 0xFF;

        try {
            BufferedImage imagen = ImageIO.read(imagenOriginal);
            if (imagen == null) {
                System.out.println("Formato de imagen no soportado");
                return;
            }

            int ancho = imagen.getWidth();
            int alto = imagen.getHeight();
            BufferedImage imagen2 = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < ancho; x++) {
                for (int y = 0; y < alto; y++) {
                    int pixel = imagen.getRGB(x, y);

                    int r = (pixel >> 16) & MASCARA;
                    int g = (pixel >> 8) & MASCARA;
                    int b = pixel & MASCARA;

                    int pixel2 = (r << 16) | (g << 8) | b;
                    imagen2.setRGB(x, y, pixel2);
                }
            }

            // Se guarda UNA sola vez, fuera de los bucles
            ImageIO.write(imagen2, "jpg", imagenClon);
            System.out.println("Imagen clonada con éxito");

        } catch (IOException e) {
            System.out.println("Error de la imagen: " + e.getMessage());
        }
    }

}
