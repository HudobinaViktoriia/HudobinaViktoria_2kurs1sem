package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArchiveManager {
    private Connection connection;

    public ArchiveManager(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        initDatabase();
    }

    private void initDatabase() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("""
            CREATE TABLE IF NOT EXISTS archives (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                creation_date DATE NOT NULL
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS files (
                id INT AUTO_INCREMENT PRIMARY KEY,
                file_name VARCHAR(255) NOT NULL,
                archive_id INT NOT NULL,
                FOREIGN KEY (archive_id) REFERENCES archives(id)
            );
        """);
    }

    public void createArchive(String name) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO archives (name, creation_date) VALUES (?, NOW())"
        );
        stmt.setString(1, name);
        stmt.executeUpdate();
    }

    public void deleteArchive(int archiveId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM files WHERE archive_id = ?"
        );
        stmt.setInt(1, archiveId);
        stmt.executeUpdate();

        stmt = connection.prepareStatement(
                "DELETE FROM archives WHERE id = ?"
        );
        stmt.setInt(1, archiveId);
        stmt.executeUpdate();
    }

    public List<Archive> listArchives() throws SQLException {
        List<Archive> archives = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM archives");

        while (rs.next()) {
            archives.add(new Archive(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDate("creation_date")
            ));
        }

        return archives;
    }

    public void addFileToArchive(int archiveId, String fileName) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO files (file_name, archive_id) VALUES (?, ?)"
        );
        stmt.setString(1, fileName);
        stmt.setInt(2, archiveId);
        stmt.executeUpdate();
    }

    public void removeFileFromArchive(int fileId) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(
                "DELETE FROM files WHERE id = ?"
        );
        stmt.setInt(1, fileId);
        stmt.executeUpdate();
    }

    public List<FileEntry> listFilesInArchive(int archiveId) throws SQLException {
        List<FileEntry> files = new ArrayList<>();
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM files WHERE archive_id = ?"
        );
        stmt.setInt(1, archiveId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            files.add(new FileEntry(
                    rs.getInt("id"),
                    rs.getString("file_name"),
                    rs.getInt("archive_id")
            ));
        }

        return files;
    }
}
