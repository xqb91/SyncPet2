/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.paciente;

import cl.starlabs.controladores.MascotaJpaController;
import cl.starlabs.modelo.Mascota;
import javax.swing.UIManager;
import cl.starlabs.herramientas.*;
import cl.starlabs.vista.propietario.DetallesPropietario;
import java.util.GregorianCalendar;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

public class DetallePaciente extends javax.swing.JFrame {

    Mascota m;
    //ventanas que ocupan la aplicación
    BuscarPaciente buscar = null;
    ListarPacientes listar = null;
    //declarando herramientas
    HerramientasRapidas hr = new HerramientasRapidas();
    
    public DetallePaciente() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    
    public DetallePaciente(Mascota m, BuscarPaciente buscar) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.m = m;
        this.buscar = buscar;
        //rellenando informacion
        hr.insertarTexto(lblNombrePaciente, m.getNombre());
        int dias;
        if(m.getDefuncion() == null) {
            dias = hr.fechasdiferenciaendias(m.getFechaNacimiento(), new GregorianCalendar().getTime());
        }else{
            dias = hr.fechasdiferenciaendias(m.getFechaNacimiento(), m.getDefuncion());
            btnDeclararDefuncion.setEnabled(false);
        }
        hr.insertarTexto(lblEdadPaciente, Math.abs((dias/365))+" años "+Math.abs((dias/365/12))+" meses "+((dias/365/12/24))+" días");
        if(m.getSexo() == 'H') {
            hr.insertarTexto(lblSexoPaciente, "Hembra");
        }else if(m.getSexo() == 'M') {
            hr.insertarTexto(lblSexoPaciente, "Macho");
        }else{
            hr.insertarTexto(lblSexoPaciente, "Hermafrodita");
        }
        hr.insertarTexto(lblRazaPaciente, m.getRaza().getNombre());
        hr.insertarTexto(lblIdentificador, m.getIdMascota()+"");
        hr.insertarTexto(lblNumeroChip, m.getNumeroChip()+"");
        hr.insertarTexto(lblGrupoSanguineo, m.getGrupoSanguineo());
        
        //info propietario
        hr.insertarTexto(lblNombrePropietario, m.getPropietario().getNombres());
        hr.insertarTexto(lblApellidosPropietario, m.getPropietario().getApaterno()+" "+m.getPropietario().getAmaterno());
        hr.insertarTexto(lblRunPropietario, m.getPropietario().getRut()+"-"+m.getPropietario().getDv());
        
        //info progenitores
        if(m.getMadre() != null) {
            hr.insertarTexto(lblInfoMadrePaciente, "Nombre: "+m.getMadre().getNombre()+" Identificador: "+m.getMadre().getIdMascota()+" Propietario: "+m.getMadre().getPropietario().getNombres()+" "+m.getMadre().getPropietario().getApaterno()+" "+m.getMadre().getPropietario().getAmaterno());
        }
        
        if(m.getPadre()!= null) {
            hr.insertarTexto(lblInfoPadrePaciente, "Nombre: "+m.getPadre().getNombre()+" Identificador: "+m.getPadre().getIdMascota()+" Propietario: "+m.getPadre().getPropietario().getNombres()+" "+m.getPadre().getPropietario().getApaterno()+" "+m.getPadre().getPropietario().getAmaterno());
        }
    }

    public DetallePaciente(Mascota m) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.m = m;
        this.listar = listar;
        //rellenando informacion
        hr.insertarTexto(lblNombrePaciente, m.getNombre());
        int dias;
        if(m.getDefuncion() == null) {
            dias = hr.fechasdiferenciaendias(m.getFechaNacimiento(), new GregorianCalendar().getTime());
        }else{
            dias = hr.fechasdiferenciaendias(m.getFechaNacimiento(), m.getDefuncion());
            btnDeclararDefuncion.setEnabled(false);
        }
        hr.insertarTexto(lblEdadPaciente, Math.abs((dias/365))+" años "+Math.abs((dias/365/12))+" meses "+((dias/365/12/24))+" días");
        if(m.getSexo() == 'H') {
            hr.insertarTexto(lblSexoPaciente, "Hembra");
        }else if(m.getSexo() == 'M') {
            hr.insertarTexto(lblSexoPaciente, "Macho");
        }else{
            hr.insertarTexto(lblSexoPaciente, "Hermafrodita");
        }
        hr.insertarTexto(lblRazaPaciente, m.getRaza().getNombre());
        hr.insertarTexto(lblIdentificador, m.getIdMascota()+"");
        hr.insertarTexto(lblNumeroChip, m.getNumeroChip()+"");
        hr.insertarTexto(lblGrupoSanguineo, m.getGrupoSanguineo());
        
        //info propietario
        hr.insertarTexto(lblNombrePropietario, m.getPropietario().getNombres());
        hr.insertarTexto(lblApellidosPropietario, m.getPropietario().getApaterno()+" "+m.getPropietario().getAmaterno());
        hr.insertarTexto(lblRunPropietario, m.getPropietario().getRut()+"-"+m.getPropietario().getDv());
        
        //info progenitores
        if(m.getMadre() != null) {
            hr.insertarTexto(lblInfoMadrePaciente, "Nombre: "+m.getMadre().getNombre()+" Identificador: "+m.getMadre().getIdMascota()+" Propietario: "+m.getMadre().getPropietario().getNombres()+" "+m.getMadre().getPropietario().getApaterno()+" "+m.getMadre().getPropietario().getAmaterno());
        }
        
        if(m.getPadre()!= null) {
            hr.insertarTexto(lblInfoPadrePaciente, "Nombre: "+m.getPadre().getNombre()+" Identificador: "+m.getPadre().getIdMascota()+" Propietario: "+m.getPadre().getPropietario().getNombres()+" "+m.getPadre().getPropietario().getApaterno()+" "+m.getPadre().getPropietario().getAmaterno());
        }
    }

    
    public DetallePaciente(Mascota m, ListarPacientes listar) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.m = m;
        this.listar = listar;
        //rellenando informacion
        hr.insertarTexto(lblNombrePaciente, m.getNombre());
        int dias;
        if(m.getDefuncion() != null) {
            dias = hr.fechasdiferenciaendias(m.getFechaNacimiento(), new GregorianCalendar().getTime());
        }else{
            dias = hr.fechasdiferenciaendias(m.getFechaNacimiento(), m.getDefuncion());
            btnDeclararDefuncion.setEnabled(false);
        }
        hr.insertarTexto(lblEdadPaciente, Math.abs((dias/365))+" años "+Math.abs((dias/365/12))+" meses "+((dias/365/12/24))+" días");
        if(m.getSexo() == 'H') {
            hr.insertarTexto(lblSexoPaciente, "Hembra");
        }else if(m.getSexo() == 'M') {
            hr.insertarTexto(lblSexoPaciente, "Macho");
        }else{
            hr.insertarTexto(lblSexoPaciente, "Hermafrodita");
        }
        hr.insertarTexto(lblRazaPaciente, m.getRaza().getNombre());
        hr.insertarTexto(lblIdentificador, m.getIdMascota()+"");
        hr.insertarTexto(lblNumeroChip, m.getNumeroChip()+"");
        hr.insertarTexto(lblGrupoSanguineo, m.getGrupoSanguineo());
        
        //info propietario
        hr.insertarTexto(lblNombrePropietario, m.getPropietario().getNombres());
        hr.insertarTexto(lblApellidosPropietario, m.getPropietario().getApaterno()+" "+m.getPropietario().getAmaterno());
        hr.insertarTexto(lblRunPropietario, m.getPropietario().getRut()+"-"+m.getPropietario().getDv());
        
        //info progenitores
        if(m.getMadre() != null) {
            hr.insertarTexto(lblInfoMadrePaciente, "Nombre: "+m.getMadre().getNombre()+" Identificador: "+m.getMadre().getIdMascota()+" Propietario: "+m.getMadre().getPropietario().getNombres()+" "+m.getMadre().getPropietario().getApaterno()+" "+m.getMadre().getPropietario().getAmaterno());
        }
        
        if(m.getPadre()!= null) {
            hr.insertarTexto(lblInfoPadrePaciente, "Nombre: "+m.getPadre().getNombre()+" Identificador: "+m.getPadre().getIdMascota()+" Propietario: "+m.getPadre().getPropietario().getNombres()+" "+m.getPadre().getPropietario().getApaterno()+" "+m.getPadre().getPropietario().getAmaterno());
        }
    }

    public void actualizarDatos() {
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblImagenPaciente = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblNombrePaciente = new javax.swing.JLabel();
        lblEdadPaciente = new javax.swing.JLabel();
        lblSexoPaciente = new javax.swing.JLabel();
        lblRazaPaciente = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblNumeroChip = new javax.swing.JLabel();
        lblGrupoSanguineo = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblIdentificador = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblImagenPropietario = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblNombrePropietario = new javax.swing.JLabel();
        lblRunPropietario = new javax.swing.JLabel();
        lblApellidosPropietario = new javax.swing.JLabel();
        btnVerPropietario = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblImagenMadrePaciente = new javax.swing.JLabel();
        lblImagenPadrePaciente = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblInfoMadrePaciente = new javax.swing.JLabel();
        lblInfoPadrePaciente = new javax.swing.JLabel();
        btnEditarPaciente = new javax.swing.JButton();
        btnDeclararDefuncion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnEliminarPaciente = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SyncPet :: Detalle de Paciente");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Paciente"));

        lblImagenPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/sistema/mascota_desconocida.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Nombre de Paciente");

        lblNombrePaciente.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblNombrePaciente.setText("Nombre completo del paciente");

        lblEdadPaciente.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblEdadPaciente.setText("0 Años 0 Meses 0 Días");

        lblSexoPaciente.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblSexoPaciente.setText("Macho/Hembra");

        lblRazaPaciente.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblRazaPaciente.setText("Raza");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/icon_airmail.gif"))); // NOI18N
        jLabel7.setText("Número de Chip");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/heart.png"))); // NOI18N
        jLabel8.setText("Grupo Sanguíneo");

        lblNumeroChip.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblNumeroChip.setText("123456789012345");

        lblGrupoSanguineo.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblGrupoSanguineo.setText("Grupo Sangúineo");

        jLabel12.setText("Identificador:");

        lblIdentificador.setText("0123456789012345");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagenPaciente)
                    .addComponent(jLabel12)
                    .addComponent(lblIdentificador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(20, 20, 20)
                        .addComponent(lblGrupoSanguineo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(28, 28, 28)
                        .addComponent(lblNumeroChip))
                    .addComponent(lblNombrePaciente)
                    .addComponent(jLabel2)
                    .addComponent(lblEdadPaciente)
                    .addComponent(lblSexoPaciente)
                    .addComponent(lblRazaPaciente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNombrePaciente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEdadPaciente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSexoPaciente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRazaPaciente))
                    .addComponent(lblImagenPaciente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblNumeroChip)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblGrupoSanguineo)
                    .addComponent(lblIdentificador, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Propietario"));

        lblImagenPropietario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/sistema/usuario_desconocido.jpg"))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel13.setText("Nombre Completo");

        lblNombrePropietario.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblNombrePropietario.setText("Nombres");

        lblRunPropietario.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblRunPropietario.setText("Run");

        lblApellidosPropietario.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        lblApellidosPropietario.setText("Apellidos");

        btnVerPropietario.setText("Ver Propietario");
        btnVerPropietario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerPropietarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImagenPropietario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRunPropietario)
                    .addComponent(jLabel13)
                    .addComponent(lblNombrePropietario)
                    .addComponent(lblApellidosPropietario))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVerPropietario)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNombrePropietario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblApellidosPropietario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRunPropietario))
                    .addComponent(lblImagenPropietario))
                .addGap(7, 7, 7)
                .addComponent(btnVerPropietario))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Progenitores del Paciente"));

        lblImagenMadrePaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/sistema/mascota_desconocida.png"))); // NOI18N

        lblImagenPadrePaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/sistema/mascota_desconocida.png"))); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("Madre del Paciente");

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel22.setText("Padre del Paciente");

        lblInfoMadrePaciente.setText("Sin información");

        lblInfoPadrePaciente.setText("Sin información");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblImagenMadrePaciente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(lblInfoMadrePaciente)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblImagenPadrePaciente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(lblInfoPadrePaciente))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagenMadrePaciente)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblInfoMadrePaciente)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagenPadrePaciente)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblInfoPadrePaciente)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnEditarPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/pencil.png"))); // NOI18N
        btnEditarPaciente.setText("Editar Paciente");
        btnEditarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarPacienteActionPerformed(evt);
            }
        });

        btnDeclararDefuncion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/emoticon_unhappy.png"))); // NOI18N
        btnDeclararDefuncion.setText("Declarar Defunción");
        btnDeclararDefuncion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeclararDefuncionActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/cancel.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnEliminarPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/delete.png"))); // NOI18N
        btnEliminarPaciente.setText("Eliminar");
        btnEliminarPaciente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPacienteActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnEditarPaciente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeclararDefuncion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEliminarPaciente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)
                        .addGap(0, 24, Short.MAX_VALUE))))
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
                    .addComponent(btnEditarPaciente)
                    .addComponent(btnDeclararDefuncion)
                    .addComponent(btnCancelar)
                    .addComponent(btnEliminarPaciente))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerPropietarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerPropietarioActionPerformed
        new DetallesPropietario(m.getPropietario()).setVisible(true);
    }//GEN-LAST:event_btnVerPropietarioActionPerformed

    private void btnEditarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarPacienteActionPerformed
        new RegistroPaciente(null, m, m.getPropietario(), this).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnEditarPacienteActionPerformed

    private void btnDeclararDefuncionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeclararDefuncionActionPerformed
        try {
            m.setDefuncion(new GregorianCalendar().getTime());
            new MascotaJpaController(Persistence.createEntityManagerFactory("SyncPetPU")).edit(m);
            hr.mostrarMensaje("Se registró la defunción del paciente");
            btnDeclararDefuncion.setEnabled(false);
        } catch (Exception e) {
            hr.mostrarError("Ocurrió un error al registrar la defunción: "+e.getMessage());
            btnDeclararDefuncion.setEnabled(true);
        }
    }//GEN-LAST:event_btnDeclararDefuncionActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        if(buscar != null) {
            buscar.setVisible(true);
            buscar.actualizarTabla();
            this.dispose();
        }else if(listar != null) {
            listar.setVisible(true);
            this.dispose();
        }else{
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEliminarPacienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPacienteActionPerformed
        int opcion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar este paciente?", "Error", JOptionPane.OK_CANCEL_OPTION);
        if(opcion == 0) {
            try {
                m.setDefuncion(new GregorianCalendar().getTime());
                new MascotaJpaController(Persistence.createEntityManagerFactory("SyncPetPU")).destroy(m.getIdMascota());
                hr.mostrarMensaje("Paciente eliminado");
                this.setEnabled(false);
                btnCancelarActionPerformed(evt);
            } catch (Exception e) {
                hr.mostrarError("Ocurrió un error al eliminar al paciente: "+e.getMessage());
                this.setEnabled(true);
            }
        }
    }//GEN-LAST:event_btnEliminarPacienteActionPerformed

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
            java.util.logging.Logger.getLogger(DetallePaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DetallePaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DetallePaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DetallePaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DetallePaciente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDeclararDefuncion;
    private javax.swing.JButton btnEditarPaciente;
    private javax.swing.JButton btnEliminarPaciente;
    private javax.swing.JButton btnVerPropietario;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblApellidosPropietario;
    private javax.swing.JLabel lblEdadPaciente;
    private javax.swing.JLabel lblGrupoSanguineo;
    private javax.swing.JLabel lblIdentificador;
    private javax.swing.JLabel lblImagenMadrePaciente;
    private javax.swing.JLabel lblImagenPaciente;
    private javax.swing.JLabel lblImagenPadrePaciente;
    private javax.swing.JLabel lblImagenPropietario;
    private javax.swing.JLabel lblInfoMadrePaciente;
    private javax.swing.JLabel lblInfoPadrePaciente;
    private javax.swing.JLabel lblNombrePaciente;
    private javax.swing.JLabel lblNombrePropietario;
    private javax.swing.JLabel lblNumeroChip;
    private javax.swing.JLabel lblRazaPaciente;
    private javax.swing.JLabel lblRunPropietario;
    private javax.swing.JLabel lblSexoPaciente;
    // End of variables declaration//GEN-END:variables
}
