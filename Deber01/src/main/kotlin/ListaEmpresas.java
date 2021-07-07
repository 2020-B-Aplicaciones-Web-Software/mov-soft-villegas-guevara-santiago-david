import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

public class ListaEmpresas {

    private JPanel panelEmpresas;
    private JButton btn_crear;
    private JButton btn_eliminar;
    private JButton btn_editar;
    private JButton btn_verJuegos;
    private JTable tablaDesarolladores;
    DefaultTableModel modelo;


    public JPanel getPanelEmpresas() {
        return panelEmpresas;
    }

    public JTable getTablaDesarolladores() {
        return tablaDesarolladores;
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public ListaEmpresas(){
        super();
        asignarModeloTable();
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




        btn_eliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int fila= tablaDesarolladores.getSelectedRow();
                if(fila>=0) {
                    modelo.removeRow(fila);
                    BaseDeDatosMemoria.Companion.eliminarEmpresa(fila);
                }
            }
        });
        btn_crear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                panelEmpresas.setVisible(false);
                JFrame frame=new JFrame("CrearEmpresa");

                frame.setContentPane(new CrearEmpresa(getPanelEmpresas(),getTablaDesarolladores(),getModelo(),frame).getPanel1());

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);


            }
        });
        btn_editar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int fila= tablaDesarolladores.getSelectedRow();
                if(fila>=0) {
                    panelEmpresas.setVisible(false);
                    JFrame frame=new JFrame("EdtarEmpresa");

                    frame.setContentPane(new ModificarEmpresa(getPanelEmpresas(),getTablaDesarolladores(),getModelo(),frame,fila).getPanel1());

                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                }
            }
        });
        btn_verJuegos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int fila= tablaDesarolladores.getSelectedRow();
                if(fila>=0) {
                    panelEmpresas.setVisible(false);
                    JFrame frame=new JFrame("ListarVieojuegos");

                    frame.setContentPane(new ListaVideojuego(getPanelEmpresas(),frame,fila).getPanelVideojuego());

                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                }
            }
        });
    btn_crear.addMouseListener(new MouseAdapter() { } );}

    public void asignarModeloTable(){

        tablaDesarolladores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modelo=new DefaultTableModel();
        modelo.addColumn("Nombre");
        modelo.addColumn("Trabajadores");
        modelo.addColumn("Fecha de fundación");
        modelo.addColumn("País");
        modelo.addColumn("Independiente");
        tablaDesarolladores.setModel(modelo);
    }
}


