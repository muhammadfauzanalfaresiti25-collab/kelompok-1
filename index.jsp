<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.mahasiswa.rmi.Mahasiswa" %>
<!DOCTYPE html>
<html>
<head>
    <title>Monitoring Data Mahasiswa (Java RMI)</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        table { border-collapse: collapse; width: 100%; margin-top: 15px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
        .error { color: red; font-weight: bold; padding: 10px; border: 1px solid red; background: #ffe6e6; }
        .card { background: #f9f9f9; padding: 15px; border-radius: 5px; margin-bottom: 20px; }
    </style>
</head>
<body>

    <h2>Aplikasi Web Monitoring Data Mahasiswa (Java RMI)</h2>

    <% 
        String error = (String) request.getAttribute("errorMessage");
        if (error != null) { 
    %>
        <div class="error"><%= error %></div>
    <% } %>

    <div class="card">
        <h3>Form Tambah Mahasiswa</h3>
        <form action="mahasiswa" method="post">
            NIM: <input type="text" name="nim" required>
            Nama: <input type="text" name="nama" required>
            Jurusan: <input type="text" name="jurusan" required>
            <button type="submit">Tambah</button>
        </form>
    </div>

    <div class="card">
        <h3>Pencarian Mahasiswa</h3>
        <form action="mahasiswa" method="get">
            Cari NIM: <input type="text" name="keyword">
            <button type="submit">Cari</button>
            <a href="mahasiswa"><button type="button">Reset</button></a>
        </form>
    </div>

    <%
        Object totalObj = request.getAttribute("jumlahMahasiswa");
        int total = (totalObj != null) ? (Integer) totalObj : 0;
    %>
    <h3>Daftar Mahasiswa (Total: <%= total %>)</h3>
    <table>
        <thead>
            <tr>
                <th>NIM</th>
                <th>Nama</th>
                <th>Jurusan</th>
                <th>Aksi</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Mahasiswa> list = (List<Mahasiswa>) request.getAttribute("listMahasiswa");
                if (list != null && !list.isEmpty()) {
                    for (Mahasiswa m : list) {
            %>
            <tr>
                <td><%= m.getNim() %></td>
                <td><%= m.getNama() %></td>
                <td><%= m.getJurusan() %></td>
                <td><a href="mahasiswa?action=delete&nim=<%= m.getNim() %>" onclick="return confirm('Hapus data?')">Hapus</a></td>
            </tr>
            <% 
                    }
                } else {
            %>
            <tr><td colspan="4">Data tidak ditemukan / Kosong.</td></tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>
