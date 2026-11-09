package com.mahasiswa.server;

import com.mahasiswa.rmi.MahasiswaService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServerApp {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            MahasiswaService service = new MahasiswaServiceImpl();
            registry.rebind("MahasiswaService", service);

            System.out.println("=== RMI Server Berhasil Dijalankan ===");
            System.out.println("Port: 1099");
            System.out.println("Service Registered: MahasiswaService");
        } catch (Exception e) {
            System.err.println("Gagal menjalankan RMI Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
