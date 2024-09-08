public class Book {
    public int id;
    public String name;
    public String author;

    public Book(int id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + author + "\n";
    }
}
