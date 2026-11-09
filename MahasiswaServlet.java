package com.mahasiswa.web;

import com.mahasiswa.rmi.Mahasiswa;
import com.mahasiswa.rmi.MahasiswaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/mahasiswa")
public class MahasiswaServlet extends HttpServlet {

    private MahasiswaService getRMIService() throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        return (MahasiswaService) registry.lookup("MahasiswaService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String keyword = req.getParameter("keyword");

        try {
            MahasiswaService service = getRMIService();

            if ("delete".equals(action)) {
                String nim = req.getParameter("nim");
                service.deleteMahasiswa(nim);
                resp.sendRedirect("mahasiswa");
                return;
            }

            List<Mahasiswa> listMahasiswa;
            if (keyword != null && !keyword.trim().isEmpty()) {
                Mahasiswa mhs = service.getMahasiswaByNim(keyword.trim());
                listMahasiswa = new ArrayList<>();
                if (mhs != null) listMahasiswa.add(mhs);
            } else {
                listMahasiswa = service.getAllMahasiswa();
            }

            req.setAttribute("listMahasiswa", listMahasiswa);
            req.setAttribute("jumlahMahasiswa", service.getJumlahMahasiswa());
            req.setAttribute("errorMessage", null);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Koneksi ke RMI Server Gagal: " + e.getMessage());
            req.setAttribute("listMahasiswa", new ArrayList<>());
            req.setAttribute("jumlahMahasiswa", 0);
        }

        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nim = req.getParameter("nim");
        String nama = req.getParameter("nama");
        String jurusan = req.getParameter("jurusan");

        try {
            MahasiswaService service = getRMIService();
            Mahasiswa mhs = new Mahasiswa(nim, nama, jurusan);
            service.addMahasiswa(mhs);
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Gagal menambah data: " + e.getMessage());
        }

        resp.sendRedirect("mahasiswa");
    }
}
