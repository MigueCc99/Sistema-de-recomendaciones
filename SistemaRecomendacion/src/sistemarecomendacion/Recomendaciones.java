package sistemarecomendacion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;

import javax.xml.catalog.Catalog;

public class Recomendaciones {
    private HashMap<Integer, String> movies;
    private ArrayList<Rating> ratings;
    private ArrayList<String> recomendadas = new ArrayList<>();
    private HashMap<Integer, Double> recomendacionUsuario = new HashMap();
    private double mediaUsuario = 1;

    public static class Rating {
        
        int userId;
        int movieId;
        double rating;

        Rating(int userId, int movieId, double rating) {
            this.userId = userId;
            this.movieId = movieId;
            this.rating = rating;
        }
        
    }

    public Recomendaciones() throws FileNotFoundException, IOException {
        try {
            movies = loadMovies("C://Users/angel/OneDrive/Documentos//NetBeansProjects//SistemaRecomendacion/src/sistemarecomendacion/movies.csv");
            ratings = loadRatings("C://Users/angel/OneDrive/Documentos//NetBeansProjects//SistemaRecomendacion/src/sistemarecomendacion/ratings.csv");
            recomendarMovies();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void calcularMedia(){
        
        mediaUsuario = 0;
        
        for (int i = 1; i < recomendacionUsuario.size(); i++){
            mediaUsuario += recomendacionUsuario.get(i);
        }
        
        mediaUsuario = mediaUsuario / (recomendacionUsuario.size()-1);
        
    }
    
    public double getMedia(){
        return mediaUsuario;
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
            if (movie.split(",")[1].charAt(0) == '"') {
                movies.put(Integer.parseInt(movie.split(",")[0]), movie.split(",")[1] + movie.split(",")[2]);
            } else {
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
            ratings.add(new Rating(Integer.parseInt(rating.split(",")[0]), Integer.parseInt(rating.split(",")[1]),
                    Double.parseDouble(rating.split(",")[2])));
        }
        b.close();

        return ratings;
    }

    private void recomendarMovies() {
        ArrayList<Integer> indicesMovies = new ArrayList<>();
        Random random = new Random();
        int prueba = 1;
        for (int i = 0; i < 20; i++) {
            /*
             * do{
             * prueba = random.nextInt(movies.size()-1);
             * }while(indicesMovies.contains(prueba));
             */

            recomendadas.add(movies.get(i + 1));
        }
    }

    public ArrayList<String> getRecomendadas() {
        return recomendadas;
    }

    public void recomendarUsuario() {
        System.out.print("\nBienvenid@ nuevo usuari@, a continuacion vas a valorar 20 peliculas:\n");
        String entradaTeclado = "";
        Scanner entradaEscaner = new Scanner(System.in);
        
        Random random = new Random();

        for (int i = 0; i < 20; i++) {
            //System.out.println(recomendadas.get(i));
            //recomendacionUsuario.put(i + 1, Double.parseDouble(entradaEscaner.nextLine()));
            recomendacionUsuario.put(i + 1, (double)(random.nextInt(5)));

        }
    }

    public void getRecomendacionUsuario() {
        for (Integer i : recomendacionUsuario.keySet()) {
            System.out.println("idMovie: " + i + " Valoración: " + recomendacionUsuario.get(i));
        }
    }
    
    public void mostrarRatings(){
        
        for (int i = 0; i < ratings.size(); i++){
            System.out.println("\nValoracion usuario " + ratings.get(i).userId + " de la pelicula " + ratings.get(i).movieId + " = " + ratings.get(i).rating);
        }
        
    }
    
    public HashMap<Integer, Double> calcularMediasUsuarios(){
        
        HashMap<Integer, Double> medias = new HashMap<>();
        
        int contador = 0;
        
        double suma;
        int div;
        
        for (int i = 1; i <= 610; i++){
            
            div = 0;
            suma = 0;
            
            while (i == ratings.get(contador).userId && contador < ratings.size()-1){
               
               suma += ratings.get(contador).rating;
               contador ++;
               div ++;
               
            }
           
           medias.put(i, suma/div);
            
        }
        
        
        
        return medias;
        
    }
    
    public double calcularPearson(ArrayList<Double> nuevo, ArrayList<Double> viejo, int id_viejo, HashMap<Integer, Double> medias){
        
        double dividendo = 0;
        double divisor_1 = 0;
        double divisor_2 = 0;
        double divisor;
                
        for (int i = 0; i < nuevo.size(); i++){
            
            //Calculo el dividendo
            
            dividendo =  (nuevo.get(i)-mediaUsuario)*(viejo.get(i)-medias.get(id_viejo));
            divisor_1 += (nuevo.get(i)-mediaUsuario)*(nuevo.get(i)-mediaUsuario);
            divisor_2 += (viejo.get(i)-medias.get(id_viejo))*(viejo.get(i)-medias.get(id_viejo));
                        
        }
        
        divisor = sqrt(divisor_1)*sqrt(divisor_2);       
        
        
        //System.out.println("\nDividendo = " + dividendo + " y el Divisor = " + divisor);
        
        return dividendo/divisor;
        
    }
    
    public HashMap<Integer, Double> calcularSimilitudes(HashMap<Integer, Double> medias ){
        
        int contador = 0;
        HashMap<Integer, Double> similitudes = new HashMap<>();
        
        ArrayList<Double> usuario_nuevo = new ArrayList<>();
        ArrayList<Double> usuario_viejo = new ArrayList<>();
        
        boolean coincide; //Para ver si tienen alguna película valorada en común
        
        for (int i = 1; i <= medias.size(); i++){
            
            coincide = false;
            
            while (i == ratings.get(contador).userId && contador < ratings.size()-1){
                
                //System.out.println("\nMovieID: " + ratings.get(contador).movieId);
                
                if(recomendacionUsuario.containsKey(ratings.get(contador).movieId)){
                    usuario_nuevo.add(recomendacionUsuario.get(ratings.get(contador).movieId));
                    usuario_viejo.add(ratings.get(contador).rating);
                    coincide = true;
                }
                
                contador ++;
                
            }
            
            if (coincide){
                similitudes.put(i, this.calcularPearson(usuario_nuevo, usuario_viejo, i, medias));
            }
            
            usuario_nuevo.clear();
            usuario_viejo.clear();
            
        }
        
        return similitudes;       
        
    }
    
    public void ordenarVecinos(HashMap<Integer, Double> similitudes,ArrayList<Integer> vecinos ){
        
        int temporal;
            
            for (int i = 0; i < vecinos.size(); i++) {
                for (int j = 1; j < vecinos.size()-1; j++) {
                    if (Math.abs(similitudes.get(vecinos.get(j - 1))) > Math.abs(similitudes.get(vecinos.get(j)))) {
                        temporal = vecinos.get(j - 1);
                        vecinos.set(j-1, vecinos.get(j));
                        vecinos.set(j, temporal);
                    }
                }
            }
        
    }
    
    public ArrayList<Integer> elegirVecinos(int k, HashMap<Integer, Double> similitudes){
        
        ArrayList<Integer> vecinos = new ArrayList<>();
        
        double min = Math.abs(similitudes.get(1));
        
        int cnt = 0;
        
        
        for (Integer key: similitudes.keySet()){  
            
            if (cnt < 10){
                vecinos.add(key);
            }
            else{
                if (Math.abs(similitudes.get(key)) > Math.abs(similitudes.get(vecinos.get(0))))
                    vecinos.set(0, key);
            }
            
            ordenarVecinos(similitudes, vecinos);
            cnt ++;
            
        }

        return vecinos;
        
    }
    

    public static void main(String args[]) {
        HashMap<Integer, String> movies = new HashMap<>();
        Recomendaciones recomendaciones;
        ArrayList<String> recomendadas = new ArrayList<>();
        HashMap<Integer, Double> mediasUsuarios = new HashMap<>();
        HashMap<Integer, Double> similitudes = new HashMap<>();
        ArrayList<Integer> vecinos = new ArrayList<>();

        try {
            recomendaciones = new Recomendaciones();
            recomendadas = recomendaciones.getRecomendadas();
            recomendaciones.recomendarUsuario();      
            
            recomendaciones.calcularMedia();
            
            System.out.println("\n\nMedia usuario: " + recomendaciones.getMedia() + "\n");
            
            //recomendaciones.mostrarRatings();
            
            mediasUsuarios = recomendaciones.calcularMediasUsuarios();

            /*for (int i = 1; i <= mediasUsuarios.size(); i++){
                System.out.println("\nMedia usuario " + i + " = " + mediasUsuarios.get(i));
            }*/
            
            similitudes = recomendaciones.calcularSimilitudes(mediasUsuarios);
            
            /* System.out.println("\nSimilutdes size " + similitudes.size() + "\n");
            
            for (Integer key: similitudes.keySet()){  
			System.out.println(key+ " = " + similitudes.get(key));
            }

            */
            
            vecinos = recomendaciones.elegirVecinos(10, similitudes);
            
            System.out.println("Size vecinos: " + vecinos.size());
            
            for (int i = 0; i < vecinos.size(); i++){
                System.out.println(vecinos.get(i) + "  y similitud = " + similitudes.get(vecinos.get(i)));
            }
           
            
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
}