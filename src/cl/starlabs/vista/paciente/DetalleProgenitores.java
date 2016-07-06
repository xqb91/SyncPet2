/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.paciente;

import cl.starlabs.controladores.MascotaJpaController;
import cl.starlabs.modelo.Mascota;
import javax.persistence.EntityManagerFactory;
import cl.starlabs.herramientas.HerramientasRapidas;
import javax.persistence.Persistence;
/**
 *
 * @author cetecom
 */
public class DetalleProgenitores extends javax.swing.JFrame {

    //variables que almacenarán datos
    Mascota tp = null;
    Mascota ma = null;
    Mascota pa = null;
    
    //controladores
    MascotaJpaController jpa;
    //externos
    EntityManagerFactory emf;
    HerramientasRapidas hr;
    
    
    public DetalleProgenitores() {
        initComponents();
        this.setLocationRelativeTo(null);
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        jpa = new MascotaJpaController(emf);
        hr = new HerramientasRapidas();
        rellenarDatos();
    }
    
    public DetalleProgenitores(Mascota tp) {
        initComponents();
        this.setLocationRelativeTo(null);
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        jpa = new MascotaJpaController(emf);
        this.tp = tp;
        hr = new HerramientasRapidas();
        rellenarDatos();
    }
    
    public DetalleProgenitores(Mascota tp, Mascota ma) {
        initComponents();
        this.setLocationRelativeTo(null);
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        jpa = new MascotaJpaController(emf);
        this.tp = tp;
        this.ma = ma;
        hr = new HerramientasRapidas();
        rellenarDatos();
    }

    public DetalleProgenitores(Mascota tp, Mascota ma, Mascota pa) {
        initComponents();
        this.setLocationRelativeTo(null);
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        jpa = new MascotaJpaController(emf);
        this.tp = tp;
        this.ma = ma;
        this.pa = pa;
        hr = new HerramientasRapidas();
        rellenarDatos();
    }

    public void rellenarDatos() {
        if(tp != null) {
            hr.insertarTexto(lblNombrePaciente, tp.getNombre());
            hr.insertarTexto(lblIdentificadorPaciente, tp.getIdMascota()+"");
            hr.insertarTexto(lblPropietarioPaciente, tp.getPropietario().getNombres()+" "+tp.getPropietario().getApaterno()+" "+tp.getPropietario().getAmaterno());
            if(tp.getMadre() != null) {
                hr.insertarTexto(lblNombreMadre, tp.getMadre().getNombre());
                hr.insertarTexto(lblIdentificadorMadre, tp.getMadre().getIdMascota()+"");
                hr.insertarTexto(lblPropietarioMadre, tp.getMadre().getPropietario().getNombres()+" "+tp.getMadre().getPropietario().getApaterno()+" "+tp.getMadre().getPropietario().getAmaterno());
            }
            if(tp.getPadre() != null) {
                hr.insertarTexto(lblNombrePadre, tp.getPadre().getNombre());
                hr.insertarTexto(lblIdentificadorPadre, tp.getPadre().getIdMascota()+"");
                hr.insertarTexto(lblPropietarioPadre, tp.getPadre().getPropietario().getNombres()+" "+tp.getPadre().getPropietario().getApaterno()+" "+tp.getPadre().getPropietario().getAmaterno());
            }
        }
        if(ma != null) {
            if(tp.getMadre() == null){
                if(tp.getIdMascota() == ma.getIdMascota()) {
                    hr.mostrarError("Ha seleccionado el mismo paciente como progenitora");
                    ma = null;
                }else{
                    if(ma.getSexo() != 'H' && ma.getSexo() != '+') {
                        hr.mostrarError("El paciente seleccionado como progenitora no es Hembra o Hermafrodita");
                        ma = null;
                    }else{
                        hr.insertarTexto(lblNombreMadre, ma.getNombre());
                        hr.insertarTexto(lblIdentificadorMadre, ma.getIdMascota()+"");
                        hr.insertarTexto(lblPropietarioMadre, ma.getPropietario().getNombres()+" "+ma.getPropietario().getApaterno()+" "+ma.getPropietario().getAmaterno());
                    }
                }
            }else{
                if(tp.getMadre().getIdMascota() == ma.getIdMascota())  {
                    hr.mostrarError("Ha seleccionado el mismo paciente como progenitora");
                    ma = null;
                }else if(tp.getMadre() != null) {
                    hr.insertarTexto(lblNombreMadre, tp.getMadre().getNombre());
                    hr.insertarTexto(lblIdentificadorMadre, tp.getMadre().getIdMascota()+"");
                    hr.insertarTexto(lblPropietarioMadre, tp.getPropietario().getNombres()+" "+tp.getPropietario().getApaterno()+" "+tp.getPropietario().getAmaterno());
                }else{
                     if(ma.getSexo() != 'H' && ma.getSexo() != '+') {
                        hr.mostrarError("El paciente seleccionado como progenitora no es Hembra o Hermafrodita");
                        ma = null;
                    }else{
                        hr.insertarTexto(lblNombreMadre, ma.getNombre());
                        hr.insertarTexto(lblIdentificadorMadre, ma.getIdMascota()+"");
                        hr.insertarTexto(lblPropietarioMadre, ma.getPropietario().getNombres()+" "+ma.getPropietario().getApaterno()+" "+ma.getPropietario().getAmaterno());
                    }                   
                }
            }
        }else if(tp != null){
            if(tp.getMadre() == null) {
                hr.insertarTexto(lblNombreMadre, "Indefinido");
                hr.insertarTexto(lblIdentificadorMadre, "Indefinido");
                hr.insertarTexto(lblPropietarioMadre, "Indefinido");
            }
        }
        if(pa != null) {
            if(tp.getPadre()== null){
                if(tp.getIdMascota() == pa.getIdMascota()) {
                    hr.mostrarError("Ha seleccionado el mismo paciente como progenitor");
                    pa = null;
                }else{
                    if(pa.getSexo() != 'M' && pa.getSexo() != '+') {
                        hr.mostrarError("El paciente seleccionado como progenitor no es Macho o Hermafrodita");
                        pa = null;
                    }else{
                        hr.insertarTexto(lblNombrePadre, pa.getNombre());
                        hr.insertarTexto(lblIdentificadorPadre, pa.getIdMascota()+"");
                        hr.insertarTexto(lblPropietarioPadre, pa.getPropietario().getNombres()+" "+pa.getPropietario().getApaterno()+" "+pa.getPropietario().getAmaterno());
                    }
                }
            }else{
                if(tp.getPadre().getIdMascota() == pa.getIdMascota())  {
                    hr.mostrarError("Ha seleccionado el mismo paciente como progenitor");
                    pa = null;
                }else if(tp.getPadre() != null) {    
                    hr.insertarTexto(lblNombrePadre, tp.getPadre().getNombre());
                    hr.insertarTexto(lblIdentificadorPadre, tp.getPadre().getIdMascota()+"");
                    hr.insertarTexto(lblPropietarioPadre, tp.getPropietario().getNombres()+" "+tp.getPropietario().getApaterno()+" "+tp.getPropietario().getAmaterno());
                }else{
                     if(pa.getSexo() != 'M' && pa.getSexo() != '+') {
                        hr.mostrarError("El paciente seleccionado como progenitor no es Macho o Hermafrodita");
                        pa = null;
                    }else{
                        hr.insertarTexto(lblNombrePadre, pa.getNombre());
                        hr.insertarTexto(lblIdentificadorPadre, pa.getIdMascota()+"");
                        hr.insertarTexto(lblPropietarioPadre, pa.getPropietario().getNombres()+" "+pa.getPropietario().getApaterno()+" "+pa.getPropietario().getAmaterno());
                    }                   
                }
            }
        }else if(tp != null){
            if(tp.getPadre() == null) {
                hr.insertarTexto(lblNombrePadre, "Indefinido");
                hr.insertarTexto(lblIdentificadorPadre, "Indefinido");
                hr.insertarTexto(lblPropietarioPadre, "Indefinido");
            }
        }
    }
    
    public Mascota getPaciente() {
        return tp;
    }

    public void setPaciente(Mascota tp) {
        this.tp = tp;
        rellenarDatos();
    }

    public Mascota getMadre() {
        return ma;
    }

    public void setMadre(Mascota ma) {
        this.ma = ma;
        rellenarDatos();
    }

    public Mascota getPadre() {
        return pa;
    }

    public void setPadre(Mascota pa) {
        this.pa = pa;
        rellenarDatos();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnBuscarPaciente = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblNombrePaciente = new javax.swing.JLabel();
        lblIdentificadorPaciente = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblPropietarioPaciente = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnBuscarMadre = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblNombreMadre = new javax.swing.JLabel();
        lblIdentificadorMadre = new javax.swing.JLabel();
        lblPropietarioMadre = new javax.swing.JLabel();
        btnEliminarMadre = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnBuscarPadre = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblNombrePadre = new javax.swing.JLabel();
        lblIdentificadorPadre = new javax.swing.JLabel();
        lblPropietarioPadre = new javax.swing.JLabel();
        btnEliminarPadre = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Detallar Progenitores");
        setResizable(false);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Paciente"));

        btnBuscarPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        btnBuscarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPacienteActionPerformed(evt);
            }
        });

        jLabel1.setText("Nombre");

        jLabel2.setText("Identificador");

        lblNombrePaciente.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblNombrePaciente.setText("Nombre Mascota");

        lblIdentificadorPaciente.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblIdentificadorPaciente.setText("Identificador");

        jLabel5.setText("Propietario");

        lblPropietarioPaciente.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblPropietarioPaciente.setText("Propietario");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPropietarioPaciente)
                    .addComponent(lblIdentificadorPaciente)
                    .addComponent(lblNombrePaciente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBuscarPaciente)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(btnBuscarPaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblNombrePaciente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblIdentificadorPaciente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblPropietarioPaciente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Madre"));

        btnBuscarMadre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        btnBuscarMadre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarMadreActionPerformed(evt);
            }
        });

        jLabel7.setText("Nombre");

        jLabel8.setText("Identificador");

        jLabel9.setText("Propietario");

        lblNombreMadre.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblNombreMadre.setText("Nombre Mascota");

        lblIdentificadorMadre.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblIdentificadorMadre.setText("Identificador");

        lblPropietarioMadre.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblPropietarioMadre.setText("Propietario");

        btnEliminarMadre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/chart_organisation_delete.png"))); // NOI18N
        btnEliminarMadre.setToolTipText("Desvincular Madre");
        btnEliminarMadre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarMadreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPropietarioMadre)
                    .addComponent(lblIdentificadorMadre)
                    .addComponent(lblNombreMadre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarMadre, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnEliminarMadre, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(lblNombreMadre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lblIdentificadorMadre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(lblPropietarioMadre)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnBuscarMadre, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminarMadre)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Padre"));

        btnBuscarPadre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        btnBuscarPadre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPadreActionPerformed(evt);
            }
        });

        jLabel13.setText("Nombre");

        jLabel14.setText("Identificador");

        jLabel15.setText("Propietario");

        lblNombrePadre.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblNombrePadre.setText("Nombre Mascota");

        lblIdentificadorPadre.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblIdentificadorPadre.setText("Identificador");

        lblPropietarioPadre.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblPropietarioPadre.setText("Propietario");

        btnEliminarPadre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/chart_organisation_delete.png"))); // NOI18N
        btnEliminarPadre.setToolTipText("Desvincular Padre");
        btnEliminarPadre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPadreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombrePadre)
                    .addComponent(lblPropietarioPadre)
                    .addComponent(lblIdentificadorPadre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscarPadre)
                    .addComponent(btnEliminarPadre, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(lblNombrePadre))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(lblIdentificadorPadre)))
                    .addComponent(btnBuscarPadre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(lblPropietarioPadre))
                    .addComponent(btnEliminarPadre))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/application_go.png"))); // NOI18N
        btnConfirmar.setText("Confirmar Cambios");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                        .addComponent(btnConfirmar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConfirmar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarMadreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarMadreActionPerformed
        new BuscarPaciente(this, "madre").setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBuscarMadreActionPerformed

    private void btnBuscarPadreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPadreActionPerformed
        new BuscarPaciente(this, "padre").setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBuscarPadreActionPerformed

    private void btnBuscarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPacienteActionPerformed
        new BuscarPaciente(this, "paciente").setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBuscarPacienteActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        rellenarDatos();
    }//GEN-LAST:event_formWindowStateChanged

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        rellenarDatos();
    }//GEN-LAST:event_formWindowGainedFocus

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        if(tp != null) {
            try {
                Mascota aux = jpa.findMascota(tp.getIdMascota());
                aux.setMadre(ma);
                aux.setPadre(pa);
                jpa.edit(aux);
                hr.mostrarMensaje("Progenitores actualizados");
                this.dispose();
            } catch (Exception e) {
                hr.mostrarError("Ocurrio un problema mientras se actualizaban los progenitores: "+e.getMessage());
            }
        }else{
            hr.mostrarError("No hay un paciente para asignar progenitores");
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnEliminarMadreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarMadreActionPerformed
        if(ma!= null) {
            ma = null;
            rellenarDatos();
        }else{
            if(tp.getMadre() != null) {
                tp.setMadre(null);
                rellenarDatos();
            }
        }
    }//GEN-LAST:event_btnEliminarMadreActionPerformed

    private void btnEliminarPadreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPadreActionPerformed
        if(pa!= null) {
            pa = null;
            rellenarDatos();
        }else{
            if(tp.getPadre()!= null) {
                tp.setPadre(null);
                rellenarDatos();
            }
        }
    }//GEN-LAST:event_btnEliminarPadreActionPerformed

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
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DetalleProgenitores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetalleProgenitores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetalleProgenitores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetalleProgenitores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DetalleProgenitores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarMadre;
    private javax.swing.JButton btnBuscarPaciente;
    private javax.swing.JButton btnBuscarPadre;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnEliminarMadre;
    private javax.swing.JButton btnEliminarPadre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblIdentificadorMadre;
    private javax.swing.JLabel lblIdentificadorPaciente;
    private javax.swing.JLabel lblIdentificadorPadre;
    private javax.swing.JLabel lblNombreMadre;
    private javax.swing.JLabel lblNombrePaciente;
    private javax.swing.JLabel lblNombrePadre;
    private javax.swing.JLabel lblPropietarioMadre;
    private javax.swing.JLabel lblPropietarioPaciente;
    private javax.swing.JLabel lblPropietarioPadre;
    // End of variables declaration//GEN-END:variables
}
