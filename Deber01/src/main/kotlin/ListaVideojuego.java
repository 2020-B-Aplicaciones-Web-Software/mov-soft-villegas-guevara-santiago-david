import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

public class ListaVideojuego {
    private JPanel panelVideojuego;
    private JButton btn_crear;
    private JButton btn_eliminar;
    private JButton btn_editar;
    private JButton btn_regresar;
    private JTable tablaVideojuegos;
    DefaultTableModel modelo;



    public ListaVideojuego(JPanel panelEmpresas, JFrame frame, int fila) {
        asignarModeloTable();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Videojuego videojuego : BaseDeDatosMemoria.Companion.getArregloEmpresaDesarrolladora().get(fila).getVideojuegos()) {

            String[] informacion = new String[5];
            informacion[0] = videojuego.getNombre();
            informacion[1] = Double.toString(videojuego.getRecaudacion());
            informacion[2] = sdf.format(videojuego.getFechaSalida());
            informacion[3] = videojuego.getGeneroPrincipal();
            informacion[4] = Boolean.toString(videojuego.getMultijugador());
            modelo.addRow(informacion);
        }
        btn_eliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int file= tablaVideojuegos.getSelectedRow();
                if(file>=0) {
                    modelo.removeRow(file);
                    BaseDeDatosMemoria.Companion.getArregloEmpresaDesarrolladora().get(fila).eliminarVideojuego(file);
                }
            }
        });
        btn_regresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
                panelEmpresas.setVisible(true);
            }
        });
        btn_crear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                panelVideojuego.setVisible(false);
                JFrame frame=new JFrame("CrearEmpresa");
                frame.setContentPane(new CrearVideojuego(getPanelVideojuego(),getTablaVideojuegos(),getModelo(),frame,fila).getPanel1());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
        btn_editar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int juego= tablaVideojuegos.getSelectedRow();
                if(juego>=0) {
                    panelVideojuego.setVisible(false);
                    JFrame frame=new JFrame("CrearEmpresa");
                    frame.setContentPane(new ActualizarVideojuego(getPanelVideojuego(),getTablaVideojuegos(),getModelo(),frame,fila,juego).getPanel1());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);

                }
            }
        });
    }

    public JPanel getPanelVideojuego() {
        return panelVideojuego;
    }

    public JTable getTablaVideojuegos() {
        return tablaVideojuegos;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }


    public void asignarModeloTable(){

        tablaVideojuegos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modelo=new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Recaudación");
        modelo.addColumn("Fecha de salida");
        modelo.addColumn("Género");
        modelo.addColumn("Multijugador");
        tablaVideojuegos.setModel(modelo);
    }
}
