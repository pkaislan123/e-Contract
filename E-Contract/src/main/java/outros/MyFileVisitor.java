package main.java.outros;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class MyFileVisitor extends SimpleFileVisitor<Path> {
	
	ArrayList<String> listadeArquivos = new ArrayList<>();
	
    public FileVisitResult visitFile(Path path, BasicFileAttributes fileAttributes){
        //System.out.println("Nome do arquivo:" + path.getFileName());
       // System.out.println("Caminho do arquivo: " + path.toString());
        listadeArquivos.add(path.toString() );
    	return FileVisitResult.CONTINUE;
    }
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes fileAttributes){
        //System.out.println("----------Nome do diretório:" + path + "----------");
        return FileVisitResult.CONTINUE;
    }
    
    public ArrayList<String> getArquivos()
    {
    	return listadeArquivos;
    }
}

 