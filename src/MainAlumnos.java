import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainAlumnos {

    public static void main(String[] args) {

        Map<String, List<Double>> alumnos = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("alumnos.txt"))) {

            String linea;

            while ((linea = reader.readLine()) != null) {

                String[] partes = linea.split(";");

                String nombre = partes[0];
                double nota = Double.parseDouble(partes[1]);

                if (!alumnos.containsKey(nombre)) {
                    alumnos.put(nombre, new ArrayList<>());
                }

                alumnos.get(nombre).add(nota);
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
            return;
        }

        Map<String, Double> aprobados = new HashMap<>();

        String mejorAlumno = "";
        double mejorMedia = 0;

        for (String nombre : alumnos.keySet()) {

            List<Double> notas = alumnos.get(nombre);
            double suma = 0;

            for (double n : notas) {
                suma += n;
            }

            double media = suma / notas.size();

            if (media >= 5) {
                aprobados.put(nombre, media);
            }

            if (media > mejorMedia) {
                mejorMedia = media;
                mejorAlumno = nombre;
            }
        }


        try (BufferedWriter reader = new BufferedWriter(new FileWriter("resultado.txt"))) {

            reader.write("ALUMNOS APROBADOS");
            reader.newLine();

            for (String nombre : aprobados.keySet()) { //recorre todos los alumnos aprobados
                reader.write(nombre + " -> " + aprobados.get(nombre));
                reader.newLine();
            }

            reader.newLine();
            reader.write("Mejor alumno: " + mejorAlumno + " (" + mejorMedia + ")");

        } catch (IOException e) {
            System.out.println("Error al escribir el archivo");
        }
    }
}