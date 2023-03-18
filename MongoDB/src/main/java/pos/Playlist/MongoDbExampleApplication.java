package pos.Playlist;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MongoDbExampleApplication{

	public static void main(String[] args) {
		SpringApplication.run(MongoDbExampleApplication.class, args);
	}

}
