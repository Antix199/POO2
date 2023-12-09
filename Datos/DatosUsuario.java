package Datos;
import Modelo.Usuario;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatosUsuario {

    public static void registrarUsuario(String rutaUsuarios, Usuario usuario) {
        try (FileWriter archivo = new FileWriter(rutaUsuarios, true);
             PrintWriter escribir = new PrintWriter(archivo)) {

            escribir.println(usuario.getNombre() + "," + usuario.getCorreo() + "," + usuario.getContrasena());
            JOptionPane.showMessageDialog(null,"¡Cuenta creada exitosamente!");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Error al registrar el usuario. Detalles: " + e.getMessage());
        }
    }

    public static List<Usuario> cargarUsuarios(String rutaUsuarios) {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaUsuarios))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    Usuario usuario = new Usuario(partes[0], partes[1], partes[2]);
                    usuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            System.out.println("El usuario no tiene datos para cargar o hubo un error al cargar los datos");
        }

        return usuarios;
    }

}