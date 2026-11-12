/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import modelo.Empleado;
import conexion.ConexionDB;
import java.sql.*;
import java.util.ArrayList;

public class EmpleadoDAO {

    public ArrayList<Empleado> listaarchivos() {
        ArrayList<Empleado> lista = new ArrayList<>();

        String sql = "SELECT IdEmpleado, Nombre, Apellido, Cargo, "
                   + "Salario, Departamento FROM dbo.Empleados";

        try {
            Connection con = ConexionDB.getConexion();

            if (con == null) {
                System.err.println("No se pudo conectar con EmpresaDB.");
                return lista;
            }

            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Empleado emp = new Empleado();

                    emp.setIdEmpleado(rs.getInt("IdEmpleado"));
                    emp.setNombre(rs.getString("Nombre"));
                    emp.setApellido(rs.getString("Apellido"));
                    emp.setCargo(rs.getString("Cargo"));
                    emp.setSalarioBase(rs.getDouble("Salario"));
                    emp.setDepartamento(rs.getString("Departamento"));

                    lista.add(emp);
                }
            }

            System.out.println("Empleados encontrados: " + lista.size());

        } catch (SQLException e) {
            System.err.println("Error al consultar empleados:");
            e.printStackTrace();
        }

        return lista;
    }

    public double obterSalario(int idEmpleado) {
        String sql = "SELECT Salario FROM dbo.Empleados WHERE IdEmpleado = ?";

        try {
            Connection con = ConexionDB.getConexion();

            if (con == null) {
                return 0;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idEmpleado);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getDouble("Salario");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public boolean insertarEmpleado(Empleado emp) {
        String sql = "INSERT INTO dbo.Empleados "
                   + "(Nombre, Apellido, Cargo, Salario, Departamento) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = ConexionDB.getConexion();

            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, emp.getNombre());
                ps.setString(2, emp.getApellido());
                ps.setString(3, emp.getCargo());
                ps.setDouble(4, emp.getSalarioBase());
                ps.setString(5, emp.getDepartamento());

                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
public boolean insertar(Empleado nuevoEmpleado) {
    return insertarEmpleado(nuevoEmpleado);
}

}