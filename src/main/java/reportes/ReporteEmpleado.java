/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package reportes;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ReporteEmpleado {

    private static final String URL =
            "jdbc:sqlserver://Nataly:1433;"
            + "databaseName=EmpresaDB;"
            + "encrypt=true;"
            + "trustServerCertificate=true;";

    private static final String USER = "sa";

    // Configurar esta variable en Windows
    private static final String PASSWORD =
            System.getenv("EMPRESADB_PASSWORD");

    public static void mostrarReporte() {

        if (PASSWORD == null || PASSWORD.isBlank()) {
            JOptionPane.showMessageDialog(
                    null,
                    "Configura EMPRESADB_PASSWORD en Windows."
            );
            return;
        }

        try (Connection conexion =
                DriverManager.getConnection(
                        URL, USER, PASSWORD)) {

            InputStream archivo =
                    ReporteEmpleado.class.getResourceAsStream(
                            "/reportess/ReporteEmpleado.jrxml"
                    );

            if (archivo == null) {
                throw new Exception(
                        "No se encontró ReporteEmpleado.jrxml"
                );
            }

            try (InputStream reportess = archivo) {

                var jasperReport =
                        JasperCompileManager.compileReport(reportess);

                JasperPrint imprimir =
                        JasperFillManager.fillReport(
                                jasperReport,
                                new HashMap<>(),
                                conexion
                        );

                JasperViewer.viewReport(imprimir, false);
            }

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Error al generar el reporte:\n"
                            + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
