/**
 * スタック / ヒープとスコープを観察するデモ。
 *
 * 実行: javac ScopeAndMemoryDemo.java && java ScopeAndMemoryDemo
 */
public class ScopeAndMemoryDemo {
    /** クラス変数。全インスタンスで共有される。 */
    static int createdCount = 0;

    /** インスタンス変数。オブジェクトごとにヒープ上へ。 */
    int id;
    String label;

    ScopeAndMemoryDemo(String label) {
        createdCount++;
        this.id = createdCount;
        this.label = label;
    }

    public static void main(String[] args) {
        // args もローカル（このメソッドのスタックフレーム上の参照）
        System.out.println("args.length=" + args.length);

        int localOnStack = 42; // プリミティブはスタック
        System.out.println("localOnStack=" + localOnStack);

        ScopeAndMemoryDemo first = new ScopeAndMemoryDemo("first");
        ScopeAndMemoryDemo second = new ScopeAndMemoryDemo("second");
        // first / second という参照はスタック、オブジェクト本体はヒープ
        first.print();
        second.print();
        System.out.println("共有 createdCount=" + createdCount);

        if (localOnStack > 0) {
            int inner = 7; // if ブロックのスコープ
            System.out.println("inner=" + inner);
        }
        // System.out.println(inner); // スコープ外なのでコンパイルできない

        callOnce("hello");
        // メソッドを抜けると、callOnce のローカル変数は消える
    }

    static void callOnce(String message) {
        int depthMarker = 1;
        System.out.println("callOnce: " + message + ", depthMarker=" + depthMarker);
    }

    void print() {
        System.out.println("id=" + id + ", label=" + label);
    }
}
