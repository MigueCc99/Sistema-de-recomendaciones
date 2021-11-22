import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;

import javax.xml.catalog.Catalog;

public class recomendaciones {

    public static void printFile(String file) throws FileNotFoundException, IOException {
        String cadena;
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            System.out.println(cadena);
        }
        b.close();
    }

    public static void printID(String file) throws FileNotFoundException, IOException {
        String cadena;
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            System.out.println(cadena.split(",")[0]);
        }
        b.close();
    }

    public static void printName(String file) throws FileNotFoundException, IOException {
        String cadena;
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            System.out.println(cadena.split(",")[1]);
        }
        b.close();
    }

    public static HashMap<String, String> loadMovies(String file) throws FileNotFoundException, IOException {
        HashMap<String, String> movies = new HashMap<String, String>();
        String movie;
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        while ((movie = b.readLine()) != null) {
            movies.put(movie.split(",")[0], movie.split(",")[1]);
        }
        b.close();

        return movies;
    }

    public static HashMap<Integer, Integer> loadRatings(String file) throws FileNotFoundException, IOException {
        HashMap<Integer, Integer> ratings = new HashMap<Integer, Integer>();
        String rating;
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        while ((rating = b.readLine()) != null) {
            ratings.put(Integer.parseInt(rating.split(",")[0]), Integer.parseInt(rating.split(",")[1]));
        }
        b.close();

        return ratings;
    }

    public static void main(String args[]) {
        HashMap<String, String> movies = new HashMap<>();
        try {
            movies = loadMovies("movies.csv");
        } catch (FileNotFoundException e) {
            e.notifyAll();
        } catch (IOException e) {
            e.notifyAll();
        }

        System.out.print("\nBienvenid@ nuevo usuari@, a continuacion vas a valorar 20 peliculas:\n");
        String entradaTeclado = "";
        Scanner entradaEscaner = new Scanner(System.in);
        /*
         * for (String i : movies.keySet()) { System.out.println("idMovie: " + i +
         * " Name: " + movies.get(i)); }
         */

        int numero;

        for (int i = 0; i < 20; i++) {
            Random random = new Random();

            System.out.println(random.nextInt(20));
            /*
             * System.out.print("\nNombre peli. Puntuacion --> "); entradaTeclado =
             * entradaEscaner.nextLine(); System.out.println("He valorado con un " +
             * entradaTeclado);
             */

        }

        entradaEscaner.close();

    }
}