/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.fichamedica;

import cl.starlabs.controladores.AnamnesisJpaController;
import cl.starlabs.controladores.DetalleUsuariosJpaController;
import cl.starlabs.controladores.HospitalizacionJpaController;
import cl.starlabs.controladores.MascotaJpaController;
import cl.starlabs.controladores.PropietarioJpaController;
import cl.starlabs.controladores.VeterinarioJpaController;
import cl.starlabs.herramientas.HerramientasRapidas;
import cl.starlabs.herramientas.HerramientasRut;
import cl.starlabs.modelo.Anamnesis;
import cl.starlabs.modelo.DetalleUsuarios;
import cl.starlabs.modelo.Hospitalizacion;
import cl.starlabs.modelo.Mascota;
import cl.starlabs.modelo.Propietario;
import cl.starlabs.modelo.Usuarios;
import cl.starlabs.modelo.Veterinario;
import java.util.GregorianCalendar;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.UIManager;
import org.eclipse.persistence.config.PersistenceUnitProperties;

/**
 *
 * @author Janno
 */
public class VistaAnamnesis extends javax.swing.JFrame {

    Anamnesis ana = null;
    Mascota m = null;
    Veterinario v = null;
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SyncPetPU");
    PropietarioJpaController cp = new PropietarioJpaController(emf);
    MascotaJpaController cm = new MascotaJpaController(emf);
    HerramientasRapidas hr = new HerramientasRapidas();
    HerramientasRut ha = new HerramientasRut();

    public VistaAnamnesis() {
        initComponents();
        desactivarPaneles();
        dateChooserFecha.setMinSelectableDate(new GregorianCalendar().getTime());
        this.setLocationRelativeTo(null);
    }

    public VistaAnamnesis(Usuarios user) {
        initComponents();
        desactivarPaneles();
        dateChooserFecha.setMinSelectableDate(new GregorianCalendar().getTime());
        this.setLocationRelativeTo(null);
        //buscando veterinario
        DetalleUsuarios du = new DetalleUsuariosJpaController(emf).findDetalleUsuarios(user.getId());
        if (du != null) {
            if (du.getVeterinario() != null) {
                v = du.getVeterinario();
            } else {
                hr.mostrarError("El usuario no es veterinario");
                this.dispose();
            }
        } else {
            hr.mostrarError("No se pudo recuperar el detalle usuario");
            this.dispose();
        }
    }

    public void rellenarmascota() {
        cmbMascota.removeAllItems();
        String rutPropietario = hr.contenido(txtRutPropietario);
        Propietario p = cp.buscarPorRut(rutPropietario);
        if (p == null) {
            hr.mostrarError("No se pudo encontrar al propietario");
        } else {
            for (Mascota m : p.getMascotaList()) {
                hr.insertarTexto(cmbMascota, m.getIdMascota() + ": " + m.getNombre());
            }
        }
    }

    public void desactivarPaneles() {
        //hr.desactivar(panelAcciones);
        //hr.desactivar(panelAnalisis);
        hr.desactivar(btnGuardar);
        hr.desactivar(btnCancelar);
        hr.desactivar(btnAlergias);
        hr.desactivar(btnContraindicacion);
        hr.desactivar(btnDesparasitacion);
        hr.desactivar(btnExamenes);
        hr.desactivar(btnFarmacos);
        hr.desactivar(btnHospitalizacion);
        hr.desactivar(btnPatologia);
        hr.desactivar(btnProcedimiento);
        hr.desactivar(btnVacunacion);
        hr.desactivar(dateChooserFecha);
        hr.desactivar(txtPeso);
        hr.desactivar(txtTemperatura);
        hr.desactivar(textAreaActitud);
        hr.desactivar(textAreaAuscultasion);
        hr.desactivar(textAreaEstadoDeConciencia);
        hr.desactivar(textAreaEstadoDeHidratacion);
        hr.desactivar(textAreaEstadoDeNutricion);
        hr.desactivar(textAreaFrecuenciaCardiaca);
        hr.desactivar(textAreaFrecuenciaRespiratoria);
        hr.desactivar(textAreaInspeccionVisual);
        hr.desactivar(textAreaNivelDeMovilidad);
        hr.desactivar(textAreaOlfaccion);
        hr.desactivar(textAreaPalpacion);
        hr.desactivar(textAreaPercucion);
    }

    public void activarPaneles() {
        //hr.desactivar(panelAcciones);
        //hr.desactivar(panelAnalisis);
        hr.activar(btnGuardar);
        hr.activar(btnCancelar);
        hr.activar(btnAlergias);
        hr.activar(btnContraindicacion);
        hr.activar(btnDesparasitacion);
        hr.activar(btnExamenes);
        hr.activar(btnFarmacos);
        hr.activar(btnHospitalizacion);
        hr.activar(btnPatologia);
        hr.activar(btnProcedimiento);
        hr.activar(btnVacunacion);
        hr.activar(dateChooserFecha);
        hr.activar(txtPeso);
        hr.activar(txtTemperatura);
        hr.activar(textAreaActitud);
        hr.activar(textAreaAuscultasion);
        hr.activar(textAreaEstadoDeConciencia);
        hr.activar(textAreaEstadoDeHidratacion);
        hr.activar(textAreaEstadoDeNutricion);
        hr.activar(textAreaFrecuenciaCardiaca);
        hr.activar(textAreaFrecuenciaRespiratoria);
        hr.activar(textAreaInspeccionVisual);
        hr.activar(textAreaNivelDeMovilidad);
        hr.activar(textAreaOlfaccion);
        hr.activar(textAreaPalpacion);
        hr.activar(textAreaPercucion);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtRutPropietario = new javax.swing.JTextField();
        btnValidarRut = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cmbMascota = new javax.swing.JComboBox<>();
        panelAnalisis = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dateChooserFecha = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPeso = new javax.swing.JTextField();
        txtTemperatura = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaInspeccionVisual = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaPalpacion = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        textAreaPercucion = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        textAreaAuscultasion = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        textAreaOlfaccion = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        textAreaEstadoDeConciencia = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        textAreaNivelDeMovilidad = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        textAreaActitud = new javax.swing.JTextArea();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        textAreaEstadoDeNutricion = new javax.swing.JTextArea();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        textAreaEstadoDeHidratacion = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        textAreaFrecuenciaCardiaca = new javax.swing.JTextArea();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        textAreaFrecuenciaRespiratoria = new javax.swing.JTextArea();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        panelAcciones = new javax.swing.JPanel();
        btnExamenes = new javax.swing.JButton();
        btnPatologia = new javax.swing.JButton();
        btnContraindicacion = new javax.swing.JButton();
        btnProcedimiento = new javax.swing.JButton();
        btnAlergias = new javax.swing.JButton();
        btnVacunacion = new javax.swing.JButton();
        btnDesparasitacion = new javax.swing.JButton();
        btnFarmacos = new javax.swing.JButton();
        btnHospitalizacion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Anamnesis");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Basicos"));

        jLabel1.setText("Rut propietario");

        txtRutPropietario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtRutPropietarioFocusLost(evt);
            }
        });
        txtRutPropietario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutPropietarioKeyTyped(evt);
            }
        });

        btnValidarRut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        btnValidarRut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidarRutActionPerformed(evt);
            }
        });

        jLabel2.setText("Mascota");

        cmbMascota.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMascotaItemStateChanged(evt);
            }
        });
        cmbMascota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMascotaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtRutPropietario)
                    .addComponent(cmbMascota, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnValidarRut)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtRutPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnValidarRut))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbMascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        panelAnalisis.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Analisis"));

        jLabel3.setText("Fecha");

        jLabel4.setText("Peso");

        jLabel5.setText("Temperatura");

        txtPeso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPesoKeyTyped(evt);
            }
        });

        txtTemperatura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTemperaturaKeyTyped(evt);
            }
        });

        jLabel6.setText("Inspeccion Visual");

        textAreaInspeccionVisual.setColumns(20);
        textAreaInspeccionVisual.setRows(5);
        jScrollPane1.setViewportView(textAreaInspeccionVisual);

        jLabel7.setText("Palpacion");

        textAreaPalpacion.setColumns(20);
        textAreaPalpacion.setRows(5);
        jScrollPane2.setViewportView(textAreaPalpacion);

        textAreaPercucion.setColumns(20);
        textAreaPercucion.setRows(5);
        jScrollPane3.setViewportView(textAreaPercucion);

        jLabel8.setText("Percusion");

        textAreaAuscultasion.setColumns(20);
        textAreaAuscultasion.setRows(5);
        jScrollPane4.setViewportView(textAreaAuscultasion);

        jLabel9.setText("Auscultasion");

        textAreaOlfaccion.setColumns(20);
        textAreaOlfaccion.setRows(5);
        jScrollPane5.setViewportView(textAreaOlfaccion);

        jLabel10.setText("Olfaccion");

        textAreaEstadoDeConciencia.setColumns(20);
        textAreaEstadoDeConciencia.setRows(5);
        jScrollPane6.setViewportView(textAreaEstadoDeConciencia);

        jLabel11.setText("Estado de conciencia");

        textAreaNivelDeMovilidad.setColumns(20);
        textAreaNivelDeMovilidad.setRows(5);
        jScrollPane7.setViewportView(textAreaNivelDeMovilidad);

        jLabel12.setText("Nivel de movilidad");

        textAreaActitud.setColumns(20);
        textAreaActitud.setRows(5);
        jScrollPane8.setViewportView(textAreaActitud);

        jLabel13.setText("Actitud");

        textAreaEstadoDeNutricion.setColumns(20);
        textAreaEstadoDeNutricion.setRows(5);
        jScrollPane9.setViewportView(textAreaEstadoDeNutricion);

        jLabel14.setText("Estado de Nutricion");

        textAreaEstadoDeHidratacion.setColumns(20);
        textAreaEstadoDeHidratacion.setRows(5);
        jScrollPane10.setViewportView(textAreaEstadoDeHidratacion);

        jLabel15.setText("Estado de Hidratacion");

        textAreaFrecuenciaCardiaca.setColumns(20);
        textAreaFrecuenciaCardiaca.setRows(5);
        jScrollPane11.setViewportView(textAreaFrecuenciaCardiaca);

        jLabel16.setText("Frecuencia Cardiaca");

        textAreaFrecuenciaRespiratoria.setColumns(20);
        textAreaFrecuenciaRespiratoria.setRows(5);
        jScrollPane12.setViewportView(textAreaFrecuenciaRespiratoria);

        jLabel17.setText("Frecuencia Respiratoria");

        jLabel18.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N
        jLabel18.setText("Grados");

        javax.swing.GroupLayout panelAnalisisLayout = new javax.swing.GroupLayout(panelAnalisis);
        panelAnalisis.setLayout(panelAnalisisLayout);
        panelAnalisisLayout.setHorizontalGroup(
            panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnalisisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAnalisisLayout.createSequentialGroup()
                        .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelAnalisisLayout.createSequentialGroup()
                                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelAnalisisLayout.createSequentialGroup()
                                        .addGap(55, 55, 55)
                                        .addComponent(txtTemperatura, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAnalisisLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dateChooserFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18))
                    .addGroup(panelAnalisisLayout.createSequentialGroup()
                        .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(36, 36, 36)
                        .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1))
                        .addGap(18, 18, 18)
                        .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addGap(12, 12, 12)
                        .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelAnalisisLayout.createSequentialGroup()
                                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(17, 17, 17)
                                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane6)
                                    .addComponent(jScrollPane5)))
                            .addGroup(panelAnalisisLayout.createSequentialGroup()
                                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAnalisisLayout.setVerticalGroup(
            panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAnalisisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateChooserFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtPeso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTemperatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAnalisisLayout.createSequentialGroup()
                        .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 74, Short.MAX_VALUE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel15)
                            .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(panelAnalisisLayout.createSequentialGroup()
                        .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAnalisisLayout.createSequentialGroup()
                                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelAnalisisLayout.createSequentialGroup()
                                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAnalisisLayout.createSequentialGroup()
                                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel16)
                                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelAnalisisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel17)))
                            .addComponent(jLabel14)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelAcciones.setBorder(javax.swing.BorderFactory.createTitledBorder("Acciones"));

        btnExamenes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/page_new.gif"))); // NOI18N
        btnExamenes.setText("Examenes");
        btnExamenes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExamenesActionPerformed(evt);
            }
        });

        btnPatologia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/pencil_add.png"))); // NOI18N
        btnPatologia.setText("Patología");
        btnPatologia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPatologiaActionPerformed(evt);
            }
        });

        btnContraindicacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/pencil_add.png"))); // NOI18N
        btnContraindicacion.setText("Contraindicación");
        btnContraindicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContraindicacionActionPerformed(evt);
            }
        });

        btnProcedimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/pencil_add.png"))); // NOI18N
        btnProcedimiento.setText("Procedimiento");
        btnProcedimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProcedimientoActionPerformed(evt);
            }
        });

        btnAlergias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/pencil_add.png"))); // NOI18N
        btnAlergias.setText("Alergias");
        btnAlergias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlergiasActionPerformed(evt);
            }
        });

        btnVacunacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/pencil_add.png"))); // NOI18N
        btnVacunacion.setText("Vacunación");
        btnVacunacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVacunacionActionPerformed(evt);
            }
        });

        btnDesparasitacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/pencil_add.png"))); // NOI18N
        btnDesparasitacion.setText("Desparasitación");
        btnDesparasitacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesparasitacionActionPerformed(evt);
            }
        });

        btnFarmacos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/pill_add.png"))); // NOI18N
        btnFarmacos.setText("Farmacos");
        btnFarmacos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFarmacosActionPerformed(evt);
            }
        });

        btnHospitalizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/pencil_add.png"))); // NOI18N
        btnHospitalizacion.setText("Hospitalización");
        btnHospitalizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHospitalizacionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAccionesLayout = new javax.swing.GroupLayout(panelAcciones);
        panelAcciones.setLayout(panelAccionesLayout);
        panelAccionesLayout.setHorizontalGroup(
            panelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnExamenes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnProcedimiento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDesparasitacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPatologia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFarmacos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAlergias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnVacunacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnContraindicacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHospitalizacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAccionesLayout.setVerticalGroup(
            panelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAccionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExamenes)
                    .addComponent(btnPatologia)
                    .addComponent(btnContraindicacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnProcedimiento)
                    .addComponent(btnAlergias)
                    .addComponent(btnVacunacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDesparasitacion)
                    .addComponent(btnFarmacos)
                    .addComponent(btnHospitalizacion))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/disk.png"))); // NOI18N
        btnGuardar.setText("Guargar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelAnalisis, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelAcciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancelar))
                        .addComponent(panelAcciones, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelAnalisis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnValidarRutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidarRutActionPerformed
        if (!hr.contenido(txtRutPropietario).isEmpty()) {
            hr.insertarTexto(txtRutPropietario, ha.formatear(hr.contenido(txtRutPropietario)));
            if (ha.validar(hr.contenido(txtRutPropietario))) {
                rellenarmascota();
                hr.focus(cmbMascota);
                activarPaneles();

            } else {
                hr.mostrarError("Rut invalido");
                hr.focus(txtRutPropietario);
                hr.insertarTexto(txtRutPropietario, "");
            }
        }
    }//GEN-LAST:event_btnValidarRutActionPerformed

    private void txtRutPropietarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutPropietarioKeyTyped
        hr.ingresaCaracteresRut(evt);
        hr.largoMaximo(txtRutPropietario, 12, evt);
    }//GEN-LAST:event_txtRutPropietarioKeyTyped

    private void txtRutPropietarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRutPropietarioFocusLost
        if (!hr.contenido(txtRutPropietario).isEmpty()) {
            hr.insertarTexto(txtRutPropietario, ha.formatear(hr.contenido(txtRutPropietario)));
            if (ha.validar(hr.contenido(txtRutPropietario))) {
                btnValidarRutActionPerformed(null);
            } else {
                hr.mostrarError("Rut invalido");
                hr.focus(txtRutPropietario);
                hr.insertarTexto(txtRutPropietario, "");
            }
        }
    }//GEN-LAST:event_txtRutPropietarioFocusLost

    private void btnExamenesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExamenesActionPerformed
        Mascota m = cm.findMascota(Integer.parseInt(hr.contenido(cmbMascota).split(":")[0]));
        if (m != null) {
            new VistaExamenes(m, v).setVisible(true);
        } else {
            hr.mostrarError("No se encontro la mascota");
        }
    }//GEN-LAST:event_btnExamenesActionPerformed

    private void btnPatologiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPatologiaActionPerformed
        Mascota m = cm.findMascota(Integer.parseInt(hr.contenido(cmbMascota).split(":")[0]));
        if (m != null) {
            new VistaPatologias(m, v).setVisible(true);
        } else {
            hr.mostrarError("No se encontro la mascota");
        }
    }//GEN-LAST:event_btnPatologiaActionPerformed

    private void btnContraindicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContraindicacionActionPerformed
        Mascota m = cm.findMascota(Integer.parseInt(hr.contenido(cmbMascota).split(":")[0]));
        if (m != null) {
            new VistaContraindicaciones(m, v).setVisible(true);
        } else {
            hr.mostrarError("No se encontro la mascota");
        }
    }//GEN-LAST:event_btnContraindicacionActionPerformed

    private void btnProcedimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcedimientoActionPerformed
        Mascota m = cm.findMascota(Integer.parseInt(hr.contenido(cmbMascota).split(":")[0]));
        if (m != null) {
            new VistaProcedimientos(m, v).setVisible(true);
        } else {
            hr.mostrarError("No se encontro la mascota");
        }
    }//GEN-LAST:event_btnProcedimientoActionPerformed

    private void btnAlergiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlergiasActionPerformed
        Mascota m = cm.findMascota(Integer.parseInt(hr.contenido(cmbMascota).split(":")[0]));
        if (m != null) {
            new VistaAlergias(m, v).setVisible(true);
        } else {
            hr.mostrarError("No se encontro la mascota");
        }
    }//GEN-LAST:event_btnAlergiasActionPerformed

    private void btnVacunacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVacunacionActionPerformed
        Mascota m = cm.findMascota(Integer.parseInt(hr.contenido(cmbMascota).split(":")[0]));
        if (m != null) {
            new VistaVacunaciones(m, v).setVisible(true);
        } else {
            hr.mostrarError("No se encontro la mascota");
        }
    }//GEN-LAST:event_btnVacunacionActionPerformed

    private void btnDesparasitacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesparasitacionActionPerformed
        Mascota m = cm.findMascota(Integer.parseInt(hr.contenido(cmbMascota).split(":")[0]));
        if (m != null) {
            new VistaDesparacitaciones(m, v).setVisible(true);
        } else {
            hr.mostrarError("No se encontro la mascota");
        }
    }//GEN-LAST:event_btnDesparasitacionActionPerformed

    private void btnFarmacosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFarmacosActionPerformed
        Mascota m = cm.findMascota(Integer.parseInt(hr.contenido(cmbMascota).split(":")[0]));
        if (m != null) {
            new VistaFarmacos(m, v).setVisible(true);
        } else {
            hr.mostrarError("No se encontro la mascota");
        }
    }//GEN-LAST:event_btnFarmacosActionPerformed

    private void btnHospitalizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHospitalizacionActionPerformed
        Mascota m = cm.findMascota(Integer.parseInt(hr.contenido(cmbMascota).split(":")[0]));
        if (m != null) {
            new Hospitalizaciones(m).setVisible(true);
        } else {
            hr.mostrarError("No se encontro la mascota");
        }
    }//GEN-LAST:event_btnHospitalizacionActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
        System.gc();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        try {
            if (!hr.esVacio(txtPeso)) {
                if (!hr.esVacio(txtTemperatura)) {
                    if (!hr.esVacio(textAreaInspeccionVisual)) {
                        if (!hr.esVacio(textAreaAuscultasion)) {
                            if (!hr.esVacio(textAreaEstadoDeConciencia)) {
                                if (!hr.esVacio(textAreaNivelDeMovilidad)) {
                                    if (!hr.esVacio(textAreaEstadoDeHidratacion)) {
                                        //Recuperando Datos
                                        if (ana == null) {
                                            ana.setIdAnamnesis(new AnamnesisJpaController(emf).ultimo());
                                        }
                                        ana.setFechaAnamnesis(dateChooserFecha.getDate());
                                        ana.setPeso(Long.parseLong(txtPeso.getText()));
                                        ana.setTemperatura(Long.parseLong(txtTemperatura.getText()));
                                        ana.setInspeccionVisual(textAreaInspeccionVisual.getText());
                                        ana.setPalpacion(textAreaPalpacion.getText());
                                        ana.setPercusion(textAreaPercucion.getText());
                                        ana.setAuscultacion(textAreaAuscultasion.getText());
                                        ana.setOlfaccion(textAreaOlfaccion.getText());
                                        ana.setEstadoConciencia(textAreaEstadoDeConciencia.getText());
                                        ana.setNivelMovilidad(textAreaNivelDeMovilidad.getText());
                                        ana.setActitud(textAreaActitud.getText());
                                        ana.setEstadoNutricion(textAreaEstadoDeNutricion.getText());
                                        ana.setGradoHidratacion(textAreaEstadoDeHidratacion.getText());
                                        ana.setFrecuenciaCardiaca(textAreaFrecuenciaCardiaca.getText());
                                        ana.setFrecuenciaRespiratoria(textAreaFrecuenciaRespiratoria.getText());
                                        m = new MascotaJpaController(emf).findMascota(Integer.parseInt(hr.contenido(cmbMascota).split(":")[0]));
                                        ana.setMascota(m);
                                        ana.setVeterinario(v);
                                        ana.setHospitalizacion(new HospitalizacionJpaController(emf).findHospitalizacion(1));

                                        if (ana == null) {
                                            new AnamnesisJpaController(emf).create(ana);
                                        } else {
                                            new AnamnesisJpaController(emf).edit(ana);
                                        }
                                        hr.mostrarMensaje("Anamnesis registrado exitosamente");
                                        btnCancelarActionPerformed(evt);

                                    } else {
                                        hr.mostrarMensaje("Debe Rellenar campo de Frecuencia Respiratoria");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            hr.mostrarError("Se produjo un error " + e.getMessage());
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtPesoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesoKeyTyped
        hr.ingresaSoloNumeros(evt);
        hr.largoMaximo(txtPeso, 4, evt);
    }//GEN-LAST:event_txtPesoKeyTyped

    private void txtTemperaturaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTemperaturaKeyTyped
        hr.ingresaSoloNumeros(evt);
        hr.largoMaximo(txtTemperatura, 2, evt);
    }//GEN-LAST:event_txtTemperaturaKeyTyped

    private void cmbMascotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMascotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMascotaActionPerformed

    private void cmbMascotaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMascotaItemStateChanged
        if (cmbMascota.getItemCount() != 0) {
            m = new MascotaJpaController(emf).findMascota(Integer.parseInt(hr.contenido(cmbMascota).split(":")[0]));
            ana = new AnamnesisJpaController(emf).buscarPorMascota(m);

            if (ana != null) {
                dateChooserFecha.setDate(ana.getFechaAnamnesis());
                txtPeso.setText(ana.getPeso() + "");
                txtTemperatura.setText(ana.getTemperatura() + "");
                textAreaActitud.setText(ana.getActitud());
                textAreaAuscultasion.setText(ana.getAuscultacion());
                textAreaEstadoDeConciencia.setText(ana.getEstadoConciencia());
                textAreaEstadoDeHidratacion.setText(ana.getGradoHidratacion());
                textAreaEstadoDeNutricion.setText(ana.getEstadoNutricion());
                textAreaInspeccionVisual.setText(ana.getInspeccionVisual());
                textAreaNivelDeMovilidad.setText(ana.getNivelMovilidad());
                textAreaOlfaccion.setText(ana.getOlfaccion());
                textAreaPalpacion.setText(ana.getPalpacion());
                textAreaPercucion.setText(ana.getPercusion());
                textAreaFrecuenciaCardiaca.setText(ana.getFrecuenciaCardiaca());
                textAreaFrecuenciaRespiratoria.setText(ana.getFrecuenciaRespiratoria());
            }
        }
    }//GEN-LAST:event_cmbMascotaItemStateChanged

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
            java.util.logging.Logger.getLogger(VistaAnamnesis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaAnamnesis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaAnamnesis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaAnamnesis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaAnamnesis().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlergias;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnContraindicacion;
    private javax.swing.JButton btnDesparasitacion;
    private javax.swing.JButton btnExamenes;
    private javax.swing.JButton btnFarmacos;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnHospitalizacion;
    private javax.swing.JButton btnPatologia;
    private javax.swing.JButton btnProcedimiento;
    private javax.swing.JButton btnVacunacion;
    private javax.swing.JButton btnValidarRut;
    private javax.swing.JComboBox<String> cmbMascota;
    private com.toedter.calendar.JDateChooser dateChooserFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel panelAcciones;
    private javax.swing.JPanel panelAnalisis;
    private javax.swing.JTextArea textAreaActitud;
    private javax.swing.JTextArea textAreaAuscultasion;
    private javax.swing.JTextArea textAreaEstadoDeConciencia;
    private javax.swing.JTextArea textAreaEstadoDeHidratacion;
    private javax.swing.JTextArea textAreaEstadoDeNutricion;
    private javax.swing.JTextArea textAreaFrecuenciaCardiaca;
    private javax.swing.JTextArea textAreaFrecuenciaRespiratoria;
    private javax.swing.JTextArea textAreaInspeccionVisual;
    private javax.swing.JTextArea textAreaNivelDeMovilidad;
    private javax.swing.JTextArea textAreaOlfaccion;
    private javax.swing.JTextArea textAreaPalpacion;
    private javax.swing.JTextArea textAreaPercucion;
    private javax.swing.JTextField txtPeso;
    private javax.swing.JTextField txtRutPropietario;
    private javax.swing.JTextField txtTemperatura;
    // End of variables declaration//GEN-END:variables
}
