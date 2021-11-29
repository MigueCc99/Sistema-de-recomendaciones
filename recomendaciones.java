import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;

import javax.xml.catalog.Catalog;

public class Recomendaciones {
    private HashMap<Integer,String> movies;
    private ArrayList<Rating> ratings;
    private ArrayList<String> recomendadas = new ArrayList<>();
    private HashMap<Integer,Double> recomendacionUsuario = new HashMap();

    public static class Rating{
        int userId;
        int movieId;
        double rating;    

        Rating(int userId, int movieId, double rating){
            this.userId = userId;
            this.movieId = movieId;
            this.rating = rating;
        }
    }

    public Recomendaciones() throws FileNotFoundException, IOException{
        try {
            movies = loadMovies("movies.csv");
            ratings = loadRatings("ratings.csv");
            recomendarMovies();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static HashMap<Integer, String> loadMovies(String file) throws FileNotFoundException, IOException {
        HashMap<Integer, String> movies = new HashMap<Integer, String>();
        String movie;
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        while ((movie = b.readLine()) != null) {
            if(movie.split(",")[1].charAt(0) == '"'){
                movies.put(Integer.parseInt(movie.split(",")[0]), movie.split(",")[1]+movie.split(",")[2]);
            }else{
                movies.put(Integer.parseInt(movie.split(",")[0]), movie.split(",")[1]);
            }
        }
        b.close();

        return movies;
    }

    public static ArrayList<Rating> loadRatings(String file) throws FileNotFoundException, IOException {
        ArrayList<Rating> ratings = new ArrayList<>();
        String rating;
        FileReader f = new FileReader(file);
        BufferedReader b = new BufferedReader(f);
        while ((rating = b.readLine()) != null) {
            ratings.add(new Rating(Integer.parseInt(rating.split(",")[0]), Integer.parseInt(rating.split(",")[1]), Double.parseDouble(rating.split(",")[2])));
        }
        b.close();

        return ratings;
    }

    private void recomendarMovies(){
        ArrayList<Integer> indicesMovies = new ArrayList<>();
        Random random = new Random();
        int prueba = 1;
        for(int i=0; i<20; i++){
            /*
            do{
                prueba = random.nextInt(movies.size()-1);
            }while(indicesMovies.contains(prueba));
            */
            
            recomendadas.add(movies.get(i+1));
        }
    }

    public ArrayList<String> getRecomendadas(){
        return recomendadas;
    }

    public void recomendarUsuario(){
        System.out.print("\nBienvenid@ nuevo usuari@, a continuacion vas a valorar 20 peliculas:\n");
        String entradaTeclado = "";
        Scanner entradaEscaner = new Scanner(System.in);   

        for (int i = 0; i < 20; i++) {
            System.out.println(recomendadas.get(i));
            recomendacionUsuario.put(i+1, Double.parseDouble(entradaEscaner.nextLine()));
        }
    }

    public void getRecomendacionUsuario(){
        for (Integer i : recomendacionUsuario.keySet()) { 
            System.out.println("idMovie: " + i + " Valoración: " + recomendacionUsuario.get(i)); 
        }
    }

    public static void main(String args[]) {
        HashMap<Integer, String> movies = new HashMap<>();
        Recomendaciones recomendaciones;
        ArrayList<String> recomendadas = new ArrayList<>();

        try {
            recomendaciones = new Recomendaciones();
            recomendadas = recomendaciones.getRecomendadas();
            recomendaciones.recomendarUsuario();
            recomendaciones.getRecomendacionUsuario();
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}