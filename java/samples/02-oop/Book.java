/**
 * 本を表すドメインクラス（カプセル化の例）。
 */
public class Book {
    private final String title;
    private final String author;
    private boolean borrowed;

    public Book(String title, String author) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("author must not be blank");
        }
        this.title = title;
        this.author = author;
        this.borrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void borrow() {
        if (borrowed) {
            throw new IllegalStateException("already borrowed: " + title);
        }
        borrowed = true;
    }

    public void giveBack() {
        borrowed = false;
    }

    @Override
    public String toString() {
        return title + " / " + author + " [" + (borrowed ? "貸出中" : "在庫あり") + "]";
    }
}
