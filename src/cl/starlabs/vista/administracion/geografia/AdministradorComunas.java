/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.starlabs.vista.administracion.geografia;

import cl.starlabs.controladores.ComunaJpaController;
import cl.starlabs.controladores.PaisJpaController;
import cl.starlabs.controladores.ProvinciaJpaController;
import cl.starlabs.controladores.RegionJpaController;
import cl.starlabs.modelo.Comuna;
import cl.starlabs.modelo.Pais;
import cl.starlabs.modelo.Provincia;
import cl.starlabs.modelo.Region;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class AdministradorComunas extends javax.swing.JFrame {

    EntityManagerFactory emf = null;
    Pais pai = null;
    Region reg = null;
    Provincia pro = null;
    Comuna com = null;
    
    public AdministradorComunas() {
        initComponents();
        
        //EntityManagerFactory
        emf = Persistence.createEntityManagerFactory("SyncPetPU");
        
        //Centrando ventana
        this.setLocationRelativeTo(null);
        
        //deshabilitando campos
        cmbRegion.setEnabled(false);
        cmbProvincia.setEnabled(false);
        lblPais.setEnabled(false);
        lblPaisNombre.setEnabled(false);
        lblRegion.setEnabled(false);
        lblRegionNombre.setEnabled(false);
        lblProvincia.setEnabled(false);
        lblProvinciaNombre.setEnabled(false);
        panelInformacionComuna.setEnabled(false);
        lblNombreComuna.setEnabled(false);
        btnAccion.setEnabled(false);
        btnCancelar.setEnabled(false);
        //rellenando valores
        rellenarComboPais();
    }

    
    public void rellenarComboPais() {
        for(Pais p : new PaisJpaController(emf).findPaisEntities()) {
            cmbPais.addItem(p.getIdPais()+": "+p.getNombre());
        }
    }
    
    public void rellenarRegiones() {
        if(cmbPais.getSelectedItem().toString().compareToIgnoreCase("seleccione país") == 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un país para poder continuar...");
            cmbPais.requestFocus();
            cmbRegion.setEnabled(false);
            cmbProvincia.setEnabled(false);
            pai = null;
        }else{
            pai = new PaisJpaController(emf).findPais(Integer.parseInt(cmbPais.getSelectedItem().toString().split(":")[0]));
            cmbRegion.removeAllItems();
            cmbRegion.addItem("Seleccione Región...");
            for(Region r : pai.getRegionList()) {
                cmbRegion.addItem(r.getIdRegion()+": "+r.getNombre());
            }
            cmbRegion.setEnabled(true);
            cmbRegion.requestFocus();
        }
    }
    
    public void rellenaProvincias() {
        if(cmbRegion.getSelectedItem().toString().compareToIgnoreCase("Seleccione Región...") == 0 || cmbRegion.getSelectedItem().toString().isEmpty()) {
            cmbProvincia.removeAllItems();
            cmbRegion.requestFocus();
            cmbProvincia.setEnabled(false);
            JOptionPane.showMessageDialog(null, "Debe seleccionar una región para continuar...");
            reg = null;
        }else{
            cmbProvincia.removeAllItems();
            cmbProvincia.addItem("Seleccione Provincia...");
            reg = new RegionJpaController(emf).findRegion(Integer.parseInt(cmbRegion.getSelectedItem().toString().split(":")[0]));
            if(reg != null) {
                for(Provincia p : reg.getProvinciaList()) {
                    cmbProvincia.addItem(p.getIdProvincia()+": "+p.getNombre());
                }
            }
            cmbProvincia.setEnabled(true);
            cmbProvincia.requestFocus();
        }
    }
    
    public void rellenarTabla() {
        if(cmbProvincia.getSelectedItem().toString().compareToIgnoreCase("Seleccione Provincia...") == 0 || cmbProvincia.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Debe seleccionar una provincia para continuar");
            cmbProvincia.requestFocus();
            pro = null;
        }else{
            pro = new ProvinciaJpaController(emf).findProvincia(Integer.parseInt(cmbProvincia.getSelectedItem().toString().split(":")[0]));
            DefaultTableModel modelo = new DefaultTableModel(new Object [][] { }, new String [] { "ID", "Nombre" });
            if(pro != null) {
                for(Comuna c : pro.getComunaList()) {
                    Object[] fila = new Object[5];
                    fila[0] = c.getIdComuna();
                    fila[1] = c.getNombre();
                    modelo.addRow(fila);
                }
            }
            tablaComunas.setModel(modelo);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        cmbPais = new javax.swing.JComboBox<String>();
        cmbRegion = new javax.swing.JComboBox<String>();
        cmbProvincia = new javax.swing.JComboBox<String>();
        btnSeleccionarComuna = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaComunas = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        panelInformacionComuna = new javax.swing.JPanel();
        lblPais = new javax.swing.JLabel();
        lblRegion = new javax.swing.JLabel();
        lblProvincia = new javax.swing.JLabel();
        lblPaisNombre = new javax.swing.JLabel();
        lblRegionNombre = new javax.swing.JLabel();
        lblProvinciaNombre = new javax.swing.JLabel();
        txtNombreComuna = new javax.swing.JTextField();
        lblNombreComuna = new javax.swing.JLabel();
        btnAccion = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

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
        setTitle("SyncPet :: Administrar Comunas");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Comunas en el sistema"));

        cmbPais.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione País" }));
        cmbPais.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmbPaisFocusLost(evt);
            }
        });

        cmbRegion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cmbRegionFocusLost(evt);
            }
        });

        btnSeleccionarComuna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/find.png"))); // NOI18N
        btnSeleccionarComuna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarComunaActionPerformed(evt);
            }
        });

        tablaComunas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaComunas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaComunasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaComunas);
        if (tablaComunas.getColumnModel().getColumnCount() > 0) {
            tablaComunas.getColumnModel().getColumn(0).setResizable(false);
            tablaComunas.getColumnModel().getColumn(0).setPreferredWidth(25);
        }

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/add.png"))); // NOI18N
        btnAgregar.setToolTipText("Agregar nueva comuna");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/starlabs/imagenes/iconos/delete.png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar comuna seleccionada");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cmbPais, 0, 196, Short.MAX_VALUE)
            .addComponent(cmbRegion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(cmbProvincia, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnSeleccionarComuna, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbRegion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSeleccionarComuna, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregar)
                    .addComponent(btnEliminar))
                .addContainerGap())
        );

        panelInformacionComuna.setBorder(javax.swing.BorderFactory.createTitledBorder("Información de Comuna"));

        lblPais.setText("País");

        lblRegion.setText("Región");

        lblProvincia.setText("Provincia");

        lblPaisNombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPaisNombre.setText("No seleccionado");

        lblRegionNombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblRegionNombre.setText("No seleccionada");

        lblProvinciaNombre.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblProvinciaNombre.setText("No seleccionada");

        txtNombreComuna.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreComunaKeyTyped(evt);
            }
        });

        lblNombreComuna.setText("Nombre de Comuna:");

        btnAccion.setText("Registrar");
        btnAccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccionActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInformacionComunaLayout = new javax.swing.GroupLayout(panelInformacionComuna);
        panelInformacionComuna.setLayout(panelInformacionComunaLayout);
        panelInformacionComunaLayout.setHorizontalGroup(
            panelInformacionComunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacionComunaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInformacionComunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombreComuna)
                    .addGroup(panelInformacionComunaLayout.createSequentialGroup()
                        .addGap(0, 38, Short.MAX_VALUE)
                        .addComponent(btnAccion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)
                        .addGap(9, 9, 9))
                    .addGroup(panelInformacionComunaLayout.createSequentialGroup()
                        .addGroup(panelInformacionComunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreComuna)
                            .addComponent(lblProvincia)
                            .addComponent(lblRegion)
                            .addGroup(panelInformacionComunaLayout.createSequentialGroup()
                                .addComponent(lblPais)
                                .addGap(52, 52, 52)
                                .addComponent(lblPaisNombre))
                            .addComponent(lblProvinciaNombre)
                            .addComponent(lblRegionNombre))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelInformacionComunaLayout.setVerticalGroup(
            panelInformacionComunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInformacionComunaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInformacionComunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPais)
                    .addComponent(lblPaisNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRegion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(lblRegionNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProvincia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProvinciaNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNombreComuna)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombreComuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelInformacionComunaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAccion)
                    .addComponent(btnCancelar)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelInformacionComuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelInformacionComuna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbPaisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbPaisFocusLost
        rellenarRegiones();
    }//GEN-LAST:event_cmbPaisFocusLost

    private void cmbRegionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cmbRegionFocusLost
        rellenaProvincias();
    }//GEN-LAST:event_cmbRegionFocusLost

    private void btnSeleccionarComunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarComunaActionPerformed
        rellenarTabla();
    }//GEN-LAST:event_btnSeleccionarComunaActionPerformed

    private void tablaComunasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaComunasMouseClicked
        if(tablaComunas.getSelectedColumn() >= 0) {
            //recuperando valores desde la tabla
            DefaultTableModel modelo = (DefaultTableModel)tablaComunas.getModel();
            //consultando por el valor a cargar, es recuperado desde el valor seleccionado recuperando el ROW ID
            com = new ComunaJpaController(emf).findComuna(Integer.parseInt(String.valueOf(modelo.getValueAt(tablaComunas.getSelectedRow(), 0))));
            //si el pais no fue encontrado
            if(com == null) {
                //se informa que el pais no fue encontrado
                JOptionPane.showMessageDialog(null, "Error: La comuna no pudo ser encontrado por el sistema");
                com = null;
            }else{
                //si el pais es encontrado, se definen valores en campos por defecto
                txtNombreComuna.setText(com.getNombre());
                this.panelInformacionComuna.setEnabled(true);
                lblPais.setEnabled(true);
                lblPaisNombre.setEnabled(true);
                lblPaisNombre.setText(pai.getNombre());
                lblRegion.setEnabled(true);
                lblRegionNombre.setEnabled(true);
                lblRegionNombre.setText(reg.getNombre());
                lblProvincia.setEnabled(true);
                lblProvinciaNombre.setEnabled(true);
                lblProvinciaNombre.setText(pro.getNombre());
                lblNombreComuna.setEnabled(true);
                btnAccion.setEnabled(true);
                btnAccion.setText("Actualizar");
                txtNombreComuna.setEnabled(true);
                txtNombreComuna.requestFocus();
                btnCancelar.setEnabled(true);
                tablaComunas.setEnabled(false);
            }
        }
    }//GEN-LAST:event_tablaComunasMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        lblPais.setEnabled(false);
        lblPaisNombre.setEnabled(false);
        lblRegion.setEnabled(false);
        lblRegionNombre.setEnabled(false);
        lblProvincia.setEnabled(false);
        lblProvinciaNombre.setEnabled(false);
        panelInformacionComuna.setEnabled(false);
        lblNombreComuna.setEnabled(false);
        tablaComunas.setEnabled(true);
        tablaComunas.requestFocus();
        txtNombreComuna.setText("");
        btnAccion.setText("Actualizar");
        btnAccion.setEnabled(false);
        btnCancelar.setEnabled(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if(pai == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un país para poder continuar");
            cmbPais.requestFocus();
        }else{
            if(reg == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar una región para poder continuar");
                cmbRegion.requestFocus();
            }else{
                if(pro == null) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una provincia para poder continuar");
                    cmbProvincia.requestFocus();
                }else{
                    lblPais.setEnabled(true);
                    lblPaisNombre.setEnabled(true);
                    lblPaisNombre.setText(pai.getNombre());
                    lblRegion.setEnabled(true);
                    lblRegionNombre.setEnabled(true);
                    lblRegionNombre.setText(reg.getNombre());
                    lblProvincia.setEnabled(true);
                    lblProvinciaNombre.setEnabled(true);
                    lblProvinciaNombre.setText(pro.getNombre());
                    txtNombreComuna.setEnabled(true);
                    txtNombreComuna.requestFocus();
                    txtNombreComuna.setText("");
                    btnAccion.setText("Registrar");
                    btnAccion.setEnabled(true);
                    btnCancelar.setEnabled(true);
                    tablaComunas.setEnabled(false);
                }
            }
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        //comprobando si hay elementos seleccionados en la tabla
        if(tablaComunas.getSelectedColumn() >= 0) {
            //recuperando valores desde la tabla
            DefaultTableModel modelo = (DefaultTableModel)tablaComunas.getModel();
            //se consulta si se desea eliminar el valor seleccionado de la tabla
            int opcion = JOptionPane.showConfirmDialog(null, "¿Esta seguro de eliminar la comuna "+String.valueOf(modelo.getValueAt(tablaComunas.getSelectedRow(), 1)).toLowerCase()+"?");
            //si la respuesta es positiva
            if(opcion == 0) {
                try {
                    //se envia el id del pais para destrucción
                    new ComunaJpaController(emf).destroy(Integer.parseInt(modelo.getValueAt(tablaComunas.getSelectedRow(), 0).toString()));
                    //se informa al usuario
                    JOptionPane.showMessageDialog(null, "Comuna eliminada");
                    //se rellena la tabla desde 0
                    rellenarTabla();
                    //se setean valores por defecto haciendo clic en el boton cancelar
                    btnCancelarActionPerformed(evt);
                } catch (Exception e) {
                    //si ocurre un error, es informado al usuario
                    JOptionPane.showMessageDialog(null, "Error al eliminar comuna: "+e.getMessage());
                }
            }
        }  
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnAccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccionActionPerformed
        //se realiza la accion dependiendo del texto del boton
        if(btnAccion.getText().compareToIgnoreCase("registrar") == 0) {
            //si es registrar, se corrobora que el campo de texto de nombre de pais no este vacio
            if(!txtNombreComuna.getText().isEmpty()) {
                try {
                    //se crea un pais creando un objeto de tipo pais con los valores predeterminados y enviandolo al controlador
                    new ComunaJpaController(emf).create(new Comuna(new ComunaJpaController(emf).ultimo(), txtNombreComuna.getText(), pro));
                    //si la creacion fue correcta, se informa al usuario
                    JOptionPane.showMessageDialog(null, "Comuna "+txtNombreComuna.getText()+" ha sido registrada con éxito");
                    //se reestablecen los campos a sus valores por defecto
                    btnCancelarActionPerformed(evt);
                    //se rellena la tabla de paises
                    rellenarTabla();
                } catch (Exception e) {
                    //si ocurre un error, se informa al usuario
                    JOptionPane.showMessageDialog(null, "Ha ocurrido un error al intentar registrar la comuna: "+e.getMessage()); 
                }
            }else{
                //si el campo de texto esta vacío, se informa al usuario y se coloca el cursor para que escriba
                JOptionPane.showMessageDialog(null, "El campo de nombre de comuna esta vacía");
                txtNombreComuna.requestFocus();
            }
        //si desea actualizar un registro...
        }else if(btnAccion.getText().compareToIgnoreCase("actualizar") == 0) {
            try {
                //se verifica que el campo de texto no este vacio
                if(!txtNombreComuna.getText().isEmpty()) {
                    //se setea el nombre nuevo desde el campo de texto al objeto pais recuperado
                    com.setNombre(txtNombreComuna.getText());
                    //se envia el objeto con el nombre actualizado al controlador
                    new ComunaJpaController(emf).edit(com);
                    //si es actualizado, se informa al usuario
                    JOptionPane.showMessageDialog(null, "La comuna ha sido actualizada");
                    //se reestablecen los campos a sus valores por defecto
                    btnCancelarActionPerformed(evt);
                    //se rellena la tabla de paises nuevamente
                    rellenarTabla();
                }else{
                    //si el campo de texto esta vacio, se informa al usuarioi
                    JOptionPane.showMessageDialog(null, "El campo de nombre de país esta vacío");
                    //se coloca el cursor para que escriba en el campo
                    txtNombreComuna.requestFocus();
                }
            } catch (Exception e) {
                // si ocurre un error, es informado al usuario
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error al intentar registrar el país: "+e.getMessage()); 
            }
        }
    }//GEN-LAST:event_btnAccionActionPerformed

    private void txtNombreComunaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreComunaKeyTyped
        if(txtNombreComuna.getText().length() >= 200) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreComunaKeyTyped

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
            java.util.logging.Logger.getLogger(AdministradorComunas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdministradorComunas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdministradorComunas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdministradorComunas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdministradorComunas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccion;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnSeleccionarComuna;
    private javax.swing.JComboBox<String> cmbPais;
    private javax.swing.JComboBox<String> cmbProvincia;
    private javax.swing.JComboBox<String> cmbRegion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblNombreComuna;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblPaisNombre;
    private javax.swing.JLabel lblProvincia;
    private javax.swing.JLabel lblProvinciaNombre;
    private javax.swing.JLabel lblRegion;
    private javax.swing.JLabel lblRegionNombre;
    private javax.swing.JPanel panelInformacionComuna;
    private javax.swing.JTable tablaComunas;
    private javax.swing.JTextField txtNombreComuna;
    // End of variables declaration//GEN-END:variables
}
