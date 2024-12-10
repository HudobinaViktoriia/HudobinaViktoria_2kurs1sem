package org.example;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            ArchiveManager manager = new ArchiveManager(
                    "jdbc:mysql://localhost:3306/file_archive_db",
                    "root",
                    "111111111"
            );

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("""
                    1. Создать архив
                    2. Удалить архив
                    3. Просмотреть архивы
                    4. Добавить файл в архив
                    5. Удалить файл из архива
                    6. Просмотреть файлы в архиве
                    0. Выйти
                """);

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> {
                        System.out.print("Введите имя архива: ");
                        String name = scanner.nextLine();
                        manager.createArchive(name);
                        System.out.println("Архив создан.");
                    }
                    case 2 -> {
                        System.out.print("Введите ID архива для удаления: ");
                        int id = scanner.nextInt();
                        manager.deleteArchive(id);
                        System.out.println("Архив удален.");
                    }
                    case 3 -> {
                        List<Archive> archives = manager.listArchives();
                        archives.forEach(System.out::println);
                    }
                    case 4 -> {
                        System.out.print("Введите ID архива: ");
                        int archiveId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Введите имя файла: ");
                        String fileName = scanner.nextLine();
                        manager.addFileToArchive(archiveId, fileName);
                        System.out.println("Файл добавлен.");
                    }
                    case 5 -> {
                        System.out.print("Введите ID файла для удаления: ");
                        int fileId = scanner.nextInt();
                        manager.removeFileFromArchive(fileId);
                        System.out.println("Файл удален.");
                    }
                    case 6 -> {
                        System.out.print("Введите ID архива: ");
                        int archiveId = scanner.nextInt();
                        List<FileEntry> files = manager.listFilesInArchive(archiveId);
                        files.forEach(System.out::println);
                    }
                    case 0 -> {
                        System.out.println("Выход.");
                        return;
                    }
                    default -> System.out.println("Неверный выбор.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
