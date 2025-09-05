package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import logic.controller.TouristActivityController;
import logic.dto.DtTouristActivity;
import logic.interfaces.ITouristActivityController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class ModifyActivity extends JInternalFrame {
  
	private static final long serialVersionUID = 1L;
	private JComboBox<DtTouristActivity> cbActividad;
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JSpinner spnDuracion;
    private JTextField txtCosto;
    private JTextField txtCiudad;
    private JTextField txtFechaAlta;

    private ITouristActivityController iActivityController;

    public ModifyActivity(ITouristActivityController iTAC) {
       
    	iActivityController = iTAC;
    	
        setSize(500, 400);
        setLayout(new BorderLayout());

        JPanel panelSelect = new JPanel();
        panelSelect.add(new JLabel("Actividad:"));
        cbActividad = new JComboBox<>();
        panelSelect.add(cbActividad);
        add(panelSelect, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        formPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        txtNombre.setEditable(false);
        formPanel.add(txtNombre);

        formPanel.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextArea(3, 20);
        formPanel.add(new JScrollPane(txtDescripcion));

        formPanel.add(new JLabel("Duración (horas):"));
        spnDuracion = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        formPanel.add(spnDuracion);

        formPanel.add(new JLabel("Costo por turista:"));
        txtCosto = new JTextField();
        formPanel.add(txtCosto);

        formPanel.add(new JLabel("Ciudad:"));
        txtCiudad = new JTextField();
        formPanel.add(txtCiudad);

        formPanel.add(new JLabel("Fecha Alta:"));
        txtFechaAlta = new JTextField();
        txtFechaAlta.setEditable(false);
        formPanel.add(txtFechaAlta);

        add(formPanel, BorderLayout.CENTER);


        JPanel panelButtons = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        panelButtons.add(btnGuardar);
        panelButtons.add(btnCancelar);
        add(panelButtons, BorderLayout.SOUTH);

        cbActividad.addActionListener(this::onActivitySelected);
        btnGuardar.addActionListener(this::onGuardar);
        btnCancelar.addActionListener(e -> dispose());

        loadActivities();
    }

    private void loadActivities() {
      
    }

    private void onActivitySelected(ActionEvent e) {
        DtTouristActivity act = (DtTouristActivity) cbActividad.getSelectedItem();
        if (act != null) {
            txtNombre.setText(act.getActivityName());
            txtDescripcion.setText(act.getDescription());
            spnDuracion.setValue(act.getDuration().toHours());
            txtCosto.setText(String.valueOf(act.getCostTurist()));
            txtCiudad.setText(act.getCity());
            txtFechaAlta.setText(act.getRegistratioDate()
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }

    private void onGuardar(ActionEvent e) {
        DtTouristActivity act = (DtTouristActivity) cbActividad.getSelectedItem();
        if (act != null) {
            try {
                iActivityController.modifyDescription(txtDescripcion.getText());
                iActivityController.modifyDuration(Duration.ofHours((int) spnDuracion.getValue()));
                iActivityController.modifyTouristFee(Float.parseFloat(txtCosto.getText()));
                iActivityController.modifyCity(txtCiudad.getText());

                JOptionPane.showMessageDialog(this, "Actividad modificada correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al modificar actividad: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
