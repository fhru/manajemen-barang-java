# Aplikasi Manajemen Barang

Aplikasi desktop Java untuk manajemen data barang dengan fitur CRUD (Create, Read, Update, Delete) lengkap menggunakan Java Swing, Hibernate ORM, dan JasperReports.

## Struktur Project

```
UjianProject/
├── pom.xml
├── README.md
├── MANUAL.md
└── src/main/
    ├── java/com/rpl/ujianproject/
    │   ├── Main.java
    │   ├── config/
    │   │   └── HibernateUtil.java
    │   ├── dao/
    │   │   └── BarangDao.java
    │   ├── entity/
    │   │   └── Barang.java
    │   ├── report/
    │   │   └── ReportGenerator.java
    │   └── ui/
    │       ├── MainFrame.java
    │       └── BarangPanel.java
    └── resources/
        ├── hibernate.cfg.xml
        └── reports/
            └── laporan_barang.jrxml
```

## Instalasi & Konfigurasi

### 1. Clone/Download Project

```bash
git clone <repository-url>
cd UjianProject
```

### 2. Konfigurasi Database

Buka file `src/main/resources/hibernate.cfg.xml` dan sesuaikan konfigurasi:

```xml
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/db_barang?createDatabaseIfNotExist=true</property>
<property name="hibernate.connection.username">root</property>
<property name="hibernate.connection.password">PASSWORD_ANDA</property>
```

### 3. Build Project

### Via NetBeans
1. Buka project di NetBeans
2. Klik kanan project > Clean And Build
3. Klik kanan project > Run

## Fitur Aplikasi

- **Create** - Tambah data barang baru
- **Read** - Lihat semua data barang dalam tabel
- **Update** - Edit data barang yang sudah ada
- **Delete** - Hapus data barang dengan konfirmasi
- **Search** - Cari barang berdasarkan kode atau nama
- **Report** - Cetak laporan daftar barang ke PDF

## Screenshot

[![image.png](https://i.postimg.cc/xTv07SZ1/image.png)](https://postimg.cc/tYgQZ8yK)
