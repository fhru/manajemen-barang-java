package com.rpl.ujianproject.ui;

import com.rpl.ujianproject.dao.BarangDao;
import com.rpl.ujianproject.entity.Barang;
import com.rpl.ujianproject.report.ReportGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BarangPanel extends JPanel {
    
    private JTextField txtKodeBarang, txtNamaBarang, txtHarga, txtStok, txtCari;
    private JTable tableBarang;
    private DefaultTableModel tableModel;
    private JButton btnSimpan, btnUpdate, btnHapus, btnBersihkan, btnCetak, btnCari;
    
    private BarangDao barangDao;
    private Long selectedId = null;
    
    public BarangPanel() {
        barangDao = new BarangDao();
        initComponents();
        loadData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Data Barang"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Kode Barang
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Kode Barang:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtKodeBarang = new JTextField(20);
        formPanel.add(txtKodeBarang, gbc);
        
        // Nama Barang
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Nama Barang:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtNamaBarang = new JTextField(20);
        formPanel.add(txtNamaBarang, gbc);
        
        // Harga
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Harga:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtHarga = new JTextField(20);
        formPanel.add(txtHarga, gbc);
        
        // Stok
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Stok:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtStok = new JTextField(20);
        formPanel.add(txtStok, gbc);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnSimpan = new JButton("Simpan");
        btnUpdate = new JButton("Update");
        btnHapus = new JButton("Hapus");
        btnBersihkan = new JButton("Bersihkan");
        btnCetak = new JButton("Cetak Laporan");
        
        buttonPanel.add(btnSimpan);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnHapus);
        buttonPanel.add(btnBersihkan);
        buttonPanel.add(btnCetak);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(buttonPanel, gbc);
        
        add(formPanel, BorderLayout.NORTH);
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari:"));
        txtCari = new JTextField(20);
        searchPanel.add(txtCari);
        btnCari = new JButton("Cari");
        searchPanel.add(btnCari);
        
        // Table
        String[] columns = {"ID", "Kode Barang", "Nama Barang", "Harga", "Stok"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableBarang = new JTable(tableModel);
        tableBarang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(tableBarang);
        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        add(tablePanel, BorderLayout.CENTER);
        
        // Event Listeners
        btnSimpan.addActionListener(e -> simpan());
        btnUpdate.addActionListener(e -> update());
        btnHapus.addActionListener(e -> hapus());
        btnBersihkan.addActionListener(e -> bersihkan());
        btnCetak.addActionListener(e -> cetakLaporan());
        btnCari.addActionListener(e -> cari());
        
        tableBarang.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tableBarang.getSelectedRow();
                if (row >= 0) {
                    selectedId = Long.parseLong(tableModel.getValueAt(row, 0).toString());
                    txtKodeBarang.setText(tableModel.getValueAt(row, 1).toString());
                    txtNamaBarang.setText(tableModel.getValueAt(row, 2).toString());
                    String hargaStr = tableModel.getValueAt(row, 3).toString().replace("Rp ", "").replace(".", "").replace(",", "");
                    txtHarga.setText(hargaStr);
                    txtStok.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        List<Barang> list = barangDao.findAll();
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        for (Barang b : list) {
            tableModel.addRow(new Object[]{
                b.getId(),
                b.getKodeBarang(),
                b.getNamaBarang(),
                format.format(b.getHarga()),
                b.getStok()
            });
        }
    }
    
    private void simpan() {
        try {
            if (!validateInput()) return;
            
            Barang barang = new Barang();
            barang.setKodeBarang(txtKodeBarang.getText().trim());
            barang.setNamaBarang(txtNamaBarang.getText().trim());
            barang.setHarga(Double.parseDouble(txtHarga.getText().trim()));
            barang.setStok(Integer.parseInt(txtStok.getText().trim()));
            
            barangDao.save(barang);
            JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
            bersihkan();
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void update() {
        try {
            if (selectedId == null) {
                JOptionPane.showMessageDialog(this, "Pilih data yang akan diupdate!");
                return;
            }
            if (!validateInput()) return;
            
            Barang barang = barangDao.findById(selectedId);
            barang.setKodeBarang(txtKodeBarang.getText().trim());
            barang.setNamaBarang(txtNamaBarang.getText().trim());
            barang.setHarga(Double.parseDouble(txtHarga.getText().trim()));
            barang.setStok(Integer.parseInt(txtStok.getText().trim()));
            
            barangDao.update(barang);
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            bersihkan();
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hapus() {
        try {
            if (selectedId == null) {
                JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                Barang barang = barangDao.findById(selectedId);
                barangDao.delete(barang);
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                bersihkan();
                loadData();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void bersihkan() {
        selectedId = null;
        txtKodeBarang.setText("");
        txtNamaBarang.setText("");
        txtHarga.setText("");
        txtStok.setText("");
        tableBarang.clearSelection();
    }
    
    private void cari() {
        String keyword = txtCari.getText().trim();
        tableModel.setRowCount(0);
        List<Barang> list = keyword.isEmpty() ? barangDao.findAll() : barangDao.search(keyword);
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        for (Barang b : list) {
            tableModel.addRow(new Object[]{
                b.getId(),
                b.getKodeBarang(),
                b.getNamaBarang(),
                format.format(b.getHarga()),
                b.getStok()
            });
        }
    }
    
    private void cetakLaporan() {
        try {
            ReportGenerator.generateReport();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validateInput() {
        if (txtKodeBarang.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kode Barang tidak boleh kosong!");
            return false;
        }
        if (txtNamaBarang.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Barang tidak boleh kosong!");
            return false;
        }
        try {
            Double.parseDouble(txtHarga.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Harga harus berupa angka!");
            return false;
        }
        try {
            Integer.parseInt(txtStok.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok harus berupa angka!");
            return false;
        }
        return true;
    }
}
