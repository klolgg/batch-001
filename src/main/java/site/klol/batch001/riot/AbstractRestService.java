package site.klol.batch001.riot;

import java.util.function.Supplier;
import org.springframework.web.client.HttpClientErrorException;

public abstract class AbstractRestService {
    protected  <T> T doExecute(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (HttpClientErrorException.BadRequest | HttpClientErrorException.NotFound e) {
            return null;
        }
    }
}
