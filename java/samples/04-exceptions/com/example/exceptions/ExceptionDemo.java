package com.example.exceptions;

/**
 * try-catch とカスタム例外のデモ。
 *
 * 実行例:
 *   cd java/samples
 *   javac 04-exceptions/com/example/exceptions/*.java
 *   java -cp 04-exceptions com.example.exceptions.ExceptionDemo
 */
public class ExceptionDemo {
    public static void main(String[] args) {
        Account account = new Account("Taro", 1000);

        try {
            account.withdraw(300);
            System.out.println("引き出し成功。残高=" + account.getBalance());
            account.withdraw(900); // 失敗する
        } catch (InsufficientBalanceException e) {
            System.err.println("業務エラー: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("入力エラー: " + e.getMessage());
        } finally {
            System.out.println("最終残高=" + account.getBalance());
        }
    }
}
