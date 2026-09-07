/**
 * Book クラスを使うデモ。
 */
public class LibraryApp {
    public static void main(String[] args) {
        Book book = new Book("リーダブルコード", "Dustin Boswell");
        System.out.println(book);

        book.borrow();
        System.out.println("借りた後: " + book);

        book.giveBack();
        System.out.println("返却後: " + book);
    }
}
