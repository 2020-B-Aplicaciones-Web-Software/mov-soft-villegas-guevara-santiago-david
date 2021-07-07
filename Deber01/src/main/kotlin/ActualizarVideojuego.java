import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActualizarVideojuego {
    private JPanel panel1;
    private JTextField text_nombre;
    private JTextField text_recaudacion;
    private JTextField text_fechaSalida;
    private JTextField text_genero;
    private JTextField text_multijugador;
    private JButton btn_actualizarJuego;
    private JButton btn_regresar;


    public ActualizarVideojuego(JPanel panelVideojuego, JTable tablaVideojuegos, DefaultTableModel modelo, JFrame frame, int fila, int juego) {
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
        Videojuego game= BaseDeDatosMemoria.Companion.getArregloEmpresaDesarrolladora().get(fila).getVideojuegos().get(juego);
        String []informacion=new String[5];
        informacion[0]= game.getNombre();
        informacion[1]= Double.toString(game.getRecaudacion());
        informacion[2]= sdf.format(game.getFechaSalida());
        informacion[3]=game.getGeneroPrincipal();
        informacion[4]=Boolean.toString(game.getMultijugador());
        text_nombre.setText(informacion[0]);
        text_recaudacion.setText(informacion[1]);
        text_fechaSalida.setText(informacion[2]);
        text_genero.setText(informacion[3]);
        text_multijugador.setText(informacion[4]);

        btn_regresar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.dispose();
                panelVideojuego.setVisible(true);
            }
        });
        btn_actualizarJuego.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {

                    int filas=tablaVideojuegos.getRowCount();
                    for (int i = 0;filas>i; i++) {
                        modelo.removeRow(0);
                    }
                } catch (Exception c) {
                    JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
                }
                if(Verificadores.Companion.validadorStrings(text_nombre.getText())&&
                        Verificadores.Companion.validadorSaldo(text_recaudacion.getText())&&
                        Verificadores.Companion.validadorStrings(text_genero.getText())&&
                        Verificadores.Companion.validadorBoleano(text_multijugador.getText())&&
                        Verificadores.Companion.validadorFecha(text_fechaSalida.getText())
                ){
                    String nombre=text_nombre.getText();
                    Double recaudacion=Double.parseDouble(text_recaudacion.getText());
                    String genero=text_genero.getText();
                    Boolean multijugador=Boolean.parseBoolean(text_multijugador.getText());
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

                    try {
                        Date fecha = formato.parse(text_fechaSalida.getText());
                        Videojuego vid= new Videojuego(nombre,recaudacion,fecha,genero,multijugador);
                        BaseDeDatosMemoria.Companion.getArregloEmpresaDesarrolladora().get(fila)
                                .getVideojuegos().get(juego).actualizar(nombre,recaudacion,fecha,genero,multijugador);
                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }

                    frame.dispose();


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
                    panelVideojuego.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(null,"Error en los datos");
                }
            }

        });
    }

    public JPanel getPanel1() {
        return panel1;
    }
}
