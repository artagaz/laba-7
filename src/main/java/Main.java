import com.google.gson.Gson;
import lombok.Builder;
import lombok.Data;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //parsing JSON
        File file = new File("/home/andrew/IdeaProjects/laba-7/books.json");
        Reader reader;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Gson gson = new Gson();

        Person[] person = gson.fromJson(reader, Person[].class);

        //Selector
        int Select = 0;
        try {
            Select = scanner.nextInt();
        } catch (Exception ignored) {
        }

        switch (Select) {

            case 1:
                //visitors list and count
                ArrayList<String> visitors = new ArrayList<>();
                for (Person p : person) {
                    visitors.add(p.getName() + " " + p.getSurname());
                }
                System.out.println(visitors.size() + " visitors: " + visitors);
                break;
            case 2:
                //unique books from favorites
                LinkedHashSet<String> bookNames = new LinkedHashSet<>();
                for (Person p : person) {
                    for (Book b : p.getFavoriteBooks()) {
                        bookNames.add(b.getName());
                    }
                }
                System.out.println(bookNames.size() + " books: " + bookNames);
                break;
            case 3:
                //sort by year
                LinkedHashSet<Book> books = new LinkedHashSet<>();
                for (Person p : person) {
                    books.addAll(Arrays.asList(p.getFavoriteBooks()));
                }
                Comparator<Book> YC = new YearComparator();//comparator of years
                TreeSet<Book> BCbY = new TreeSet<>(YC);//array w comp
                BCbY.addAll(books);
                System.out.println(BCbY);
                break;
            case 4:
                //check if someone have book w author Jane Austen
                for (Person p : person) {
                    for (Book b : p.getFavoriteBooks()) {
                        if (b.getAuthor().equals("Jane Austen")) System.out.println(p.getName());
                    }
                }
                break;
            case 5:
                //max num of added books
                Person readingExpert = person[0];
                for (int i = 1; i < person.length; i++) {
                    if (readingExpert.getFavoriteBooks().length < person[i].getFavoriteBooks().length)//finding max num
                        readingExpert = person[i];
                }
                System.out.println(readingExpert.getName() + " " + readingExpert.getSurname() + " readed " + readingExpert.getFavoriteBooks().length + " books");
                break;
            case 6:
                //SMS
                ArrayList<Person> smsAgree = new ArrayList<>();
                double mid = 0.0;
                for (int i = 0; i < person.length; i++) {
                    if (person[i].isSubscribed()) smsAgree.add(person[i]);//list of subscribed
                    mid += person[i].getFavoriteBooks().length;
                    if (i == person.length - 1) mid /= person.length;//mid
                }

                ArrayList<SMS> smsList = new ArrayList<>();//generating sms list
                for (Person p : smsAgree) {
                    if (p.getFavoriteBooks().length > mid)
                        smsList.add(SMS.builder().number(p.getPhone()).message("You are bookworm").build());
                    else if (p.getFavoriteBooks().length < mid)
                        smsList.add(SMS.builder().number(p.getPhone()).message("Read more").build());
                    else smsList.add(SMS.builder().number(p.getPhone()).message("Fine").build());
                }

                System.out.println(smsList);
                break;
            default:
                break;
        }


    }
}

@Data
class Book {
    private String name;
    private String author;
    private int publishingYear;
    private String isbn;
    private String publisher;
}

//comparator bu year
class YearComparator implements Comparator<Book> {
    @Override
    public int compare(Book b1, Book b2) {
        return b1.getPublishingYear() - b2.getPublishingYear();
    }
}

@Data
class Person {
    private String name;
    private String surname;
    private String phone;
    private boolean subscribed;
    private Book[] favoriteBooks;
}

@Data
@Builder
class SMS {
    private String number;
    private String message;
}
