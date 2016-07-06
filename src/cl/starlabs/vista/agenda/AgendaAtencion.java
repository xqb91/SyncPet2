/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.agenda;

import cl.starlabs.controladores.AgendaDetalleJpaController;
import cl.starlabs.controladores.AgendaJpaController;
import cl.starlabs.controladores.DetalleUsuariosJpaController;
import cl.starlabs.controladores.MascotaJpaController;
import cl.starlabs.controladores.PropietarioJpaController;
import cl.starlabs.controladores.SucursalJpaController;
import cl.starlabs.controladores.VeterinarioJpaController;
import cl.starlabs.herramientas.HerramientasRapidas;
import cl.starlabs.herramientas.HerramientasRut;
import cl.starlabs.modelo.Agenda;
import cl.starlabs.modelo.AgendaDetalle;
import cl.starlabs.modelo.DetalleUsuarios;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.Propietario;
import cl.starlabs.modelo.Sucursal;
import cl.starlabs.modelo.Veterinario;
import cl.starlabs.vista.paciente.RegistroPaciente;
import cl.starlabs.vista.propietario.RegistroPropietario;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Victor Manuel Araya
 */
public class AgendaAtencion extends javax.swing.JFrame {

    // ***********instancia de variables necesarias para el sistema ***************
    //--- tools
    HerramientasRapidas              hr = new HerramientasRapidas();
    HerramientasRut                  ha = new HerramientasRut();
    
    //--- valores globales
    Calendar                        cal = null;
    Sucursal                        suc = null;
    Propietario                     pro = null;
    
    //--- elemento clave
    Agenda                          age = null;
    
    //---- valores de base de datos
    EntityManagerFactory            emf = Persistence.createEntityManagerFactory("SyncPetPU");
    AgendaDetalleJpaController      jpa = new AgendaDetalleJpaController(emf);
    AgendaJpaController             jpb = new AgendaJpaController(emf);
    AgendaDetalleJpaController      jpc = new AgendaDetalleJpaController(emf);
    SucursalJpaController           jpd = new SucursalJpaController(emf);
    PropietarioJpaController        jpe = new PropietarioJpaController(emf);
    DetalleUsuariosJpaController    jpf = new DetalleUsuariosJpaController(emf);
    
    public AgendaAtencion() {
        initComponents();
        //centrando ventana
        this.setLocationRelativeTo(null);
        //colocando icono a ventana
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/cl/starlabs/imagenes/iconos/calendar_add.png"));
        setIconImage(icon);
        
        //seteando los valores globales de hora y seteando valores en calendario y agendamiento de hora
        inicializarAgendamiento();
        
        //setenado valores de prueba de aplicación
        this.suc = jpd.findSucursal(1);
        
        //rellenando lista de veterinarios de la sucursal
        rellenarVeterinarios(suc);
        if(cmbVeterinario.getItemCount() == 0) {
            deshabilitarCamposSeleccion();
        }else{
            deshabilitarCamposSeleccion();
            hr.activar(lblRunPropietario);
            hr.activar(txtRun);
            hr.activar(btnFind);
            hr.focus(txtRun);
        }
    }
    
    public AgendaAtencion(Sucursal suc) {
        initComponents();
        //centrando ventana
        this.setLocationRelativeTo(null);
        //colocando icono a ventana
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/cl/starlabs/imagenes/iconos/calendar_add.png"));
        setIconImage(icon);
        
        //seteando los valores globales de hora y seteando valores en calendario y agendamiento de hora
        inicializarAgendamiento();
        
        //setenado valores de prueba de aplicación
        this.suc = suc;
        
        //rellenando lista de veterinarios de la sucursal
        rellenarVeterinarios(suc);
        if(cmbVeterinario.getItemCount() == 0) {
            deshabilitarCamposSeleccion();
        }else{
            deshabilitarCamposSeleccion();
            hr.activar(lblRunPropietario);
            hr.activar(txtRun);
            hr.activar(btnFind);
            hr.focus(txtRun);
        }
    }
    

    // --- libreria de métodos utiles de esta aplicacion
    
    //deshabilita los campos cuando la ventana se prepara para agendar una hora
    public void deshabilitarCamposSeleccion() {
        hr.desactivar(lblRunPropietario);
        hr.desactivar(lblPropietario);
        hr.desactivar(txtRun);
        hr.desactivar(btnFind);
        hr.desactivar(lblPropietarioData);
        hr.desactivar(lblDireccion);
        hr.desactivar(lblDireccionData);
        hr.desactivar(lblCiudad);
        hr.desactivar(lblCiudadData);
        hr.desactivar(lblPaciente);
        hr.desactivar(cmbPaciente);
        hr.desactivar(lblFechaAgendar);
        hr.desactivar(lblHoraAgendar);
        hr.desactivar(btnMas);
        hr.desactivar(btnMenos);
        //calendario.setEnabled(false);
        hora.setEnabled(false);
    }
    
    //habilita los campos cuando la ventana se prepara para agendar una hora
    public void habilitarCamposSeleccion() {
        hr.activar(lblRunPropietario);
        hr.activar(lblPropietario);
        hr.activar(txtRun);
        hr.activar(btnFind);
        hr.activar(lblPropietarioData);
        hr.activar(lblDireccion);
        hr.activar(lblDireccionData);
        hr.activar(lblCiudad);
        hr.activar(lblCiudadData);
        hr.activar(lblPaciente);
        hr.activar(cmbPaciente);
        hr.activar(lblFechaAgendar);
        hr.activar(lblHoraAgendar);
        hr.activar(btnMas);
        hr.activar(btnMenos);
        //calendario.setEnabled(true);
        hora.setEnabled(true);
    }
    
    //setea los valores por defecto para el calendario y el sistema en general
    public void inicializarAgendamiento() {
        //seteando la variable de fecha global
        this.cal = new GregorianCalendar();
        //this.cal.add(Calendar.HOUR_OF_DAY, -1);
        cal.set(Calendar.MILLISECOND, 000);
        //seteando la fecha minima de seleccion al calendario
        this.calendario.setMinSelectableDate(new GregorianCalendar().getTime());
        //seteando la fecha máxima de seleccion al calendario (máximo 5 meses)
        Calendar aux = cal;
        aux.add(Calendar.MONTH, 5);
        aux.set(Calendar.SECOND, 0);
        this.calendario.setMaxSelectableDate(aux.getTime());
        //devolviendo la fecha auxiliar a la actual
        aux.add(Calendar.MONTH, -5);
        aux.set(Calendar.MILLISECOND, 000);
        //seteando valor de la hora a agendar (debe encontrarse en intervalos de 15 minutos)
        if(aux.get(Calendar.MINUTE) > 0 && aux.get(Calendar.MINUTE) < 15) {
            int valor = 15 - aux.get(Calendar.MINUTE);
            aux.add(Calendar.MINUTE, valor);
            hora.setTime(aux.getTime());
        }else if(aux.get(Calendar.MINUTE) > 15 && aux.get(Calendar.MINUTE) < 30) {
            int valor = 30 - aux.get(Calendar.MINUTE);
            aux.add(Calendar.MINUTE, valor);
            hora.setTime(aux.getTime());
        }else if(aux.get(Calendar.MINUTE) > 30 && aux.get(Calendar.MINUTE) < 45) {
            int valor = 45 - aux.get(Calendar.MINUTE);
            aux.add(Calendar.MINUTE, valor);
            hora.setTime(aux.getTime()); 
        }else if(aux.get(Calendar.MINUTE) > 45 && aux.get(Calendar.MINUTE) < 60){
            int valor = 60 - aux.get(Calendar.MINUTE);
            aux.add(Calendar.MINUTE, valor);
            hora.setTime(aux.getTime());
        }
        cal.set(Calendar.HOUR_OF_DAY, aux.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, aux.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, 00);
        
        //rellenar datos para hoy
        rellenarEventosDefault();
        indicarFechaSeleccionada();
        
        //seleccionando la hora del evento actual en la tabla
        seleccionaElementoTablaSegunHora();
    }
    
    //setea los valores para los veterinarios de esta sucursal
    public void rellenarVeterinarios(Sucursal suc) {
        for(DetalleUsuarios dt : jpf.buscarPorSucursal(suc)) {
            if(dt.getVeterinario() != null) {
                hr.insertarTexto(cmbVeterinario, dt.getVeterinario().getIdVeterinario()+": Dr. "+dt.getVeterinario().getNombres().split(" ")[0]+" "+dt.getVeterinario().getApaterno());
            }
        }
    }
    
    //setea los valores para los pacientes de un propietario
    public void rellenaPacientes() {
        if(pro != null) {
            for(Mascota m : pro.getMascotaList()) {
                hr.insertarTexto(cmbPaciente, m.getIdMascota()+": "+m.getNombre());
            }
        }
    }
    
    public void vaciarVeterinarios() {
        cmbVeterinario.removeAllItems();
    }
    
    public void vaciarPacientes() {
        cmbPaciente.removeAllItems();
    }
    
    public void actualizarProcesoRegistroPaciente() {
        btnFindActionPerformed(null);
    }
    
    public void rellenarEventosDefault() {
        DefaultTableModel modelo = new DefaultTableModel(new Object [][] { }, new String [] { "Hora", "Detalle" });
        Calendar aux = new GregorianCalendar();
        aux.set(Calendar.HOUR_OF_DAY, 0);
        aux.set(Calendar.MINUTE, 0);
        aux.set(Calendar.SECOND, 0);
        aux.set(Calendar.MILLISECOND, 000);
        int contador = 0;
        while(contador < 96 ) {
            Object[] obj = new Object[2];
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            obj[0] = df.format(aux.getTime());
            if(jpb.eventos(aux.getTime()).size() > 0) {
                for(Agenda a : jpb.eventos(aux.getTime())) {
                    if(a == null) {
                        obj[1] = "Libre";
                    }else{
                        obj[1] = "["+a.getIdEvento()+"] "+a.getAgendaDetalleList().get(0).getMascota().getNombre()+": Propietario "+a.getAgendaDetalleList().get(0).getMascota().getPropietario().getRut()+"-"+a.getAgendaDetalleList().get(0).getMascota().getPropietario().getDv();
                    }
                }
            }else{
                obj[1] = "Libre";
            }
            modelo.addRow(obj);
            aux.add(Calendar.MINUTE, 15);
            contador++;
        }
        //arreglar este método
        
        tablaResultados.setModel(modelo);
        tablaResultados.getColumnModel().getColumn(0).setMaxWidth(45);
        tablaResultados.getColumnModel().getColumn(0).setMinWidth(45);
        tablaResultados.getColumnModel().getColumn(0).setWidth(45);
    }
    
    public void rellenarEventosByFecha(Calendar fecha) {
        try {
            DefaultTableModel modelo = new DefaultTableModel(new Object [][] { }, new String [] { "Hora", "Detalle" });
            Calendar aux = new GregorianCalendar();
            aux.set(Calendar.DAY_OF_MONTH, fecha.get(Calendar.DAY_OF_MONTH));
            aux.set(Calendar.MONTH, fecha.get(Calendar.MONTH));
            aux.set(Calendar.YEAR, fecha.get(Calendar.YEAR));
            aux.set(Calendar.HOUR_OF_DAY, 0);
            aux.set(Calendar.MINUTE, 0);
            aux.set(Calendar.SECOND, 0);
            aux.set(Calendar.MILLISECOND, 000);
            int contador = 0;
            while(contador < 96 ) {
                Object[] obj = new Object[2];
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                obj[0] = df.format(aux.getTime());
                if(jpb.eventos(aux.getTime()).size() > 0) {
                    for(Agenda a : jpb.eventos(aux.getTime())) {
                        if(a == null) {
                            obj[1] = "Libre";
                        }else{
                            obj[1] = "["+a.getIdEvento()+"] "+a.getAgendaDetalleList().get(0).getMascota().getNombre()+": Propietario "+a.getAgendaDetalleList().get(0).getMascota().getPropietario().getRut()+"-"+a.getAgendaDetalleList().get(0).getMascota().getPropietario().getDv();
                        }
                    }
                }else{
                    obj[1] = "Libre";
                }
                modelo.addRow(obj);
                aux.add(Calendar.MINUTE, 15);
                contador++;
            }
            //arreglar este método

            tablaResultados.setModel(modelo);
            tablaResultados.getColumnModel().getColumn(0).setMaxWidth(45);
            tablaResultados.getColumnModel().getColumn(0).setMinWidth(45);
            tablaResultados.getColumnModel().getColumn(0).setWidth(45);
            //cal.add(Calendar.DAY_OF_MONTH, -1);
        }catch(Exception e) {
        }
    }
    
    public void seleccionaElementoTablaSegunHora() {
        try {
            Calendar aux = cal;
            String elemento = new SimpleDateFormat("HH:mm").format(aux.getTime());
            for(int i = 0; i<tablaResultados.getRowCount(); i++) {
                if(tablaResultados.getValueAt(i, 0).toString().compareToIgnoreCase(elemento) == 0) {
                    tablaResultados.changeSelection(i, 1, false, false);
                    break;
                }
            }
        } catch (Exception e) {
        }
    }
    
    public void indicarFechaSeleccionada() {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMMM-yyyy");
        hr.insertarTexto(lblEventosDetail, "Eventos para el "+df.format(calendario.getDate()).replace("-", " de "));
    }
    // --- fin de la libreria de metodos
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblMedicoVeterinario = new javax.swing.JLabel();
        lblRunPropietario = new javax.swing.JLabel();
        lblPaciente = new javax.swing.JLabel();
        cmbVeterinario = new javax.swing.JComboBox();
        txtRun = new javax.swing.JTextField();
        cmbPaciente = new javax.swing.JComboBox();
        btnFind = new javax.swing.JButton();
        lblPropietario = new javax.swing.JLabel();
        lblPropietarioData = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblDireccionData = new javax.swing.JLabel();
        lblCiudad = new javax.swing.JLabel();
        lblCiudadData = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        calendario = new com.toedter.calendar.JCalendar();
        hora = new lu.tudor.santec.jtimechooser.JTimeChooser();
        btnMenos = new javax.swing.JButton();
        btnMas = new javax.swing.JButton();
        lblHoraAgendar = new javax.swing.JLabel();
        lblFechaAgendar = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblEventosDetail = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();
        btnReservarHora = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnReestablecer = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Agendamiento de Horas");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Generales"));

        lblMedicoVeterinario.setText("Médico Veterinario");

        lblRunPropietario.setText("RUN Propietario");

        lblPaciente.setText("Paciente");

        txtRun.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRunFocusLost(evt);
            }
        });
        txtRun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRunKeyTyped(evt);
            }
        });

        cmbPaciente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cmbPacienteFocusGained(evt);
            }
        });

        btnFind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        lblPropietario.setText("Propietario");

        lblPropietarioData.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblPropietarioData.setText("No especificado");

        lblDireccion.setText("Dirección");

        lblDireccionData.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblDireccionData.setText("No especificado");

        lblCiudad.setText("Ciudad");

        lblCiudadData.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblCiudadData.setText("No especificado");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMedicoVeterinario)
                    .addComponent(lblPaciente)
                    .addComponent(lblRunPropietario)
                    .addComponent(lblPropietario)
                    .addComponent(lblDireccion)
                    .addComponent(lblCiudad))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtRun)
                        .addGap(10, 10, 10)
                        .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(cmbPaciente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPropietarioData)
                            .addComponent(cmbVeterinario, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDireccionData)
                            .addComponent(lblCiudadData))
                        .addGap(0, 8, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMedicoVeterinario)
                    .addComponent(cmbVeterinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRunPropietario)
                    .addComponent(txtRun, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPropietario)
                    .addComponent(lblPropietarioData))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDireccion)
                    .addComponent(lblDireccionData))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCiudadData)
                    .addComponent(lblCiudad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPaciente)
                    .addComponent(cmbPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Agendamiento"));

        calendario.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                calendarioPropertyChange(evt);
            }
        });

        btnMenos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/arrow_left.gif"))); // NOI18N
        btnMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenosActionPerformed(evt);
            }
        });

        btnMas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/arrow_right.gif"))); // NOI18N
        btnMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMasActionPerformed(evt);
            }
        });

        lblHoraAgendar.setText("Hora a agendar");

        lblFechaAgendar.setText("Fecha a Agendar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFechaAgendar)
                    .addComponent(calendario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblHoraAgendar)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnMenos, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFechaAgendar)
                    .addComponent(lblHoraAgendar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMenos, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calendario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMas, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Horarios"));

        lblEventosDetail.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblEventosDetail.setText("Eventos para : dd de mm de yyyy");

        tablaResultados.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Detalle"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaResultados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaResultadosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaResultados);
        if (tablaResultados.getColumnModel().getColumnCount() > 0) {
            tablaResultados.getColumnModel().getColumn(0).setResizable(false);
            tablaResultados.getColumnModel().getColumn(1).setResizable(false);
            tablaResultados.getColumnModel().getColumn(1).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblEventosDetail)
                        .addGap(0, 149, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lblEventosDetail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnReservarHora.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/calendar_add.png"))); // NOI18N
        btnReservarHora.setText("Reservar Hora");
        btnReservarHora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReservarHoraActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnReestablecer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/arrow_undo.png"))); // NOI18N
        btnReestablecer.setText("Reestablecer");
        btnReestablecer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReestablecerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReestablecer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnReservarHora)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReservarHora)
                    .addComponent(btnCancelar)
                    .addComponent(btnReestablecer))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenosActionPerformed
        //atrasa la hora a agendar en 15 minutos
        Calendar aux = new GregorianCalendar();
        //aux.add(Calendar.HOUR_OF_DAY, -1);
        Calendar aux3 = new GregorianCalendar();
        //aux3.add(Calendar.HOUR_OF_DAY, -1);
        Calendar aux2 = hora.getCalendarWithTime(aux);
        cal.add(Calendar.MINUTE, -15);
        if(!this.cal.getTime().before(aux3.getTime())) {
            calendario.setDate(cal.getTime());
            hora.setTime(cal.getTime());
        }else{
            cal.add(Calendar.MINUTE, 15);
            hr.mostrarError("Estas tratando de viajar en el tiempo: No se pueden seleccionar horas pasadas para agendar un evento.");
        }
        
        rellenarEventosByFecha(cal);
        indicarFechaSeleccionada();
        seleccionaElementoTablaSegunHora();
    }//GEN-LAST:event_btnMenosActionPerformed

    private void txtRunFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRunFocusLost
        //formanteando y validando el rut escrito del propietario
        if(!hr.contenido(txtRun).isEmpty()) {
            hr.insertarTexto(txtRun, ha.formatear(hr.contenido(txtRun)));
            //validando
            if(ha.validar(hr.contenido(txtRun))) {
                btnFindActionPerformed(null);
            }else{
                hr.mostrarError("Rut erroneo");
                txtRun.selectAll();
                hr.focus(txtRun);
            }
        }
    }//GEN-LAST:event_txtRunFocusLost

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        if(!hr.contenido(txtRun).isEmpty()) {
            hr.insertarTexto(txtRun, ha.formatear(hr.contenido(txtRun)));
            //validando
            if(ha.validar(hr.contenido(txtRun))) {
                //buscar los datos del propietario y almacenarlo en la variable global
                this.pro = jpe.buscarPorRut(hr.contenido(txtRun));
                if(pro == null) {
                    if(hr.preguntar("El propietario con RUN "+hr.contenido(txtRun)+" no se encuentra registrado.") == 0) {
                        RegistroPropietario rpro = new RegistroPropietario(suc);
                        rpro.setRun(hr.contenido(txtRun));
                        rpro.setVisible(true);
                    }else{
                        hr.insertarTexto(txtRun, "");
                        hr.focus(txtRun);
                        //Estado original
                        hr.desactivar(lblPropietario);
                        hr.desactivar(lblPropietarioData);
                        hr.insertarTexto(lblPropietarioData, "No especificado");
                        hr.desactivar(lblDireccion);
                        hr.desactivar(lblDireccionData);
                        hr.insertarTexto(lblDireccionData, "No especificado");
                        hr.desactivar(lblCiudad);
                        hr.desactivar(lblCiudadData);
                        hr.insertarTexto(lblCiudadData, "No especificado");
                        vaciarPacientes();
                        hr.activar(lblPaciente);
                        hr.desactivar(cmbPaciente);
                    }
                }else{
                    if(pro.getMascotaList().size() == 0) {
                        int respuesta = hr.preguntar("El propietario no tiene pacientes asociados. ¿Desea registrar un paciente asociado a este propietario?");
                        if(respuesta == 0) {
                            RegistroPaciente rpp = new RegistroPaciente(this);
                            rpp.setRun(hr.contenido(txtRun));
                            rpp.setVisible(true);
                            this.dispose();
                        }else{
                            //Estado original
                            hr.desactivar(lblPropietario);
                            hr.desactivar(lblPropietarioData);
                            hr.insertarTexto(lblPropietarioData, "No especificado");
                            hr.desactivar(lblDireccion);
                            hr.desactivar(lblDireccionData);
                            hr.insertarTexto(lblDireccionData, "No especificado");
                            hr.desactivar(lblCiudad);
                            hr.desactivar(lblCiudadData);
                            hr.insertarTexto(lblCiudadData, "No especificado");
                            vaciarPacientes();
                            hr.desactivar(cmbPaciente);
                            hr.desactivar(lblPaciente);
                            pro = null;
                        }
                    }else{
                        //rellenando datos y habilitando campos
                        hr.activar(lblPropietario);
                        hr.activar(lblPropietarioData);
                        hr.insertarTexto(lblPropietarioData, pro.getNombres().split(" ")[0]+" "+pro.getApaterno()+" "+pro.getAmaterno());
                        hr.activar(lblDireccion);
                        hr.activar(lblDireccionData);
                        hr.insertarTexto(lblDireccionData, pro.getDireccion());
                        hr.activar(lblCiudad);
                        hr.activar(lblCiudadData);
                        hr.insertarTexto(lblCiudadData, pro.getComuna().getNombre());
                        vaciarPacientes();
                        hr.activar(cmbPaciente);
                        rellenaPacientes();
                        hr.focus(cmbPaciente);
                        hr.activar(lblPaciente);
                    }
                }
            }else{
                hr.mostrarError("Rut erroneo");
                txtRun.selectAll();
                hr.focus(txtRun);
            }
        }
    }//GEN-LAST:event_btnFindActionPerformed

    private void cmbPacienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbPacienteFocusGained
        if(cmbPaciente.getItemCount() != 0) {
            hr.activar(lblFechaAgendar);
            hr.activar(lblHoraAgendar);
            //calendario.setEnabled(true);
            hora.setEnabled(true);
            hr.activar(btnMas);
            hr.activar(btnMenos);
        }else{
            hr.desactivar(lblFechaAgendar);
            hr.desactivar(lblHoraAgendar);
            //calendario.setEnabled(false);
            hora.setEnabled(false);
            hr.desactivar(btnMas);
            hr.desactivar(btnMenos);
        }
    }//GEN-LAST:event_cmbPacienteFocusGained

    private void btnMasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMasActionPerformed
        cal.add(Calendar.MINUTE, 15);
        calendario.setDate(cal.getTime());
        hora.setTime(cal.getTime());
        rellenarEventosByFecha(cal);
        indicarFechaSeleccionada();
        seleccionaElementoTablaSegunHora();
    }//GEN-LAST:event_btnMasActionPerformed

    private void tablaResultadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaResultadosMouseClicked
        //revisando acción del evento seleccionado
        if(cmbPaciente.isEnabled() == false && pro == null) {
            //edición de evento
            String elemento = hr.retornaValorTabla(1, tablaResultados);
            if(elemento.compareToIgnoreCase("libre") == 0) {
                hr.mostrarError("No se puede editar una hora sin agendamiento previo");
            }else{
                //descoponiendo & obteniendo identificador de agenda
                String identificador = hr.retornaValorTabla(1, tablaResultados);
                identificador = identificador.split("]")[0].replace("[", "").trim();
                //obteniendo detalles del evento
                try{
                    Agenda aux = jpb.findAgenda(Integer.parseInt(identificador));
                    if(aux == null) {
                        hr.mostrarError("El evento con identificador "+identificador+" ya no esta disponible en la base de datos");
                    }else{
                        if(hr.preguntar("¿Desea ver el detalle del evento del "+new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss").format(aux.getFechaEvento()).replace(" ", " a las ").replace("-", " de ")+" para el paciente "+aux.getAgendaDetalleList().get(0).getMascota().getNombre()+"?") == 0) {
                            new DetalleEvento(aux).setVisible(true);
                        }
                    }
                }catch(Exception e) {
                    hr.mostrarError("No se pudo encontrar el evento: "+e.getMessage());
                }
            }
        }else{
            //creación dee evento
            Calendar aux = this.cal;
            Calendar aux2 = new GregorianCalendar(TimeZone.getTimeZone("GMT-4"));
            //aux2.add(Calendar.HOUR_OF_DAY, -1);
            String horaSeleccionada = hr.retornaValorTabla(0, tablaResultados);
            aux.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaSeleccionada.split(":")[0]));
            aux.set(Calendar.MINUTE, Integer.parseInt(horaSeleccionada.split(":")[1]));
            aux.set(Calendar.SECOND, 0);
            aux.set(Calendar.MILLISECOND, 000);
            //vericando que la hora seleccionada sea anterior a la hora actual
            if(aux.getTime().before(aux2.getTime())) {
                hr.mostrarError("La hora seleccionada no puede ser asignada ya que es una hora pasada");
            }else{
                //verificando que la hora este libre
                if(hr.retornaValorTabla(1, tablaResultados).compareToIgnoreCase("libre") == 0) {
                    cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaSeleccionada.split(":")[0]));
                    cal.set(Calendar.MINUTE, Integer.parseInt(horaSeleccionada.split(":")[1]));
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 000);
                    //actualizar valores en campo de selección de hora
                    hora.setTime(aux.getTime());                       
                }else{
                    hr.mostrarError("El "+new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss").format(cal.getTime()).replace(" ", " existe una hora reservada a las ").replace("-", " de ")+".");
                }
            }
        }
        
    }//GEN-LAST:event_tablaResultadosMouseClicked

    private void calendarioPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_calendarioPropertyChange
        try {
            this.cal.set(Calendar.DAY_OF_MONTH, calendario.getCalendar().get(Calendar.DAY_OF_MONTH));
            this.cal.set(Calendar.MONTH, calendario.getCalendar().get(Calendar.MONTH));
            this.cal.set(Calendar.YEAR, calendario.getCalendar().get(Calendar.YEAR));
            rellenarEventosByFecha(cal);
            indicarFechaSeleccionada();
            seleccionaElementoTablaSegunHora();
        }catch(Exception e) {
            
        }
    }//GEN-LAST:event_calendarioPropertyChange

    private void btnReestablecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReestablecerActionPerformed
        //seteando los valores globales de hora y seteando valores en calendario y agendamiento de hora
        inicializarAgendamiento();
        
        //setenado valores de prueba de aplicación
        this.suc = jpd.findSucursal(1);
        
        //rellenando lista de veterinarios de la sucursal
        rellenarVeterinarios(suc);
        if(cmbVeterinario.getItemCount() == 0) {
            deshabilitarCamposSeleccion();
        }else{
            deshabilitarCamposSeleccion();
            hr.activar(lblRunPropietario);
            hr.activar(txtRun);
            hr.activar(btnFind);
            hr.focus(txtRun);
        }
        pro = null;
        age = null;
        vaciarPacientes();
        hr.insertarTexto(txtRun, "");
        hr.focus(txtRun);
        //Estado original
        hr.desactivar(lblPropietario);
        hr.desactivar(lblPropietarioData);
        hr.insertarTexto(lblPropietarioData, "No especificado");
        hr.desactivar(lblDireccion);
        hr.desactivar(lblDireccionData);
        hr.insertarTexto(lblDireccionData, "No especificado");
        hr.desactivar(lblCiudad);
        hr.desactivar(lblCiudadData);
        hr.insertarTexto(lblCiudadData, "No especificado");
        vaciarPacientes();
        hr.activar(lblPaciente);
        hr.desactivar(cmbPaciente);
        
    }//GEN-LAST:event_btnReestablecerActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
        System.gc();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnReservarHoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReservarHoraActionPerformed
        if((/*pro != null &&*/ cmbPaciente.isEnabled() == true) && age == null) {
            //crear agendamiento
            try {
                if(hr.retornaValorTabla(1, tablaResultados).compareToIgnoreCase("libre") == 0) {
                    Agenda aux = new Agenda();
                    AgendaDetalle gaux = new AgendaDetalle();
                    //recuperando datos
                    Veterinario vet = new VeterinarioJpaController(emf).findVeterinario(Integer.parseInt(hr.contenido(cmbVeterinario).split(":")[0]));
                    Mascota     mas = new MascotaJpaController(emf).findMascota(Integer.parseInt(hr.contenido(cmbPaciente).split(":")[0]));

                    //asignando valores
                    aux.setIdEvento(jpb.ultimo());
                    aux.setFechaEvento(this.cal.getTime());
                    aux.setEstado("0");
                    aux.setSucursal(suc);

                    gaux.setIdDetalle(jpa.ultimo());
                    gaux.setEventoAgenda(aux);
                    gaux.setVeterinario(vet);
                    gaux.setMascota(mas);

                    //escribiendo datos en DB
                    jpb.create(aux);
                    jpa.create(gaux);
                    hr.mostrarMensaje("Se realizó con exito la reserva para el "+new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss").format(cal.getTime()).replace(" ", "  a las ").replace("-", " de "));
                    btnReestablecerActionPerformed(evt);
                }else{
                    hr.mostrarError("El "+new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss").format(cal.getTime()).replace(" ", " existe una hora reservada a las ").replace("-", " de ")+".");                    
                }
            }catch(Exception e) {
                hr.mostrarError("Ocurrió un error al intentar registrar el evento: "+e.getMessage());
            }
        }
    }//GEN-LAST:event_btnReservarHoraActionPerformed

    private void txtRunKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRunKeyTyped
        hr.ingresaCaracteresRut(evt);
        hr.largoMaximo(txtRun, 12, evt);
    }//GEN-LAST:event_txtRunKeyTyped

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AgendaAtencion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgendaAtencion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgendaAtencion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgendaAtencion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgendaAtencion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnMas;
    private javax.swing.JButton btnMenos;
    private javax.swing.JButton btnReestablecer;
    private javax.swing.JButton btnReservarHora;
    private com.toedter.calendar.JCalendar calendario;
    private javax.swing.JComboBox cmbPaciente;
    private javax.swing.JComboBox cmbVeterinario;
    private lu.tudor.santec.jtimechooser.JTimeChooser hora;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblCiudad;
    private javax.swing.JLabel lblCiudadData;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDireccionData;
    private javax.swing.JLabel lblEventosDetail;
    private javax.swing.JLabel lblFechaAgendar;
    private javax.swing.JLabel lblHoraAgendar;
    private javax.swing.JLabel lblMedicoVeterinario;
    private javax.swing.JLabel lblPaciente;
    private javax.swing.JLabel lblPropietario;
    private javax.swing.JLabel lblPropietarioData;
    private javax.swing.JLabel lblRunPropietario;
    private javax.swing.JTable tablaResultados;
    private javax.swing.JTextField txtRun;
    // End of variables declaration//GEN-END:variables
}
