package site.klol.batch001.job.step01;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.database.JpaItemWriter;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class JpaItemListWriter<T> extends JpaItemWriter<List<T>> {
    private final JpaItemWriter<T> delegate;

    @Override
    public void write(Chunk<? extends List<T>> items) {
        List<T> totalList = new ArrayList<>();

        for(List<T> list : items) {
            totalList.addAll(list);
        }

        delegate.write(new Chunk<>(totalList));
    }
}
