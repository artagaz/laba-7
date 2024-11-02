import com.google.gson.Gson;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;

import static java.util.Comparator.comparing;

public class Main {
    public static void main(String[] args){

        //parsing JSON
        File file = new File("/home/andrew/IdeaProjects/laba-7/books.json");
        Reader reader = null;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();

        Person[] person = gson.fromJson(reader, Person[].class);

        //1
        ArrayList<String> visitors = new ArrayList<>();
        for (Person p : person){
            visitors.add(p.getName()+" "+p.getSurname());
        }
        System.out.println(visitors.size()+" visitors: "+visitors.toString());

        //2
        LinkedHashSet<Book> books = new LinkedHashSet<>();
        for (Person p : person){
            books.addAll(Arrays.asList(p.getFavoriteBooks()));
        }
        ArrayList<String> names = new ArrayList<>();
        for (Book b : books){
            names.add(b.getName());
        }
        System.out.println(books.size()+" books: "+ names.toString());

        //3
        Comparator<Book> YC = new YearComparator();
        TreeSet<Book> BCbY = new TreeSet<>(YC);
        BCbY.addAll(books);
        System.out.println(BCbY.toString());


    }
}

@Data
class Book{
    private String name;
    private String author;
    private int publishingYear;
    private String isbn;
    private String publisher;
}

class YearComparator implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return b1.getPublishingYear() - b2.getPublishingYear();
    }
}

@Data
class Person{
    private String name;
    private String surname;
    private String phone;
    private boolean subscribed;
    private Book[] favoriteBooks;
    String Show(){
        System.out.println(name);
        return null;
    }
}
