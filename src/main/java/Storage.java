import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Storage {
    private RedissonClient redisson;
    private RKeys rKeys;
    private RScoredSortedSet<String> visitorsQueue;
    private final static String KEY = "Visitors";
    private long getLastShowDate(){
        return LocalDateTime.now().toEpochSecond(ZoneOffset.MIN);
    }
}
