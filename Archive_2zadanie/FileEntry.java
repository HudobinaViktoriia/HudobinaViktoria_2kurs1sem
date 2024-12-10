package org.example;

public class FileEntry {
    private int id;
    private String fileName;
    private int archiveId;

    public FileEntry(int id, String fileName, int archiveId) {
        this.id = id;
        this.fileName = fileName;
        this.archiveId = archiveId;
    }

    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public int getArchiveId() {
        return archiveId;
    }

    @Override
    public String toString() {
        return "FileEntry{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", archiveId=" + archiveId +
                '}';
    }
}
