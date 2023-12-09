package Modelo;

import Datos.DatosUsuario;

import javax.swing.*;
import java.io.File;
import java.util.List;
import java.util.Optional;


public class CuentaUsuario {

    private static String carpetaUsuarios = System.getProperty("user.dir") + File.separator + "Usuarios";
    private static String rutaUsuarios = carpetaUsuarios + File.separator + "usuarios.csv";

    public static Usuario iniciarSesion(String correo, String contrasena) {
        Optional<Usuario> usuarioOptional = buscarUsuarioPorCorreo(correo);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (verificarContrasena(usuario, contrasena)) {
                return usuario;
            }
        }

        return null;
    }

    private static boolean verificarContrasena(Usuario usuario, String contrasena) {
        return usuario.getContrasena().equals(contrasena);
    }

    public static boolean correoExiste(String correo) {
        List<Usuario> usuarios = DatosUsuario.cargarUsuarios(rutaUsuarios);
        return usuarios.stream().anyMatch(usuario -> usuario.getCorreo().equals(correo));
    }

    public static boolean crearCuenta(String nombre, String correo, String contrasena) {
        if (ValidarEntradaUsuario.validarNombre(nombre) && ValidarEntradaUsuario.validarCorreo(correo) && ValidarEntradaUsuario.validarContrasena(contrasena)) {
            DatosUsuario.registrarUsuario(rutaUsuarios,new Usuario(nombre, correo, contrasena));
            return true;
        } else {
            String mensajeError = mensajeErrorCrearCuenta(nombre, correo, contrasena);
            JOptionPane.showMessageDialog(null, mensajeError);
            return false;
        }
    }

    private static String mensajeErrorCrearCuenta(String nombre, String correo, String contrasena) {
        String mensajeError = "Error: Los datos ingresados no son válidos. \n Verifica la información proporcionada:\n";

        if (!ValidarEntradaUsuario.validarNombre(nombre)) {
            mensajeError += "- El nombre no es válido.\n";
        }

        if (!ValidarEntradaUsuario.validarCorreo(correo)) {
            mensajeError += "- El correo electrónico no es válido o ya está en uso.\n";
        }

        if (!ValidarEntradaUsuario.validarContrasena(contrasena)) {
            mensajeError += "- La contraseña no es válida, mínimo 5 carácteres.\n";
        }

        return mensajeError;
    }

    public static Optional<Usuario> buscarUsuarioPorCorreo(String correo) {
        return DatosUsuario.cargarUsuarios(rutaUsuarios).stream()
                .filter(usuario -> usuario.getCorreo().equals(correo))
                .findFirst();
    }

    private static void crearCarpetaUsuarios() {
        File carpeta = new File(carpetaUsuarios);
        if (!carpeta.exists()) {
            boolean creado = carpeta.mkdir();
            if (creado) {
                System.out.println("Carpeta Usuarios creada correctamente.");
            } else {
                System.err.println("Error al crear la carpeta Usuarios.");
            }
        }
    }

    static {
        crearCarpetaUsuarios();
    }

}
