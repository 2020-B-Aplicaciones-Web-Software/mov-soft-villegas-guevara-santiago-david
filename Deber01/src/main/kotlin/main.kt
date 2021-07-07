

import javax.swing.JFrame



fun main(args: Array<String>) {

    val frame = JFrame("Lista Empresas")
    frame.contentPane = ListaEmpresas().panelEmpresas
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.pack()
    frame.isVisible = true


}
