import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.lang.System.out;

public class RedisStorage {
    private RedissonClient redisson;
    private RKeys rKeys;
    private RScoredSortedSet<String> visitorsQueue;
    private final static String KEY = "Visitors";
    private long getLastShowDate(){
        return LocalDateTime.now().toEpochSecond(ZoneOffset.MIN);
    }


    public void printAllKeys(){
        Iterable<String> keys = rKeys.getKeys();
        keys.forEach(System.out::println);
    }

    public void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
        rKeys = redisson.getKeys();
        visitorsQueue = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);
    }

    void shutdown(){
        redisson.shutdown();
    }

    void markDisplayUserOnPage(int user_id){
        visitorsQueue.add(getLastShowDate(), String.valueOf(user_id));
    }


//
//    void deleteOldShow(){
//        visitorsQueue.removeRangeByScore()
//    }

    public int getNextShownVisitor(){
        return Integer.parseInt(visitorsQueue.first());
    }
}
