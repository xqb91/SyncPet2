/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.principal;

import cl.starlabs.controladores.AgendaJpaController;
import cl.starlabs.herramientas.HerramientasRapidas;
import cl.starlabs.modelo.Agenda;
import cl.starlabs.modelo.Sucursal;
import cl.starlabs.modelo.Usuarios;
import cl.starlabs.vista.administracion.clinica.AdministracionClinica;
import cl.starlabs.vista.administracion.clinica.AdministracionSucursales;
import cl.starlabs.vista.administracion.config.AdminCaracter;
import cl.starlabs.vista.administracion.config.AdminContraindicaciones;
import cl.starlabs.vista.administracion.config.AdminEspecies;
import cl.starlabs.vista.administracion.config.AdminExamenes;
import cl.starlabs.vista.administracion.config.AdminFarmacos;
import cl.starlabs.vista.administracion.config.AdminHabitad;
import cl.starlabs.vista.administracion.config.AdminPatologia;
import cl.starlabs.vista.administracion.config.AdminProcedimientos;
import cl.starlabs.vista.administracion.config.AdminRazas;
import cl.starlabs.vista.administracion.config.AdminTipoAlergias;
import cl.starlabs.vista.administracion.config.AdminVacunas;
import cl.starlabs.vista.administracion.geografia.AdministradorComunas;
import cl.starlabs.vista.administracion.geografia.AdministradorPaises;
import cl.starlabs.vista.administracion.geografia.AdministradorProvincias;
import cl.starlabs.vista.administracion.geografia.AdministradorRegiones;
import cl.starlabs.vista.administracion.trabajadores.AdministradorTrabajadores;
import cl.starlabs.vista.agenda.AgendaAtencion;
import cl.starlabs.vista.agenda.BuscarAtencion;
import cl.starlabs.vista.agenda.Calendario;
import cl.starlabs.vista.agenda.DetalleEvento;
import cl.starlabs.vista.agenda.EventosParaHoy;
import cl.starlabs.vista.login.PantallaBloqueo;
import cl.starlabs.vista.paciente.BuscarPaciente;
import cl.starlabs.vista.paciente.DetalleProgenitores;
import cl.starlabs.vista.paciente.ListarPacientes;
import cl.starlabs.vista.paciente.RegistroPaciente;
import cl.starlabs.vista.propietario.BuscarPropietario;
import cl.starlabs.vista.propietario.ListarPropietarios;
import cl.starlabs.vista.propietario.RegistroPropietario;
import cl.starlabs.vista.veterinario.BuscarVeterinario;
import cl.starlabs.vista.veterinario.ListarVeterinario;
import cl.starlabs.vista.veterinario.RegistroVeterinario;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Victor Manuel Araya
 */
public class PrincipalAdmin extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     */
    Usuarios u = null;
    Sucursal s = null;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SyncPetPU");
    AgendaJpaController jpb = new AgendaJpaController(emf);
    
    public PrincipalAdmin() {
        initComponents();
        //centrando ventana
        this.setLocationRelativeTo(null);
        //colocando icono a ventana
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/cl/starlabs/imagenes/sistema/logo_renovado.png"));
        setIconImage(icon);
        setVisible(true);
        rellenarEventosDefault();
    }
    
     public PrincipalAdmin(Usuarios u, Sucursal s) {
        initComponents();
        //usuario
        this.u = u;
        //sucursal
        this.s = s;
        //seteando el titulo de la ventana
        this.setTitle("SyncPet Administrador :: Conectado como "+u.getUsuario()+" ("+s.getNombre()+")");
        //centrando ventana
        this.setLocationRelativeTo(null);
        //colocando icono a ventana
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/cl/starlabs/imagenes/sistema/logo_renovado.png"));
        setIconImage(icon);
        setVisible(true);
        rellenarEventosDefault();
    }

     
    public void rellenarEventosDefault() {
        DefaultTableModel modelo = new DefaultTableModel(new Object [][] { }, new String [] { "Hora", "Evento", "Paciente", "Propietario" });
        Calendar inicio = new GregorianCalendar();
        inicio.set(Calendar.MILLISECOND, 000);
        Calendar finali = new GregorianCalendar();
        finali.set(Calendar.HOUR_OF_DAY, 23);
        finali.set(Calendar.MINUTE, 59);
        finali.set(Calendar.SECOND, 59);
        finali.set(Calendar.MILLISECOND, 999);
        
        for(Agenda a : jpb.eventosPorFecha(inicio.getTime(), finali.getTime())) {
            Object[] obj = new Object[4];
            obj[0] = new SimpleDateFormat("HH:mm").format(a.getFechaEvento());
            obj[1] = a.getIdEvento();
            obj[2] = a.getAgendaDetalleList().get(0).getMascota().getNombre();
            obj[3] = a.getAgendaDetalleList().get(0).getMascota().getPropietario().getNombres().split(" ")[0]+" "+a.getAgendaDetalleList().get(0).getMascota().getPropietario().getApaterno();
            modelo.addRow(obj);
        }
        //arreglar este método
        
        tablaAtencionesProximas.setModel(modelo);
        tablaAtencionesProximas.getColumnModel().getColumn(0).setMaxWidth(45);
        tablaAtencionesProximas.getColumnModel().getColumn(0).setMinWidth(45);
        tablaAtencionesProximas.getColumnModel().getColumn(0).setWidth(45);
        tablaAtencionesProximas.getColumnModel().getColumn(1).setMaxWidth(45);
        tablaAtencionesProximas.getColumnModel().getColumn(1).setMinWidth(45);
        tablaAtencionesProximas.getColumnModel().getColumn(1).setWidth(45);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelResumen = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblNumPropietarios = new javax.swing.JLabel();
        lblNumPacientes = new javax.swing.JLabel();
        lblNumCumpleaños = new javax.swing.JLabel();
        lblNumHospitalizados = new javax.swing.JLabel();
        lblHoraSistema = new javax.swing.JLabel();
        panelProximasAtenciones = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaAtencionesProximas = new javax.swing.JTable();
        btnAddPaciente = new javax.swing.JButton();
        btnAddPropietario = new javax.swing.JButton();
        btnAddEvento = new javax.swing.JButton();
        btnBloquearTerminal = new javax.swing.JButton();
        btnCambiarSucursal = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenSyncpet = new javax.swing.JMenu();
        syncmen_server = new javax.swing.JMenuItem();
        syncmen_logout = new javax.swing.JMenuItem();
        syncmen_lock = new javax.swing.JMenuItem();
        syncmen_salir = new javax.swing.JMenuItem();
        menPacientes = new javax.swing.JMenu();
        menPacientes_add = new javax.swing.JMenuItem();
        menPacientes_find = new javax.swing.JMenuItem();
        menPacientes_admin = new javax.swing.JMenuItem();
        menPacientes_tree = new javax.swing.JMenuItem();
        menPropietarios = new javax.swing.JMenu();
        menPropietarios_add = new javax.swing.JMenuItem();
        menPropietarios_find = new javax.swing.JMenuItem();
        menPropietarios_admin = new javax.swing.JMenuItem();
        menAgenda = new javax.swing.JMenu();
        menAgenda_calendario = new javax.swing.JMenuItem();
        menAgenda_hoy = new javax.swing.JMenuItem();
        menAgenda_add = new javax.swing.JMenuItem();
        menAgenda_find = new javax.swing.JMenuItem();
        menAgenda_urgencia = new javax.swing.JMenuItem();
        menFicha = new javax.swing.JMenu();
        menFicha_anamnesis = new javax.swing.JMenuItem();
        menFicha_find = new javax.swing.JMenuItem();
        menRep = new javax.swing.JMenu();
        menAdmin = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        menAdmin_clinica = new javax.swing.JMenuItem();
        menAdmin_Sucursal = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        menAdmin_trabajadores = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        adminMenuGeografia = new javax.swing.JMenu();
        menAdminPais = new javax.swing.JMenuItem();
        menuAdminRegiones = new javax.swing.JMenuItem();
        menuAdminProvincias = new javax.swing.JMenuItem();
        menuAdminComunas = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        confAdminAlergias = new javax.swing.JMenuItem();
        confAdminEspecies = new javax.swing.JMenuItem();
        confAdminRazas = new javax.swing.JMenuItem();
        confAdminCaracter = new javax.swing.JMenuItem();
        confAdminHabitad = new javax.swing.JMenuItem();
        confAdminVacunas = new javax.swing.JMenuItem();
        confAdminExamenes = new javax.swing.JMenuItem();
        confAdminPatologias = new javax.swing.JMenuItem();
        confAdminContraindicacion = new javax.swing.JMenuItem();
        confAdminFarmacos = new javax.swing.JMenuItem();
        confAdminProcedimientos = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: conectado como usuario@nombre_clínica (Sucursal)");
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });

        panelResumen.setBorder(javax.swing.BorderFactory.createTitledBorder("Resumen del Sistema"));

        jLabel1.setText("Propietarios Registrados");

        jLabel2.setText("Pacientes Registrados");

        jLabel3.setText("Cumpleaños de Pacientes");

        jLabel4.setText("Pacientes Hospitalizados");

        jLabel5.setText("Hora del Servidor");

        lblNumPropietarios.setText("0 Propietarios");

        lblNumPacientes.setText("0 Pacientes");

        lblNumCumpleaños.setText("0 Cumpleaños hoy");

        lblNumHospitalizados.setText("0 Pacientes hospitalizados");

        lblHoraSistema.setText("00:00:00 del 00 de mes de 0000");

        javax.swing.GroupLayout panelResumenLayout = new javax.swing.GroupLayout(panelResumen);
        panelResumen.setLayout(panelResumenLayout);
        panelResumenLayout.setHorizontalGroup(
            panelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResumenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelResumenLayout.createSequentialGroup()
                        .addGroup(panelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(31, 31, 31)
                        .addGroup(panelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNumPropietarios)
                            .addComponent(lblNumPacientes)))
                    .addGroup(panelResumenLayout.createSequentialGroup()
                        .addGroup(panelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(26, 26, 26)
                        .addGroup(panelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblHoraSistema)
                            .addComponent(lblNumHospitalizados)
                            .addComponent(lblNumCumpleaños))))
                .addContainerGap(84, Short.MAX_VALUE))
        );
        panelResumenLayout.setVerticalGroup(
            panelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelResumenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblNumPropietarios))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblNumPacientes))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblNumCumpleaños))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblNumHospitalizados))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblHoraSistema))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelProximasAtenciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Próximas Atenciones Agendadas"));

        tablaAtencionesProximas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Paciente", "Propietario", "Hora", "Acciones"
            }
        ));
        tablaAtencionesProximas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaAtencionesProximasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaAtencionesProximas);

        javax.swing.GroupLayout panelProximasAtencionesLayout = new javax.swing.GroupLayout(panelProximasAtenciones);
        panelProximasAtenciones.setLayout(panelProximasAtencionesLayout);
        panelProximasAtencionesLayout.setHorizontalGroup(
            panelProximasAtencionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        panelProximasAtencionesLayout.setVerticalGroup(
            panelProximasAtencionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        btnAddPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/add.png"))); // NOI18N
        btnAddPaciente.setText("Agregar Paciente");
        btnAddPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPacienteActionPerformed(evt);
            }
        });

        btnAddPropietario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/add.png"))); // NOI18N
        btnAddPropietario.setText("Agregar Propietario");
        btnAddPropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddPropietarioActionPerformed(evt);
            }
        });

        btnAddEvento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/date.png"))); // NOI18N
        btnAddEvento.setText("Agendar Evento");

        btnBloquearTerminal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/sistema/logo_mini.png"))); // NOI18N
        btnBloquearTerminal.setText("Bloquear Terminal");
        btnBloquearTerminal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBloquearTerminalActionPerformed(evt);
            }
        });

        btnCambiarSucursal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/sistema/logo_mini.png"))); // NOI18N
        btnCambiarSucursal.setText("Cambiar de Sucursal");
        btnCambiarSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarSucursalActionPerformed(evt);
            }
        });

        MenSyncpet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/sistema/logo_mini.png"))); // NOI18N
        MenSyncpet.setText("SyncPet");

        syncmen_server.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        syncmen_server.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/server.png"))); // NOI18N
        syncmen_server.setText("Estado del Servidor");
        MenSyncpet.add(syncmen_server);

        syncmen_logout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        syncmen_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/disconnect.png"))); // NOI18N
        syncmen_logout.setText("Cerrar Sesión");
        syncmen_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syncmen_logoutActionPerformed(evt);
            }
        });
        MenSyncpet.add(syncmen_logout);

        syncmen_lock.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        syncmen_lock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/lock.png"))); // NOI18N
        syncmen_lock.setText("Bloquear Terminal");
        syncmen_lock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syncmen_lockActionPerformed(evt);
            }
        });
        MenSyncpet.add(syncmen_lock);

        syncmen_salir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        syncmen_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/door_out.png"))); // NOI18N
        syncmen_salir.setText("Salir");
        MenSyncpet.add(syncmen_salir);

        jMenuBar1.add(MenSyncpet);

        menPacientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/pet.png"))); // NOI18N
        menPacientes.setText("Pacientes");

        menPacientes_add.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        menPacientes_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/add.png"))); // NOI18N
        menPacientes_add.setText("Registrar Paciente");
        menPacientes_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPacientes_addActionPerformed(evt);
            }
        });
        menPacientes.add(menPacientes_add);

        menPacientes_find.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        menPacientes_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        menPacientes_find.setText("Buscar Paciente");
        menPacientes_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPacientes_findActionPerformed(evt);
            }
        });
        menPacientes.add(menPacientes_find);

        menPacientes_admin.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        menPacientes_admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/application.png"))); // NOI18N
        menPacientes_admin.setText("Administrar Pacientes");
        menPacientes_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPacientes_adminActionPerformed(evt);
            }
        });
        menPacientes.add(menPacientes_admin);

        menPacientes_tree.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        menPacientes_tree.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/chart_organisation.png"))); // NOI18N
        menPacientes_tree.setText("Especificar Progenitores");
        menPacientes_tree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPacientes_treeActionPerformed(evt);
            }
        });
        menPacientes.add(menPacientes_tree);

        jMenuBar1.add(menPacientes);

        menPropietarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/user.png"))); // NOI18N
        menPropietarios.setText("Propietarios");

        menPropietarios_add.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F6, 0));
        menPropietarios_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/user_add.png"))); // NOI18N
        menPropietarios_add.setText("Registrar Propietario");
        menPropietarios_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPropietarios_addActionPerformed(evt);
            }
        });
        menPropietarios.add(menPropietarios_add);

        menPropietarios_find.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_U, java.awt.event.InputEvent.CTRL_MASK));
        menPropietarios_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        menPropietarios_find.setText("Buscar Propietario");
        menPropietarios_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPropietarios_findActionPerformed(evt);
            }
        });
        menPropietarios.add(menPropietarios_find);

        menPropietarios_admin.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        menPropietarios_admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/application.png"))); // NOI18N
        menPropietarios_admin.setText("Administrar Propietarios");
        menPropietarios_admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menPropietarios_adminActionPerformed(evt);
            }
        });
        menPropietarios.add(menPropietarios_admin);

        jMenuBar1.add(menPropietarios);

        menAgenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/calendar.png"))); // NOI18N
        menAgenda.setText("Agenda");

        menAgenda_calendario.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        menAgenda_calendario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/date.png"))); // NOI18N
        menAgenda_calendario.setText("Calendario");
        menAgenda_calendario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menAgenda_calendarioActionPerformed(evt);
            }
        });
        menAgenda.add(menAgenda_calendario);

        menAgenda_hoy.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menAgenda_hoy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/bell.png"))); // NOI18N
        menAgenda_hoy.setText("Eventos para hoy");
        menAgenda_hoy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menAgenda_hoyActionPerformed(evt);
            }
        });
        menAgenda.add(menAgenda_hoy);

        menAgenda_add.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F7, 0));
        menAgenda_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/calendar_add.png"))); // NOI18N
        menAgenda_add.setText("Agendar Atención");
        menAgenda_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menAgenda_addActionPerformed(evt);
            }
        });
        menAgenda.add(menAgenda_add);

        menAgenda_find.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        menAgenda_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        menAgenda_find.setText("Buscar Atención");
        menAgenda_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menAgenda_findActionPerformed(evt);
            }
        });
        menAgenda.add(menAgenda_find);

        menAgenda_urgencia.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        menAgenda_urgencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/exclamation.png"))); // NOI18N
        menAgenda_urgencia.setText("Atención de Urgencia");
        menAgenda.add(menAgenda_urgencia);

        jMenuBar1.add(menAgenda);

        menFicha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/book.png"))); // NOI18N
        menFicha.setText("Ficha Clínica");

        menFicha_anamnesis.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F3, 0));
        menFicha_anamnesis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/book_go.png"))); // NOI18N
        menFicha_anamnesis.setText("Anamnesis");
        menFicha.add(menFicha_anamnesis);

        menFicha_find.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, 0));
        menFicha_find.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        menFicha_find.setText("Busca Paciente");
        menFicha.add(menFicha_find);

        jMenuBar1.add(menFicha);

        menRep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/chart_curve.png"))); // NOI18N
        menRep.setText("Reportes");
        jMenuBar1.add(menRep);

        menAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/cog.png"))); // NOI18N
        menAdmin.setText("Administración");

        jMenu9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/building_link.png"))); // NOI18N
        jMenu9.setText("Clínica y Sucursales");

        menAdmin_clinica.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        menAdmin_clinica.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/building.png"))); // NOI18N
        menAdmin_clinica.setText("Información de Clínica");
        menAdmin_clinica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menAdmin_clinicaActionPerformed(evt);
            }
        });
        jMenu9.add(menAdmin_clinica);

        menAdmin_Sucursal.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.CTRL_MASK));
        menAdmin_Sucursal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/building_add.png"))); // NOI18N
        menAdmin_Sucursal.setText("Sucursales");
        menAdmin_Sucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menAdmin_SucursalActionPerformed(evt);
            }
        });
        jMenu9.add(menAdmin_Sucursal);

        menAdmin.add(jMenu9);

        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/user_gray.png"))); // NOI18N
        jMenu8.setText("Trabajadores");

        menAdmin_trabajadores.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        menAdmin_trabajadores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/application.png"))); // NOI18N
        menAdmin_trabajadores.setText("Administrar Trabajadores");
        menAdmin_trabajadores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menAdmin_trabajadoresActionPerformed(evt);
            }
        });
        jMenu8.add(menAdmin_trabajadores);
        jMenu8.add(jSeparator1);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/user_suit.png"))); // NOI18N
        jMenuItem1.setText("Registrar Veterinarios");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        jMenuItem2.setText("Buscar Veterinario");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/application_side_expand.png"))); // NOI18N
        jMenuItem3.setText("Administrar Veterinarios");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem3);

        menAdmin.add(jMenu8);

        adminMenuGeografia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/world_2.png"))); // NOI18N
        adminMenuGeografia.setText("Geografía");

        menAdminPais.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/world.png"))); // NOI18N
        menAdminPais.setText("Administrar Paises");
        menAdminPais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menAdminPaisActionPerformed(evt);
            }
        });
        adminMenuGeografia.add(menAdminPais);

        menuAdminRegiones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/world.png"))); // NOI18N
        menuAdminRegiones.setText("Administrar Regiones");
        menuAdminRegiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAdminRegionesActionPerformed(evt);
            }
        });
        adminMenuGeografia.add(menuAdminRegiones);

        menuAdminProvincias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/world.png"))); // NOI18N
        menuAdminProvincias.setText("Administrar Provincias");
        menuAdminProvincias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAdminProvinciasActionPerformed(evt);
            }
        });
        adminMenuGeografia.add(menuAdminProvincias);

        menuAdminComunas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/world.png"))); // NOI18N
        menuAdminComunas.setText("Administrar Comunas");
        menuAdminComunas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAdminComunasActionPerformed(evt);
            }
        });
        adminMenuGeografia.add(menuAdminComunas);

        menAdmin.add(adminMenuGeografia);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/sistema/logo_mini.png"))); // NOI18N
        jMenu1.setText("Configuración de Términos");

        confAdminAlergias.setText("Administrar Tipo de Alergias");
        confAdminAlergias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confAdminAlergiasActionPerformed(evt);
            }
        });
        jMenu1.add(confAdminAlergias);

        confAdminEspecies.setText("Administrar Especies");
        confAdminEspecies.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confAdminEspeciesActionPerformed(evt);
            }
        });
        jMenu1.add(confAdminEspecies);

        confAdminRazas.setText("Administrar Razas");
        confAdminRazas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confAdminRazasActionPerformed(evt);
            }
        });
        jMenu1.add(confAdminRazas);

        confAdminCaracter.setText("Administrar Carácter");
        confAdminCaracter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confAdminCaracterActionPerformed(evt);
            }
        });
        jMenu1.add(confAdminCaracter);

        confAdminHabitad.setText("Administrar Habitad");
        confAdminHabitad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confAdminHabitadActionPerformed(evt);
            }
        });
        jMenu1.add(confAdminHabitad);

        confAdminVacunas.setText("Administrar Vacunas");
        confAdminVacunas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confAdminVacunasActionPerformed(evt);
            }
        });
        jMenu1.add(confAdminVacunas);

        confAdminExamenes.setText("Administrar Examenes");
        confAdminExamenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confAdminExamenesActionPerformed(evt);
            }
        });
        jMenu1.add(confAdminExamenes);

        confAdminPatologias.setText("Administrar Patologías");
        confAdminPatologias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confAdminPatologiasActionPerformed(evt);
            }
        });
        jMenu1.add(confAdminPatologias);

        confAdminContraindicacion.setText("Administrar Contraindicaciones");
        confAdminContraindicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confAdminContraindicacionActionPerformed(evt);
            }
        });
        jMenu1.add(confAdminContraindicacion);

        confAdminFarmacos.setText("Administrar Farmacos");
        confAdminFarmacos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confAdminFarmacosActionPerformed(evt);
            }
        });
        jMenu1.add(confAdminFarmacos);

        confAdminProcedimientos.setText("Administrar Procedimientos");
        confAdminProcedimientos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confAdminProcedimientosActionPerformed(evt);
            }
        });
        jMenu1.add(confAdminProcedimientos);

        menAdmin.add(jMenu1);

        jMenuBar1.add(menAdmin);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelResumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(panelProximasAtenciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnAddPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBloquearTerminal, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCambiarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelResumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelProximasAtenciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBloquearTerminal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCambiarSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void syncmen_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syncmen_logoutActionPerformed
        u = null;
        s = null;
        new cl.starlabs.vista.login.IniciarSesion().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_syncmen_logoutActionPerformed

    private void syncmen_lockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_syncmen_lockActionPerformed
        new PantallaBloqueo(this, u).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_syncmen_lockActionPerformed

    private void menPacientes_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPacientes_addActionPerformed
        new RegistroPaciente(u).setVisible(true);
    }//GEN-LAST:event_menPacientes_addActionPerformed

    private void menPacientes_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPacientes_adminActionPerformed
        new ListarPacientes(u, s).setVisible(true);
    }//GEN-LAST:event_menPacientes_adminActionPerformed

    private void menAdminPaisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menAdminPaisActionPerformed
        new AdministradorPaises().setVisible(true);
    }//GEN-LAST:event_menAdminPaisActionPerformed

    private void menuAdminRegionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAdminRegionesActionPerformed
        new AdministradorRegiones().setVisible(true);
    }//GEN-LAST:event_menuAdminRegionesActionPerformed

    private void menuAdminProvinciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAdminProvinciasActionPerformed
        new AdministradorProvincias().setVisible(true);
    }//GEN-LAST:event_menuAdminProvinciasActionPerformed

    private void menuAdminComunasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAdminComunasActionPerformed
        new AdministradorComunas().setVisible(true);
    }//GEN-LAST:event_menuAdminComunasActionPerformed

    private void menAdmin_trabajadoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menAdmin_trabajadoresActionPerformed
        new AdministradorTrabajadores(s).setVisible(true);
    }//GEN-LAST:event_menAdmin_trabajadoresActionPerformed

    private void menAdmin_SucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menAdmin_SucursalActionPerformed
        new AdministracionSucursales(s).setVisible(true);
    }//GEN-LAST:event_menAdmin_SucursalActionPerformed

    private void menAdmin_clinicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menAdmin_clinicaActionPerformed
        new AdministracionClinica(s).setVisible(true);
    }//GEN-LAST:event_menAdmin_clinicaActionPerformed

    private void confAdminAlergiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confAdminAlergiasActionPerformed
        new AdminTipoAlergias().setVisible(true);
    }//GEN-LAST:event_confAdminAlergiasActionPerformed

    private void confAdminEspeciesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confAdminEspeciesActionPerformed
        new AdminEspecies().setVisible(true);
    }//GEN-LAST:event_confAdminEspeciesActionPerformed

    private void confAdminRazasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confAdminRazasActionPerformed
        new AdminRazas().setVisible(true);
    }//GEN-LAST:event_confAdminRazasActionPerformed

    private void confAdminCaracterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confAdminCaracterActionPerformed
        new AdminCaracter().setVisible(true);
    }//GEN-LAST:event_confAdminCaracterActionPerformed

    private void confAdminHabitadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confAdminHabitadActionPerformed
        new AdminHabitad().setVisible(true);
    }//GEN-LAST:event_confAdminHabitadActionPerformed

    private void confAdminVacunasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confAdminVacunasActionPerformed
        new AdminVacunas().setVisible(true);
    }//GEN-LAST:event_confAdminVacunasActionPerformed

    private void confAdminExamenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confAdminExamenesActionPerformed
        new AdminExamenes().setVisible(true);
    }//GEN-LAST:event_confAdminExamenesActionPerformed

    private void confAdminPatologiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confAdminPatologiasActionPerformed
        new AdminPatologia().setVisible(true);
    }//GEN-LAST:event_confAdminPatologiasActionPerformed

    private void confAdminContraindicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confAdminContraindicacionActionPerformed
        new AdminContraindicaciones().setVisible(true);
    }//GEN-LAST:event_confAdminContraindicacionActionPerformed

    private void confAdminFarmacosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confAdminFarmacosActionPerformed
        new AdminFarmacos().setVisible(true);
    }//GEN-LAST:event_confAdminFarmacosActionPerformed

    private void confAdminProcedimientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confAdminProcedimientosActionPerformed
        new AdminProcedimientos().setVisible(true);
    }//GEN-LAST:event_confAdminProcedimientosActionPerformed

    private void menPacientes_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPacientes_findActionPerformed
        new BuscarPaciente(u).setVisible(true);
    }//GEN-LAST:event_menPacientes_findActionPerformed

    private void btnBloquearTerminalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBloquearTerminalActionPerformed
        new PantallaBloqueo(this, u).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBloquearTerminalActionPerformed

    private void btnAddPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPacienteActionPerformed
        new RegistroPaciente(u).setVisible(true);
    }//GEN-LAST:event_btnAddPacienteActionPerformed

    private void menPacientes_treeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPacientes_treeActionPerformed
        new DetalleProgenitores().setVisible(true);
    }//GEN-LAST:event_menPacientes_treeActionPerformed

    private void menPropietarios_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPropietarios_addActionPerformed
        new RegistroPropietario(s).setVisible(true);
    }//GEN-LAST:event_menPropietarios_addActionPerformed

    private void menPropietarios_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPropietarios_findActionPerformed
        new BuscarPropietario(s).setVisible(true);
    }//GEN-LAST:event_menPropietarios_findActionPerformed

    private void menPropietarios_adminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menPropietarios_adminActionPerformed
        new ListarPropietarios(s).setVisible(true);
    }//GEN-LAST:event_menPropietarios_adminActionPerformed

    private void btnAddPropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddPropietarioActionPerformed
        new RegistroPropietario(s).setVisible(true);
    }//GEN-LAST:event_btnAddPropietarioActionPerformed

    private void btnCambiarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarSucursalActionPerformed
        u = null;
        s = null;
        new cl.starlabs.vista.login.IniciarSesion().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCambiarSucursalActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new ListarVeterinario(s).setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new BuscarVeterinario(s).setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new RegistroVeterinario(s).setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void menAgenda_calendarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menAgenda_calendarioActionPerformed
        new Calendario().setVisible(true);
    }//GEN-LAST:event_menAgenda_calendarioActionPerformed

    private void menAgenda_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menAgenda_addActionPerformed
        new AgendaAtencion(s).setVisible(true);
    }//GEN-LAST:event_menAgenda_addActionPerformed

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        rellenarEventosDefault();
    }//GEN-LAST:event_formWindowLostFocus

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        rellenarEventosDefault();
    }//GEN-LAST:event_formWindowGainedFocus

    private void menAgenda_hoyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menAgenda_hoyActionPerformed
        new EventosParaHoy().setVisible(true);
    }//GEN-LAST:event_menAgenda_hoyActionPerformed

    private void menAgenda_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menAgenda_findActionPerformed
        new BuscarAtencion().setVisible(true);
    }//GEN-LAST:event_menAgenda_findActionPerformed

    private void tablaAtencionesProximasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaAtencionesProximasMouseClicked
        String identificador = new cl.starlabs.herramientas.HerramientasRapidas().retornaValorTabla(1, tablaAtencionesProximas);
        identificador = identificador.split("]")[0].replace("[", "").trim();
        Agenda aux = jpb.findAgenda(Integer.parseInt(identificador));
        if(aux != null) {
            if(new cl.starlabs.herramientas.HerramientasRapidas().preguntar("¿Desea ver el detalle del evento del "+new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss").format(aux.getFechaEvento()).replace(" ", " a las ").replace("-", " de ")+" para el paciente "+aux.getAgendaDetalleList().get(0).getMascota().getNombre()+"?") == 0) {
                new DetalleEvento(aux).setVisible(true);
            }
        }
    }//GEN-LAST:event_tablaAtencionesProximasMouseClicked

    
    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(PrincipalAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu MenSyncpet;
    private javax.swing.JMenu adminMenuGeografia;
    private javax.swing.JButton btnAddEvento;
    private javax.swing.JButton btnAddPaciente;
    private javax.swing.JButton btnAddPropietario;
    private javax.swing.JButton btnBloquearTerminal;
    private javax.swing.JButton btnCambiarSucursal;
    private javax.swing.JMenuItem confAdminAlergias;
    private javax.swing.JMenuItem confAdminCaracter;
    private javax.swing.JMenuItem confAdminContraindicacion;
    private javax.swing.JMenuItem confAdminEspecies;
    private javax.swing.JMenuItem confAdminExamenes;
    private javax.swing.JMenuItem confAdminFarmacos;
    private javax.swing.JMenuItem confAdminHabitad;
    private javax.swing.JMenuItem confAdminPatologias;
    private javax.swing.JMenuItem confAdminProcedimientos;
    private javax.swing.JMenuItem confAdminRazas;
    private javax.swing.JMenuItem confAdminVacunas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblHoraSistema;
    private javax.swing.JLabel lblNumCumpleaños;
    private javax.swing.JLabel lblNumHospitalizados;
    private javax.swing.JLabel lblNumPacientes;
    private javax.swing.JLabel lblNumPropietarios;
    private javax.swing.JMenu menAdmin;
    private javax.swing.JMenuItem menAdminPais;
    private javax.swing.JMenuItem menAdmin_Sucursal;
    private javax.swing.JMenuItem menAdmin_clinica;
    private javax.swing.JMenuItem menAdmin_trabajadores;
    private javax.swing.JMenu menAgenda;
    private javax.swing.JMenuItem menAgenda_add;
    private javax.swing.JMenuItem menAgenda_calendario;
    private javax.swing.JMenuItem menAgenda_find;
    private javax.swing.JMenuItem menAgenda_hoy;
    private javax.swing.JMenuItem menAgenda_urgencia;
    private javax.swing.JMenu menFicha;
    private javax.swing.JMenuItem menFicha_anamnesis;
    private javax.swing.JMenuItem menFicha_find;
    private javax.swing.JMenu menPacientes;
    private javax.swing.JMenuItem menPacientes_add;
    private javax.swing.JMenuItem menPacientes_admin;
    private javax.swing.JMenuItem menPacientes_find;
    private javax.swing.JMenuItem menPacientes_tree;
    private javax.swing.JMenu menPropietarios;
    private javax.swing.JMenuItem menPropietarios_add;
    private javax.swing.JMenuItem menPropietarios_admin;
    private javax.swing.JMenuItem menPropietarios_find;
    private javax.swing.JMenu menRep;
    private javax.swing.JMenuItem menuAdminComunas;
    private javax.swing.JMenuItem menuAdminProvincias;
    private javax.swing.JMenuItem menuAdminRegiones;
    private javax.swing.JPanel panelProximasAtenciones;
    private javax.swing.JPanel panelResumen;
    private javax.swing.JMenuItem syncmen_lock;
    private javax.swing.JMenuItem syncmen_logout;
    private javax.swing.JMenuItem syncmen_salir;
    private javax.swing.JMenuItem syncmen_server;
    private javax.swing.JTable tablaAtencionesProximas;
    // End of variables declaration//GEN-END:variables
}
