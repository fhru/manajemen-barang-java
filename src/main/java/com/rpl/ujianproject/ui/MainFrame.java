package com.rpl.ujianproject.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    
    public MainFrame() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Aplikasi Manajemen Barang");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        BarangPanel barangPanel = new BarangPanel();
        add(barangPanel, BorderLayout.CENTER);
    }
}
