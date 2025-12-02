package com.rpl.ujianproject.report;

import com.rpl.ujianproject.dao.BarangDao;
import com.rpl.ujianproject.entity.Barang;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.util.List;

public class ReportGenerator {
    
    public static void generateReport() throws JRException {
        BarangDao barangDao = new BarangDao();
        List<Barang> barangList = barangDao.findAll();
        
        InputStream reportStream = ReportGenerator.class.getResourceAsStream("/reports/laporan_barang.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(barangList);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
        
        JasperViewer.viewReport(jasperPrint, false);
    }
}
