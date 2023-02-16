public class Emulator {
    public static void main(String[] args) {
        RedisStorage redis = new RedisStorage();
        redis.init();
        redis.markDisplayUserOnPage(1);
        waitMillis(1000);
        redis.markDisplayUserOnPage(2);
        waitMillis(1000);
        redis.markDisplayUserOnPage(3);
        waitMillis(1000);
        redis.markDisplayUserOnPage(4);
        System.out.println(redis.getNextShownVisitor());
        redis.shutdown();
    }

    private static void waitMillis(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
