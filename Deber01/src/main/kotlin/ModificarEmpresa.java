import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ModificarEmpresa {
    private JPanel panel1;
    private JTextField text_nombre;
    private JTextField text_numeroTrabajadores;
    private JTextField text_fechaFundacion;
    private JTextField text_pais;
    private JTextField text_independiente;
    private JButton btn_ActualizarEmpresa;
    private JButton btn_regresar;

    public JPanel getPanel1() {
        return panel1;
    }

    public ModificarEmpresa(JPanel panelEmpresas, JTable tablaDesarolladores, DefaultTableModel modelo, JFrame frame, int fila) {
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
        EmpresaDesarrolladora emp= BaseDeDatosMemoria.Companion.getArregloEmpresaDesarrolladora().get(fila);
        String []informacion=new String[5];
        informacion[0]= emp.getNombre();
        informacion[1]= Integer.toString(emp.getNumeroTrabajadores());
        informacion[2]= sdf.format(emp.getFechaFundacion());
        informacion[3]=emp.getPais();
        informacion[4]=Boolean.toString(emp.getIndependiente());
        text_nombre.setText(informacion[0]);
        text_numeroTrabajadores.setText(informacion[1]);
        text_fechaFundacion.setText(informacion[2]);
        text_pais.setText(informacion[3]);
        text_independiente.setText(informacion[4]);
        btn_regresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
                panelEmpresas.setVisible(true);
            }
        });
        btn_ActualizarEmpresa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {

                    int filas=tablaDesarolladores.getRowCount();
                    for (int i = 0;filas>i; i++) {
                        modelo.removeRow(0);
                    }
                } catch (Exception c) {
                    JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
                }
                if(Verificadores.Companion.validadorStrings(text_nombre.getText())&&
                        Verificadores.Companion.isNumeric(text_numeroTrabajadores.getText())&&
                        Verificadores.Companion.validadorStrings(text_pais.getText())&&
                        Verificadores.Companion.validadorBoleano(text_independiente.getText())&&
                        Verificadores.Companion.validadorFecha(text_fechaFundacion.getText())
                ){
                    String nombre=text_nombre.getText();
                    Integer numeroTrabajadores=Integer.parseInt(text_numeroTrabajadores.getText());
                    String pais=text_pais.getText();
                    Boolean indie=Boolean.parseBoolean(text_independiente.getText());
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

                    try {
                        Date fecha = formato.parse(text_fechaFundacion.getText());

                        BaseDeDatosMemoria.Companion.getArregloEmpresaDesarrolladora().get(fila).actualizar(nombre,numeroTrabajadores,fecha,pais,indie);
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }

                    frame.dispose();


                    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
                    for (EmpresaDesarrolladora empresa:BaseDeDatosMemoria.Companion.getArregloEmpresaDesarrolladora()){
                        String []informacion=new String[5];
                        informacion[0]= empresa.getNombre();
                        informacion[1]= Integer.toString(empresa.getNumeroTrabajadores());
                        informacion[2]= sdf.format(empresa.getFechaFundacion());
                        informacion[3]=empresa.getPais();
                        informacion[4]=Boolean.toString(empresa.getIndependiente());
                        modelo.addRow(informacion);

                    }
                    panelEmpresas.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(null,"Error en los datos");
                }
            }

        });
    }
}
